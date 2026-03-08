package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import id.brokiem.voxelbridge.protocol.types.EntityMetadata;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceAddPlayerPacket implements Packet {
    private int entityId;
    private String name;
    private int x;
    private int y;
    private int z;
    private byte yRot;
    private byte xRot;
    private byte yHeadRot;
    private short carriedItem;
    private long xuid;
    private long onlineXuid;
    private byte playerIndex;
    private int skinId;
    private int capeId;
    private int uiGamePrivileges;
    private EntityMetadata entityData;

    @Override
    public int getId() {
        return 0x14;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.name = ProtocolUtils.readStringLce(buf);
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.yRot = buf.readByte();
        this.xRot = buf.readByte();
        this.yHeadRot = buf.readByte();
        this.carriedItem = buf.readShort();
        this.xuid = buf.readLong();
        this.onlineXuid = buf.readLong();
        this.playerIndex = buf.readByte();
        this.skinId = buf.readInt(); // read as int, stored as unsigned (DWORD)
        this.capeId = buf.readInt();
        this.uiGamePrivileges = buf.readInt();
        this.entityData = EntityMetadata.read(buf, EntityMetadata.StringEncoding.LCE);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        ProtocolUtils.writeStringLce(buf, name);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(yRot);
        buf.writeByte(xRot);
        buf.writeByte(yHeadRot);
        buf.writeShort(carriedItem);
        buf.writeLong(xuid);
        buf.writeLong(onlineXuid);
        buf.writeByte(playerIndex);
        buf.writeInt(skinId);
        buf.writeInt(capeId);
        buf.writeInt(uiGamePrivileges);
        entityData.write(buf, EntityMetadata.StringEncoding.LCE);
    }
}