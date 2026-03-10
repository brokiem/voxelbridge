# Packet Implementation Status

This document tracks the implementation status of packets in VoxelBridge.

**Status Legend:**

* 🟢 **Implemented**: Packet class exists, translator exists, and is registered.
* 🟠 **Partial**: Packet class exists but translator is missing or incomplete.
* 🟡 **Missing**: Packet is not implemented.

---

## Java Edition Packets (Protocol 1.7.2)

### Clientbound (Java -> LCE)

| Packet ID | Packet Name                   | Status | Translator                        | Notes                                   |
|:----------|:------------------------------|:-------|:----------------------------------|:----------------------------------------|
| 0x00      | Keep Alive                    | 🟢     | `JavaKeepAliveTranslator`         |                                         |
| 0x01      | Join Game                     | 🟢     | `JavaLoginTranslator`             | Translates to LCE Login + SetTime       |
| 0x02      | Chat Message                  | 🟠     | `JavaChatTranslator`              | Missing translation keys and formatting |
| 0x03      | Time Update                   | 🟢     | `JavaUpdateTimeTranslator`        |                                         |
| 0x04      | Entity Equipment              | 🟢     | `JavaEntityEquipmentTranslator`   |                                         |
| 0x05      | Spawn Position                | 🟢     | `JavaSpawnPositionTranslator`     |                                         |
| 0x06      | Update Health                 | 🟢     | `JavaUpdateHealthTranslator`      |                                         |
| 0x07      | Respawn                       | 🟢     | `JavaRespawnTranslator`           |                                         |
| 0x08      | Player Position And Look      | 🟢     | `JavaPositionTranslator`          |                                         |
| 0x09      | Held Item Change              | 🟢     | `JavaHeldItemSlotTranslator`      |                                         |
| 0x0A      | Use Bed                       | 🟡     | —                                 |                                         |
| 0x0B      | Animation                     | 🟡     | —                                 |                                         |
| 0x0C      | Spawn Player                  | 🟢     | `JavaNamedEntitySpawnTranslator`  |                                         |
| 0x0D      | Collect Item                  | 🟢     | `JavaCollectItemTranslator`       |                                         |
| 0x0E      | Spawn Object                  | 🟢     | `JavaSpawnEntityTranslator`       |                                         |
| 0x0F      | Spawn Mob                     | 🟢     | `JavaSpawnEntityLivingTranslator` |                                         |
| 0x10      | Spawn Painting                | 🟡     | —                                 |                                         |
| 0x11      | Spawn Experience Orb          | 🟡     | —                                 |                                         |
| 0x12      | Entity Velocity               | 🟡     | —                                 |                                         |
| 0x13      | Destroy Entities              | 🟢     | `JavaDestroyEntitiesTranslator`   |                                         |
| 0x14      | Entity                        | 🟢     | `JavaEntityTranslator`            |                                         |
| 0x15      | Entity Relative Move          | 🟢     | `JavaRelEntityMoveTranslator`     |                                         |
| 0x16      | Entity Look                   | 🟢     | `JavaEntityLookTranslator`        |                                         |
| 0x17      | Entity Look and Relative Move | 🟢     | `JavaEntityMoveLookTranslator`    |                                         |
| 0x18      | Entity Teleport               | 🟢     | `JavaEntityTeleportTranslator`    |                                         |
| 0x19      | Entity Head Look              | 🟢     | `JavaEntityHeadLookTranslator`    |                                         |
| 0x1A      | Entity Status                 | 🟡     | —                                 |                                         |
| 0x1B      | Attach Entity                 | 🟡     | —                                 |                                         |
| 0x1C      | Entity Metadata               | 🟢     | `JavaEntityMetadataTranslator`    |                                         |
| 0x1D      | Entity Effect                 | 🟡     | —                                 |                                         |
| 0x1E      | Remove Entity Effect          | 🟡     | —                                 |                                         |
| 0x1F      | Set Experience                | 🟢     | `JavaExperienceTranslator`        |                                         |
| 0x20      | Entity Properties             | 🟡     | —                                 |                                         |
| 0x21      | Chunk Data                    | 🟢     | `JavaMapChunkTranslator`          |                                         |
| 0x22      | Multi Block Change            | 🟡     | —                                 |                                         |
| 0x23      | Block Change                  | 🟢     | `JavaBlockChangeTranslator`       |                                         |
| 0x24      | Block Action                  | 🟡     | —                                 |                                         |
| 0x25      | Block Break Animation         | 🟡     | —                                 |                                         |
| 0x26      | Map Chunk Bulk                | 🟢     | `JavaMapChunkBulkTranslator`      |                                         |
| 0x27      | Explosion                     | 🟡     | —                                 |                                         |
| 0x28      | Effect                        | 🟡     | —                                 |                                         |
| 0x29      | Sound Effect                  | 🟡     | —                                 |                                         |
| 0x2A      | Particle                      | 🟡     | —                                 |                                         |
| 0x2B      | Change Game State             | 🟡     | —                                 |                                         |
| 0x2C      | Spawn Global Entity           | 🟡     | —                                 |                                         |
| 0x2D      | Open Window                   | 🟢     | `JavaOpenWindowTranslator`        |                                         |
| 0x2E      | Close Window                  | 🟢     | `JavaCloseWindowTranslator`       |                                         |
| 0x2F      | Set Slot                      | 🟢     | `JavaSetSlotTranslator`           |                                         |
| 0x30      | Window Items                  | 🟢     | `JavaWindowItemsTranslator`       |                                         |
| 0x31      | Window Property               | 🟢     | `JavaCraftProgressBarTranslator`  |                                         |
| 0x32      | Confirm Transaction           | 🟢     | `JavaTransactionTranslator`       |                                         |
| 0x33      | Update Sign                   | 🟡     | —                                 |                                         |
| 0x34      | Maps                          | 🟡     | —                                 |                                         |
| 0x35      | Update Block Entity           | 🟡     | —                                 |                                         |
| 0x36      | Sign Editor Open              | 🟡     | —                                 |                                         |
| 0x37      | Statistics                    | 🟡     | —                                 |                                         |
| 0x38      | Player List Item              | 🟡     | —                                 |                                         |
| 0x39      | Player Abilities              | 🟢     | `JavaAbilitiesTranslator`         |                                         |
| 0x3A      | Tab-Complete                  | 🟡     | —                                 |                                         |
| 0x3B      | Scoreboard Objective          | 🟡     | —                                 |                                         |
| 0x3C      | Update Score                  | 🟡     | —                                 |                                         |
| 0x3D      | Display Scoreboard            | 🟡     | —                                 |                                         |
| 0x3E      | Teams                         | 🟡     | —                                 |                                         |
| 0x3F      | Plugin Message                | 🟡     | —                                 |                                         |
| 0x40      | Disconnect                    | 🟢     | `JavaKickDisconnectTranslator`    |                                         |

