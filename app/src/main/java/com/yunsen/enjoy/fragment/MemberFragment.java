package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.CollectionActivity;
import com.yunsen.enjoy.activity.user.TishiWxBangDingActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.ui.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberFragment extends BaseFragment {

    public static String yth;
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
            } else {
                Intent intent = new Intent(getActivity(),
                        TishiWxBangDingActivity.class);
                startActivity(intent);
            }
        } else {
            if (TextUtils.isEmpty(user_name_phone)) {
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

    @OnClick(R.id.team_layout) //团队信息
    public void onTeamLayoutClicked() {
        UIHelper.showTeamActivity(getActivity());
    }

    @OnClick(R.id.finance_layout)
    public void onFinanceLayoutClicked() {
        UIHelper.showAssetsActivity(getActivity());
    }

    @OnClick(R.id.recharge_layout)
    public void onRechargeLayoutClicked() {
        UIHelper.showRechargeActivity(getActivity());
    }

    @OnClick(R.id.apply_service_layout) //申请服务商（我是服务商）
    public void onApplyServiceLayoutClicked() {
        UIHelper.showApplyServiceActivity(getActivity());
    }

    @OnClick(R.id.appointment_layout) //预约管理
    public void onAppointmentLayoutClicked() {
        UIHelper.showAppointmentActivity(getActivity());
    }

    @OnClick(R.id.help_layout)
    public void onHelpLayoutClicked() {
        UIHelper.showHelpActivity(getActivity());
    }

    @OnClick(R.id.logout_layout)
    public void onLogoutLayoutClicked() {
        Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}