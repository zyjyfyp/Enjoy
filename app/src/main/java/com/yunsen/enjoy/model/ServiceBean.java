package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/6/1.
 */

public class ServiceBean {
    /**
     * user_id : 6783
     * service_id : 1
     * service_list : ,1,
     * update_time : 2018-05-24 09:17:06
     */

    private int user_id;
    private int service_id;
    private String service_list;
    private String update_time;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getService_list() {
        return service_list;
    }

    public void setService_list(String service_list) {
        this.service_list = service_list;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
