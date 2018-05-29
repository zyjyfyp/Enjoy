package com.yunsen.enjoy.ui.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

/**
 * Created by cundong on 2015/10/9.
 * <p/>
 * 继承自RecyclerView.OnScrollListener，可以监听到是否滑动到页面最低部
 */
public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements OnListLoadNextPageListener {

    /**
     * 当前RecyclerView类型
     */
    protected LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;


    private boolean mHasFinish = true;

    private LoadMoreLayout mLoadMoreLayout;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    private static final String TAG = "EndlessRecyclerOnScroll";

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (((lastVisibleItemPosition) >= totalItemCount - 1) && visibleItemCount > 0 || visibleItemCount <= totalItemCount - 1) {
            switch (newState) {
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    Log.e(TAG, "onScrollStateChanged: SCROLL_STATE_DRAGGING");
                    if (mHasFinish) {
                        onLoadStart();
                    }
                    break;
                case RecyclerView.SCROLL_STATE_IDLE:
                    if (mHasFinish && (lastVisibleItemPosition) >= totalItemCount - 1 && visibleItemCount > 0) {
                        mHasFinish = false;
                        onLoadNextPage(recyclerView);
                    }
                    Log.e(TAG, "onScrollStateChanged: SCROLL_STATE_IDLE");
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    Log.e(TAG, "onScrollStateChanged: SCROLL_STATE_SETTLING");
                    break;
            }
        }
    }


    /**
     * 取数组中最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    @Override
    public void onLoadStart() {
        if (mLoadMoreLayout != null) {
            mLoadMoreLayout.visibleView();
            mLoadMoreLayout.showloadingStart();
        }
    }

    @Override
    public void onLoadNextPage(final View view) {
        if (mLoadMoreLayout != null) {
            mLoadMoreLayout.showLoading();
        }
    }

    @Override
    public void onRefreshComplete() {
        mHasFinish = true;
        if (mLoadMoreLayout != null) {
            mLoadMoreLayout.goneView();
        }
    }

    @Override
    public void noMore(String text) {
        if (mLoadMoreLayout != null) {
            mLoadMoreLayout.showLoadNoMore(text);
        }
    }


    public void setLoadMoreLayout(LoadMoreLayout loadMoreLayout) {
        this.mLoadMoreLayout = loadMoreLayout;
    }

    public static enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }
}
