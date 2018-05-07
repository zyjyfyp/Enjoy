package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.SProviderModel;


/**
 * Created by Administrator on 2018/5/7.
 */

public class ServiceShopInfoResponse extends RestApiResponse {
    private SProviderModel data;

    public SProviderModel getData() {
        return data;
    }

    public void setData(SProviderModel data) {
        this.data = data;
    }
}
