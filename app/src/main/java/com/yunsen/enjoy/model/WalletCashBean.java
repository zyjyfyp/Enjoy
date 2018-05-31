package com.yunsen.enjoy.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/5/31.
 * 提现明细
 */

public class WalletCashBean {
    /**
     * id : 70049
     * serial_no : H171012205325532538
     * trade_no : null
     * order_no : B171012205315531589
     * from_user_id : 7025
     * from_user_name : T15220072935
     * from_previous : 7203.83
     * from_expense : 538
     * from_balance : 6665.83
     * to_user_id : null
     * to_user_name : null
     * to_previous : null
     * to_income : null
     * to_balance : null
     * fund_id : 1
     * platform_id : 1
     * payment_id : 2
     * consumer_id : 0
     * consumer_name : null
     * expenses_id : 0
     * company_id : 0
     * company_name : null
     * datatype : null
     * remark : 扣取账户金额
     * add_time : 2017-10-12 20:53:25
     * update_time : 2017-10-12 20:53:25
     */

    private int id;
    private String serial_no;
    private Object trade_no;
    private String order_no;
    private int from_user_id;
    private String from_user_name;
    private double from_previous;
    private int from_expense;
    private double from_balance;
    private Object to_user_id;
    private Object to_user_name;
    private Object to_previous;
    private Object to_income;
    private Object to_balance;
    private int fund_id;
    private int platform_id;
    private int payment_id;
    private int consumer_id;
    private Object consumer_name;
    private int expenses_id;
    private int company_id;
    private Object company_name;
    private Object datatype;
    private String remark;
    private String add_time;
    private String update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public Object getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(Object trade_no) {
        this.trade_no = trade_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(int from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getFrom_user_name() {
        return from_user_name;
    }

    public void setFrom_user_name(String from_user_name) {
        this.from_user_name = from_user_name;
    }

    public double getFrom_previous() {
        return from_previous;
    }

    public void setFrom_previous(double from_previous) {
        this.from_previous = from_previous;
    }

    public int getFrom_expense() {
        return from_expense;
    }

    public String getFrom_expenseStr() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(from_expense);
    }

    public void setFrom_expense(int from_expense) {
        this.from_expense = from_expense;
    }

    public double getFrom_balance() {
        return from_balance;
    }

    public void setFrom_balance(double from_balance) {
        this.from_balance = from_balance;
    }

    public Object getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(Object to_user_id) {
        this.to_user_id = to_user_id;
    }

    public Object getTo_user_name() {
        return to_user_name;
    }

    public void setTo_user_name(Object to_user_name) {
        this.to_user_name = to_user_name;
    }

    public Object getTo_previous() {
        return to_previous;
    }

    public void setTo_previous(Object to_previous) {
        this.to_previous = to_previous;
    }

    public Object getTo_income() {
        return to_income;
    }

    public void setTo_income(Object to_income) {
        this.to_income = to_income;
    }

    public Object getTo_balance() {
        return to_balance;
    }

    public void setTo_balance(Object to_balance) {
        this.to_balance = to_balance;
    }

    public int getFund_id() {
        return fund_id;
    }

    public void setFund_id(int fund_id) {
        this.fund_id = fund_id;
    }

    public int getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(int platform_id) {
        this.platform_id = platform_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        this.consumer_id = consumer_id;
    }

    public Object getConsumer_name() {
        return consumer_name;
    }

    public void setConsumer_name(Object consumer_name) {
        this.consumer_name = consumer_name;
    }

    public int getExpenses_id() {
        return expenses_id;
    }

    public void setExpenses_id(int expenses_id) {
        this.expenses_id = expenses_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public Object getCompany_name() {
        return company_name;
    }

    public void setCompany_name(Object company_name) {
        this.company_name = company_name;
    }

    public Object getDatatype() {
        return datatype;
    }

    public void setDatatype(Object datatype) {
        this.datatype = datatype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}