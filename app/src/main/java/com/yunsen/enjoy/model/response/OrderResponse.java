package com.yunsen.enjoy.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.OrderDataBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class OrderResponse extends RestApiResponse {

    private List<OrderDataBean> data;

    public List<OrderDataBean> getData() {
        return data;
    }

    public void setData(List<OrderDataBean> data) {
        this.data = data;
    }

}
