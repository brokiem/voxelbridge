package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import id.brokiem.voxelbridge.protocol.types.SlotData;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaServerboundWindowClickPacket implements Packet {
    private byte windowId;
    private short slot;
    private byte mouseButton;
    private short action;
    private byte mode;
    private SlotData item = SlotData.empty();

    @Override
    public int getId() {
        return 0x0E;
    }

    @Override
    public void read(ByteBuf buf) {
        this.windowId = buf.readByte();
        this.slot = buf.readShort();
        this.mouseButton = buf.readByte();
        this.action = buf.readShort();
        this.mode = buf.readByte();
        this.item = ProtocolUtils.readJavaSlot(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(windowId);
        buf.writeShort(slot);
        buf.writeByte(mouseButton);
        buf.writeShort(action);
        buf.writeByte(mode);
        ProtocolUtils.writeJavaSlot(buf, item);
    }
}
