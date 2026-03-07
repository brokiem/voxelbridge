package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundRespawnPacket implements Packet {
    private int dimension;
    private byte difficulty;
    private byte gameMode;
    private String levelType = "";

    @Override
    public int getId() {
        return 0x07;
    }

    @Override
    public void read(ByteBuf buf) {
        this.dimension = buf.readInt();
        this.difficulty = buf.readByte();
        this.gameMode = buf.readByte();
        this.levelType = ProtocolUtils.readString(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(dimension);
        buf.writeByte(difficulty);
        buf.writeByte(gameMode);
        ProtocolUtils.writeString(buf, levelType);
    }
}
