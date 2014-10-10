package com.clashwars.cwreward.config.data;

public class LoginData {

    private int logins = 0;
    private long lastRewardTime = 0;

    public int getLogins() {
        return logins;
    }
    public void setLogins(int logins) {
        this.logins = logins;
    }

    public long getLastRewardTime() {
        return lastRewardTime;
    }
    public long getTimeSinceReward() {
        return Math.max(System.currentTimeMillis() - lastRewardTime, 0);
    }
    public long getTimeTillReward() {
        return Math.max(86400000 - getTimeSinceReward(), 0);
    }
    public void setLastRewardTime(long lastRewardTime) {
        this.lastRewardTime = lastRewardTime;
    }
}
