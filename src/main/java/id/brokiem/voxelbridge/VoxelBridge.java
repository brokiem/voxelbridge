package id.brokiem.voxelbridge;

import id.brokiem.voxelbridge.server.ProxyConfig;
import id.brokiem.voxelbridge.server.ProxyServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VoxelBridge {
    public static void main(String[] args) {
        ProxyConfig config = ProxyConfig.builder()
                .listenPort(25565)
                .targetHost("127.0.0.1")
                .targetPort(25569)
                .build();

        ProxyServer proxyServer = new ProxyServer(config);
        Thread shutdownHook = new Thread(proxyServer::close, "voxelbridge-shutdown");
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        log.info("---");
        log.info("VoxelBridge Proxy");
        log.info("---");

        try {
            proxyServer.start();
        } catch (InterruptedException e) {
            log.error("[Proxy] Interrupted: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
        } finally {
            proxyServer.close();

            try {
                Runtime.getRuntime().removeShutdownHook(shutdownHook);
            } catch (IllegalStateException ignored) {
                // JVM shutdown is already in progress.
            }
        }
    }
}
