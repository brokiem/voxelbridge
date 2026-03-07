package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundClientCommandPacket implements Packet {
    public static final int PERFORM_RESPAWN = 0;
    public static final int REQUEST_STATS = 1;
    public static final int OPEN_INVENTORY_ACHIEVEMENT = 2;

    private int payload;

    @Override
    public int getId() {
        return 0x16;
    }

    @Override
    public void read(ByteBuf buf) {
        payload = ProtocolUtils.readVarInt(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeVarInt(buf, payload);
    }
}
