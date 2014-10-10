package com.clashwars.cwreward.reward.internal;

import com.clashwars.cwcore.helpers.CWItem;
import com.clashwars.cwcore.utils.CWUtil;
import com.clashwars.cwreward.CWReward;
import com.clashwars.cwreward.reward.*;
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
        Map<Integer, Double> valuesPerc = new HashMap<Integer, Double>();
        rewards.put("coinsTest", new CoinReward(new String[] {"*"}, 0.1f, 100, 250));
        rewards.put("itemTest1", new ItemReward(new String[] {"*"}, 0.1f, new CWItem(Material.DIAMOND), 1, 3));
        rewards.put("itemTest2", new ItemReward(new String[] {"*"}, 0.1f, new CWItem(Material.TNT), Arrays.asList(2,4,6)));
        rewards.put("expTest1", new ExpReward(new String[] {"*"}, 0.1f, Arrays.asList(100, 200, 300, 400, 500)));
        rewards.put("expTest2", new ExpReward(new String[] {"*"}, 0.1f, 999));
        rewards.put("cmdTest1", new CmdReward(new String[] {"*"}, 0.1f, 1, 10, "spawnmob pig {VAL} {PLAYER}", "{VAL} Pigs"));

        // VOTING REWARDS
        valuesPerc.put(1, 1.0);
        valuesPerc.put(2, 0.5);
        valuesPerc.put(3, 0.1);
        rewards.put("vote-dia", new ItemReward(new String[] {"*", "vote"}, 0.1f, new CWItem(Material.DIAMOND), valuesPerc, false));

        valuesPerc.clear();
        valuesPerc.put(4, 1.0);
        valuesPerc.put(8, 0.5);
        valuesPerc.put(16, 0.1);
        rewards.put("vote-tnt", new ItemReward(new String[] {"*", "vote"}, 0.15f, new CWItem(Material.TNT), valuesPerc, false));
        rewards.put("vote-emeralds", new ItemReward(new String[] {"*", "vote"}, 0.25f, new CWItem(Material.EMERALD), valuesPerc, false));
        rewards.put("vote-xp", new ExpReward(new String[] {"*", "vote"}, 0.2f, Arrays.asList(100, 200, 300, 400, 500)));
        rewards.put("vote-coins", new CoinReward(new String[] {"*", "vote"}, 0.2f, Arrays.asList(200, 300, 400, 500, 600, 700, 800)));
        rewards.put("vote-villager", new CmdReward(new String[] {"*", "vote"}, 0.01f, 1, "spawnmob villager {VAL} {PLAYER}", "&6SPECIAL&8: &aA villager"));
        //TODO: vote-haste
        //TODO: vote-clashpoints


        // EVENT REWARDS
        valuesPerc.clear();
        valuesPerc.put(1, 1.0);
        valuesPerc.put(2, 0.5);
        rewards.put("event-dia", new ItemReward(new String[] {"*", "event"}, 0.05f, new CWItem(Material.DIAMOND), valuesPerc, false));

        valuesPerc.clear();
        valuesPerc.put(2, 1.0);
        valuesPerc.put(4, 0.5);
        valuesPerc.put(8, 0.1);
        rewards.put("event-tnt", new ItemReward(new String[] {"*", "event"}, 0.1f, new CWItem(Material.TNT), valuesPerc, false));
        rewards.put("event-emeralds", new ItemReward(new String[] {"*", "event"}, 0.2f, new CWItem(Material.EMERALD), valuesPerc, false));
        rewards.put("event-xp", new ExpReward(new String[] {"*", "event"}, 0.15f, Arrays.asList(50, 100, 150, 200, 250, 300)));
        rewards.put("event-coins", new CoinReward(new String[] {"*", "event"}, 0.15f, Arrays.asList(50, 100, 150, 200, 250, 300)));
        rewards.put("event-classxp", new ClassReward(new String[] {"*", "event"}, 0.2f, Arrays.asList(50, 100, 150, 200, 250, 300)));
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
        Map<String, Reward> categorizedRewards = getRewardsByCategory(category);

        double totalPerc = 0;
        for (Reward reward : categorizedRewards.values()) {
            totalPerc += reward.getPercentage();
        }

        double random = Math.random() * totalPerc;
        for (Reward reward : categorizedRewards.values()) {
            random -= reward.getPercentage();
            if (random <= 0.0d) {
                return reward.execute(playerName);
            }
        }
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

    public Map<String, Reward> getRewards() {
        return rewards;
    }

}
