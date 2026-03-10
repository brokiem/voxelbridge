package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundOpenWindowPacket implements Packet {
    private short windowId;
    private short inventoryType;
    private String windowTitle;
    private short slotCount;
    private boolean useProvidedTitle;
    private Integer entityId; // only when inventoryType == 11 (horse)

    @Override
    public int getId() {
        return 0x2D;
    }

    @Override
    public void read(ByteBuf buf) {
        this.windowId = buf.readUnsignedByte();
        this.inventoryType = buf.readUnsignedByte();
        this.windowTitle = ProtocolUtils.readString(buf);
        this.slotCount = buf.readUnsignedByte();
        this.useProvidedTitle = buf.readBoolean();
        if (inventoryType == 11) {
            this.entityId = buf.readInt();
        } else {
            this.entityId = null;
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(windowId);
        buf.writeByte(inventoryType);
        ProtocolUtils.writeString(buf, windowTitle != null ? windowTitle : "");
        buf.writeByte(slotCount);
        buf.writeBoolean(useProvidedTitle);
        if (inventoryType == 11 && entityId != null) {
            buf.writeInt(entityId);
        }
    }
}
