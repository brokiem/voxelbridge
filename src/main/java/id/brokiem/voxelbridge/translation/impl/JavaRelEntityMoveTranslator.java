package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundRelEntityMovePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceMoveEntityPositionPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaRelEntityMoveTranslator implements ClientboundTranslator<JavaClientboundRelEntityMovePacket> {
    @Override
    public TranslationResult translate(JavaClientboundRelEntityMovePacket input, Session session) {
        LceMoveEntityPositionPacket packet = new LceMoveEntityPositionPacket();
        packet.setEntityId(input.getEntityId());
        packet.setX(input.getDX());
        packet.setY(input.getDY());
        packet.setZ(input.getDZ());
        return TranslationResult.toClient(packet);
    }
}
