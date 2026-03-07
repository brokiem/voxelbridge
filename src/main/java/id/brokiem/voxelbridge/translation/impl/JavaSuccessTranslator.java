package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.login.JavaClientboundSuccessPacket;
import id.brokiem.voxelbridge.session.ConnectionState;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Translates:
 * Java Packet: JavaClientboundSuccessPacket
 * LCE Packet: None
 * <p>
 * Consumes the Java login success packet, advances the session into the play
 * state, and emits no direct LCE packet because gameplay setup continues with
 * later play-state packets.
 */
@Slf4j
public class JavaSuccessTranslator implements ClientboundTranslator<JavaClientboundSuccessPacket> {
    @Override
    public TranslationResult translate(JavaClientboundSuccessPacket input, Session session) {
        log.debug("Dropping Java success packet for user={}, uuid={}", input.getUsername(), input.getUuid());
        return new TranslationResult(List.of(), List.of(), ConnectionState.PLAY, null);
    }
}

