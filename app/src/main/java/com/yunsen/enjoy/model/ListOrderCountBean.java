package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/7/6.
 */

public class ListOrderCountBean {
    /**
     * add_time : 2018-07-04 22:15:43
     * datatype : 订单
     * nick_name : 哈里
     * user_code : 1208
     * real_amount : 100
     * paycount : 12
     */

    private String add_time;
    private String datatype;
    private String nick_name;
    private String user_code;
    private double real_amount;
    private double paycount;

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public double getReal_amount() {
        return real_amount;
    }

    public void setReal_amount(double real_amount) {
        this.real_amount = real_amount;
    }

    public double getPaycount() {
        return paycount;
    }

    public void setPaycount(double paycount) {
        this.paycount = paycount;
    }
}
