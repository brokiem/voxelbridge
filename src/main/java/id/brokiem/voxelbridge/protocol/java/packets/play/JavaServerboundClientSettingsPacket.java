package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundClientSettingsPacket implements Packet {

    private String locale;
    private byte viewDistance;
    private byte chatFlags;
    private boolean chatColors;
    private short difficulty; // Unsigned byte
    private boolean showCape;

    @Override
    public int getId() {
        return 0x15;
    }

    @Override
    public void read(ByteBuf buf) {
        this.locale = ProtocolUtils.readString(buf);
        this.viewDistance = buf.readByte();
        this.chatFlags = buf.readByte();
        this.chatColors = buf.readBoolean();
        this.difficulty = buf.readUnsignedByte();
        this.showCape = buf.readBoolean();
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeString(buf, locale != null ? locale : "en_US");
        buf.writeByte(viewDistance);
        buf.writeByte(chatFlags);
        buf.writeBoolean(chatColors);
        buf.writeByte(difficulty);
        buf.writeBoolean(showCape);
    }
}
