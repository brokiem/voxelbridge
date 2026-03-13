package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundGameStateChangePacket implements Packet {

    private short reason;
    private float gameMode;

    @Override
    public int getId() {
        return 0x2B;
    }

    @Override
    public void read(ByteBuf buf) {
        this.reason = buf.readUnsignedByte();
        this.gameMode = buf.readFloat();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(reason);
        buf.writeFloat(gameMode);
    }
}
