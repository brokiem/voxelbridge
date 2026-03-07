package id.brokiem.voxelbridge.protocol.lce.codec;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class LcePacketDecoder extends MessageToMessageDecoder<ByteBuf> {
    private final PacketRegistry registry;

    public LcePacketDecoder() {
        this.registry = LcePacketRegistry.create();
    }

    public LcePacketDecoder(PacketRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf frame, List<Object> out) {
        while (frame.isReadable()) {
            int packetId = frame.readUnsignedByte();
            Packet packet = registry.createPacket(packetId);
            if (packet == null) {
                log.warn("[LCE] Unknown packet 0x{}, {} bytes skipped", Integer.toHexString(packetId), frame.readableBytes());
                frame.skipBytes(frame.readableBytes());
                return;
            }
            packet.read(frame);
            out.add(packet);
        }
    }
}
