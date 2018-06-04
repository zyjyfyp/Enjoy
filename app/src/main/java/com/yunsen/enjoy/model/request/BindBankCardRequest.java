package com.yunsen.enjoy.model.request;

/**
 * Created by Administrator on 2018/5/31.
 */

public class BindBankCardRequest {
    private String user_id;//6783
    private String sign;//23F203FAD6BA1278A787CBFA6F5061E1
    private String bank_certtype;//‘’
    private String bank_account;//张勇
    private String bank_name;//公司银行
    private String bank_branch;//‘’
    private String bank_province;//深圳
    private String bank_city;//宝安
    private String bank_area;//固戍
    private String bank_certificate;//''
    private String bank_card;//''

    public BindBankCardRequest(String user_id, String sign) {
        this.user_id = user_id;
        this.sign = sign;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBank_certtype() {
        return bank_certtype;
    }

    public void setBank_certtype(String bank_certtype) {
        this.bank_certtype = bank_certtype;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_branch() {
        return bank_branch;
    }

    public void setBank_branch(String bank_branch) {
        this.bank_branch = bank_branch;
    }

    public String getBank_province() {
        return bank_province;
    }

    public void setBank_province(String bank_province) {
        this.bank_province = bank_province;
    }

    public String getBank_city() {
        return bank_city;
    }

    public void setBank_city(String bank_city) {
        this.bank_city = bank_city;
    }

    public String getBank_area() {
        return bank_area;
    }

    public void setBank_area(String bank_area) {
        this.bank_area = bank_area;
    }

    public String getBank_certificate() {
        return bank_certificate;
    }

    public void setBank_certificate(String bank_certificate) {
        this.bank_certificate = bank_certificate;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }
}
