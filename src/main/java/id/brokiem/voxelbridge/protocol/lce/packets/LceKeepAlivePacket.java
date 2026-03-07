package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceKeepAlivePacket implements Packet {
    private int keepAliveId;

    @Override
    public int getId() {
        return 0x00;
    }

    @Override
    public void read(ByteBuf buf) {
        this.keepAliveId = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(keepAliveId);
    }
}
