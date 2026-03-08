package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.*;

@Getter
@Setter
public class JavaClientboundEntityLookPacket implements Packet {
    private int entityId;
    private byte yaw;
    private byte pitch;

    @Override
    public int getId() {
        return 0x16;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(yaw);
        buf.writeByte(pitch);
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.yaw = buf.readByte();
        this.pitch = buf.readByte();
    }
}
