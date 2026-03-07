package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundMapChunkPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceBlockRegionUpdatePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceChunkVisibilityPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import id.brokiem.voxelbridge.util.compression.ZlibUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JavaMapChunkTranslator implements ClientboundTranslator<JavaClientboundMapChunkPacket> {

    private static final byte[] BLOCK_REMAP = new byte[256];

    static {
        for (int i = 0; i < 256; i++) {
            BLOCK_REMAP[i] = (i <= 160 || (i >= 170 && i <= 173)) ? (byte) i : (byte) 1;
        }
    }

    private static final int META_OFF = 65536;
    private static final int BLIGHT_OFF = META_OFF + 32768;
    private static final int SLIGHT_OFF = BLIGHT_OFF + 32768;
    private static final int BIOME_OFF = SLIGHT_OFF + 32768;
    private static final int TOTAL_SIZE = BIOME_OFF + 256;

    @Override
    public TranslationResult translate(JavaClientboundMapChunkPacket input, Session session) {
        try {
            byte[] decompressed = ZlibUtil.decompress(input.getCompressedData());

            LceChunkVisibilityPacket visibility = new LceChunkVisibilityPacket();
            visibility.setX(input.getX());
            visibility.setZ(input.getZ());
            visibility.setVisible(true);

            LceBlockRegionUpdatePacket chunk = buildConsoleChunk(input.getX(), input.getZ(), input.getBitMap() & 0xFFFF, input.getAddBitMap() & 0xFFFF, input.isGroundUp(), decompressed, true);

            return TranslationResult.toClient(List.of(visibility, chunk));
        } catch (Exception e) {
            log.warn("Error translating chunk at {},{}: {}", input.getX(), input.getZ(), e.getMessage());
            return TranslationResult.empty();
        }
    }

    static LceBlockRegionUpdatePacket buildConsoleChunk(int chunkX, int chunkZ, int primaryBitMask, int addBitMask, boolean groundUp, byte[] decompressed, boolean skyLightSent) {
        byte[] out = new byte[TOTAL_SIZE];
        // Absent sky-light sections default to fully-lit (nibble = 15 = 0xF) rather than dark.
        Arrays.fill(out, SLIGHT_OFF, SLIGHT_OFF + 32768, (byte) 0xFF);

        final int sectionCount = Integer.bitCount(primaryBitMask);
        final int javaMetaBase = sectionCount * 4096;
        final int javaBlitBase = javaMetaBase + sectionCount * 2048;
        final int javaSlitBase = skyLightSent ? javaBlitBase + sectionCount * 2048 : -1;

        int sectionIdx = 0;
        for (int s = 0; s < 16; s++) {
            if ((primaryBitMask & (1 << s)) == 0) continue;

            final int baseY = s << 4; // s * 16

            // Both Java and Console use YZX order. Within a section the index is identical;
            // the Console buffer is simply offset by baseY * 256 relative to the Java section.
            final int jBlk = sectionIdx * 4096;
            final int consBlkBase = baseY << 8; // baseY * 256
            for (int j = 0; j < 4096; j++) {
                out[consBlkBase + j] = BLOCK_REMAP[decompressed[jBlk + j] & 0xFF];
            }

            // Java packed-nibble layout:
            //   index i = y*256 + z*16 + x
            //   byte  = i >> 1
            //   low nibble when i is even  ←→  when x is even (y*256 and z*16 are always even)
            //
            // Console packed-nibble layout (SparseDataStorage / SparseLightStorage):
            //   Split at y = 128.  Section boundaries are multiples of 16, so no section
            //   straddles y = 128 — we branch once per section, not once per voxel.
            //   Lower half (totalY 0-127):
            //     byte = (x*16 + z) * 64 + totalY / 2
            //     low nibble when totalY is even
            //   Upper half (totalY 128-255):
            //     byte = 16384 + (x*16 + z) * 64 + (totalY - 128) / 2
            //     low nibble when (totalY - 128) is even  ←→  when totalY is even
            //
            // Because baseY is always even (multiple of 16), totalY = baseY + y pairs neatly:
            //   y=0 (even) → low nibble of console byte at offset yp=0
            //   y=1 (odd)  → high nibble of the same byte
            // This lets us assemble each console byte in a single write.
            //
            // Java y-stride in the nibble array = 128 bytes (= 256 blocks / 2).
            // For fixed (x, z), y1 = y0 + 1  →  jb1 = jb0 + 128.

            final int jMeta = javaMetaBase + sectionIdx * 2048;
            final int jBlit = javaBlitBase + sectionIdx * 2048;
            final int jSlit = (javaSlitBase >= 0) ? javaSlitBase + sectionIdx * 2048 : -1;

            if (baseY < 128) {
                // Lower half: consByte base = (x*16 + z) * 64 + baseY/2
                for (int x = 0; x < 16; x++) {
                    final boolean jLow = (x & 1) == 0;
                    for (int z = 0; z < 16; z++) {
                        final int cBase = (x * 16 + z) * 64 + (baseY >> 1);
                        for (int yp = 0; yp < 8; yp++) { // 8 y-pairs per section
                            final int y0 = yp << 1;
                            final int jb0 = (y0 * 256 + z * 16 + x) >> 1; // = y0*128 + z*8 + x/2
                            final int jb1 = jb0 + 128;                     // y1 = y0 + 1

                            final byte rawMeta0 = decompressed[jMeta + jb0];
                            final byte rawMeta1 = decompressed[jMeta + jb1];
                            out[META_OFF + cBase + yp] = jLow
                                    ? (byte) ((rawMeta0 & 0x0F) | (rawMeta1 & 0x0F) << 4)
                                    : (byte) ((rawMeta0 >> 4 & 0x0F) | (rawMeta1 >> 4 & 0x0F) << 4);

                            final byte rawBlit0 = decompressed[jBlit + jb0];
                            final byte rawBlit1 = decompressed[jBlit + jb1];
                            out[BLIGHT_OFF + cBase + yp] = jLow
                                    ? (byte) ((rawBlit0 & 0x0F) | (rawBlit1 & 0x0F) << 4)
                                    : (byte) ((rawBlit0 >> 4 & 0x0F) | (rawBlit1 >> 4 & 0x0F) << 4);

                            if (jSlit >= 0) {
                                final byte rawSlit0 = decompressed[jSlit + jb0];
                                final byte rawSlit1 = decompressed[jSlit + jb1];
                                out[SLIGHT_OFF + cBase + yp] = jLow
                                        ? (byte) ((rawSlit0 & 0x0F) | (rawSlit1 & 0x0F) << 4)
                                        : (byte) ((rawSlit0 >> 4 & 0x0F) | (rawSlit1 >> 4 & 0x0F) << 4);
                            }
                        }
                    }
                }
            } else {
                // Upper half: consByte base = 16384 + (x*16 + z) * 64 + (baseY - 128) / 2
                for (int x = 0; x < 16; x++) {
                    final boolean jLow = (x & 1) == 0;
                    for (int z = 0; z < 16; z++) {
                        final int cBase = 16384 + (x * 16 + z) * 64 + ((baseY - 128) >> 1);
                        for (int yp = 0; yp < 8; yp++) {
                            final int y0 = yp << 1;
                            final int jb0 = (y0 * 256 + z * 16 + x) >> 1;
                            final int jb1 = jb0 + 128;

                            final byte rawMeta0 = decompressed[jMeta + jb0];
                            final byte rawMeta1 = decompressed[jMeta + jb1];
                            out[META_OFF + cBase + yp] = jLow
                                    ? (byte) ((rawMeta0 & 0x0F) | (rawMeta1 & 0x0F) << 4)
                                    : (byte) ((rawMeta0 >> 4 & 0x0F) | (rawMeta1 >> 4 & 0x0F) << 4);

                            final byte rawBlit0 = decompressed[jBlit + jb0];
                            final byte rawBlit1 = decompressed[jBlit + jb1];
                            out[BLIGHT_OFF + cBase + yp] = jLow
                                    ? (byte) ((rawBlit0 & 0x0F) | (rawBlit1 & 0x0F) << 4)
                                    : (byte) ((rawBlit0 >> 4 & 0x0F) | (rawBlit1 >> 4 & 0x0F) << 4);

                            if (jSlit >= 0) {
                                final byte rawSlit0 = decompressed[jSlit + jb0];
                                final byte rawSlit1 = decompressed[jSlit + jb1];
                                out[SLIGHT_OFF + cBase + yp] = jLow
                                        ? (byte) ((rawSlit0 & 0x0F) | (rawSlit1 & 0x0F) << 4)
                                        : (byte) ((rawSlit0 >> 4 & 0x0F) | (rawSlit1 >> 4 & 0x0F) << 4);
                            }
                        }
                    }
                }
            }

            sectionIdx++;
        }

        // biomes
        if (groundUp) {
            final int addCount = Integer.bitCount(addBitMask);
            final int biomeStart = sectionCount * 4096
                    + sectionCount * 2048                          // metadata
                    + sectionCount * 2048                          // blockLight
                    + (skyLightSent ? sectionCount * 2048 : 0)     // skyLight (absent in Nether/End)
                    + addCount * 2048;                             // add data
            if (biomeStart + 256 <= decompressed.length) {
                System.arraycopy(decompressed, biomeStart, out, BIOME_OFF, 256);
            }
        }

        final byte[] rle = compressLZXRLE(out);
        final byte[] compressed = ZlibUtil.compress(rle);

        LceBlockRegionUpdatePacket pkt = new LceBlockRegionUpdatePacket();
        pkt.setChunkFlags((byte) (groundUp ? 0x01 : 0x00));
        pkt.setX(chunkX << 4);
        pkt.setY((short) 0);
        pkt.setZ(chunkZ << 4);
        pkt.setXs((byte) 15);    // 16 - 1
        pkt.setYs((byte) 255);   // 256 - 1 (stored as unsigned)
        pkt.setZs((byte) 15);    // 16 - 1
        pkt.setSizeAndLevel(compressed.length & 0x3FFFFFFF); // levelIdx = 0
        pkt.setData(compressed);
        return pkt;
    }

    /**
     * RLE encoder matching C++ {@code CompressLZXRLE} in compression.cpp.
     *
     * <p>Encoding rules:
     * <ul>
     *   <li>Run of 1-3 bytes of value v (v ≠ 0xFF): emit the bytes literally.</li>
     *   <li>Run of 1-3 bytes of value 0xFF, OR any run ≥ 4: emit {0xFF, count-1, v}.</li>
     * </ul>
     */
    static byte[] compressLZXRLE(byte[] data) {
        if (data == null || data.length == 0) {
            return new byte[0];
        }

        // Worst case: every byte is 0xFF (single byte runs) → 2 output bytes each.
        // Pre-allocating avoids all resizing and eliminates ByteArrayOutputStream overhead.
        final byte[] out = new byte[data.length * 2];
        int outPos = 0;
        int i = 0;
        final int len = data.length;

        while (i < len) {
            final byte val = data[i];
            final int start = i;

            int end = i + 1;
            final int limit = Math.min(i + 256, len);
            while (end < limit && data[end] == val) {
                end++;
            }
            final int runLen = end - start;
            i = end;

            if (runLen <= 3) {
                if ((val & 0xFF) == 0xFF) {
                    out[outPos++] = (byte) 0xFF;
                    out[outPos++] = (byte) (runLen - 1);
                } else {
                    // Unrolled literal emit — avoids loop overhead for the common short-run case
                    out[outPos++] = val;
                    if (runLen > 1) out[outPos++] = val;
                    if (runLen > 2) out[outPos++] = val;
                }
            } else {
                out[outPos++] = (byte) 0xFF;
                out[outPos++] = (byte) (runLen - 1);
                out[outPos++] = val;
            }
        }

        return Arrays.copyOf(out, outPos);
    }
}