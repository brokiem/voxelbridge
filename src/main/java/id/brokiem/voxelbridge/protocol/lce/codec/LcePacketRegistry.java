package id.brokiem.voxelbridge.protocol.lce.codec;

import id.brokiem.voxelbridge.protocol.PacketRegistry;
import id.brokiem.voxelbridge.protocol.lce.packets.*;

public final class LcePacketRegistry {
    private LcePacketRegistry() {
    }

    public static PacketRegistry create() {
        PacketRegistry registry = new PacketRegistry();
        registry.register(0x00, LceKeepAlivePacket::new);
        registry.register(0x01, LceLoginPacket::new);
        registry.register(0x02, LcePreLoginPacket::new);
        registry.register(0x04, LceSetTimePacket::new);
        registry.register(0x06, LceSetSpawnPositionPacket::new);
        registry.register(0x08, LceSetHealthPacket::new);
        registry.register(0x0A, LceMovePlayerPacket::new);
        registry.register(0x0B, LceMovePlayerPositionPacket::new);
        registry.register(0x0C, LceMovePlayerRotationPacket::new);
        registry.register(0x0D, LceMovePlayerPositionRotationPacket::new);
        registry.register(0x10, LceSetCarriedItemPacket::new);
        registry.register(0xCA, LcePlayerAbilitiesPacket::new);
        registry.register(0xFF, LceDisconnectPacket::new);
        registry.register(0x32, LceChunkVisibilityPacket::new);
        registry.register(0x33, LceBlockRegionUpdatePacket::new);
        registry.register(0xCD, LceClientCommandPacket::new);
        registry.register(0x09, LceRespawnPacket::new);
        registry.register(0x03, LceChatPacket::new);
        return registry;
    }
}
