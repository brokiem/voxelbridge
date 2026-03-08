package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundEntityMoveLookPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceMoveEntityPositionRotationPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaEntityMoveLookTranslator implements ClientboundTranslator<JavaClientboundEntityMoveLookPacket> {
    @Override
    public TranslationResult translate(JavaClientboundEntityMoveLookPacket input, Session session) {
        LceMoveEntityPositionRotationPacket packet = new LceMoveEntityPositionRotationPacket();
        packet.setEntityId(input.getEntityId());
        packet.setX(input.getDX());
        packet.setY(input.getDY());
        packet.setZ(input.getDZ());
        packet.setYaw(input.getYaw());
        packet.setPitch(input.getPitch());
        return TranslationResult.toClient(packet);
    }
}
