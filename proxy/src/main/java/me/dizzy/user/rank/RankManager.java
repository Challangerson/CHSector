package me.dizzy.user.rank;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RankManager {
    private final Rank defaultRank;
    public List<Rank> ranks;

    public RankManager() {
        this.ranks = Arrays.asList(
                new Rank("USER", "&7", "&7 ", "&fGRACZ", Set.of("salk.kit.gracz"), 0, true),
                new Rank("MINI_MEDIA", "&7", "&d ", "&dMINIMEDIA", Set.of("salk.kit.minimedia"), 1, false),
                new Rank("VIP", "&7", "&6 ", "&6&lVIP", Set.of("salk.kit.vip"), 2, false),
                new Rank("MEDIA", "&f", "&5 ", "&bMEDIA", Set.of("salk.kit.media"), 3, false),
                new Rank("HELPER", "&b", "&bHelper &7", "&9&lHELPER", Set.of("salk.kit.helper"), 4, false),
                new Rank("KID_MOD", "&a", "&aKidMod &7", "&b&lKIDMOD", Set.of("salk.kit.kidmod"), 5, false),
                new Rank("MOD", "&2", "&2Mod &7", "&2&lMOD", Set.of("salk.kit.mod"), 6, false),
                new Rank("ADMIN", "&c", "&cAdmin &7", "&cADMIN", Set.of("salk.kit.admin"), 7, false),
                new Rank("HA", "&c", "&cH@ &7", "&cH@", Set.of("*"), 8, false),
                new Rank("ROOT", "&4", "&4ROOT &7", "&4&lROOT", Set.of("*"), 9, false)
        );
        this.defaultRank = this.getDefaultRankManager();
    }

    public Rank getDefaultRank() {
        return this.defaultRank;
    }

    public Rank getRank(String name) {

        name = name.toUpperCase();

        for (Rank rank : this.ranks) {
            if (rank.name().equals(name)) {
                return rank;
            }
        }
        return null;
    }



    private Rank getDefaultRankManager() {
        for (Rank rank : this.ranks) {
            if (rank.defaultRank()) {
                return rank;
            }
        }
        return null;
    }

    public List<Rank> getRanks() {
        return this.ranks;
    }
}
