package com.yunsen.enjoy.fragment.notice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.fragment.NoticeFragment;
import com.yunsen.enjoy.fragment.notice.adapter.NoticeListAdapter;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.OneNoticeInfoBean;
import com.yunsen.enjoy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/17.
 */

public class DefaultNoticeFragment extends BaseFragment {
    @Bind(R.id.default_notice_recycler)
    RecyclerView defaultNoticeRecycler;
    private int mFragmentType;
    private int mPageIndex = 1;
    private ArrayList<OneNoticeInfoBean> mDatas;
    private NoticeListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_default_notice;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        defaultNoticeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatas = new ArrayList<>();
        mAdapter = new NoticeListAdapter(getActivity(), mDatas);
        defaultNoticeRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mFragmentType = bundle.getInt(Constants.FRAGMENT_TYPE_KEY, 1);
    }

    @Override
    protected void requestData() {
        HttpProxy.getOneTypeNoticeList(String.valueOf(mFragmentType), String.valueOf(mPageIndex), new HttpCallBack<List<OneNoticeInfoBean>>() {
            @Override
            public void onSuccess(List<OneNoticeInfoBean> responseData) {
                if (responseData != null && responseData.size() > 0) {
                    OneNoticeInfoBean infoBean = responseData.get(0);
                    Fragment parentFragment = getParentFragment();
                    if (parentFragment instanceof NoticeFragment) {
                        ((NoticeFragment) parentFragment).setMessageNumber(mFragmentType, infoBean.getMessageSize());
                    }


                } else {
                    Fragment parentFragment = getParentFragment();
                    if (parentFragment instanceof NoticeFragment) {
                        ((NoticeFragment) parentFragment).setMessageNumber(mFragmentType, 0);
                    }
                }
                mAdapter.addDatas(responseData);
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    ToastUtils.makeTextShort(e.getMessage());
                }
                Fragment parentFragment = getParentFragment();
                if (parentFragment instanceof NoticeFragment) {
                    ((NoticeFragment) parentFragment).setMessageNumber(mFragmentType, 0);
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
