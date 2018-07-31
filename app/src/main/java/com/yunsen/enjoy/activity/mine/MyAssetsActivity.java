package com.yunsen.enjoy.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.MyAssetsAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.MyAssetsBean;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.PullToRefreshView;
import com.yunsen.enjoy.widget.PullToRefreshView.OnFooterRefreshListener;
import com.yunsen.enjoy.widget.PullToRefreshView.OnHeaderRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 财务管理
 *
 * @author Administrator
 */
public class MyAssetsActivity extends BaseFragmentActivity implements OnClickListener {
    private ImageView cursor1, cursor2, cursor3, cursor4;
    private LinearLayout index_item0, index_item1, index_item2, index_item3;
    private SharedPreferences spPreferences;
    private TextView tv_ticket, tv_shop_ticket, tv_jifen_ticket,
            tv_djjifen_ticket;
    private ArrayList<MyAssetsBean> list;
    private ListView listView;
    private PullToRefreshView refresh;
    MyAssetsAdapter adapter;
    int len;
    public static String fund_id = "0";
    private int RUN_METHOD = -1;
    private DialogProgress progress;
    private ImageView iv_biaoti1, iv_biaoti2, iv_biaoti3, iv_biaoti4;
    private TextView listTitle;
    private String mSign;

    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_my_assets;
    }

    @Override
    protected void initView() {
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mSign = spPreferences.getString(SpConstants.LOGIN_SIGN, "");
        progress = new DialogProgress(this);
        Initialize();
        list = new ArrayList<MyAssetsBean>();
        adapter = new MyAssetsAdapter(list, MyAssetsActivity.this);
        listView.setAdapter(adapter);
        topDatas();
        fund_id = "1";
        //	判断状态到界面
        String status = getIntent().getStringExtra(Constants.MY_ASSETS_INDEX_KEY);
        if (status != null) {
            if (status.equals("1")) {
                cursor1.setVisibility(View.VISIBLE);
                cursor2.setVisibility(View.INVISIBLE);
                cursor3.setVisibility(View.INVISIBLE);
                cursor4.setVisibility(View.INVISIBLE);
                listTitle.setText("收支明细");
                fund_id = "1";
                load_list(true, fund_id);
            } else if (status.equals("2")) {
                listTitle.setText("冻结资金明细");
                cursor1.setVisibility(View.INVISIBLE);
                cursor2.setVisibility(View.VISIBLE);
                cursor3.setVisibility(View.INVISIBLE);
                cursor4.setVisibility(View.INVISIBLE);
                fund_id = "6";
                load_list(true, fund_id);
            } else if (status.equals("3")) {
                listTitle.setText("佣金明细");
                cursor1.setVisibility(View.INVISIBLE);
                cursor2.setVisibility(View.INVISIBLE);
                cursor3.setVisibility(View.VISIBLE);
                cursor4.setVisibility(View.INVISIBLE);
                fund_id = "3";
                load_list(true, fund_id);
            } else if (status.equals("4")) {
                listTitle.setText("提现明细");
                cursor1.setVisibility(View.INVISIBLE);
                cursor2.setVisibility(View.INVISIBLE);
                cursor3.setVisibility(View.INVISIBLE);
                cursor4.setVisibility(View.VISIBLE);
                fund_id = "11";
                request4(true);
            }
        } else {
            load_list(true, fund_id);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    private static final String TAG = "MyAssetsActivity";

    @Override
    public void requestData() {
        HttpProxy.getCrashMoneyAll(mSign, new HttpCallBack<Double>() {
            @Override
            public void onSuccess(Double responseData) {
                tv_jifen_ticket.setText(responseData + "元");//提现
                Log.e(TAG, "onSuccess: " + responseData);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    private void request4(boolean flag) {
        progress.CreateProgress();
        if (flag) {
            // 计数和容器清零
            CURRENT_NUM = 1;
        } else {
            CURRENT_NUM++;
        }
        final boolean fFlog = flag;

        HttpProxy.getWithDrawCash(mSign, String.valueOf(CURRENT_NUM), new HttpCallBack<List<MyAssetsBean>>() {
            @Override
            public void onSuccess(List<MyAssetsBean> responseData) {
                progress.CloseProgress();
                if (fFlog) {
                    adapter.upData(responseData);
                } else {
                    adapter.addData(responseData);
                }
                if (responseData == null) {
                    ToastUtils.makeTextShort("暂无数据");
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    ToastUtils.makeTextShort(e.getMessage());
                }
                progress.CloseProgress();
            }
        });
    }

    /**
     * 控件初始化
     */
    private void Initialize() {
        try {
            listTitle = (TextView) findViewById(R.id.list_title);
            iv_biaoti1 = (ImageView) findViewById(R.id.iv_biaoti1);
            iv_biaoti2 = (ImageView) findViewById(R.id.iv_biaoti2);
            iv_biaoti3 = (ImageView) findViewById(R.id.iv_biaoti3);
            iv_biaoti4 = (ImageView) findViewById(R.id.iv_biaoti4);
            Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.zhye);
            BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
            iv_biaoti1.setBackgroundDrawable(bd1);
            Bitmap bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ylj);
            BitmapDrawable bd2 = new BitmapDrawable(this.getResources(), bm2);
            iv_biaoti2.setBackgroundDrawable(bd2);
            Bitmap bm3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jb);
            BitmapDrawable bd3 = new BitmapDrawable(this.getResources(), bm3);
            iv_biaoti3.setBackgroundDrawable(bd3);
            Bitmap bm4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.hb);
            BitmapDrawable bd4 = new BitmapDrawable(this.getResources(), bm4);
            iv_biaoti4.setBackgroundDrawable(bd4);
            refresh = (PullToRefreshView) findViewById(R.id.refresh);
            refresh.setOnHeaderRefreshListener(listHeadListener);
            refresh.setOnFooterRefreshListener(listFootListener);
            listView = (ListView) findViewById(R.id.new_list);
            tv_ticket = (TextView) findViewById(R.id.tv_ticket);
            tv_shop_ticket = (TextView) findViewById(R.id.tv_shop_ticket);
            tv_jifen_ticket = (TextView) findViewById(R.id.tv_jifen_ticket);
            tv_djjifen_ticket = (TextView) findViewById(R.id.tv_djjifen_ticket);
            index_item0 = (LinearLayout) findViewById(R.id.index_item0);
            index_item1 = (LinearLayout) findViewById(R.id.index_item1);
            index_item2 = (LinearLayout) findViewById(R.id.index_item2);
            index_item3 = (LinearLayout) findViewById(R.id.index_item3);
            cursor1 = (ImageView) findViewById(R.id.cursor1);
            cursor2 = (ImageView) findViewById(R.id.cursor2);
            cursor3 = (ImageView) findViewById(R.id.cursor3);
            cursor4 = (ImageView) findViewById(R.id.cursor4);
            index_item0.setOnClickListener(this);
            index_item1.setOnClickListener(this);
            index_item2.setOnClickListener(this);
            index_item3.setOnClickListener(this);

            ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
            iv_fanhui.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    finish();
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_item0:
                listTitle.setText("收支明细");
                cursor1.setVisibility(View.VISIBLE);
                cursor2.setVisibility(View.INVISIBLE);
                cursor3.setVisibility(View.INVISIBLE);
                cursor4.setVisibility(View.INVISIBLE);
                fund_id = "1";
                load_list(true, fund_id);
                break;
            case R.id.index_item1:
                cursor1.setVisibility(View.INVISIBLE);
                cursor2.setVisibility(View.VISIBLE);
                cursor3.setVisibility(View.INVISIBLE);
                cursor4.setVisibility(View.INVISIBLE);
                fund_id = "6";
                listTitle.setText("冻结资金明细");
                load_list(true, fund_id);
                break;
            case R.id.index_item2:
                listTitle.setText("佣金明细");
                cursor1.setVisibility(View.INVISIBLE);
                cursor2.setVisibility(View.INVISIBLE);
                cursor3.setVisibility(View.VISIBLE);
                cursor4.setVisibility(View.INVISIBLE);
                fund_id = "3";
                load_list(true, fund_id);
                break;
            case R.id.index_item3:
                listTitle.setText("提现明细");
                cursor1.setVisibility(View.INVISIBLE);
                cursor2.setVisibility(View.INVISIBLE);
                cursor3.setVisibility(View.INVISIBLE);
                cursor4.setVisibility(View.VISIBLE);
                fund_id = "11";
                request4(true);
                break;
            default:
                break;
        }
    }

    /**
     * 上拉列表刷新加载
     */
    private OnHeaderRefreshListener listHeadListener = new OnHeaderRefreshListener() {

        @Override
        public void onHeaderRefresh(PullToRefreshView view) {
            refresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ("11".equals(fund_id)) {
                        request4(true);
                    } else {
                        load_list(true, fund_id);
                    }
                    refresh.onHeaderRefreshComplete();
                }
            }, 1000);
        }
    };

    /**
     * 下拉列表刷新加载
     */
    private OnFooterRefreshListener listFootListener = new OnFooterRefreshListener() {

        @Override
        public void onFooterRefresh(PullToRefreshView view) {
            refresh.postDelayed(new Runnable() {

                @Override
                public void run() {
                    try {
                        if ("11".equals(fund_id)) {
                            request4(false);
                        } else {
                            load_list(false, fund_id);
                        }
                        refresh.onFooterRefreshComplete();
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }, 1000);
        }
    };


    /**
     * 第1个列表数据解析
     */
    private int CURRENT_NUM = 1;
    private final int VIEW_NUM = 10;

    private void load_list(boolean flag, String fund_id) {
        progress.CreateProgress();
        RUN_METHOD = 1;
        if (flag) {
            // 计数和容器清零
            CURRENT_NUM = 1;
        } else {
            CURRENT_NUM++;
        }
        final boolean fFlag = flag;
        String user_name = spPreferences.getString(SpConstants.USER_NAME, "");
        String user_id = spPreferences.getString(SpConstants.USER_ID, "");
        System.out.println("=====================fund_id--" + fund_id);
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_payrecord_list?user_id="
                        + user_id + "&user_name=" + user_name + "&fund_id=" + fund_id
                        + "&page_size=" + VIEW_NUM + "&page_index=" + CURRENT_NUM + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        ArrayList<MyAssetsBean> datas = new ArrayList<>();
                        try {
                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                JSONArray jsonArray = object.getJSONArray("data");
                                len = jsonArray.length();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    MyAssetsBean data = new MyAssetsBean();
                                    data.fund = json.getString("fund");
                                    data.income = json.getString("income");
                                    data.user_name = json.getString("user_name");
                                    data.add_time = json.getString("add_time");
                                    data.expense = json.getString("expense");
                                    data.remark = json.getString("remark");
                                    data.balance = json.getString("balance");
                                    datas.add(data);
                                }
                            } else {
                                Toast.makeText(MyAssetsActivity.this, info, Toast.LENGTH_SHORT).show();
                            }

                            if (fFlag) {
                                adapter.upData(datas);
                            } else {
                                adapter.addData(datas);
                            }
                            progress.CloseProgress();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String s) {
                        super.onFailure(throwable, s);
                        progress.CloseProgress();
                    }
                }, null);
    }


    /**
     * 顶部的 数据值
     */
    private void topDatas() {
        SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String amount = sp.getString(SpConstants.AMOUNT, "0.0");
        String reserve = sp.getString(SpConstants.RESERVE, "0.0");
        tv_ticket.setText(amount + "元");//余额
//        tv_shop_ticket.setText(reserve + "元");//冻结资金

//        tv_jifen_ticket.setText("0.0元");//提现
        tv_djjifen_ticket.setText("0.0元");// 佣金
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            if (list.size() > 0) {
                list.clear();
                list = null;
            }
            BitmapDrawable bd1 = (BitmapDrawable) iv_biaoti1.getBackground();
            iv_biaoti1.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd1.setCallback(null);
            bd1.getBitmap().recycle();
            BitmapDrawable bd2 = (BitmapDrawable) iv_biaoti2.getBackground();
            iv_biaoti2.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd2.setCallback(null);
            bd2.getBitmap().recycle();
            BitmapDrawable bd3 = (BitmapDrawable) iv_biaoti3.getBackground();
            iv_biaoti3.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd3.setCallback(null);
            bd3.getBitmap().recycle();
            BitmapDrawable bd4 = (BitmapDrawable) iv_biaoti4.getBackground();
            iv_biaoti4.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd4.setCallback(null);
            bd4.getBitmap().recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
