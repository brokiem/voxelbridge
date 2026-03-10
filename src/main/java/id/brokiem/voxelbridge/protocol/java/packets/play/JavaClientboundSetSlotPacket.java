package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import id.brokiem.voxelbridge.protocol.types.SlotData;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundSetSlotPacket implements Packet {
    private byte windowId;
    private short slot;
    private SlotData item;

    @Override
    public int getId() {
        return 0x2F;
    }

    @Override
    public void read(ByteBuf buf) {
        this.windowId = buf.readByte();
        this.slot = buf.readShort();
        this.item = ProtocolUtils.readJavaSlot(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(windowId);
        buf.writeShort(slot);
        ProtocolUtils.writeJavaSlot(buf, item);
    }
}
