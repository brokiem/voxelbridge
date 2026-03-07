package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundUpdateHealthPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetHealthPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

/**
 * Translates:
 * Java Packet: JavaClientboundUpdateHealthPacket
 * LCE Packet: LceSetHealthPacket
 * <p>
 * Converts the Java health update into the LCE health packet so the console
 * client reflects the same health, food, and saturation values.
 */
public class JavaUpdateHealthTranslator implements ClientboundTranslator<JavaClientboundUpdateHealthPacket> {
    @Override
    public TranslationResult translate(JavaClientboundUpdateHealthPacket input, Session session) {
        LceSetHealthPacket lce = new LceSetHealthPacket();
        lce.setHealth(input.getHealth());
        lce.setFood(input.getFood());
        lce.setSaturation(input.getSaturation());
        lce.setDamageSource((byte) 0);
        return TranslationResult.toClient(lce);
    }
}

