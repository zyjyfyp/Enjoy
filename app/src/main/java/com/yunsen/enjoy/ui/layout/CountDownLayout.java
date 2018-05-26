package com.yunsen.enjoy.ui.layout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
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
    private TextView countDownInfo;
    private boolean mHasStart;

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
        countDownInfo = (TextView) rootView.findViewById(R.id.count_down_info_tv);
    }

    private static final String TAG = "CountDownLayout";

    public void setData(long startTime, long endTime, boolean hasStart) {
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mHasStart = hasStart;
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
            upView(true);
        }
    }

    private boolean upView(boolean isFirst) {
        if (!isFirst) {
            mCurrentTime++;
        }
        long time = mStartTime - mCurrentTime;
//        Logger.d( "upView333: mMessageTag=" + mMessageTag + " time = " + time);
        if (time < 0) {
            //已经开始抢了
            time = mEndTime - mCurrentTime;
            hasStart++;
            countDownInfo.setText("距结束");
            if (!mHasStart) {
                //切换到开始
                EventBus.getDefault().post(new UpHomeUiEvent(EventConstants.HOME_UI_UP, mCurrentTime));
            }
            if (time < 0) {
                //结束
                EventBus.getDefault().post(new UpHomeUiEvent(EventConstants.HOME_UI_UP, mCurrentTime));
                return false;
            }
        } else {
            countDownInfo.setText("距开始");
        }
        long second = time % 60;
        long minute = time / 60 % 60;
        long hour = time / 3600 % 24;
        long day = time / 24 / 3600 % 99;
        secondTv.setText(String.format("%02d", second));
        minuteTv.setText(String.format("%02d", minute));
        hourTv.setText(String.format("%02d", hour));
        dayTv.setText(String.format("%02d", day));
        return true;
    }

    /**
     * 停止倒计时
     */
    public void stopMessage() {
        Log.e(TAG, "stopMessage1: 停止" + mMessageTag);
        if (sHandler.hasMessages(mMessageTag)) {
            sHandler.removeMessages(mMessageTag);
            Logger.d("stopMessage11: 停止倒计时");
        }
    }


    /**
     * todo message 没有销毁
     */
    private static class MyHandler extends Handler {
        WeakReference<CountDownLayout> weakReference;

        public MyHandler(CountDownLayout layout) {
            weakReference = new WeakReference<CountDownLayout>(layout);
        }

        @Override
        public void handleMessage(Message msg) {
            CountDownLayout layout = weakReference.get();
            if (layout != null) {
                if (layout.upView(false)) {
                    layout.sHandler.sendEmptyMessageDelayed(layout.mMessageTag, 1000);
                } else {
                    EventBus.getDefault().post(new UpHomeUiEvent(EventConstants.HOME_UI_UP, layout.mCurrentTime));
                }
            }
        }
    }


}
