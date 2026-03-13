package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundEntityVelocityPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetEntityMotionPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaEntityVelocityTranslator implements ClientboundTranslator<JavaClientboundEntityVelocityPacket> {
    @Override
    public TranslationResult translate(JavaClientboundEntityVelocityPacket input, Session session) {
        LceSetEntityMotionPacket lce = new LceSetEntityMotionPacket();
        lce.setEntityId(input.getEntityId());
        lce.setXa(input.getVelocityX());
        lce.setYa(input.getVelocityY());
        lce.setZa(input.getVelocityZ());
        return TranslationResult.toClient(lce);
    }
}
