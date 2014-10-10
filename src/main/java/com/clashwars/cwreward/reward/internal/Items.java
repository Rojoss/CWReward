package com.clashwars.cwreward.reward.internal;

import com.clashwars.cwcore.CWCore;
import com.clashwars.cwcore.helpers.CWItem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class Items extends Reward {

    CWItem item;

    //Item rewards work for offline players with the queue system but it wont keep meta data.
    //So it's best to only give item rewards to online players.

    public Items(String[] categories, float percentage, CWItem item) {
        super(categories, percentage, item.getAmount());
    }

    public Items(String[] categories, float percentage, CWItem item, int minAmt, int maxAmt) {
        super(categories, percentage, minAmt, maxAmt);
        this.item = item;
    }

    public Items(String[] categories, float percentage, CWItem item, List<Integer> amounts) {
        super(categories, percentage, amounts);
        this.item = item;
    }

    public Items(String[] categories, float percentage, CWItem item, Map<Integer, Float> amounts, boolean addTogether) {
        super(categories, percentage, amounts, addTogether);
        this.item = item;
    }

    @Override
    public String execute(String player) {
        int amount = getValue();
        item.setAmount(Math.max(amount, 1));

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        if (offlinePlayer != null && offlinePlayer.isOnline()) {
            item.giveToPlayer((Player)offlinePlayer);
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "queue " + player + " cmd give {PLAYER} " + item.getType().name() + ":" + item.getDurability() + " " + item.getAmount());
        }
        return "" + item.getAmount() + " " + CWCore.inst().getMaterials().getDisplayName(item.getType(), item.getDurability());
    }
}