---

## Legacy Console Edition (LCE) Packets

### Serverbound (LCE -> Java)

| Packet ID | Packet Name                         | Status | Translator                                | Notes |
|:----------|:------------------------------------|:-------|:------------------------------------------|:------|
| 0x00      | Keep Alive                          | 🟢     | `LceKeepAliveTranslator`                  |       |
| 0x01      | Login                               | 🟢     | `LceLoginTranslator`                      |       |
| 0x02      | Pre-Login                           | 🟢     | `LcePreLoginTranslator`                   |       |
| 0x03      | Chat                                | 🟢     | `LceChatTranslator`                       |       |
| 0x04      | Set Time                            | 🟢     | —                                         |       |
| 0x05      | Set Equipped Item                   | 🟠     | —                                         |       |
| 0x06      | Set Spawn Position                  | 🟡     | —                                         |       |
| 0x07      | Interact                            | 🟢     | `LceInteractTranslator`                   |       |
| 0x08      | Set Health                          | 🟢     | —                                         |       |
| 0x09      | Respawn                             | 🟢     | —                                         |       |
| 0x0A      | Move Player                         | 🟢     | `LceMovePlayerTranslator`                 |       |
| 0x0B      | Move Player Position                | 🟢     | `LceMovePlayerPositionTranslator`         |       |
| 0x0C      | Move Player Rotation                | 🟢     | `LceMovePlayerRotationTranslator`         |       |
| 0x0D      | Move Player Position Rotation       | 🟢     | `LceMovePlayerPositionRotationTranslator` |       |
| 0x0E      | Player Action                       | 🟢     | `LcePlayerActionTranslator`               |       |
| 0x0F      | Use Item                            | 🟢     | `LceUseItemTranslator`                    |       |
| 0x10      | Set Carried Item                    | 🟢     | `LceSetCarriedItemTranslator`             |       |
| 0x11      | Entity Action At Position           | 🟡     | —                                         |       |
| 0x12      | Animate                             | 🟢     | `LceAnimateTranslator`                    |       |
| 0x13      | Player Command                      | 🟡     | —                                         |       |
| 0x14      | Add Player                          | 🟢     | —                                         |       |
| 0x16      | Take Item Entity                    | 🟢     | —                                         |       |
| 0x17      | Add Entity                          | 🟢     | —                                         |       |
| 0x18      | Add Mob                             | 🟢     | —                                         |       |
| 0x19      | Add Painting                        | 🟡     | —                                         |       |
| 0x1A      | Add Experience Orb                  | 🟡     | —                                         |       |
| 0x1B      | Player Input                        | 🟡     | —                                         |       |
| 0x1C      | Set Entity Motion                   | 🟡     | —                                         |       |
| 0x1D      | Remove Entities                     | 🟢     | —                                         |       |
| 0x1E      | Move Entity                         | 🟢     | —                                         |       |
| 0x1F      | Move Entity Position                | 🟢     | —                                         |       |
| 0x20      | Move Entity Rotation                | 🟢     | —                                         |       |
| 0x21      | Move Entity Position Rotation       | 🟢     | —                                         |       |
| 0x22      | Teleport Entity                     | 🟢     | —                                         |       |
| 0x23      | Rotate Head                         | 🟢     | —                                         |       |
| 0x26      | Entity Event                        | 🟡     | —                                         |       |
| 0x27      | Set Entity Link                     | 🟡     | —                                         |       |
| 0x28      | Set Entity Data                     | 🟢     | —                                         |       |
| 0x29      | Update Mob Effect                   | 🟡     | —                                         |       |
| 0x2A      | Remove Mob Effect                   | 🟡     | —                                         |       |
| 0x2B      | Set Experience                      | 🟢     | —                                         |       |
| 0x2C      | Update Attributes                   | 🟡     | —                                         |       |
| 0x32      | Chunk Visibility                    | 🟢     | —                                         |       |
| 0x33      | Block Region Update                 | 🟢     | —                                         |       |
| 0x34      | Chunk Tiles Update                  | 🟡     | —                                         |       |
| 0x35      | Tile Update                         | 🟢     | —                                         |       |
| 0x36      | Tile Event                          | 🟡     | —                                         |       |
| 0x37      | Tile Destruction                    | 🟡     | —                                         |       |
| 0x3C      | Explode                             | 🟡     | —                                         |       |
| 0x3D      | Level Event                         | 🟡     | —                                         |       |
| 0x3E      | Level Sound                         | 🟡     | —                                         |       |
| 0x3F      | Level Particles                     | 🟡     | —                                         |       |
| 0x46      | Game Event                          | 🟡     | —                                         |       |
| 0x47      | Add Global Entity                   | 🟡     | —                                         |       |
| 0x64      | Container Open                      | 🟠     | —                                         | No-op |
| 0x65      | Container Close                     | 🟢     | `LceContainerCloseTranslator`             |       |
| 0x66      | Container Click                     | 🟢     | `LceContainerClickTranslator`             |       |
| 0x67      | Container Set Slot                  | 🟠     | —                                         | No-op |
| 0x68      | Container Set Content               | 🟠     | —                                         | No-op |
| 0x69      | Container Set Data                  | 🟠     | —                                         | No-op |
| 0x6A      | Container Ack                       | 🟢     | `LceContainerAckTranslator`               |       |
| 0x6B      | Set Creative Mode Slot              | 🟢     | `LceSetCreativeModeSlotTranslator`        |       |
| 0x6C      | Container Button Click              | 🟡     | —                                         |       |
| 0x82      | Sign Update                         | 🟡     | —                                         |       |
| 0x83      | Complex Item Data                   | 🟡     | —                                         |       |
| 0x84      | Tile Entity Data                    | 🟡     | —                                         |       |
| 0x85      | Tile Editor Open                    | 🟡     | —                                         |       |
| 0x96      | Craft Item                          | 🟡     | —                                         |       |
| 0x97      | Trade Item                          | 🟡     | —                                         |       |
| 0x98      | Debug Options                       | 🟡     | —                                         |       |
| 0x99      | Server Settings Changed             | 🟡     | —                                         |       |
| 0x9A      | Texture                             | 🟡     | —                                         |       |
| 0x9B      | Chunk Visibility Area               | 🟢     | —                                         |       |
| 0x9C      | Update Progress                     | 🟡     | —                                         |       |
| 0x9D      | Texture Change                      | 🟡     | —                                         |       |
| 0x9E      | Update Game Rule Progress           | 🟡     | —                                         |       |
| 0x9F      | Kick Player                         | 🟡     | —                                         |       |
| 0xA0      | Texture And Geometry                | 🟡     | —                                         |       |
| 0xA1      | Texture And Geometry Change         | 🟡     | —                                         |       |
| 0xA2      | Move Entity Small                   | 🟡     | —                                         |       |
| 0xA3      | Move Entity Small Position          | 🟡     | —                                         |       |
| 0xA4      | Move Entity Small Rotation          | 🟡     | —                                         |       |
| 0xA5      | Move Entity Small Position Rotation | 🟡     | —                                         |       |
| 0xA6      | XZ                                  | 🟡     | —                                         |       |
| 0xA7      | Game Command                        | 🟡     | —                                         |       |
| 0xC8      | Award Stat                          | 🟡     | —                                         |       |
| 0xC9      | Player Info                         | 🟡     | —                                         |       |
| 0xCA      | Player Abilities                    | 🟡     | —                                         |       |
| 0xCD      | Client Command                      | 🟢     | `LceClientCommandTranslator`              |       |
| 0xCE      | Set Objective                       | 🟡     | —                                         |       |
| 0xCF      | Set Score                           | 🟡     | —                                         |       |
| 0xD0      | Set Display Objective               | 🟡     | —                                         |       |
| 0xD1      | Set Player Team                     | 🟡     | —                                         |       |
| 0xFA      | Custom Payload                      | 🟡     | —                                         |       |
| 0xFE      | Get Info                            | 🟡     | —                                         |       |
| 0xFF      | Disconnect                          | 🟡     | —                                         |       |