package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.types.EntityMetadata;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceAddMobPacket implements Packet {
    private short entityId;
    private byte type;
    private int x;
    private int y;
    private int z;
    private byte yRot;
    private byte xRot;
    private byte yHeadRot;
    private short xd;
    private short yd;
    private short zd;
    // Entity metadata (flags, health, etc.)
    private EntityMetadata entityData;

    @Override
    public int getId() {
        return 0x18;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readShort();
        this.type = buf.readByte();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.yRot = buf.readByte();
        this.xRot = buf.readByte();
        this.yHeadRot = buf.readByte();
        this.xd = buf.readShort();
        this.yd = buf.readShort();
        this.zd = buf.readShort();
        this.entityData = EntityMetadata.read(buf, EntityMetadata.StringEncoding.LCE);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(entityId);
        buf.writeByte(type & 0xFF);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(yRot);
        buf.writeByte(xRot);
        buf.writeByte(yHeadRot);
        buf.writeShort(xd);
        buf.writeShort(yd);
        buf.writeShort(zd);
        entityData.write(buf, EntityMetadata.StringEncoding.LCE);
    }
}