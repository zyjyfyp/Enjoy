package com.yunsen.enjoy.ui.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.home.GoodsPartsAdapter;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class GoodsPartsLayout extends LinearLayout implements MultiItemTypeAdapter.OnItemClickListener, View.OnClickListener {
    private Context mContext;
    private LayoutInflater inflater;
    private View rootView;
    private RecyclerView recyclerView;
    private ArrayList<CarDetails> mDatas;
    private GoodsPartsAdapter mAdapter;
    private TextView moreTv;

    public GoodsPartsLayout(Context context) {
        super(context);
        initView(context);
    }

    public GoodsPartsLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GoodsPartsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        rootView = inflater.inflate(R.layout.home_shop_layout, this);
        recyclerView = ((RecyclerView) rootView.findViewById(R.id.home_activity_recycler));
        moreTv = ((TextView) rootView.findViewById(R.id.more_tv));
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mDatas = new ArrayList<>();
        mAdapter = new GoodsPartsAdapter(mContext, R.layout.goods_parts_item, mDatas);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        moreTv.setOnClickListener(this);
    }

    public void setData(List<CarDetails> datas) {
        mAdapter.upData(datas);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > position) {
            if (mDatas != null && mDatas.size() > position) {
                CarDetails details = mDatas.get(position);
                UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(details.getId()), details.getTitle());
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public void onClick(View v) {
        UIHelper.showPartsShopActivity(mContext);
    }
}
