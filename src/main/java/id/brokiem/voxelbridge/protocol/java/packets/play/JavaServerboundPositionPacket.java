package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundPositionPacket implements Packet {
    private double x;
    private double stance;
    private double y;
    private double z;
    private boolean onGround;

    @Override
    public int getId() {
        return 0x04;
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readDouble();
        this.stance = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.onGround = buf.readBoolean();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(stance);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeBoolean(onGround);
    }
}
