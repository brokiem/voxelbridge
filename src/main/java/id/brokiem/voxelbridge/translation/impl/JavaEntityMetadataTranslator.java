package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundEntityMetadataPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetEntityDataPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaEntityMetadataTranslator implements ClientboundTranslator<JavaClientboundEntityMetadataPacket> {
    @Override
    public TranslationResult translate(JavaClientboundEntityMetadataPacket input, Session session) {
        LceSetEntityDataPacket packet = new LceSetEntityDataPacket();
        packet.setEntityId(input.getEntityId());
        packet.setMetadata(input.getMetadata());
        return TranslationResult.toClient(packet);
    }
}
