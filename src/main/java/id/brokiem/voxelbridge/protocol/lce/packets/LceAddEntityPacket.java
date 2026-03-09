package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceAddEntityPacket implements Packet {
    private short id;
    private byte type;
    private int x;
    private int y;
    private int z;
    private byte yRot;
    private byte xRot;
    private int data;
    private short xa;
    private short ya;
    private short za;

    @Override
    public int getId() {
        return 0x17;
    }

    @Override
    public void read(ByteBuf buf) {
        this.id = buf.readShort();
        this.type = buf.readByte();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.yRot = buf.readByte();
        this.xRot = buf.readByte();
        this.data = buf.readInt();
        if (this.data > -1) {
            this.xa = buf.readShort();
            this.ya = buf.readShort();
            this.za = buf.readShort();
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(id);
        buf.writeByte(type);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(yRot);
        buf.writeByte(xRot);
        buf.writeInt(data);
        if (this.data > -1) {
            buf.writeShort(xa);
            buf.writeShort(ya);
            buf.writeShort(za);
        }
    }
}
