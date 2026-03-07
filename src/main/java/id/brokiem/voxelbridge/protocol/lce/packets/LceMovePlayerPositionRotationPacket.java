package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * LCE MovePlayerPacket PosRot variant (ID 13). Full position + rotation.
 * The first MovePlayerPacket received sets started=true on the LCE client.
 */
@Getter
@Setter
public class LceMovePlayerPositionRotationPacket implements Packet {
    private double x;
    private double y;
    private double yView;
    private double z;
    private float yRot;
    private float xRot;
    /**
     * Bit 0 = onGround, bit 1 = isFlying
     */
    private byte flags;

    @Override
    public int getId() {
        return 0x0D; // 13 - PosRot variant
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.yView = buf.readDouble();
        this.z = buf.readDouble();
        this.yRot = buf.readFloat();
        this.xRot = buf.readFloat();
        this.flags = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(yView);
        buf.writeDouble(z);
        buf.writeFloat(yRot);
        buf.writeFloat(xRot);
        buf.writeByte(flags);
    }
}
