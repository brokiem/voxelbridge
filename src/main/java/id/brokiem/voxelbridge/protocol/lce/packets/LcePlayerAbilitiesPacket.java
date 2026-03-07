package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LcePlayerAbilitiesPacket implements Packet {
    /**
     * Flags: invulnerable(0x1), flying(0x2), canFly(0x4), instabuild(0x8)
     */
    private byte bitfield;
    private float flyingSpeed;
    private float walkingSpeed;

    @Override
    public int getId() {
        return 0xCA; // 202
    }

    @Override
    public void read(ByteBuf buf) {
        this.bitfield = buf.readByte();
        this.flyingSpeed = buf.readFloat();
        this.walkingSpeed = buf.readFloat();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(bitfield);
        buf.writeFloat(flyingSpeed);
        buf.writeFloat(walkingSpeed);
    }
}
