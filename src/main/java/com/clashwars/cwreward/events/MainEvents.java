package com.clashwars.cwreward.events;

import com.clashwars.cwcore.utils.CWUtil;
import com.clashwars.cwreward.CWReward;
import com.clashwars.cwreward.config.data.LoginData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class MainEvents implements Listener {

    private CWReward cwr;

    public MainEvents(CWReward cwr) {
        this.cwr = cwr;
    }

    @EventHandler
    public void login(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        LoginData loginData = cwr.getLoginCfg().getLogin(uuid);
        //First login.
        if (loginData.getLastRewardTime() <= 0) {
            player.sendMessage(CWUtil.formatCWMsg("&6Come back tomorrow to get your first daily login reward."));
            loginData.setLogins(0);
            loginData.setLastRewardTime(System.currentTimeMillis());
            cwr.getLoginCfg().setLogin(uuid, loginData);
            return;
        }
        if (loginData.getTimeSinceReward() > 86400000) {
            if (loginData.getTimeSinceReward() <= 172800000) {
                player.sendMessage(CWUtil.formatCWMsg("&6Your login count has been increased by one because you logged on within a day this means you will get more coins!"));
                loginData.setLogins(loginData.getLogins() + 1);
            } else {
                player.sendMessage(CWUtil.formatCWMsg("&cYour login count got reset because you didn't log in within a day."));
                player.sendMessage(CWUtil.formatCWMsg("&cIf you log in every day you get more and more coins each day."));
                loginData.setLogins(1);
            }
            //Give reward
            int coins = 50 * loginData.getLogins();
            player.sendMessage("&6You received your daily login reward. &e" + coins + " coins&6.");
            cwr.getEconomy().depositPlayer(player, coins);
            loginData.setLastRewardTime(System.currentTimeMillis());
            cwr.getLoginCfg().setLogin(uuid, loginData);
        } else {
            player.sendMessage(CWUtil.formatCWMsg("&6Come back in &5" + CWUtil.formatTime(loginData.getTimeTillReward(), "&5%H&8:&5%M&8:&5%S") + " &6to get your daily login reward."));
        }

    }
}
