package me.dizzy.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.config.ProxyConfig;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;
import java.util.UUID;

public class ConfigurationEvent {

    private final Favicon favicon;
    private final boolean loadFavicon;

    public ConfigurationEvent(Favicon favicon, boolean loadFavicon) {
        this.favicon = favicon;
        this.loadFavicon = loadFavicon;
    }


    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing ping = event.getPing();

        ServerPing.Builder builder = ServerPing.builder();

        builder.description(MiniMessage.miniMessage().deserialize("&8&l<&7&m<&7&m----------------&f&l<&6&m------&f&[ &f&lMED&e&lPVP &f&l]>&6&m-----&7&m----------------&7&l>&8&l>\n" +
                "\n" +
                "    &fZyczymy wesolych swiat, spedzcie je jak najlepiej!"));

        builder.samplePlayers(
                List.of(
                    new ServerPing.SamplePlayer("Maciej", UUID.randomUUID()),
                    new  ServerPing.SamplePlayer("Dizzy", UUID.randomUUID())
                )
        );

        if(this.loadFavicon && this.favicon != null) {
            builder.favicon(this.favicon);
        }

        builder.build();

    }
}
