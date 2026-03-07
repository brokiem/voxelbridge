package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundKickDisconnectPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceDisconnectPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import id.brokiem.voxelbridge.translation.mapper.DisconnectReasonMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Translates:
 * Java Packet: JavaClientboundKickDisconnectPacket
 * LCE Packet: LceDisconnectPacket
 * <p>
 * Converts an in-game Java disconnect into the LCE disconnect packet so the
 * console client is removed from play with the server-provided reason.
 */
@Slf4j
public class JavaKickDisconnectTranslator implements ClientboundTranslator<JavaClientboundKickDisconnectPacket> {
    @Override
    public TranslationResult translate(JavaClientboundKickDisconnectPacket input, Session session) {
        log.info("Java server disconnected client: {}", input.getReason());
        LceDisconnectPacket lce = new LceDisconnectPacket();
        lce.setReason(DisconnectReasonMapper.mapJavaToLce(input.getReason()));
        return TranslationResult.toClient(lce);
    }
}

