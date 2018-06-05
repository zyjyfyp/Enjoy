package com.yunsen.enjoy.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.widget.ViewPagerAdapter;

import java.util.ArrayList;


/**
 * @author weiping
 * @version V1.0
 * @Package com.zhangshang
 * @Description: 引导页
 * @date 2014-9-25 下午03:26:37
 */
public class Guide2Activity extends AppCompatActivity implements OnPageChangeListener {
    LayoutInflater layoutInflater;
    LinearLayout ll_yindaoye1;
    ImageView iv_yindaoye1;
    private Button bv_experience;
    // 定义ViewPager对象
    private ViewPager viewPager;
    // 定义ViewPager适配器
    private ViewPagerAdapter vpAdapter;
    // 定义一个ArrayList来存放View
    private ArrayList<View> views;

    // 引导图片资源
    private static final int[] pics = {R.drawable.zams_ydy_1, R.drawable.zams_ydy_2};

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        ll_yindaoye1 = (LinearLayout) findViewById(R.id.ll_yindaoye1);
        iv_yindaoye1 = (ImageView) findViewById(R.id.iv_yindaoye1);
        bv_experience = (Button) findViewById(R.id.bv_experience);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        views = new ArrayList<View>();


    }

    /**
     * 初始化数据
     */
    private void initData() {
        layoutInflater = LayoutInflater.from(this);
        // 初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            View view = layoutInflater.inflate(R.layout.item_guide_image, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_image);
            iv.setImageResource(pics[i]);
            views.add(view);
        }
        vpAdapter = new ViewPagerAdapter(views);
        // 设置数据
        viewPager.setAdapter(vpAdapter);
        // 设置监听
        viewPager.setOnPageChangeListener(this);

        bv_experience.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getClasss();
            }
        });

    }

    public void getClasss() {
        Intent intent = new Intent(Guide2Activity.this, MainActivity.class);
        startActivity(intent);
        SharedPreferences preferences = getSharedPreferences(SpConstants.SP_GUIDE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String appVersionName = DeviceUtil.getAppVersionName(this);
        editor.putString(SpConstants.APP_VERSION_NAME, appVersionName);
        editor.commit();
        finish();
    }

    /**
     * 当滑动状态改变时调用
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    /**
     * 当当前页面被滑动时调用
     */

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    /**
     * 当新的页面被选中时调用
     */
    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        if (0 == position) {
            ll_yindaoye1.setVisibility(View.VISIBLE);
            bv_experience.setVisibility(View.GONE);
        } else if (1 == position) {
            ll_yindaoye1.setVisibility(View.GONE);
            bv_experience.setVisibility(View.VISIBLE);
        }
    }


}
