package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.lce.packets.LceContainerClosePacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundCloseWindowPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaCloseWindowTranslator implements ClientboundTranslator<JavaClientboundCloseWindowPacket> {
    @Override
    public TranslationResult translate(JavaClientboundCloseWindowPacket input, Session session) {
        LceContainerClosePacket lce = new LceContainerClosePacket();
        lce.setContainerId((byte) (input.getWindowId() & 0xFF));
        return TranslationResult.toClient(lce);
    }
}
