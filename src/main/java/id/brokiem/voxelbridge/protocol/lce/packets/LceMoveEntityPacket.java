package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceMoveEntityPacket implements Packet {
    private int entityId;

    @Override
    public int getId() {
        return 0x1E;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readShort() & 0xFFFF;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(entityId);
    }
}
