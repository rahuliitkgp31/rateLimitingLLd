package org.example.entities;

public class UserTokenBucket {
    private String userId;
    long  lastReqTime;
    private int refillRatePerSec;
    private int currTokens;
    private int maxTokensAllowed;

    public UserTokenBucket(String userId, long lastReqTime, int refillRatePerSec, int currTokens, int maxTokensAllowed) {
        this.userId = userId;
        this.lastReqTime = lastReqTime;
        this.refillRatePerSec = refillRatePerSec;
        this.currTokens = currTokens;
        this.maxTokensAllowed = maxTokensAllowed;
    }

    public int getMaxTokensAllowed() {
        return maxTokensAllowed;
    }

    public void setMaxTokensAllowed(int maxTokensAllowed) {
        this.maxTokensAllowed = maxTokensAllowed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getLastReqTime() {
        return lastReqTime;
    }

    public void setLastReqTime(long lastReqTime) {
        this.lastReqTime = lastReqTime;
    }

    public int getRefillRatePerSec() {
        return refillRatePerSec;
    }

    public void setRefillRatePerSec(int refillRatePerSec) {
        this.refillRatePerSec = refillRatePerSec;
    }

    public int getCurrTokens() {
        return currTokens;
    }

    public void setCurrTokens(int currTokens) {
        this.currTokens = currTokens;
    }
}
