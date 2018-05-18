package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.GoodsCarInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public class GoodsCarResponse extends RestApiResponse {

    private List<GoodsCarInfo> data;

    public List<GoodsCarInfo> getData() {
        return data;
    }

    public void setData(List<GoodsCarInfo> data) {
        this.data = data;
    }


}
