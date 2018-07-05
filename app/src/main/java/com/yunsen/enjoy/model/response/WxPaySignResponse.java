package com.yunsen.enjoy.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.WxPaySignBean;

/**
 * Created by Administrator on 2018/7/5.
 */

public class WxPaySignResponse extends RestApiResponse {

    /**
     * data : {"app_id":"wxe60c28541b0fa8a2","mch_id":"1505676961","nonce_str":"f4b466874b324f97bea85ba9a5559bb2","partner_id":"1505676961","result_code":"SUCCESS","return_msg":"OK","prepay_id":"wx05161832165732da295400f33959486117","url":"https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx05161832165732da295400f33959486117&package=18EB8A5FB2305DF06AC8BD938AD6DA45","sign":"8A6A961E15925E4BDC800B0A69BC3A3B","trade_type":"APP","out_trade_no":"T18070309450945995","timestamp":"1530778712"}
     */

    private WxPaySignBean data;

    public WxPaySignBean getData() {
        return data;
    }

    public void setData(WxPaySignBean data) {
        this.data = data;
    }

}
