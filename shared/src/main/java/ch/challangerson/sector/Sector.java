package ch.challangerson.sector;

public class Sector {
    private final String id;

    private final SectorType sectorType;

    private final int minX,maxX,minZ,maxZ,centerX,centerZ;

    private int players;

    private long lastUpdate;

    private double tps;

    public Sector(String id,SectorType sectorType,int minX,int maxX,int minZ,int maxZ) {
        this.id = id;
        this.sectorType = sectorType;
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.centerX = (this.minX + this.maxX) / 2;
        this.centerZ = (this.minZ + this.maxZ) / 2;
    }

    public String getId() {
        return this.id;
    }

    public SectorType getSectorType() {
        return this.sectorType;
    }

    public int getMinX() {
        return this.minX;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMinZ() {
        return this.minZ;
    }

    public int getMaxZ() {
        return this.maxZ;
    }

    public int getCenterX() {
        return this.centerX;
    }

    public int getCenterZ() {
        return this.centerZ;
    }

    public int getPlayers() {
        return this.players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public long getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public double getTps() {
        return this.tps;
    }

    public void setTps(double tps) {
        this.tps = tps;
    }

    public boolean isOnline() {
        return this.lastUpdate > System.currentTimeMillis() - 60000;
    }
}
