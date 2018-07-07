package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/7/6.
 */

public  class ProfitCountBean {
    /**
     * push_income : 0
     * team_income : 0
     * agent_income : 0
     * 盖亚我的业绩直推/团队/代理（累计收益）统计：get_profit_count
     */

    private double push_income;
    private double team_income;
    private double agent_income;
    /**
     * profit_count : 0
     * month_profit_count : 0
     * order_count : 1
     * 统计本月盈收和累计收益：get_payment_amount_sum
     */

    private double profit_count;
    private double month_profit_count;
    private int order_count;

    public double getPush_income() {
        return push_income;
    }

    public void setPush_income(double push_income) {
        this.push_income = push_income;
    }

    public double getTeam_income() {
        return team_income;
    }

    public void setTeam_income(double team_income) {
        this.team_income = team_income;
    }

    public double getAgent_income() {
        return agent_income;
    }

    public void setAgent_income(double agent_income) {
        this.agent_income = agent_income;
    }

    public double getProfit_count() {
        return profit_count;
    }

    public void setProfit_count(double profit_count) {
        this.profit_count = profit_count;
    }

    public double getMonth_profit_count() {
        return month_profit_count;
    }

    public void setMonth_profit_count(double month_profit_count) {
        this.month_profit_count = month_profit_count;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }
}
