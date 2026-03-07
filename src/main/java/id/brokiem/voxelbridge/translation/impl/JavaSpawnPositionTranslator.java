package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundSpawnPositionPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetSpawnPositionPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

/**
 * Translates:
 * Java Packet: JavaClientboundSpawnPositionPacket
 * LCE Packet: LceSetSpawnPositionPacket
 * <p>
 * Converts the Java world spawn position packet into the LCE spawn-position
 * packet so the console client tracks the same default respawn location.
 */
public class JavaSpawnPositionTranslator implements ClientboundTranslator<JavaClientboundSpawnPositionPacket> {
    @Override
    public TranslationResult translate(JavaClientboundSpawnPositionPacket input, Session session) {
        LceSetSpawnPositionPacket lce = new LceSetSpawnPositionPacket();
        lce.setX(input.getX());
        lce.setY(input.getY());
        lce.setZ(input.getZ());
        return TranslationResult.toClient(lce);
    }
}

