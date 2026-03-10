package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.types.SlotData;
import id.brokiem.voxelbridge.protocol.lce.packets.LceContainerSetSlotPacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundSetSlotPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaSetSlotTranslator implements ClientboundTranslator<JavaClientboundSetSlotPacket> {
    @Override
    public TranslationResult translate(JavaClientboundSetSlotPacket input, Session session) {
        LceContainerSetSlotPacket lce = new LceContainerSetSlotPacket();
        lce.setContainerId(input.getWindowId());
        lce.setSlot(input.getSlot());
        SlotData item = input.getItem();
        lce.setItem(item != null ? item : SlotData.empty());
        return TranslationResult.toClient(lce);
    }
}
