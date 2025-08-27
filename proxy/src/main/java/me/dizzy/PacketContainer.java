package me.dizzy;

import ch.challangerson.adapter.redis.RedisManager;
import ch.challangerson.adapter.redis.packet.PacketHandler;
import ch.challangerson.adapter.redis.packet.PacketSender;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import me.dizzy.player.SectorPlayer;
import me.dizzy.util.ChatUtil;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class PacketContainer {

    private final PacketHandler packetHandler;
    private final RedisManager redisManager;
    private final ProxyServer proxyServer;
    private final PacketSender packetSender;
    private final Logger logger;


    public PacketContainer(PacketHandler packetHandler, RedisManager redisManager, ProxyServer proxyServer, PacketSender packetSender, Logger logger) {
        this.packetHandler = packetHandler;
        this.redisManager = redisManager;
        this.proxyServer = proxyServer;
        this.packetSender = packetSender;
        this.logger = logger;
        this.subscribeChannels();
        this.registerPackets();
    }

    private void subscribeChannels() {
        this.redisManager.subscribe("ch_proxy", "ch_proxy_1");
        System.out.println("Subscribing to channels");
    }

    private boolean isServerAlive(ServerPing serverPing) {
        return serverPing != null && serverPing.getVersion() != null && serverPing.getPlayers().isPresent();
    }

    private void registerPackets() {
        this.packetHandler.registerPacketHandler("SECTOR_PLAYER", (packet, arg) -> {
            SectorPlayer sectorPlayer = (SectorPlayer) packet;

            Optional<Player> optionalPlayer = this.proxyServer.getPlayer(sectorPlayer.getUniqueId());

            if(optionalPlayer.isEmpty()) {
                this.logger.warn("No player found for UUID " + sectorPlayer.getUniqueId());
                return null;
            }

            Player player = optionalPlayer.get();

            ChatUtil.sendMessage(player, "&8>> &aPróba łączenia z sektorem...");

            String toSectorName = sectorPlayer.getToSectorName();

            Optional<RegisteredServer> optionalToServer = this.proxyServer.getServer(toSectorName);

            if(optionalToServer.isEmpty()) {
                ChatUtil.sendMessage(player, "&8>> &cSektor &f" + toSectorName + " &cnie istnieje!");
                for(int i = 0; i<=10; i++) {
                    logger.info("CWELU NIE MA SERWERA {}", toSectorName);
                }
                return null;
            }

            RegisteredServer toServer = optionalToServer.get();

            toServer.ping().orTimeout(1, TimeUnit.SECONDS).thenAccept(serverPing -> {

                if(!isServerAlive(serverPing)) {
                    ChatUtil.sendMessage(player, "&8>> &cSerwer jest aktualnie wyłączony! &7(&f" + toSectorName + ")");
                    return;
                }

                player.createConnectionRequest(toServer).connect().thenAccept(connection -> {
                   if(connection.isSuccessful()) {
                          ChatUtil.sendMessage(player, "&8>> &aPołączono z sektorem &f" + toSectorName);
                            System.out.println("ch_" + toSectorName.toLowerCase());
                          this.packetSender.sendPacket("ch_" + toSectorName.toLowerCase(), sectorPlayer);
                     } else {
                          ChatUtil.sendMessage(player, "&8>> &cWystąpił błąd z połączeniem! &7(&f" + toSectorName + "&7)!");
                   }
                });

            }).exceptionally(throwable -> {;
                ChatUtil.sendMessage(player, "&8>> &cSerwer jest aktualnie wyłączony! &7(&f" + toSectorName + ")");
                return null;
            });

            return null;
        });

    }
}
