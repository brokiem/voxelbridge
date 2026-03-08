package id.brokiem.voxelbridge.protocol.types;

import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;

import java.util.Map;
import java.util.TreeMap;

public class EntityMetadata {
    private static final int TYPE_SHIFT = 5;
    private static final int TYPE_MASK = 0xE0;
    private static final int ID_MASK = 0x1F;
    private static final int EOF_MARKER = 0x7F;
    private static final int MAX_STRING_LENGTH = 64;

    public enum DataType {
        BYTE(0), SHORT(1), INT(2), FLOAT(3), STRING(4), ITEM(5);

        public final int id;

        DataType(int id) {
            this.id = id;
        }

        public static DataType fromId(int id) {
            for (DataType t : values()) {
                if (t.id == id) return t;
            }
            throw new DecoderException("Unknown metadata type: " + id);
        }
    }

    public enum StringEncoding {
        LCE,  // UTF-16BE + short length (LCE protocol)
        JAVA  // UTF-8   + short length (Java edition protocol)
    }

    private final Map<Integer, Parameter<?>> values = new TreeMap<>();

    public static EntityMetadata read(ByteBuf buf, StringEncoding encoding) {
        EntityMetadata metadata = new EntityMetadata();

        int header;
        while ((header = buf.readUnsignedByte()) != EOF_MARKER) {
            int typeId = (header & TYPE_MASK) >> TYPE_SHIFT;
            int id = (header & ID_MASK);

            DataType type = DataType.fromId(typeId);
            Object value = readValue(buf, type, encoding);

            metadata.values.put(id, new Parameter<>(type, value));
        }

        return metadata;
    }

    public void write(ByteBuf buf, StringEncoding encoding) {
        for (Map.Entry<Integer, Parameter<?>> entry : values.entrySet()) {
            int id = entry.getKey();
            Parameter<?> param = entry.getValue();

            int header = ((param.type.id << TYPE_SHIFT) | (id & ID_MASK)) & 0xFF;
            buf.writeByte(header);

            writeValue(buf, param, encoding);
        }
        buf.writeByte(EOF_MARKER);
    }

    private static Object readValue(ByteBuf buf, DataType type, StringEncoding encoding) {
        return switch (type) {
            case BYTE -> buf.readByte();
            case SHORT -> buf.readShort();
            case INT -> buf.readInt();
            case FLOAT -> buf.readFloat();
            case STRING -> switch (encoding) {
                case LCE -> ProtocolUtils.readStringLce(buf);
                case JAVA -> ProtocolUtils.readString(buf, MAX_STRING_LENGTH);
            };
            case ITEM -> readItem(buf);
        };
    }

    private static void writeValue(ByteBuf buf, Parameter<?> param, StringEncoding encoding) {
        switch (param.type) {
            case BYTE -> buf.writeByte((Byte) param.value);
            case SHORT -> buf.writeShort((Short) param.value);
            case INT -> buf.writeInt((Integer) param.value);
            case FLOAT -> buf.writeFloat((Float) param.value);
            case STRING -> {
                switch (encoding) {
                    case LCE -> ProtocolUtils.writeStringLce(buf, (String) param.value);
                    case JAVA -> ProtocolUtils.writeString(buf, (String) param.value);
                }
            }
            case ITEM -> writeItem(buf, (Item) param.value);
        }
    }

    private static Item readItem(ByteBuf buf) {
        short id = buf.readShort();
        if (id < 0) return null;

        byte count = buf.readByte();
        short damage = buf.readShort();
        short nbtSize = buf.readShort();
        byte[] nbt = null;

        if (nbtSize >= 0) {
            nbt = new byte[nbtSize];
            buf.readBytes(nbt);
        }

        return new Item(id, count, damage, nbt);
    }

    private static void writeItem(ByteBuf buf, Item item) {
        if (item == null) {
            buf.writeShort(-1);
            return;
        }

        buf.writeShort(item.id);
        buf.writeByte(item.count);
        buf.writeShort(item.damage);

        if (item.nbt == null) {
            buf.writeShort(-1);
        } else {
            buf.writeShort(item.nbt.length);
            buf.writeBytes(item.nbt);
        }
    }

    public record Parameter<T>(DataType type, T value) {
    }

    public record Item(short id, byte count, short damage, byte[] nbt) {
        public boolean hasNbt() {
            return nbt != null;
        }
    }
}