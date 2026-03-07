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
        registry.register(0x0D, LceMovePlayerPacket::new);
        registry.register(0x10, LceSetCarriedItemPacket::new);
        registry.register(0xCA, LcePlayerAbilitiesPacket::new);
        registry.register(0xFF, LceDisconnectPacket::new);
        return registry;
    }
}
