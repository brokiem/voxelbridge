package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundEntityTeleportPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceTeleportEntityPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaEntityTeleportTranslator implements ClientboundTranslator<JavaClientboundEntityTeleportPacket> {
    @Override
    public TranslationResult translate(JavaClientboundEntityTeleportPacket input, Session session) {
        LceTeleportEntityPacket packet = new LceTeleportEntityPacket();
        packet.setId((short) input.getEntityId());
        packet.setX(input.getX());
        packet.setY(input.getY());
        packet.setZ(input.getZ());
        packet.setYRot(input.getYaw());
        packet.setXRot(input.getPitch());
        return TranslationResult.toClient(packet);
    }
}

