package me.dizzy.user;

import me.dizzy.SectorsProxy;
import me.dizzy.user.rank.UserRank;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class ProxyUser {
    private final UUID uuid;
    private String name;
    private boolean isPremium, isLogged;
    private String code;
    private UserRank rank;
    private boolean isConnectedDiscord;
    private int discordCode;

    public ProxyUser(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.isLogged = false;
        this.isPremium = false;
        this.rank = new UserRank(null, SectorsProxy.getInstance().getRankManager().getDefaultRank(), 0L);
        this.code = String.valueOf(new Random().nextInt(999999));

        this.isConnectedDiscord = false;
        this.discordCode = 0;
    }

    public boolean isLogged() {
        return this.isLogged;
    }

    public void setLogged(boolean logged) {
        this.isLogged = logged;
    }

    public int getDiscordCode() {
        return this.discordCode;
    }

    public void setDiscordCode(int discordCode) {
        this.discordCode = discordCode;
    }

    public boolean isConnectedDiscord() {
        return this.isConnectedDiscord;
    }

    public void setConnectedDiscord(boolean connectedDiscord) {
        isConnectedDiscord = connectedDiscord;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isPremium() {
        return this.isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRank getRank() {
        return this.rank;
    }

    public void setRank(UserRank rank) {
        this.rank = rank;
    }
}
