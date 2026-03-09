package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundSpawnEntityPacket implements Packet {
    private int entityId;
    private int type;
    private int x;
    private int y;
    private int z;
    private byte pitch;
    private byte yaw;
    private int data;
    private short velocityX;
    private short velocityY;
    private short velocityZ;

    @Override
    public int getId() {
        return 0x0E;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = ProtocolUtils.readVarInt(buf);
        this.type = buf.readByte();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.pitch = buf.readByte();
        this.yaw = buf.readByte();
        this.data = buf.readInt();
        if (this.data > 0) {
            this.velocityX = buf.readShort();
            this.velocityY = buf.readShort();
            this.velocityZ = buf.readShort();
        }
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeVarInt(buf, entityId);
        buf.writeByte(type);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(pitch);
        buf.writeByte(yaw);
        buf.writeInt(data);
        if (data > 0) {
            buf.writeShort(velocityX);
            buf.writeShort(velocityY);
            buf.writeShort(velocityZ);
        }
    }
}
