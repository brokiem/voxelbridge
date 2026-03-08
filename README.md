# VoxelBridge

VoxelBridge is a translation proxy that allows Minecraft Legacy Console Edition (LCE) to connect to Minecraft Java Edition servers.

![LCE and JE Screenshot](assets/LCEandJE.png)

> [!NOTE]
> Many gameplay packets are still missing or incomplete. Expect bugs and missing features. See **[/PACKET_TRACKING.md](PACKET_TRACKING.md)**

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

It will generate a `config.yml` file when you run the proxy for the first time.

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
5.  The proxy should appear in the LAN games list.