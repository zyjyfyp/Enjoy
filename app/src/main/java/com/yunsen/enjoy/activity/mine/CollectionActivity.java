package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.CollectFragmentAdapter;
import com.yunsen.enjoy.activity.mine.fragment.MyCollectionFragment;
import com.yunsen.enjoy.activity.mine.fragment.ShopCollectionFragment;

import java.util.ArrayList;


/**
 * 收藏页面
 */
public class CollectionActivity extends BaseFragmentActivity implements OnClickListener {
    private ImageView mBackImg;
    private MyCollectionFragment mMyCollection;
    private ShopCollectionFragment mShopCollection;
    private TabLayout tab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.collect_ware;
    }

    @Override
    protected void initView() {
        mBackImg = (ImageView) findViewById(R.id.iv_fanhui);
        tab = (TabLayout) findViewById(R.id.collect_tab);
        viewPager = (ViewPager) findViewById(R.id.collect_pager);
        tab.setupWithViewPager(viewPager);
        ArrayList<Fragment> fragments = new ArrayList<>();

        mMyCollection = new MyCollectionFragment();
        mShopCollection = new ShopCollectionFragment();
        fragments.add(mMyCollection);
        fragments.add(mShopCollection);
        viewPager.setAdapter(new CollectFragmentAdapter(getSupportFragmentManager(), fragments));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initListener() {
        mBackImg.setOnClickListener(this);
    }


    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_fanhui) {
            finish();
        }

    }
}
