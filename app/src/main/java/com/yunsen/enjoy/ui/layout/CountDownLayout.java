package com.yunsen.enjoy.ui.layout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpHomeUiEvent;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/5/24.
 */

public class CountDownLayout extends LinearLayout {

    private View rootView;
    private Handler sHandler;
    private TextView dayTv;
    private TextView minuteTv;
    private TextView hourTv;
    private TextView secondTv;
    private int mMessageTag;
    private long mCurrentTime;
    private long mEndTime;
    private long mStartTime;

    private int hasStart = 0;

    public CountDownLayout(Context context) {
        super(context);
        initView(context);
    }

    public CountDownLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CountDownLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        sHandler = new CountDownLayout.MyHandler(this);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.count_down_layout, this);
        hourTv = (TextView) rootView.findViewById(R.id.home_activity_hour);
        minuteTv = (TextView) rootView.findViewById(R.id.home_activity_minute);
        secondTv = (TextView) rootView.findViewById(R.id.home_activity_second);
        dayTv = (TextView) rootView.findViewById(R.id.home_activity_day);
    }

    public void setData(long startTime, long endTime) {
        this.mStartTime = startTime;
        this.mEndTime = endTime;
    }

    /**
     * 开始倒计时
     *
     * @param messageTag
     * @param currentTime
     */
    public void startCountDown(int messageTag, long currentTime) {
        this.mMessageTag = messageTag;
        this.mCurrentTime = currentTime;
        if (!sHandler.hasMessages(messageTag)) {
            sHandler.sendEmptyMessageDelayed(messageTag, 1000);
            upView();
        }
    }

    private boolean upView() {
        long time = mStartTime - mCurrentTime;

        if (time < 0) {
            //已经开始抢了
            time = mEndTime - mCurrentTime;
            hasStart++;
            if (hasStart == 1) {
                EventBus.getDefault().post(new UpHomeUiEvent(EventConstants.HOME_UI_UP, mCurrentTime));
            }
            if (time < 0) {
                return false;
            }
        }
        long second = time % 60;
        long minute = time / 60 % 60;
        long hour = time / 3600 % 60;
        long day = time / 24 / 3600 % 60;
        secondTv.setText(String.format("%02d", second));
        minuteTv.setText(String.format("%02d", minute));
        hourTv.setText(String.format("%02d", hour));
        dayTv.setText(String.format("%02d", day));
        mCurrentTime++;
        return true;
    }

    /**
     * 停止倒计时
     */
    public void stopMessage() {
        if (sHandler.hasMessages(mMessageTag)) {
            sHandler.removeMessages(mMessageTag);
            Logger.d("stopMessage: 停止倒计时");
        }
    }


    /**
     * todo message 没有销毁
     */
    private class MyHandler extends Handler {
        WeakReference<CountDownLayout> weakReference;

        public MyHandler(CountDownLayout layout) {
            weakReference = new WeakReference<CountDownLayout>(layout);
        }

        @Override
        public void handleMessage(Message msg) {
            CountDownLayout layout = weakReference.get();
            if (layout != null) {
                if (layout.upView()) {
                    sHandler.sendEmptyMessageDelayed(layout.mMessageTag, 1000);
                } else {
                    EventBus.getDefault().post(new UpHomeUiEvent(EventConstants.HOME_UI_UP, layout.mCurrentTime));
                }
            }
        }
    }


}
