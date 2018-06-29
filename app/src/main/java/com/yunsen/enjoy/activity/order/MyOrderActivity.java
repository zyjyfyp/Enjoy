package com.yunsen.enjoy.activity.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.order.adapter.MyOrderllAdapter;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.MyOrderData;
import com.yunsen.enjoy.model.OrderBean;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.thirdparty.PayResult;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.PullToRefreshView;
import com.yunsen.enjoy.widget.PullToRefreshView.OnFooterRefreshListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 *
 * @author Administrator
 */
public class MyOrderActivity extends AppCompatActivity implements OnClickListener {
    private ImageView iv_fanhui, cursor2, cursor3, cursor4, cursor5;
    private Button fanhui, btn_chongzhi;
    private LinearLayout index_item1, index_item2, index_item3, index_item4;
    private SharedPreferences spPreferences;
    private PullToRefreshView refresh;
    private ListView my_list;
    private MyOrderllAdapter madapter;
    private ArrayList<MyOrderData> list;
    ImageView imageView1;
    MyOrderData md;
    OrderBean mb;
    private DialogProgress progress;
    String user_name, user_id, login_sign, order_no;
    int len;
    String strwhere = "datatype=1";
    private int RUN_METHOD = -1;
    String recharge_no, total_c;
    LinearLayout no_data_no;
    String payment_status;
    String type = "";
    public static boolean teby = false;
    public static String notify_url;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_order_list);
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name = spPreferences.getString(SpConstants.USER_NAME, "");
        user_id = spPreferences.getString("user_id", "");
        progress = new DialogProgress(MyOrderActivity.this);
        Initialize();
        list = new ArrayList<MyOrderData>();
        madapter = new MyOrderllAdapter(list, MyOrderActivity.this, handler);
        my_list.setAdapter(madapter);
    }

    @Override
    public void onResume() {

        super.onResume();
        try {
            System.out.println("状态==============" + teby);
            //余额支付更新
            if (teby == true) {
                //				userloginqm();
            }

            //		//判断状态到界面
            String status = getIntent().getStringExtra("status");
            if (status != null) {
                if (status.equals("0")) {
                    item1();
                    strwhere = "datatype=1";
                    //				list.clear();
                    load_list(true, strwhere);
                } else if (status.equals("1")) {
                    item2();
                    strwhere = "payment_status=1%20and%20datatype=1";
                    //				list.clear();
                    load_list(true, strwhere);
                } else if (status.equals("2")) {
                    item3();
                    strwhere = "payment_status=2%20and%20express_status=1%20and%20status=2%20and%20datatype=1";
                    //				list.clear();
                    load_list(true, strwhere);
                } else if (status.equals("3")) {
                    item4();
                    strwhere = "payment_status=2%20and%20express_status=2%20and%20status=2%20and%20datatype=1";
                    //				list.clear();
                    load_list(true, strwhere);
                } else if (status.equals("4")) {
                    item5();
                    strwhere = "payment_status%3d2+and+express_status+%3e+1+and+status%3d3+and+datatype%3d1";
                    load_list(true, strwhere);
                }
            } else {
                load_list(true, strwhere);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    //当Activity被销毁时会调用onDestory方法
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            //        activity = null;
            BitmapDrawable bd = (BitmapDrawable) imageView1.getBackground();
            imageView1.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
            bd.setCallback(null);
            bd.getBitmap().recycle();

            if (MyOrderllAdapter.type == true) {
                MyOrderllAdapter.mAq.clear();
                MyOrderllAdapter.mAq.recycle(my_list);
                MyOrderllAdapter.type = false;
            }


            if (list.size() > 0) {
                list.clear();
                list = null;
            }

            //使用LeakCanary观察是否有内存泄漏
            //        MyApplication.getRefWatcher().watch(this);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 控件初始化
     */
    private void Initialize() {

        try {
            imageView1 = (ImageView) findViewById(R.id.iv_no_data);
            Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.no_data);
            BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
            imageView1.setBackgroundDrawable(bd);

            refresh = (PullToRefreshView) findViewById(R.id.refresh);
            refresh.setOnHeaderRefreshListener(listHeadListener);
            refresh.setOnFooterRefreshListener(listFootListener);
            my_list = (ListView) findViewById(R.id.new_list);
            no_data_no = (LinearLayout) findViewById(R.id.no_data_no);
            index_item1 = (LinearLayout) findViewById(R.id.index_item1);
            index_item2 = (LinearLayout) findViewById(R.id.index_item2);
            index_item3 = (LinearLayout) findViewById(R.id.index_item3);
            index_item4 = (LinearLayout) findViewById(R.id.index_item4);
            cursor2 = (ImageView) findViewById(R.id.cursor2);
            cursor3 = (ImageView) findViewById(R.id.cursor3);
            cursor4 = (ImageView) findViewById(R.id.cursor4);
            cursor5 = (ImageView) findViewById(R.id.cursor5);
            index_item1.setOnClickListener(this);
            index_item2.setOnClickListener(this);
            index_item3.setOnClickListener(this);
            index_item4.setOnClickListener(this);

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

            case R.id.index_item1:
                item2();
                list = new ArrayList<MyOrderData>();
                System.out.println("list状态==============" + list.size());
                strwhere = "payment_status=1%20and%20datatype=1";
                System.out.println("=========22============" + strwhere);
                load_list(true, strwhere);
                break;
            case R.id.index_item2:
                item3();
                list = new ArrayList<MyOrderData>();
                System.out.println("list状态==============" + list.size());
                strwhere = "payment_status=2%20and%20express_status=1%20and%20status=2%20and%20datatype=1";
                System.out.println("=========33============" + strwhere);
                load_list(true, strwhere);
                break;
            case R.id.index_item3:
                item4();
                list = new ArrayList<MyOrderData>();
                System.out.println("list状态==============" + list.size());
                strwhere = "payment_status=2%20and%20express_status=2%20and%20status=2%20and%20datatype=1";
                System.out.println("=========55============" + strwhere);
                load_list(true, strwhere);
                break;
            case R.id.index_item4:
                item5();
                list = new ArrayList<MyOrderData>();
                System.out.println("list状态==============" + list.size());
                strwhere = "payment_status%3d2+and+express_status+%3e+1+and+status%3d3+and+datatype%3d1";
                System.out.println("=========66============" + strwhere);
                load_list(true, strwhere);
                break;

            default:
                break;
        }
    }

    private void item1() {
        cursor2.setVisibility(View.INVISIBLE);
        cursor3.setVisibility(View.INVISIBLE);
        cursor4.setVisibility(View.INVISIBLE);
        cursor5.setVisibility(View.INVISIBLE);
    }

    private void item2() {
        cursor2.setVisibility(View.VISIBLE);
        cursor3.setVisibility(View.INVISIBLE);
        cursor4.setVisibility(View.INVISIBLE);
        cursor5.setVisibility(View.INVISIBLE);
    }

    private void item3() {
        cursor2.setVisibility(View.INVISIBLE);
        cursor3.setVisibility(View.VISIBLE);
        cursor4.setVisibility(View.INVISIBLE);
        cursor5.setVisibility(View.INVISIBLE);
    }

    private void item4() {
        cursor2.setVisibility(View.INVISIBLE);
        cursor3.setVisibility(View.INVISIBLE);
        cursor4.setVisibility(View.VISIBLE);
        cursor5.setVisibility(View.INVISIBLE);
    }

    private void item5() {
        cursor2.setVisibility(View.INVISIBLE);
        cursor3.setVisibility(View.INVISIBLE);
        cursor4.setVisibility(View.INVISIBLE);
        cursor5.setVisibility(View.VISIBLE);
    }


    /**
     * 上拉列表刷新加载
     */
    private PullToRefreshView.OnHeaderRefreshListener listHeadListener = new PullToRefreshView.OnHeaderRefreshListener() {

        @Override
        public void onHeaderRefresh(PullToRefreshView view) {

            refresh.postDelayed(new Runnable() {

                @Override
                public void run() {
                    refresh.onHeaderRefreshComplete();
                }
            }, Toast.LENGTH_SHORT);
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
                        //						if (RUN_METHOD == 0) {
                        //							System.out.println("RUN_METHOD1========="+ RUN_METHOD);
                        //							load_list2(false);
                        //						} else {
                        System.out.println("strwhere=========" + strwhere);

                        load_list(false, strwhere);
                        //						}
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

    private void load_list(boolean flag, String strwhere) {
        progress.CreateProgress();
        RUN_METHOD = 1;
        if (flag) {
            // 计数和容器清零
            CURRENT_NUM = 1;
            list = new ArrayList<MyOrderData>();
            //			System.out.println("=========list11============"+list.size());
        }
        System.out.println("====1=====list============" + list.size());//5897
        System.out.println("=========strwhere============" + strwhere);//5897

        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_order_page_size_list?user_id=" + user_id + "" +
                        "&page_size=" + VIEW_NUM + "&page_index=" + CURRENT_NUM + "&strwhere=" + strwhere + "&orderby=",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        System.out.println("=========数据接口============" + arg1);
                        try {

                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                JSONArray jsonArray = object.getJSONArray("data");
                                len = jsonArray.length();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    md = new MyOrderData();
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    md.setId(obj.getString("id"));
                                    md.setAccept_no(obj.getString("accept_no"));
                                    md.setOrder_no(obj.getString("order_no"));
                                    md.setTrade_no(obj.getString("trade_no"));
                                    md.setCompany_name(obj.getString("company_name"));
                                    md.setAccept_name(obj.getString("accept_name"));
                                    md.setPayment_status(obj.getString("payment_status"));
                                    md.setExpress_status(obj.getString("express_status"));
                                    md.setExpress_fee(obj.getString("express_fee"));
                                    md.setStatus(obj.getString("status"));
                                    md.setProvince(obj.getString("province"));
                                    md.setCashing_packet(obj.getString("cashing_packet_total"));
                                    md.setAddress(obj.getString("address"));
                                    md.setUser_name(obj.getString("user_name"));
                                    md.setPayment_time(obj.getString("payment_time"));//支付时间
                                    md.setPayable_amount(obj.getString("payable_amount"));
                                    md.setAdd_time(obj.getString("add_time"));//创建时间
                                    md.setComplete_time(obj.getString("complete_time"));//完成时间
                                    md.setRebate_time(obj.getString("rebate_time"));
                                    md.setExpress_no(obj.getString("express_no"));
                                    md.setCity(obj.getString("city"));
                                    md.setArea(obj.getString("area"));
                                    md.setMobile(obj.getString("mobile"));
                                    //								String getPayable_amount  = md.getPayable_amount();
                                    //								System.out.println("============="+getPayable_amount);
                                    String order_goods = obj.getString("order_goods");
                                    System.out.println("=====order_goods=====================" + order_goods);
                                    md.setList(new ArrayList<OrderBean>());
                                    JSONArray ja = new JSONArray(order_goods);
                                    List<OrderBean> lists = new ArrayList<OrderBean>();
                                    for (int j = 0; j < ja.length(); j++) {
                                        mb = new OrderBean();
                                        JSONObject jo = ja.getJSONObject(j);
                                        mb.setImg_url(jo.getString("img_url"));
                                        mb.setArticle_title(jo.getString("article_title"));
                                        mb.setSell_price(jo.getString("sell_price"));
                                        mb.setMarket_price(jo.getString("market_price"));
                                        mb.setReal_price(jo.getString("real_price"));
                                        mb.setQuantity(jo.getInt("quantity"));
                                        mb.setArticle_id(jo.getString("article_id"));
                                        mb.setCashing_packet(jo.getString("cashing_packet"));
                                        String zhouString = mb.getArticle_title();
                                        System.out.println("article_title=============" + zhouString);
                                        md.getList().add(mb);
                                        lists.add(mb);
                                    }
                                    list.add(md);
                                }

                                md = null;
                                mb = null;
                                //								progress.CloseProgress();
                                no_data_no.setVisibility(View.GONE);
                            } else {
                                //									System.out.println("====list.size()========="+list.size());
                                progress.CloseProgress();
                                if (list.size() == 0) {
                                    no_data_no.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(MyOrderActivity.this, "没有订单了", Toast.LENGTH_SHORT).show();
                                }
                            }

                            System.out.println("==2==list.size()=========" + list.size());

                            if (len != 0) {
                                CURRENT_NUM = CURRENT_NUM + 1;
                            }
                            progress.CloseProgress();

                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = list;
                            handler.sendMessage(msg);
                            //								handler.sendEmptyMessage(0);
                            progress.CloseProgress();

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }

                }, MyOrderActivity.this);
    }

    Handler handler = new Handler() {
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //			list = (ArrayList<MyOrderData>) msg.obj;
                    madapter.putData(list);
                    if (list.size() > 0) {
                        MyOrderllAdapter.mAq.clear();
                    }
                    //			madapter.notifyDataSetChanged();
                    //			madapter = new MyOrderllAdapter(list,MyOrderActivity.this,handler);
                    //			my_list.setAdapter(madapter);
                    //			progress.CloseProgress();
                    break;
                case 1:
                    order_no = (String) msg.obj;
                    dialog1();
                    break;
                case 2:
                    order_no = (String) msg.obj;
                    dialog2();
                    break;
                case 3:
                    order_no = (String) msg.obj;
                    dialog3();
                    break;
                case 4:
                    order_no = (String) msg.obj;

                    //				  Intent intent = new Intent(MyOrderActivity.this, MyOrderZFActivity.class);
                    //				  intent.putExtra("order_no",order_no);
                    //				  startActivity(intent);

                    //					CommomConfrim.showSheet(MyOrderActivity.this,new onDeleteSelect() {
                    //
                    //								@Override
                    //								public void onClick(int resID) {
                    //
                    //									switch (resID) {
                    //									case R.id.item0:
                    //										// 余额支付
                    //										break;
                    //									case R.id.item1:
                    //										break;
                    //									case R.id.item2:// 支付宝
                    //										loadzhidu(order_no);
                    //										break;
                    //									case R.id.item3:// 微信
                    //										break;
                    //									case R.id.item4:
                    //
                    //										break;
                    //									default:
                    //										break;
                    //									}
                    //								}
                    //
                    //							}, cancelListener, null);

                    break;
                case 5://支付宝
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();
                    System.out.println(resultInfo + "---" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(MyOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        userloginqm();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MyOrderActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(MyOrderActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();


                        }
                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };

    OnCancelListener cancelListener = new OnCancelListener() {

        @Override
        public void onCancel(DialogInterface dialog) {
        }
    };

    /**
     * 申请退款
     */
    protected void dialog1() {
        AlertDialog.Builder builder = new Builder(MyOrderActivity.this);
        builder.setMessage("是否确定申请退款?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //				fukuanok2(order_no);
                type = "1";
                userloginqm();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    protected void dialog2() {
        AlertDialog.Builder builder = new Builder(MyOrderActivity.this);
        builder.setMessage("是否确定取消订单?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                fukuanok2(order_no);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    protected void dialog3() {
        AlertDialog.Builder builder = new Builder(MyOrderActivity.this);
        builder.setMessage("是否确定删除订单?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                fukuanok3(order_no);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }


    /**
     * 确认付款
     *
     * @param
     * @param
     */
    public void fukuanok(String order_no2) {
        progress.CreateProgress();
        order_no = order_no2;
        System.out.println("order_no=================================" + order_no);
        String login_sign = spPreferences.getString("login_sign", "");
        System.out.println("login_sign=================================" + login_sign);
        AsyncHttp.get(URLConstants.REALM_NAME_LL
                        + "/update_order_payment?user_id=" + user_id + "&user_name=" + user_name + "" +
                        "&trade_no=" + order_no + "&sign=" + login_sign + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            System.out.println("确认付款=================================" + arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                progress.CloseProgress();
                                Toast.makeText(MyOrderActivity.this, info, Toast.LENGTH_SHORT).show();
                            } else {
                                progress.CloseProgress();
                                Toast.makeText(MyOrderActivity.this, info, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, MyOrderActivity.this);

    }

    /**
     * 取消订单
     *
     * @param
     * @param
     */
    public void fukuanok2(String order_no2) {
        progress.CreateProgress();
        order_no = order_no2;
        System.out.println("order_no=================================" + order_no);
        String login_sign = spPreferences.getString("login_sign", "");
        System.out.println("login_sign=================================" + login_sign);
        AsyncHttp.get(URLConstants.REALM_NAME_LL
                        + "/update_order_cancel?user_id=" + user_id + "&user_name=" + user_name + "" +
                        "&trade_no=" + order_no + "&sign=" + login_sign + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            System.out.println("取消订单=================================" + arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                progress.CloseProgress();
                                Toast.makeText(MyOrderActivity.this, info, Toast.LENGTH_SHORT).show();
                                load_list(true, strwhere);
                                madapter.notifyDataSetChanged();
                            } else {
                                progress.CloseProgress();
                                Toast.makeText(MyOrderActivity.this, info, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, MyOrderActivity.this);

    }

    /**
     * 删除订单
     *
     * @param
     * @param
     */
    public void fukuanok3(String order_no2) {
        progress.CreateProgress();
        order_no = order_no2;
        System.out.println("order_no=================================" + order_no);
        String login_sign = spPreferences.getString("login_sign", "");
        System.out.println("login_sign=================================" + login_sign);
        AsyncHttp.get(URLConstants.REALM_NAME_LL
                        + "/delete_order?user_id=" + user_id + "&user_name=" + user_name + "" +
                        "&trade_no=" + order_no + "&sign=" + login_sign + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            System.out.println("取消订单=================================" + arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                progress.CloseProgress();
                                Toast.makeText(MyOrderActivity.this, info, Toast.LENGTH_SHORT).show();
                                load_list(true, strwhere);
                                madapter.notifyDataSetChanged();
                            } else {
                                progress.CloseProgress();
                                Toast.makeText(MyOrderActivity.this, info, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, MyOrderActivity.this);

    }


    /**
     * 获取登录签名
     */
    private void userloginqm() {
        try {
            SharedPreferences spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
            String user_name = spPreferences.getString(SpConstants.USER_NAME, "");
            String strUrlone = URLConstants.REALM_NAME_LL + "/get_user_model?username=" + user_name + "";
            System.out.println("======11=============" + strUrlone);
            AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    try {
                        JSONObject object = new JSONObject(arg1);
                        String status = object.getString("status");
                        JSONObject obj = object.getJSONObject("data");
                        if (status.equals("y")) {
                            UserRegisterllData data = new UserRegisterllData();
                            data.login_sign = obj.getString("login_sign");
                            login_sign = data.login_sign;
                            System.out.println("======order_no=============" + order_no);
                            if (type.equals("1")) {
                                getKuiKuan(login_sign, order_no);
                            } else {
                                loadguanggaoll(order_no, login_sign);
                            }
                        } else {
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                ;
            }, MyOrderActivity.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 退款
     *
     * @param login_sign
     * @param order_no
     */
    private void getKuiKuan(String login_sign, String order_no) {
        try {
            SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
            String user_id = sp.getString(SpConstants.USER_ID, "");
            String strUrlone = URLConstants.REALM_NAME_LL + "/order_refund?user_id=" + user_id + "&trade_no=" + order_no + "&sign=" + login_sign + "";
            System.out.println("======11=============" + strUrlone);
            AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    try {
                        System.out.println("======arg1=============" + arg1);
                        JSONObject object = new JSONObject(arg1);
                        String status = object.getString("status");
                        String info = object.getString("info");
                        if (status.equals("y")) {
                            Toast.makeText(MyOrderActivity.this, info, Toast.LENGTH_SHORT).show();
                            load_list(true, strwhere);
                        } else {
                            Toast.makeText(MyOrderActivity.this, info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                ;
            }, MyOrderActivity.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 更新订单
     *
     * @param login_sign
     * @param
     */
    private void loadguanggaoll(String recharge_noll, String login_sign) {
        try {
            //			recharge_no = recharge_noll;
            System.out.println("recharge_no=================================" + recharge_noll);
            System.out.println("login_sign=================================" + login_sign);
            AsyncHttp.get(URLConstants.REALM_NAME_LL
                            + "/update_order_payment?user_id=" + user_id + "&user_name=" + user_name + "" +
                            "&trade_no=" + recharge_noll + "&sign=" + login_sign + "",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("更新订单=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    teby = false;
                                    //							    	   finish();
                                    Toast.makeText(MyOrderActivity.this, info, Toast.LENGTH_SHORT).show();
                                } else {
                                    progress.CloseProgress();
                                    teby = false;
                                    Toast.makeText(MyOrderActivity.this, info, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            super.onFailure(arg0, arg1);
                            System.out.println("11=================================" + arg0);
                            System.out.println("22=================================" + arg1);
                            Toast.makeText(MyOrderActivity.this, "更新订单网络超时异常", Toast.LENGTH_SHORT).show();
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


}
