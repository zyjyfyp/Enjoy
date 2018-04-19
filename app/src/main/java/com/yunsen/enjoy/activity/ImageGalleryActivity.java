package com.yunsen.enjoy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.yunsen.enjoy.R;
import com.yunsen.enjoy.ui.photoview.PhotoViewAdapter;
import com.yunsen.enjoy.ui.swipebacklayout.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiansj on 15/8/6.
 */
public class ImageGalleryActivity extends SwipeBackActivity {

    private int position;
    private List<String> imgUrls; //图片列表
    private TextView headTitle;
    private Button headBackBtn;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public int getLayout() {
        return R.layout.activity_touch_gallery;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        imgUrls = intent.getStringArrayListExtra("images");
        if (imgUrls == null) {
            imgUrls = new ArrayList<>();
        }

        headTitle = (TextView) findViewById(R.id.textHeadTitle);
        headTitle.setText("1/" + imgUrls.size());
        headBackBtn = (Button) findViewById(R.id.btnBack);
        headBackBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initGalleryViewPager();
    }

    @Override
    protected void initListener() {
        initViewEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initViewEvent() {
        headBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initGalleryViewPager() {
        PhotoViewAdapter pagerAdapter = new PhotoViewAdapter(this, imgUrls);
        pagerAdapter.setOnItemChangeListener(new PhotoViewAdapter.OnItemChangeListener() {
            int len = imgUrls.size();

            @Override
            public void onItemChange(int currentPosition) {
                headTitle.setText((currentPosition + 1) + "/" + len);
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.viewer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(position);
    }

}
