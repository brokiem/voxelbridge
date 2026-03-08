package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import id.brokiem.voxelbridge.protocol.types.EntityMetadata;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JavaClientboundSpawnEntityLivingPacket implements Packet {
    private int entityId;
    private int type;
    private int x;
    private int y;
    private int z;
    private byte yaw;
    private byte pitch;
    private byte headPitch;
    private short velocityX;
    private short velocityY;
    private short velocityZ;
    private EntityMetadata metadata;

    @Override
    public int getId() {
        return 0x0F;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = ProtocolUtils.readVarInt(buf);
        this.type = buf.readUnsignedByte();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.yaw = buf.readByte();
        this.pitch = buf.readByte();
        this.headPitch = buf.readByte();
        this.velocityX = buf.readShort();
        this.velocityY = buf.readShort();
        this.velocityZ = buf.readShort();
        this.metadata = EntityMetadata.read(buf, EntityMetadata.StringEncoding.JAVA);
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeVarInt(buf, entityId);
        buf.writeByte(type & 0xFF);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(yaw);
        buf.writeByte(pitch);
        buf.writeByte(headPitch);
        buf.writeShort(velocityX);
        buf.writeShort(velocityY);
        buf.writeShort(velocityZ);
        metadata.write(buf, EntityMetadata.StringEncoding.JAVA);
    }
}