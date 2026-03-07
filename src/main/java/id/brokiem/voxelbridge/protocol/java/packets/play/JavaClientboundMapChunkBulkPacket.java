package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_map_chunk_bulk
 * ID: 0x26
 * Direction: Clientbound
 * State: Play
 * <p>
 * Contains zlib-compressed block, metadata, lighting, and biome data for multiple chunk columns.
 * The chunk columns are sent in the same order as the chunkX, chunkZ, bitMap, and addBitMap arrays.
 */
@Getter
@Setter
public class JavaClientboundMapChunkBulkPacket implements Packet {

    private short chunkColumnCount;
    private int dataLength;
    private boolean skyLightSent;
    private byte[] compressedChunkData;

    private int[] chunkX;
    private int[] chunkZ;
    private int[] bitMap;
    private int[] addBitMap;

    @Override
    public int getId() {
        return 0x26;
    }

    @Override
    public void read(ByteBuf buf) {
        this.chunkColumnCount = buf.readShort();
        this.dataLength = buf.readInt();
        this.skyLightSent = buf.readBoolean();
        this.compressedChunkData = new byte[dataLength];
        buf.readBytes(this.compressedChunkData);

        this.chunkX = new int[chunkColumnCount];
        this.chunkZ = new int[chunkColumnCount];
        this.bitMap = new int[chunkColumnCount];
        this.addBitMap = new int[chunkColumnCount];

        for (int i = 0; i < chunkColumnCount; i++) {
            chunkX[i] = buf.readInt();
            chunkZ[i] = buf.readInt();
            bitMap[i] = buf.readUnsignedShort();
            addBitMap[i] = buf.readUnsignedShort();
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(chunkColumnCount);
        buf.writeInt(dataLength);
        buf.writeBoolean(skyLightSent);
        buf.writeBytes(compressedChunkData);

        for (int i = 0; i < chunkColumnCount; i++) {
            buf.writeInt(chunkX[i]);
            buf.writeInt(chunkZ[i]);
            buf.writeShort(bitMap[i]);
            buf.writeShort(addBitMap[i]);
        }
    }
}
