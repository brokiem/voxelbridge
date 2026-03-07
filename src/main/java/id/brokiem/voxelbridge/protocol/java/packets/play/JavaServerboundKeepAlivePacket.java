package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_keep_alive
 * ID: 0x00
 * Direction: Serverbound
 * State: Play
 * <p>
 * Sent by the client in response to a server keep-alive packet using the same
 * ID value so the play connection remains open.
 */
@Getter
@Setter
public class JavaServerboundKeepAlivePacket implements Packet {
    private int keepAliveId;

    @Override
    public int getId() {
        return 0x00;
    }

    @Override
    public void read(ByteBuf buf) {
        this.keepAliveId = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(keepAliveId);
    }
}

