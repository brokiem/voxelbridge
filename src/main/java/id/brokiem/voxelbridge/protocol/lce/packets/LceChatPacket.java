package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceChatPacket implements Packet {
    private short messageType;
    private String[] stringArgs = new String[0];
    private int[] intArgs = new int[0];

    @Override
    public int getId() {
        return 0x03;
    }

    @Override
    public void read(ByteBuf buf) {
        this.messageType = buf.readShort();
        short packed = buf.readShort();
        int stringCount = (packed >> 4) & 0xF;
        int intCount = packed & 0xF;
        this.stringArgs = new String[stringCount];
        for (int i = 0; i < stringCount; i++) {
            this.stringArgs[i] = ProtocolUtils.readStringLce(buf);
        }
        this.intArgs = new int[intCount];
        for (int i = 0; i < intCount; i++) {
            this.intArgs[i] = buf.readInt();
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeShort(messageType);
        int stringCount = stringArgs.length;
        int intCount = intArgs.length;
        buf.writeShort((short) ((stringCount << 4) | intCount));
        for (String s : stringArgs) ProtocolUtils.writeStringLce(buf, s);
        for (int v : intArgs) buf.writeInt(v);
    }
}
