package com.clashwars.cwreward.reward.internal;

public class Reward {

    private String[] categories;
    private float percentage;

    public Reward(String[] categories, float percentage) {
        this.categories = categories;
        this.percentage = percentage;
    }

    public String execute(String player) {
        return "";
    }

    public String[] getCategories() {
        return categories;
    }

    public boolean hasCategory(String category) {
        for (String cat : categories) {
            if (cat.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }

    public float getPercentage() {
        return percentage;
    }
}
