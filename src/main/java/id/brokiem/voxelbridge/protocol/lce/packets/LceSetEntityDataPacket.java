package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.types.EntityMetadata;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceSetEntityDataPacket implements Packet {
    private int entityId;
    private EntityMetadata metadata;

    @Override
    public int getId() {
        return 0x28;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.metadata = EntityMetadata.read(buf, EntityMetadata.StringEncoding.LCE);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        metadata.write(buf, EntityMetadata.StringEncoding.LCE);
    }
}
