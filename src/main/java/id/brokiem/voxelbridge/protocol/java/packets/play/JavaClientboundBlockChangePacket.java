package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundBlockChangePacket implements Packet {
    private int x;
    private short y;
    private int z;
    private int blockId;
    private int blockMetadata;

    @Override
    public int getId() {
        return 0x23;
    }

    @Override
    public void read(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readUnsignedByte();
        this.z = buf.readInt();
        this.blockId = ProtocolUtils.readVarInt(buf);
        this.blockMetadata = buf.readUnsignedByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeByte(y);
        buf.writeInt(z);
        ProtocolUtils.writeVarInt(buf, blockId);
        buf.writeByte(blockMetadata);
    }
}
