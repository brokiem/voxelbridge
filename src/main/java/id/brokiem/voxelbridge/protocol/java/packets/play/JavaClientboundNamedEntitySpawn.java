package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import id.brokiem.voxelbridge.protocol.types.EntityMetadata;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundNamedEntitySpawn implements Packet {
    private int entityId;
    private String uuid;
    private String name;
    private int x;
    private int y;
    private int z;
    private byte yaw, pitch;
    private short currentItem;
    private EntityMetadata metadata;

    @Override
    public int getId() {
        return 0x0c;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = ProtocolUtils.readVarInt(buf);
        this.uuid = ProtocolUtils.readString(buf);
        this.name = ProtocolUtils.readString(buf);
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.yaw = buf.readByte();
        this.pitch = buf.readByte();
        this.currentItem = buf.readShort();
        this.metadata = EntityMetadata.read(buf, EntityMetadata.StringEncoding.JAVA);
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeVarInt(buf, this.entityId);
        ProtocolUtils.writeString(buf, this.uuid);
        ProtocolUtils.writeString(buf, this.name);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeByte(this.yaw);
        buf.writeByte(this.pitch);
        buf.writeShort(this.currentItem);
        this.metadata.write(buf, EntityMetadata.StringEncoding.JAVA);
    }
}
