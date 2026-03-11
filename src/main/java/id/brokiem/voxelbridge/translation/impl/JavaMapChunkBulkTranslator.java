package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundMapChunkBulkPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceBlockRegionUpdatePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceChunkVisibilityAreaPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceChunkVisibilityPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import id.brokiem.voxelbridge.util.compression.ZlibUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JavaMapChunkBulkTranslator implements ClientboundTranslator<JavaClientboundMapChunkBulkPacket> {

    @Override
    public TranslationResult translate(JavaClientboundMapChunkBulkPacket input, Session session) {
        try {
            List<Packet> outputPackets = new ArrayList<>();

            byte[] decompressedData = ZlibUtil.decompress(input.getCompressedChunkData());
            int chunkColumnCount = input.getChunkColumnCount();

            int[] chunkX = input.getChunkX();
            int[] chunkZ = input.getChunkZ();

            // Chunk visibility area bounding box
            int minX = chunkX[0], maxX = chunkX[0], minZ = chunkZ[0], maxZ = chunkZ[0];
            for (int i = 1; i < chunkColumnCount; i++) {
                minX = Math.min(minX, chunkX[i]);
                maxX = Math.max(maxX, chunkX[i]);
                minZ = Math.min(minZ, chunkZ[i]);
                maxZ = Math.max(maxZ, chunkZ[i]);
            }
            LceChunkVisibilityAreaPacket visibilityAreaPacket = new LceChunkVisibilityAreaPacket();
            visibilityAreaPacket.setMinX(minX);
            visibilityAreaPacket.setMaxX(maxX);
            visibilityAreaPacket.setMinZ(minZ);
            visibilityAreaPacket.setMaxZ(maxZ);
            outputPackets.add(visibilityAreaPacket);

            // Per-chunk visibility and data
            int dataOffset = 0;
            for (int i = 0; i < chunkColumnCount; i++) {
                LceChunkVisibilityPacket visibilityPacket = new LceChunkVisibilityPacket();
                visibilityPacket.setX(chunkX[i]);
                visibilityPacket.setZ(chunkZ[i]);
                visibilityPacket.setVisible(true);
                outputPackets.add(visibilityPacket);

                int bitMap = input.getBitMap()[i];
                int addBitMap = input.getAddBitMap()[i];
                int chunkDataSize = calculateChunkDataSize(bitMap, addBitMap, input.isSkyLightSent(), true);

                if (dataOffset + chunkDataSize > decompressedData.length) {
                    log.warn("Data underflow for chunk {},{} (need {}, have {})", chunkX[i], chunkZ[i], chunkDataSize, decompressedData.length - dataOffset);
                    break;
                }

                byte[] chunkSlice = new byte[chunkDataSize];
                System.arraycopy(decompressedData, dataOffset, chunkSlice, 0, chunkDataSize);
                dataOffset += chunkDataSize;

                LceBlockRegionUpdatePacket chunkDataPacket = JavaMapChunkTranslator.buildFullConsoleChunk(chunkX[i], chunkZ[i], bitMap, addBitMap, chunkSlice, input.isSkyLightSent());
                outputPackets.add(chunkDataPacket);
            }

            return TranslationResult.toClient(outputPackets);
        } catch (Exception e) {
            log.error("Failed to translate Java MapChunkBulk packet at chunk ({}, {})", input.getChunkX()[0], input.getChunkZ()[0], e);
            return TranslationResult.empty();
        }
    }

    static int calculateChunkDataSize(int bitMap, int addBitMap, boolean skyLight, boolean groundUp) {
        int sectionCount = Integer.bitCount(bitMap);
        int addCount = Integer.bitCount(addBitMap);
        int size = sectionCount * (4096 + 2048 + 2048); // blocks + metadata + blockLight
        if (skyLight) size += sectionCount * 2048;
        size += addCount * 2048;
        if (groundUp) size += 256;
        return size;
    }
}
