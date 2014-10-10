package com.clashwars.cwreward;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwreward.commands.Commands;
import com.clashwars.cwreward.config.LoginCfg;
import com.clashwars.cwreward.config.PluginCfg;
import com.clashwars.cwreward.events.MainEvents;
import com.clashwars.cwreward.reward.internal.RewardManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class CWReward extends JavaPlugin {
    private static CWReward instance;
    private CWCore cwcore;
    private Economy econ;

    private Gson gson = new Gson();

    private PluginCfg cfg;
    private LoginCfg loginCfg;

    private RewardManager rm;

    private Commands cmds;

    private final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onDisable() {
        loginCfg.save();
        Bukkit.getScheduler().cancelTasks(this);
        log("disabled");
    }

    @Override
    public void onEnable() {
        instance = this;

        Plugin plugin = getServer().getPluginManager().getPlugin("CWCore");
        if (plugin == null || !(plugin instanceof CWCore)) {
            log("CWCore dependency couldn't be loaded!");
            setEnabled(false);
            return;
        }
        cwcore = (CWCore) plugin;
        econ = cwcore.GetDM().getEconomy();
        if (econ == null) {
            log("Vault couldn't be loaded.");
            setEnabled(false);
            return;
        }

        cfg = new PluginCfg("plugins/CWReward/CWReward.yml");
        cfg.load();

        loginCfg = new LoginCfg("plugins/CWReward/logins.yml");
        loginCfg.load();

        rm = new RewardManager(this);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new MainEvents(this), this);

        cmds = new Commands(this);

        log("loaded successfully");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return cmds.onCommand(sender, cmd, label, args);
    }

    public void log(Object msg) {
        log.info("[CWReward " + getDescription().getVersion() + "] " + msg.toString());
    }

    public static CWReward inst() {
        return instance;
    }

	
	/* Getters & Setters */

    public Gson getGson() {
        return gson;
    }

    public Economy getEconomy() {
        return econ;
    }

    public RewardManager getRM() {
        return rm;
    }

    public PluginCfg getCfg() {
        return cfg;
    }

    public LoginCfg getLoginCfg() {
        return loginCfg;
    }
}