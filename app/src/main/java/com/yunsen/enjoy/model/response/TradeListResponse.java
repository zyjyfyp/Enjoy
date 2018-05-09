package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.TradeData;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class TradeListResponse extends RestApiResponse {


    /**
     * id : 291
     * parent_id : 0
     * title : 代理
     * img_url :
     * icon_url :
     */

    private List<TradeData> data;

    public List<TradeData> getData() {
        return data;
    }

    public void setData(List<TradeData> data) {
        this.data = data;
    }


}
