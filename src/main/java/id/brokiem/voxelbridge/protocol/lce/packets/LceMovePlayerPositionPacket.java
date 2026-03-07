package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * LCE MovePlayerPacket position-only variant (ID 11 / 0x0B).
 * Wire: double x, y, yView, z, byte flags.
 */
@Getter
@Setter
public class LceMovePlayerPositionPacket implements Packet {
    private double x;
    private double y;
    private double yView;
    private double z;
    /**
     * Bit 0 = onGround, bit 1 = isFlying.
     */
    private byte flags;

    @Override
    public int getId() {
        return 0x0B;
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.yView = buf.readDouble();
        this.z = buf.readDouble();
        this.flags = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(yView);
        buf.writeDouble(z);
        buf.writeByte(flags);
    }
}
