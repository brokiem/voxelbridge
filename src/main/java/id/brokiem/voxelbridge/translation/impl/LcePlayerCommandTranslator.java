package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundEntityActionPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LcePlayerCommandPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LcePlayerCommandTranslator implements ServerboundTranslator<LcePlayerCommandPacket> {
    @Override
    public TranslationResult translate(LcePlayerCommandPacket input, Session session) {
        int javaAction = mapAction(input.getAction());
        if (javaAction == -1) {
            log.debug("Dropping unmapped LCE PlayerCommand action: {}", input.getAction());
            return TranslationResult.empty();
        }

        JavaServerboundEntityActionPacket packet = new JavaServerboundEntityActionPacket();
        packet.setEntityId(input.getEntityId());
        packet.setActionId((byte) javaAction);
        packet.setJumpBoost(input.getData());
        return TranslationResult.toServer(packet);
    }

    private int mapAction(int lceAction) {
        return switch (lceAction) {
            case LcePlayerCommandPacket.START_SNEAKING -> 1;
            case LcePlayerCommandPacket.STOP_SNEAKING -> 2;
            case LcePlayerCommandPacket.STOP_SLEEPING -> 3;
            case LcePlayerCommandPacket.START_SPRINTING -> 4;
            case LcePlayerCommandPacket.STOP_SPRINTING -> 5;
            case LcePlayerCommandPacket.RIDING_JUMP -> 6;
            case LcePlayerCommandPacket.OPEN_INVENTORY -> 7;
            default -> -1; // IDLE_ANIM and unknown actions
        };
    }
}
