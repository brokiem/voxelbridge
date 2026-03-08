# VoxelBridge

VoxelBridge is a translation proxy that allows **Minecraft Legacy Console Edition (LCE) clients to connect to Minecraft Java Edition servers**. It works by translating network packets between the two protocols in real-time.

![LCE and JE Screenshot](assets/LCEandJE.png)

> [!NOTE]
> Many gameplay packets are still missing or incomplete. Expect bugs and missing features.

## Architecture

VoxelBridge acts as a "man-in-the-middle" server:

1.  **Proxy Server**: Listens for incoming connections from LCE clients (default port 25570).
2.  **Discovery Broadcaster**: Sends UDP broadcasts so LCE clients can automatically find the proxy on the LAN.
3.  **Packet Decoding**: Decodes raw bytes from LCE clients into packet objects.
4.  **Translation Layer**:
    *   **Serverbound (LCE -> Java)**: Translates LCE packets (like movement, input) into Java Edition packets.
    *   **Clientbound (Java -> LCE)**: Translates Java Edition packets (like chunk data, entity updates) into LCE packets.
5.  **Java Client**: Forwards the translated packets to the target Java Edition server.

## Configuration

When you run VoxelBridge for the first time, it will generate a `config.yml` file.

## Running the Proxy

### Prerequisites

*   Java 21 or newer
*   Maven (for building)

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/voxelbridge-1.0-SNAPSHOT.jar
```

### Connecting

1.  Start your target Minecraft Java Edition server (1.7.2).
2.  Configure `config.yml` to point to your Java server.
3.  Run VoxelBridge.
4.  Open Minecraft Legacy Console Edition (e.g., Windows, Xbox 360, PS3) on the same network.
5.  The proxy should appear in the LAN games list. If not, you may need to manually connect to the proxy's IP address.

## Implementation Status

We track the implementation status of all packets in a separate document. See **[/PACKET_TRACKING.md](PACKET_TRACKING.md)**

This file lists which packets are fully implemented, partially implemented, or missing.
