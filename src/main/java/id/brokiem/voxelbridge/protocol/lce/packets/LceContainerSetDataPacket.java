package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceContainerSetDataPacket implements Packet {
    private byte containerId;
    private short property;
    private short value;

    @Override
    public int getId() {
        return 0x69;
    }

    @Override
    public void read(ByteBuf buf) {
        this.containerId = buf.readByte();
        this.property = buf.readShort();
        this.value = buf.readShort();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(containerId);
        buf.writeShort(property);
        buf.writeShort(value);
    }
}
