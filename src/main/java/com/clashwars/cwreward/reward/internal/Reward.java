package com.clashwars.cwreward.reward.internal;

import com.clashwars.cwcore.utils.CWUtil;

import java.util.List;
import java.util.Map;

public class Reward {

    private String[] categories;
    private float percentage;

    protected int type = -1;

    protected int val = -1;
    protected int minVal = -1;
    protected int maxVal = -1;
    protected List<Integer> values;
    protected Map<Integer, Float> valuesPerc;

    /**
     * Create a reward without a value.
     * @param categories The reward categories.
     * @param percentage The percentage chance to get this.
     */
    public Reward(String[] categories, float percentage) {
        type = 0;
        this.categories = categories;
        this.percentage = percentage;
    }

     /**
     * Create reward with a specific value. [TYPE: 1]
     * @param categories The reward categories.
     * @param percentage The percentage chance to get this.
     * @param val The value to give.
     */
    public Reward(String[] categories, float percentage, int val) {
        type = 1;
        this.categories = categories;
        this.percentage = percentage;
        this.val = val;
    }

    /**
     * Create reward with a random value between the 2 specified numbers. [TYPE: 2]
     * @param categories The reward categories.
     * @param percentage The percentage chance to get this.
     * @param minVal The minimum value.
     * @param maxVal The maximum value.
     */
    public Reward(String[] categories, float percentage, int minVal, int maxVal) {
        type = 2;
        this.categories = categories;
        this.percentage = percentage;
        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    /**
     * Create reward with a list of values and it will pick a random one out of it. [TYPE: 3]
     * @param categories The reward categories.
     * @param percentage The percentage chance to get this.
     * @param values A list with values to pick a random one out.
     */
    public Reward(String[] categories, float percentage, List<Integer> values) {
        type = 3;
        this.categories = categories;
        this.percentage = percentage;
        this.values = values;
    }

    /**
     * Create reward with a map of values and percentages.
     * The percentages need to sum with with a total of 1.
     * For example [50->0.4, 100->0.3, 150->0.2, 200->0.1]
     * If addTogether is true it will check each reward 1 by 1.
     * so you have 40% chance to get 50 and if you get that and then you also get the 30% chance you will get 150.
     * You should always specify a value with 100% chance because otherwise it's not guaranteed there will be a value.
     * @param categories The reward categories.
     * @param percentage The percentage chance to get this.
     * @param valuesPerc The map with values and percentages.
     * @param addTogether Should it sum up all values?
     */
    public Reward(String[] categories, float percentage, Map<Integer, Float> valuesPerc, boolean addTogether) {
        this.categories = categories;
        this.percentage = percentage;
        this.valuesPerc = valuesPerc;
        if (addTogether) {
            type = 4;
        } else {
            type = 5;
        }
    }


    /**
     * Execute the reward and give it to the specified player.
     * @param player The player to give the reward to.
     * @return String with the reward name for example '10 Diamond' or '250 Coins' returns empty string if no reward was given.
     */
    public String execute(String player) {
        return "";
    }

    /**
     * Get the value based on the type and the values specified in the constructor.
     * For example if it's mode 2 it will return a random value between the min/max value.
     * Make sure to call this once and save it locally because it most likely will return a diff value each time it's called.
     * @return Specific value for this reward.
     */
    public int getValue() {
        int value = 0;
        if (type == 1) {
            value = val;
        } else if (type == 2) {
            value = CWUtil.random(minVal, maxVal);
        } else if (type == 3) {
            value = CWUtil.random(values);
        } else if (type == 4) {
            value = 0;
            for (int v : valuesPerc.keySet()) {
                if (CWUtil.randomFloat() <= valuesPerc.get(v)) {
                    value += v;
                }
            }
        } else if (type == 4) {
            //TODO: Mode 4.
        }
        return value;
    }


    /**
     * Get all categories the reward is in.
     * @return Array of categories.
     */
    public String[] getCategories() {
        return categories;
    }

    /**
     * Check if this rewards is in the specified category.
     * @param category The name of the category to check.
     * @return True if it has the category and False if not.
     */
    public boolean hasCategory(String category) {
        for (String cat : categories) {
            if (cat.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the percentage of this reward.
     * @return Float number between 0.0 and 1.0.
     */
    public float getPercentage() {
        return percentage;
    }
}
