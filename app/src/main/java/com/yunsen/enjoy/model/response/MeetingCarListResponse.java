package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.MeetingCarData;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class MeetingCarListResponse extends RestApiResponse {

    private List<MeetingCarData> data;

    public List<MeetingCarData> getData() {
        return data;
    }

    public void setData(List<MeetingCarData> data) {
        this.data = data;
    }


}
