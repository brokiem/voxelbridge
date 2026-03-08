package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundEntityHeadLookPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceRotateHeadPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaEntityHeadLookTranslator implements ClientboundTranslator<JavaClientboundEntityHeadLookPacket> {
    @Override
    public TranslationResult translate(JavaClientboundEntityHeadLookPacket input, Session session) {
        LceRotateHeadPacket packet = new LceRotateHeadPacket();
        packet.setEntityId(input.getEntityId());
        packet.setHeadYaw(input.getHeadYaw());
        return TranslationResult.toClient(packet);
    }
}
