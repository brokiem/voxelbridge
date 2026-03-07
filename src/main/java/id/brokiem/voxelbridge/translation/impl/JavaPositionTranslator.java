package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundPositionPacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundPositionLookPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceMovePlayerPacket;
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
        // Java 0x08 Y = eyes; LCE expects y = feet, yView = eyes
        double eyeY = input.getY();
        double feetY = eyeY - 1.62;

        LceMovePlayerPacket lce = new LceMovePlayerPacket();
        lce.setX(input.getX());
        lce.setY(feetY);
        lce.setYView(eyeY);
        lce.setZ(input.getZ());
        lce.setYRot(input.getYaw());
        lce.setXRot(input.getPitch());
        lce.setFlags((byte) (input.isOnGround() ? 1 : 0));

        JavaServerboundPositionLookPacket response = new JavaServerboundPositionLookPacket();
        response.setX(input.getX());
        response.setStanceY(eyeY);
        response.setFeetY(feetY);
        response.setZ(input.getZ());
        response.setYaw(input.getYaw());
        response.setPitch(input.getPitch());
        response.setOnGround(input.isOnGround());

        return TranslationResult.toClientAndServer(lce, response);
    }
}

