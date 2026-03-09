package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundSpawnEntityPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceAddEntityPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaSpawnEntityTranslator implements ClientboundTranslator<JavaClientboundSpawnEntityPacket> {
    @Override
    public TranslationResult translate(JavaClientboundSpawnEntityPacket input, Session session) {
        LceAddEntityPacket packet = new LceAddEntityPacket();
        packet.setId((short) input.getEntityId());
        packet.setType((byte) input.getType());
        packet.setX(input.getX());
        packet.setY(input.getY());
        packet.setZ(input.getZ());
        packet.setYRot(input.getYaw());
        packet.setXRot(input.getPitch());
        if (input.getData() != 0) {
            packet.setData(input.getData());
            packet.setXa(input.getVelocityX());
            packet.setYa(input.getVelocityY());
            packet.setZa(input.getVelocityZ());
        } else {
            packet.setData(-1);
        }
        
        return TranslationResult.toClient(packet);
    }
}
