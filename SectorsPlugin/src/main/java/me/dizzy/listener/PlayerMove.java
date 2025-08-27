package me.dizzy.listener;

import ch.challangerson.adapter.redis.packet.PacketSender;
import ch.challangerson.sector.Sector;
import me.dizzy.helper.BorderHelper;
import me.dizzy.helper.ConnectHelper;
import me.dizzy.helper.LogicHelper;
import me.dizzy.player.SectorPlayer;
import me.dizzy.sector.BorderCheckResult;
import me.dizzy.sector.SectorBounds;
import me.dizzy.util.ChatUtil;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

import static me.dizzy.sector.BorderCheckResult.NOT_NEAR_BORDER;

public class PlayerMove implements Listener {

    private final Sector currentSector;
    private final HashMap<UUID, Long> connectionTime;
    private final PacketSender packetSender;

    public PlayerMove(Sector currentSector, PacketSender packetSender) {
        this.currentSector = currentSector;
        this.packetSender = packetSender;
        this.connectionTime = new HashMap<>();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (isSamePosition(event.getFrom(), event.getTo())) {
            return;
        }

        Player player = event.getPlayer();
        Location loc = player.getLocation();
        double x = loc.getX();
        double z = loc.getZ();

        if (this.currentSector != null) {
            checkSectorBorders(player, x, z);
        }


    }

    private boolean isSamePosition(Location from, Location to) {
        return from.getX() == to.getX()
                && from.getY() == to.getY()
                && from.getZ() == to.getZ();
    }


    private void checkSectorBorders(Player player, double x, double z) {
        SectorBounds bounds = new SectorBounds(this.currentSector);
        BorderCheckResult borderCheck = checkAllBorders(x, z, bounds);
        int distance = (int) borderCheck.getDistanceToBorder();

        if (borderCheck.isNearBorder()) {

            if(distance <= 1) {

                if(borderCheck.getAdjacentSector().isPresent()) {
                    UUID uuid = player.getUniqueId();
                    Sector adjacentSector = borderCheck.getAdjacentSector().get();

                    if (!connectionTime.containsKey(uuid)) {
                        connectionTime.put(uuid, System.currentTimeMillis());
                    } else {
                        long lastConnection = connectionTime.get(uuid);

                        if (System.currentTimeMillis() - lastConnection < 2500) {
                            BorderHelper.knockPlayer(player, this.currentSector);
                            player.sendMessage(ChatUtil.colorize("&8>> &cZbyt szybko poruszasz sie po sektorze!"));
                            return;
                        }

                        SectorPlayer sectorPlayer = ConnectHelper.sectorPlayerFromPlayer(
                                player,
                                this.currentSector.getId(),
                                adjacentSector.getId());

//                        System.out.println(sectorPlayer.toString());

                        this.packetSender.sendPacket("ch_proxy", sectorPlayer);
//                        player.sendMessage(ChatUtil.colorize("&8>> &aLaczenie z sektorem &f" + adjacentSector.getId() + "&a..."));

                        connectionTime.put(uuid, System.currentTimeMillis());
                    }


                    player.sendTitle(ChatUtil.colorize("&e&lS E K T O R"),
                            ChatUtil.colorize("&f" + adjacentSector.getId()), 20, 20, 20);

                } else {
                    BorderHelper.knockPlayer(player, this.currentSector);
                    player.sendTitle(ChatUtil.colorize("&c&lG R A N I C A"),
                            ChatUtil.colorize("&fSwiat sie tutaj konczy!"), 20, 20, 20);
                }
            }

            String message = formatBorderMessage(borderCheck);
            notifyPlayer(player, message, borderCheck.getDistanceToBorder());
        }
    }

    private String formatBorderMessage(BorderCheckResult result) {
        String sectorInfo = result.getAdjacentSector()
                .map(sector -> "&7>> &eZblizasz sie do granicy sektora &f" + sector.getId())
                .orElse("&7>> &eZblizasz sie do &ckranca &eswiata");

        return String.format("%s &8| &f%dm",
                sectorInfo, (int) result.getDistanceToBorder());
    }

    private BorderCheckResult checkAllBorders(double x, double z, SectorBounds bounds) {
        double distToMinX = x - bounds.getMinX();
        double distToMaxX = bounds.getMaxX() - x;
        double distToMinZ = z - bounds.getMinZ();
        double distToMaxZ = bounds.getMaxZ() - z;

        double minDistance = Math.min(
                Math.min(Math.abs(distToMinX), Math.abs(distToMaxX)),
                Math.min(Math.abs(distToMinZ), Math.abs(distToMaxZ))
        );

        if (minDistance <= (double) 30) {
            if (Math.abs(distToMinX) == minDistance) {
                return checkAdjacentSector(bounds.getMinX() - 0.1, z, bounds.getSector(), minDistance);
            } else if (Math.abs(distToMaxX) == minDistance) {
                return checkAdjacentSector(bounds.getMaxX() + 0.1, z, bounds.getSector(), minDistance);
            } else if (Math.abs(distToMinZ) == minDistance) {
                return checkAdjacentSector(x, bounds.getMinZ() - 0.1, bounds.getSector(), minDistance);
            } else {
                return checkAdjacentSector(x, bounds.getMaxZ() + 0.1, bounds.getSector(), minDistance);
            }
        }
        return NOT_NEAR_BORDER;
    }

    private BorderCheckResult checkAdjacentSector(double x, double z, Sector currentSector, double distance) {
        Sector adjacent = LogicHelper.findSectorBeyondEdge(x, z, currentSector);
        return new BorderCheckResult(true, adjacent, distance);
    }


    BossBar bossBar = BossBar.bossBar(
            ChatUtil.createComponent(""),
            1.0f,
            BossBar.Color.YELLOW,
            BossBar.Overlay.NOTCHED_6
    );


    private void notifyPlayer(Player player, String message, double distance) {
        if (message == null || message.isEmpty()) return;

        float d = (float) Math.min(1.0f, Math.max(0.0f, distance * (1.0f / 30.0f)));
        bossBar.name(ChatUtil.createComponent(message));
        bossBar.progress(d);


        player.showBossBar(
                bossBar
        );
    }
}



