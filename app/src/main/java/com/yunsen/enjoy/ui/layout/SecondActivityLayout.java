package com.yunsen.enjoy.ui.layout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.home.FillActivityAdapter;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.StringUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.LoadMoreView;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.lang.ref.WeakReference;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SecondActivityLayout extends LinearLayout implements MultiItemTypeAdapter.OnItemClickListener, View.OnClickListener {
    private Context mContext;
    private View rootView;
    private RecyclerView recycler;
    private ArrayList<CarDetails> mDatas;
    private FillActivityAdapter mAdapter;
    private View moreTv;
    private LinearLayout topLayout;
    private LoadMoreLayout loadMoreLayout;


    public SecondActivityLayout(Context context) {
        super(context);
        initView(context);
    }

    public SecondActivityLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SecondActivityLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.second_activity_layout, this);
        topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        recycler = (RecyclerView) rootView.findViewById(R.id.second_activity_recycler);
        moreTv = rootView.findViewById(R.id.more_tv);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        loadMoreLayout = new LoadMoreLayout(context);
        mDatas = new ArrayList<>();
        mAdapter = new FillActivityAdapter(mContext, R.layout.fill_activity_item, mDatas);
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recycler.setAdapter(headerAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(recycler, loadMoreLayout);
        loadMoreLayout.goneView();
        mAdapter.setOnItemClickListener(this);
        moreTv.setOnClickListener(this);
    }

    public LoadMoreLayout getLoadMoreLayout() {
        return loadMoreLayout;
    }

    public RecyclerView getRecycler() {
        return recycler;
    }

    public FillActivityAdapter getAdapter() {
        return mAdapter;
    }

    public void setTopTitleVisibility(int visibility) {
        topLayout.setVisibility(visibility);
    }

    public void upData(List<CarDetails> datas, long currentTime) {
        if (mAdapter != null) {
            mAdapter.upData(datas, currentTime);
        }
    }

    public void upTimeUi(long currentTime) {
        if (mAdapter != null) {
            mAdapter.upTime(currentTime);
        }
    }

    /**
     * 添加
     *
     * @param datas
     * @param currentTime
     */
    public boolean addData(List<CarDetails> datas, long currentTime) {
        if (mAdapter != null) {
            return mAdapter.addDatas(datas, currentTime);
        }
        return false;
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > position) {
            CarDetails details = mDatas.get(position);
//                if (holder instanceof ViewHolder) {
//                    CountDownLayout downLayout = (CountDownLayout) ((ViewHolder) holder).getView(R.id.count_down_layout);
//                }
            if (adapter instanceof FillActivityAdapter) {
                String startString = details.getStart_time();
                String endString = details.getEnd_time();
                long startTime = StringUtils.toDate(startString) != null ? StringUtils.toDate(startString).getTime() : 0;
                long endTime = StringUtils.toDate(endString) != null ? StringUtils.toDate(endString).getTime() : 0;
                startTime = startTime / 1000;
                endTime = endTime / 1000;
                long currentTime = ((FillActivityAdapter) adapter).getCurrentTime();
                if (currentTime < startTime) {
//                    ToastUtils.makeTextShort("秒杀活动即将开始");
                    UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(details.getId()), details.getTitle(), Constants.DEFAULT_BUY, -1);
                } else if (currentTime > endTime) {
//                    ToastUtils.makeTextShort("秒杀活动已结束");
                    UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(details.getId()), details.getTitle(), Constants.DEFAULT_BUY, -2);
                } else {
                    UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(details.getId()), details.getTitle(), Constants.DEFAULT_BUY, endTime - currentTime);
                }
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }


    @Override
    public void onClick(View v) {
        UIHelper.showSecondActivityActivity(mContext);
    }


}
