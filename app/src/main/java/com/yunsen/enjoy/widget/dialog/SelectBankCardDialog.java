package com.yunsen.enjoy.widget.dialog;

import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.WithdrawCashActivity;
import com.yunsen.enjoy.adapter.SelectBankCardAdapter;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.BindCardBean;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/6/1.
 */

public class SelectBankCardDialog extends Dialog implements View.OnClickListener, MultiItemTypeAdapter.OnItemClickListener {
    private Activity mAct;
    private RecyclerView recyclerView;
    private TextView bindNewCard;
    private SelectBankCardAdapter mAdapter;
    private List<BindCardBean> mData;
    private onSelectBankListener mCallBack;

    public SelectBankCardDialog(@NonNull Activity act, List<BindCardBean> datas) {
        super(act, R.style.SelectBankDialogStyle);
        this.mAct = act;
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
        LayoutInflater inflater = (LayoutInflater) mAct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.select_bank_card_layout, null);
        setContentView(rootView);
        recyclerView = ((RecyclerView) rootView.findViewById(R.id.recycler_view));
        bindNewCard = ((TextView) rootView.findViewById(R.id.bind_new_card_tv));
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mAct));
        mAdapter = new SelectBankCardAdapter(mAct, R.layout.select_bank_card_item, mData);
        recyclerView.setAdapter(mAdapter);
    }

    private void requestData() {

    }

    public void upData(List<BindCardBean> datas) {
        if (datas != null && mAdapter != null) {
            mAdapter.upDatas(datas);
        }
    }

    private void initListener() {
        bindNewCard.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bind_new_card_tv) {
            UIHelper.showBindBankCardActivity(mAct);
            this.dismiss();
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mCallBack != null) {
            if (mData != null && mData.size() > position) {
//                CheckBox checkBox = (CheckBox) view.findViewById(R.id.select_bank_item_cb);
                int size = mData.size();
                for (int i = 0; i < size; i++) {
                    BindCardBean cardBean = mData.get(i);
                    if (position == i) {
                        cardBean.setIs_default(1);
                    } else {
                        cardBean.setIs_default(0);
                    }
                }
                mAdapter.notifyDataSetChanged();
                mCallBack.onCallBack(mData.get(position));
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    public void setOnSelectBackListener(onSelectBankListener callBack) {
        this.mCallBack = callBack;
    }

    public interface onSelectBankListener {
        public void onCallBack(BindCardBean data);
    }
}
