package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.RestApiResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */

public class NoticeResponse extends RestApiResponse {

    private List<NoticeModel> data;

    public List<NoticeModel> getData() {
        return data;
    }

    public void setData(List<NoticeModel> data) {
        this.data = data;
    }

}
