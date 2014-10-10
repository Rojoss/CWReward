package com.clashwars.cwreward.reward;

import com.clashwars.cwreward.reward.internal.Reward;

import java.util.List;
import java.util.Map;

public class CoinReward extends Reward {

    public CoinReward(String[] categories, float percentage, int value) {
        super(categories, percentage, value);
    }

    public CoinReward(String[] categories, float percentage, int minVal, int maxVal) {
        super(categories, percentage, minVal, maxVal);
    }

    public CoinReward(String[] categories, float percentage, List<Integer> values) {
        super(categories, percentage, values);
    }

    public CoinReward(String[] categories, float percentage, Map<Integer, Float> valuesPerc, boolean addTogether) {
        super(categories, percentage, valuesPerc, addTogether);
    }


    @Override
    public String execute(String player) {
        int coins = getValue();

        //TODO: Give coins.

        return "" + coins + " Coins";
    }

}
