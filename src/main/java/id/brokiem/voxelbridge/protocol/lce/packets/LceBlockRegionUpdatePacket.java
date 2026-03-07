package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceBlockRegionUpdatePacket implements Packet {
    private byte chunkFlags;
    private int x;
    private short y;
    private int z;
    private byte xs;
    private byte ys;
    private byte zs;
    private int sizeAndLevel;
    private byte[] data;

    @Override
    public int getId() {
        return 0x33;
    }

    @Override
    public void read(ByteBuf buf) {
        this.chunkFlags = buf.readByte();
        this.x = buf.readInt();
        this.y = buf.readShort();
        this.z = buf.readInt();
        this.xs = buf.readByte();
        this.ys = buf.readByte();
        this.zs = buf.readByte();
        this.sizeAndLevel = buf.readInt();
        int size = sizeAndLevel & 0x3FFFFFFF;
        this.data = new byte[size];
        buf.readBytes(this.data);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(chunkFlags);
        buf.writeInt(x);
        buf.writeShort(y);
        buf.writeInt(z);
        buf.writeByte(xs);
        buf.writeByte(ys);
        buf.writeByte(zs);
        buf.writeInt(sizeAndLevel);
        buf.writeBytes(data);
    }
}
