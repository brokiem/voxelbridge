package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceUpdateMobEffectPacket implements Packet {

    private int entityId;
    private byte effectId;
    private byte amplifier;
    private short duration;

    @Override
    public int getId() {
        return 0x29;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.effectId = buf.readByte();
        this.amplifier = buf.readByte();
        this.duration = buf.readShort();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(effectId);
        buf.writeByte(amplifier);
        buf.writeShort(duration);
    }
}
