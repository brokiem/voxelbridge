package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundEntityStatusPacket implements Packet {

    private int entityId;
    private byte entityStatus;

    @Override
    public int getId() {
        return 0x1A;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.entityStatus = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(entityStatus);
    }
}
