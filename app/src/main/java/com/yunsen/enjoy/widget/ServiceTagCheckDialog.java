package com.yunsen.enjoy.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.dealer.SelectTagAdapter;
import com.yunsen.enjoy.model.ServiceProject;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.widget.interfaces.onLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.onRightServiceProjectOnclickListener;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2018/7/9.
 */

public class ServiceTagCheckDialog extends Dialog {
    private List<ServiceProject> mDatas;
    private String leftStr;
    private String rightStr;
    private RecyclerView recylerView;
    private TextView rightTv;
    private TextView leftTv;
    private onRightServiceProjectOnclickListener rightOnclickListener;
    private onLeftOnclickListener leftOnclickListener;
    private SelectTagAdapter mAdapter;

    public ServiceTagCheckDialog(@NonNull Context context, @NonNull List<ServiceProject> datas) {
        super(context, R.style.BottomDialogStyle);
        this.mDatas = datas;
        float dimen40 = context.getResources().getDimension(R.dimen.dp_40);
        float height = datas.size() * dimen40 + DeviceUtil.dp2px(context, 80);
        height = Math.min(DeviceUtil.getScreenHeight() / 2, height);
        // 拿到Dialog的Window, 修改Window的属性
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = (int) height;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_service_tag_dialog);
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
        recylerView = (RecyclerView) findViewById(R.id.recycler_view);
        recylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SelectTagAdapter(getContext(), R.layout.select_tag_item, mDatas);
        recylerView.setAdapter(mAdapter);
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                if (adapter instanceof SelectTagAdapter) {
                    ((SelectTagAdapter) adapter).setNeedToCheck(position);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        //设置确定按钮被点击后，向外界提供监听
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightOnclickListener != null) {
                    rightOnclickListener.onRightClick(mAdapter.getCheckedDatas());
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
    public void setRightOnclickListener(String str, onRightServiceProjectOnclickListener onRightOnclickListener) {
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

}
