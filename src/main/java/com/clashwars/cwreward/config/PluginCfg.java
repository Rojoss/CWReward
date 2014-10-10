package com.clashwars.cwreward.config;

import com.clashwars.cwcore.config.internal.EasyConfig;

public class PluginCfg extends EasyConfig {

    public int SOME_CONFIG_PROPERTY = 0;

    public PluginCfg(String fileName) {
        this.setFile(fileName);
    }
}
