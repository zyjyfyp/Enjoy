package com.yunsen.enjoy.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.MyApplyCarBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12/012.
 */

public class MyApplyCarListResponse extends RestApiResponse {

    private List<MyApplyCarBean> data;

    public List<MyApplyCarBean> getData() {
        return data;
    }

    public void setData(List<MyApplyCarBean> data) {
        this.data = data;
    }

}
