package com.yunsen.enjoy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.utils.DeviceUtil;

/**
 * Created by Administrator on 2018/7/4.
 */

public class ZyRingView extends View {
    private float mAllProfit = 0;
    private float mOneProfit = 0;
    private float mTwoProfit = 0;
    private float mThreeProfit = 0;

    private int mRingWidth;
    private Context mContext;

    public ZyRingView(Context context) {
        super(context);
        initView(context);
    }

    public ZyRingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ZyRingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mRingWidth = DeviceUtil.dp2px(context, 20);
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        int min = Math.min(width, height) - mRingWidth;

        CircleRing cRing = new CircleRing(width, height, min, mRingWidth, Color.GRAY);
        cRing.drawDefaultRing(canvas);

        int startAngle = 0;
        CircleRing oneRing = new CircleRing(width, height, min, mRingWidth, mContext.getResources().getColor(R.color.color_yellow));
        int oneSweepAngle = getOneSweepAngle();
        oneRing.drawCircleRing(canvas, startAngle, oneSweepAngle);

        startAngle += oneSweepAngle;
        CircleRing twoRing = new CircleRing(width, height, min, mRingWidth, mContext.getResources().getColor(R.color.color_violet));
        int twoSweepAngle = getTwoSweepAngle();
        twoRing.drawCircleRing(canvas, startAngle, twoSweepAngle);

        startAngle += twoSweepAngle;
        CircleRing threeRing = new CircleRing(width, height, min, mRingWidth, mContext.getResources().getColor(R.color.color_pink));
        int threeSweepAngle = getThreeSweepAngle();
        threeRing.drawCircleRing(canvas, startAngle, threeSweepAngle);
    }

    class CircleRing {
        int ringX;
        int ringY;
        int ringRadius;
        int ringWidth;
        Paint paint;

        public CircleRing(int ringX, int ringY, int ringRadius, int ringWidth, int ringColor) {
            this.ringX = ringX;
            this.ringY = ringY;
            this.ringRadius = ringRadius;
            this.ringWidth = ringWidth;
            paint = new Paint();
            paint.reset();
            paint.setColor(ringColor);
            paint.setAntiAlias(true); //消除锯齿
            paint.setStrokeWidth(ringWidth);
            paint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形,只显示边缘的线，不显示内部
        }

        public void drawCircleRing(Canvas canvas, int startAngle, int sweepAngle) {
            RectF rect = new RectF(ringX - ringRadius, ringY - ringRadius, ringX + ringRadius, ringY + ringRadius);
            canvas.drawArc(rect, startAngle, sweepAngle, false, paint);
        }

        public void drawDefaultRing(Canvas canvas) {
            RectF rect = new RectF(ringX - ringRadius, ringY - ringRadius, ringX + ringRadius, ringY + ringRadius);
            canvas.drawArc(rect, 0, 360, false, paint);
        }
    }

    private int getOneSweepAngle() {
        int angle = 0;
        if (mAllProfit != 0) {
            angle = (int) (mOneProfit / mAllProfit * 360);
        }
        return angle;
    }

    private int getTwoSweepAngle() {
        int angle = 0;
        if (mAllProfit != 0) {
            angle = (int) (mTwoProfit / mAllProfit * 360);
        }
        return angle;
    }

    private int getThreeSweepAngle() {
        int angle = 0;
        if (mAllProfit != 0) {
            angle = (int) (mThreeProfit / mAllProfit * 360);
        }
        return angle;
    }

    public void initData(float allProfit, float oneProfit, float twoProfit, float threeProfit) {
        mAllProfit = allProfit;
        mOneProfit = oneProfit;
        mTwoProfit = twoProfit;
        mThreeProfit = threeProfit;
        this.invalidate();
    }

}
