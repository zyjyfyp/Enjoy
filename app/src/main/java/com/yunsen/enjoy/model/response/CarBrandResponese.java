package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.CarBrandList;


/**
 * Created by Administrator on 2018/4/28.
 */

public class CarBrandResponese extends RestApiResponse {

    /**
     * data : {"list":[],"listA":[{"flag":"A","id":664,"user_id":1,"user_name":"administrator","channel_id":7,"title":"奥迪","call_index":"2001704101707050268","parent_id":0,"class_list":",664,","class_layer":1,"sort_id":99,"link_url":"","img_url":"/upload/201804/10/201804101727168120.png","content":"","seo_title":"","seo_keywords":"","seo_description":"","update_time":"2018-04-10 17:27:18"}],"listB":[{"flag":"B","id":666,"user_id":1,"user_name":"administrator","channel_id":7,"title":"奔驰","call_index":"9567704101718240476","parent_id":0,"class_list":",666,","class_layer":1,"sort_id":99,"link_url":"","img_url":"/upload/201804/10/201804101720016594.jpg","content":"","seo_title":"","seo_keywords":"","seo_description":"","update_time":"2018-04-10 17:20:03"},{"flag":"","id":665,"user_id":1,"user_name":"administrator","channel_id":7,"title":"宝马","call_index":"4611404101717070196","parent_id":0,"class_list":",665,","class_layer":1,"sort_id":99,"link_url":"","img_url":"/upload/201804/10/201804101723202008.png","content":"","seo_title":"宝马","seo_keywords":"","seo_description":"","update_time":"2018-04-10 17:23:21"}],"listC":[],"listD":[{"flag":"D","id":654,"user_id":118,"user_name":"姚小娟","channel_id":7,"title":"大众","call_index":"9843407081424210573","parent_id":0,"class_list":",654,","class_layer":1,"sort_id":0,"link_url":"","img_url":"","content":"","seo_title":"易孝宝","seo_keywords":"","seo_description":"","update_time":"2018-03-06 10:36:36"}],"listE":[],"listF":[],"listG":[],"listH":[],"listI":[],"listJ":[],"listK":[],"listL":[],"listM":[],"listN":[],"listO":[],"listP":[],"listQ":[],"listR":[],"listS":[],"listT":[],"listW":[],"listV":[],"listU":[],"listX":[],"listY":[],"listZ":[]}
     */

    private CarBrandList data;

    public CarBrandList getData() {
        return data;
    }

    public void setData(CarBrandList data) {
        this.data = data;
    }


}
