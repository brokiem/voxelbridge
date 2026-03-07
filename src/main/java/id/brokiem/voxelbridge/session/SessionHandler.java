package id.brokiem.voxelbridge.session;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.translation.TranslationResult;
import id.brokiem.voxelbridge.translation.TranslationTarget;
import id.brokiem.voxelbridge.translation.TranslatorRegistry;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionHandler {
    @Getter
    private final Session session;
    private final TranslatorRegistry translatorRegistry;

    public SessionHandler(Session session, TranslatorRegistry translatorRegistry) {
        this.session = session;
        this.translatorRegistry = translatorRegistry;
    }

    public void handleServerbound(Packet packet) {
        TranslationResult result = translatorRegistry.translateServerbound(packet, session);
        if (result == null) {
            return;
        }

        if (result.newState() != null) {
            log.debug("[Session] State transition {} -> {}", session.getState(), result.newState());
            session.setState(result.newState());
        }

        if (result.packets().isEmpty()) {
            return;
        }

        Channel target = resolveTarget(result);
        if (result.target() == TranslationTarget.SERVER) {
            if (target != null && target.isActive()) {
                for (Packet out : result.packets()) {
                    target.write(out);
                }
                target.flush();
            } else {
                for (Packet out : result.packets()) {
                    session.queueForServer(out);
                }
                log.debug("[Session] Queued {} serverbound packets (Java connection pending)", result.packets().size());
            }
        } else {
            if (target != null && target.isActive()) {
                for (Packet out : result.packets()) {
                    target.write(out);
                }
                target.flush();
            } else {
                log.warn("[Session] Target channel unavailable");
            }
        }
    }

    public void handleClientbound(Packet packet) {
        TranslationResult result = translatorRegistry.translateClientbound(packet, session);
        if (result == null) {
            return;
        }

        if (result.newState() != null) {
            log.debug("[Session] State transition {} -> {}", session.getState(), result.newState());
            session.setState(result.newState());
        }

        if (result.packets().isEmpty() && (result.serverPackets() == null || result.serverPackets().isEmpty())) {
            return;
        }

        Channel clientChannel = session.getClientChannel();
        if (clientChannel != null && clientChannel.isActive() && !result.packets().isEmpty()) {
            for (Packet out : result.packets()) {
                clientChannel.write(out);
            }
            clientChannel.flush();
        } else if (!result.packets().isEmpty()) {
            log.warn("[Session] Client channel unavailable for clientbound packets");
        }

        if (result.serverPackets() != null && !result.serverPackets().isEmpty()) {
            Channel serverChannel = session.getServerChannel();
            if (serverChannel != null && serverChannel.isActive()) {
                for (Packet out : result.serverPackets()) {
                    serverChannel.write(out);
                }
                serverChannel.flush();
            } else {
                for (Packet out : result.serverPackets()) {
                    session.queueForServer(out);
                }
                log.debug("[Session] Queued {} server response packets (Java connection pending)", result.serverPackets().size());
            }
        }
    }

    private Channel resolveTarget(TranslationResult result) {
        if (result.target() == null) {
            return null;
        }
        return switch (result.target()) {
            case SERVER -> session.getServerChannel();
            case CLIENT -> session.getClientChannel();
        };
    }
}
