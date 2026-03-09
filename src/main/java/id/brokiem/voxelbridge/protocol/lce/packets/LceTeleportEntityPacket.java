package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceTeleportEntityPacket implements Packet {
    private short id;
    private int x;
    private int y;
    private int z;
    private byte yRot;
    private byte xRot;

    @Override
    public int getId() {
        return 0x22;
    }

    @Override
    public void read(ByteBuf buf) {
        this.id = buf.readShort();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.yRot = buf.readByte();
        this.xRot = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(id);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(yRot);
        buf.writeByte(xRot);
    }
}

