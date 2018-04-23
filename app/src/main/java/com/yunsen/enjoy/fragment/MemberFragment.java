package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.os.Bundle;


import com.yunsen.enjoy.R;

public class MemberFragment extends BaseFragment {

    private Activity context;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_member;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {

    }


//    void initView() {
//        scrollView = (PullToZoomScrollViewEx) root.findViewById(R.id.scrollView);
//        View headView = LayoutInflater.from(context).inflate(R.layout.member_head_view, null, false);
//        View zoomView = LayoutInflater.from(context).inflate(R.layout.member_zoom_view, null, false);
//        View contentView = LayoutInflater.from(context).inflate(R.layout.member_content_view, null, false);
//        scrollView.setHeaderView(headView);
//        scrollView.setZoomView(zoomView);
//        scrollView.setScrollContentView(contentView);
//
//        headView.findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UIHelper.showLogin(getActivity());
//            }
//        });
//
//        headView.findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UIHelper.showLogin(getActivity());
//            }
//        });
//
//
//        scrollView.getPullRootView().findViewById(R.id.textBalance).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        scrollView.getPullRootView().findViewById(R.id.textRecord).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        scrollView.getPullRootView().findViewById(R.id.textAttention).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        scrollView.getPullRootView().findViewById(R.id.textHelp).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        scrollView.getPullRootView().findViewById(R.id.textCalculator).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//
//        scrollView.getPullRootView().findViewById(R.id.textSetting).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//
////        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
////        context.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
////        int mScreenHeight = localDisplayMetrics.heightPixels;
////        int mScreenWidth = localDisplayMetrics.widthPixels;
////        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
////        scrollView.setHeaderLayoutParams(localObject);
//    }
}