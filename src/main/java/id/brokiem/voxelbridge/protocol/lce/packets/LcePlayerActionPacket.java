package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LcePlayerActionPacket implements Packet {
    public static final int START_DESTROY_BLOCK = 0;
    public static final int ABORT_DESTROY_BLOCK = 1;
    public static final int STOP_DESTROY_BLOCK = 2;
    public static final int DROP_ALL_ITEMS = 3;
    public static final int DROP_ITEM = 4;
    public static final int RELEASE_USE_ITEM = 5;

    private int action;
    private int x;
    private int y;
    private int z;
    private int face;

    @Override
    public int getId() {
        return 0x0E;
    }

    @Override
    public void read(ByteBuf buf) {
        this.action = buf.readUnsignedByte();
        this.x = buf.readInt();
        this.y = buf.readUnsignedByte();
        this.z = buf.readInt();
        this.face = buf.readUnsignedByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(action);
        buf.writeInt(x);
        buf.writeByte(y);
        buf.writeInt(z);
        buf.writeByte(face);
    }
}
