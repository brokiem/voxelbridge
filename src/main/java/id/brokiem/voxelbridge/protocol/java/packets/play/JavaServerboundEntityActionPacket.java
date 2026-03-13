package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundEntityActionPacket implements Packet {

    private int entityId;
    private byte actionId;
    private int jumpBoost;

    @Override
    public int getId() {
        return 0x0B;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.actionId = buf.readByte();
        this.jumpBoost = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(actionId);
        buf.writeInt(jumpBoost);
    }
}
