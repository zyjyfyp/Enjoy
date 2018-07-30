package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;

/**
 * Created by Administrator on 2018/7/30/030.
 */

public class CrashMoneyResponse extends RestApiResponse {

    /**
     * data : 10
     */

    private double data;

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
}
