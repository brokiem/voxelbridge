package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.lce.packets.LceDisconnectPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LcePreLoginPacket;
import id.brokiem.voxelbridge.server.ProxyConfig;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LcePreLoginTranslator implements ServerboundTranslator<LcePreLoginPacket> {
    private final ProxyConfig config;

    public LcePreLoginTranslator(ProxyConfig config) {
        this.config = config;
    }

    @Override
    public TranslationResult translate(LcePreLoginPacket input, Session session) {
        if (input.getNetcodeVersion() != config.getLceNetVersion()) {
            log.warn("Rejecting client: netcodeVersion={}, expected {}", input.getNetcodeVersion(), config.getLceNetVersion());
            LceDisconnectPacket disconnect = new LceDisconnectPacket();
            disconnect.setReason(LceDisconnectPacket.OUTDATED_CLIENT);
            return TranslationResult.toClient(disconnect);
        }

        session.setUsername(input.getLoginKey());

        LcePreLoginPacket response = new LcePreLoginPacket();
        response.setNetcodeVersion((short) config.getLceNetVersion());
        response.setLoginKey(input.getLoginKey());
        response.setFriendsOnlyBits(input.getFriendsOnlyBits());
        response.setUgcPlayersVersion(input.getUgcPlayersVersion());
        response.setDwPlayerCount(input.getDwPlayerCount());
        response.setPlayerXuids(input.getPlayerXuids());
        response.setSzUniqueSaveName(input.getSzUniqueSaveName());
        response.setServerSettings(input.getServerSettings());
        response.setHostIndex(input.getHostIndex());
        response.setTexturePackId(input.getTexturePackId());

        return TranslationResult.toClient(response);
    }
}
