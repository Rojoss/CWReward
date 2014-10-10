package com.clashwars.cwreward.config;

import com.clashwars.cwcore.config.internal.EasyConfig;
import com.clashwars.cwreward.CWReward;
import com.clashwars.cwreward.config.data.LoginData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginCfg extends EasyConfig {

    public Map<String, String> logins = new HashMap<String, String>();

    public LoginCfg(String fileName) {
        this.setFile(fileName);
    }

    public LoginData getLogin(UUID uuid) {
        if (logins.containsKey(uuid.toString())) {
            return CWReward.inst().getGson().fromJson(logins.get(uuid.toString()), LoginData.class);
        }
        return new LoginData();
    }

    public void setLogin(UUID uuid, LoginData data) {
        logins.put(uuid.toString(), CWReward.inst().getGson().toJson(data));
        save();
    }

    public Map<UUID, LoginData> getLogins() {
        Map<UUID, LoginData> playerLogins = new HashMap<UUID, LoginData>();
        for (String uuid : logins.keySet()) {
            playerLogins.put(UUID.fromString(uuid), CWReward.inst().getGson().fromJson(logins.get(uuid), LoginData.class));
        }
        return playerLogins;
    }
}
