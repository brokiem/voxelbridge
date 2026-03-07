package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_map_chunk
 * ID: 0x21
 * Direction: Clientbound
 * State: Play
 * <p>
 * Contains zlib-compressed block, metadata, lighting, and biome data for a
 * single chunk column.
 */
@Getter
@Setter
public class JavaClientboundMapChunkPacket implements Packet {

    private int x;
    private int z;
    private boolean groundUp;
    private int bitMap;
    private int addBitMap;
    private int compressedSize;
    private byte[] compressedData;

    @Override
    public int getId() {
        return 0x21;
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readInt();
        this.z = buf.readInt();
        this.groundUp = buf.readBoolean();
        this.bitMap = buf.readUnsignedShort();
        this.addBitMap = buf.readUnsignedShort();
        this.compressedSize = buf.readInt();
        this.compressedData = new byte[compressedSize];
        buf.readBytes(this.compressedData);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(z);
        buf.writeBoolean(groundUp);
        buf.writeShort(bitMap);
        buf.writeShort(addBitMap);
        buf.writeInt(compressedSize);
        buf.writeBytes(compressedData);
    }
}
