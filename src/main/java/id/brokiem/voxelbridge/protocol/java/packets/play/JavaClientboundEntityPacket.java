package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.*;

@Getter
@Setter
public class JavaClientboundEntityPacket implements Packet {
    private int entityId;

    @Override
    public int getId() {
        return 0x14;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
    }
}
