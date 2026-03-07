package id.brokiem.voxelbridge.network;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.java.codec.*;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.session.SessionHandler;
import id.brokiem.voxelbridge.translation.TranslatorRegistry;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LceClientHandler extends ChannelInboundHandlerAdapter {

    private final String targetHost;
    private final int targetPort;
    private final TranslatorRegistry translatorRegistry;
    private SessionHandler sessionHandler;

    public LceClientHandler(String targetHost, int targetPort, TranslatorRegistry translatorRegistry) {
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.translatorRegistry = translatorRegistry;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Session session = new Session(ctx.channel());
        this.sessionHandler = new SessionHandler(session, translatorRegistry);

        Bootstrap b = new Bootstrap();
        b.group(ctx.channel().eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        JavaPacketRegistry javaRegistry = JavaPacketRegistry.create();
                        ch.pipeline().addLast("java-frame-dec", new JavaFrameDecoder());
                        ch.pipeline().addLast("java-frame-enc", new JavaFrameEncoder());
                        ch.pipeline().addLast("java-dec", new JavaPacketDecoder(javaRegistry, session));
                        ch.pipeline().addLast("java-enc", new JavaPacketEncoder());
                        ch.pipeline().addLast("server-connection", new JavaServerHandler(session, sessionHandler));
                    }
                });

        ChannelFuture f = b.connect(targetHost, targetPort);
        session.setServerChannel(f.channel());

        f.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("[LCE] Connected to target: {}:{}", targetHost, targetPort);
                session.flushPendingServerbound();
            } else {
                Throwable cause = future.cause();
                log.error("[LCE] Failed to connect to {}:{}: {}", targetHost, targetPort, cause != null ? cause.getMessage() : "unknown", cause);
                ctx.close();
            }
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof Packet packet) {
                sessionHandler.handleServerbound(packet);
            } else {
                log.debug("[LCE] Unhandled message: {}", msg.getClass().getSimpleName());
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("[LCE] Client disconnected: {}", ctx.channel().remoteAddress());
        if (sessionHandler != null) {
            Channel serverChannel = sessionHandler.getSession().getServerChannel();
            if (serverChannel != null) {
                serverChannel.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("[LCE] Exception: {}", cause.getMessage(), cause);
        ctx.close();
    }
}
