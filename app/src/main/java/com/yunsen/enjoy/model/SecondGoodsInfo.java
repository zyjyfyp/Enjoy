package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/6/15.
 */

public class SecondGoodsInfo {
    public static final int NO_BEGIN = 0;  //未开始
    public static final int HAS_START = 1; //已开始未结束
    public static final int HAS_END = 2;   //结束
    private long startTime;
    private long endTime;
    private long currentTime;
    private long remainingTime;//剩余时间

    private int state; //0未开始 1 已开始未结束 2 结束

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }

    public boolean nextRemainingTime() {
        remainingTime--;
        if (remainingTime < 0) {
            return false;
        }
        return true;
    }
}
