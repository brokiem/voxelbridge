package id.brokiem.voxelbridge.protocol.java.codec;

import id.brokiem.voxelbridge.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class JavaFrameEncoder extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf buf, ByteBuf out) {
        int bodyLen = buf.readableBytes();
        int headerLen = varIntSize(bodyLen);
        out.ensureWritable(headerLen + bodyLen);

        ProtocolUtils.writeVarInt(out, bodyLen);
        out.writeBytes(buf);
    }

    private int varIntSize(int paramInt) {
        if ((paramInt & 0xFFFFFF80) == 0)
            return 1;
        if ((paramInt & 0xFFFFC000) == 0)
            return 2;
        if ((paramInt & 0xFFE00000) == 0)
            return 3;
        if ((paramInt & 0xF0000000) == 0)
            return 4;
        return 5;
    }
}
