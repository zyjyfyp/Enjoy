package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/7/5.
 */

public class RechargeModel {
    private String money;
    private boolean isCheck;

    public RechargeModel(String money, boolean isCheck) {
        this.money = money;
        this.isCheck = isCheck;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
