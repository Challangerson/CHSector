package me.dizzy.permission;

import com.velocitypowered.api.permission.PermissionFunction;
import com.velocitypowered.api.permission.PermissionSubject;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;
import me.dizzy.user.ProxyUser;
import me.dizzy.user.ProxyUsers;
import me.dizzy.user.rank.Rank;
import me.dizzy.user.rank.UserRank;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class PermissionProvider implements PermissionFunction {

    private final PermissionSubject permissionSubject;
    private final ProxyUsers proxyUsers;

    public PermissionProvider(PermissionSubject permissionSubject, ProxyUsers proxyUsers) {
        this.permissionSubject = permissionSubject;
        this.proxyUsers = proxyUsers;
    }

    @Override
    public @NotNull Tristate getPermissionValue(String permission) {
        if(this.permissionSubject instanceof Player player) {

            ProxyUser proxyUser = this.proxyUsers.getUser(player.getUniqueId());

            if (proxyUser == null) {

                player.sendActionBar(
                        LegacyComponentSerializer.legacyAmpersand().deserialize(
                                "&cChuja masz a nie permisje, zglos to do administracji!"
                        )
                );

                return Tristate.FALSE;
            }

            Rank rank = proxyUser.getRank().currentRank();

            if(rank.permissions().contains("*")) {
                return Tristate.TRUE;
            }

            return rank.hasPermission(permission) ? Tristate.TRUE : Tristate.FALSE;
        }

        return Tristate.UNDEFINED;
    }
}
