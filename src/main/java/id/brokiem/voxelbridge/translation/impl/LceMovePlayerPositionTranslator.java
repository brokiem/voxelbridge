package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundPositionPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceMovePlayerPositionPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceMovePlayerPositionTranslator implements ServerboundTranslator<LceMovePlayerPositionPacket> {

    @Override
    public TranslationResult translate(LceMovePlayerPositionPacket input, Session session) {
        boolean onGround = (input.getFlags() & 0x01) != 0;

        JavaServerboundPositionPacket positionPacket = new JavaServerboundPositionPacket();
        positionPacket.setX(input.getX());
        positionPacket.setStance(input.getY());
        positionPacket.setY(input.getYView());
        positionPacket.setZ(input.getZ());
        positionPacket.setOnGround(onGround);

        return TranslationResult.toServer(positionPacket);
    }
}
