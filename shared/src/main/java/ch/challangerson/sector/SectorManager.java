package ch.challangerson.sector;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SectorManager {

    private Map<String, Sector> sectors = new ConcurrentHashMap<>();
    private String currentSector;

    public void createSector(String id, Sector sector) {
        if (this.sectors.containsKey(id)) {
            throw new IllegalArgumentException("Sector with id " + id + " already exists.");
        }
        this.sectors.put(id, sector);
    }

    public Sector getSector(String id) {
        return this.sectors.get(id);
    }

    public Sector getSectorByLocation(Location location) {
        return this.sectors.values().stream().filter(sector -> isIn(location,sector)).filter(
                sector -> !sector.equals(getCurrentSector()) && !sector.getSectorType().equals(SectorType.SPAWN) || !getCurrentSector().getSectorType().equals(SectorType.SPAWN))
                .findFirst().orElse(null);
    }

    public Sector getSpawnSector() {
        return this.sectors.values().stream().filter(sector -> sector.getSectorType().equals(SectorType.SPAWN))
                .filter(Sector::isOnline).findFirst().orElse(null);
    }

    public static boolean isIn(final Location location, final Sector sector) {
        return location.getBlockX() > sector.getMinX() && location.getBlockX() < sector.getMaxX() && location.getBlockZ() > sector.getMinZ() && location.getBlockZ() < sector.getMaxZ();
    }

    public double distance(Location location) {
        Sector sector = this.getCurrentSector();
        return Math.min(
                Math.min(Math.abs(sector.getMinX() - location.getX()),
                        Math.abs(sector.getMaxX() - location.getX())),
                Math.min(Math.abs(sector.getMinZ() - location.getZ()),
                        Math.abs(sector.getMaxZ() - location.getZ()))) + 1.0;
    }

    public void setCurrentSector(String currentSector) {
        this.currentSector = currentSector;
    }

    public String getCurrentSectorID() {
        return this.currentSector;
    }

    public Sector getCurrentSector() {
        return this.sectors.get(currentSector);
    }

    public Map<String, Sector> getSectors() {
        return this.sectors;
    }
}
