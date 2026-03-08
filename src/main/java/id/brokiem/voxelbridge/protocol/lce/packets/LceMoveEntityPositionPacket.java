package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceMoveEntityPositionPacket implements Packet {
    private int entityId;
    private byte x;
    private byte y;
    private byte z;

    @Override
    public int getId() {
        return 0x1F;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readShort() & 0xFFFF;
        this.x = buf.readByte();
        this.y = buf.readByte();
        this.z = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(entityId);
        buf.writeByte(x);
        buf.writeByte(y);
        buf.writeByte(z);
    }
}
