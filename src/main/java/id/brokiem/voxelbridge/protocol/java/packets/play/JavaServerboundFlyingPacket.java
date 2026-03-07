package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundFlyingPacket implements Packet {
    private boolean onGround;

    @Override
    public int getId() {
        return 0x03;
    }

    @Override
    public void read(ByteBuf buf) {
        this.onGround = buf.readBoolean();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeBoolean(onGround);
    }
}
