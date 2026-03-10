package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.lce.packets.LceContainerSetDataPacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundCraftProgressBarPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaCraftProgressBarTranslator implements ClientboundTranslator<JavaClientboundCraftProgressBarPacket> {
    @Override
    public TranslationResult translate(JavaClientboundCraftProgressBarPacket input, Session session) {
        LceContainerSetDataPacket lce = new LceContainerSetDataPacket();
        lce.setContainerId((byte) (input.getWindowId() & 0xFF));
        lce.setProperty(input.getProperty());
        lce.setValue(input.getValue());
        return TranslationResult.toClient(lce);
    }
}
