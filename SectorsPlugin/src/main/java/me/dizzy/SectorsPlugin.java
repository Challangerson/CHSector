package me.dizzy;

import ch.challangerson.Shared;
import ch.challangerson.sector.Sector;
import ch.challangerson.sector.SectorManager;
import ch.challangerson.temporary.Configuration;
import me.dizzy.commands.ClearBossBar;
import me.dizzy.listener.PlayerMove;
import me.dizzy.task.SectorBorderUpdateTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class SectorsPlugin extends JavaPlugin {

    private static SectorsPlugin sectorsPlugin;
    private Shared shared;
    private Sector actuallSector;

    private SectorManager sectorManager;

    @Override
    public void onEnable() {
        sectorsPlugin = this;
        this.shared = new Shared();
        this.sectorManager = new SectorManager();
        new Configuration(sectorManager);

        this.saveDefaultConfig();

        String sectorName = this.getConfig().getString("sektor-name");

        if(sectorName == null) {
            this.getLogger().severe("Sektor name is not set in the config.yml! Please set 'sektor-name' to a valid sector name.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Sector sector = this.sectorManager.getSector(sectorName);

        if (sector == null) {
            this.getLogger().severe("Sector '" + sectorName + "' does not exist! Please check your configuration.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.sectorManager.setCurrentSector(sector.getId());
        this.actuallSector = this.sectorManager.getCurrentSector();

        new PacketContainer(shared.getPacketHandler(), shared.getRedisManager(), this.actuallSector, this.sectorManager);
        this.initTasks();
        this.initListeners();
        this.getCommand("clean").setExecutor(new ClearBossBar());
//        this.getDataFolder().mkdir();
    }



    private void initTasks() {
        new SectorBorderUpdateTask(this);
    }

    private void initListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerMove(this.actuallSector, this.shared.getPacketSender()), this);
    }

    @Override
    public void onDisable() {
        if(this.shared != null) {
            this.shared.shutdown();
        }
    }

    public Sector getActuallSector() {
        return this.actuallSector;
    }

    public static SectorsPlugin getSectorsPlugin() {
        return sectorsPlugin;
    }

    public static void setSectorsPlugin(SectorsPlugin sectorsPlugin) {
        SectorsPlugin.sectorsPlugin = sectorsPlugin;
    }

    public Shared getShared() {
        return this.shared;
    }

    public void setShared(Shared shared) {
        this.shared = shared;
    }

    public SectorManager getSectorManager() {
        return this.sectorManager;
    }

    public void setSectorManager(SectorManager sectorManager) {
        this.sectorManager = sectorManager;
    }
}
