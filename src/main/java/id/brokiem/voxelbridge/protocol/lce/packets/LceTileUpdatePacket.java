package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceTileUpdatePacket implements Packet {
    private int x;
    private int y;
    private int z;
    private int block;
    private int data;
    private int levelIdx;

    @Override
    public int getId() {
        return 0x35;
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readUnsignedByte();
        this.z = buf.readInt();
        this.block = buf.readUnsignedShort();
        int dataLevel = buf.readUnsignedByte();
        this.data = dataLevel & 0xF;
        this.levelIdx = (dataLevel >> 4) & 0xF;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeByte(y);
        buf.writeInt(z);
        buf.writeShort(block);
        int dataLevel = ((levelIdx & 0xF) << 4) | (data & 0xF);
        buf.writeByte(dataLevel);
    }
}
