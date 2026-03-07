# Minecraft Protocol — PC 1.7

---

## Table of Contents

- [Types](#types)
- [handshaking / toClient / types](#handshaking--toclient--types)
- [handshaking / toServer / types](#handshaking--toserver--types)
- [status / toClient / types](#status--toclient--types)
- [status / toServer / types](#status--toserver--types)
- [login / toClient / types](#login--toclient--types)
- [login / toServer / types](#login--toserver--types)
- [play / toClient / types](#play--toclient--types)
- [play / toServer / types](#play--toserver--types)

---

## Types

### slot

| Field Name | Field Type | Notes |
| --- | --- | --- |
| blockId | i16 | |
| **if blockId == -1** | void | |
| **if blockId == default:** | | |
| &nbsp;&nbsp;itemCount | i8 | |
| &nbsp;&nbsp;itemDamage | i16 | |
| &nbsp;&nbsp;nbtData | compressedNbt | |

---

### position_iii

| Field Name | Field Type | Notes |
| --- | --- | --- |
| x | i32 | |
| y | i32 | |
| z | i32 | |

---

### position_isi

| Field Name | Field Type | Notes |
| --- | --- | --- |
| x | i32 | |
| y | i16 | |
| z | i32 | |

---

### position_ibi

| Field Name | Field Type | Notes |
| --- | --- | --- |
| x | i32 | |
| y | u8 | |
| z | i32 | |

---

### entityMetadataItem

| Field Name | Field Type | Notes |
| --- | --- | --- |
| **entityMetadataItem** (switch on compareTo) | | |
| &nbsp;&nbsp;is 0 | i8 | |
| &nbsp;&nbsp;is 1 | i16 | |
| &nbsp;&nbsp;is 2 | i32 | |
| &nbsp;&nbsp;is 3 | f32 | |
| &nbsp;&nbsp;is 4 | string | |
| &nbsp;&nbsp;is 5 | slot | |
| &nbsp;&nbsp;is 6: x, y, z | i32, i32, i32 | |
| &nbsp;&nbsp;is 7: pitch, yaw, roll | f32, f32, f32 | |

---

## handshaking / toClient / types

### packet

| Field Name | Field Type | Notes |
| --- | --- | --- |
| name | varint enum | |

---

## handshaking / toServer / types

### packet_set_protocol `0x00`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| protocolVersion | varint | |
| serverHost | string | |
| serverPort | u16 | |
| nextState | varint | |

---

### packet_legacy_server_list_ping `0xfe`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| payload | u8 | |

---

### packet

| Field Name | Field Type | Notes |
| --- | --- | --- |
| name | varint enum (0=set_protocol, 254=legacy_server_list_ping) | |
| **params** (switch on name) | | |
| &nbsp;&nbsp;is set_protocol | packet_set_protocol | |
| &nbsp;&nbsp;is legacy_server_list_ping | packet_legacy_server_list_ping | |

---

## status / toClient / types

### packet_server_info `0x00`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| response | string | |

---

### packet_ping `0x01`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| time | i64 | |

---

### packet

| Field Name | Field Type | Notes |
| --- | --- | --- |
| name | varint enum (0=server_info, 1=ping) | |
| **params** (switch on name) | | |
| &nbsp;&nbsp;is server_info | packet_server_info | |
| &nbsp;&nbsp;is ping | packet_ping | |

---

## status / toServer / types

### packet_ping_start `0x00`

*(no fields)*

---

### packet_ping `0x01`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| time | i64 | |

---

### packet

| Field Name | Field Type | Notes |
| --- | --- | --- |
| name | varint enum (0=ping_start, 1=ping) | |
| **params** (switch on name) | | |
| &nbsp;&nbsp;is ping_start | packet_ping_start | |
| &nbsp;&nbsp;is ping | packet_ping | |

---

## login / toClient / types

### packet_disconnect `0x00`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| reason | string | |

---

### packet_encryption_begin `0x01`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| serverId | string | |

---

### packet_success `0x02`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| uuid | string | |
| username | string | |

---

### packet

| Field Name | Field Type | Notes |
| --- | --- | --- |
| name | varint enum (0=disconnect, 1=encryption_begin, 2=success) | |
| **params** (switch on name) | | |
| &nbsp;&nbsp;is disconnect | packet_disconnect | |
| &nbsp;&nbsp;is encryption_begin | packet_encryption_begin | |
| &nbsp;&nbsp;is success | packet_success | |

---

## login / toServer / types

### packet_login_start `0x00`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| username | string | |

---

### packet_encryption_begin `0x01`

*(no fields)*

---

### packet

| Field Name | Field Type | Notes |
| --- | --- | --- |
| name | varint enum (0=login_start, 1=encryption_begin) | |
| **params** (switch on name) | | |
| &nbsp;&nbsp;is login_start | packet_login_start | |
| &nbsp;&nbsp;is encryption_begin | packet_encryption_begin | |

---

## play / toClient / types

### Packet Index

| ID | Packet Name |
| --- | --- |
| 0x00 | packet_keep_alive |
| 0x01 | packet_login |
| 0x02 | packet_chat |
| 0x03 | packet_update_time |
| 0x04 | packet_entity_equipment |
| 0x05 | packet_spawn_position |
| 0x06 | packet_update_health |
| 0x07 | packet_respawn |
| 0x08 | packet_position |
| 0x09 | packet_held_item_slot |
| 0x0a | packet_bed |
| 0x0b | packet_animation |
| 0x0c | packet_named_entity_spawn |
| 0x0d | packet_collect |
| 0x0e | packet_spawn_entity |
| 0x0f | packet_spawn_entity_living |
| 0x10 | packet_spawn_entity_painting |
| 0x11 | packet_spawn_entity_experience_orb |
| 0x12 | packet_entity_velocity |
| 0x13 | packet_entity_destroy |
| 0x14 | packet_entity |
| 0x15 | packet_rel_entity_move |
| 0x16 | packet_entity_look |
| 0x17 | packet_entity_move_look |
| 0x18 | packet_entity_teleport |
| 0x19 | packet_entity_head_rotation |
| 0x1a | packet_entity_status |
| 0x1b | packet_attach_entity |
| 0x1c | packet_entity_metadata |
| 0x1d | packet_entity_effect |
| 0x1e | packet_remove_entity_effect |
| 0x1f | packet_experience |
| 0x20 | packet_update_attributes |
| 0x21 | packet_map_chunk |
| 0x22 | packet_multi_block_change |
| 0x23 | packet_block_change |
| 0x24 | packet_block_action |
| 0x25 | packet_block_break_animation |
| 0x26 | packet_map_chunk_bulk |
| 0x27 | packet_explosion |
| 0x28 | packet_world_event |
| 0x29 | packet_named_sound_effect |
| 0x2a | packet_world_particles |
| 0x2b | packet_game_state_change |
| 0x2c | packet_spawn_entity_weather |
| 0x2d | packet_open_window |
| 0x2e | packet_close_window |
| 0x2f | packet_set_slot |
| 0x30 | packet_window_items |
| 0x31 | packet_craft_progress_bar |
| 0x32 | packet_transaction |
| 0x33 | packet_update_sign |
| 0x34 | packet_map |
| 0x35 | packet_tile_entity_data |
| 0x36 | packet_open_sign_entity |
| 0x37 | packet_statistics |
| 0x38 | packet_player_info |
| 0x39 | packet_abilities |
| 0x3a | packet_tab_complete |
| 0x3b | packet_scoreboard_objective |
| 0x3c | packet_scoreboard_score |
| 0x3d | packet_scoreboard_display_objective |
| 0x3e | packet_scoreboard_team |
| 0x3f | packet_custom_payload |
| 0x40 | packet_kick_disconnect |

---

### packet_keep_alive `0x00`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| keepAliveId | i32 | |

---

### packet_login `0x01`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| gameMode | u8 | |
| dimension | i8 | |
| difficulty | u8 | |
| maxPlayers | u8 | |
| levelType | string | |

---

### packet_chat `0x02`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| message | string | |

---

### packet_update_time `0x03`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| age | i64 | |
| time | i64 | |

---

### packet_entity_equipment `0x04`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| slot | i16 | |
| item | slot | |

---

### packet_spawn_position `0x05`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| location | position_iii | |

---

### packet_update_health `0x06`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| health | f32 | |
| food | i16 | |
| foodSaturation | f32 | |

---

### packet_respawn `0x07`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| dimension | i32 | |
| difficulty | u8 | |
| gamemode | u8 | |
| levelType | string | |

---

### packet_position `0x08`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| x | f64 | |
| y | f64 | |
| z | f64 | |
| yaw | f32 | |
| pitch | f32 | |
| onGround | bool | |

---

### packet_held_item_slot `0x09`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| slot | i8 | |

---

### packet_bed `0x0a`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| location | position_ibi | |

---

### packet_animation `0x0b`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | varint | |
| animation | u8 | |

---

### packet_named_entity_spawn `0x0c`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | varint | |
| playerUUID | string | |
| playerName | string | |
| data length | varint | |
| data array: name | string | |
| data array: value | string | |
| data array: signature | string | |
| x | i32 | |
| y | i32 | |
| z | i32 | |
| yaw | i8 | |
| pitch | i8 | |
| currentItem | i16 | |
| metadata | entityMetadata | |

---

### packet_collect `0x0d`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| collectedEntityId | i32 | |
| collectorEntityId | i32 | |

---

### packet_spawn_entity `0x0e`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | varint | |
| type | i8 | |
| x | i32 | |
| y | i32 | |
| z | i32 | |
| pitch | i8 | |
| yaw | i8 | |
| objectData: intField | i32 | |
| **velocityX** (if intField == 0: void, else: i16) | | |
| **velocityY** (if intField == 0: void, else: i16) | | |
| **velocityZ** (if intField == 0: void, else: i16) | | |

---

### packet_spawn_entity_living `0x0f`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | varint | |
| type | u8 | |
| x | i32 | |
| y | i32 | |
| z | i32 | |
| yaw | i8 | |
| pitch | i8 | |
| headPitch | i8 | |
| velocityX | i16 | |
| velocityY | i16 | |
| velocityZ | i16 | |
| metadata | entityMetadata | |

---

### packet_spawn_entity_painting `0x10`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | varint | |
| title | string | |
| location | position_iii | |
| direction | i32 | |

---

### packet_spawn_entity_experience_orb `0x11`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | varint | |
| x | i32 | |
| y | i32 | |
| z | i32 | |
| count | i16 | |

---

### packet_entity_velocity `0x12`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| velocityX | i16 | |
| velocityY | i16 | |
| velocityZ | i16 | |

---

### packet_entity_destroy `0x13`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityIds length | i8 | |
| entityIds array | i32 | |

---

### packet_entity `0x14`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |

---

### packet_rel_entity_move `0x15`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| dX | i8 | |
| dY | i8 | |
| dZ | i8 | |

---

### packet_entity_look `0x16`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| yaw | i8 | |
| pitch | i8 | |

---

### packet_entity_move_look `0x17`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| dX | i8 | |
| dY | i8 | |
| dZ | i8 | |
| yaw | i8 | |
| pitch | i8 | |

---

### packet_entity_teleport `0x18`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| x | i32 | |
| y | i32 | |
| z | i32 | |
| yaw | i8 | |
| pitch | i8 | |

---

### packet_entity_head_rotation `0x19`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| headYaw | i8 | |

---

### packet_entity_status `0x1a`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| entityStatus | i8 | |

---

### packet_attach_entity `0x1b`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| vehicleId | i32 | |
| leash | bool | |

---

### packet_entity_metadata `0x1c`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| metadata | entityMetadata | |

---

### packet_entity_effect `0x1d`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| effectId | i8 | |
| amplifier | i8 | |
| duration | i16 | |

---

### packet_remove_entity_effect `0x1e`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| effectId | i8 | |

---

### packet_experience `0x1f`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| experienceBar | f32 | |
| level | i16 | |
| totalExperience | i16 | |

---

### packet_update_attributes `0x20`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| properties length | i32 | |
| properties array: key | string | |
| properties array: value | f64 | |
| properties array: modifiers length | i16 | |
| modifiers array: uuid | UUID | |
| modifiers array: amount | f64 | |
| modifiers array: operation | i8 | |

---

### packet_map_chunk `0x21`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| x | i32 | |
| z | i32 | |
| groundUp | bool | |
| bitMap | u16 | |
| addBitMap | u16 | |

---

### packet_multi_block_change `0x22`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| chunkX | i32 | |
| chunkZ | i32 | |
| dataLength | i32 | |
| records array: y | u8 | Length is `recordCount` |

---

### packet_block_change `0x23`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| location | position_ibi | |
| type | varint | |
| metadata | u8 | |

---

### packet_block_action `0x24`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| location | position_isi | |
| byte1 | u8 | |
| byte2 | u8 | |
| blockId | varint | |

---

### packet_block_break_animation `0x25`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | varint | |
| location | position_iii | |
| destroyStage | i8 | |

---

### packet_map_chunk_bulk `0x26`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| skyLightSent | bool | |
| meta array: x | i32 | Length is `chunkColumnCount` |
| meta array: z | i32 | |
| meta array: bitMap | u16 | |
| meta array: addBitMap | u16 | |

---

### packet_explosion `0x27`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| x | f32 | |
| y | f32 | |
| z | f32 | |
| radius | f32 | |
| affectedBlockOffsets length | i32 | |
| affectedBlockOffsets array: x | i8 | |
| affectedBlockOffsets array: y | i8 | |
| affectedBlockOffsets array: z | i8 | |
| playerMotionX | f32 | |
| playerMotionY | f32 | |
| playerMotionZ | f32 | |

---

### packet_world_event `0x28`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| effectId | i32 | |
| location | position_ibi | |
| data | i32 | |
| global | bool | |

---

### packet_named_sound_effect `0x29`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| soundName | string | |
| x | i32 | |
| y | i32 | |
| z | i32 | |
| volume | f32 | |
| pitch | u8 | |

---

### packet_world_particles `0x2a`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| particleName | string | |
| x | f32 | |
| y | f32 | |
| z | f32 | |
| offsetX | f32 | |
| offsetY | f32 | |
| offsetZ | f32 | |
| particleData | f32 | |
| particles | i32 | |

---

### packet_game_state_change `0x2b`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| reason | u8 | |
| gameMode | f32 | |

---

### packet_spawn_entity_weather `0x2c`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | varint | |
| type | i8 | |
| x | i32 | |
| y | i32 | |
| z | i32 | |

---

### packet_open_window `0x2d`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| windowId | u8 | |
| inventoryType | u8 | |
| windowTitle | string | |
| slotCount | u8 | |
| useProvidedTitle | bool | |
| **entityId** (if inventoryType == 11: i32, else: void) | | |

---

### packet_close_window `0x2e`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| windowId | u8 | |

---

### packet_set_slot `0x2f`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| windowId | i8 | |
| slot | i16 | |
| item | slot | |

---

### packet_window_items `0x30`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| windowId | u8 | |
| items length | i16 | |
| items array | slot | |

---

### packet_craft_progress_bar `0x31`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| windowId | u8 | |
| property | i16 | |
| value | i16 | |

---

### packet_transaction `0x32`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| windowId | u8 | |
| action | i16 | |
| accepted | bool | |

---

### packet_update_sign `0x33`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| location | position_isi | |
| text1 | string | |
| text2 | string | |
| text3 | string | |
| text4 | string | |

---

### packet_map `0x34`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| itemDamage | varint | |

---

### packet_tile_entity_data `0x35`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| location | position_isi | |
| action | u8 | |
| nbtData | compressedNbt | |

---

### packet_open_sign_entity `0x36`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| location | position_iii | |

---

### packet_statistics `0x37`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entries length | varint | |
| entries array: name | string | |
| entries array: value | varint | |

---

### packet_player_info `0x38`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| playerName | string | |
| online | bool | |
| ping | i16 | |

---

### packet_abilities `0x39`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| flags | i8 | |
| flyingSpeed | f32 | |
| walkingSpeed | f32 | |

---

### packet_tab_complete `0x3a`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| matches length | varint | |
| matches array | string | |

---

### packet_scoreboard_objective `0x3b`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| name | string | |
| displayText | string | |
| action | i8 | |

---

### packet_scoreboard_score `0x3c`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| itemName | string | |
| action | i8 | |
| **scoreName** (if action == 1: void, else: string) | | |
| **value** (if action == 1: void, else: i32) | | |

---

### packet_scoreboard_display_objective `0x3d`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| position | i8 | |
| name | string | |

---

### packet_scoreboard_team `0x3e`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| team | string | |
| mode | i8 | |
| **name** (if mode == 0 or 2: string, else: void) | | |
| **prefix** (if mode == 0 or 2: string, else: void) | | |
| **suffix** (if mode == 0 or 2: string, else: void) | | |
| **friendlyFire** (if mode == 0 or 2: i8, else: void) | | |
| **players** (if mode == 0, 3, or 4: i16 length + string array; else: void) | | |

---

### packet_custom_payload `0x3f`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| channel | string | |

---

### packet_kick_disconnect `0x40`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| reason | string | |

---

## play / toServer / types

### Packet Index

| ID | Packet Name |
| --- | --- |
| 0x00 | packet_keep_alive |
| 0x01 | packet_chat |
| 0x02 | packet_use_entity |
| 0x03 | packet_flying |
| 0x04 | packet_position |
| 0x05 | packet_look |
| 0x06 | packet_position_look |
| 0x07 | packet_block_dig |
| 0x08 | packet_block_place |
| 0x09 | packet_held_item_slot |
| 0x0a | packet_arm_animation |
| 0x0b | packet_entity_action |
| 0x0c | packet_steer_vehicle |
| 0x0d | packet_close_window |
| 0x0e | packet_window_click |
| 0x0f | packet_transaction |
| 0x10 | packet_set_creative_slot |
| 0x11 | packet_enchant_item |
| 0x12 | packet_update_sign |
| 0x13 | packet_abilities |
| 0x14 | packet_tab_complete |
| 0x15 | packet_settings |
| 0x16 | packet_client_command |
| 0x17 | packet_custom_payload |

---

### packet_keep_alive `0x00`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| keepAliveId | i32 | |

---

### packet_chat `0x01`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| message | string | |

---

### packet_use_entity `0x02`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| target | i32 | |
| mouse | i8 | |
| **x** (if mouse == 2: f32, else: void) | | |
| **y** (if mouse == 2: f32, else: void) | | |
| **z** (if mouse == 2: f32, else: void) | | |

---

### packet_flying `0x03`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| onGround | bool | |

---

### packet_position `0x04`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| x | f64 | |
| stance | f64 | |
| y | f64 | |
| z | f64 | |
| onGround | bool | |

---

### packet_look `0x05`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| yaw | f32 | |
| pitch | f32 | |
| onGround | bool | |

---

### packet_position_look `0x06`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| x | f64 | |
| stance | f64 | |
| y | f64 | |
| z | f64 | |
| yaw | f32 | |
| pitch | f32 | |
| onGround | bool | |

---

### packet_block_dig `0x07`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| status | i8 | |
| location | position_ibi | |
| face | i8 | |

---

### packet_block_place `0x08`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| location | position_ibi | |
| direction | i8 | |
| heldItem | slot | |
| cursorX | i8 | |
| cursorY | i8 | |
| cursorZ | i8 | |

---

### packet_held_item_slot `0x09`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| slotId | i16 | |

---

### packet_arm_animation `0x0a`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| animation | i8 | |

---

### packet_entity_action `0x0b`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| entityId | i32 | |
| actionId | i8 | |
| jumpBoost | i32 | |

---

### packet_steer_vehicle `0x0c`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| sideways | f32 | |
| forward | f32 | |
| jump | bool | |
| unmount | bool | |

---

### packet_close_window `0x0d`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| windowId | u8 | |

---

### packet_window_click `0x0e`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| windowId | i8 | |
| slot | i16 | |
| mouseButton | i8 | |
| action | i16 | |
| mode | i8 | |
| item | slot | |

---

### packet_transaction `0x0f`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| windowId | i8 | |
| action | i16 | |
| accepted | bool | |

---

### packet_set_creative_slot `0x10`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| slot | i16 | |
| item | slot | |

---

### packet_enchant_item `0x11`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| windowId | i8 | |
| enchantment | i8 | |

---

### packet_update_sign `0x12`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| location | position_isi | |
| text1 | string | |
| text2 | string | |
| text3 | string | |
| text4 | string | |

---

### packet_abilities `0x13`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| flags | i8 | |
| flyingSpeed | f32 | |
| walkingSpeed | f32 | |

---

### packet_tab_complete `0x14`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| text | string | |

---

### packet_settings `0x15`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| locale | string | |
| viewDistance | i8 | |
| chatFlags | i8 | |
| chatColors | bool | |
| difficulty | u8 | |
| showCape | bool | |

---

### packet_client_command `0x16`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| payload | i8 | |

---

### packet_custom_payload `0x17`

| Field Name | Field Type | Notes |
| --- | --- | --- |
| channel | string | |