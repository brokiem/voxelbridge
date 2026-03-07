package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundClientCommandPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceClientCommandPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceClientCommandTranslator implements ServerboundTranslator<LceClientCommandPacket> {

    @Override
    public TranslationResult translate(LceClientCommandPacket input, Session session) {
        int payload;

        if (input.getAction() == LceClientCommandPacket.LOGIN_COMPLETE) {
            payload = JavaServerboundClientCommandPacket.REQUEST_STATS;
        } else if (input.getAction() == LceClientCommandPacket.PERFORM_RESPAWN) {
            payload = JavaServerboundClientCommandPacket.PERFORM_RESPAWN;
        } else {
            return TranslationResult.empty();
        }

        JavaServerboundClientCommandPacket packet = new JavaServerboundClientCommandPacket();
        packet.setPayload(payload);

        return TranslationResult.toServer(packet);
    }
}
