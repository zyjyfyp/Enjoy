package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/6/9.
 */

public class WxSignData {

    /**
     * app_id : wxe60c28541b0fa8a2
     * mch_id : 1505676961
     * nonce_str : 54106511832848f1bddd2f8f3f085368
     * partner_id : 1505676961
     * result_code : SUCCESS
     * return_msg : OK
     * prepay_id : wx09173612576840d7a53e97c93126728849
     * url : https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx09173612576840d7a53e97c93126728849&package=890BAB980EC49A181CB17902F463E3D9
     * sign : A9DEEB20BF386BD82B30EAABFC9250D8
     * trade_type : APP
     * out_trade_no : T18060819580758743
     * timestamp : 1528536973
     */

    private String app_id;
    private String mch_id;
    private String nonce_str;
    private String partner_id;
    private String result_code;
    private String return_msg;
    private String prepay_id;
    private String url;
    private String sign;
    private String trade_type;
    private String out_trade_no;
    private String timestamp;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
