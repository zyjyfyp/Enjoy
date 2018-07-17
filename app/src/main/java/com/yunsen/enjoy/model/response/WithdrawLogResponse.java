package com.yunsen.enjoy.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.WithdrawLogData;

import java.util.List;

/**
 * Created by Administrator on 2018/7/17/017.
 */

public class WithdrawLogResponse extends RestApiResponse {

    private List<WithdrawLogData> data;

    public List<WithdrawLogData> getData() {
        return data;
    }

    public void setData(List<WithdrawLogData> data) {
        this.data = data;
    }

}
