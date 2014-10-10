package com.clashwars.cwreward.config;

import com.clashwars.cwcore.config.internal.EasyConfig;

public class LoginCfg extends EasyConfig {

    public int SOME_CONFIG_PROPERTY = 0;

    public LoginCfg(String fileName) {
        this.setFile(fileName);
    }
}
