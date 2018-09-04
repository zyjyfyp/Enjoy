package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.CommentBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/28/028.
 */

public class CommentResponse extends RestApiResponse {

    private List<CommentBean> data;

    public CommentResponse() {
    }

    public List<CommentBean> getData() {
        return data;
    }

    public void setData(List<CommentBean> data) {
        this.data = data;
    }


}
