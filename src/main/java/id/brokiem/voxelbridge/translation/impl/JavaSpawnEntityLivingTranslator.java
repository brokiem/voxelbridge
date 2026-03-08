package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundSpawnEntityLivingPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceAddMobPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaSpawnEntityLivingTranslator implements ClientboundTranslator<JavaClientboundSpawnEntityLivingPacket> {

    @Override
    public TranslationResult translate(JavaClientboundSpawnEntityLivingPacket input, Session session) {
        LceAddMobPacket packet = new LceAddMobPacket();
        packet.setEntityId((short)input.getEntityId());
        packet.setType((byte) input.getType());
        packet.setX(input.getX());
        packet.setY(input.getY());
        packet.setZ(input.getZ());
        packet.setYRot(input.getYaw());
        packet.setXRot(input.getPitch());
        packet.setYHeadRot(input.getHeadPitch());
        packet.setXd(input.getVelocityX());
        packet.setYd(input.getVelocityY());
        packet.setZd(input.getVelocityZ());
        packet.setEntityData(input.getMetadata());

        return TranslationResult.toClient(packet);
    }
}
