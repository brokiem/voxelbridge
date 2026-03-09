package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundExperiencePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetExperiencePacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaExperienceTranslator implements ClientboundTranslator<JavaClientboundExperiencePacket> {
    @Override
    public TranslationResult translate(JavaClientboundExperiencePacket packet, Session session) {
        LceSetExperiencePacket lcePacket = new LceSetExperiencePacket();
        lcePacket.setExperienceProgress(packet.getExperienceBar());
        lcePacket.setExperienceLevel(packet.getLevel());
        lcePacket.setTotalExperience(packet.getTotalExperience());
        return TranslationResult.toClient(lcePacket);
    }
}
