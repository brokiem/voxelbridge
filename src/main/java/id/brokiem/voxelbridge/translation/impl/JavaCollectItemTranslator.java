package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundCollectItemPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceTakeItemEntityPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaCollectItemTranslator implements ClientboundTranslator<JavaClientboundCollectItemPacket> {
    @Override
    public TranslationResult translate(JavaClientboundCollectItemPacket input, Session session) {
        LceTakeItemEntityPacket packet = new LceTakeItemEntityPacket();
        packet.setItemId(input.getCollectedEntityId());
        packet.setPlayerId(input.getCollectorEntityId());
        return TranslationResult.toClient(packet);
    }
}
