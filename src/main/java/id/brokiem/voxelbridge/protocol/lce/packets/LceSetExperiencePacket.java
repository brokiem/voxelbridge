package id.brokiem.voxelbridge.protocol.lce.packets;

import id.brokiem.voxelbridge.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LceSetExperiencePacket implements Packet {
    private float experienceProgress;
    private short experienceLevel;
    private short totalExperience;

    @Override
    public int getId() {
        return 0x2B;
    }

    @Override
    public void read(ByteBuf buf) {
        this.experienceProgress = buf.readFloat();
        this.experienceLevel = buf.readShort();
        this.totalExperience = buf.readShort();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeFloat(experienceProgress);
        buf.writeShort(experienceLevel);
        buf.writeShort(totalExperience);
    }
}
