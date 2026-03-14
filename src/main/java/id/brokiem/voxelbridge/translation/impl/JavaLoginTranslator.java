package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundLoginPacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundClientSettingsPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceLoginPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceSetTimePacket;
import id.brokiem.voxelbridge.server.ProxyConfig;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import id.brokiem.voxelbridge.translation.TranslationTarget;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Translates:
 * Java Packet: JavaClientboundLoginPacket
 * LCE Packet: LceLoginPacket, LceSetTimePacket
 * <p>
 * Converts the Java play-state login packet into the LCE login packet and also
 * emits an initial LCE time packet so the console client can enter the world
 * with synchronized session metadata and time state.
 */
@Slf4j
public class JavaLoginTranslator implements ClientboundTranslator<JavaClientboundLoginPacket> {
    private final ProxyConfig config;

    public JavaLoginTranslator(ProxyConfig config) {
        this.config = config;
    }

    @Override
    public TranslationResult translate(JavaClientboundLoginPacket input, Session session) {
        session.setEntityId(input.getEntityId());

        LceLoginPacket lce = new LceLoginPacket();
        lce.setClientVersion(input.getEntityId());
        lce.setUserName(session.getUsername() != null ? session.getUsername() : "Player");
        lce.setLevelTypeName(input.getLevelType());
        lce.setSeed(0);
        lce.setGameType(input.getGameMode() & 0x7);
        lce.setDimension(input.getDimension());
        lce.setMapHeight((byte) 255);
        lce.setMaxPlayers((byte) input.getMaxPlayers());
        lce.setOfflineXuid(0);
        lce.setOnlineXuid(0);
        lce.setFriendsOnlyUGC(false);
        lce.setUgcPlayersVersion(0);
        lce.setDifficulty((byte) input.getDifficulty());
        lce.setMultiplayerInstanceId(0);
        lce.setPlayerIndex((byte) 0);
        lce.setPlayerSkinId(0);
        lce.setPlayerCapeId(0);
        lce.setGuest(false);
        lce.setNewSeaLevel(true);
        lce.setUiGamePrivileges(0);
        lce.setXzSize((short) config.getLevelMaxWidth());
        lce.setHellScale((byte) config.getHellLevelMaxScale());

        session.setLceReady(true);

        // Emit synthetic SetTime (gameTime=0, dayTime=6000 noon) so LCE client has time
        // even if the Java server sends Update Time late or in a different order
        LceSetTimePacket setTime = new LceSetTimePacket();
        setTime.setGameTime(0);
        setTime.setDayTime(6000);

        JavaServerboundClientSettingsPacket clientSettings = new JavaServerboundClientSettingsPacket();
        clientSettings.setLocale("en_US");
        clientSettings.setViewDistance((byte) 8);
        clientSettings.setChatFlags((byte) 0); // Enabled
        clientSettings.setChatColors(false);
        clientSettings.setDifficulty(input.getDifficulty());
        clientSettings.setShowCape(true);

        return new TranslationResult(List.of(lce, setTime), List.of(clientSettings), null, TranslationTarget.CLIENT);
    }
}

