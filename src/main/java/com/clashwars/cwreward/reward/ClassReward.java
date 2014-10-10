package com.clashwars.cwreward.reward;

import com.clashwars.cwcore.utils.CWUtil;
import com.clashwars.cwreward.reward.internal.Reward;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClassReward extends Reward {

    private final List<String> classes = Arrays.asList("Warrior", "Archer", "Guardian", "Rogue");


    public ClassReward(String[] categories, float percentage, int value) {
        super(categories, percentage, value);
    }

    public ClassReward(String[] categories, float percentage, int minVal, int maxVal) {
        super(categories, percentage, minVal, maxVal);
    }

    public ClassReward(String[] categories, float percentage, List<Integer> values) {
        super(categories, percentage, values);
    }

    public ClassReward(String[] categories, float percentage, Map<Integer, Double> valuesPerc, boolean addTogether) {
        super(categories, percentage, valuesPerc, addTogether);
    }

    @Override
    public String execute(String player) {
        int exp = getValue();
        String className = CWUtil.random(classes);
        String cmd = "classes givexp " + exp + " " + player + " " + className;

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        if (offlinePlayer != null && offlinePlayer.isOnline()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "queue " + player + " cmd " + cmd);
        }

        return "" + exp + " " + className + " XP";
    }

}
