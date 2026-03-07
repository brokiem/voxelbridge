package id.brokiem.voxelbridge.server;

import id.brokiem.voxelbridge.network.SmallIdHandshakeHandler;
import id.brokiem.voxelbridge.translation.TranslatorRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ProxyServer implements AutoCloseable {
    private final ProxyConfig config;
    private final TranslatorRegistry translatorRegistry;
    private final AtomicBoolean running = new AtomicBoolean();

    private volatile EventLoopGroup bossGroup;
    private volatile EventLoopGroup workerGroup;
    private volatile EventLoopGroup discoveryGroup;
    private volatile Channel serverChannel;
    private volatile DiscoveryBroadcaster discoveryBroadcaster;

    public ProxyServer(ProxyConfig config) {
        this.config = config;
        this.translatorRegistry = new TranslatorRegistry(config);
    }

    public void start() throws InterruptedException {
        if (!running.compareAndSet(false, true)) {
            throw new IllegalStateException("ProxyServer is already running");
        }

        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        discoveryGroup = new NioEventLoopGroup(1);

        try {
            discoveryBroadcaster = new DiscoveryBroadcaster(discoveryGroup, config.getListenPort(), config.getLceNetVersion());
            discoveryBroadcaster.start();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast("small-id", new SmallIdHandshakeHandler(config, translatorRegistry));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            ChannelFuture f = bootstrap.bind(config.getListenPort()).sync();
            serverChannel = f.channel();
            log.info("Listening on 0.0.0.0:{}", config.getListenPort());
            log.info("Forwarding to {}:{}", config.getTargetHost(), config.getTargetPort());
            log.info("LAN discovery: UDP 25566");
            log.info("Ready.");

            serverChannel.closeFuture().sync();
        } finally {
            close();
        }
    }

    @Override
    public void close() {
        if (!running.getAndSet(false)) {
            return;
        }

        closeChannel(serverChannel);
        serverChannel = null;

        DiscoveryBroadcaster currentBroadcaster = discoveryBroadcaster;
        discoveryBroadcaster = null;
        if (currentBroadcaster != null) {
            currentBroadcaster.stop();
        }

        shutdownGroup(workerGroup, "worker");
        workerGroup = null;
        shutdownGroup(bossGroup, "boss");
        bossGroup = null;
        shutdownGroup(discoveryGroup, "discovery");
        discoveryGroup = null;
    }

    private void closeChannel(Channel channel) {
        if (channel == null) {
            return;
        }

        ChannelFuture closeFuture = channel.close();
        if (!channel.eventLoop().inEventLoop()) {
            closeFuture.awaitUninterruptibly();
        }
    }

    private void shutdownGroup(EventLoopGroup group, String name) {
        if (group == null) {
            return;
        }

        try {
            group.shutdownGracefully().syncUninterruptibly();
        } catch (Exception e) {
            log.warn("Failed to shut down {} event loop group cleanly: {}", name, e.getMessage(), e);
        }
    }
}
