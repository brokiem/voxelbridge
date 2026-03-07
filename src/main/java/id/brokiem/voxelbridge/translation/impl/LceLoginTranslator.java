package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.handshake.JavaServerboundSetProtocolPacket;
import id.brokiem.voxelbridge.protocol.java.packets.login.JavaServerboundLoginStartPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceDisconnectPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceLoginPacket;
import id.brokiem.voxelbridge.server.ProxyConfig;
import id.brokiem.voxelbridge.session.ConnectionState;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class LceLoginTranslator implements ServerboundTranslator<LceLoginPacket> {
    private final ProxyConfig config;

    public LceLoginTranslator(ProxyConfig config) {
        this.config = config;
    }

    @Override
    public TranslationResult translate(LceLoginPacket input, Session session) {
        if (input.getClientVersion() != config.getLceProtocolVersion()) {
            log.warn("Rejecting client {}: clientVersion={}, expected {}", input.getUserName(), input.getClientVersion(), config.getLceProtocolVersion());
            LceDisconnectPacket disconnect = new LceDisconnectPacket();
            disconnect.setReason(input.getClientVersion() > config.getLceProtocolVersion()
                    ? LceDisconnectPacket.OUTDATED_SERVER
                    : LceDisconnectPacket.OUTDATED_CLIENT);
            return TranslationResult.toClient(disconnect);
        }

        session.setUsername(input.getUserName());
        log.info("LCE Client {} initiated login.", input.getUserName());

        JavaServerboundSetProtocolPacket handshake = new JavaServerboundSetProtocolPacket();
        handshake.setProtocolVersion(config.getJavaProtocolVersion());
        handshake.setServerAddress(config.getTargetHost());
        handshake.setServerPort(config.getTargetPort());
        handshake.setNextState(2);

        JavaServerboundLoginStartPacket loginStart = new JavaServerboundLoginStartPacket();
        loginStart.setUsername(input.getUserName());

        return TranslationResult.toServer(List.of(handshake, loginStart), ConnectionState.LOGIN);
    }
}
