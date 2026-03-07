package id.brokiem.voxelbridge.util.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;

import java.util.zip.DataFormatException;

public final class ZlibUtil {

    // Reuse Netty's pooled allocator — avoids GC pressure on large payloads
    private static final ByteBufAllocator ALLOC = PooledByteBufAllocator.DEFAULT;

    /**
     * Decompresses zlib-wrapped deflate data.
     *
     * @throws DataFormatException if the input is not valid zlib data
     */
    public static byte[] decompress(byte[] data) throws DataFormatException {
        // ZlibCodecFactory picks JNI (netty-tcnative / zlib-native) when available,
        // falling back to the JDK Inflater — same format, faster path when native is present.
        EmbeddedChannel channel = new EmbeddedChannel(
                ZlibCodecFactory.newZlibDecoder(ZlibWrapper.ZLIB)
        );

        ByteBuf output = ALLOC.buffer(data.length * 4);
        try {
            ByteBuf input = ALLOC.buffer(data.length);
            input.writeBytes(data);

            // writeInbound triggers the decoder; finish() flushes any remaining state
            boolean written = channel.writeInbound(input);
            channel.finish();

            if (!written && channel.inboundMessages().isEmpty()) {
                throw new DataFormatException("Decompression produced no output");
            }

            ByteBuf chunk;
            while ((chunk = channel.readInbound()) != null) {
                try {
                    output.writeBytes(chunk);
                } finally {
                    chunk.release();
                }
            }

            byte[] result = new byte[output.readableBytes()];
            output.readBytes(result);
            return result;

        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            if (cause instanceof DataFormatException dfe) {
                throw dfe;
            }
            DataFormatException ex = new DataFormatException("Decompression failed: " + cause.getMessage());
            ex.initCause(cause);
            throw ex;
        } finally {
            output.release();
            channel.close();
        }
    }

    /**
     * Compresses data using zlib (deflate with zlib header/trailer).
     */
    public static byte[] compress(byte[] data) {
        EmbeddedChannel channel = new EmbeddedChannel(
                ZlibCodecFactory.newZlibEncoder(ZlibWrapper.ZLIB, 6, 15, 8)
        );

        ByteBuf output = ALLOC.buffer(data.length);
        try {
            ByteBuf input = ALLOC.buffer(data.length);
            input.writeBytes(data);

            // writeOutbound triggers the encoder
            channel.writeOutbound(input);
            // finish() causes the encoder to emit the zlib trailer
            channel.finish();

            ByteBuf chunk;
            while ((chunk = channel.readOutbound()) != null) {
                try {
                    output.writeBytes(chunk);
                } finally {
                    chunk.release();
                }
            }

            byte[] result = new byte[output.readableBytes()];
            output.readBytes(result);
            return result;

        } finally {
            output.release();
            channel.close();
        }
    }
}