package com.yunsen.enjoy.model;

import com.yunsen.enjoy.http.URLConstants;

/**
 * Created by Administrator on 2018/8/28/028.
 */

public class CommentBean {
    /**
     * id : 0
     * company_id : 0
     * channel_id : 0
     * article_id : 0
     * topic_id : 0
     * parent_id : 0
     * user_id : 13871
     * user_name : 13249089599
     * avatar : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLVoZTJqoxlBqickLJTe5bvJI9obVmo6UsP1z0lZ3Uqkvuk4EPy8lZpibI3zr5brzXjWQdaCicH5g6Xg/132
     * user_ip : null
     * content : %u6CA1%u4E8B%uFF0C%u6D4B%u8BD5
     * is_lock : 0
     * add_time : 2018-08-28 17:21:38
     * update_time : 2018-08-28 17:25:29
     * is_reply : 0
     * reply_content :
     * reply_time : 2018-08-28 17:25:29
     * score : 0
     */

    private int id;
    private int company_id;
    private int channel_id;
    private int article_id;
    private int topic_id;
    private int parent_id;
    private int user_id;
    private String user_name;
    private String avatar;
    private Object user_ip;
    private String content;
    private int is_lock;
    private String add_time;
    private String update_time;
    private int is_reply;
    private String reply_content;
    private String reply_time;
    private int score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar() {
        if (avatar != null && avatar.startsWith("http")) {
            return avatar;
        } else {
            return URLConstants.REALM_URL + avatar;
        }
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Object getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(Object user_ip) {
        this.user_ip = user_ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIs_lock() {
        return is_lock;
    }

    public void setIs_lock(int is_lock) {
        this.is_lock = is_lock;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getIs_reply() {
        return is_reply;
    }

    public void setIs_reply(int is_reply) {
        this.is_reply = is_reply;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
