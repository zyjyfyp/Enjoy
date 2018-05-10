package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;

/**
 * Created by Administrator on 2018/5/10.
 */

public class StringResponse extends RestApiResponse {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
