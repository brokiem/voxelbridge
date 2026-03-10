package id.brokiem.voxelbridge.protocol;

import id.brokiem.voxelbridge.protocol.types.SlotData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class ProtocolUtils {
    private static final int MAX_VARINT_BYTES = 5;
    private static final int MAX_JAVA_STRING_BYTES = 32767 * 4;

    private ProtocolUtils() {
    }

    public static int readVarInt(ByteBuf buf) {
        int value = 0;
        for (int numRead = 0; numRead < MAX_VARINT_BYTES; numRead++) {
            if (!buf.isReadable()) {
                throw new DecoderException("Incomplete VarInt");
            }

            int currentByte = buf.readUnsignedByte();
            value |= (currentByte & 0x7F) << (numRead * 7);
            if ((currentByte & 0x80) == 0) {
                return value;
            }
        }

        throw new DecoderException("VarInt is too big");
    }

    public static void writeVarInt(ByteBuf buf, int value) {
        while (true) {
            if ((value & ~0x7F) == 0) {
                buf.writeByte(value);
                return;
            }

            buf.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
    }

    public static String readString(ByteBuf buf) {
        return readString(buf, MAX_JAVA_STRING_BYTES);
    }

    public static String readString(ByteBuf buf, int maxBytes) {
        int length = readVarInt(buf);
        if (length < 0 || length > maxBytes) {
            throw new DecoderException("Invalid string length: " + length);
        }
        if (buf.readableBytes() < length) {
            throw new DecoderException("Not enough data for string of length " + length);
        }

        String value = buf.toString(buf.readerIndex(), length, StandardCharsets.UTF_8);
        buf.skipBytes(length);
        return value;
    }

    // For LCE strings (often UTF-16, length prefixed by short)
    public static String readStringLce(ByteBuf buf) {
        if (buf.readableBytes() < 2) {
            throw new DecoderException("Not enough data for LCE string length");
        }

        int length = buf.readUnsignedShort();
        int byteLength = Math.multiplyExact(length, 2);
        if (buf.readableBytes() < byteLength) {
            throw new DecoderException("Not enough data for LCE string of length " + length);
        }

        String value = buf.toString(buf.readerIndex(), byteLength, StandardCharsets.UTF_16BE);
        buf.skipBytes(byteLength);
        return value;
    }

    public static void writeStringLce(ByteBuf buf, String value) {
        String safeValue = value != null ? value : "";
        if (safeValue.length() > 0xFFFF) {
            throw new EncoderException("LCE string exceeds unsigned short length limit");
        }

        buf.writeShort(safeValue.length());
        buf.writeBytes(safeValue.getBytes(StandardCharsets.UTF_16BE));
    }

    public static void writeString(ByteBuf buf, String value) {
        String safeValue = value != null ? value : "";
        byte[] bytes = safeValue.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > MAX_JAVA_STRING_BYTES) {
            throw new EncoderException("String exceeds maximum Java protocol length");
        }

        writeVarInt(buf, bytes.length);
        buf.writeBytes(bytes);
    }

    public static SlotData readJavaSlot(ByteBuf buf) {
        short blockId = buf.readShort();
        if (blockId < 0) {
            return SlotData.empty();
        }
        short itemCount = buf.readUnsignedByte();
        short itemDamage = buf.readShort();
        short nbtLen = buf.readShort();

        byte[] nbtData = new byte[0];
        if (nbtLen > 0) {
            ByteBuf nbtSlice = buf.readSlice(nbtLen);

            try (GZIPInputStream gzip = new GZIPInputStream(new ByteBufInputStream(nbtSlice));
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] tmp = new byte[1024];
                int len;
                while ((len = gzip.read(tmp)) != -1) {
                    out.write(tmp, 0, len);
                }
                nbtData = out.toByteArray();
            } catch (IOException e) {
                throw new DecoderException("Failed to decompress Java slot NBT", e);
            }
        }

        return new SlotData(blockId, (byte) itemCount, itemDamage, nbtData);
    }

    public static void writeJavaSlot(ByteBuf buf, SlotData slot) {
        if (slot == null || slot.isEmpty()) {
            buf.writeShort(-1);
            return;
        }
        buf.writeShort(slot.getItemId());
        buf.writeByte(slot.getItemCount());
        buf.writeShort(slot.getItemDamage());

        byte[] nbt = slot.getNbtData() != null ? slot.getNbtData() : new byte[0];
        if (nbt.length == 0) {
            buf.writeShort(-1);
            return;
        }

        // Write compressed NBT into a temp buffer so we know the length first
        ByteBuf tmp = buf.alloc().buffer();
        try {
            try (GZIPOutputStream gzip = new GZIPOutputStream(new ByteBufOutputStream(tmp))) {
                gzip.write(nbt);
            } catch (IOException e) {
                throw new EncoderException("Failed to compress Java slot NBT", e);
            }
            buf.writeShort(tmp.readableBytes());
            buf.writeBytes(tmp);
        } finally {
            tmp.release();
        }
    }

    public static SlotData readLceItem(ByteBuf buf) {
        short itemId = buf.readShort();
        if (itemId < 0) {
            return SlotData.empty();
        }
        byte itemCount = buf.readByte();
        short itemDamage = buf.readShort();
        short nbtLen = buf.readShort();
        byte[] nbtData = (nbtLen <= 0 || nbtLen == 0xFFFF) ? new byte[0] : new byte[nbtLen];
        if (nbtData.length > 0) {
            buf.readBytes(nbtData);
        }
        return new SlotData(itemId, itemCount, itemDamage, nbtData);
    }

    public static void writeLceItem(ByteBuf buf, SlotData slot) {
        if (slot == null || slot.isEmpty()) {
            buf.writeShort(-1);
            return;
        }
        buf.writeShort(slot.getItemId());
        buf.writeByte(slot.getItemCount());
        buf.writeShort(slot.getItemDamage());
        byte[] nbt = slot.getNbtData() != null ? slot.getNbtData() : new byte[0];
        if (nbt.length > 0) {
            buf.writeShort(nbt.length);
            buf.writeBytes(nbt);
        } else {
            buf.writeShort(-1);
        }
    }
}
