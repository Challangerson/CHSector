package ch.challangerson.temporary;

import ch.challangerson.sector.Sector;
import ch.challangerson.sector.SectorManager;
import ch.challangerson.sector.SectorType;

public class Configuration {

    public Configuration(SectorManager sectorManager) {
        Sector sector = new Sector("w1", SectorType.NORMAL, -100, 100, -100, 100);
        Sector sector2 = new Sector("w2", SectorType.NORMAL, -100, 100, -100, -200);

        sectorManager.createSector(sector.getId(), sector);
        sectorManager.createSector(sector2.getId(), sector2);
    }
}
