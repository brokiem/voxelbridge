package id.brokiem.voxelbridge.translation.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundChatPacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceChatPacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaChatTranslator implements ClientboundTranslator<JavaClientboundChatPacket> {

    @Override
    public TranslationResult translate(JavaClientboundChatPacket input, Session session) {
        String inputMessage = input.getMessage();
        // band-aid solution
        // TODO: properly format json messages with adventure-api and also format java translation keys
        String message = formatChatTypeText(inputMessage);

        if (message.length() > 100) {
            message = message.substring(0, 100);
        }

        LceChatPacket chatPacket = new LceChatPacket();
        chatPacket.setMessageType((short) 0); // 0 = no localization
        chatPacket.setStringArgs(new String[]{message}); // The chat message as a string argument
        chatPacket.setIntArgs(new int[0]); // No integer arguments

        return TranslationResult.toClient(chatPacket);
    }

    public String formatChatTypeText(String json) {
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        if (!obj.has("translate")) {
            return json;
        }

        String key = obj.get("translate").getAsString();

        if (!key.equals("chat.type.text")) {
            return json;
        }

        JsonArray with = obj.getAsJsonArray("with");

        String sender = extractText(with.get(0).getAsJsonObject());
        String message = extractText(with.get(1));

        return "<" + sender + "> " + message;
    }

    private String extractText(com.google.gson.JsonElement element) {
        if (element.isJsonPrimitive()) {
            return element.getAsString();
        }

        JsonObject obj = element.getAsJsonObject();

        if (obj.has("text")) {
            return obj.get("text").getAsString();
        }

        return obj.toString();
    }
}
