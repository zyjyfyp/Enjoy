package com.yunsen.enjoy.activity.mine.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/10.
 */

public class TeamFragment extends BaseFragment {

    @Bind(R.id.number_tv)
    TextView numberTv;
    @Bind(R.id.order_tv)
    TextView orderTv;
    @Bind(R.id.money_tv)
    TextView moneyTv;
    @Bind(R.id.no_data_tv)
    TextView noDataTv;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_team;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
