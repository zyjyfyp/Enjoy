package com.yunsen.enjoy.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;


import com.yunsen.enjoy.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 文字上下滚动
 */

public class ADTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {


    private int index = -1;
    private Context context;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    index = next(); //取得下标值
                    updateText();  //更新TextSwitcherd显示内容;
                    sendEmptyMessageDelayed(1, mTime);
                    break;
                case 2:
                    index = next(); //取得下标值
                    updateText();  //更新TextSwitcherd显示内容;
                    sendEmptyMessageDelayed(2, mTime);
                    break;

            }
        }

        ;
    };

    private List<String> resources;
    private long mTime;

    public ADTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ADTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        this.setFactory(this);
        this.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_from_bottom));
        this.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_from_top));
    }

    public void setResources(List<String> res) {
        this.resources = res;
    }

    public void setTextStillTime(long time, int eventId) {
        this.mTime = time;
        mHandler.sendEmptyMessageDelayed(eventId, 1);
    }


    private int next() {
        int flag = index + 1;
        if (flag > resources.size() - 1) {
            flag = flag - resources.size();
        }
        return flag;
    }

    private void updateText() {
        this.setText(resources.get(index));
    }

    @Override
    public View makeView() {
        TextView tv = new TextView(context);
        tv.setSingleLine();
        tv.setEllipsize(TextUtils.TruncateAt.END);
        FrameLayout.LayoutParams lp = new
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        tv.setLayoutParams(lp);
        return tv;
    }

    public void onStartAuto(int eventId) {
        if (mHandler != null && !mHandler.hasMessages(eventId)) {
            mHandler.sendEmptyMessageDelayed(eventId, mTime);
        }
    }

    public void onStopAuto(int eventId) {
        if (mHandler != null && mHandler.hasMessages(eventId)) {
            mHandler.removeMessages(eventId);
        }
    }

}
