package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_update_health
 * ID: 0x06
 * Direction: Clientbound
 * State: Play
 * <p>
 * Updates the player's health, food level, and food saturation values during
 * normal gameplay.
 */
@Getter
@Setter
public class JavaClientboundUpdateHealthPacket implements Packet {
    private float health;
    private short food;
    private float saturation;

    @Override
    public int getId() {
        return 0x06;
    }

    @Override
    public void read(ByteBuf buf) {
        this.health = buf.readFloat();
        this.food = buf.readShort();
        this.saturation = buf.readFloat();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeFloat(health);
        buf.writeShort(food);
        buf.writeFloat(saturation);
    }
}

