package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceRotateHeadPacket implements Packet {
    private int entityId;
    private byte headYaw;

    @Override
    public int getId() {
        return 0x23;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.headYaw = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(headYaw);
    }
}
