package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundUseEntityPacket implements Packet {
    public static final int LEFT_CLICK = 0;
    public static final int RIGHT_CLICK = 1;

    private int target;
    private byte mouse;

    @Override
    public int getId() {
        return 0x02;
    }

    @Override
    public void read(ByteBuf buf) {
        this.target = buf.readInt();
        this.mouse = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(target);
        buf.writeByte(mouse);
    }
}
