package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.HeightFilterBean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/11.
 */

public class HeightFilterResponse extends RestApiResponse {

    private List<HeightFilterBean> data;

    public List<HeightFilterBean> getData() {
        return data;
    }

    public void setData(List<HeightFilterBean> data) {
        this.data = data;
    }
}
