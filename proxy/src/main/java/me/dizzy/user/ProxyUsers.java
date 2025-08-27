package me.dizzy.user;

import me.dizzy.util.Randomizer;

import java.util.HashMap;
import java.util.UUID;

public class ProxyUsers {
    private final HashMap<String, ProxyUser> users;

    public ProxyUsers() {
        this.users = new HashMap<>();
    }

    public ProxyUser addUser(UUID uuid, String nickName) {
        ProxyUser newUser = new ProxyUser(uuid, nickName);
        this.users.put(nickName, newUser);
        newUser.setCode(Randomizer.generateRandomWord(6));

        return newUser;
    }


    public ProxyUser getUser(UUID uuid) {
        for(ProxyUser user : this.users.values()) {
            if(user.getUuid().equals(uuid)) {
                return user;
            }
        }
        return null;
    }

    public ProxyUser getUser(String name) {
        return this.users.get(name);
    }

    public boolean isExist(String name) {
        return this.users.containsKey(name);
    }



    public HashMap<String, ProxyUser> getUsers() {
        return this.users;
    }
}
