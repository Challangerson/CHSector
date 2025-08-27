package ch.challangerson.sector;

public class Entry {
    final Sector sector;
    final double distance;

    public Entry(Sector sector, double distance) {
        this.sector = sector;
        this.distance = distance;
    }

    public Sector getSector() {
        return this.sector;
    }

    public double getDistance() {
        return this.distance;
    }
}
