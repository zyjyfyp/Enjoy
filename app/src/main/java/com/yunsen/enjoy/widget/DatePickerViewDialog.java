package com.yunsen.enjoy.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.widget.interfaces.onLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.onRightOnclickListener;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/5/4.
 */

public class DatePickerViewDialog extends Dialog {
    Context mContext;
    private DatePicker datePicker;
    private TextView leftTv;
    private TextView rightTv;
    private onLeftOnclickListener leftOnclickListener;
    private onRightOnclickListener rightOnclickListener;
    private String leftStr;
    private String rightStr;

    public DatePickerViewDialog(@NonNull Context context) {
        super(context, R.style.BottomDialogStyle);
        this.mContext = context;
        // 拿到Dialog的Window, 修改Window的属性
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker_dialog);
        intView();
        initData();
        //初始化界面控件的事件
        initEvent();
    }


    private void intView() {
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        datePicker = (DatePicker) findViewById(R.id.date_picker);
        leftTv = (TextView) findViewById(R.id.left_tv);
        rightTv = (TextView) findViewById(R.id.right_tv);
        datePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerDividerColor(datePicker);
    }

    private void initData() {
        if (leftStr != null) {
            leftTv.setText(leftStr);
        }
        if (rightStr != null) {
            rightTv.setText(rightStr);
        }
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightOnclickListener != null) {
                    rightOnclickListener.onRightClick(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftOnclickListener != null) {
                    leftOnclickListener.onLeftClick();
                }
            }
        });
    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onLeftOnclickListener
     */
    public void setLeftOnclickListener(String str, onLeftOnclickListener onLeftOnclickListener) {
        if (str != null) {
            leftStr = str;
        }
        this.leftOnclickListener = onLeftOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onRightOnclickListener
     */
    public void setRightOnclickListener(String str, onRightOnclickListener onRightOnclickListener) {
        if (str != null) {
            rightStr = str;
        }
        this.rightOnclickListener = onRightOnclickListener;
    }

    private static final String TAG = "DatePickerViewDialog";

    /**
     * 设置时间选择器的分割线颜色
     *
     * @param datePicker
     */
    private void setNumberPickerDividerColor(DatePicker datePicker) {
        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);
        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);
            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {//颜色
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(getContext().getResources().getColor(R.color.color_22ac38)));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获得时间
     *
     * @return yyyy-mm-dd
     */
    public String getDate(int year, int month, int day) {
        StringBuilder sbDate = new StringBuilder();
        sbDate.append(format2Digits(year)).append("-")
                .append(format2Digits(month + 1)).append("-")
                .append(format2Digits(day));
        return sbDate.toString();
    }

    private String format2Digits(int value) {
        return String.format("%02d", value);
    }
}
