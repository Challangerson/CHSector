package me.dizzy.user.rank;

public record UserRank(Rank previousRank, Rank currentRank, long time) {
}
