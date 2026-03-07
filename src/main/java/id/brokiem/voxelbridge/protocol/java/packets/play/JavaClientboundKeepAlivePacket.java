package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_keep_alive
 * ID: 0x00
 * Direction: Clientbound
 * State: Play
 * <p>
 * Sent periodically by the server to keep the play connection alive and verify
 * that the client is still responsive. The client echoes the same ID back.
 */
@Getter
@Setter
public class JavaClientboundKeepAlivePacket implements Packet {
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

