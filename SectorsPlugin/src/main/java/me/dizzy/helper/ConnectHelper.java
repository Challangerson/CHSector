package me.dizzy.helper;

import me.dizzy.SectorsPlugin;
import me.dizzy.player.SectorPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.UUID;

public class ConnectHelper {

    public static SectorPlayer sectorPlayerFromPlayer(Player player, String fromSector, String toSector) {
        UUID uniqueId = player.getUniqueId();
        System.out.println("uuid git");
        String name = player.getName();
        System.out.println("name git");
        String gameMode = player.getGameMode().name();
        System.out.println("gamemode git");
        String items = SerializerHelper.serializeItems(player.getInventory().getContents());
        System.out.println("items git");
        String armor = SerializerHelper.serializeItems(player.getInventory().getArmorContents());
        System.out.println("armor git");
        String enderChest = SerializerHelper.serializeItems(player.getEnderChest().getContents());
        System.out.println("enderchest git");
        String location = SerializerHelper.serializeLocation(player.getLocation());
        System.out.println("location git");
        double health = player.getHealth();
        System.out.println("health git");
        float walkSpeed = player.getWalkSpeed();
        System.out.println("walkspeed git");
        float flySpeed = player.getFlySpeed();
        System.out.println("flyspeed git");
        float exp = player.getExp();
        System.out.println("exp git");
        int totalExp = player.getTotalExperience();
        System.out.println("totalexp git");
        int foodLevel = player.getFoodLevel();
        int fireTicks = player.getFireTicks();
        int heldSlot = player.getInventory().getHeldItemSlot();
        int level = player.getLevel();
        boolean allowFlight = player.getAllowFlight();
        boolean flying = player.isFlying();
        boolean isOp = player.isOp();
        boolean sprinting = player.isSprinting();

        // 2. Pass the variables to the constructor
        return new SectorPlayer(
                uniqueId,
                name,
                fromSector,
                gameMode,
                items,
                armor,
                enderChest,
                null, // Potion effects are passed as null
                location,
                health,
                walkSpeed,
                flySpeed,
                exp,
                totalExp,
                foodLevel,
                fireTicks,
                heldSlot,
                level,
                allowFlight,
                flying,
                isOp,
                sprinting,
                toSector
        );
    }

    public static void setPlayer(SectorPlayer sectorPlayer) {
        Player player = Bukkit.getPlayer(sectorPlayer.getUniqueId());
        System.out.println("TEST");

        if (player == null) {
            System.out.println("Error with synchro user data. His uuid: " + sectorPlayer.getUniqueId());
            return;
        }

        player.getInventory().setContents(SerializerHelper.deserializeItems(sectorPlayer.getItems()));
        System.out.println("1");
        player.getInventory().setArmorContents(SerializerHelper.deserializeItems(sectorPlayer.getArmor()));
        player.getEnderChest().setContents(SerializerHelper.deserializeItems(sectorPlayer.getEnderChest()));
        System.out.println("2");
        player.setHealth(sectorPlayer.getHealth());
        player.setWalkSpeed(sectorPlayer.getWalkSpeed());
        player.setFlySpeed(sectorPlayer.getFlySpeed());
        System.out.println("3");
        player.setExp(sectorPlayer.getExp());
        player.setTotalExperience(sectorPlayer.getTotalExp());
        player.setFoodLevel(sectorPlayer.getFoodLevel());
        player.setFireTicks(sectorPlayer.getFireTicks());
        player.getInventory().setHeldItemSlot(sectorPlayer.getHeldSlot());
        player.setLevel(sectorPlayer.getLevel());
        System.out.println("4");
        player.setAllowFlight(sectorPlayer.isAllowFlight());
        player.setFlying(sectorPlayer.isFlying());
        player.setOp(sectorPlayer.isOp());
        player.setSprinting(sectorPlayer.isSprinting());


        Bukkit.getScheduler().runTask(SectorsPlugin.getSectorsPlugin(), () -> {
            Location location = SerializerHelper.deserializeLocation(sectorPlayer.getLocation());

            player.setGameMode(GameMode.valueOf(sectorPlayer.getGameMode()));
            player.getActivePotionEffects().clear();


            Collection<PotionEffect> potionEffects = SerializerHelper.deserializePotionEffects(sectorPlayer.getPotionEffects());

            if(potionEffects != null) {
                potionEffects.forEach(player::addPotionEffect);
            }

            assert location != null;

            player.teleport(location);
            player.setVelocity(location.getDirection());
        });

        System.out.println("Synchronized player: " + sectorPlayer.getUniqueId() + " to sector: " + sectorPlayer.getToSectorName());
    }
}
