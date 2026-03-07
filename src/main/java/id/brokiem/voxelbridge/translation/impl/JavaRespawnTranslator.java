package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundRespawnPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceRespawnPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class JavaRespawnTranslator implements ClientboundTranslator<JavaClientboundRespawnPacket> {

    @Override
    public TranslationResult translate(JavaClientboundRespawnPacket input, Session session) {
        LceRespawnPacket respawnPacket = new LceRespawnPacket();
        respawnPacket.setDimension((byte) input.getDimension());
        respawnPacket.setGameMode(input.getGameMode());
        respawnPacket.setMapHeight((short) 256);
        respawnPacket.setLevelType(input.getLevelType().isEmpty() ? "default" : input.getLevelType());
        respawnPacket.setSeed(0L);
        respawnPacket.setDifficulty(input.getDifficulty());
        respawnPacket.setNewSeaLevel(true);
        respawnPacket.setNewEntityId(session.getEntityId());
        respawnPacket.setXzSize(864);
        respawnPacket.setHellScale(3);

        return TranslationResult.toClient(respawnPacket);
    }
}
