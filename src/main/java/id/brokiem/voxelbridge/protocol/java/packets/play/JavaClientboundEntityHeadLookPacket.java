package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundEntityHeadLookPacket implements Packet {
    private int entityId;
    private byte headYaw;

    @Override
    public int getId() {
        return 0x19;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.headYaw = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(headYaw);
    }
}
