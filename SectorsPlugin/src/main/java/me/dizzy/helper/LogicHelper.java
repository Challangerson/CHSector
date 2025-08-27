package me.dizzy.helper;

import ch.challangerson.sector.Entry;
import ch.challangerson.sector.Sector;
import me.dizzy.SectorsPlugin;

import java.util.Collection;

public class LogicHelper {


    private static final SectorsPlugin PLUGIN = SectorsPlugin.getSectorsPlugin();

    public static Sector findSectorContaining(double x, double z) {
        for (Sector sector : getSectors()) {
            if (isPointInSector(x, z, sector)) {
                return sector;
            }
        }
        return null;
    }

    public static Entry findNearestSector(double x, double z, double maxDistance) {
        Entry nearest = null;
        double minDistanceSquared = maxDistance * maxDistance;

        for (Sector sector : getSectors()) {
            double distanceSquared = getDistanceSquaredToSector(x, z, sector);

            if (distanceSquared <= minDistanceSquared) {
                minDistanceSquared = distanceSquared;
                nearest = new Entry(sector, Math.sqrt(distanceSquared));
            }
        }
        return nearest;
    }

    public static Sector findSectorBeyondEdge(double x, double z, Sector current) {
        for (Sector sector : getSectors()) {
            if (sector != current && isPointInSector(x, z, sector)) {
                return sector;
            }
        }
        return null;
    }

    private static Collection<Sector> getSectors() {
        return PLUGIN.getSectorManager().getSectors().values();
    }

    private static boolean isPointInSector(double x, double z, Sector sector) {
        double minX = Math.min(sector.getMinX(), sector.getMaxX());
        double maxX = Math.max(sector.getMinX(), sector.getMaxX());
        double minZ = Math.min(sector.getMinZ(), sector.getMaxZ());
        double maxZ = Math.max(sector.getMinZ(), sector.getMaxZ());

        return x >= minX && x <= maxX && z >= minZ && z <= maxZ;
    }

    private static double getDistanceSquaredToSector(double x, double z, Sector sector) {
        double minX = Math.min(sector.getMinX(), sector.getMaxX());
        double maxX = Math.max(sector.getMinX(), sector.getMaxX());
        double minZ = Math.min(sector.getMinZ(), sector.getMaxZ());
        double maxZ = Math.max(sector.getMinZ(), sector.getMaxZ());

        double dx = Math.max(minX - x, Math.max(x - maxX, 0));
        double dz = Math.max(minZ - z, Math.max(z - maxZ, 0));

        return dx * dx + dz * dz;
    }
}
