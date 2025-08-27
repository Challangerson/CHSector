package me.dizzy.helper;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SerializerHelper {

    public static String serializeItems(ItemStack[] items) {
        try {
            byte[] data = ItemStack.serializeItemsAsBytes(items == null ? new ItemStack[0] : items);
            return Base64.getEncoder().encodeToString(data);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to serialize ItemStacks", ex);
        }
    }

    public static String serializePotionEffects(Collection<PotionEffect> effects) {
        List<Map<String, Object>> serialized = effects.stream()
                .map(PotionEffect::serialize)
                .toList();

        return new Gson().toJson(serialized);
    }

    public static List<PotionEffect> deserializePotionEffects(String json) {
        Type type = new TypeToken<List<Map<String, Object>>>() {
        }.getType();
        List<Map<String, Object>> data = new Gson().fromJson(json, type);

        return data.stream()
                .map(PotionEffect::new)
                .toList();
    }

    public static ItemStack[] deserializeItems(String base64) {
        try {
            byte[] data = Base64.getDecoder().decode(base64);
            return ItemStack.deserializeItemsFromBytes(data);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to deserialize ItemStacks", ex);
        }
    }

    public static String serializeLocation(Location location) {
        try {
            Vector vector = location.getDirection();

            return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":"
                    + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch() + ":"
                    + vector.getX() + ":" + vector.getY() + ":" + vector.getZ();
        } catch (final Exception ex) {
            return "";
        }
    }

    public static Location deserializeLocation(String serializedData) {
        try {
            final String[] split = serializedData.split(":");
            Location location = new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]),
                    Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]),
                    Float.parseFloat(split[5]));

            location.setDirection(new Vector(Double.parseDouble(split[6]), Double.parseDouble(split[7]), Double.parseDouble(split[8])));

            return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]),
                    Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]),
                    Float.parseFloat(split[5]));
        } catch (final Exception ex) {
            return null;
        }
    }
}
