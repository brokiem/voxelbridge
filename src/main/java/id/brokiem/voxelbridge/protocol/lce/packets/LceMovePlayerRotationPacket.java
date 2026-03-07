package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * LCE MovePlayerPacket rotation-only variant (ID 12 / 0x0C).
 * Wire: float yRot, xRot, byte flags.
 */
@Getter
@Setter
public class LceMovePlayerRotationPacket implements Packet {
    private float yRot;
    private float xRot;
    /**
     * Bit 0 = onGround, bit 1 = isFlying.
     */
    private byte flags;

    @Override
    public int getId() {
        return 0x0C;
    }

    @Override
    public void read(ByteBuf buf) {
        this.yRot = buf.readFloat();
        this.xRot = buf.readFloat();
        this.flags = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeFloat(yRot);
        buf.writeFloat(xRot);
        buf.writeByte(flags);
    }
}
