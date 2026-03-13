package id.brokiem.voxelbridge.network;

import id.brokiem.voxelbridge.protocol.lce.codec.LcePacketDecoder;
import id.brokiem.voxelbridge.protocol.lce.codec.LcePacketEncoder;
import id.brokiem.voxelbridge.server.ProxyConfig;
import id.brokiem.voxelbridge.translation.TranslatorRegistry;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SmallIdHandshakeHandler extends ChannelInboundHandlerAdapter {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(4);

    private final ProxyConfig config;
    private final TranslatorRegistry translatorRegistry;

    public SmallIdHandshakeHandler(ProxyConfig config, TranslatorRegistry translatorRegistry) {
        this.config = config;
        this.translatorRegistry = translatorRegistry;
    }

    private static byte nextSmallId() {
        while (true) {
            int current = NEXT_ID.get();
            int next = current == 255 ? 4 : current + 1;
            if (NEXT_ID.compareAndSet(current, next)) {
                return (byte) current;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        byte id = nextSmallId();
        ctx.channel().config().setAutoRead(false);
        log.info("[LCE] Client connected: {} (smallId={})", ctx.channel().remoteAddress(), id & 0xFF);

        ctx.writeAndFlush(Unpooled.wrappedBuffer(new byte[]{id})).addListener(f -> {
            if (!f.isSuccess()) {
                log.error("[LCE] Failed to send smallId: {}", f.cause() != null ? f.cause().getMessage() : "unknown", f.cause());
                ctx.close();
                return;
            }

            ChannelPipeline p = ctx.pipeline();
            p.addAfter(ctx.name(), "lce-frame-dec", new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, 4 * 1024 * 1024, 0, 4, 0, 4, true));
            p.addAfter("lce-frame-dec", "lce-frame-enc", new LengthFieldPrepender(ByteOrder.BIG_ENDIAN, 4, 0, false));
            p.addAfter("lce-frame-enc", "lce-decoder", new LcePacketDecoder());
            p.addAfter("lce-decoder", "lce-encoder", new LcePacketEncoder());
            p.addAfter("lce-encoder", "client-connection", new LceClientHandler(config.getTargetHost(), config.getTargetPort(), translatorRegistry));
            p.remove(this);
            ctx.channel().config().setAutoRead(true);
            ctx.read();
            ctx.fireChannelActive();
        });
    }
}
