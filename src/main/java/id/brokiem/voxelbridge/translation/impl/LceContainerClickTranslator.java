package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.types.SlotData;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundWindowClickPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceContainerClickPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceContainerClickTranslator implements ServerboundTranslator<LceContainerClickPacket> {
    @Override
    public TranslationResult translate(LceContainerClickPacket input, Session session) {
        JavaServerboundWindowClickPacket packet = new JavaServerboundWindowClickPacket();
        packet.setWindowId(input.getContainerId());
        packet.setSlot(input.getSlot());
        packet.setMouseButton(input.getMouseButton());
        packet.setAction(input.getAction());
        packet.setMode(input.getMode());
        SlotData item = input.getItem();
        packet.setItem(item != null ? item : SlotData.empty());
        return TranslationResult.toServer(packet);
    }
}
