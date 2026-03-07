package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_held_item_slot
 * ID: 0x09
 * Direction: Clientbound
 * State: Play
 * <p>
 * Changes the selected hotbar slot so the client switches the currently held
 * item in the player's hand.
 */
@Getter
@Setter
public class JavaClientboundHeldItemSlotPacket implements Packet {
    private byte slot;

    @Override
    public int getId() {
        return 0x09;
    }

    @Override
    public void read(ByteBuf buf) {
        this.slot = buf.readByte();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeByte(slot);
    }
}

