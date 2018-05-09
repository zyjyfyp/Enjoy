package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.ServiceProject;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class ServiceProjectListResponse extends RestApiResponse{

    /**
     * id : 4
     * title : 全新
     * parent_id : 0
     * service_list : ,4,
     * service_layer : 1
     * sort_id : 99
     * is_hot : 0
     * is_red : 0
     * status : 0
     * icon_url :
     * img_url :
     * content :
     * seo_title :
     * seo_keywords :
     * seo_description :
     * update_time : 2018-05-09 16:06:23
     */

    private List<ServiceProject> data;

    public List<ServiceProject> getData() {
        return data;
    }

    public void setData(List<ServiceProject> data) {
        this.data = data;
    }


}
