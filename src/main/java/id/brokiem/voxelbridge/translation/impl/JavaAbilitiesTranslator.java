package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundAbilitiesPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LcePlayerAbilitiesPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

/**
 * Translates:
 * Java Packet: JavaClientboundAbilitiesPacket
 * LCE Packet: LcePlayerAbilitiesPacket
 * <p>
 * Converts the Java Edition abilities packet into the Legacy Console Edition
 * abilities packet so the LCE client receives updated permission flags and
 * movement speed values.
 */
public class JavaAbilitiesTranslator implements ClientboundTranslator<JavaClientboundAbilitiesPacket> {
    @Override
    public TranslationResult translate(JavaClientboundAbilitiesPacket input, Session session) {
        LcePlayerAbilitiesPacket lce = new LcePlayerAbilitiesPacket();
        lce.setBitfield(input.getFlags());
        lce.setFlyingSpeed(input.getFlyingSpeed());
        lce.setWalkingSpeed(input.getWalkingSpeed());
        return TranslationResult.toClient(lce);
    }
}

