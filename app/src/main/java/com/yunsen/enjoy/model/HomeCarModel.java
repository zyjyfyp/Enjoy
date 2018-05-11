package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/5/11.
 */

public class HomeCarModel {
    private String money;
    private String name;

    public HomeCarModel(String money, String name) {
        this.money = money;
        this.name = name;
    }

    public HomeCarModel() {
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
