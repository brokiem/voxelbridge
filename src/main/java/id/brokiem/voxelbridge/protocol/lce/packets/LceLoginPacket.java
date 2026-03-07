package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceLoginPacket implements Packet {

    private int clientVersion;
    private String userName;
    private String levelTypeName;
    private long seed;
    private int gameType;
    private byte dimension;
    private byte mapHeight;
    private byte maxPlayers;
    private long offlineXuid;
    private long onlineXuid;
    private boolean friendsOnlyUGC;
    private int ugcPlayersVersion;
    private byte difficulty;
    private int multiplayerInstanceId;
    private byte playerIndex;
    private int playerSkinId;
    private int playerCapeId;
    private boolean isGuest;
    private boolean newSeaLevel;
    private int uiGamePrivileges;

    // _LARGE_WORLDS suffix fields
    private short xzSize;
    private byte hellScale;

    @Override
    public int getId() {
        return 0x01;
    }

    @Override
    public void read(ByteBuf buf) {
        this.clientVersion = buf.readInt();
        this.userName = ProtocolUtils.readStringLce(buf);
        this.levelTypeName = ProtocolUtils.readStringLce(buf);
        this.seed = buf.readLong();
        this.gameType = buf.readInt();
        this.dimension = buf.readByte();
        this.mapHeight = buf.readByte();
        this.maxPlayers = buf.readByte();
        this.offlineXuid = buf.readLong();
        this.onlineXuid = buf.readLong();
        this.friendsOnlyUGC = buf.readBoolean();
        this.ugcPlayersVersion = buf.readInt();
        this.difficulty = buf.readByte();
        this.multiplayerInstanceId = buf.readInt();
        this.playerIndex = buf.readByte();
        this.playerSkinId = buf.readInt();
        this.playerCapeId = buf.readInt();
        this.isGuest = buf.readBoolean();
        this.newSeaLevel = buf.readBoolean();
        this.uiGamePrivileges = buf.readInt();

        this.xzSize = buf.readShort();
        this.hellScale = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(clientVersion);
        ProtocolUtils.writeStringLce(buf, userName != null ? userName : "");
        ProtocolUtils.writeStringLce(buf, levelTypeName != null ? levelTypeName : "default");
        buf.writeLong(seed);
        buf.writeInt(gameType);
        buf.writeByte(dimension);
        buf.writeByte(mapHeight);
        buf.writeByte(maxPlayers);
        buf.writeLong(offlineXuid);
        buf.writeLong(onlineXuid);
        buf.writeBoolean(friendsOnlyUGC);
        buf.writeInt(ugcPlayersVersion);
        buf.writeByte(difficulty);
        buf.writeInt(multiplayerInstanceId);
        buf.writeByte(playerIndex);
        buf.writeInt(playerSkinId);
        buf.writeInt(playerCapeId);
        buf.writeBoolean(isGuest);
        buf.writeBoolean(newSeaLevel);
        buf.writeInt(uiGamePrivileges);

        buf.writeShort(xzSize);
        buf.writeByte(hellScale);
    }
}
