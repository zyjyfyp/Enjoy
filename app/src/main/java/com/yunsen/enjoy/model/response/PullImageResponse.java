package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.PullImageResult;

/**
 * Created by Administrator on 2018/5/10.
 */

public class PullImageResponse extends RestApiResponse{

    /**
     * img_url : /upload/201805/10/201805100058112141.jpg
     */

    private PullImageResult data;

    public PullImageResult getData() {
        return data;
    }

    public void setData(PullImageResult data) {
        this.data = data;
    }


}
