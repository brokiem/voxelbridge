package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceRemoveEntitiesPacket implements Packet {
    private int[] entityIds;

    @Override
    public int getId() {
        return 0x1D;
    }

    @Override
    public void read(ByteBuf buf) {
        int count = buf.readByte();
        this.entityIds = new int[count];
        for (int i = 0; i < count; i++) {
            this.entityIds[i] = buf.readInt();
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(entityIds.length);
        for (int entityId : entityIds) {
            buf.writeInt(entityId);
        }
    }
}
