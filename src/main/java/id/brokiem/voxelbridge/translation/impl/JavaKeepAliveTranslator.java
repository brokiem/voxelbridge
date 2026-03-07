package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundKeepAlivePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceKeepAlivePacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

/**
 * Translates:
 * Java Packet: JavaClientboundKeepAlivePacket
 * LCE Packet: LceKeepAlivePacket
 * <p>
 * Passes the Java keep-alive ID through to the LCE keep-alive packet so the
 * console client can maintain the server connection.
 */
public class JavaKeepAliveTranslator implements ClientboundTranslator<JavaClientboundKeepAlivePacket> {
    @Override
    public TranslationResult translate(JavaClientboundKeepAlivePacket input, Session session) {
        LceKeepAlivePacket lce = new LceKeepAlivePacket();
        lce.setKeepAliveId(input.getKeepAliveId());
        return TranslationResult.toClient(lce);
    }
}

