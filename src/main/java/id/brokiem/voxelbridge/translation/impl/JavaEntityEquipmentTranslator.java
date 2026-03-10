package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.types.SlotData;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetEquippedItemPacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundEntityEquipmentPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaEntityEquipmentTranslator implements ClientboundTranslator<JavaClientboundEntityEquipmentPacket> {
    @Override
    public TranslationResult translate(JavaClientboundEntityEquipmentPacket input, Session session) {
        LceSetEquippedItemPacket lce = new LceSetEquippedItemPacket();
        lce.setEntityId(input.getEntityId());
        lce.setSlot(input.getSlot());
        SlotData item = input.getItem();
        lce.setItem(item != null ? item : SlotData.empty());
        return TranslationResult.toClient(lce);
    }
}
