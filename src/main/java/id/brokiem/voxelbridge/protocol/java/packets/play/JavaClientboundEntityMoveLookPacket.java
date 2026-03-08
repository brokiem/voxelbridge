package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.*;

@Getter
@Setter
public class JavaClientboundEntityMoveLookPacket implements Packet {
    private int entityId;
    private byte dX;
    private byte dY;
    private byte dZ;
    private byte yaw;
    private byte pitch;

    @Override
    public int getId() {
        return 0x17;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(dX);
        buf.writeByte(dY);
        buf.writeByte(dZ);
        buf.writeByte(yaw);
        buf.writeByte(pitch);
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.dX = buf.readByte();
        this.dY = buf.readByte();
        this.dZ = buf.readByte();
        this.yaw = buf.readByte();
        this.pitch = buf.readByte();
    }
}
