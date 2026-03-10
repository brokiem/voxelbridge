package id.brokiem.voxelbridge.protocol.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlotData {
    public static final short EMPTY = -1;

    private short itemId = EMPTY;
    private byte itemCount = 0;
    private short itemDamage = 0;
    private byte[] nbtData = new byte[0];

    public boolean isEmpty() {
        return itemId == EMPTY;
    }

    public static SlotData empty() {
        return new SlotData(EMPTY, (byte) 0, (short) 0, new byte[0]);
    }
}
