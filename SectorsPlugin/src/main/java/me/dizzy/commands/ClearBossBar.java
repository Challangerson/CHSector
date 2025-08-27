package me.dizzy.commands;

import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClearBossBar implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player player)) return false;

        Iterable<? extends BossBar> bossBars = player.activeBossBars();

        if(!bossBars.iterator().hasNext()) {
            player.sendMessage("§cNie masz aktywnych bossbarów do usunięcia.");
            return true;
        }

        bossBars.forEach(player::hideBossBar);

        return true;
    }
}
