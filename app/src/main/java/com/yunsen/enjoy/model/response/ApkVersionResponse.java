package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.ApkVersionInfo;

/**
 * Created by Administrator on 2018/6/7.
 */

public class ApkVersionResponse extends RestApiResponse {
    /**
     * data : {"id":2,"user_id":0,"user_name":"","channel_id":5,"category_id":16,"is_http":0,"title":"安卓 08-26","file_size":14782033,"file_version":"1.2","file_name":"","is_verify":0,"file_path":"/upload/201608/26/201608262034380018.apk","file_ext":"apk","img_url":"","link_url":"","content":"","add_time":"2016-08-01 23:20:39","update_time":"2016-08-03 10:49:16"}
     */
    private ApkVersionInfo data;

    public ApkVersionInfo getData() {
        return data;
    }

    public void setData(ApkVersionInfo data) {
        this.data = data;
    }
}
