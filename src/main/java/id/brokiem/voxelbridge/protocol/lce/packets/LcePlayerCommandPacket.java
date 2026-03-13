package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LcePlayerCommandPacket implements Packet {

    public static final int START_SNEAKING = 1;
    public static final int STOP_SNEAKING = 2;
    public static final int STOP_SLEEPING = 3;
    public static final int START_SPRINTING = 4;
    public static final int STOP_SPRINTING = 5;
    public static final int START_IDLEANIM = 6;
    public static final int STOP_IDLEANIM = 7;
    public static final int RIDING_JUMP = 8;
    public static final int OPEN_INVENTORY = 9;

    private int entityId;
    private int action;
    private int data;

    @Override
    public int getId() {
        return 0x13;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.action = buf.readByte();
        this.data = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(action);
        buf.writeInt(data);
    }
}
