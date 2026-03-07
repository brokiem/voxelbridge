package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LcePreLoginPacket implements Packet {

    public static final int SAVE_NAME_LEN = 14;

    private short netcodeVersion;
    private String loginKey;
    private byte friendsOnlyBits;
    private int ugcPlayersVersion;
    private byte dwPlayerCount;
    private long[] playerXuids;
    private byte[] szUniqueSaveName = new byte[SAVE_NAME_LEN];
    private int serverSettings;
    private byte hostIndex;
    private int texturePackId;

    @Override
    public int getId() {
        return 0x02;
    }

    @Override
    public void read(ByteBuf buf) {
        this.netcodeVersion = buf.readShort();
        this.loginKey = ProtocolUtils.readStringLce(buf);
        this.friendsOnlyBits = buf.readByte();
        this.ugcPlayersVersion = buf.readInt();
        this.dwPlayerCount = buf.readByte();
        if (this.dwPlayerCount > 0) {
            this.playerXuids = new long[this.dwPlayerCount];
            for (int i = 0; i < this.dwPlayerCount; i++) {
                this.playerXuids[i] = buf.readLong();
            }
        } else {
            this.playerXuids = new long[0];
        }
        buf.readBytes(this.szUniqueSaveName);
        this.serverSettings = buf.readInt();
        this.hostIndex = buf.readByte();
        this.texturePackId = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(netcodeVersion);
        ProtocolUtils.writeStringLce(buf, loginKey != null ? loginKey : "");
        buf.writeByte(friendsOnlyBits);
        buf.writeInt(ugcPlayersVersion);
        buf.writeByte(dwPlayerCount);
        if (dwPlayerCount > 0 && playerXuids != null) {
            for (int i = 0; i < dwPlayerCount; i++) {
                if (i < playerXuids.length) {
                    buf.writeLong(playerXuids[i]);
                } else {
                    buf.writeLong(0);
                }
            }
        }
        if (szUniqueSaveName != null && szUniqueSaveName.length == SAVE_NAME_LEN) {
            buf.writeBytes(szUniqueSaveName);
        } else {
            buf.writeZero(SAVE_NAME_LEN);
        }
        buf.writeInt(serverSettings);
        buf.writeByte(hostIndex);
        buf.writeInt(texturePackId);
    }
}
