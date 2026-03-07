package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceSetTimePacket implements Packet {
    private long gameTime;
    private long dayTime;

    @Override
    public int getId() {
        return 0x04;
    }

    @Override
    public void read(ByteBuf buf) {
        this.gameTime = buf.readLong();
        this.dayTime = buf.readLong();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeLong(gameTime);
        buf.writeLong(dayTime);
    }
}
