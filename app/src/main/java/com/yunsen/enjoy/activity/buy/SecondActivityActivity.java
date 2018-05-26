package com.yunsen.enjoy.activity.buy;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.fragment.home.FillActivityAdapter;
import com.yunsen.enjoy.http.HttpCallBack2;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpHomeUiEvent;
import com.yunsen.enjoy.ui.layout.SecondActivityLayout;
import com.yunsen.enjoy.ui.recyclerview.EndlessRecyclerOnScrollListener;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/25.
 */

public class SecondActivityActivity extends BaseFragmentActivity {
    private static final String TAG = "SecondActivityActivity";
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.secondLayout)
    SecondActivityLayout secondLayout;
    private int mPageIndex = 1;
    private boolean isLoadMore = false;
    private boolean mHasMore = true;
    private LoadMoreLayout loadMoreLayout;

    private EndlessRecyclerOnScrollListener listener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadStart() {
            super.onLoadStart();
            loadMoreLayout.visibleView();
            loadMoreLayout.showloadingStart();
        }

        @Override
        public void onLoadNextPage(View view) {
            Log.e(TAG, "onLoadNextPage: ");
            loadMoreLayout.showLoading();
            mPageIndex++;
            isLoadMore = true;
            requestData();
        }

        @Override
        public void onRefreshComplete() {
            super.onRefreshComplete();
            loadMoreLayout.goneView();
            Log.e(TAG, "onRefreshComplete: ");
        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_second_activity;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        secondLayout.setTopTitleVisibility(View.GONE);
        actionBarTitle.setText("秒杀活动");
        loadMoreLayout = secondLayout.getLoadMoreLayout();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initListener() {
//        EndlessRecyclerOnScrollListener
        secondLayout.getRecycler().addOnScrollListener(listener);
    }

    @Override
    public void requestData() {
        HttpProxy.getSecondActivityMoreData(mPageIndex, new HttpCallBack2<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData, Object otherData) {
                String data = (String) otherData;
                if (isLoadMore) {
                    secondLayout.upData(responseData, Long.parseLong(data));
                } else {
                    mHasMore = secondLayout.addData(responseData, Long.parseLong(data));
                }
                if (mHasMore) {
                    listener.onRefreshComplete();
                } else {
                    loadMoreLayout.showLoadNoMore();
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                loadMoreLayout.showLoadNoMore();
            }
        });
    }

    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RecyclerView recycler = secondLayout.getRecycler();
        if (recycler != null) {
            recycler.removeOnScrollListener(listener);
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent2(UpHomeUiEvent event) {
        final UpHomeUiEvent fEvent = event;
        if (event.getEventId() == EventConstants.HOME_UI_UP) {
            secondLayout.post(new Runnable() {
                @Override
                public void run() {
                    secondLayout.upTimeUi(fEvent.getCurrentTime());
                }
            });
        }
    }

}
