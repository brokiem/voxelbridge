# Packet Implementation Status

This document tracks the implementation status of packets in VoxelBridge.

**Status Legend:**
*   **Implemented**: Packet class exists, translator exists, and is registered.
*   **Partial**: Packet class exists but translator is missing or incomplete.
*   **Missing**: Packet is not implemented.

---

## Java Edition Packets (Protocol 1.7.2)

### Clientbound (Java -> LCE)

| Packet ID | Packet Name                   | Status      | Translator                        | Notes                                   |
|:----------|:------------------------------|:------------|:----------------------------------|:----------------------------------------|
| 0x00      | Keep Alive                    | Implemented | `JavaKeepAliveTranslator`         |                                         |
| 0x01      | Join Game                     | Implemented | `JavaLoginTranslator`             | Translates to LCE Login + SetTime       |
| 0x02      | Chat Message                  | Partial     | `JavaChatTranslator`              | Missing translation keys and formatting | |
| 0x03      | Time Update                   | Implemented | `JavaUpdateTimeTranslator`        |                                         |
| 0x04      | Entity Equipment              | Missing     | —                                 |                                         |
| 0x05      | Spawn Position                | Implemented | `JavaSpawnPositionTranslator`     |                                         |
| 0x06      | Update Health                 | Implemented | `JavaUpdateHealthTranslator`      |                                         |
| 0x07      | Respawn                       | Implemented | `JavaRespawnTranslator`           |                                         |
| 0x08      | Player Position And Look      | Implemented | `JavaPositionTranslator`          |                                         |
| 0x09      | Held Item Change              | Implemented | `JavaHeldItemSlotTranslator`      |                                         |
| 0x0A      | Use Bed                       | Missing     | —                                 |                                         |
| 0x0B      | Animation                     | Missing     | —                                 |                                         |
| 0x0C      | Spawn Player                  | Implemented | `JavaNamedEntitySpawnTranslator`  |                                         |
| 0x0D      | Collect Item                  | Implemented | `JavaCollectItemTranslator`       |                                         |
| 0x0E      | Spawn Object                  | Missing     | —                                 |                                         |
| 0x0F      | Spawn Mob                     | Implemented | `JavaSpawnEntityLivingTranslator` |                                         |
| 0x10      | Spawn Painting                | Missing     | —                                 |                                         |
| 0x11      | Spawn Experience Orb          | Missing     | —                                 |                                         |
| 0x12      | Entity Velocity               | Missing     | —                                 |                                         |
| 0x13      | Destroy Entities              | Missing     | —                                 |                                         |
| 0x14      | Entity                        | Implemented | `JavaEntityTranslator`            |                                         |
| 0x15      | Entity Relative Move          | Implemented | `JavaRelEntityMoveTranslator`     |                                         |
| 0x16      | Entity Look                   | Implemented | `JavaEntityLookTranslator`        |                                         |
| 0x17      | Entity Look and Relative Move | Implemented | `JavaEntityMoveLookTranslator`    |                                         |
| 0x18      | Entity Teleport               | Missing     | —                                 |                                         |
| 0x19      | Entity Head Look              | Implemented | `JavaEntityHeadLookTranslator`    |                                         |
| 0x1A      | Entity Status                 | Missing     | —                                 |                                         |
| 0x1B      | Attach Entity                 | Missing     | —                                 |                                         |
| 0x1C      | Entity Metadata               | Missing     | —                                 |                                         |
| 0x1D      | Entity Effect                 | Missing     | —                                 |                                         |
| 0x1E      | Remove Entity Effect          | Missing     | —                                 |                                         |
| 0x1F      | Set Experience                | Missing     | —                                 |                                         |
| 0x20      | Entity Properties             | Missing     | —                                 |                                         |
| 0x21      | Chunk Data                    | Implemented | `JavaMapChunkTranslator`          |                                         |
| 0x22      | Multi Block Change            | Missing     | —                                 |                                         |
| 0x23      | Block Change                  | Missing     | —                                 |                                         |
| 0x24      | Block Action                  | Missing     | —                                 |                                         |
| 0x25      | Block Break Animation         | Missing     | —                                 |                                         |
| 0x26      | Map Chunk Bulk                | Implemented | `JavaMapChunkBulkTranslator`      |                                         |
| 0x27      | Explosion                     | Missing     | —                                 |                                         |
| 0x28      | Effect                        | Missing     | —                                 |                                         |
| 0x29      | Sound Effect                  | Missing     | —                                 |                                         |
| 0x2A      | Particle                      | Missing     | —                                 |                                         |
| 0x2B      | Change Game State             | Missing     | —                                 |                                         |
| 0x2C      | Spawn Global Entity           | Missing     | —                                 |                                         |
| 0x2D      | Open Window                   | Missing     | —                                 |                                         |
| 0x2E      | Close Window                  | Missing     | —                                 |                                         |
| 0x2F      | Set Slot                      | Missing     | —                                 |                                         |
| 0x30      | Window Items                  | Missing     | —                                 |                                         |
| 0x31      | Window Property               | Missing     | —                                 |                                         |
| 0x32      | Confirm Transaction           | Missing     | —                                 |                                         |
| 0x33      | Update Sign                   | Missing     | —                                 |                                         |
| 0x34      | Maps                          | Missing     | —                                 |                                         |
| 0x35      | Update Block Entity           | Missing     | —                                 |                                         |
| 0x36      | Sign Editor Open              | Missing     | —                                 |                                         |
| 0x37      | Statistics                    | Missing     | —                                 |                                         |
| 0x38      | Player List Item              | Missing     | —                                 |                                         |
| 0x39      | Player Abilities              | Implemented | `JavaAbilitiesTranslator`         |                                         |
| 0x3A      | Tab-Complete                  | Missing     | —                                 |                                         |
| 0x3B      | Scoreboard Objective          | Missing     | —                                 |                                         |
| 0x3C      | Update Score                  | Missing     | —                                 |                                         |
| 0x3D      | Display Scoreboard            | Missing     | —                                 |                                         |
| 0x3E      | Teams                         | Missing     | —                                 |                                         |
| 0x3F      | Plugin Message                | Missing     | —                                 |                                         |
| 0x40      | Disconnect                    | Implemented | `JavaKickDisconnectTranslator`    |                                         |

