package id.brokiem.voxelbridge.protocol.java.packets.handshake;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

/**
 * Packet Name: packet_set_protocol
 * ID: 0x00
 * Direction: Serverbound
 * State: Handshake
 * <p>
 * Sent by the client at the start of a connection to declare the Java protocol
 * version, target host and port, and the next state it wants to enter.
 */
@Getter
@Setter
public class JavaServerboundSetProtocolPacket implements Packet {
    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private int nextState;

    @Override
    public int getId() {
        return 0x00; // Set Protocol is 0x00 in the handshaking state
    }

    @Override
    public void read(ByteBuf buf) {
        this.protocolVersion = ProtocolUtils.readVarInt(buf);
        this.serverAddress = ProtocolUtils.readString(buf);
        this.serverPort = buf.readUnsignedShort();
        this.nextState = ProtocolUtils.readVarInt(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        ProtocolUtils.writeVarInt(buf, protocolVersion);
        ProtocolUtils.writeString(buf, serverAddress);
        buf.writeShort(serverPort);
        ProtocolUtils.writeVarInt(buf, nextState);
    }
}

