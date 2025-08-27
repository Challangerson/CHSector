package me.dizzy.task;

import ch.challangerson.sector.Sector;
import me.dizzy.SectorsPlugin;
import me.dizzy.helper.BorderHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SectorBorderUpdateTask extends BukkitRunnable {

    private final SectorsPlugin plugin;

    public SectorBorderUpdateTask(SectorsPlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimer(plugin, 65L, 65L);
    }

    @Override
    public void run() {
        Sector sector = this.plugin.getSectorManager().getCurrentSector();

        double size = Math.abs(sector.getMaxX() - sector.getMinX());

        for(Player player : Bukkit.getOnlinePlayers()) {
            BorderHelper.setWorldBorder(player, size, sector.getCenterX(), sector.getCenterZ());
        }
    }
}
