package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundHeldItemSlotPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetCarriedItemPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceSetCarriedItemTranslator implements ServerboundTranslator<LceSetCarriedItemPacket> {
    @Override
    public TranslationResult translate(LceSetCarriedItemPacket input, Session session) {
        JavaServerboundHeldItemSlotPacket packet = new JavaServerboundHeldItemSlotPacket();
        packet.setSlot(input.getSlot());
        return TranslationResult.toServer(packet);
    }
}

