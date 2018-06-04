package com.yunsen.enjoy.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.adapter.BindCardTypeAdapter;
import com.yunsen.enjoy.model.BindCardTypeBean;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/4.
 */

public class BindCardTypeDialog extends Dialog implements MultiItemTypeAdapter.OnItemClickListener {
    private List<BindCardTypeBean> mData;
    private Activity mContext;
    private RecyclerView recyclerView;
    private BindCardTypeAdapter mAdapter;
    private onBindBankTypeListener mOnItemClick;

    public BindCardTypeDialog(@NonNull Activity context, List<BindCardTypeBean> datas) {
        super(context, R.style.SelectBankDialogStyle);
        this.mContext = context;
        // 拿到Dialog的Window, 修改Window的属性
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        int height = DeviceUtil.getHeight(context) / 3 * 2;
        int width = DeviceUtil.getWidth(context) / 3 * 2;
        attributes.width = width;
        attributes.height = height;
        attributes.gravity = Gravity.CENTER;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
        this.mData = datas;
        if (mData == null) {
            mData = new ArrayList<>();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        requestData();
        initListener();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.bind_card_type_layout, null);
        setContentView(rootView);
        recyclerView = ((RecyclerView) rootView.findViewById(R.id.recycler_view));
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new BindCardTypeAdapter(mContext, R.layout.bind_card_type_item, mData);
        recyclerView.setAdapter(mAdapter);
    }

    private void requestData() {

    }

    private void initListener() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mData != null && mData.size() > position) {
            int size = mData.size();
            for (int i = 0; i < size; i++) {
                BindCardTypeBean bean = mData.get(i);
                bean.setCheck(position == i);
            }
            mAdapter.notifyDataSetChanged();
            if (mOnItemClick != null) {
                mOnItemClick.onCallBack(mData.get(position));
                if (this.isShowing()) {
                    this.dismiss();
                }
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }


    public void setOnBindBackTypeListener(onBindBankTypeListener callBack) {
        this.mOnItemClick = callBack;
    }

    public interface onBindBankTypeListener {
        public void onCallBack(BindCardTypeBean data);
    }

}
