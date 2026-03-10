package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceContainerOpenPacket implements Packet {
    private byte containerId;
    private byte type;
    private String title = "";
    private byte slotCount;
    private boolean customName;
    private int entityId; // when type == 11 (horse inventory)

    @Override
    public int getId() {
        return 0x64;
    }

    @Override
    public void read(ByteBuf buf) {
        this.containerId = buf.readByte();
        this.type = buf.readByte();
        this.slotCount = buf.readByte();
        this.customName = buf.readBoolean();
        if (type == 11) {
            this.entityId = buf.readInt();
        } else {
            this.entityId = 0;
        }
        if (this.customName) {
            this.title = ProtocolUtils.readStringLce(buf);
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(containerId);
        buf.writeByte(type);
        buf.writeByte(slotCount);
        buf.writeBoolean(customName);
        if (type == 11) {
            buf.writeInt(entityId);
        }
        if (customName) {
            ProtocolUtils.writeStringLce(buf, title != null ? title : "");
        }
    }
}
