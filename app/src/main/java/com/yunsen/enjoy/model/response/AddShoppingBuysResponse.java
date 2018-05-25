package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.OrderInfo;

/**
 * Created by Administrator on 2018/5/22.
 */

public class AddShoppingBuysResponse extends RestApiResponse {

    /**
     * data : {"buy_no":"B201805221047294357","count":2}
     */

    private OrderInfo data;

    public OrderInfo getData() {
        return data;
    }

    public void setData(OrderInfo data) {
        this.data = data;
    }


}
