package id.brokiem.voxelbridge.protocol.java.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

public class JavaFrameDecoder extends ByteToMessageDecoder {
    private static final int MAX_VARINT_BYTES = 5;
    private static final int MAX_FRAME_SIZE = 4 * 1024 * 1024;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int frameLength = 0;
        int readerIndex = in.readerIndex();

        for (int numRead = 0; numRead < MAX_VARINT_BYTES; numRead++) {
            if (in.readableBytes() < numRead + 1) {
                return;
            }

            int currentByte = in.getUnsignedByte(readerIndex + numRead);
            frameLength |= (currentByte & 0x7F) << (numRead * 7);

            if ((currentByte & 0x80) == 0) {
                if (frameLength < 0) {
                    throw new CorruptedFrameException("Negative frame length: " + frameLength);
                }
                if (frameLength > MAX_FRAME_SIZE) {
                    throw new TooLongFrameException("Java frame exceeds " + MAX_FRAME_SIZE + " bytes: " + frameLength);
                }
                if (in.readableBytes() < numRead + 1 + frameLength) {
                    return;
                }

                in.skipBytes(numRead + 1);
                out.add(in.readRetainedSlice(frameLength));
                return;
            }
        }

        throw new CorruptedFrameException("VarInt length exceeds 5 bytes");
    }
}
