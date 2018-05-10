package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.TeamFragmentAdapter;
import com.yunsen.enjoy.activity.mine.fragment.TeamFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 团队信息
 */
public class TeamActivity extends BaseFragmentActivity {

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
    private TeamFragment teamTwoFragment;
    private TeamFragment teamThreeFragment;
    private List<Fragment> mFragments;

    @Override
    public int getLayout() {
        return R.layout.activity_team;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("团队信息");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initFragment();
        viewpager.setAdapter(new TeamFragmentAdapter(getSupportFragmentManager(), mFragments));
        tabLayout.setupWithViewPager(viewpager);
    }

    private void initFragment() {
        mFragments = new ArrayList<Fragment>();
        teamOneFragment = new TeamFragment();
        teamTwoFragment = new TeamFragment();
        teamThreeFragment = new TeamFragment();
        mFragments.add(teamOneFragment);
        mFragments.add(teamTwoFragment);
        mFragments.add(teamThreeFragment);
    }

    @Override
    protected void initListener() {

    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
