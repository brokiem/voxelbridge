package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundCollectItemPacket implements Packet {
    private int collectedEntityId;
    private int collectorEntityId;

    @Override
    public int getId() {
        return 0x0D;
    }

    @Override
    public void read(ByteBuf buf) {
        this.collectedEntityId = buf.readInt();
        this.collectorEntityId = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(collectedEntityId);
        buf.writeInt(collectorEntityId);
    }
}
