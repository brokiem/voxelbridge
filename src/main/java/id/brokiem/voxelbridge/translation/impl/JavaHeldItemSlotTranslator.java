package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundHeldItemSlotPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetCarriedItemPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

/**
 * Translates:
 * Java Packet: JavaClientboundHeldItemSlotPacket
 * LCE Packet: LceSetCarriedItemPacket
 * <p>
 * Converts the Java held-item slot update into the LCE carried-item packet so
 * the console client switches to the same hotbar slot.
 */
public class JavaHeldItemSlotTranslator implements ClientboundTranslator<JavaClientboundHeldItemSlotPacket> {
    @Override
    public TranslationResult translate(JavaClientboundHeldItemSlotPacket input, Session session) {
        LceSetCarriedItemPacket lce = new LceSetCarriedItemPacket();
        lce.setSlot((short) (input.getSlot() & 0xFF));
        return TranslationResult.toClient(lce);
    }
}

