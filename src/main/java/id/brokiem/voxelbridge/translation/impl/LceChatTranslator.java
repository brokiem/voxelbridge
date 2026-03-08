package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.java.packets.play.JavaServerboundChatPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceChatPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ServerboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

public class LceChatTranslator implements ServerboundTranslator<LceChatPacket> {

    @Override
    public TranslationResult translate(LceChatPacket input, Session session) {
        String message = (input.getStringArgs().length > 0) ? input.getStringArgs()[0] : "";
        if (message.length() > 100) message = message.substring(0, 100);

        JavaServerboundChatPacket chatPacket = new JavaServerboundChatPacket();
        chatPacket.setMessage(message);

        return TranslationResult.toServer(chatPacket);
    }
}
