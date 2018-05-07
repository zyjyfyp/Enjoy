package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.SProviderModel;

import java.util.List;

/**
 * Created by Administrator on 2018/5/7.
 */

public class ServerShopResponse extends RestApiResponse {

    private List<SProviderModel> data;

    public List<SProviderModel> getData() {
        return data;
    }

    public void setData(List<SProviderModel> data) {
        this.data = data;
    }


}
