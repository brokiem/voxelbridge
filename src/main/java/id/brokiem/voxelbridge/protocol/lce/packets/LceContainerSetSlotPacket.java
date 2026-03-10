package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import id.brokiem.voxelbridge.protocol.types.SlotData;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceContainerSetSlotPacket implements Packet {
    private byte containerId;
    private short slot;
    private SlotData item = SlotData.empty();

    @Override
    public int getId() {
        return 0x67;
    }

    @Override
    public void read(ByteBuf buf) {
        this.containerId = buf.readByte();
        this.slot = buf.readShort();
        this.item = ProtocolUtils.readLceItem(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(containerId);
        buf.writeShort(slot);
        ProtocolUtils.writeLceItem(buf, item);
    }
}
