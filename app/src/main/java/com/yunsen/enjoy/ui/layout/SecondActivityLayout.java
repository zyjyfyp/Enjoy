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
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

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
    private TextView hourTv;
    private TextView minuteTv;
    private TextView secondTv;
    private RecyclerView recycler;
    private ArrayList<CarDetails> mDatas;
    private FillActivityAdapter mAdapter;
    private static Handler sHandler;
    private static int TIME_FLAG = 1;
    private long mRemainingTime;
    private View moreTv;
    private LinearLayout topLayout;


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
        topLayout = (LinearLayout) rootView.findViewById(R.id.top_layout);
        recycler = (RecyclerView) rootView.findViewById(R.id.second_activity_recycler);
        moreTv = rootView.findViewById(R.id.more_tv);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        mDatas = new ArrayList<>();
        mAdapter = new FillActivityAdapter(mContext, R.layout.fill_activity_item, mDatas);
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        moreTv.setOnClickListener(this);
    }

    public void setTopTitleVisibility(int visibility) {
        topLayout.setVisibility(visibility);
    }

    public void setData(List<CarDetails> datas, long currentTime) {
        if (mAdapter != null) {
            mAdapter.upData(datas, currentTime);
        }
    }

    public void upTimeUi(long currentTime) {
        if (mAdapter != null) {
            mAdapter.upTime(currentTime);
        }
    }


    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > position) {
            CarDetails details = mDatas.get(position);
            UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(details.getId()), details.getTitle());
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    /**
     * @param time
     */
    public void sendRemaingingTime(long time) {
        this.mRemainingTime = time;
        if (!sHandler.hasMessages(TIME_FLAG)) {
            sHandler.sendEmptyMessageDelayed(TIME_FLAG, 1000);
        }
    }

    @Override
    public void onClick(View v) {
        UIHelper.showSecondActivityActivity(mContext);
    }

    public void removeMessage() {
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
                if (layout.mRemainingTime > 0) {
                    sHandler.sendEmptyMessageDelayed(TIME_FLAG, 1000);
                    Time time = new Time(layout.mRemainingTime);
                    int hours = time.getHours();
                    int minutes = time.getMinutes();
                    int seconds = time.getSeconds();
                    layout.hourTv.setText("" + hours);
                    layout.minuteTv.setText("" + minutes);
                    layout.secondTv.setText("" + seconds);
                }
            }
        }
    }
}
