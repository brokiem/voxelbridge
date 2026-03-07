package id.brokiem.voxelbridge.translation;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.session.Session;

public interface ClientboundTranslator<I extends Packet> {
    TranslationResult translate(I input, Session session);
}
