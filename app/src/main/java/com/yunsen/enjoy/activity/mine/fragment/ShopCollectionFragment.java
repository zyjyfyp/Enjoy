package com.yunsen.enjoy.activity.mine.fragment;

import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.fragment.buy.FilterRecAdapter;
import com.yunsen.enjoy.fragment.home.StoreRecyclerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/5/15.
 */

public class ShopCollectionFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener {
    private SharedPreferences spPreferences;
    private RecyclerView recyclerView;
    private TextView noCollectTv;
    private ArrayList<SProviderModel> mDatas;
    private StoreRecyclerAdapter mAdapter;
    private String mUserId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_collection;
    }

    @Override
    protected void initView() {
        spPreferences = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.collect_recycler);
        noCollectTv = (TextView) rootView.findViewById(R.id.no_collect_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatas = new ArrayList<>();
        mAdapter = new StoreRecyclerAdapter(getActivity(), R.layout.shop_item, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        SharedPreferences sp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserId = sp.getString(SpConstants.USER_ID, "");
    }

    @Override
    protected void requestData() {
        HttpProxy.getShopCollectList(mUserId, "1", new HttpCallBack<List<SProviderModel>>() {
            @Override
            public void onSuccess(List<SProviderModel> responseData) {
                mAdapter.upDatas(responseData);
                if (responseData == null) {
                    noCollectTv.setVisibility(View.VISIBLE);
                } else {
                    noCollectTv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                noCollectTv.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > position) {
            SProviderModel model = mDatas.get(position);
            int id = model.getUser_id();
            UIHelper.showServiceShopInfoActivity(getActivity(), String.valueOf(id));
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        ToastUtils.makeTextShort("长按删除功能暂无");
        return true;
    }
}
