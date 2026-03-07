package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceSetCarriedItemPacket implements Packet {
    private short slot;

    @Override
    public int getId() {
        return 0x10;
    }

    @Override
    public void read(ByteBuf buf) {
        this.slot = buf.readShort();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(slot);
    }
}
