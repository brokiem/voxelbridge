package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundAnimationPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceAnimatePacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaAnimationTranslator implements ClientboundTranslator<JavaClientboundAnimationPacket> {
    @Override
    public TranslationResult translate(JavaClientboundAnimationPacket input, Session session) {
        int lceAction = mapAnimationId(input.getAnimation());
        if (lceAction == -1) {
            return TranslationResult.empty();
        }

        LceAnimatePacket lce = new LceAnimatePacket();
        lce.setId(input.getEntityId());
        lce.setAction(lceAction);
        return TranslationResult.toClient(lce);
    }

    private int mapAnimationId(int javaAnimation) {
        return switch (javaAnimation) {
            case 0 -> LceAnimatePacket.SWING;
            case 1 -> LceAnimatePacket.HURT;
            case 2 -> LceAnimatePacket.WAKE_UP;
            case 3 -> LceAnimatePacket.EAT;
            case 4 -> LceAnimatePacket.CRITICAL_HIT;
            case 5 -> LceAnimatePacket.MAGIC_CRITICAL_HIT;
            default -> -1;
        };
    }
}
