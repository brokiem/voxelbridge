package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundFlyingPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceMovePlayerPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceMovePlayerTranslator implements ServerboundTranslator<LceMovePlayerPacket> {

    @Override
    public TranslationResult translate(LceMovePlayerPacket input, Session session) {
        JavaServerboundFlyingPacket flyingPacket = new JavaServerboundFlyingPacket();
        flyingPacket.setOnGround((input.getFlags() & 0x01) != 0);

        return TranslationResult.toServer(flyingPacket);
    }
}
