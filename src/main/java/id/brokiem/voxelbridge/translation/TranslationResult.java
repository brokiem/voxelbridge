package id.brokiem.voxelbridge.translation;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.session.ConnectionState;

import java.util.List;

public record TranslationResult(
        List<Packet> packets,
        List<Packet> serverPackets,
        ConnectionState newState,
        TranslationTarget target) {

    public static TranslationResult toServer(Packet packet) {
        return new TranslationResult(List.of(packet), List.of(), null, TranslationTarget.SERVER);
    }

    public static TranslationResult toServer(List<Packet> packets, ConnectionState newState) {
        return new TranslationResult(packets, List.of(), newState, TranslationTarget.SERVER);
    }

    public static TranslationResult toClient(Packet packet) {
        return new TranslationResult(List.of(packet), List.of(), null, TranslationTarget.CLIENT);
    }

    public static TranslationResult toClient(Packet packet, ConnectionState newState) {
        return new TranslationResult(List.of(packet), List.of(), newState, TranslationTarget.CLIENT);
    }

    /**
     * Client packet to LCE and server packet(s) to Java (e.g. Position And Look response).
     */
    public static TranslationResult toClientAndServer(Packet clientPacket, Packet serverPacket) {
        return new TranslationResult(List.of(clientPacket), List.of(serverPacket), null, TranslationTarget.CLIENT);
    }
}
