package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceSetHealthPacket implements Packet {
    private float health;
    private short food;
    private float saturation;
    private byte damageSource;

    @Override
    public int getId() {
        return 0x08;
    }

    @Override
    public void read(ByteBuf buf) {
        this.health = buf.readFloat();
        this.food = buf.readShort();
        this.saturation = buf.readFloat();
        this.damageSource = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeFloat(health);
        buf.writeShort(food);
        buf.writeFloat(saturation);
        buf.writeByte(damageSource);
    }
}
