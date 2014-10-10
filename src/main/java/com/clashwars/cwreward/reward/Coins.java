package com.clashwars.cwreward.reward;

import com.clashwars.cwcore.utils.CWUtil;
import com.clashwars.cwreward.reward.internal.Reward;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class Coins extends Reward {

    int coinType = -1;
    int value = 1;
    int minValue = -1;
    int maxValue = -1;
    List<Integer> values = null;
    Map<Integer, Float> valuesPerc = null;

    /**
     * Create coins with a specific value. [TYPE: 0]
     * @param categories The reward categories.
     * @param percentage The percentage chance to get this.
     * @param value The amount of coins to give.
     */
    public Coins(String[] categories, float percentage, int value) {
        super(categories, percentage);
        coinType = 0;
        this.value = value;
    }

    /**
     * Create coins with a random value between the 2 specified numbers. [TYPE: 1]
     * @param categories The reward categories.
     * @param percentage The percentage chance to get this.
     * @param minValue The minimum amount of coins to give.
     * @param maxValue The maximum amount of coins to give.
     */
    public Coins(String[] categories, float percentage, int minValue, int maxValue) {
        super(categories, percentage);
        coinType = 1;
        this.minValue = minValue;
        this.maxValue = minValue;
    }

    /**
     * Create coins with a list of values and it will pick a random one out of it. [TYPE: 2]
     * @param categories The reward categories.
     * @param percentage The percentage chance to get this.
     * @param values A list with coin values to pick a random one out.
     */
    public Coins(String[] categories, float percentage, List<Integer> values) {
        super(categories, percentage);
        coinType = 2;
        this.values = values;
    }

    /**
     * Create coins with a map of values and percentages.
     * The percentages need to sum with with a total of 1.
     * For example [50->0.4, 100->0.3, 150->0.2, 200->0.1]
     * If addTogether is true it will check each reward 1 by 1.
     * so you have 40% chance to get 50 and if you get that and then you also get the 30% chance you will get 150.
     * You should always specify a value with 100% chance because otherwise it's not guaranteed there will be a value.
     * @param categories The reward categories.
     * @param percentage The percentage chance to get this.
     * @param valuesPerc
     * @param addTogether Should it sum up all values?
     */
    public Coins(String[] categories, float percentage, Map<Integer, Float> valuesPerc, boolean addTogether) {
        super(categories, percentage);
        if (addTogether) {
            coinType = 3;
        } else {
            coinType = 4;
        }
        this.valuesPerc = valuesPerc;
    }


    @Override
    public String execute(String player) {
        int coins = -1;
        if (coinType == 0) {
            coins = value;
        } else if (coinType == 1) {
            coins = CWUtil.random(minValue, maxValue);
        } else if (coinType == 2) {
            coins = CWUtil.random(values);
        } else if (coinType == 3) {
            coins = 0;
            for (int val : valuesPerc.keySet()) {
                float random = CWUtil.randomFloat();
                if (valuesPerc.get(val) <= random) {
                    coins += val;
                }
            }
        } else if (coinType == 4) {
            //TODO: Mode 4.
        }

        //TODO: Give coins.

        return "" + coins + " Coins";
    }

}
