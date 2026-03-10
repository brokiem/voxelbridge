package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceContainerAckPacket implements Packet {
    private byte containerId;
    private short action;
    private boolean accepted;

    @Override
    public int getId() {
        return 0x6A;
    }

    @Override
    public void read(ByteBuf buf) {
        this.containerId = buf.readByte();
        this.action = buf.readShort();
        this.accepted = buf.readBoolean();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(containerId);
        buf.writeShort(action);
        buf.writeBoolean(accepted);
    }
}
