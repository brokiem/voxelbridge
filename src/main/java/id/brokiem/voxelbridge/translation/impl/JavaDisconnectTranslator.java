package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.login.JavaClientboundDisconnectPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceDisconnectPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import id.brokiem.voxelbridge.translation.mapper.DisconnectReasonMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Translates:
 * Java Packet: JavaClientboundDisconnectPacket
 * LCE Packet: LceDisconnectPacket
 * <p>
 * Converts a login-phase Java disconnect into the LCE disconnect packet so the
 * client receives the rejection reason before gameplay begins.
 */
@Slf4j
public class JavaDisconnectTranslator implements ClientboundTranslator<JavaClientboundDisconnectPacket> {
    @Override
    public TranslationResult translate(JavaClientboundDisconnectPacket input, Session session) {
        log.info("Java server rejected login: {}", input.getReason());
        LceDisconnectPacket lce = new LceDisconnectPacket();
        lce.setReason(DisconnectReasonMapper.mapJavaToLce(input.getReason()));
        return TranslationResult.toClient(lce);
    }
}

