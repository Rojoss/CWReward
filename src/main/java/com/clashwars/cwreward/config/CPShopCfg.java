package com.clashwars.cwreward.config;

import com.clashwars.cwcore.config.internal.EasyConfig;

public class CPShopCfg extends EasyConfig {

    public int SOME_CONFIG_PROPERTY = 0;

    public CPShopCfg(String fileName) {
        this.setFile(fileName);
    }
}
