package id.brokiem.voxelbridge.session;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Deque;

@Getter
@Setter
public class Session {
    /**
     * Packets to send to Java server once the connection is active
     */
    private final Deque<Packet> pendingServerbound = new ArrayDeque<>();
    private Channel clientChannel;
    private Channel serverChannel;
    private ConnectionState state = ConnectionState.HANDSHAKE;
    private String username;
    private int entityId;

    public Session(Channel clientChannel) {
        this.clientChannel = clientChannel;
    }

    public void queueForServer(Packet packet) {
        pendingServerbound.addLast(packet);
    }

    /**
     * Flushes pending serverbound packets. Call when Java connection becomes active.
     */
    public void flushPendingServerbound() {
        Channel server = getServerChannel();
        if (server == null || !server.isActive()) {
            return;
        }
        while (!pendingServerbound.isEmpty()) {
            Packet p = pendingServerbound.pollFirst();
            server.write(p);
        }
        server.flush();
    }
}
