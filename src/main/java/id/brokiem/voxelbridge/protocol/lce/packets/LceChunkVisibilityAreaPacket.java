package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceChunkVisibilityAreaPacket implements Packet {
    private int minX;
    private int maxX;
    private int minZ;
    private int maxZ;

    @Override
    public int getId() {
        return 0x9B;
    }

    @Override
    public void read(ByteBuf buf) {
        this.minX = buf.readInt();
        this.maxX = buf.readInt();
        this.minZ = buf.readInt();
        this.maxZ = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(minX);
        buf.writeInt(maxX);
        buf.writeInt(minZ);
        buf.writeInt(maxZ);
    }
}
