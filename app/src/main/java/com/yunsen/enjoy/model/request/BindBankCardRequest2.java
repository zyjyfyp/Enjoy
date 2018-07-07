package com.yunsen.enjoy.model.request;

/**
 * Created by Administrator on 2018/5/31.
 */

public class BindBankCardRequest2 {
    private String user_id;//用户id
    private String user_name;//用户名
    private String mobile;//手机号码
    private String code;//验证码
    private String bank_name;//开户银行
    private String bank_branch;//支行名称
    private String bank_account;//开户名
    private String bank_card;//银行卡号

    public BindBankCardRequest2(String user_id, String user_name) {
        this.user_id = user_id;
        this.user_name = user_name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public void setBank_branch(String bank_branch) {
        this.bank_branch = bank_branch;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }
}
