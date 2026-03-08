package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundEntityLookPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceMoveEntityRotationPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaEntityLookTranslator implements ClientboundTranslator<JavaClientboundEntityLookPacket> {
    @Override
    public TranslationResult translate(JavaClientboundEntityLookPacket input, Session session) {
        LceMoveEntityRotationPacket packet = new LceMoveEntityRotationPacket();
        packet.setEntityId(input.getEntityId());
        packet.setYaw(input.getYaw());
        packet.setPitch(input.getPitch());
        return TranslationResult.toClient(packet);
    }
}
