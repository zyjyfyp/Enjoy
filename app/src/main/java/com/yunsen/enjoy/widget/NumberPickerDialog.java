package com.yunsen.enjoy.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.yunsen.enjoy.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/4/23.
 */

public class NumberPickerDialog extends Dialog {

    private TextView leftTv;
    private TextView rightTv;
    private onLeftOnclickListener leftOnclickListener;
    private onRightOnclickListener rightOnclickListener;
    private String[] datas;
    private String leftStr;
    private String rightStr;
    private NumberPicker picker;

    public NumberPickerDialog(@NonNull Context context, String... data) {
        super(context, R.style.BottomDialogStyle);
        this.datas = data;
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
        setContentView(R.layout.num_picker_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }


    private void initData() {
        if (leftStr != null) {
            leftTv.setText(leftStr);
        }
        if (rightStr != null) {
            rightTv.setText(rightStr);
        }
    }

    private void initView() {
        leftTv = (TextView) findViewById(R.id.left_tv);
        rightTv = (TextView) findViewById(R.id.right_tv);
        picker = (NumberPicker) findViewById(R.id.number_picker);
        picker.setDisplayedValues(datas);
        setNumberPickerDividerColor(picker);
        picker.setMaxValue(datas.length - 1);
        picker.setMinValue(0);
        picker.setWrapSelectorWheel(true);

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
                    rightOnclickListener.onRightClick(picker.getValue());
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

    private static final String TAG = "NumberPickerDialog";

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


    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(getContext().getResources().getColor(R.color.color_22ac38)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onRightOnclickListener {
        public void onRightClick(int index);
    }

    public interface onLeftOnclickListener {
        public void onLeftClick();
    }

}
