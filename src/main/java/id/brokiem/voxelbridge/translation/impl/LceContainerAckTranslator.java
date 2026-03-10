package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundTransactionPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceContainerAckPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceContainerAckTranslator implements ServerboundTranslator<LceContainerAckPacket> {
    @Override
    public TranslationResult translate(LceContainerAckPacket input, Session session) {
        JavaServerboundTransactionPacket packet = new JavaServerboundTransactionPacket();
        packet.setWindowId(input.getContainerId());
        packet.setAction(input.getAction());
        packet.setAccepted(input.isAccepted());
        return TranslationResult.toServer(packet);
    }
}
