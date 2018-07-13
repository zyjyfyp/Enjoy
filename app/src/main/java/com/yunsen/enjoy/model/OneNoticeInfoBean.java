package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

/**
 * Created by Administrator on 2018/7/12/012.
 */

public class OneNoticeInfoBean {
    /**
     * id : 2396
     * template_id : 8
     * datatype_id : 1
     * company_id : null
     * post_user_id : 1
     * post_user_name : administrator
     * accept_user_id : 60627
     * accept_user_name : 17688929088
     * is_read : 0
     * is_system : null
     * title : 系统更新
     * img_url :
     * content : 当前最新系统版本更新为2.0.0.2，请您及时更新
     * post_time : 2018-06-13 15:11:33
     * read_time : null
     * update_time : 2018-06-13 15:11:33
     */

    private int id;
    private int template_id;
    private int datatype_id;
    private Object company_id;
    private int post_user_id;
    private String post_user_name;
    private int accept_user_id;
    private String accept_user_name;
    private int is_read;
    private Object is_system;
    private String title;
    private String img_url;
    private String content;
    private String post_time;
    private Object read_time;
    private String update_time;
    private int messageSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }

    public int getDatatype_id() {
        return datatype_id;
    }

    public void setDatatype_id(int datatype_id) {
        this.datatype_id = datatype_id;
    }

    public Object getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Object company_id) {
        this.company_id = company_id;
    }

    public int getPost_user_id() {
        return post_user_id;
    }

    public void setPost_user_id(int post_user_id) {
        this.post_user_id = post_user_id;
    }

    public String getPost_user_name() {
        return post_user_name;
    }

    public void setPost_user_name(String post_user_name) {
        this.post_user_name = post_user_name;
    }

    public int getAccept_user_id() {
        return accept_user_id;
    }

    public void setAccept_user_id(int accept_user_id) {
        this.accept_user_id = accept_user_id;
    }

    public String getAccept_user_name() {
        return accept_user_name;
    }

    public void setAccept_user_name(String accept_user_name) {
        this.accept_user_name = accept_user_name;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public Object getIs_system() {
        return is_system;
    }

    public void setIs_system(Object is_system) {
        this.is_system = is_system;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        if (img_url != null && img_url.startsWith("http")) {
            return img_url;
        } else {
            return URLConstants.REALM_URL + img_url;
        }
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public Object getRead_time() {
        return read_time;
    }

    public void setRead_time(Object read_time) {
        this.read_time = read_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }
}
