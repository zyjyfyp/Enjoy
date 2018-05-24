package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.ClassifyBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24.
 */

public class ClassifyResponse extends RestApiResponse {

    private List<ClassifyBean> data;

    public List<ClassifyBean> getData() {
        return data;
    }

    public void setData(List<ClassifyBean> data) {
        this.data = data;
    }


}
