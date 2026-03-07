package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_abilities
 * ID: 0x39
 * Direction: Clientbound
 * State: Play
 * <p>
 * Updates the player's ability flags and movement speeds, including flying,
 * invulnerability, creative permissions, and walk speed.
 */
@Getter
@Setter
public class JavaClientboundAbilitiesPacket implements Packet {
    private byte flags;
    private float flyingSpeed;
    private float walkingSpeed;

    @Override
    public int getId() {
        return 0x39;
    }

    @Override
    public void read(ByteBuf buf) {
        this.flags = buf.readByte();
        this.flyingSpeed = buf.readFloat();
        this.walkingSpeed = buf.readFloat();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(flags);
        buf.writeFloat(flyingSpeed);
        buf.writeFloat(walkingSpeed);
    }
}

