package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundEntityVelocityPacket implements Packet {

    private int entityId;
    private short velocityX;
    private short velocityY;
    private short velocityZ;

    @Override
    public int getId() {
        return 0x12;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.velocityX = buf.readShort();
        this.velocityY = buf.readShort();
        this.velocityZ = buf.readShort();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeShort(velocityX);
        buf.writeShort(velocityY);
        buf.writeShort(velocityZ);
    }
}
