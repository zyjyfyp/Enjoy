package com.yunsen.enjoy.widget.drag;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.ui.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/5.
 */
public class DragLayout extends FrameLayout implements View.OnClickListener {
    private List<DragAttr> mAttr = new ArrayList<>(3);

    private int oldIndex = 0;

    private ImageView dragIcon;
    private ViewDragHelper mDragHelper;
    private boolean mCanDrag = true;
    private int oldXStart;
    private int oldYStart;
    private int oldXEnd;
    private int oldYEnd;
    private int finalLeft = -1;
    private int finalTop = -1;
    private int statusType = 0;//0无 随便移动   1靠左  2靠右 0靠左右
    private float showPercent = 1;
    private int screenWidth;
    private Activity mActivity;

    public DragLayout(Context context) {
        super(context);
        initView();
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        screenWidth = getScreenWidth(getContext());
        dragIcon = new ImageView(getContext());
        dragIcon.setImageResource(R.mipmap.phone_drag_icon);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM | Gravity.END;
        lp.bottomMargin = 300;
        lp.rightMargin = 50;
        dragIcon.setLayoutParams(lp);
        this.addView(dragIcon);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return mCanDrag;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int height = DragLayout.this.getHeight() - child.getHeight();
                height = top > height ? height : top;
                height = 0 > height ? 0 : height;
                return height;

            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int childWidth = DragLayout.this.getWidth() - child.getWidth();
                int width = left > childWidth ? childWidth : left;
                width = 0 > width ? 0 : width;
                return width;
            }

            @Override
            public void onViewDragStateChanged(int state) {
                switch (state) {
                    case ViewDragHelper.STATE_IDLE:
                        oldXStart = dragIcon.getLeft();
                        oldYStart = dragIcon.getTop();
                        oldXEnd = oldXStart + dragIcon.getWidth();
                        oldYEnd = oldYStart + dragIcon.getHeight();
                        //                        Log.e(TAG, "onViewDragStateChanged: oldXStart=" + oldXStart + "  oldYEnd=" + oldYStart);
                        break;
                    case ViewDragHelper.STATE_DRAGGING:
                        break;
                    case ViewDragHelper.STATE_SETTLING:
                        break;
                }
                super.onViewDragStateChanged(state);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                int viewWidth = releasedChild.getWidth();
                int viewHeight = releasedChild.getHeight();
                int curLeft = releasedChild.getLeft();
                int curTop = releasedChild.getTop();

                finalTop = curTop < 0 ? 0 : curTop;
                finalLeft = curLeft < 0 ? 0 : curLeft;
                if ((finalTop + viewHeight) > getHeight()) {
                    finalTop = getHeight() - viewHeight;
                }

                if ((finalLeft + viewWidth) > getWidth()) {
                    finalLeft = getWidth() - viewWidth;
                }
                switch (statusType) {
                    case 0://无
                        break;
                    case 1://左
                        finalLeft = -(int) (viewWidth * (1 - showPercent));
                        break;
                    case 2://右
                        finalLeft = screenWidth - (int) (viewWidth * showPercent);
                        break;
                    case 3://左右
                        finalLeft = -(int) (viewWidth * (1 - showPercent));
                        if ((curLeft + viewWidth / 2) > screenWidth / 2) {
                            finalLeft = screenWidth - (int) (viewWidth * showPercent);
                        }
                        break;
                }
                mDragHelper.settleCapturedViewAt(finalLeft, finalTop);
                invalidate();
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {

                Log.e("TAG", "滑动到左边或右边了");
                super.onEdgeTouched(edgeFlags, pointerId);
                Toast.makeText(getContext(), "点击到左边了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float evX = (int) ev.getX();
        float evY = (int) ev.getY();
        boolean b = super.dispatchTouchEvent(ev);
        Log.e(TAG, "dispatchTouchEvent: dispatch= evX=" + evX + "  evY=" + evY);
        Log.e(TAG, "dispatchTouchEvent: oldXStart=" + oldXStart + " oldXEnd= " + oldXEnd + "oldYStart="
                + oldYStart + " oldYEnd=" + oldYEnd);
        if (evX > oldXStart && evX < oldXEnd && evY > oldYStart && evY < oldYEnd) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = mDragHelper.shouldInterceptTouchEvent(ev);
        Log.e(TAG, "onInterceptTouchEvent: intercept=" + b);
        return b;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        oldXStart = dragIcon.getLeft();
        oldYStart = dragIcon.getTop();
        oldXEnd = oldXStart + dragIcon.getWidth();
        oldYEnd = oldYStart + dragIcon.getHeight();
        if (finalLeft == -1 && finalTop == -1) {
            mAttr.add(new DragAttr(oldXStart, oldYStart, oldXEnd, oldYEnd));
            mAttr.add(new DragAttr(oldXStart, oldYStart, oldXEnd, oldYEnd));
            mAttr.add(new DragAttr(oldXStart, oldYStart, oldXEnd, oldYEnd));
            mAttr.add(new DragAttr(oldXStart, oldYStart, oldXEnd, oldYEnd));
            super.onLayout(changed, left, top, right, bottom);
        }
    }

    private static final String TAG = "DragLayout";

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public void setCanDrag(boolean mCanDrag) {
        this.mCanDrag = mCanDrag;
    }

    public void setDragIconDrawable(Drawable drawable) {
        dragIcon.setImageDrawable(drawable);
    }

    public void setDragIconClick(Activity act) {
        this.mActivity=act;
        dragIcon.setOnClickListener(this);
    }

    /**
     * 保存状态
     *
     * @return
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    public void move() {
        //        mDragHelper.isCapturedViewUnder()
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public void noticeView(int index) {
        DragAttr olderDragAttr = mAttr.get(oldIndex);
        olderDragAttr.saveLayout(oldXStart, oldYStart, oldXEnd, oldYEnd);
        DragAttr attr = mAttr.get(index);

        if (attr.needLayout()) {
            dragIcon.layout(attr.getStartX(), attr.getStartY(), attr.getEndX(), attr.getEndY());
        }
        oldIndex = index;
    }

    @Override
    public void onClick(View v) {
        if (mActivity instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) mActivity).requestPermission(Permission.CALL_PHONE, Constants.CALL_PHONE);
        }
    }
}
