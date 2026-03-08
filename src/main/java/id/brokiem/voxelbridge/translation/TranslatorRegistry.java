package id.brokiem.voxelbridge.translation;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.server.ProxyConfig;
import id.brokiem.voxelbridge.session.ConnectionState;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.impl.*;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TranslatorRegistry {
    private final Map<Integer, ServerboundTranslator<?>> serverbound = new HashMap<>();
    private final Map<ConnectionState, Map<Integer, ClientboundTranslator<?>>> clientbound = new EnumMap<>(ConnectionState.class);
    private final Set<Integer> loggedUnsupportedServerbound = ConcurrentHashMap.newKeySet();
    private final Set<String> loggedUnsupportedClientbound = ConcurrentHashMap.newKeySet();

    public TranslatorRegistry(ProxyConfig config) {
        for (ConnectionState state : ConnectionState.values()) {
            clientbound.put(state, new HashMap<>());
        }

        // Serverbound: LCE -> Java (flat map, LCE IDs are globally unique)
        serverbound.put(0x00, new LceKeepAliveTranslator());
        serverbound.put(0x01, new LceLoginTranslator(config));
        serverbound.put(0x02, new LcePreLoginTranslator(config));
        //serverbound.put(0x0A, new LceMovePlayerTranslator()); // not needed
        serverbound.put(0x0B, new LceMovePlayerPositionTranslator());
        serverbound.put(0x0C, new LceMovePlayerRotationTranslator());
        serverbound.put(0x0D, new LceMovePlayerPositionRotationTranslator());
        serverbound.put(0xCD, new LceClientCommandTranslator());
        serverbound.put(0x03, new LceChatTranslator());

        // Clientbound LOGIN: Java -> LCE
        // After the login-state success packet, state transitions to PLAY so the play-state login packet is decoded in PLAY
        clientbound.get(ConnectionState.LOGIN).put(0x00, new JavaDisconnectTranslator());
        clientbound.get(ConnectionState.LOGIN).put(0x02, new JavaSuccessTranslator());

        // Clientbound PLAY: Java -> LCE
        clientbound.get(ConnectionState.PLAY).put(0x00, new JavaKeepAliveTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x01, new JavaLoginTranslator(config));
        clientbound.get(ConnectionState.PLAY).put(0x03, new JavaUpdateTimeTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x05, new JavaSpawnPositionTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x06, new JavaUpdateHealthTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x08, new JavaPositionTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x09, new JavaHeldItemSlotTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x39, new JavaAbilitiesTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x40, new JavaKickDisconnectTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x21, new JavaMapChunkTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x26, new JavaMapChunkBulkTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x07, new JavaRespawnTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x02, new JavaChatTranslator());
        clientbound.get(ConnectionState.PLAY).put(0x0F, new JavaSpawnEntityLivingTranslator());
    }

    @SuppressWarnings("unchecked")
    public TranslationResult translateServerbound(Packet packet, Session session) {
        ServerboundTranslator<Packet> translator = (ServerboundTranslator<Packet>) serverbound.get(packet.getId());
        if (translator != null) {
            log.info("Translating serverbound LCE packet 0x{} using {}", Integer.toHexString(packet.getId()), translator.getClass().getSimpleName());
            return translator.translate(packet, session);
        }
        if (loggedUnsupportedServerbound.add(packet.getId())) {
            log.warn("Dropping unsupported serverbound LCE packet 0x{}", Integer.toHexString(packet.getId()));
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public TranslationResult translateClientbound(Packet packet, Session session) {
        ConnectionState state = session.getState();
        Map<Integer, ClientboundTranslator<?>> stateMap = clientbound.get(state);
        if (stateMap != null) {
            ClientboundTranslator<Packet> translator = (ClientboundTranslator<Packet>) stateMap.get(packet.getId());
            if (translator != null) {
                log.info("Translating clientbound Java packet 0x{} in state {} using {}", Integer.toHexString(packet.getId()), state, translator.getClass().getSimpleName());
                return translator.translate(packet, session);
            }
        }
        String key = state + ":" + packet.getId();
        if (loggedUnsupportedClientbound.add(key)) {
            log.warn("Dropping unsupported clientbound Java packet 0x{} in state {}", Integer.toHexString(packet.getId()), state);
        }
        return null;
    }
}
