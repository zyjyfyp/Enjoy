package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.ShopCarCount;

/**
 * Created by Administrator on 2018/5/18.
 */

public class ShopCarAccountResponse extends RestApiResponse {

    /**
     * data : {"count":3,"quantity":7,"amount":1216}
     */

    private ShopCarCount data;

    public ShopCarCount getData() {
        return data;
    }

    public void setData(ShopCarCount data) {
        this.data = data;
    }


}
