package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceChunkVisibilityPacket implements Packet {
    private int x;
    private int z;
    private boolean visible;

    @Override
    public int getId() {
        return 0x32;
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readInt();
        this.z = buf.readInt();
        this.visible = buf.readBoolean();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(z);
        buf.writeBoolean(visible);
    }
}
