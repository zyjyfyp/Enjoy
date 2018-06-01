package com.yunsen.enjoy.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.adapter.SelectBankCardAdapter;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.BindCardBean;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/6/1.
 */

public class SelectBankCardDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private RecyclerView recyclerView;
    private TextView bindNewCard;
    private SelectBankCardAdapter mAdapter;
    private List<BindCardBean> mData;

    public SelectBankCardDialog(@NonNull Context context, List<BindCardBean> datas) {
        super(context);
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
        View rootView = inflater.inflate(R.layout.select_bank_card_layout, null);
        setContentView(rootView);
        recyclerView = ((RecyclerView) rootView.findViewById(R.id.recycler_view));
        bindNewCard = ((TextView) rootView.findViewById(R.id.bind_new_card_tv));
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SelectBankCardAdapter(mContext, R.layout.select_bank_card_item, mData);
        recyclerView.setAdapter(mAdapter);
    }

    private void requestData() {

    }

    public void upData(List<BindCardBean> datas) {
        if (datas != null) {
            mAdapter.upDatas(datas);
        }
    }

    private void initListener() {
        bindNewCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
