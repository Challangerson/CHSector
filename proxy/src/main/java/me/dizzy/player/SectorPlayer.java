package me.dizzy.player;

import ch.challangerson.adapter.redis.packet.Packet;

import java.util.UUID;

public class SectorPlayer implements Packet {
    private UUID uniqueId;
    private String nickname, fromSectorName, gameMode, items, armor, enderChest, potionEffects, location, toSectorName;
    private double health;
    private float walkSpeed, flySpeed, exp;
    private int totalExp, foodLevel, fireTicks, heldSlot, level;
    private boolean allowFlight, flying, op, sprinting;


    public SectorPlayer() {
    }

    public SectorPlayer(SectorPlayer sectorPlayerData) {
        this.uniqueId = sectorPlayerData.getUniqueId();
        this.nickname = sectorPlayerData.getNickname();
        this.fromSectorName = sectorPlayerData.getFromSectorName();
        this.gameMode = sectorPlayerData.getGameMode();
        this.items = sectorPlayerData.getItems();
        this.armor = sectorPlayerData.getArmor();
        this.enderChest = sectorPlayerData.getEnderChest();
        this.potionEffects = sectorPlayerData.getPotionEffects();
        this.location = sectorPlayerData.getLocation();
        this.health = sectorPlayerData.getHealth();
        this.walkSpeed = sectorPlayerData.getWalkSpeed();
        this.flySpeed = sectorPlayerData.getFlySpeed();
        this.exp = sectorPlayerData.getExp();
        this.totalExp = sectorPlayerData.getTotalExp();
        this.foodLevel = sectorPlayerData.getFoodLevel();
        this.fireTicks = sectorPlayerData.getFireTicks();
        this.heldSlot = sectorPlayerData.getHeldSlot();
        this.level = sectorPlayerData.getLevel();
        this.allowFlight = sectorPlayerData.isAllowFlight();
        this.flying = sectorPlayerData.isFlying();
        this.op = sectorPlayerData.isOp();
        this.sprinting = sectorPlayerData.isSprinting();
        this.toSectorName = sectorPlayerData.getToSectorName();

    }

    public SectorPlayer(UUID uniqueId, String nickname,
                        String fromSectorName, String gameMode,
                        String items, String armor, String enderChest,
                        String potionEffects, String location, double health,
                        float walkSpeed, float flySpeed, float exp, int totalExp,
                        int foodLevel, int fireTicks, int heldSlot, int level, boolean allowFlight,
                        boolean flying, boolean op, boolean sprinting, String toSectorName) {
        this.uniqueId = uniqueId;
        this.nickname = nickname;
        this.fromSectorName = fromSectorName;
        this.gameMode = gameMode;
        this.items = items;
        this.armor = armor;
        this.enderChest = enderChest;
        this.potionEffects = potionEffects;
        this.location = location;
        this.health = health;
        this.walkSpeed = walkSpeed;
        this.flySpeed = flySpeed;
        this.exp = exp;
        this.totalExp = totalExp;
        this.foodLevel = foodLevel;
        this.fireTicks = fireTicks;
        this.heldSlot = heldSlot;
        this.level = level;
        this.allowFlight = allowFlight;
        this.flying = flying;
        this.op = op;
        this.sprinting = sprinting;
        this.toSectorName = toSectorName;
    }




    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFromSectorName() {
        return this.fromSectorName;
    }

    public void setFromSectorName(String fromSectorName) {
        this.fromSectorName = fromSectorName;
    }

    public String getGameMode() {
        return this.gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getItems() {
        return this.items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getArmor() {
        return this.armor;
    }

    public void setArmor(String armor) {
        this.armor = armor;
    }

    public String getEnderChest() {
        return this.enderChest;
    }

    public void setEnderChest(String enderChest) {
        this.enderChest = enderChest;
    }

    public String getPotionEffects() {
        return this.potionEffects;
    }

    public void setPotionEffects(String potionEffects) {
        this.potionEffects = potionEffects;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public void setLocation(Location location) {
//        this.location = BukkitSerializer.serializeLocation(location);
//    }

    public double getHealth() {
        return this.health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public void setFlySpeed(float flySpeed) {
        this.flySpeed = flySpeed;
    }

    public float getExp() {
        return this.exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public int getTotalExp() {
        return this.totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public int getFoodLevel() {
        return this.foodLevel;
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    public int getFireTicks() {
        return this.fireTicks;
    }

    public void setFireTicks(int fireTicks) {
        this.fireTicks = fireTicks;
    }

    public int getHeldSlot() {
        return this.heldSlot;
    }

    public void setHeldSlot(int heldSlot) {
        this.heldSlot = heldSlot;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAllowFlight() {
        return this.allowFlight;
    }

    public void setAllowFlight(boolean allowFlight) {
        this.allowFlight = allowFlight;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public boolean isOp() {
        return this.op;
    }

    public void setOp(boolean op) {
        this.op = op;
    }

    public boolean isSprinting() {
        return this.sprinting;
    }

    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }

    public void setToSectorName(String toSectorName) {
        this.toSectorName = toSectorName;
    }

    public String getToSectorName() {
        return this.toSectorName;
    }

    @Override
    public String toString() {
        return "SectorPlayer{" +
                "uniqueId=" + uniqueId +
                ", nickname='" + nickname + '\'' +
                ", fromSectorName='" + fromSectorName + '\'' +
                ", gameMode='" + gameMode + '\'' +
                ", items='" + items + '\'' +
                ", armor='" + armor + '\'' +
                ", enderChest='" + enderChest + '\'' +
                ", potionEffects='" + potionEffects + '\'' +
                ", location='" + location + '\'' +
                ", toSectorName='" + toSectorName + '\'' +
                ", health=" + health +
                ", walkSpeed=" + walkSpeed +
                ", flySpeed=" + flySpeed +
                ", exp=" + exp +
                ", totalExp=" + totalExp +
                ", foodLevel=" + foodLevel +
                ", fireTicks=" + fireTicks +
                ", heldSlot=" + heldSlot +
                ", level=" + level +
                ", allowFlight=" + allowFlight +
                ", flying=" + flying +
                ", op=" + op +
                ", sprinting=" + sprinting +
                '}';
    }

    @Override
    public String getType() {
        return "SECTOR_PLAYER";
    }
}
