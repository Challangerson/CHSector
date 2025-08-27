package me.dizzy.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import me.dizzy.api.RestApi;
import me.dizzy.user.ProxyUser;
import me.dizzy.user.ProxyUsers;
import me.dizzy.util.Randomizer;
import org.slf4j.Logger;

public class PreLogin {
    private final RestApi restApi;
    private final ProxyUsers proxyUsers;


    public PreLogin(RestApi restApi, ProxyUsers proxyUsers) {
        this.restApi = restApi;
        this.proxyUsers = proxyUsers;
    }

    @Subscribe(priority = 1)
    public void onPreLoginEvent(PreLoginEvent event) {

        String nickName = event.getUsername();

        ProxyUser proxyUser = this.proxyUsers.getUser(nickName);

        if(proxyUser == null) {
            proxyUser = proxyUsers.addUser(event.getUniqueId(), nickName);
            proxyUser.setCode(Randomizer.generateRandomWord(6));
        }





        int code = this.restApi.sendPremiumRequest(nickName);

        if (code == 200) {
            event.setResult(PreLoginEvent.PreLoginComponentResult.forceOnlineMode());
            proxyUser.setPremium(true);
        } else {
            event.setResult(PreLoginEvent.PreLoginComponentResult.forceOfflineMode());
            proxyUser.setPremium(false);
        }
    }
}
