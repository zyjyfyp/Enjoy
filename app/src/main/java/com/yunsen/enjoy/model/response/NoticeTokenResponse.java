package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.NoticeTokeBean;

/**
 * Created by Administrator on 2018/6/14.
 */

public class NoticeTokenResponse extends RestApiResponse {

    /**
     * data : {"token_code":"success","device_token":"8986833B4CF3F6FF323E20B9BF34C519B46119FC9DEC15F4"}
     */

    private NoticeTokeBean data;

    public NoticeTokeBean getData() {
        return data;
    }

    public void setData(NoticeTokeBean data) {
        this.data = data;
    }

}
