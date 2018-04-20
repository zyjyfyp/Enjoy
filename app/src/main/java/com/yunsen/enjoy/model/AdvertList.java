package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.RestApiResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */

public class AdvertList extends RestApiResponse {

    /**
     * status : y
     * code : yes
     * info : 根据广告位输出广告
     * data : [{"id":94,"aid":13,"terminal_id":2,"category_id":0,"article_id":0,"title":"乐享新车","start_time":"2016-08-24 21:09:22","end_time":"2019-08-24 21:09:22","ad_url":"/upload/201608/24/201608242109447736.jpg","link_url":"/pages/goods/goods_show?id=14837","remark":"","sort_id":1,"status":0,"user_id":0,"user_name":null,"company_id":0,"company_name":"自营","add_time":"2016-08-24 21:09:46","update_time":"2017-03-28 09:20:36"},{"id":93,"aid":13,"terminal_id":2,"category_id":0,"article_id":0,"title":"乐享新车","start_time":"2016-08-24 21:09:22","end_time":"2019-08-24 21:09:22","ad_url":"/upload/201608/24/201608242109447736.jpg","link_url":"/pages/goods/goods_show?id=14837","remark":"","sort_id":1,"status":0,"user_id":0,"user_name":null,"company_id":0,"company_name":"自营","add_time":"2016-08-24 21:09:46","update_time":"2017-03-28 09:20:36"},{"id":92,"aid":13,"terminal_id":2,"category_id":0,"article_id":0,"title":"乐享新车","start_time":"2016-08-24 21:09:22","end_time":"2019-08-24 21:09:22","ad_url":"/upload/201608/24/201608242109447736.jpg","link_url":"/pages/goods/goods_show?id=14837","remark":"","sort_id":1,"status":0,"user_id":0,"user_name":null,"company_id":0,"company_name":"自营","add_time":"2016-08-24 21:09:46","update_time":"2017-03-28 09:20:36"},{"id":91,"aid":13,"terminal_id":2,"category_id":0,"article_id":0,"title":"乐享新车","start_time":"2016-08-24 21:09:22","end_time":"2019-08-24 21:09:22","ad_url":"/upload/201608/24/201608242109447736.jpg","link_url":"/pages/goods/goods_show?id=14837","remark":"","sort_id":1,"status":0,"user_id":0,"user_name":null,"company_id":0,"company_name":"自营","add_time":"2016-08-24 21:09:46","update_time":"2017-03-28 09:20:36"},{"id":90,"aid":13,"terminal_id":2,"category_id":0,"article_id":0,"title":"乐享新车","start_time":"2016-08-24 21:09:22","end_time":"2019-08-24 21:09:22","ad_url":"/upload/201608/24/201608242109447736.jpg","link_url":"/pages/goods/goods_show?id=14837","remark":"","sort_id":1,"status":0,"user_id":0,"user_name":null,"company_id":0,"company_name":"自营","add_time":"2016-08-24 21:09:46","update_time":"2017-03-28 09:20:36"},{"id":27,"aid":13,"terminal_id":2,"category_id":0,"article_id":0,"title":"乐享新车","start_time":"2016-08-24 21:09:22","end_time":"2019-08-24 21:09:22","ad_url":"/upload/201608/24/201608242109447736.jpg","link_url":"/pages/goods/goods_show?id=14837","remark":"","sort_id":1,"status":0,"user_id":0,"user_name":null,"company_id":0,"company_name":"自营","add_time":"2016-08-24 21:09:46","update_time":"2017-03-28 09:20:36"}]
     * record : 0
     * redirect :
     * timer : 0
     * callback :
     * datetime : 2018-04-20 09:57:20
     * timestamp : 1524189440
     */
    private List<AdvertModel> data;

    public List<AdvertModel> getData() {
        return data;
    }

    public void setData(List<AdvertModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AdvertList{" +
                "data=" + data +
                '}';
    }

}
