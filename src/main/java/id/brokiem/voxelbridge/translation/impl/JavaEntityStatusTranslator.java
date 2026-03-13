package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundEntityStatusPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceEntityEventPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaEntityStatusTranslator implements ClientboundTranslator<JavaClientboundEntityStatusPacket> {
    @Override
    public TranslationResult translate(JavaClientboundEntityStatusPacket input, Session session) {
        LceEntityEventPacket lce = new LceEntityEventPacket();
        lce.setEntityId(input.getEntityId());
        lce.setEventId(input.getEntityStatus());
        return TranslationResult.toClient(lce);
    }
}
