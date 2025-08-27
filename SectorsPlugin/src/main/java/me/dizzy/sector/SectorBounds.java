package me.dizzy.sector;

import ch.challangerson.sector.Sector;

public class SectorBounds {
    final Sector sector;
    final double minX;
    final double maxX;
    final double minZ;
    final double maxZ;

    public SectorBounds(Sector sector) {
        this.sector = sector;
        this.minX = Math.min(sector.getMinX(), sector.getMaxX());
        this.maxX = Math.max(sector.getMinX(), sector.getMaxX());
        this.minZ = Math.min(sector.getMinZ(), sector.getMaxZ());
        this.maxZ = Math.max(sector.getMinZ(), sector.getMaxZ());
    }

    public Sector getSector() {
        return this.sector;
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMinZ() {
        return this.minZ;
    }

    public double getMaxZ() {
        return this.maxZ;
    }
}