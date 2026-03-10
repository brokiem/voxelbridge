package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.lce.packets.LceContainerAckPacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundTransactionPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaTransactionTranslator implements ClientboundTranslator<JavaClientboundTransactionPacket> {
    @Override
    public TranslationResult translate(JavaClientboundTransactionPacket input, Session session) {
        LceContainerAckPacket lce = new LceContainerAckPacket();
        lce.setContainerId((byte) (input.getWindowId() & 0xFF));
        lce.setAction(input.getAction());
        lce.setAccepted(input.isAccepted());
        return TranslationResult.toClient(lce);
    }
}
