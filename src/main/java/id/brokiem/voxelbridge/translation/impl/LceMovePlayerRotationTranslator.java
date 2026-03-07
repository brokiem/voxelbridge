package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundLookPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceMovePlayerRotationPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceMovePlayerRotationTranslator implements ServerboundTranslator<LceMovePlayerRotationPacket> {

    @Override
    public TranslationResult translate(LceMovePlayerRotationPacket input, Session session) {
        boolean onGround = (input.getFlags() & 0x01) != 0;

        JavaServerboundLookPacket lookPacket = new JavaServerboundLookPacket();
        lookPacket.setYaw(input.getYRot());
        lookPacket.setPitch(input.getXRot());
        lookPacket.setOnGround(onGround);

        return TranslationResult.toServer(lookPacket);
    }
}
