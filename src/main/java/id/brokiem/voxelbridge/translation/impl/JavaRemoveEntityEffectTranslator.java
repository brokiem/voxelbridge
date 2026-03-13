package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundRemoveEntityEffectPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceRemoveMobEffectPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaRemoveEntityEffectTranslator implements ClientboundTranslator<JavaClientboundRemoveEntityEffectPacket> {
    @Override
    public TranslationResult translate(JavaClientboundRemoveEntityEffectPacket input, Session session) {
        LceRemoveMobEffectPacket lce = new LceRemoveMobEffectPacket();
        lce.setEntityId(input.getEntityId());
        lce.setEffectId(input.getEffectId());
        return TranslationResult.toClient(lce);
    }
}
