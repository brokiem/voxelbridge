package id.brokiem.voxelbridge.protocol.lce.packets;

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
public class LceContainerSetContentPacket implements Packet {
    private byte containerId;
    private List<SlotData> items = new ArrayList<>();

    @Override
    public int getId() {
        return 0x68;
    }

    @Override
    public void read(ByteBuf buf) {
        this.containerId = buf.readByte();
        int count = buf.readShort();
        this.items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            items.add(ProtocolUtils.readLceItem(buf));
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(containerId);
        buf.writeShort(items.size());
        for (SlotData item : items) {
            ProtocolUtils.writeLceItem(buf, item);
        }
    }
}
