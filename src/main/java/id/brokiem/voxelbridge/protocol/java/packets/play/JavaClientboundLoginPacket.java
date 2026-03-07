package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_login
 * ID: 0x01
 * Direction: Clientbound
 * State: Play
 * <p>
 * Initializes the player in the world by providing the entity ID, game mode,
 * dimension, difficulty, player limit, and level type for the session.
 */
@Getter
@Setter
public class JavaClientboundLoginPacket implements Packet {

    private int entityId;
    private short gameMode; // Unsigned Byte
    private byte dimension;
    private short difficulty; // Unsigned Byte
    private short maxPlayers; // Unsigned Byte
    private String levelType;

    @Override
    public int getId() {
        return 0x01;
    }

    @Override
    public void read(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.gameMode = buf.readUnsignedByte();
        this.dimension = buf.readByte();
        this.difficulty = buf.readUnsignedByte();
        this.maxPlayers = buf.readUnsignedByte();
        this.levelType = ProtocolUtils.readString(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(gameMode);
        buf.writeByte(dimension);
        buf.writeByte(difficulty);
        buf.writeByte(maxPlayers);
        ProtocolUtils.writeString(buf, levelType != null ? levelType : "default");
    }
}

