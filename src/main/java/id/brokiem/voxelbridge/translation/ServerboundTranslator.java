package id.brokiem.voxelbridge.translation;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.session.Session;

public interface ServerboundTranslator<I extends Packet> {
    TranslationResult translate(I input, Session session);
}
