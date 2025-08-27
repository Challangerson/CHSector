package me.dizzy.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import com.velocitypowered.api.util.GameProfile;
import me.dizzy.api.RestApi;
import me.dizzy.user.ProxyUser;
import me.dizzy.user.ProxyUsers;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class GameProfileEvent {
    private final RestApi restApi;
    private final Logger logger;
    private final ProxyUsers proxyUsers;

    public GameProfileEvent(RestApi restApi, Logger logger, ProxyUsers proxyUsers) {
        this.restApi = restApi;
        this.logger = logger;
        this.proxyUsers = proxyUsers;
    }

    @Subscribe(priority = 0)
    public void onGameProfileEvent(GameProfileRequestEvent event) {


        String nickName = event.getGameProfile().getName();


        ProxyUser user = this.proxyUsers.getUser(nickName);

        if(user == null) {
            this.logger.error("Failed to get user from ProxyUsers v1");
            return;
        }

        if(!user.isPremium()) {
            return;
        }


        JSONObject response = this.restApi.sendRequest("https://api.ashcon.app/mojang/v2/user/" + nickName);

        List<GameProfile.Property> properties = new LinkedList<>();

        JSONObject raw = response.getJSONObject("textures").getJSONObject("raw");

        properties.add(new GameProfile.Property("textures", raw.getString("value"), raw.getString("signature")));


        GameProfile gameProfile = new GameProfile(user.getUuid() ,nickName, properties);
        event.setGameProfile(gameProfile);


    }
}
