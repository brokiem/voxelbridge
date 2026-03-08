package id.brokiem.voxelbridge.protocol.java.codec;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.PacketRegistry;
import id.brokiem.voxelbridge.protocol.java.packets.login.JavaClientboundDisconnectPacket;
import id.brokiem.voxelbridge.protocol.java.packets.login.JavaClientboundSuccessPacket;
import id.brokiem.voxelbridge.protocol.java.packets.play.*;
import id.brokiem.voxelbridge.session.ConnectionState;

import java.util.EnumMap;
import java.util.Map;

public final class JavaPacketRegistry {
    private final Map<ConnectionState, PacketRegistry> stateRegistries = new EnumMap<>(ConnectionState.class);

    private JavaPacketRegistry() {
    }

    public static JavaPacketRegistry create() {
        JavaPacketRegistry jpr = new JavaPacketRegistry();

        PacketRegistry login = new PacketRegistry();
        login.register(0x00, JavaClientboundDisconnectPacket::new);
        login.register(0x02, JavaClientboundSuccessPacket::new);
        jpr.stateRegistries.put(ConnectionState.LOGIN, login);

        PacketRegistry play = new PacketRegistry();
        play.register(0x00, JavaClientboundKeepAlivePacket::new);
        play.register(0x01, JavaClientboundLoginPacket::new);
        play.register(0x03, JavaClientboundUpdateTimePacket::new);
        play.register(0x05, JavaClientboundSpawnPositionPacket::new);
        play.register(0x06, JavaClientboundUpdateHealthPacket::new);
        play.register(0x08, JavaClientboundPositionPacket::new);
        play.register(0x09, JavaClientboundHeldItemSlotPacket::new);
        play.register(0x39, JavaClientboundAbilitiesPacket::new);
        play.register(0x40, JavaClientboundKickDisconnectPacket::new);
        play.register(0x21, JavaClientboundMapChunkPacket::new);
        play.register(0x26, JavaClientboundMapChunkBulkPacket::new);
        play.register(0x07, JavaClientboundRespawnPacket::new);
        play.register(0x02, JavaClientboundChatPacket::new);
        play.register(0x0F, JavaClientboundSpawnEntityLivingPacket::new);
        play.register(0x0c, JavaClientboundNamedEntitySpawn::new);
        play.register(0x0D, JavaClientboundCollectItemPacket::new);
        play.register(0x14, JavaClientboundEntityPacket::new);
        play.register(0x15, JavaClientboundRelEntityMovePacket::new);
        play.register(0x16, JavaClientboundEntityLookPacket::new);
        play.register(0x17, JavaClientboundEntityMoveLookPacket::new);
        play.register(0x19, JavaClientboundEntityHeadLookPacket::new);
        play.register(0x0A, JavaServerboundArmAnimationPacket::new);
        jpr.stateRegistries.put(ConnectionState.PLAY, play);

        return jpr;
    }

    public Packet createPacket(ConnectionState state, int id) {
        PacketRegistry registry = stateRegistries.get(state);
        if (registry != null) {
            return registry.createPacket(id);
        }
        return null;
    }
}
