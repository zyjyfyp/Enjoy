package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.tools.utils.UIHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.CollectionActivity;
import com.yunsen.enjoy.activity.user.TishiWxBangDingActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.ui.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberFragment extends BaseFragment {

    @Bind(R.id.login_icon)
    ImageView loginIcon;
    @Bind(R.id.login_tv)
    TextView loginTv;
    @Bind(R.id.order_img_1)
    ImageView orderImg1;
    @Bind(R.id.order_title_1)
    TextView orderTitle1;
    @Bind(R.id.order_number_1)
    TextView orderNumber1;
    @Bind(R.id.order_img_2)
    ImageView orderImg2;
    @Bind(R.id.order_title_2)
    TextView orderTitle2;
    @Bind(R.id.order_number_2)
    TextView orderNumber2;
    @Bind(R.id.order_img_3)
    ImageView orderImg3;
    @Bind(R.id.order_title_3)
    TextView orderTitle3;
    @Bind(R.id.order_number_3)
    TextView orderNumber3;
    @Bind(R.id.order_img_4)
    ImageView orderImg4;
    @Bind(R.id.order_title_4)
    TextView orderTitle4;
    @Bind(R.id.order_number_4)
    TextView orderNumber4;
    @Bind(R.id.account_manager_layout)
    LinearLayout accountManagerLayout;
    @Bind(R.id.collection_layout)
    LinearLayout collectionLayout;
    @Bind(R.id.team_layout)
    LinearLayout teamLayout;
    @Bind(R.id.finance_layout)
    LinearLayout financeLayout;
    @Bind(R.id.recharge_layout)
    LinearLayout rechargeLayout;
    @Bind(R.id.apply_service_layout)
    LinearLayout applyServiceLayout;
    @Bind(R.id.appointment_layout)
    LinearLayout appointmentLayout;
    @Bind(R.id.help_layout)
    LinearLayout helpLayout;
    @Bind(R.id.logout_layout)
    LinearLayout logoutLayout;
    private Activity context;

    private String nickname;
    private String user_name_phone;
    private SharedPreferences spPreferences;
    private String user_id;
    private String user_name_key;
    private SharedPreferences spPreferences_login;
    private String headimgurl;
    private String unionid;
    private String access_token;
    private String sex;

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
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void initData() {
        spPreferences = getActivity().getSharedPreferences("longuserset",
                Context.MODE_PRIVATE);
        user_name_phone = spPreferences.getString("user", "");
        System.out.println("user_name_phone================="
                + user_name_phone);
        if (!TextUtils.isEmpty(user_name_phone)) {
            user_id = spPreferences.getString("user_id", "");
            user_name_key = user_name_phone;
        }

        spPreferences_login = getActivity().getSharedPreferences(
                "longuserset_login", Context.MODE_PRIVATE);

        nickname = spPreferences_login.getString("nickname", "");
        headimgurl = spPreferences_login.getString("headimgurl", "");
        unionid = spPreferences_login.getString("unionid", "");
        access_token = spPreferences_login.getString("access_token", "");
        sex = spPreferences_login.getString("sex", "");
        String oauth_openid = spPreferences_login.getString("oauth_openid", "");

    }


    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.login_icon)
    public void onLoginIconClicked() {
    }

    @OnClick(R.id.order_img_1)
    public void onOrderImg1Clicked() {
    }

    @OnClick(R.id.order_img_2)
    public void onOrderImg2Clicked() {
    }

    @OnClick(R.id.order_img_3)
    public void onOrderImg3Clicked() {
    }

    @OnClick(R.id.order_img_4)
    public void onOrderImg4Clicked() {
    }

    @OnClick(R.id.account_manager_layout)
    public void onAccountManagerLayoutClicked() {
        UIHelper.showPersonCenterActivity(getActivity());
    }

    @OnClick(R.id.collection_layout) //收藏
    public void onCollectionLayoutClicked() {

        if (!TextUtils.isEmpty(nickname)) {
            if (!TextUtils.isEmpty(user_name_phone)) {
                Intent intent5 = new Intent(getActivity(),
                        CollectionActivity.class);
                startActivity(intent5);
            } else {// TODO: 2018/4/25 zyjy
                Intent intent = new Intent(getActivity(),
                        TishiWxBangDingActivity.class);
                startActivity(intent);
            }
        } else {
            if (TextUtils.isEmpty(user_name_phone)) { // TODO: 2018/4/25 zyjy
                Intent intent9 = new Intent(getActivity(),
                        UserLoginActivity.class);
                startActivity(intent9);
            } else {
                Intent intent9 = new Intent(getActivity(),
                        CollectionActivity.class);
                startActivity(intent9);
            }
        }
    }

    @OnClick(R.id.team_layout)
    public void onTeamLayoutClicked() {
    }

    @OnClick(R.id.finance_layout)
    public void onFinanceLayoutClicked() {
    }

    @OnClick(R.id.recharge_layout)
    public void onRechargeLayoutClicked() {
    }

    @OnClick(R.id.apply_service_layout)
    public void onApplyServiceLayoutClicked() {
    }

    @OnClick(R.id.appointment_layout)
    public void onAppointmentLayoutClicked() {
    }

    @OnClick(R.id.help_layout)
    public void onHelpLayoutClicked() {
    }

    @OnClick(R.id.logout_layout)
    public void onLogoutLayoutClicked() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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