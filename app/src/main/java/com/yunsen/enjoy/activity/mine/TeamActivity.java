package com.yunsen.enjoy.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.TeamFragmentAdapter;
import com.yunsen.enjoy.activity.mine.fragment.TeamFragment;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 团队信息
 */
public class TeamActivity extends BaseFragmentActivity {

    @Bind(R.id.team_top_layout)
    LinearLayout topLayout;
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private TeamFragment teamOneFragment;
    //    private TeamFragment teamTwoFragment;
//    private TeamFragment teamThreeFragment;
    private List<Fragment> mFragments;
    private PopupWindow mpopuWindw;
    private PopupWindow popupWindow;
    private String mUserId;
    private String mShareUrl;

    @Override
    public int getLayout() {
        return R.layout.activity_team;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("我的团队");
        actionBarRight.setVisibility(View.VISIBLE);
        actionBarRight.setImageResource(R.drawable.share_app_seletor);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserId = sp.getString(SpConstants.USER_ID, "");
        mShareUrl = URLConstants.REALM_URL + "/appshare/" + mUserId + ".html" +
                "?unionid=" + AccountUtils.getUnionid() + "&shareid=" + mUserId + "&from=android ";
        //http://mobile.szlxkg.com/appshare/8823.html
        initFragment();
        viewpager.setAdapter(new TeamFragmentAdapter(getSupportFragmentManager(), mFragments));
//        tabLayout.setupWithViewPager(viewpager);
    }

    private void initFragment() {
        mFragments = new ArrayList<Fragment>();
        teamOneFragment = new TeamFragment();
        Bundle args1 = new Bundle();
        args1.putInt(Constants.TEAM_TYPE_KEY, Constants.TEAM_TYPE_ONE);
        teamOneFragment.setArguments(args1);

//        teamTwoFragment = new TeamFragment();
//        Bundle args2 = new Bundle();
//        args2.putInt(Constants.TEAM_TYPE_KEY, Constants.TEAM_TYPE_TWO);
//        teamTwoFragment.setArguments(args2);
//
//        teamThreeFragment = new TeamFragment();
//        Bundle args3 = new Bundle();
//        args3.putInt(Constants.TEAM_TYPE_KEY, Constants.TEAM_TYPE_THREE);
//        teamThreeFragment.setArguments(args3);
//
        mFragments.add(teamOneFragment);
//        mFragments.add(teamTwoFragment);
//        mFragments.add(teamThreeFragment);
    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.action_back, R.id.action_bar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.action_bar_right:
                showSharePopupWindow();
                break;
        }
    }

    private void showSharePopupWindow() {
        // 加载popupWindow的布局文件
        LayoutInflater layoutInflater;
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = layoutInflater.inflate(R.layout.share_app_layout, null, false);
        View qrCodeView = contentView.findViewById(R.id.qr_code_layout);
        View shareLayout = contentView.findViewById(R.id.share_layout);
        popupWindow = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.share_layout_bg));
// 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        qrCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.makeTextShort("面对面");
                UIHelper.showExtensionActivity(TeamActivity.this);
                popupWindow.dismiss();
            }
        });
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showShareAppInfoActivity(TeamActivity.this, "");
                popupWindow.dismiss();
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(actionBarRight, -10, 0);
    }

}
