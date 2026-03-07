# VoxelBridge

VoxelBridge is a protocol translation proxy that allows **Minecraft Legacy Console Edition (LCE) clients to connect to Minecraft Java Edition servers**. It works by translating network packets between the two protocols in real-time.

## Project Status

> [!NOTE]
> This project is currently in an experimental state. While the core networking and handshake sequence are implemented, many gameplay packets are still missing or incomplete. Expect bugs and missing features.

## Features

- **Protocol Translation**: Translates packets between LCE (Protocol 78) and Java Edition 1.7.2 (Protocol 4).
- **Configurable Target**: Connect to any Java Edition server by editing `config.yml`.
- **LAN Discovery**: Broadcasts the proxy to LCE clients on the local network so it appears in the server list.
- **Session Management**: Handles player login, handshake, and basic session state.

## Architecture Overview

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

```yaml
# ============================================
# VoxelBridge Configuration
# ============================================

# The Java Edition server the proxy should connect to
java-server:
  # IP address or hostname of the Java server
  host: "127.0.0.1"

  # Port of the Java server (default Minecraft port is 25565)
  port: 25565

# Proxy settings
proxy:
  # IP address the proxy should listen on
  # 0.0.0.0 means it will accept connections from anywhere
  listen-host: "0.0.0.0"

  # Port the proxy listens on for LCE clients
  listen-port: 25570
```

### Settings

*   `java-server.host`: The IP address of the target Minecraft Java server.
*   `java-server.port`: The port of the target Minecraft Java server.
*   `proxy.listen-host`: The interface the proxy binds to. Use `0.0.0.0` to listen on all interfaces.
*   `proxy.listen-port`: The port LCE clients will connect to.

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

## Packet Implementation Status

We track the implementation status of all packets in a separate document.

👉 **[View Packet Implementation Status](PACKET_TRACKING.md)**

This file lists which packets are fully implemented, partially implemented, or missing.
