package com.clashwars.cwreward.reward.internal;

import com.clashwars.cwcore.helpers.CWItem;
import com.clashwars.cwcore.utils.CWUtil;
import com.clashwars.cwreward.CWReward;
import com.clashwars.cwreward.reward.CoinReward;
import com.clashwars.cwreward.reward.ItemReward;
import org.bukkit.Material;

import java.util.*;

public class RewardManager {

    private CWReward cwr;
    private Map<String, Reward> rewards = new HashMap<String, Reward>();

    public RewardManager(CWReward cwr) {
        this.cwr = cwr;
        populateRewards();
    }

    public void populateRewards() {
        rewards.put("coinsTest", new CoinReward(new String[] {"*"}, 0.5f, 100, 250));
        rewards.put("itemTest1", new ItemReward(new String[] {"*"}, 0.5f, new CWItem(Material.DIAMOND), 1, 3));
        rewards.put("itemTest2", new ItemReward(new String[] {"*"}, 0.5f, new CWItem(Material.TNT), Arrays.asList(2,4,6)));
    }

    public String giveSpecificReward(String playerName, String name) {
        if (rewards.containsKey(name)) {
            return rewards.get(name).execute(playerName);
        }
        return "";
    }

    public String giveRandomReward(String playerName) {
        return giveRandomReward(playerName, "*");
    }

    public String giveRandomReward(String playerName, String category) {
        //TODO: implement.
        return "";
    }

    public List<String> giveRewards(String playerName, String category, boolean forceReward) {
        List<String> rewardsGiven = new ArrayList<String>();
        Map<String, Reward> categorizedRewards = getRewardsByCategory(category);
        if (forceReward) {
            int count = 0;
            while (rewardsGiven.size() <= 0 && count <= 5) {
                for (Reward reward : categorizedRewards.values()) {
                    if (CWUtil.randomFloat() <= reward.getPercentage()) {
                        rewardsGiven.add(reward.execute(playerName));
                    }
                }
                count++;
            }
        } else {
            for (Reward reward : categorizedRewards.values()) {
                if (CWUtil.randomFloat() <= reward.getPercentage()) {
                    rewardsGiven.add(reward.execute(playerName));
                }
            }
        }
        return rewardsGiven;
    }


    public Map<String, Reward> getRewardsByCategory(String category) {
        Map<String, Reward> categorizedRewards = new HashMap<String, Reward>();
        for (String name : rewards.keySet()) {
            Reward r = rewards.get(name);
            if (r.hasCategory(category)) {
                categorizedRewards.put(name, r);
            }
        }
        return categorizedRewards;
    }

}
