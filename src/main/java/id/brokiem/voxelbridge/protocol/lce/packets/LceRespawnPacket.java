package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceRespawnPacket implements Packet {
    private byte dimension;
    private int gameMode;
    private int mapHeight;
    private String levelType = "default";
    private long seed;
    private byte difficulty;
    private boolean newSeaLevel;
    private int newEntityId;
    // _LARGE_WORLDS fields
    private int xzSize;
    private int hellScale;

    @Override
    public int getId() {
        return 0x09;
    }

    @Override
    public void read(ByteBuf buf) {
        dimension = buf.readByte();
        gameMode = buf.readByte();
        mapHeight = buf.readShort();
        levelType = ProtocolUtils.readStringLce(buf);
        seed = buf.readLong();
        difficulty = buf.readByte();
        newSeaLevel = buf.readBoolean();
        newEntityId = buf.readShort();
        xzSize = buf.readShort();
        hellScale = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(dimension);
        buf.writeByte(gameMode);
        buf.writeShort(mapHeight);
        ProtocolUtils.writeStringLce(buf, levelType);
        buf.writeLong(seed);
        buf.writeByte(difficulty);
        buf.writeBoolean(newSeaLevel);
        buf.writeShort(newEntityId);
        buf.writeShort(xzSize);
        buf.writeByte(hellScale);
    }
}
