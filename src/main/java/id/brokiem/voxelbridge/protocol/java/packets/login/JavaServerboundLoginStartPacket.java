package id.brokiem.voxelbridge.protocol.java.packets.login;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_login_start
 * ID: 0x00
 * Direction: Serverbound
 * State: Login
 * <p>
 * Begins the login phase by sending the player's username to the Java server
 * after the initial handshake has selected the login state.
 */
@Getter
@Setter
public class JavaServerboundLoginStartPacket implements Packet {
    private String username;

    @Override
    public int getId() {
        return 0x00;
    }

    @Override
    public void read(ByteBuf buf) {
        this.username = ProtocolUtils.readString(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeString(buf, username);
    }
}

