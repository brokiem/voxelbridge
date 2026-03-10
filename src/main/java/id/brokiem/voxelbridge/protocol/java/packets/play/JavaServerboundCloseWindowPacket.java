package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundCloseWindowPacket implements Packet {
    private byte windowId;

    @Override
    public int getId() {
        return 0x0D;
    }

    @Override
    public void read(ByteBuf buf) {
        this.windowId = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(windowId);
    }
}
