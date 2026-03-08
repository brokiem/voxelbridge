package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceTakeItemEntityPacket implements Packet {
    private int itemId;
    private int playerId;

    @Override
    public int getId() {
        return 0x16;
    }

    @Override
    public void read(ByteBuf buf) {
        this.itemId = buf.readInt();
        this.playerId = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(itemId);
        buf.writeInt(playerId);
    }
}
