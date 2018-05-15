package com.yunsen.enjoy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.JuJingCaiXqActivity;
import com.yunsen.enjoy.activity.mine.JuTuanConfrimActivity;
import com.yunsen.enjoy.activity.mine.JuTuanGouXqActivity;
import com.yunsen.enjoy.activity.mine.adapter.JuTuanAdapter;
import com.yunsen.enjoy.activity.mine.adapter.ZhongAnYlAdapter;
import com.yunsen.enjoy.activity.user.CanTuanFengXiangActivity;
import com.yunsen.enjoy.activity.user.DBFengXiangActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.JuTuanGouData;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.InScrollListView;
import com.yunsen.enjoy.widget.MyCountdownTimer;
import com.yunsen.enjoy.widget.MyGridView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 云商聚
 *
 * @author Administrator
 */
public class JuTuanGouXq2Activity extends AppCompatActivity implements OnClickListener {

    private ImageView iv_img;
    private DialogProgress progress;
    private SharedPreferences spPreferences;
    private SharedPreferences spPreferences_user;
    private ListView listview_01;
    private ArrayList<JuTuanGouData> list = null;
    private ArrayList<JuTuanGouData> list_ll = null;
    private ArrayList<JuTuanGouData> list_cy;
    List<Integer> list_num;
    private ZhongAnYlAdapter zaylaAdapter;
    private TextView tv_titel, tv_price, tv_jutuan_price, tv_anniu1, tv_tuangoujia, tv_tuanshu;
    JuTuanGouData data;
    JuTuanGouData bean;
    private GridView gridView;
    private ListView new_list, list_tuanjia;
    //	private ListView new_list;
    public static boolean fanhui_type = false;
    JuTuanAdapter arrayadapter;
    private WebView webview;
    private String item_id, goumai_id, tuangoujia;
    String user_id, login_sign,  choujiang, sp_id, ct_id, groupon_no;
    String province, city, area, user_address, user_mobile, name, shopping_address_id;
    private InScrollListView list_shop_cart;
    String zhuangtai = "100";
    //	TuanchengyuanAdapter adapter;
    //	private MyGridView myGridView;
    private MyGridView gridView2;
    //	private MyGridView gridView_tx;
    View iv_view;
    String type;
    String zhifu_wx = "1";
    public static String datetime, end_time, timer_time;
    public static String foreman_id, foreman_name, tuan_id;
    public static String ct_tuanshu, people, share_img_url;
    public AQuery mAq;
    private TextView txt_time, tv_tuan, tv_kaituan;
    private LinearLayout ll_kaituan, ll_qu_kaituan, ll_jutuanjia, ll_jiaguo;
    public static long day;
    String stare, orders_no, fx_cs;
    private long hour = 0;//时间变量
    private long minute = 0;
    private long second = 0;
    private long time = 0;//毫秒为单位
    private long current_time = 0;
    private long interval = 10 * 1000;//每次增加或减少的额度为10秒
    private MyCount count;//定时类对象
    java.util.Date now;
    java.util.Date date;
    long hourl, min, s, zongxs;
    LinearLayout ll_buju;
    String fx_shuzi;
    public static Handler handlerll;
    String user_name = "";
    String user_name_3 = "";
    String weixin = "";
    String qq = "";
    String user_name_3_wx = "";
    String user_name_3_qq = "";
    String user_name_key = "";

    public static int fangshi = 0;

    @Override
    public void onResume() {

        super.onResume();
        //			progress.CloseProgress();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jutuangou_xq_2);//activity_jutuangou_xq_2 activity_jutuangou_title2
        progress = new DialogProgress(JuTuanGouXq2Activity.this);
        fx_shuzi = getIntent().getStringExtra("fx_shuzi");
        System.out.println("=======fx_shuzi===========1=====================" + fx_shuzi);
        System.out.println("=======JuJingCaiXqActivity.fx_canshu==================" + JuJingCaiXqActivity.fx_canshu);
        System.out.println("=======JuTuanGouXqActivity.fx_canshu================" + JuTuanGouXqActivity.fx_canshu);
        if (!JuJingCaiXqActivity.fx_canshu.equals("")) {
            fx_cs = JuJingCaiXqActivity.fx_canshu;
            //			JuJingCaiXqActivity.fx_canshu = "";
        } else {
            fx_cs = JuTuanGouXqActivity.fx_canshu;
            //			JuTuanGouXqActivity.fx_canshu = "";
        }
        System.out.println("=======fx_cs================================" + fx_cs);

        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name = spPreferences.getString("user", "");
        user_id = spPreferences.getString("user_id", "");
        login_sign = spPreferences.getString(SpConstants.LOGIN_SIGN, "");

        fanhui_type = true;
        try {

            handlerll = new Handler() {
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 2:
                            finish();
                            break;
                    }
                }
            };

