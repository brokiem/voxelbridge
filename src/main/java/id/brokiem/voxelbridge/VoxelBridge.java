package id.brokiem.voxelbridge;

import id.brokiem.voxelbridge.server.ProxyConfig;
import id.brokiem.voxelbridge.server.ProxyServer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class VoxelBridge {
    public static void main(String[] args) {
        log.info("---");
        log.info("VoxelBridge Proxy");
        log.info("---");

        File configFile = new File("config.yml");
        ProxyConfig config = ProxyConfig.load(configFile);

        ProxyServer proxyServer = new ProxyServer(config);
        Thread shutdownHook = new Thread(proxyServer::close, "voxelbridge-shutdown");
        Runtime.getRuntime().addShutdownHook(shutdownHook);

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
