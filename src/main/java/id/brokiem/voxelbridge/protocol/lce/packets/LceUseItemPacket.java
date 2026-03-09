package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceUseItemPacket implements Packet {
    private int x;
    private short y;
    private int z;
    private byte face;
    private short itemId = -1;
    private byte itemCount = 0;
    private short itemDamage = 0;
    private byte[] nbtData = new byte[0];
    private float clickX;
    private float clickY;
    private float clickZ;

    public static final float CLICK_ACCURACY = 16.0f;

    @Override
    public int getId() {
        return 0x0F;
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readUnsignedByte();
        this.z = buf.readInt();
        this.face = buf.readByte();
        // Read item
        this.itemId = buf.readShort();
        if (itemId >= 0) {
            this.itemCount = buf.readByte();
            this.itemDamage = buf.readShort();
            short nbtLen = buf.readShort();
            if (nbtLen > 0) {
                nbtData = new byte[nbtLen];
                buf.readBytes(nbtData);
            } else {
                nbtData = new byte[0];
            }
        }
        this.clickX = buf.readUnsignedByte() / CLICK_ACCURACY;
        this.clickY = buf.readUnsignedByte() / CLICK_ACCURACY;
        this.clickZ = buf.readUnsignedByte() / CLICK_ACCURACY;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeByte(y);
        buf.writeInt(z);
        buf.writeByte(face);
        // Write item
        buf.writeShort(itemId);
        if (itemId >= 0) {
            buf.writeByte(itemCount);
            buf.writeShort(itemDamage);
            if (nbtData.length > 0) {
                buf.writeShort(nbtData.length);
                buf.writeBytes(nbtData);
            } else {
                buf.writeShort(-1);
            }
        }
        buf.writeByte((int) (clickX * CLICK_ACCURACY));
        buf.writeByte((int) (clickY * CLICK_ACCURACY));
        buf.writeByte((int) (clickZ * CLICK_ACCURACY));
    }
}
