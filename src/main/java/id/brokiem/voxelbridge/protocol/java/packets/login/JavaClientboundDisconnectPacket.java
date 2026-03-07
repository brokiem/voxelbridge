package id.brokiem.voxelbridge.protocol.java.packets.login;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_disconnect
 * ID: 0x00
 * Direction: Clientbound
 * State: Login
 * <p>
 * Sent by the server when login is rejected or aborted before the connection
 * enters the play state. Carries a human-readable disconnect reason.
 */
@Getter
@Setter
public class JavaClientboundDisconnectPacket implements Packet {
    private String reason;

    @Override
    public int getId() {
        return 0x00;
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

