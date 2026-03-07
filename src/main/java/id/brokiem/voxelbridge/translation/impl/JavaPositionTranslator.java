package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundPositionPacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundPositionLookPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceMovePlayerPositionRotationPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import lombok.extern.slf4j.Slf4j;

/**
 * Translates:
 * Java Packet: JavaClientboundPositionPacket
 * LCE Packet: LceMovePlayerPacket
 * <p>
 * Converts the Java player position update into an LCE move packet and also
 * sends a Java position-look acknowledgement back to the server to confirm the
 * new position state.
 */
@Slf4j
public class JavaPositionTranslator implements ClientboundTranslator<JavaClientboundPositionPacket> {
    @Override
    public TranslationResult translate(JavaClientboundPositionPacket input, Session session) {
        // Java Y is the player feet level
        double feetY = input.getY();

        LceMovePlayerPositionRotationPacket lce = new LceMovePlayerPositionRotationPacket();
        lce.setX(input.getX());
        lce.setY(feetY);
        lce.setYView(feetY + 1.62);
        lce.setZ(input.getZ());
        lce.setYRot(input.getYaw());
        lce.setXRot(input.getPitch());
        lce.setFlags((byte) (input.isOnGround() ? 1 : 0));

        JavaServerboundPositionLookPacket response = new JavaServerboundPositionLookPacket();
        response.setX(input.getX());
        response.setStance(feetY);
        response.setY(feetY + 1.62);
        response.setZ(input.getZ());
        response.setYaw(input.getYaw());
        response.setPitch(input.getPitch());
        response.setOnGround(input.isOnGround());

        return TranslationResult.toClientAndServer(lce, response);
    }
}

