package me.dizzy;

import ch.challangerson.adapter.redis.RedisManager;
import ch.challangerson.adapter.redis.packet.PacketHandler;
import ch.challangerson.sector.Sector;
import ch.challangerson.sector.SectorManager;
import ch.challangerson.sector.SectorType;
import me.dizzy.helper.ConnectHelper;
import me.dizzy.player.SectorPlayer;

public class PacketContainer {
    private final PacketHandler packetHandler;
    private final RedisManager redisManager;
    private final Sector sector;
    private final SectorManager sectorManager;

    public PacketContainer(PacketHandler packetHandler, RedisManager redisManager, Sector sector, SectorManager sectorManager) {
        this.packetHandler = packetHandler;
        this.redisManager = redisManager;
        this.sector = sector;
        this.sectorManager = sectorManager;
        this.subscribeChannels();
        this.registerPackets();
    }

    private void subscribeChannels() {
        System.out.println("Subscribing to channels");
        this.redisManager.subscribe("ch_sector",
                "ch_" + this.sector.getId().toLowerCase(),
                (sector.getSectorType().equals(SectorType.SPAWN)
                        ? "ch_spawn" : "ch_game"));
    }

    private void registerPackets() {
        this.packetHandler.registerPacketHandler("SECTOR_PLAYER", (packet, arg) -> {
            SectorPlayer sectorPlayer = (SectorPlayer) packet;
            System.out.println("SET PLAYER");

            ConnectHelper.setPlayer(sectorPlayer);
            return null;
        });

    }


}
