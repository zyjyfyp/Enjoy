package com.yunsen.enjoy.model.event;

/**
 * Created by Administrator on 2018/5/11.
 */

public class UpCityEvent {
    private int eventId;
    private String city;
    private String cityCode;

    public UpCityEvent(int eventId, String city) {
        this.eventId = eventId;
        this.city = city;
    }

    public UpCityEvent(int eventId, String city, String cityCode) {
        this.eventId = eventId;
        this.city = city;
        this.cityCode = cityCode;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
