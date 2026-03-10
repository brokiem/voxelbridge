package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundCloseWindowPacket implements Packet {
    private short windowId;

    @Override
    public int getId() {
        return 0x2E;
    }

    @Override
    public void read(ByteBuf buf) {
        this.windowId = buf.readUnsignedByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(windowId);
    }
}
