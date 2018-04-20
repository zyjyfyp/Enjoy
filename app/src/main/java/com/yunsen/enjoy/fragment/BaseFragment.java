package com.yunsen.enjoy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

/**
 * Created by yunsenA on 2018/4/18.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId = getLayoutId();
        rootView = inflater.inflate(resId, container, false);
        initView();
        initData();
        requestData();
        initListener();
        return rootView;
    }


    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void requestData();

    protected abstract void initListener();

    @Override
    public void onResume() {
        super.onResume();
        Picasso.with(getActivity()).resumeTag(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        Picasso.with(getActivity()).pauseTag(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Picasso.with(getActivity()).cancelTag(getActivity());
    }
}
