package id.brokiem.voxelbridge.protocol.java.packets.play;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClientboundExperiencePacket implements Packet {
    private float experienceBar;
    private short level;
    private short totalExperience;

    @Override
    public int getId() {
        return 0x1F;
    }

    @Override
    public void read(ByteBuf buf) {
        this.experienceBar = buf.readFloat();
        this.level = buf.readShort();
        this.totalExperience = buf.readShort();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeFloat(experienceBar);
        buf.writeShort(level);
        buf.writeShort(totalExperience);
    }
}

