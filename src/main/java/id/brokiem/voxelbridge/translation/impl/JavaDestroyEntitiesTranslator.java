package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundDestroyEntitiesPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceRemoveEntitiesPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaDestroyEntitiesTranslator implements ClientboundTranslator<JavaClientboundDestroyEntitiesPacket> {
    @Override
    public TranslationResult translate(JavaClientboundDestroyEntitiesPacket input, Session session) {
        LceRemoveEntitiesPacket packet = new LceRemoveEntitiesPacket();
        packet.setEntityIds(input.getEntityIds());
        return TranslationResult.toClient(packet);
    }
}
