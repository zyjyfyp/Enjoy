package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.MonthAmountBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */

public class MonthAmountResponse extends RestApiResponse {

    private List<MonthAmountBean> data;

    public List<MonthAmountBean> getData() {
        return data;
    }

    public void setData(List<MonthAmountBean> data) {
        this.data = data;
    }

}
