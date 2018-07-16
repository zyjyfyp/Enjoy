package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/7/6.
 */

public  class AchievementAccountBean {
    /**
     * recordCount : 0
     * orderCounts : 0
     * cumulative_income : 0
     */

    private int recordCount;
    private int orderCounts;
    private double cumulative_income;

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getOrderCounts() {
        return orderCounts;
    }

    public void setOrderCounts(int orderCounts) {
        this.orderCounts = orderCounts;
    }

    public double getCumulative_income() {
        return cumulative_income;
    }

    public void setCumulative_income(double cumulative_income) {
        this.cumulative_income = cumulative_income;
    }
}
