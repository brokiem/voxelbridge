package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundBlockPlacePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceUseItemPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceUseItemTranslator implements ServerboundTranslator<LceUseItemPacket> {
    @Override
    public TranslationResult translate(LceUseItemPacket input, Session session) {
        JavaServerboundBlockPlacePacket packet = new JavaServerboundBlockPlacePacket();
        packet.setX(input.getX());
        packet.setY(input.getY());
        packet.setZ(input.getZ());
        packet.setDirection(input.getFace());
        packet.setHeldItem(input.getItemId());
        packet.setItemId(input.getItemId());
        packet.setItemCount(input.getItemCount());
        packet.setItemDamage(input.getItemDamage());
        packet.setNbtData(input.getNbtData());
        packet.setCursorX((byte) (input.getClickX() * 16));
        packet.setCursorY((byte) (input.getClickY() * 16));
        packet.setCursorZ((byte) (input.getClickZ() * 16));
        return TranslationResult.toServer(packet);
    }
}
