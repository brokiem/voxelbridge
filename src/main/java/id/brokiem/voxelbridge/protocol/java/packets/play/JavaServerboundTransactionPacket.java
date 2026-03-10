package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundTransactionPacket implements Packet {
    private byte windowId;
    private short action;
    private boolean accepted;

    @Override
    public int getId() {
        return 0x0F;
    }

    @Override
    public void read(ByteBuf buf) {
        this.windowId = buf.readByte();
        this.action = buf.readShort();
        this.accepted = buf.readBoolean();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(windowId);
        buf.writeShort(action);
        buf.writeBoolean(accepted);
    }
}
