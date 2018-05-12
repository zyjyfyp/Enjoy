package com.yunsen.enjoy.widget.city;

/**
 * Created by Administrator on 2018/5/12.
 */

public class YsCity {

    /**
     * id : 0
     * code : 659000
     * province_code : null
     * name : 省直辖行政单位
     * spell : S
     * lng : null
     * lat : null
     * status : 0
     * sort_id : 0
     * is_default : 0
     * update_time : 2018-05-12 11:00:51
     */

    private String id;
    private String code;
    private String province_code;
    private String name;
    private String spell;
    private Object lng;
    private Object lat;
    private int status;
    private int sort_id;
    private int is_default;
    private String update_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public Object getLng() {
        return lng;
    }

    public void setLng(Object lng) {
        this.lng = lng;
    }

    public Object getLat() {
        return lat;
    }

    public void setLat(Object lat) {
        this.lat = lat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}