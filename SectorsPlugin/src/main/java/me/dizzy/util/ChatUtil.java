package me.dizzy.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;

public class ChatUtil {
    public static String color(String message) {
        return message.replace("&", "§");
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message)
                .replaceAll(">>", "»")
                .replaceAll("<<", "«")
                .replace("*", "•");
    }

    public static String stripColor(String message) {
        return message.replace("§", "");
    }

    public static String format(String message, Object... args) {
        return String.format(color(message), args);
    }

    public static Component createComponent(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string).replaceText(builder -> builder.matchLiteral(">>").replacement("»")).replaceText(builder -> builder.matchLiteral("<<").replacement("«"));
    }
}
