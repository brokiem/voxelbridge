package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundKeepAlivePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceKeepAlivePacket;
import id.brokiem.voxelbridge.session.ConnectionState;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceKeepAliveTranslator implements ServerboundTranslator<LceKeepAlivePacket> {
    @Override
    public TranslationResult translate(LceKeepAlivePacket input, Session session) {
        if (!session.getState().equals(ConnectionState.PLAY)) {
            return TranslationResult.empty();
        }

        JavaServerboundKeepAlivePacket java = new JavaServerboundKeepAlivePacket();
        java.setKeepAliveId(input.getKeepAliveId());
        return TranslationResult.toServer(java);
    }
}
