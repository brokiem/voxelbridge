package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_spawn_position
 * ID: 0x05
 * Direction: Clientbound
 * State: Play
 * <p>
 * Sets the world's spawn or respawn coordinates that the client should treat
 * as the default player spawn position.
 */
@Getter
@Setter
public class JavaClientboundSpawnPositionPacket implements Packet {
    private int x;
    private int y;
    private int z;

    @Override
    public int getId() {
        return 0x05;
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }
}

