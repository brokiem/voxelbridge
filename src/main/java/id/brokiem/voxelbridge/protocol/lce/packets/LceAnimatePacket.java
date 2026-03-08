package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceAnimatePacket implements Packet {
    public static final int SWING = 1;
    public static final int HURT = 2;
    public static final int WAKE_UP = 3;
    public static final int RESPAWN = 4;
    public static final int EAT = 5;
    public static final int CRITICAL_HIT = 6;
    public static final int MAGIC_CRITICAL_HIT = 7;

    private int id;
    private int action;

    @Override
    public int getId() {
        return 0x12;
    }

    @Override
    public void read(ByteBuf buf) {
        this.id = buf.readInt();
        this.action = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(id);
        buf.writeByte(action);
    }
}

