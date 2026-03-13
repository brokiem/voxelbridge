package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceRemoveMobEffectPacket implements Packet {

    private int entityId;
    private byte effectId;

    @Override
    public int getId() {
        return 0x2A;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.effectId = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(effectId);
    }
}
