package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundEntityPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceMoveEntityPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaEntityTranslator implements ClientboundTranslator<JavaClientboundEntityPacket> {
    @Override
    public TranslationResult translate(JavaClientboundEntityPacket input, Session session) {
        LceMoveEntityPacket packet = new LceMoveEntityPacket();
        packet.setEntityId(input.getEntityId());
        return TranslationResult.toClient(packet);
    }
}
