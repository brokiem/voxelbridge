package id.brokiem.voxelbridge.translation.mapper;

import id.brokiem.voxelbridge.protocol.lce.packets.LceDisconnectPacket;

/**
 * Maps Java Edition disconnect reason strings (JSON text) to LCE eDisconnectReason enum values.
 * Java sends human-readable JSON; LCE expects a 4-byte integer enum.
 */
public final class DisconnectReasonMapper {

    private DisconnectReasonMapper() {
    }

    /**
     * Maps a Java disconnect reason string to LCE eDisconnectReason.
     * Java typically sends JSON like {@code {"text":"Kicked by an operator"}}.
     * Returns {@link LceDisconnectPacket#CLOSED} for unrecognized reasons.
     */
    public static int mapJavaToLce(String javaReason) {
        if (javaReason == null) {
            return LceDisconnectPacket.CLOSED;
        }
        String lower = javaReason.toLowerCase();
        if (lower.contains("kick")) {
            return LceDisconnectPacket.KICKED;
        }
        if (lower.contains("banned")) {
            return LceDisconnectPacket.BANNED;
        }
        if (lower.contains("timeout") || lower.contains("timed out")) {
            return LceDisconnectPacket.TIME_OUT;
        }
        if (lower.contains("server full") || lower.contains("full")) {
            return LceDisconnectPacket.SERVER_FULL;
        }
        if (lower.contains("outdated client") || lower.contains("outdatedclient")) {
            return LceDisconnectPacket.OUTDATED_CLIENT;
        }
        if (lower.contains("outdated server") || lower.contains("outdatedserver")) {
            return LceDisconnectPacket.OUTDATED_SERVER;
        }
        return LceDisconnectPacket.CLOSED;
    }
}
