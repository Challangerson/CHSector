package me.dizzy.util;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ChatUtil {

    public static Component colorize(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }

    public static void sendMessage(CommandSource source, String message) {
        source.sendMessage(colorize(message.replaceAll(">>", "»")
                .replaceAll("<<", "«")
                .replaceAll("<3", "❤")));
    }
}
