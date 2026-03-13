package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceSetEntityMotionPacket implements Packet {

    private int entityId;
    private int xa;
    private int ya;
    private int za;

    @Override
    public int getId() {
        return 0x1C;
    }

    @Override
    public void read(ByteBuf buf) {
        short idAndFlag = buf.readShort();
        this.entityId = idAndFlag & 0x07FF;
        if ((idAndFlag & 0x0800) != 0) {
            // Byte mode
            this.xa = buf.readByte() * 16;
            this.ya = buf.readByte() * 16;
            this.za = buf.readByte() * 16;
        } else {
            // Short mode
            this.xa = buf.readShort();
            this.ya = buf.readShort();
            this.za = buf.readShort();
        }
    }

    @Override
    public void write(ByteBuf buf) {
        // Check if byte mode is possible
        if (xa >= -2048 && xa < 2048 && ya >= -2048 && ya < 2048 && za >= -2048 && za < 2048) {
            buf.writeShort(entityId | 0x0800);
            buf.writeByte(xa / 16);
            buf.writeByte(ya / 16);
            buf.writeByte(za / 16);
        } else {
            buf.writeShort(entityId);
            buf.writeShort(xa);
            buf.writeShort(ya);
            buf.writeShort(za);
        }
    }
}
