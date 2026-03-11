package id.brokiem.voxelbridge.translation.impl;

import id.brokiem.voxelbridge.protocol.Packet;
import id.brokiem.voxelbridge.protocol.java.packets.play.JavaClientboundMultiBlockChangePacket;
import id.brokiem.voxelbridge.protocol.lce.packets.LceTileUpdatePacket;
import id.brokiem.voxelbridge.session.Session;
import id.brokiem.voxelbridge.translation.ClientboundTranslator;
import id.brokiem.voxelbridge.translation.TranslationResult;

import java.util.ArrayList;
import java.util.List;

public class JavaMultiBlockChangeTranslator implements ClientboundTranslator<JavaClientboundMultiBlockChangePacket> {
    
    private static final byte[] BLOCK_REMAP = new byte[256];

    static {
        for (int i = 0; i < 256; i++) {
            BLOCK_REMAP[i] = (i <= 160 || (i >= 170 && i <= 173)) ? (byte) i : (byte) 1;
        }
    }

    @Override
    public TranslationResult translate(JavaClientboundMultiBlockChangePacket input, Session session) {
        int[] records = input.getRecords();
        if (records == null || records.length == 0) {
            return TranslationResult.empty();
        }

        List<Packet> packets = new ArrayList<>(records.length);
        int baseX = input.getChunkX() << 4;
        int baseZ = input.getChunkZ() << 4;

        for (int record : records) {
            int blockData = (record >>> 16) & 0xFFFF;
            int blockId = (blockData >> 4) & 0xFFF;
            int metadata = blockData & 0xF;
            int y = (record >>> 8) & 0xFF;
            int z = baseZ + ((record >>> 4) & 0xF);
            int x = baseX + (record & 0xF);

            byte safeBlockId = (blockId < 256) ? BLOCK_REMAP[blockId] : (byte) 1;

            LceTileUpdatePacket tile = new LceTileUpdatePacket();
            tile.setX(x);
            tile.setY(y);
            tile.setZ(z);
            tile.setBlock(safeBlockId & 0xFF);
            tile.setData(metadata);
            
            packets.add(tile);
        }

        return TranslationResult.toClient(packets);
    }
}
