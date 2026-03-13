package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundAnimationPacket implements Packet {

    private int entityId;
    private short animation; // Unsigned byte

    @Override
    public int getId() {
        return 0x0B;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = ProtocolUtils.readVarInt(buf);
        this.animation = buf.readUnsignedByte();
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeVarInt(buf, entityId);
        buf.writeByte(animation);
    }
}
