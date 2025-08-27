package me.dizzy.helper;


import ch.challangerson.sector.Sector;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.border.WorldBorder;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BorderHelper {

    public static void setWorldBorder(Player player, double size, double centerX, double centerZ) {
//        ServerLevel level = (ServerLevel) player.getWorld();
        WorldBorder worldBorder = new WorldBorder();

        worldBorder.setSize(size + 0.5);
        worldBorder.setCenter(centerX, centerZ);
        worldBorder.setWarningBlocks(0);
        worldBorder.setDamagePerBlock(4);

        worldBorder.world = ((CraftWorld) player.getWorld()).getHandle().getWorldBorder().world;

        ClientboundInitializeBorderPacket borderPacket = new ClientboundInitializeBorderPacket(worldBorder);
        ClientboundSetBorderCenterPacket centerPacket = new ClientboundSetBorderCenterPacket(worldBorder);
        ClientboundSetBorderSizePacket sizePacket = new ClientboundSetBorderSizePacket(worldBorder);
        ServerGamePacketListenerImpl serverPlayer = ((CraftPlayer) player).getHandle().connection;

//        serverPlayer.connection.send(sizePacket);
//        serverPlayer.connection.send(centerPacket);
        serverPlayer.send(borderPacket);

    }


    public static void knockPlayer(Player player, Sector sector) {
        Location center = new Location(player.getWorld(),
                sector.getCenterX(),
                player.getLocation().getY(),
                sector.getCenterZ()
        );

        player.setVelocity(center.toVector().subtract(
                player.getLocation().toVector()).normalize().multiply(1.5D)
        );
    }

}
