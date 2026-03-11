package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundMultiBlockChangePacket implements Packet {
    private int chunkX;
    private int chunkZ;
    private short recordCount;
    private int[] records;

    @Override
    public int getId() {
        return 0x22;
    }

    @Override
    public void read(ByteBuf buf) {
        this.chunkX = buf.readInt();
        this.chunkZ = buf.readInt();
        this.recordCount = buf.readShort();
        
        int count = this.recordCount & 0xFFFF;
        buf.readInt(); // Java 1.7 sends a Data Size int before the byte array
        this.records = new int[count];
        for (int i = 0; i < count; i++) {
            this.records[i] = buf.readInt();
        }
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeInt(chunkX);
        buf.writeInt(chunkZ);
        buf.writeShort(recordCount);
        
        int count = recordCount & 0xFFFF;
        buf.writeInt(count * 4);
        for (int i = 0; i < count; i++) {
            buf.writeInt(records[i]);
        }
    }
}
