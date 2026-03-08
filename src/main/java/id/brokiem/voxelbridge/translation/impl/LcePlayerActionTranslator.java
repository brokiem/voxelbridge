package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundPlayerDiggingPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LcePlayerActionPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LcePlayerActionTranslator implements ServerboundTranslator<LcePlayerActionPacket> {
    @Override
    public TranslationResult translate(LcePlayerActionPacket input, Session session) {
        JavaServerboundPlayerDiggingPacket packet = new JavaServerboundPlayerDiggingPacket();
        packet.setStatus((byte) input.getAction());
        packet.setX(input.getX());
        packet.setY((short) input.getY());
        packet.setZ(input.getZ());
        packet.setFace((byte) input.getFace());
        return TranslationResult.toServer(packet);
    }
}
