package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundUpdateTimePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetTimePacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

/**
 * Translates:
 * Java Packet: JavaClientboundUpdateTimePacket
 * LCE Packet: LceSetTimePacket
 * <p>
 * Converts the Java world-time update into the LCE time packet so the console
 * client stays synchronized with the server's current game time.
 */
public class JavaUpdateTimeTranslator implements ClientboundTranslator<JavaClientboundUpdateTimePacket> {
    @Override
    public TranslationResult translate(JavaClientboundUpdateTimePacket input, Session session) {
        LceSetTimePacket lce = new LceSetTimePacket();
        lce.setGameTime(input.getWorldAge());
        lce.setDayTime(input.getTimeOfDay());
        return TranslationResult.toClient(lce);
    }
}

