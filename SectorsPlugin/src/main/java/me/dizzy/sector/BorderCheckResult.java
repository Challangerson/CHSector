package me.dizzy.sector;

import ch.challangerson.sector.Sector;

import java.util.Optional;

public record BorderCheckResult(boolean nearBorder, Sector adjacentSector, double distance) {
    public static final BorderCheckResult NOT_NEAR_BORDER = new BorderCheckResult(false, null, 0);

    public boolean isNearBorder() {
        return nearBorder;
    }

    public double getDistanceToBorder() {
        return distance;
    }

    public Optional<Sector> getAdjacentSector() {
        return Optional.ofNullable(adjacentSector);
    }
}