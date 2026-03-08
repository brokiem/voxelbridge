package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundPlayerDiggingPacket implements Packet {
    private byte status;
    private int x;
    private short y;
    private int z;
    private byte face;

    @Override
    public int getId() {
        return 0x07;
    }

    @Override
    public void read(ByteBuf buf) {
        this.status = buf.readByte();
        this.x = buf.readInt();
        this.y = buf.readUnsignedByte();
        this.z = buf.readInt();
        this.face = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(status);
        buf.writeInt(x);
        buf.writeByte(y);
        buf.writeInt(z);
        buf.writeByte(face);
    }
}
