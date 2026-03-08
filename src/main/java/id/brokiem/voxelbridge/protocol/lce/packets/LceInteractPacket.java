package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceInteractPacket implements Packet {
    public static final int INTERACT = 0;
    public static final int ATTACK = 1;

    private int source;
    private int target;
    private int action;

    @Override
    public int getId() {
        return 0x07;
    }

    @Override
    public void read(ByteBuf buf) {
        this.source = buf.readInt();
        this.target = buf.readInt();
        this.action = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(source);
        buf.writeInt(target);
        buf.writeByte(action);
    }
}
