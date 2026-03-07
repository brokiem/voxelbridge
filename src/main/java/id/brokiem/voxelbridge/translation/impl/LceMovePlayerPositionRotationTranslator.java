package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundPositionLookPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceMovePlayerPositionRotationPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceMovePlayerPositionRotationTranslator implements ServerboundTranslator<LceMovePlayerPositionRotationPacket> {

    @Override
    public TranslationResult translate(LceMovePlayerPositionRotationPacket input, Session session) {
        boolean onGround = (input.getFlags() & 0x01) != 0;

        JavaServerboundPositionLookPacket positionLookPacket = new JavaServerboundPositionLookPacket();
        positionLookPacket.setX(input.getX());
        positionLookPacket.setStance(input.getY());
        positionLookPacket.setY(input.getYView());
        positionLookPacket.setZ(input.getZ());
        positionLookPacket.setYaw(input.getYRot());
        positionLookPacket.setPitch(input.getXRot());
        positionLookPacket.setOnGround(onGround);

        return TranslationResult.toServer(positionLookPacket);
    }
}
