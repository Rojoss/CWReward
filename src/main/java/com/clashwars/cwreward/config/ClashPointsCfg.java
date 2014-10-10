package com.clashwars.cwreward.config;

import com.clashwars.cwcore.config.internal.EasyConfig;

public class ClashPointsCfg extends EasyConfig {

    public int SOME_CONFIG_PROPERTY = 0;

    public ClashPointsCfg(String fileName) {
        this.setFile(fileName);
    }
}
