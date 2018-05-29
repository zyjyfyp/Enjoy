package com.yunsen.enjoy.model.event;

/**
 * Created by Administrator on 2018/4/24.
 */

public class UpUiEvent {
    private int eventId;
    private boolean hasMore = false;
    private boolean isMore = true;


    public UpUiEvent(int eventId) {
        this.eventId = eventId;
    }

    public UpUiEvent(int eventId, boolean hasMore) {
        this.eventId = eventId;
        this.hasMore = hasMore;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
