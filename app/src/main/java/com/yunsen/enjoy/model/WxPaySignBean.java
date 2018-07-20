package com.yunsen.enjoy.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2018/7/5.
 */

public class WxPaySignBean {
    /**
     * app_id : wxe60c28541b0fa8a2
     * mch_id : 1505676961
     * nonce_str : f4b466874b324f97bea85ba9a5559bb2
     * partner_id : 1505676961
     * resuWxPaySignBeanlt_code : SUCCESS
     * return_msg : OK
     * prepay_id : wx05161832165732da295400f33959486117
     * url : https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx05161832165732da295400f33959486117&package=18EB8A5FB2305DF06AC8BD938AD6DA45
     * sign : 8A6A961E15925E4BDC800B0A69BC3A3B
     * trade_type : APP
     * out_trade_no : T18070309450945995
     * timestamp : 1530778712
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
    @JSONField(name = "timestamp")
    private String timestampX;

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

    public String getTimestampX() {
        return timestampX;
    }

    public void setTimestampX(String timestampX) {
        this.timestampX = timestampX;
    }
}
