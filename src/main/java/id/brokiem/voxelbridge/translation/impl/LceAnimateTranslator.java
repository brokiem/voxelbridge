package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundArmAnimationPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceAnimatePacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceAnimateTranslator implements ServerboundTranslator<LceAnimatePacket> {
    @Override
    public TranslationResult translate(LceAnimatePacket input, Session session) {
        JavaServerboundArmAnimationPacket packet = new JavaServerboundArmAnimationPacket();
        packet.setEntityId(input.getId());
        packet.setAnimation((byte) input.getAction());
        return TranslationResult.toServer(packet);
    }
}

