package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_kick_disconnect
 * ID: 0x40
 * Direction: Clientbound
 * State: Play
 * <p>
 * Ends an active play session and provides the reason the Java server is
 * disconnecting the client.
 */
@Getter
@Setter
public class JavaClientboundKickDisconnectPacket implements Packet {
    private String reason;

    @Override
    public int getId() {
        return 0x40;
    }

    @Override
    public void read(ByteBuf buf) {
        this.reason = ProtocolUtils.readString(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeString(buf, reason != null ? reason : "");
    }
}

