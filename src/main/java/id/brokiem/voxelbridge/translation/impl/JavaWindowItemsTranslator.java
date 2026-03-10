package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.lce.packets.LceContainerSetContentPacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundWindowItemsPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

import java.util.ArrayList;

public class JavaWindowItemsTranslator implements ClientboundTranslator<JavaClientboundWindowItemsPacket> {
    @Override
    public TranslationResult translate(JavaClientboundWindowItemsPacket input, Session session) {
        LceContainerSetContentPacket lce = new LceContainerSetContentPacket();
        lce.setContainerId((byte) (input.getWindowId() & 0xFF));
        lce.setItems(input.getItems() != null ? new ArrayList<>(input.getItems()) : new ArrayList<>());
        return TranslationResult.toClient(lce);
    }
}
