package id.brokiem.voxelbridge.protocol.java.codec;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class JavaPacketEncoder extends MessageToMessageEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) {
        ByteBuf buf = ctx.alloc().buffer();
        boolean success = false;
        try {
            ProtocolUtils.writeVarInt(buf, msg.getId());
            msg.write(buf);
            out.add(buf);
            success = true;
        } finally {
            if (!success) {
                buf.release();
            }
        }
    }
}
