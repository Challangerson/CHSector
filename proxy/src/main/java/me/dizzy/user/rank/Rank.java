package me.dizzy.user.rank;

import java.util.Set;

public record Rank(String name, String colorTyping, String chatPrefix, String nameTagPrefix, Set<String> permissions,
                   int priority, boolean defaultRank) {

    public boolean hasPermission(String permission) {
        return this.permissions.contains(permission);
    }

    public boolean isHigherThan(Rank rank) {
        return this.priority > rank.priority();
    }


}
