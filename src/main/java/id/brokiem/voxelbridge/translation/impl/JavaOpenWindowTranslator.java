package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.lce.packets.LceContainerOpenPacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundOpenWindowPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaOpenWindowTranslator implements ClientboundTranslator<JavaClientboundOpenWindowPacket> {
    @Override
    public TranslationResult translate(JavaClientboundOpenWindowPacket input, Session session) {
        LceContainerOpenPacket lce = new LceContainerOpenPacket();
        lce.setContainerId((byte) (input.getWindowId() & 0xFF));
        lce.setType((byte) (input.getInventoryType() & 0xFF));
        lce.setTitle(input.getWindowTitle() != null ? input.getWindowTitle() : "");
        lce.setSlotCount((byte) (input.getSlotCount() & 0xFF));
        lce.setCustomName(input.isUseProvidedTitle());
        lce.setEntityId(input.getEntityId() != null ? input.getEntityId() : 0);
        return TranslationResult.toClient(lce);
    }
}
