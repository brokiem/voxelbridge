package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundBlockChangePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceTileUpdatePacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaBlockChangeTranslator implements ClientboundTranslator<JavaClientboundBlockChangePacket> {
    @Override
    public TranslationResult translate(JavaClientboundBlockChangePacket input, Session session) {
        int blockId = input.getBlockId();
        if (blockId <= 160 || (blockId >= 170 && blockId <= 173)) {
            blockId = 1;
        }

        LceTileUpdatePacket packet = new LceTileUpdatePacket();
        packet.setX(input.getX());
        packet.setY(input.getY());
        packet.setZ(input.getZ());
        packet.setBlock(blockId);
        packet.setData(input.getBlockMetadata() & 0x0F);
        packet.setLevelIdx(0); // Assuming Overworld for now, or we need to track dimension in Session
        return TranslationResult.toClient(packet);
    }
}
