package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceContainerClosePacket implements Packet {
    private byte containerId;

    @Override
    public int getId() {
        return 0x65;
    }

    @Override
    public void read(ByteBuf buf) {
        this.containerId = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(containerId);
    }
}
