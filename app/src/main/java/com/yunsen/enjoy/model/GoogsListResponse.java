package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.RestApiResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class GoogsListResponse extends RestApiResponse {

    private List<GoodsData> data;

    public List<GoodsData> getData() {
        return data;
    }

    public void setData(List<GoodsData> data) {
        this.data = data;
    }


}
