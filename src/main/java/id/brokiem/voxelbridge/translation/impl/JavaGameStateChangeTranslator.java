package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundGameStateChangePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceGameEventPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaGameStateChangeTranslator implements ClientboundTranslator<JavaClientboundGameStateChangePacket> {
    @Override
    public TranslationResult translate(JavaClientboundGameStateChangePacket input, Session session) {
        LceGameEventPacket lce = new LceGameEventPacket();
        lce.setEvent(input.getReason());
        lce.setParam((int) input.getGameMode());
        return TranslationResult.toClient(lce);
    }
}
