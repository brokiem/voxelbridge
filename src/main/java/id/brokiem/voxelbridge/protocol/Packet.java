package id.brokiem.voxelbridge.protocol;

import io.netty.buffer.ByteBuf;

public interface Packet {
    int getId();

    void read(ByteBuf buf);

    void write(ByteBuf buf);
}
