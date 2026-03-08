package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundChatPacket implements Packet {
    private String message = "";

    @Override
    public int getId() {
        return 0x02;
    }

    @Override
    public void read(ByteBuf buf) {
        this.message = ProtocolUtils.readString(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeString(buf, message);
    }
}
