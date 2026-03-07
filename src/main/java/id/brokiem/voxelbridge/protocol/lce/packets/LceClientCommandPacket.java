package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceClientCommandPacket implements Packet {
    public static final int LOGIN_COMPLETE = 0;
    public static final int PERFORM_RESPAWN = 1;

    private byte action;

    @Override
    public int getId() {
        return 0xCD;
    }

    @Override
    public void read(ByteBuf buf) {
        this.action = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(action);
    }
}
