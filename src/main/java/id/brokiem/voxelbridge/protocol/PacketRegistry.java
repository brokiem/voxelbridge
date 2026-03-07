package id.brokiem.voxelbridge.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PacketRegistry {
    private final Map<Integer, Supplier<? extends Packet>> packets = new HashMap<>();

    public <T extends Packet> void register(int id, Supplier<T> factory) {
        packets.put(id, factory);
    }

    public Packet createPacket(int id) {
        Supplier<? extends Packet> factory = packets.get(id);
        return factory != null ? factory.get() : null;
    }
}
