package com.yunsen.enjoy.model.event;

/**
 * Created by Administrator on 2018/4/28.
 */

public class ActivityResultEvent {
    private int eventId;
    private int dataId;
    private String name;
    private String fragmentType;

    public ActivityResultEvent(int eventId) {
        this.eventId = eventId;
    }

    public ActivityResultEvent(int eventId, int dataId) {
        this.eventId = eventId;
        this.dataId = dataId;
    }

    public ActivityResultEvent(int eventId, int dataId, String name) {
        this.eventId = eventId;
        this.dataId = dataId;
        this.name = name;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFragmentType() {
        return fragmentType;
    }

    public void setFragmentType(String fragmentType) {
        this.fragmentType = fragmentType;
    }
}