            //		choujiang = getIntent().getStringExtra("choujiang");
            //		System.out.println("choujiang===================="+choujiang);

            mAq = new AQuery(this);
            intren();

            String orders_no_ll = getIntent().getStringExtra("trade_no");
            System.out.println("orders_no_ll====================" + orders_no_ll);
            if (orders_no_ll != null) {
                orders_no = orders_no_ll;
                //			loadzhonganyl(orders_no);
                //			loadcantuan(orders_no);
                //			loadWeatherxq(orders_no);
                getjutuanuser(orders_no);
            } else {
                SharedPreferences spPreferences = getSharedPreferences("longuserset_id", Context.MODE_PRIVATE);
                String ct_order_no = spPreferences.getString("ct_order_no", "");
                orders_no = ct_order_no;
                System.out.println("orders_no=========22===========" + orders_no);
                //			loadzhongany3(orders_no);
                System.out.println("jiekou====================" + getIntent().getStringExtra("jiekou"));
                if (getIntent().getStringExtra("jiekou").equals("1")) {
                    loadWeatherxq(orders_no);
                } else {
                    getjutuanxq(orders_no);
                }
            }
            System.out.println("orders_no====================" + orders_no);

            //		orders_no = "G170422134249424918";
            //		loadzhonganyl(orders_no);

        } catch (Exception e) {

            e.printStackTrace();
        }

        try {

            //		Button fanhui = (Button) findViewById(R.id.fanhui);
            //		fanhui.setOnClickListener(this);

            ImageView img_shared = (ImageView) findViewById(R.id.img_shared);
            img_shared.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    try {
                        if (user_name.equals("")) {
                            Intent intent = new Intent(JuTuanGouXq2Activity.this, UserLoginActivity.class);
                            startActivity(intent);
                        } else {
                            if (UserLoginActivity.wx_fanhui == false) {
                                Intent intent5 = new Intent(JuTuanGouXq2Activity.this, UserLoginActivity.class);
                                startActivity(intent5);
                            } else {
                                Intent intentll = new Intent(JuTuanGouXq2Activity.this, DBFengXiangActivity.class);
                                intentll.putExtra("ct_id", ct_id);
                                intentll.putExtra("title", data.getTitle());
                                intentll.putExtra("company_id", data.getCompany_id());
                                //						intentll.putExtra("img_url", share_img_url);
                                intentll.putExtra("subtitle", data.getSubtitle());
                                intentll.putExtra("img_url", "");
                                intentll.putExtra("fx_shuzi", fx_cs);
                                startActivity(intentll);
                            }
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 控件初始化
     */
    public void intren() {
        try {
            list_tuanjia = (ListView) findViewById(R.id.list_tuanjia);
            gridView2 = (MyGridView) findViewById(R.id.gridview2);
            gridView = (GridView) findViewById(R.id.gridView);
            iv_img = (ImageView) findViewById(R.id.img_tp);
            tv_titel = (TextView) findViewById(R.id.tv_titel);
            tv_price = (TextView) findViewById(R.id.tv_price);
            tv_jutuan_price = (TextView) findViewById(R.id.tv_jutuan_price);
            tv_anniu1 = (TextView) findViewById(R.id.tv_anniu1);
            txt_time = (TextView) findViewById(R.id.tvshowtime);
            tv_tuan = (TextView) findViewById(R.id.tv_tuanshu);
            tv_kaituan = (TextView) findViewById(R.id.tv_kaituan);
            ll_kaituan = (LinearLayout) findViewById(R.id.ll_kaituan);
            ll_qu_kaituan = (LinearLayout) findViewById(R.id.ll_qu_kaituan);
            ll_jutuanjia = (LinearLayout) findViewById(R.id.ll_jutuanjia);
            ll_jiaguo = (LinearLayout) findViewById(R.id.ll_jiaguo);
            iv_view = findViewById(R.id.iv_view);

            //		myGridView = (MyGridView) findViewById(R.id.mGv);
            //		gridView_tx = (MyGridView) findViewById(R.id.gridView_tx);
            tv_anniu1.setOnClickListener(this);

            ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
            iv_fanhui.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    finish();
                    String type = getIntent().getStringExtra("type");//聚精彩状态
                    System.out.println("type-------------" + type);
                    //				Intent intent = new Intent(JuTuanGouXq2Activity.this,JuTuanGouXqActivity.class);
                    //				intent.putExtra("type",type);//聚精彩状态
                    //				startActivity(intent);
                }
            });

            webview = (WebView) findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.addJavascriptInterface(new JavascriptHandler(), "handler");
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                }
            });


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    class JavascriptHandler {
        @JavascriptInterface
        public void getContent(String htmlContent) {
        }
    }

    Handler handler = new Handler() {
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    final ArrayList<JuTuanGouData> carts = (ArrayList<JuTuanGouData>) msg.obj;
                    tuangoujia = JuTuanAdapter.tuangoujia;
                    //				String tuanshu = JuTuanAdapter.tuanshu;
                    //				String tuangoujia = (String) msg.obj;
                    //				tv_tuangoujia.setText("￥"+tuangoujia);
                    //				tv_tuanshu.setText(tuanshu+"人团数");
                    break;
                case 1:
                    try {

                        System.out.println("list个数是多少====================" + list.size());
                        arrayadapter = new JuTuanAdapter(list, getApplicationContext(), handler);
                        list_tuanjia.setAdapter(arrayadapter);
                        setListViewHeightBasedOnChildren(list_tuanjia);
                        list_tuanjia.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                //		            	 flag = false;
                                //		            	 String id = listll.get(arg2).getId();
                                //		            	 System.out.println("=====第二层数据1====================="+INDX);
                                arrayadapter.setSeclection(arg2);
                                arrayadapter.notifyDataSetChanged();
                            }
                        });
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    break;
                case 2:
                    //				adapter = new TuanchengyuanAdapter(list_cy, JuTuanGouXq2Activity.this);
                    //				gridView2.setAdapter(adapter);
                    break;
                case 3:
                    break;
                case 5:
                    break;
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 输出成员订单列表
     *
     * @param trade_no
     */
    private void getjutuanuser(String trade_no) {
        list = new ArrayList<JuTuanGouData>();
        list_ll = new ArrayList<JuTuanGouData>();
        progress.CreateProgress();
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_article_member_list?trade_no=" + trade_no + "&top=10"
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        System.out.println("输出内容详情=========" + arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            datetime = object.getString("datetime");
                            if (status.equals("y")) {
                                //						JSONObject jsonobt = object.getJSONObject("data");
                                JSONArray jsot_ll = object.getJSONArray("data");
                                for (int k = 0; k < jsot_ll.length(); k++) {
                                    JSONObject obj1 = jsot_ll.getJSONObject(k);
                                    data = new JuTuanGouData();
                                    data.setOrder_no(obj1.getString("order_no"));
                                    data.setTrade_no(obj1.getString("trade_no"));
                                    data.setCompany_id(obj1.getString("company_id"));
                                    JSONArray jsot1 = obj1.getJSONArray("order_goods");
                                    for (int q = 0; q < jsot1.length(); q++) {
                                        JSONObject jsont = jsot1.getJSONObject(q);
                                        //							data = new JuTuanGouData();
                                        //							data.setId(jsont.getString("id"));
                                        data.setArticle_title(jsont.getString("article_title"));
                                        data.setImg_url(jsont.getString("img_url"));
                                        data.setArticle_id(jsont.getString("article_id"));
                                        data.setOrder_id(jsont.getString("order_id"));
                                        data.setGoods_id(jsont.getString("goods_id"));
                                        data.setQuantity(jsont.getString("quantity"));
                                        data.setSpec_text(jsont.getString("spec_text"));
                                        ////						data.setShare_img_url(jsont.getString("share_img_url"));
                                        data.setForeman_id(jsont.getString("foreman_id"));
                                        data.setForeman_name(jsont.getString("foreman_name"));
                                        data.setTimer_time(jsont.getString("timer_time"));
                                        data.setEnd_time(jsont.getString("end_time"));
                                        data.setStart_time(jsont.getString("start_time"));
                                        data.setActivity_people(jsont.getInt("activity_people"));
                                        data.setActivity_member(jsont.getInt("activity_member"));
                                        data.setActivity_price(jsont.getString("activity_price"));
                                        data.setSell_price(jsont.getString("sell_price"));
                                        data.setMarket_price(jsont.getString("market_price"));
                                        data.setTimer_time(jsont.getString("timer_time"));
                                        data.setEnd_time(jsont.getString("end_time"));
                                        data.setStart_time(jsont.getString("start_time"));
                                        //							data.setGroupon_no(jsont.getString("groupon_no"));
                                        //							data.setGroupon_item_id(jsont.getString("groupon_item_id"));
                                        ////						data.setOrder_no(jsont.getString("order_no"));
                                        //				    		end_time = data.getEnd_time();
                                        foreman_id = data.getForeman_id();
                                        foreman_name = data.getForeman_name();
                                        //							share_img_url = data.getShare_img_url();
                                        timer_time = data.getTimer_time();
                                        ct_id = data.getOrder_id();
                                        //							groupon_no = data.getGroupon_no();
                                        //							System.out.println("=======groupon_no================================"+groupon_no);
                                        //							groupon_item_people = data.getGroupon_item_people();
                                        ct_tuanshu = String.valueOf(data.getActivity_people() - data.getActivity_member());
                                        list_ll.add(data);
                                        //							gettime();//开团获取倒计时
                                    }
                                }

                            } else {
                                Toast.makeText(JuTuanGouXq2Activity.this, info, Toast.LENGTH_SHORT).show();
                            }
                            //					System.out.println("=====list_ll.size()====================="+list_ll.size());
                            intrendata();
                            //					handler.sendEmptyMessage(1);
                            getkaituan();
                            gettime();//开团获取倒计时
                            webview.loadUrl(URLConstants.REALM_NAME_HTTP + "/mobile/goods/conent-" + data.getArticle_id() + ".html");//商品介绍
                            progress.CloseProgress();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                }, null);
    }

    /**
     * 输出团长订单列表(自己的团)
     *
     * @param groupon_id
     * @param category_id
     */
    public static String img_url, title, sell_price, article_id, goods_id, price;

    private void loadWeatherxq(String trade_no) {
        list = new ArrayList<JuTuanGouData>();
        list_ll = new ArrayList<JuTuanGouData>();
        progress.CreateProgress();
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_order_myselft_list?user_id=" + user_id + "&user_name=" + user_name + "&trade_no=" + trade_no + "&top=1"
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        System.out.println("输出内容详情=========" + arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            datetime = object.getString("datetime");
                            if (status.equals("y")) {
                                JSONObject jsonobt = object.getJSONObject("data");
                                JSONObject obj = jsonobt.getJSONObject("article_model");
                                data = new JuTuanGouData();
                                data.setId(obj.getString("id"));
                                data.setTitle(obj.getString("title"));
                                data.setImg_url(obj.getString("img_url"));
                                data.setAdd_time(obj.getString("add_time"));
                                data.setStart_time(obj.getString("start_time"));
                                data.setUpdate_time(obj.getString("update_time"));
                                data.setCategory_id(obj.getString("category_id"));
                                data.setEnd_time(obj.getString("end_time"));
                                data.setSubtitle(obj.getString("subtitle"));
                                data.setImgs_url(obj.getString("imgs_url"));
                                data.setCompany_id(obj.getString("company_id"));
                                item_id = data.getId();
                                //							img_url = data.img_url;
                                //							title = data.title;
                                JSONObject jsot = obj.getJSONObject("default_spec_item");
                                data.setGoods_id(jsot.getString("goods_id"));
                                data.setArticle_id(jsot.getString("article_id"));
                                data.setSell_price(jsot.getString("sell_price"));
                                //							sell_price = data.sell_price;
                                //							article_id = data.article_id;
                                //							goods_id = data.goods_id;
                                JSONObject jsoct = jsot.getJSONObject("default_activity_price");
                                data.setPeople(jsoct.getString("people"));
                                data.setPrice(jsoct.getString("price"));
                                //							price = data.price;
                                JSONArray jsonArray = jsot.getJSONArray("activity_price");
                                for (int k = 0; k < jsonArray.length(); k++) {
                                    JSONObject objet2 = jsonArray.getJSONObject(k);
                                    JuTuanGouData data = new JuTuanGouData();
                                    data.setGoods_id(objet2.getString("goods_id"));
                                    data.setPeople(objet2.getString("people"));
                                    data.setPrice(objet2.getString("price"));
                                    list.add(data);
                                }

                                //						JSONObject jsonobt = object.getJSONObject("data");
                                JSONArray jsot_ll = jsonobt.getJSONArray("foreman_list");
                                for (int k = 0; k < jsot_ll.length(); k++) {
                                    JSONObject obj1 = jsot_ll.getJSONObject(k);
                                    data = new JuTuanGouData();
                                    //						data.setOrder_no(obj1.getString("order_no"));
                                    //						data.setOrder_no(obj1.getString("order_no"));
                                    data.setCompany_id(obj1.getString("company_id"));
                                    JSONArray jsot1 = obj1.getJSONArray("order_goods");
                                    for (int q = 0; q < jsot1.length(); q++) {
                                        JSONObject jsont = jsot1.getJSONObject(q);
                                        //							data = new JuTuanGouData();
                                        data.setArticle_id(jsont.getString("article_id"));
                                        data.setOrder_id(jsont.getString("order_id"));
                                        data.setGoods_id(jsont.getString("goods_id"));
                                        data.setQuantity(jsont.getString("quantity"));

                                        ////						data.setShare_img_url(jsont.getString("share_img_url"));
                                        data.setArticle_title(jsont.getString("article_title"));
                                        data.setImg_url(jsont.getString("img_url"));
                                        data.setForeman_id(jsont.getString("foreman_id"));
                                        data.setForeman_name(jsont.getString("foreman_name"));
                                        data.setTimer_time(jsont.getString("timer_time"));
                                        data.setEnd_time(jsont.getString("end_time"));
                                        data.setStart_time(jsont.getString("start_time"));
                                        data.setActivity_people(jsont.getInt("activity_people"));
                                        data.setActivity_member(jsont.getInt("activity_member"));
                                        data.setActivity_price(jsont.getString("activity_price"));
                                        data.setSell_price(jsont.getString("sell_price"));
                                        data.setMarket_price(jsont.getString("market_price"));
                                        //							data.setGroupon_no(jsont.getString("groupon_no"));
                                        //							data.setGroupon_item_id(jsont.getString("groupon_item_id"));
                                        ////						data.setOrder_no(jsont.getString("order_no"));
                                        //				    		end_time = data.getEnd_time();
                                        foreman_id = data.getForeman_id();
                                        foreman_name = data.getForeman_name();
                                        //							share_img_url = data.getShare_img_url();
                                        timer_time = data.getTimer_time();
                                        ct_id = data.getOrder_id();
                                        ct_tuanshu = String.valueOf(data.getActivity_people() - data.getActivity_member());
                                        //							list_ll.add(data);
                                        gettime();//开团获取倒计时
                                    }
                                }

                            } else {
                                Toast.makeText(JuTuanGouXq2Activity.this, info, Toast.LENGTH_SHORT).show();
                            }
                            System.out.println("=====list.size()=====================" + list.size());
                            System.out.println("=====list_ll.size()=====================" + list_ll.size());
                            intrendata();
                            handler.sendEmptyMessage(1);
                            getkaituan();
                            //					gettime();//开团获取倒计时
                            webview.loadUrl(URLConstants.REALM_NAME_HTTP + "/mobile/goods/conent-" + data.getArticle_id() + ".html");//商品介绍
                            progress.CloseProgress();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                }, null);
    }

    /**
     * 输出团长订单列表
     *
     * @param
     * @param
     */
    //	public static String img_url,title,sell_price,article_id,goods_id,price;
    private void getjutuanxq(String trade_no) {
        list = new ArrayList<JuTuanGouData>();
        list_ll = new ArrayList<JuTuanGouData>();
        progress.CreateProgress();
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_order_foreman_list?trade_no=" + trade_no + "&top=1"
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        System.out.println("输出内容详情=========" + arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            datetime = object.getString("datetime");
                            if (status.equals("y")) {
                                JSONObject jsonobt = object.getJSONObject("data");
                                JSONObject obj = jsonobt.getJSONObject("article_model");
                                data = new JuTuanGouData();
                                data.setId(obj.getString("id"));
                                data.setTitle(obj.getString("title"));
                                data.setImg_url(obj.getString("img_url"));
                                data.setAdd_time(obj.getString("add_time"));
                                data.setStart_time(obj.getString("start_time"));
                                data.setUpdate_time(obj.getString("update_time"));
                                data.setCategory_id(obj.getString("category_id"));
                                data.setEnd_time(obj.getString("end_time"));
                                data.setSubtitle(obj.getString("subtitle"));
                                data.setImgs_url(obj.getString("imgs_url"));
                                data.setCompany_id(obj.getString("company_id"));
                                item_id = data.getId();
                                //							img_url = data.img_url;
                                //							title = data.title;
                                JSONObject jsot = obj.getJSONObject("default_spec_item");
                                data.setGoods_id(jsot.getString("goods_id"));
                                data.setArticle_id(jsot.getString("article_id"));
                                data.setSell_price(jsot.getString("sell_price"));
                                //							sell_price = data.sell_price;
                                //							article_id = data.article_id;
                                //							goods_id = data.goods_id;
                                JSONObject jsoct = jsot.getJSONObject("default_activity_price");
                                data.setPeople(jsoct.getString("people"));
                                data.setPrice(jsoct.getString("price"));
                                //							price = data.price;
                                JSONArray jsonArray = jsot.getJSONArray("activity_price");
                                for (int k = 0; k < jsonArray.length(); k++) {
                                    JSONObject objet2 = jsonArray.getJSONObject(k);
                                    JuTuanGouData data = new JuTuanGouData();
                                    data.setGoods_id(objet2.getString("goods_id"));
                                    data.setPeople(objet2.getString("people"));
                                    data.setPrice(objet2.getString("price"));
                                    list.add(data);
                                }


                                //						JSONObject jsonobt = object.getJSONObject("data");
                                JSONArray jsot_ll = jsonobt.getJSONArray("foreman_list");
                                for (int k = 0; k < jsot_ll.length(); k++) {
                                    JSONObject obj1 = jsot_ll.getJSONObject(k);
                                    data = new JuTuanGouData();
                                    //						data.setOrder_no(obj1.getString("order_no"));
                                    //						data.setOrder_no(obj1.getString("order_no"));
                                    data.setCompany_id(obj1.getString("company_id"));
                                    JSONArray jsot1 = obj1.getJSONArray("order_goods");
                                    for (int q = 0; q < jsot1.length(); q++) {
                                        JSONObject jsont = jsot1.getJSONObject(q);
                                        //							data = new JuTuanGouData();
                                        data.setArticle_id(jsont.getString("article_id"));
                                        data.setOrder_id(jsont.getString("order_id"));
                                        data.setGoods_id(jsont.getString("goods_id"));
                                        data.setQuantity(jsont.getString("quantity"));

                                        ////						data.setShare_img_url(jsont.getString("share_img_url"));
                                        data.setArticle_title(jsont.getString("article_title"));
                                        data.setImg_url(jsont.getString("img_url"));
                                        data.setForeman_id(jsont.getString("foreman_id"));
                                        data.setForeman_name(jsont.getString("foreman_name"));
                                        data.setTimer_time(jsont.getString("timer_time"));
                                        data.setEnd_time(jsont.getString("end_time"));
                                        data.setStart_time(jsont.getString("start_time"));
                                        data.setActivity_people(jsont.getInt("activity_people"));
                                        data.setActivity_member(jsont.getInt("activity_member"));
                                        data.setActivity_price(jsont.getString("activity_price"));
                                        data.setSell_price(jsont.getString("sell_price"));
                                        data.setMarket_price(jsont.getString("market_price"));
                                        //							data.setGroupon_no(jsont.getString("groupon_no"));
                                        //							data.setGroupon_item_id(jsont.getString("groupon_item_id"));
                                        ////						data.setOrder_no(jsont.getString("order_no"));
                                        //				    		end_time = data.getEnd_time();
                                        foreman_id = data.getForeman_id();
                                        foreman_name = data.getForeman_name();
                                        //							share_img_url = data.getShare_img_url();
                                        timer_time = data.getTimer_time();
                                        ct_id = data.getOrder_id();
                                        ct_tuanshu = String.valueOf(data.getActivity_people() - data.getActivity_member());
                                        //							list_ll.add(data);
                                        //							gettime();//开团获取倒计时
                                    }
                                }

                            } else {
                                Toast.makeText(JuTuanGouXq2Activity.this, info, Toast.LENGTH_SHORT).show();
                            }
                            System.out.println("=====list.size()=====================" + list.size());
                            System.out.println("=====list_ll.size()=====================" + list_ll.size());
                            intrendata();
                            handler.sendEmptyMessage(1);
                            getkaituan();
                            //					gettime();//开团获取倒计时
                            webview.loadUrl(URLConstants.REALM_NAME_HTTP + "/mobile/goods/conent-" + data.getArticle_id() + ".html");//商品介绍
                            progress.CloseProgress();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                }, null);
    }

    /**
     * 获取值到控件
     */
    public void intrendata() {
        try {
            Picasso.with(this)
                    .load(URLConstants.REALM_NAME_HTTP + data.getImg_url())
                    .into(iv_img);
            //        mAq.id(iv_img).image(URLConstants.REALM_NAME_HTTP+data.getImg_url());
            //        mAq.clear();
            tv_titel.setText(data.getArticle_title());
            tv_price.setText("原价：" + data.getSell_price());

            //		tv_tuangoujia.setText("￥"+list.get(0).getGroupon_price());
            //		tv_tuanshu.setText(list.get(0).getPeople()+"人团数");
            tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public void getkaituan() {
        try {

            stare = getIntent().getStringExtra("stare");
            //		String ct_id = getIntent().getStringExtra("ct_id");
            System.out.println("=======stare================================" + stare);
            System.out.println("=======ct_id================================" + ct_id);
            //		stare = "2";
            System.out.println("=======ct_tuanshu===========1=====================" + ct_tuanshu);
            if (stare != null) {
                if (stare.equals("1")) {//参团
                    ll_qu_kaituan.setVisibility(View.GONE);
                    ll_jiaguo.setVisibility(View.VISIBLE);
                    ll_kaituan.setVisibility(View.GONE);
                    tv_anniu1.setVisibility(View.VISIBLE);
                    txt_time.setVisibility(View.VISIBLE);
                    ll_jutuanjia.setVisibility(View.GONE);
                    tv_jutuan_price.setVisibility(View.VISIBLE);
                    tv_jutuan_price.setText("聚团价：" + data.getActivity_price());
                } else if (stare.equals("3")) {//参团
                    ll_qu_kaituan.setVisibility(View.GONE);
                    ll_jiaguo.setVisibility(View.GONE);
                    ll_kaituan.setVisibility(View.GONE);
                    tv_anniu1.setVisibility(View.GONE);
                    txt_time.setVisibility(View.VISIBLE);
                    txt_time.setText("已参团");
                } else {//邀请
                    tv_kaituan.setText(datetime);
                    tv_tuan.setText(ct_tuanshu);
                    ll_kaituan.setVisibility(View.VISIBLE);
                    ll_qu_kaituan.setVisibility(View.VISIBLE);
                    tv_anniu1.setVisibility(View.GONE);
                    ll_jiaguo.setVisibility(View.GONE);
                    txt_time.setVisibility(View.VISIBLE);

                    //				txt_time.setVisibility(View.VISIBLE);
                    //				tv_price.setVisibility(View.GONE);
                    //				tv_anniu1.setVisibility(View.GONE);
                    //				ll_jutuanjia.setVisibility(View.GONE);
                }

                //			System.out.println("=======datetime====================="+datetime);
                //			System.out.println("=======timer_time================================"+timer_time);

                //			System.out.println("=======stare===========2====================="+stare);
                System.out.println("=======ct_id===========1=====================" + ct_id);
                System.out.println("getIntent().getStringExtrafx_shuzi======================" + getIntent().getStringExtra("fx_shuzi"));

                if (stare != null) {
                    if (stare.equals("1")) {
                        String fx_yes = getIntent().getStringExtra("fx_yes");
                        System.out.println("=======fx_yes===========1=====================" + fx_yes);
                        //一键参团支付后提示分享
                        if (fx_yes == null) {
                            Intent intent = new Intent(JuTuanGouXq2Activity.this, CanTuanFengXiangActivity.class);
                            intent.putExtra("ct_id", ct_id);
                            intent.putExtra("ct_tuanshu", ct_tuanshu);
                            intent.putExtra("title", data.getArticle_title());
                            //						intent.putExtra("subtitle","");
                            intent.putExtra("subtitle", data.getSubtitle());
                            intent.putExtra("company_id", data.getCompany_id());
                            intent.putExtra("img_url", "");
                            intent.putExtra("fx_shuzi", fx_cs);
                            startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(JuTuanGouXq2Activity.this, CanTuanFengXiangActivity.class);
                        intent.putExtra("ct_id", ct_id);
                        intent.putExtra("ct_tuanshu", ct_tuanshu);
                        intent.putExtra("title", data.getArticle_title());
                        //					intent.putExtra("subtitle","");
                        intent.putExtra("subtitle", data.getSubtitle());
                        intent.putExtra("company_id", data.getCompany_id());
                        intent.putExtra("img_url", "");
                        intent.putExtra("fx_shuzi", fx_cs);
                        //					intent.putExtra("datetime",datetime);
                        //					intent.putExtra("timer_time",timer_time);
                        startActivity(intent);
                    }
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void gettime() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //			String end_time = getIntent().getStringExtra("end_time");
            //			String jieshu = "Toast.LENGTH_SHORT4-03-26 13:31:40";
            System.out.println("=====timer_time====================" + timer_time);
            System.out.println("=====datetime=====================" + datetime);
            now = df.parse(timer_time);
        } catch (java.text.ParseException e1) {

            e1.printStackTrace();
        }

        try {
            //			String end_time = getIntent().getStringExtra("datetime");
            //			String kaishi = "Toast.LENGTH_SHORT4-03-26 13:31:40";
            date = df.parse(datetime);
        } catch (java.text.ParseException e1) {

            e1.printStackTrace();
        }

        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hourl = (l / (60 * 60 * 1000) - day * 24);
        min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        long xiaoshi = day * 24;
        zongxs = xiaoshi;
        System.out.println("----------" + xiaoshi + "--" + zongxs);
        System.out.println("" + day + "天" + hourl + "小时" + min + "分" + s + "秒");

        //		    time = (hourl * 3600 + min * 60 + s) * 1000;
        time = (zongxs * 3600 + min * 60 + s) * 1000;
        System.out.println("time--------------" + time);
        count = new MyCount(time, 1000);
        System.out.println("2-------------" + count);
        count.start();//开始计时
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.tv_anniu1:
                getgouwu();
                //			if (!user_name_phone.equals("")) {
                //			Intent intent = new Intent(JuTuanGouXq2Activity.this,JuTuanConfrimActivity.class);
                ////			System.out.println("data.user_id================================="+data.user_id);
                ////		 	System.out.println("data.user_name================================="+data.user_name);
                ////			String groupon_id = getIntent().getStringExtra("id");
                //			intent.putExtra("buy_no",orders_no);
                //			intent.putExtra("title", data.article_title);
                //			intent.putExtra("activity_price", data.activity_price);
                //			intent.putExtra("spec_text",data.spec_text);
                //			intent.putExtra("img_url", data.img_url);
                //			intent.putExtra("ct_tuanshu", ct_tuanshu);
                //			intent.putExtra("foreman_id",foreman_id);
                //			intent.putExtra("foreman_name",foreman_name);
                ////			intent.putExtra("foreman_id", data.user_id);
                ////			intent.putExtra("foreman_name", data.user_name);
                //			intent.putExtra("stare", "3");
                //			intent.putExtra("type_jutuan","1");//状态
                //			intent.putExtra("type_wx",zhifu_wx);//支付方式
                //			intent.putExtra("jiekou", getIntent().getStringExtra("jiekou"));//jiekou = getIntent().getStringExtra("type_xq");
                //			startActivity(intent);
                //			} else {
                //				Intent intent = new Intent(JuTuanGouXq2Activity.this,UserLoginActivity.class);
                //				startActivity(intent);
                //				progress.CloseProgress();
                //			}
                break;
            default:
                break;
        }
    }

    private void getgouwu() {

        try {
            /**
             * 商品加入购物清单
             */
            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/add_activity_buy?user_id=" + user_id + "&user_name=" + user_name + "&user_sign=" + login_sign + "&" +
                            "article_id=" + data.article_id + "&goods_id=" + data.goods_id + "&quantity=" + 1 + "&order_id=" + data.order_id + "&people=" + data.activity_people + "",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {

                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject jsonObject = new JSONObject(arg1);
                                String status = jsonObject.getString("status");
                                System.out.println("购物清单================" + arg1);
                                String info = jsonObject.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    JSONObject obj = jsonObject.getJSONObject("data");
                                    String buy_no = obj.getString("buy_no");
                                    //							String count = obj.getString("count");
                                    Toast.makeText(JuTuanGouXq2Activity.this, info, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(JuTuanGouXq2Activity.this, JuTuanConfrimActivity.class);
                                    System.out.println("data.activity_people================" + data.activity_people);
                                    intent.putExtra("buy_no", buy_no);
                                    intent.putExtra("title", data.article_title);
                                    intent.putExtra("activity_price", data.activity_price);
                                    intent.putExtra("spec_text", data.spec_text);
                                    intent.putExtra("img_url", data.img_url);
                                    intent.putExtra("ct_tuanshu", String.valueOf(data.activity_people));
                                    intent.putExtra("foreman_id", foreman_id);
                                    intent.putExtra("foreman_name", foreman_name);
                                    intent.putExtra("stare", "3");
                                    intent.putExtra("type_jutuan", "1");//状态
                                    intent.putExtra("type_wx", zhifu_wx);//支付方式
                                    intent.putExtra("jiekou", getIntent().getStringExtra("jiekou"));//jiekou = getIntent().getStringExtra("type_xq");
                                    startActivity(intent);
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(JuTuanGouXq2Activity.this, info, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            progress.CloseProgress();
                            System.out.println("==========================访问接口失败！");
                            System.out.println("=========================" + arg0);
                            System.out.println("==========================" + arg1);
                            Toast.makeText(JuTuanGouXq2Activity.this, "异常", Toast.LENGTH_SHORT).show();
                            super.onFailure(arg0, arg1);
                        }


                    }, null);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    //实现计时功能的类
    class MyCount extends MyCountdownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            //媒体对象
            txt_time.setText("此团已结束");
        }

        //更新剩余时间
        @Override
        public void onTick(long millisUntilFinished, int percent) {
            current_time = millisUntilFinished;
            //			System.out.println("current_time-------------"+current_time);
            //			long day= (millisUntilFinished / 1000);
            //			long myhour = (millisUntilFinished / 1000) / 3600;
            //			long myminute = ((millisUntilFinished / 1000) - myhour * 3600) / 60;
            //			long mysecond = millisUntilFinished / 1000 - myhour * 3600 - myminute * 60;
            //			txt_time.setText("剩余时间: "+day+":" + myhour + ":" + myminute + ":" + mysecond);

            long day = current_time / (24 * 60 * 60 * 1000);
            long hour = (current_time / (60 * 60 * 1000) - day * 24);
            long min = ((current_time / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (current_time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            //			   System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
            //			   txt_time.setText("剩余时间: "+day+":" + hour + ":" + min + ":" + s);
            txt_time.setText("剩余时间: " + day + "天" + hour + "小时" + min + "分" + s + "秒");
        }
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.getDividerHeight();//获取子项间分隔符占用的高度
        //         params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
