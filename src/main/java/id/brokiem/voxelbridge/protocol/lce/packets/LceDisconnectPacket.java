package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceDisconnectPacket implements Packet {

    /**
     * LCE eDisconnectReason enum values (from DisconnectPacket.h)
     */
    public static final int NONE = 0;
    public static final int QUITTING = 1;
    public static final int CLOSED = 2;
    public static final int LOGIN_TOO_LONG = 3;
    public static final int KICKED = 9;
    public static final int TIME_OUT = 11;
    public static final int SERVER_FULL = 16;
    public static final int OUTDATED_SERVER = 17;
    public static final int OUTDATED_CLIENT = 18;
    public static final int BANNED = 24;

    private int reason = CLOSED;

    @Override
    public int getId() {
        return 0xFF;
    }

    @Override
    public void read(ByteBuf buf) {
        this.reason = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(reason);
    }
}