---

## Legacy Console Edition (LCE) Packets

### Serverbound (LCE -> Java)

| Packet ID | Packet Name                         | Status      | Translator                                | Notes |
|:----------|:------------------------------------|:------------|:------------------------------------------|:------|
| 0x00      | Keep Alive                          | Implemented | `LceKeepAliveTranslator`                  |       |
| 0x01      | Login                               | Implemented | `LceLoginTranslator`                      |       |
| 0x02      | Pre-Login                           | Implemented | `LcePreLoginTranslator`                   |       |
| 0x03      | Chat                                | Implemented | `LceChatTranslator`                       |       |
| 0x04      | Set Time                            | Missing     | —                                         |       |
| 0x05      | Set Equipped Item                   | Missing     | —                                         |       |
| 0x06      | Set Spawn Position                  | Missing     | —                                         |       |
| 0x07      | Interact                            | Implemented | `LceInteractTranslator`                   |       |
| 0x08      | Set Health                          | Missing     | —                                         |       |
| 0x09      | Respawn                             | Missing     | —                                         |       |
| 0x0A      | Move Player                         | Implemented | `LceMovePlayerTranslator`                 |       |
| 0x0B      | Move Player Position                | Implemented | `LceMovePlayerPositionTranslator`         |       |
| 0x0C      | Move Player Rotation                | Implemented | `LceMovePlayerRotationTranslator`         |       |
| 0x0D      | Move Player Position Rotation       | Implemented | `LceMovePlayerPositionRotationTranslator` |       |
| 0x0E      | Player Action                       | Missing     | —                                         |       |
| 0x0F      | Use Item                            | Missing     | —                                         |       |
| 0x10      | Set Carried Item                    | Missing     | —                                         |       |
| 0x11      | Entity Action At Position           | Missing     | —                                         |       |
| 0x12      | Animate                             | Missing     | —                                         |       |
| 0x13      | Player Command                      | Missing     | —                                         |       |
| 0x14      | Add Player                          | Implemented | —                                         |       |
| 0x16      | Take Item Entity                    | Implemented | —                                         |       |
| 0x17      | Add Entity                          | Missing     | —                                         |       |
| 0x18      | Add Mob                             | Implemented | —                                         |       |
| 0x19      | Add Painting                        | Missing     | —                                         |       |
| 0x1A      | Add Experience Orb                  | Missing     | —                                         |       |
| 0x1B      | Player Input                        | Missing     | —                                         |       |
| 0x1C      | Set Entity Motion                   | Missing     | —                                         |       |
| 0x1D      | Remove Entities                     | Missing     | —                                         |       |
| 0x1E      | Move Entity                         | Implemented | —                                         |       |
| 0x1F      | Move Entity Position                | Implemented | —                                         |       |
| 0x20      | Move Entity Rotation                | Implemented | —                                         |       |
| 0x21      | Move Entity Position Rotation       | Implemented | —                                         |       |
| 0x22      | Teleport Entity                     | Missing     | —                                         |       |
| 0x23      | Rotate Head                         | Implemented | —                                         |       |
| 0x26      | Entity Event                        | Missing     | —                                         |       |
| 0x27      | Set Entity Link                     | Missing     | —                                         |       |
| 0x28      | Set Entity Data                     | Missing     | —                                         |       |
| 0x29      | Update Mob Effect                   | Missing     | —                                         |       |
| 0x2A      | Remove Mob Effect                   | Missing     | —                                         |       |
| 0x2B      | Set Experience                      | Missing     | —                                         |       |
| 0x2C      | Update Attributes                   | Missing     | —                                         |       |
| 0x32      | Chunk Visibility                    | Missing     | —                                         |       |
| 0x33      | Block Region Update                 | Missing     | —                                         |       |
| 0x34      | Chunk Tiles Update                  | Missing     | —                                         |       |
| 0x35      | Tile Update                         | Missing     | —                                         |       |
| 0x36      | Tile Event                          | Missing     | —                                         |       |
| 0x37      | Tile Destruction                    | Missing     | —                                         |       |
| 0x3C      | Explode                             | Missing     | —                                         |       |
| 0x3D      | Level Event                         | Missing     | —                                         |       |
| 0x3E      | Level Sound                         | Missing     | —                                         |       |
| 0x3F      | Level Particles                     | Missing     | —                                         |       |
| 0x46      | Game Event                          | Missing     | —                                         |       |
| 0x47      | Add Global Entity                   | Missing     | —                                         |       |
| 0x64      | Container Open                      | Missing     | —                                         |       |
| 0x65      | Container Close                     | Missing     | —                                         |       |
| 0x66      | Container Click                     | Missing     | —                                         |       |
| 0x67      | Container Set Slot                  | Missing     | —                                         |       |
| 0x68      | Container Set Content               | Missing     | —                                         |       |
| 0x69      | Container Set Data                  | Missing     | —                                         |       |
| 0x6A      | Container Ack                       | Missing     | —                                         |       |
| 0x6B      | Set Creative Mode Slot              | Missing     | —                                         |       |
| 0x6C      | Container Button Click              | Missing     | —                                         |       |
| 0x82      | Sign Update                         | Missing     | —                                         |       |
| 0x83      | Complex Item Data                   | Missing     | —                                         |       |
| 0x84      | Tile Entity Data                    | Missing     | —                                         |       |
| 0x85      | Tile Editor Open                    | Missing     | —                                         |       |
| 0x96      | Craft Item                          | Missing     | —                                         |       |
| 0x97      | Trade Item                          | Missing     | —                                         |       |
| 0x98      | Debug Options                       | Missing     | —                                         |       |
| 0x99      | Server Settings Changed             | Missing     | —                                         |       |
| 0x9A      | Texture                             | Missing     | —                                         |       |
| 0x9B      | Chunk Visibility Area               | Missing     | —                                         |       |
| 0x9C      | Update Progress                     | Missing     | —                                         |       |
| 0x9D      | Texture Change                      | Missing     | —                                         |       |
| 0x9E      | Update Game Rule Progress           | Missing     | —                                         |       |
| 0x9F      | Kick Player                         | Missing     | —                                         |       |
| 0xA0      | Texture And Geometry                | Missing     | —                                         |       |
| 0xA1      | Texture And Geometry Change         | Missing     | —                                         |       |
| 0xA2      | Move Entity Small                   | Missing     | —                                         |       |
| 0xA3      | Move Entity Small Position          | Missing     | —                                         |       |
| 0xA4      | Move Entity Small Rotation          | Missing     | —                                         |       |
| 0xA5      | Move Entity Small Position Rotation | Missing     | —                                         |       |
| 0xA6      | XZ                                  | Missing     | —                                         |       |
| 0xA7      | Game Command                        | Missing     | —                                         |       |
| 0xC8      | Award Stat                          | Missing     | —                                         |       |
| 0xC9      | Player Info                         | Missing     | —                                         |       |
| 0xCA      | Player Abilities                    | Missing     | —                                         |       |
| 0xCD      | Client Command                      | Implemented | `LceClientCommandTranslator`              |       |
| 0xCE      | Set Objective                       | Missing     | —                                         |       |
| 0xCF      | Set Score                           | Missing     | —                                         |       |
| 0xD0      | Set Display Objective               | Missing     | —                                         |       |
| 0xD1      | Set Player Team                     | Missing     | —                                         |       |
| 0xFA      | Custom Payload                      | Missing     | —                                         |       |
| 0xFE      | Get Info                            | Missing     | —                                         |       |
| 0xFF      | Disconnect                          | Missing     | —                                         |       |
