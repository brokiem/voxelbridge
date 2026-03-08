package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundArmAnimationPacket implements Packet {
    public static final byte NO_ANIMATION = 0;
    public static final byte SWING_ARM = 1;
    public static final byte DAMAGE = 2;
    public static final byte LEAVE_BED = 3;
    public static final byte EAT_FOOD = 5;
    public static final byte CRITICAL_EFFECT = 6;
    public static final byte MAGIC_CRITICAL_EFFECT = 7;
    public static final byte UNKNOWN_102 = (byte) 102;
    public static final byte UNKNOWN_104 = (byte) 104;
    public static final byte CROUCH = (byte) 104;
    public static final byte UNCROUCH = (byte) 105;

    private int entityId;
    private byte animation;

    @Override
    public int getId() {
        return 0x0A;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.animation = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(animation);
    }
}

