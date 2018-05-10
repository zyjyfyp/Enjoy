package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.WatchCarBean;

/**
 * Created by Administrator on 2018/5/10.
 */

public class WatchCarResponse extends RestApiResponse {

    /**
     * data : {"total_amount":0,"trade_no":"T180510154937493781"}
     */

    private WatchCarBean data;

    public WatchCarBean getData() {
        return data;
    }

    public void setData(WatchCarBean data) {
        this.data = data;
    }

}
