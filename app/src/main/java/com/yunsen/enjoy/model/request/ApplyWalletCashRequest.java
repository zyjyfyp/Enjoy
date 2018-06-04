package com.yunsen.enjoy.model.request;

/**
 * Created by Administrator on 2018/5/31.
 * 申请提现的request
 */

public class ApplyWalletCashRequest {
    private String user_id;//6783
    private String sign;//23F203FAD6BA1278A787CBFA6F5061E1
    private String bank_id;//1006
    private String amount;//0.01
    private String paypassword;//123456
    private String desc;//测试

    public ApplyWalletCashRequest(String user_id, String sign) {
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

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
