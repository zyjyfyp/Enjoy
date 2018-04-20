package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.RestApiResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */

public class ServiceProvideResponse extends RestApiResponse {

    /**
     * data : null
     */

    private List<SProviderModel> data;

    public List<SProviderModel> getData() {
        return data;
    }

    public void setData(List<SProviderModel> data) {
        this.data = data;
    }
}
