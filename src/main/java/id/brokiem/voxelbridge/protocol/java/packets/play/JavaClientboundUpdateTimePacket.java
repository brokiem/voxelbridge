package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_update_time
 * ID: 0x03
 * Direction: Clientbound
 * State: Play
 * <p>
 * Updates the world's total age and current time of day so the client can keep
 * sky lighting, day-night visuals, and game time synchronized.
 */
@Getter
@Setter
public class JavaClientboundUpdateTimePacket implements Packet {
    private long worldAge;
    private long timeOfDay;

    @Override
    public int getId() {
        return 0x03;
    }

    @Override
    public void read(ByteBuf buf) {
        this.worldAge = buf.readLong();
        this.timeOfDay = buf.readLong();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeLong(worldAge);
        buf.writeLong(timeOfDay);
    }
}

