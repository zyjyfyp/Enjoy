package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.AddressInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/6/28.
 */

public class ShoppingAddressResponse extends RestApiResponse {

    private List<AddressInfo> data;

    public List<AddressInfo> getData() {
        return data;
    }

    public void setData(List<AddressInfo> data) {
        this.data = data;
    }


}
