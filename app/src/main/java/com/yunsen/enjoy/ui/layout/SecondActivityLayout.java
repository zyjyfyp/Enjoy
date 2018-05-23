package com.yunsen.enjoy.ui.layout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.home.FillActivityAdapter;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SecondActivityLayout extends LinearLayout implements MultiItemTypeAdapter.OnItemClickListener {
    private Context mContext;
    private View rootView;
    private TextView hourTv;
    private TextView minuteTv;
    private TextView secondTv;
    private RecyclerView recycler;
    private ArrayList<CarDetails> mDatas;
    private FillActivityAdapter mAdapter;
    private static Handler sHandler;
    private static int TIME_FLAG = 1;
    private long mRemainingtime;


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
        sHandler = new MyHandler(this);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.second_activity_layout, this);
        hourTv = (TextView) rootView.findViewById(R.id.home_activity_hour);
        minuteTv = (TextView) rootView.findViewById(R.id.home_activity_minute);
        secondTv = (TextView) rootView.findViewById(R.id.home_activity_second);
        recycler = (RecyclerView) rootView.findViewById(R.id.second_activity_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        mDatas = new ArrayList<>();
        mAdapter = new FillActivityAdapter(mContext, R.layout.fill_activity_item, mDatas);
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }


    public void setData(List<CarDetails> datas) {
        if (mAdapter != null) {
            mAdapter.upData(datas);
        }
    }


    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > position) {
            ToastUtils.makeTextShort(mDatas.get(position).getTitle());
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    private static class MyHandler extends Handler {
        private WeakReference<SecondActivityLayout> weakReference;

        public MyHandler(SecondActivityLayout secondActivityLayout) {
            this.weakReference = new WeakReference<SecondActivityLayout>(secondActivityLayout);
        }

        @Override
        public void handleMessage(Message msg) {
            SecondActivityLayout layout = weakReference.get();
            if (layout != null) {
                if (layout.mRemainingtime > 0) {
                    sHandler.sendEmptyMessageDelayed(TIME_FLAG, 1000);
//                    layout.hourTv.setText();
                }
            }
        }
    }
}
