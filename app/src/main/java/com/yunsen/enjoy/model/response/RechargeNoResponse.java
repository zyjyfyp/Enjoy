package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.RechargeNoBean;

/**
 * Created by Administrator on 2018/7/10.
 */

public class RechargeNoResponse extends RestApiResponse {

    /**
     * data : {"recharge_no":"R180710104920492038"}
     */

    private RechargeNoBean data;

    public RechargeNoBean getData() {
        return data;
    }

    public void setData(RechargeNoBean data) {
        this.data = data;
    }


}
