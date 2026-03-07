package id.brokiem.voxelbridge.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Getter
@Slf4j
public class ProxyConfig {
    private final String targetHost;
    private final int targetPort;
    private final String listenHost;
    private final int listenPort;
    private final int javaProtocolVersion;
    private final int lceProtocolVersion;
    private final int lceNetVersion;
    private final int levelMaxWidth;
    private final int hellLevelMaxScale;

    private ProxyConfig(Builder builder) {
        this.targetHost = builder.targetHost;
        this.targetPort = builder.targetPort;
        this.listenHost = builder.listenHost;
        this.listenPort = builder.listenPort;
        this.javaProtocolVersion = builder.javaProtocolVersion;
        this.lceProtocolVersion = builder.lceProtocolVersion;
        this.lceNetVersion = builder.lceNetVersion;
        this.levelMaxWidth = builder.levelMaxWidth;
        this.hellLevelMaxScale = builder.hellLevelMaxScale;
    }

    public static ProxyConfig load(File file) {
        if (!file.exists()) {
            log.info("Configuration file not found, creating default: {}", file.getAbsolutePath());
            createDefaultConfig(file);
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            return fromMap(data);
        } catch (Exception e) {
            log.error("Failed to load configuration file: {}", e.getMessage(), e);
            log.warn("Using default configuration.");
            return builder().build();
        }
    }

    private static void createDefaultConfig(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("# ============================================\n");
            writer.write("# VoxelBridge Configuration\n");
            writer.write("# ============================================\n");
            writer.write("# This file controls how the proxy behaves.\n");
            writer.write("# You normally only need to edit the Java server\n");
            writer.write("# address if you want to connect to a different server.\n");
            writer.write("# ============================================\n\n");

            writer.write("# The Java Edition server the proxy should connect to\n");
            writer.write("java-server:\n");
            writer.write("  # IP address or hostname of the Java server\n");
            writer.write("  host: \"127.0.0.1\"\n\n");
            writer.write("  # Port of the Java server (default Minecraft port is 25565)\n");
            writer.write("  port: 25565\n\n");

            writer.write("# Proxy settings\n");
            writer.write("proxy:\n");
            writer.write("  # IP address the proxy should listen on\n");
            writer.write("  # 0.0.0.0 means it will accept connections from anywhere\n");
            writer.write("  listen-host: \"0.0.0.0\"\n\n");
            writer.write("  # Port the proxy listens on for LCE clients\n");
            writer.write("  listen-port: 25570\n");
        } catch (Exception e) {
            log.error("Failed to create default configuration file: {}", e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private static ProxyConfig fromMap(Map<String, Object> data) {
        Builder builder = builder();

        if (data != null) {
            if (data.containsKey("java-server")) {
                Map<String, Object> javaServer = (Map<String, Object>) data.get("java-server");
                if (javaServer != null) {
                    if (javaServer.containsKey("host")) {
                        builder.targetHost((String) javaServer.get("host"));
                    }
                    if (javaServer.containsKey("port")) {
                        builder.targetPort((Integer) javaServer.get("port"));
                    }
                }
            }

            if (data.containsKey("proxy")) {
                Map<String, Object> proxy = (Map<String, Object>) data.get("proxy");
                if (proxy != null) {
                    if (proxy.containsKey("listen-host")) {
                        builder.listenHost((String) proxy.get("listen-host"));
                    }
                    if (proxy.containsKey("listen-port")) {
                        builder.listenPort((Integer) proxy.get("listen-port"));
                    }
                }
            }
        }

        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String targetHost = "127.0.0.1";
        private int targetPort = 25565;
        private String listenHost = "0.0.0.0";
        private int listenPort = 25570;
        private int javaProtocolVersion = 4;
        private int lceProtocolVersion = 78;
        private int lceNetVersion = 560;
        private int levelMaxWidth = 320;
        private int hellLevelMaxScale = 8;

        private static void validatePort(String name, int value) {
            if (value < 1 || value > 65535) {
                throw new IllegalStateException(name + " must be between 1 and 65535");
            }
        }

        private static void validatePositive(String name, int value) {
            if (value <= 0) {
                throw new IllegalStateException(name + " must be positive");
            }
        }

        public Builder targetHost(String targetHost) {
            this.targetHost = targetHost;
            return this;
        }

        public Builder targetPort(int targetPort) {
            this.targetPort = targetPort;
            return this;
        }

        public Builder listenHost(String listenHost) {
            this.listenHost = listenHost;
            return this;
        }

        public Builder listenPort(int listenPort) {
            this.listenPort = listenPort;
            return this;
        }

        public Builder javaProtocolVersion(int javaProtocolVersion) {
            this.javaProtocolVersion = javaProtocolVersion;
            return this;
        }

        public Builder lceProtocolVersion(int lceProtocolVersion) {
            this.lceProtocolVersion = lceProtocolVersion;
            return this;
        }

        public Builder lceNetVersion(int lceNetVersion) {
            this.lceNetVersion = lceNetVersion;
            return this;
        }

        public Builder levelMaxWidth(int levelMaxWidth) {
            this.levelMaxWidth = levelMaxWidth;
            return this;
        }

        public Builder hellLevelMaxScale(int hellLevelMaxScale) {
            this.hellLevelMaxScale = hellLevelMaxScale;
            return this;
        }

        public ProxyConfig build() {
            targetHost = Objects.requireNonNull(targetHost, "targetHost");
            if (targetHost.isBlank()) {
                throw new IllegalStateException("targetHost must not be blank");
            }
            listenHost = Objects.requireNonNull(listenHost, "listenHost");
            if (listenHost.isBlank()) {
                throw new IllegalStateException("listenHost must not be blank");
            }
            validatePort("targetPort", targetPort);
            validatePort("listenPort", listenPort);
            validatePositive("javaProtocolVersion", javaProtocolVersion);
            validatePositive("lceProtocolVersion", lceProtocolVersion);
            validatePositive("lceNetVersion", lceNetVersion);
            validatePositive("levelMaxWidth", levelMaxWidth);
            validatePositive("hellLevelMaxScale", hellLevelMaxScale);
            return new ProxyConfig(this);
        }
    }
}
