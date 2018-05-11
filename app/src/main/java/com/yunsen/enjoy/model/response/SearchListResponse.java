package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.CarDetails;

import java.util.List;

/**
 * Created by Administrator on 2018/5/11.
 */

public class SearchListResponse extends RestApiResponse {
    private List<CarDetails> data;

    public List<CarDetails> getData() {
        return data;
    }

    public void setData(List<CarDetails> data) {
        this.data = data;
    }
}
