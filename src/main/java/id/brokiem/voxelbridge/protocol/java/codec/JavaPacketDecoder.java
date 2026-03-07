package id.brokiem.voxelbridge.protocol.java.codec;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import id.brokiem.voxelbridge.session.Session;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JavaPacketDecoder extends MessageToMessageDecoder<ByteBuf> {
    private final JavaPacketRegistry registry;
    private final Session session;

    public JavaPacketDecoder(JavaPacketRegistry registry, Session session) {
        this.registry = registry;
        this.session = session;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (!in.isReadable()) {
            return;
        }

        int packetId = ProtocolUtils.readVarInt(in);
        Packet packet = registry.createPacket(session.getState(), packetId);
        if (packet != null) {
            packet.read(in);
            if (in.isReadable()) {
                log.warn("[Java] Packet 0x{} in state {} left {} trailing bytes after decode",
                        Integer.toHexString(packetId), session.getState(), in.readableBytes());
                in.skipBytes(in.readableBytes());
            }
            out.add(packet);
        } else {
            int payloadSize = in.readableBytes();
            in.skipBytes(payloadSize);
            log.debug("[Java] Unknown packet 0x{} in state {}, dropped ({} bytes)", Integer.toHexString(packetId), session.getState(), payloadSize);
        }
    }
}
