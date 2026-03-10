package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.types.SlotData;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundSetCreativeSlotPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetCreativeModeSlotPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceSetCreativeModeSlotTranslator implements ServerboundTranslator<LceSetCreativeModeSlotPacket> {
    @Override
    public TranslationResult translate(LceSetCreativeModeSlotPacket input, Session session) {
        JavaServerboundSetCreativeSlotPacket packet = new JavaServerboundSetCreativeSlotPacket();
        packet.setSlot(input.getSlot());
        SlotData item = input.getItem();
        packet.setItem(item != null ? item : SlotData.empty());
        return TranslationResult.toServer(packet);
    }
}
