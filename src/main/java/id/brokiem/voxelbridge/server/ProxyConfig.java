package id.brokiem.voxelbridge.server;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ProxyConfig {
    private final String targetHost;
    private final int targetPort;
    private final int listenPort;
    private final int javaProtocolVersion;
    private final int lceProtocolVersion;
    private final int lceNetVersion;
    private final int levelMaxWidth;
    private final int hellLevelMaxScale;

    private ProxyConfig(Builder builder) {
        this.targetHost = builder.targetHost;
        this.targetPort = builder.targetPort;
        this.listenPort = builder.listenPort;
        this.javaProtocolVersion = builder.javaProtocolVersion;
        this.lceProtocolVersion = builder.lceProtocolVersion;
        this.lceNetVersion = builder.lceNetVersion;
        this.levelMaxWidth = builder.levelMaxWidth;
        this.hellLevelMaxScale = builder.hellLevelMaxScale;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String targetHost = "127.0.0.1";
        private int targetPort = 25569;
        private int listenPort = 25565;
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
