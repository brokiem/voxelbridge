package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceMoveEntityRotationPacket implements Packet {
    private int entityId;
    private byte yaw;
    private byte pitch;

    @Override
    public int getId() {
        return 0x20;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readShort() & 0xFFFF;
        this.yaw = buf.readByte();
        this.pitch = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(entityId);
        buf.writeByte(yaw);
        buf.writeByte(pitch);
    }
}
