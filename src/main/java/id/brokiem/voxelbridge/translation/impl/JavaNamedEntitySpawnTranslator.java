package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundNamedEntitySpawn;
import id.brokiem.voxelbridge.protocol.lce.packets.LceAddPlayerPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaNamedEntitySpawnTranslator implements ClientboundTranslator<JavaClientboundNamedEntitySpawn> {

    @Override
    public TranslationResult translate(JavaClientboundNamedEntitySpawn input, Session session) {
        LceAddPlayerPacket packet = new LceAddPlayerPacket();
        packet.setEntityId(input.getEntityId());
        packet.setName(input.getName());
        log.info(String.valueOf(input.getEntityId()));
        log.info(input.getName());
        packet.setX(input.getX());
        packet.setY(input.getY());
        packet.setZ(input.getZ());
        packet.setYRot(input.getYaw());
        packet.setXRot(input.getPitch());
        packet.setYHeadRot(input.getYaw());
        packet.setCarriedItem(input.getCurrentItem());
        packet.setXuid((long) (Math.random() * Long.MAX_VALUE));
        packet.setOnlineXuid((long) (Math.random() * Long.MAX_VALUE));
        packet.setPlayerIndex((byte)0); // TODO: find out about this
        packet.setSkinId(0);
        packet.setCapeId(0);
        packet.setUiGamePrivileges(0);
        packet.setEntityData(input.getMetadata());

        return TranslationResult.toClient(packet);
    }
}
