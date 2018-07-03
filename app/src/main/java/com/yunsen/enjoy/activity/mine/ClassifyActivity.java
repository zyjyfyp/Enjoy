package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.fragment.ClassifyFragment;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.utils.DeviceUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/2.
 */

public class ClassifyActivity extends BaseFragmentActivity {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @Bind(R.id.fragment_container3)
    FrameLayout fragmentContainer3;
    @Bind(R.id.fragment_container2)
    FrameLayout fragmentContainer2;


    private ClassifyFragment fragmentOne;
    private ClassifyFragment fragmentTwo;
    private ClassifyFragment fragmentThree;
    private int mScreenWidth;
    private String mData1;
    private String mData2;
    private String mData3;

    @Override
    public int getLayout() {
        return R.layout.activity_classify;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("类目");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentOne = new ClassifyFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.FRAGMENT_TYPE_KEY, 1);
        fragmentOne.setArguments(args);
        transaction.add(R.id.fragment_container, fragmentOne);
        transaction.commit();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mScreenWidth = DeviceUtil.getScreenWidth();
    }

    @Override
    protected void initListener() {

    }


    public void showFragmentTwo(String data) {
//        fragmentContainer.getLayoutParams().width = mScreenWidth / 3;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentTwo == null) {
            fragmentTwo = new ClassifyFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.FRAGMENT_TYPE_KEY, 2);
            fragmentTwo.setArguments(args);
            transaction.add(R.id.fragment_container2, fragmentTwo);
        } else {
            transaction.show(fragmentTwo);
        }
        if (fragmentThree != null && fragmentThree.isVisible()) {
            transaction.hide(fragmentThree);
        }
        transaction.commit();
        mData1 = data;
    }

    public void showFragmentThree(String data) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentThree == null) {
            fragmentThree = new ClassifyFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.FRAGMENT_TYPE_KEY, 3);
            fragmentThree.setArguments(args);
            transaction.add(R.id.fragment_container3, fragmentThree);
        } else {
            transaction.show(fragmentThree);
        }
        transaction.commit();
        mData2 = data;
    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    public void submitClassifyData(String data) {
        if (fragmentOne != null && fragmentTwo != null & fragmentThree != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fragmentOne);
            transaction.remove(fragmentTwo);
            transaction.remove(fragmentThree);
            transaction.commit();
        }
        mData3 = data;
        String datas = mData1 + mData2 + mData3;
        Intent intent = new Intent();
        intent.putExtra(Constants.CLASSIFY_DATA, datas);
        setResult(RESULT_OK, intent);
        finish();
    }
}
