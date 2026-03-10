package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundCloseWindowPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceContainerClosePacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceContainerCloseTranslator implements ServerboundTranslator<LceContainerClosePacket> {
    @Override
    public TranslationResult translate(LceContainerClosePacket input, Session session) {
        JavaServerboundCloseWindowPacket packet = new JavaServerboundCloseWindowPacket();
        packet.setWindowId(input.getContainerId());
        return TranslationResult.toServer(packet);
    }
}
