package com.yunsen.enjoy.fragment.discover;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.event.DiscoverEvent;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpFilterReqEvent;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.FlowLayout;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/6/19.
 * 推广fragment
 */

public class SpreadFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener {
    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;
    @Bind(R.id.recycler_spread)
    RecyclerView recyclerSpread;
    private String mId;
    private String mParentId;
    private String mChannelId;
    private ArrayList<CarDetails> mDatas;
    private SpreadAdapter mAdapter;
    private int mPosition = 0; //位置
    private int mPageIndex = 1;
    private boolean mIsLoadMore = false;
    private LoadMoreLayout loadMoreLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_spread;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        recyclerSpread.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mDatas = new ArrayList<>();
        mAdapter = new SpreadAdapter(getActivity(), R.layout.spread_item, mDatas);
        HeaderAndFooterRecyclerViewAdapter footerRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerSpread.setAdapter(footerRecyclerViewAdapter);
        loadMoreLayout = new LoadMoreLayout(getActivity());
        RecyclerViewUtils.setFooterView(recyclerSpread, loadMoreLayout);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mId = bundle.getString(Constants.CLASSIFY_ID);
        mParentId = bundle.getString(Constants.CLASSIFY_PARENT_ID);
        mChannelId = bundle.getString(Constants.CLASSIFY_CHANNEL_ID);
        mPosition = bundle.getInt(Constants.FRAGMENT_TYPE_KEY);
    }

    @Override
    protected void requestData() {
        HttpProxy.getSpreadDatas(String.valueOf(mPageIndex), mId, new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                boolean hasMore = mAdapter.addBaseDatas(responseData);
                if (hasMore) {
                    EventBus.getDefault().post(new DiscoverEvent(EventConstants.SHOW_HAS_MORE, mPosition));
                    loadMoreLayout.goneView();
                } else {
                    loadMoreLayout.showLoadNoMore(null);
                    EventBus.getDefault().post(new DiscoverEvent(EventConstants.NO_MORE, mPosition));
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                loadMoreLayout.showLoadNoMore(null);
                EventBus.getDefault().post(new DiscoverEvent(EventConstants.NO_MORE, mPosition));
            }
        });
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (position >= 0 && position < mDatas.size()) {
            CarDetails details = mDatas.get(position);
            UIHelper.showHasTitleWebActivity(getActivity(), details);
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLodeMoreEvent(UpFilterReqEvent event) {
        if (event.getEventId() == EventConstants.SHOW_HAS_MORE && event.getCurrentIndex() == mPosition) {
            mPageIndex++;
            mIsLoadMore = true;
            requestData();
        }
    }
}
