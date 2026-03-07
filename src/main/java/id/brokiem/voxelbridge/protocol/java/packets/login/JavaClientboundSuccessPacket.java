package id.brokiem.voxelbridge.protocol.java.packets.login;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_success
 * ID: 0x02
 * Direction: Clientbound
 * State: Login
 * <p>
 * Confirms that login succeeded and provides the authenticated player's UUID
 * and username before the session transitions into the play state.
 */
@Getter
@Setter
public class JavaClientboundSuccessPacket implements Packet {
    private String uuid;
    private String username;

    @Override
    public int getId() {
        return 0x02; // Success is 0x02 in the login state
    }

    @Override
    public void read(ByteBuf buf) {
        this.uuid = ProtocolUtils.readString(buf);
        this.username = ProtocolUtils.readString(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeString(buf, uuid);
        ProtocolUtils.writeString(buf, username);
    }
}

