package id.brokiem.voxelbridge.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DiscoveryBroadcaster {

    private static final int DISCOVERY_PORT = 25566;
    private static final int MAGIC = 0x4D434C4E;
    private static final int INTERVAL_MS = 1500;

    private final EventLoopGroup group;
    private final int gamePort;
    private final int protocolVersion;
    private volatile boolean running = false;
    private volatile Channel channel;
    private volatile ScheduledFuture<?> broadcastTask;

    public DiscoveryBroadcaster(EventLoopGroup group, int gamePort, int protocolVersion) {
        this.group = group;
        this.gamePort = gamePort;
        this.protocolVersion = protocolVersion;
    }

    public synchronized void start() throws InterruptedException {
        if (running) {
            return;
        }

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) {
                        // No inbound handler needed — we only send
                    }
                });

        ChannelFuture bindFuture = b.bind(0).sync();
        channel = bindFuture.channel();
        running = true;
        broadcastTask = channel.eventLoop().scheduleAtFixedRate(this::broadcast, 0, INTERVAL_MS, TimeUnit.MILLISECONDS);
    }

    private void broadcast() {
        if (!running) {
            return;
        }

        Channel currentChannel = channel;
        if (currentChannel == null || !currentChannel.isActive()) {
            return;
        }

        InetSocketAddress broadcast = new InetSocketAddress("255.255.255.255", DISCOVERY_PORT);
        currentChannel.writeAndFlush(new DatagramPacket(createDiscoveryResponse(currentChannel.alloc()), broadcast))
                .addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess() && running) {
                        log.debug("[Discovery] Broadcast failed: {}", future.cause() != null ? future.cause().getMessage() : "unknown");
                    }
                });
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;
        ScheduledFuture<?> currentTask = broadcastTask;
        broadcastTask = null;
        if (currentTask != null) {
            currentTask.cancel(false);
        }

        Channel currentChannel = channel;
        channel = null;
        if (currentChannel != null) {
            currentChannel.close().awaitUninterruptibly();
        }
    }

    private ByteBuf createDiscoveryResponse(ByteBufAllocator allocator) {
        ByteBuf buf = allocator.buffer(84);
        buf.writeIntLE(MAGIC);
        buf.writeShortLE(protocolVersion);
        buf.writeShortLE(gamePort);

        byte[] hostNameBytes = "VoxelBridge".getBytes(StandardCharsets.UTF_16LE);
        byte[] paddedHostName = new byte[64];
        // Cap at 62 to preserve the null terminator (wchar_t[32] = 64 bytes)
        System.arraycopy(hostNameBytes, 0, paddedHostName, 0, Math.min(hostNameBytes.length, 62));
        buf.writeBytes(paddedHostName);

        buf.writeByte(0);   // playerCount
        buf.writeByte(8);   // maxPlayers
        buf.writeIntLE(0);  // gameHostSettings
        buf.writeIntLE(0);  // texturePackParentId
        buf.writeByte(0);   // subTexturePackId
        buf.writeByte(1);   // isJoinable
        return buf;
    }
}
