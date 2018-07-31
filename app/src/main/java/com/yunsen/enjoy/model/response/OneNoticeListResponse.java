package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.OneNoticeInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12/012.
 */

public class OneNoticeListResponse extends RestApiResponse {
    private List<OneNoticeInfoBean> data;

    public List<OneNoticeInfoBean> getData() {
        return data;
    }

    public void setData(List<OneNoticeInfoBean> data) {
        this.data = data;
    }

}
