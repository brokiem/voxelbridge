package id.brokiem.voxelbridge.network;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.session.SessionHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaServerHandler extends ChannelInboundHandlerAdapter {
    private final Session session;
    private final SessionHandler sessionHandler;

    public JavaServerHandler(Session session, SessionHandler sessionHandler) {
        this.session = session;
        this.sessionHandler = sessionHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof Packet packet) {
                sessionHandler.handleClientbound(packet);
            } else {
                log.debug("[Java] Unhandled message: {}", msg.getClass().getSimpleName());
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (session.getClientChannel() != null) {
            session.getClientChannel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("[Java] Exception: {}", cause.getMessage(), cause);
        ctx.close();
    }
}
