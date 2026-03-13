package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceGameEventPacket implements Packet {

    public static final int NO_RESPAWN_BED_AVAILABLE = 0;
    public static final int START_RAINING = 1;
    public static final int STOP_RAINING = 2;
    public static final int CHANGE_GAME_MODE = 3;
    public static final int WIN_GAME = 4;
    public static final int DEMO_EVENT = 5;
    public static final int SUCCESSFUL_BOW_HIT = 6;

    private int event;
    private int param;

    @Override
    public int getId() {
        return 0x46;
    }

    @Override
    public void read(ByteBuf buf) {
        this.event = buf.readByte();
        this.param = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(event);
        buf.writeByte(param);
    }
}
