package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_position_look
 * ID: 0x06
 * Direction: Serverbound
 * State: Play
 * <p>
 * Sends the client's position, stance, view rotation, and grounded state to
 * the server, commonly after a server-issued position update.
 */
@Getter
@Setter
public class JavaServerboundPositionLookPacket implements Packet {
    private double x;
    private double stanceY;
    private double feetY;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;

    @Override
    public int getId() {
        return 0x06;
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readDouble();
        this.stanceY = buf.readDouble();
        this.feetY = buf.readDouble();
        this.z = buf.readDouble();
        this.yaw = buf.readFloat();
        this.pitch = buf.readFloat();
        this.onGround = buf.readBoolean();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(stanceY);
        buf.writeDouble(feetY);
        buf.writeDouble(z);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
        buf.writeBoolean(onGround);
    }
}

