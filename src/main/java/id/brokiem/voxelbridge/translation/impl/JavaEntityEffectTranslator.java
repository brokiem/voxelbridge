package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundEntityEffectPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceUpdateMobEffectPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaEntityEffectTranslator implements ClientboundTranslator<JavaClientboundEntityEffectPacket> {
    @Override
    public TranslationResult translate(JavaClientboundEntityEffectPacket input, Session session) {
        LceUpdateMobEffectPacket lce = new LceUpdateMobEffectPacket();
        lce.setEntityId(input.getEntityId());
        lce.setEffectId(input.getEffectId());
        lce.setAmplifier(input.getAmplifier());
        lce.setDuration(input.getDuration());
        return TranslationResult.toClient(lce);
    }
}
