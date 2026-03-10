package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import id.brokiem.voxelbridge.protocol.types.SlotData;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JavaClientboundWindowItemsPacket implements Packet {
    private short windowId;
    private List<SlotData> items = new ArrayList<>();

    @Override
    public int getId() {
        return 0x30;
    }

    @Override
    public void read(ByteBuf buf) {
        this.windowId = buf.readUnsignedByte();
        int count = buf.readShort();
        this.items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            items.add(ProtocolUtils.readJavaSlot(buf));
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(windowId);
        buf.writeShort(items.size());
        for (SlotData item : items) {
            ProtocolUtils.writeJavaSlot(buf, item);
        }
    }
}
