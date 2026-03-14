package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import id.brokiem.voxelbridge.protocol.types.SlotData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundBlockPlacePacket implements Packet {
    private int x;
    private short y;
    private int z;
    private byte direction;
    private SlotData item = SlotData.empty();
    private byte cursorX;
    private byte cursorY;
    private byte cursorZ;

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
        this.item = ProtocolUtils.readJavaSlot(buf);
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
        ProtocolUtils.writeJavaSlot(buf, item);
        buf.writeByte(cursorX);
        buf.writeByte(cursorY);
        buf.writeByte(cursorZ);
    }
}
