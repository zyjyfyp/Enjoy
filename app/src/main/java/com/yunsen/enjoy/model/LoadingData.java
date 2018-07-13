package com.yunsen.enjoy.model;

/**
 * Created by Administrator on 2018/7/13/013.
 */

public class LoadingData {
    private int pageIndex;
    private boolean isLoadMore;
    private boolean hasLoaMore;
    private int currentFragment;

    public LoadingData(int currentIndex) {
        this.pageIndex = 1;
        this.isLoadMore = false;
        this.hasLoaMore = true;
        this.currentFragment = currentIndex;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public boolean isHasLoaMore() {
        return hasLoaMore;
    }

    public void setHasLoaMore(boolean hasLoaMore) {
        this.hasLoaMore = hasLoaMore;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
