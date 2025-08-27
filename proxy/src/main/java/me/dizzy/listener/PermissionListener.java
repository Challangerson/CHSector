package me.dizzy.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.permission.PermissionsSetupEvent;
import me.dizzy.permission.PermissionProvider;
import me.dizzy.user.ProxyUsers;

public class PermissionListener {

    private final ProxyUsers proxyUsers;

    public PermissionListener(ProxyUsers proxyUsers) {
        this.proxyUsers = proxyUsers;
    }

    @Subscribe
    public void onPermissionsSetup(PermissionsSetupEvent event) {
        event.setProvider(permissionSubject ->
                new PermissionProvider(event.getSubject(), this.proxyUsers)
        );
    }
}
