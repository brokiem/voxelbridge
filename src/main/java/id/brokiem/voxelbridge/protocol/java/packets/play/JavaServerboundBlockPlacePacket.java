package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundBlockPlacePacket implements Packet {
    private int x;
    private short y;
    private int z;
    private byte direction;
    private short heldItem;
    private byte cursorX;
    private byte cursorY;
    private byte cursorZ;

    private short itemId = -1;
    private byte itemCount = 0;
    private short itemDamage = 0;
    private byte[] nbtData = new byte[0];

    @Override
    public int getId() {
        return 0x08;
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readUnsignedByte();
        this.z = buf.readInt();
        this.direction = buf.readByte();
        // Read slot
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
        this.cursorX = buf.readByte();
        this.cursorY = buf.readByte();
        this.cursorZ = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeByte(y);
        buf.writeInt(z);
        buf.writeByte(direction);
        // Write slot
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
        buf.writeByte(cursorX);
        buf.writeByte(cursorY);
        buf.writeByte(cursorZ);
    }
}
