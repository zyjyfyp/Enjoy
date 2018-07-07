package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/7/6.
 */

public class ListcumulativeIncomeBean {
    /**
     * consumer_name : 哈里
     * consumer_id : 1208
     * month_income : 84
     */

    private String consumer_name;
    private String consumer_id;
    private double month_income;

    public String getConsumer_name() {
        return consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(String consumer_id) {
        this.consumer_id = consumer_id;
    }

    public double getMonth_income() {
        return month_income;
    }

    public void setMonth_income(double month_income) {
        this.month_income = month_income;
    }
}