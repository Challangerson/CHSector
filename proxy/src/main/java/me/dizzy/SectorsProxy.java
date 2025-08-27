package me.dizzy;

import ch.challangerson.Shared;
import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.util.Favicon;
import me.dizzy.api.RestApi;
import me.dizzy.listener.ConfigurationEvent;
import me.dizzy.listener.GameProfileEvent;
import me.dizzy.listener.PermissionListener;
import me.dizzy.listener.PreLogin;
import me.dizzy.user.ProxyUsers;
import me.dizzy.user.rank.RankManager;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;

@Plugin(
    id = "proxy",
    name = "proxy",
    version = "1.0"
    ,description = "Plugin for plots for minecraft"
    ,url = "https://challangerson.pl"
    ,authors = {"Challagnerson"}
)
public class SectorsProxy {

    private final Logger logger;
    private static SectorsProxy sectorsProxy;
    private final ProxyServer server;
    private final RankManager rankManager;
    private final RestApi restApi;
    private final ProxyUsers proxyUsers;
    private Favicon favicon;
    private boolean loadFavicon;
    private Shared shared;

    @Inject
    public SectorsProxy(Logger logger, ProxyServer server) {
        sectorsProxy = this;
        this.logger = logger;
        this.server = server;
        this.proxyUsers = new ProxyUsers();
        this.rankManager = new RankManager();
        this.restApi = new RestApi();
        this.shared = new Shared();
        try {
            this.favicon = Favicon.create(Path.of("favicon.png"));
            this.loadFavicon = true;
        } catch (Exception e) {
            this.logger.error("Failed to load favicon: {}", e.getMessage());
            this.loadFavicon = false;
            this.favicon = null; // Fallback if favicon loading fails
        }
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        sectorsProxy = this;
        new PacketContainer(this.shared.getPacketHandler(), this.shared.getRedisManager(), this.server, this.shared.getPacketSender(), this.logger);
        this.registerListeners();
        logger.info("Sectors Proxy has been initialized!");
    }


    private void registerListeners() {
        List.of(
                new GameProfileEvent(this.restApi, this.logger, this.proxyUsers),
                new PreLogin(this.restApi, this.proxyUsers),
                new PermissionListener(this.proxyUsers),
                new ConfigurationEvent(this.favicon, this.loadFavicon)
        ).forEach(event -> this.server.getEventManager().register(this, event));
    }

    public static SectorsProxy getInstance() {
        return sectorsProxy;
    }

    public RankManager getRankManager() {
        return this.rankManager;
    }
}
