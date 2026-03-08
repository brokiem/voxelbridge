package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundUseEntityPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceInteractPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LceInteractTranslator implements ServerboundTranslator<LceInteractPacket> {

    @Override
    public TranslationResult translate(LceInteractPacket input, Session session) {
        byte action = switch (input.getAction()) {
            case LceInteractPacket.ATTACK -> JavaServerboundUseEntityPacket.LEFT_CLICK;
            case LceInteractPacket.INTERACT -> JavaServerboundUseEntityPacket.RIGHT_CLICK;
            default -> JavaServerboundUseEntityPacket.LEFT_CLICK;
        };

        JavaServerboundUseEntityPacket packet = new JavaServerboundUseEntityPacket();
        packet.setTarget(input.getTarget());
        packet.setMouse(action);
        return TranslationResult.toServer(packet);
    }
}
