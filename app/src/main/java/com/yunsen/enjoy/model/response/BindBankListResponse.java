package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.BindCardBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */

public class BindBankListResponse extends RestApiResponse {

    private List<BindCardBean> data;

    public List<BindCardBean> getData() {
        return data;
    }

    public void setData(List<BindCardBean> data) {
        this.data = data;
    }
}
