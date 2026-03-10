package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import id.brokiem.voxelbridge.protocol.types.SlotData;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceContainerClickPacket implements Packet {
    private byte containerId;
    private short slot;
    private byte mouseButton;
    private short action;
    private byte mode;
    private SlotData item = SlotData.empty();
    private short uid;

    @Override
    public int getId() {
        return 0x66;
    }

    @Override
    public void read(ByteBuf buf) {
        this.containerId = buf.readByte();
        this.slot = buf.readShort();
        this.mouseButton = buf.readByte();
        this.action = buf.readShort();
        this.mode = buf.readByte();
        this.item = ProtocolUtils.readLceItem(buf);
        this.uid = buf.readShort();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(containerId);
        buf.writeShort(slot);
        buf.writeByte(mouseButton);
        buf.writeShort(action);
        buf.writeByte(mode);
        ProtocolUtils.writeLceItem(buf, item);
        buf.writeShort(uid);
    }
}
