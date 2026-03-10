package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import id.brokiem.voxelbridge.protocol.types.SlotData;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundSetCreativeSlotPacket implements Packet {
    private short slot;
    private SlotData item = SlotData.empty();

    @Override
    public int getId() {
        return 0x10;
    }

    @Override
    public void read(ByteBuf buf) {
        this.slot = buf.readShort();
        this.item = ProtocolUtils.readJavaSlot(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(slot);
        ProtocolUtils.writeJavaSlot(buf, item);
    }
}
