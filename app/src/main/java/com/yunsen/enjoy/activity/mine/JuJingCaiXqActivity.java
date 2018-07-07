package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.yunsen.enjoy.activity.mine.adapter.JuTuanAdapter;
import com.yunsen.enjoy.activity.mine.adapter.MyJutuanMxAdapter;
import com.yunsen.enjoy.activity.mine.adapter.TuanchengyuanAdapterll;
import com.yunsen.enjoy.activity.mine.adapter.ZhongAnYlAdapter;
import com.yunsen.enjoy.activity.user.DBFengXiangActivity;
import com.yunsen.enjoy.activity.user.TishiWxBangDingActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.JuTuanGouData;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.MyCountdownTimer;
import com.yunsen.enjoy.widget.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 云商聚
 *
 * @author Administrator
 */
public class JuJingCaiXqActivity extends AppCompatActivity implements OnClickListener {

    private ImageView iv_img;
    private DialogProgress progress;
    //	private SharedPreference spPreferences_user;
    private SharedPreferences spPreferences;
    private ListView listview_01;
    private ArrayList<JuTuanGouData> list = null;
    private ArrayList<JuTuanGouData> list_ll = null;
    private ArrayList<JuTuanGouData> list_tx = null;
    private ArrayList<JuTuanGouData> list_cy;
    List<Integer> list_num;
    private ZhongAnYlAdapter zaylaAdapter;
    private TextView tv_titel, tv_price, tv_yuanjia, tv_tuangoujia, tv_tuanshu, tv_yq_cantuan, tv_kaituan_ts;
    JuTuanGouData data;
    JuTuanGouData bean;
    private GridView gridView;
    private MyGridView gridView2;
    private ListView new_list, list_tuanjia;
    JuTuanAdapter arrayadapter;
    private WebView webview;
    private LinearLayout ll_dianping, ll_lijigoumai, ll_tuangou;
    public static String item_id, goumai_id, tuangoujia, tuanshu;
    String user_id, user_name, choujiang, sp_id, ct_id, pt_fx, groupon_no;
    //	String province,city,area,user_address,user_mobile,name,shopping_address_id;
    String zhuangtai = "100";
    MyJutuanMxAdapter adapter;
    private TextView txt_time, tv_hd_time, tv_pt_gz;
    public static boolean fanhui_type = false;
    View iv_view;
    String type;
    int num, groupon_item_people;
    String id, orders_no;
    private static SimpleDateFormat sf = null;
    public static String datetime, end_time, timer_time, start_time;
    public static String foreman_id, foreman_name, tuan_id;
    public static String ct_tuanshu, foreman_id_pt;
    //	public static String people;
    private LinearLayout ll_qu_kaituan, ll_kaituan;
    public AQuery mAq;
    public static long day, hour;
    public static String fx_canshu = "";
    private TextView tv_tuanshu_ll, tv_anniu1, tv_anniu2;
    private long minute = 0;
    private long second = 0;
    private long time = 0;//毫秒为单位
    private long current_time = 0;
    private long interval = 10 * 1000;//每次增加或减少的额度为10秒
    private MyCount count;//定时类对象
    java.util.Date now_1;
    java.util.Date date_1;
    /**
     * 截至时间数据源
     **/
    private List<Date> listData;
    /**
     * 当前时间
     **/
    private long time_Current;
    /**
     * ListView控件
     **/
    private ListView listView;

    long hourl, min, s, zongxs;
    /**
     * 适配器
     **/
    private MyCountAdapter myCountAdapter;
    java.util.Date now;
    java.util.Date date;
    //这里很重要，使用Handler的延时效果，每隔一秒刷新一下适配器，以此产生倒计时效果
    private Handler handler_timeCurrent = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            time_Current = time_Current + 1000;
            myCountAdapter.notifyDataSetChanged();
            handler_timeCurrent.sendEmptyMessageDelayed(0, 1000);
        }
    };

    String weixin = "";
    String qq = "";
    String user_name_phone = "";
    String user_name_3_wx = "";
    String user_name_3_qq = "";
    String user_name_key = "";
    String oauth_name;
    String datall;
    String nickname = "";
    public static int fangshi = 0;
    public static boolean type_xq = false;//聚团详情销售属性不显示
    public static boolean type_spec_item = false;//聚团详情销售属性不显示
    public static int spec_text_list = 0;//销售套餐判断为0
    public static boolean taocan_type = false;//判断商品套餐价格

    @Override
    public void onResume() {

        super.onResume();
        JuTuanGouXqActivity.type_xq = false;//聚团详情销售属性不显示
        JuTuanGouXqActivity.type_spec_item = false;//聚团详情销售属性不显示
        JuTuanGouXqActivity.fx_canshu = "";
        WareInformationActivity.fangshi = 0;//返回0不判断为商品详情
        WareInformationActivity.taocan_type = false;//判断商品套餐价格

        WareInformationActivity.spec_text_list = 0;//销售套餐判断为0
        JuTuanGouXqActivity.spec_text_list = 0;//销售套餐判断为0

//			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
//			user_name_phone = spPreferences.getString("user", "");
//			user_id = spPreferences.getString("user_id", "");
//			user_name = spPreferences.getString("user_name", "");
//			type = getIntent().getStringExtra("type");//聚精彩状态

        System.out.println("user_name_phone================" + user_name_phone);

        if (JuTuanConfrimActivity.fanhui_type == true) {
            JuTuanConfrimActivity.fanhui_type = false;
            String groupon_id = getIntent().getStringExtra("id");
            System.out.println("groupon_id=====2=====聚精彩==========" + groupon_id);
            loadWeatherxq(groupon_id);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jutuangou_title);//activity_jutuangou_title activity_jutuangou_xq
        progress = new DialogProgress(JuJingCaiXqActivity.this);
//		JuTuanGouXqActivity.type_xq = false;//聚团详情销售属性不显示
//		JuTuanGouXqActivity.type_spec_item = false;//聚团详情销售属性不显示
        fx_canshu = getIntent().getStringExtra("fx_shuzi");
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name_phone = spPreferences.getString(SpConstants.USER_NAME, "");
        user_id = spPreferences.getString("user_id", "");
        user_name = spPreferences.getString("user_name", "");
        type = getIntent().getStringExtra("type");//聚精彩状态
        try {


            choujiang = getIntent().getStringExtra("choujiang");
            System.out.println("choujiang====================" + choujiang);
            mAq = new AQuery(this);
            intren();

            String groupon_id = getIntent().getStringExtra("id");
            System.out.println("groupon_id=====2=====聚精彩==========" + groupon_id);
            loadWeatherxq(groupon_id);

//		Button fanhui = (Button) findViewById(R.id.fanhui);
//		fanhui.setOnClickListener(this);


            Button iv_fanhui = (Button) findViewById(R.id.fanhui);
            iv_fanhui.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    System.out.println("iv_fanhui====================");
                    finish();
                }
            });

            ImageView img_shared = (ImageView) findViewById(R.id.img_shared);
            img_shared.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    try {
                        if (user_name_phone.equals("")) {
                            Intent intent = new Intent(JuJingCaiXqActivity.this, UserLoginActivity.class);
                            startActivity(intent);
                            progress.CloseProgress();
                        } else {
                            if (UserLoginActivity.wx_fanhui == false) {
                                Intent intent = new Intent(JuJingCaiXqActivity.this, UserLoginActivity.class);
                                startActivity(intent);
                                progress.CloseProgress();
                            } else {
                                System.out.println("pt_fx===========1=========" + pt_fx);
                                System.out.println("ct_id====================" + ct_id);
                                Intent intentll = new Intent(JuJingCaiXqActivity.this, DBFengXiangActivity.class);
                                if (ct_id != null) {
                                    intentll.putExtra("ct_id", ct_id);
                                    System.out.println("ct_id========1============" + ct_id);
                                } else {
                                    intentll.putExtra("pt_id", pt_fx);
                                    System.out.println("pt_fx=========2===========" + pt_fx);
                                }
//						intentll.putExtra("pt_id",pt_fx);
                                intentll.putExtra("title", data.getTitle());
                                intentll.putExtra("fx_shuzi", "groupon");
//						intentll.putExtra("img_url", data.share_img_url);
                                intentll.putExtra("subtitle", data.getSubtitle());
                                intentll.putExtra("company_id", data.getCompany_id());
                                intentll.putExtra("fx_shuzi", "groupon");
                                intentll.putExtra("img_url", "");
//						intentll.putExtra("subtitle","");
                                startActivity(intentll);
                            }
                        }
//				}
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    //防止当前Activity结束以后,   handler依然继续循环浪费资源
    @Override
    protected void onDestroy() {
        handler_timeCurrent.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    /**
     * 控件初始化
     */
    public void intren() {
        try {
//		listview_01=(ListView) findViewById(R.id.listview_01);
            list_tuanjia = (ListView) findViewById(R.id.list_tuanjia);
            gridView2 = (MyGridView) findViewById(R.id.gridview2);
            txt_time = (TextView) findViewById(R.id.tvshowtime);
            tv_hd_time = (TextView) findViewById(R.id.tv_hd_time);
            tv_pt_gz = (TextView) findViewById(R.id.tv_pt_kaituan_gz);
            tv_anniu1 = (TextView) findViewById(R.id.tv_anniu1);
            tv_anniu2 = (TextView) findViewById(R.id.tv_anniu2);
            tv_tuanshu_ll = (TextView) findViewById(R.id.tv_tuanshu_ll);
            ll_dianping = (LinearLayout) findViewById(R.id.ll_dianping);
            ll_lijigoumai = (LinearLayout) findViewById(R.id.ll_lijigoumai);
            ll_tuangou = (LinearLayout) findViewById(R.id.ll_tuangou);
            ll_qu_kaituan = (LinearLayout) findViewById(R.id.ll_qu_kaituan);
            ll_kaituan = (LinearLayout) findViewById(R.id.ll_kaituan);
            new_list = (ListView) findViewById(R.id.new_list);
            gridView = (GridView) findViewById(R.id.gridView);
            iv_img = (ImageView) findViewById(R.id.img);
            tv_titel = (TextView) findViewById(R.id.tv_titel);
            tv_price = (TextView) findViewById(R.id.tv_price);
            tv_yuanjia = (TextView) findViewById(R.id.tv_yuanjia);
            tv_tuangoujia = (TextView) findViewById(R.id.tv_tuangoujia);
            tv_tuanshu = (TextView) findViewById(R.id.tv_tuanshu);
            tv_yq_cantuan = (TextView) findViewById(R.id.tv_yq_cantuan);
            tv_kaituan_ts = (TextView) findViewById(R.id.tv_kaituan_ts);
            iv_view = findViewById(R.id.iv_view);
//		listview_01.setFocusable(false);
            tv_anniu1.setOnClickListener(this);
            ll_dianping.setOnClickListener(this);
            ll_lijigoumai.setOnClickListener(this);
            ll_tuangou.setOnClickListener(this);

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
                    tuanshu = JuTuanAdapter.tuanshu;
//				String tuangoujia = (String) msg.obj;
                    tv_tuangoujia.setText("¥" + tuangoujia);
                    tv_tuanshu.setText(tuanshu + "人团");
                    break;
                case 1:
//				System.out.println("list个数是多少===================="+list.size());
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

                    break;
                case 2:
                    try {
                        System.out.println("list个数是多少2====================" + list_ll.size());
                        if (list_ll.size() == 0) {
                            iv_view.setVisibility(View.GONE);
                        } else {
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
//						String end_time = "2004-03-26 13:31:40";
                                System.out.println("timer_time=========2===========" + timer_time);
                                System.out.println("datetime===========2=========" + datetime);
                                String time = timer_time;
                                now = df.parse(time);
                            } catch (java.text.ParseException e1) {

                                e1.printStackTrace();
                            }

                            try {
//						String datetime = "2004-03-26 13:31:30";
                                String time = datetime;
                                date = df.parse(time);
                                time_Current = date.getTime();
                            } catch (java.text.ParseException e1) {

                                e1.printStackTrace();
                            }

                            myCountAdapter = new MyCountAdapter();
                            new_list.setAdapter(myCountAdapter);
                            setListViewHeightBasedOnChildren(new_list);
                            handler_timeCurrent.sendEmptyMessageDelayed(0, 1000);

                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    break;
//			case 3://拼团分享
//				break;
                case 4:
                    try {

                        adapter = new MyJutuanMxAdapter(list_ll, JuJingCaiXqActivity.this);
                        new_list.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(new_list);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    break;

                case 5:
//  					new_list.setVisibility(View.GONE);
                    iv_view.setVisibility(View.GONE);
                    break;
//              case 6://参团分享
//				break;
                case 7:
                    ct_id = (String) msg.obj;
                    System.out.println("ct_id=========7===========" + ct_id);
                    break;
                case 8:
                    ct_tuanshu = (String) msg.obj;
                    System.out.println("ct_tuanshu=========8===========" + ct_tuanshu);
                    break;
                default:
                    break;
            }
        }

        ;
    };


    /**
     * 输出内容详情
     *
     * @param groupon_id
     * @param category_id
     */
    public static String img_url, title, sell_price, article_id, goods_id, price, spec_text;
    public static ArrayList data_shuzu, data_monney, data_goods_id, data_market_price, data_people, data_goods_id_1, data_people_1, data_price, data_spec_text, data_exchange_price, data_exchange_point;
    ArrayList list_data1, list_data2;

    private void loadWeatherxq(String groupon_id) {
        progress.CreateProgress();
        data_shuzu = new ArrayList();
        data_monney = new ArrayList();
        data_goods_id = new ArrayList();
        data_market_price = new ArrayList();
        data_people = new ArrayList();
        data_goods_id_1 = new ArrayList();
        data_people_1 = new ArrayList();
        data_price = new ArrayList();
        data_spec_text = new ArrayList();
        data_exchange_price = new ArrayList();
        data_exchange_point = new ArrayList();
        list_data2 = new ArrayList();
        list = new ArrayList<JuTuanGouData>();
        list_ll = new ArrayList<JuTuanGouData>();
//		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_model?id="+groupon_id+""
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_article_myselft_list?user_id=" + user_id + "&user_name=" + user_name + "&article_id=" + groupon_id + "&datatype=5&top=1"
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
//						JSONArray jsonArray = object.getJSONArray("data");
                                JSONObject jsonobt = object.getJSONObject("data");
                                data = new JuTuanGouData();
//						for (int i = 0; i < jsonArray.length(); i++) {
//							JSONObject obj = jsonArray.getJSONObject(i);
                                JSONObject obj = jsonobt.getJSONObject("article_model");
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
                                img_url = data.img_url;
                                title = data.title;
                                pt_fx = data.getId();
                                JSONObject jsot = obj.getJSONObject("default_spec_item");
                                data.setGoods_id(jsot.getString("goods_id"));
                                data.setArticle_id(jsot.getString("article_id"));
                                data.setSell_price(jsot.getString("sell_price"));
                                data.setSpec_text(jsot.getString("spec_text"));
                                spec_text = jsot.getString("spec_text");
                                sell_price = data.sell_price;
                                article_id = data.article_id;
                                goods_id = data.goods_id;
                                JSONObject jsoct = jsot.getJSONObject("default_activity_price");
                                data.setPeople(jsoct.getString("people"));
                                data.setPrice(jsoct.getString("price"));
                                price = data.price;
                                JSONArray jsonArray = jsot.getJSONArray("activity_price");
                                for (int k = 0; k < jsonArray.length(); k++) {
                                    JSONObject objet2 = jsonArray.getJSONObject(k);
                                    JuTuanGouData data = new JuTuanGouData();
                                    data.setGoods_id(objet2.getString("goods_id"));
                                    data.setPeople(objet2.getString("people"));
                                    data.setPrice(objet2.getString("price"));
                                    list.add(data);
                                    data_people.add(data.people);
                                }

                                JSONArray jsonay = obj.getJSONArray("spec_item");
                                for (int i = 0; i < jsonay.length(); i++) {
                                    JSONObject objt = jsonay.getJSONObject(i);
                                    JuTuanGouData data = new JuTuanGouData();
//				    		data.setSpec_text(objt.getString("spec_text"));
                                    data.setSell_price(objt.getString("sell_price"));
                                    data.setMarket_price(objt.getString("market_price"));
                                    data.setSpec_ids(objt.getString("spec_ids"));
                                    data.setGoods_id(objt.getString("goods_id"));
                                    data.setArticle_id(objt.getString("article_id"));
                                    data.setSpec_text(objt.getString("spec_text"));
                                    data.setExchange_point(objt.getString("exchange_point"));
                                    data.setExchange_price(objt.getString("exchange_price"));

                                    data_shuzu.add(data.spec_ids);
                                    data_monney.add(data.sell_price);
                                    data_market_price.add(data.market_price);
                                    data_goods_id.add(data.goods_id);
                                    data_spec_text.add(data.spec_text);
                                    data_exchange_point.add(data.exchange_point);
                                    data_exchange_price.add(data.exchange_price);
                                    JSONArray jsonArray2 = objt.getJSONArray("activity_price");
                                    for (int k = 0; k < jsonArray2.length(); k++) {
                                        JSONObject objet2 = jsonArray2.getJSONObject(k);
                                        JuTuanGouData data1 = new JuTuanGouData();
                                        data1.setGoods_id(objet2.getString("goods_id"));
                                        data1.setPeople(objet2.getString("people"));
                                        data1.setPrice(objet2.getString("price"));
                                        data_goods_id_1.add(data1.goods_id);
                                        data_people_1.add(data1.people);
                                        data_price.add(data1.price);
                                    }
                                }

                                JSONObject jsocet = obj.getJSONObject("activity");
                                data.setActivity_rule(jsocet.getString("activity_rule"));
//							JSONArray jsonArray = obj.getJSONArray("activity_item");
//							for (int i = 0; i < jsonArray.length(); i++) {
//								JuTuanGouData data = new JuTuanGouData();
//								JSONObject objet = jsonArray.getJSONObject(i);
//								data.setPeople(objet.getString("people"));
//								data.setPrice_discount(objet.getString("price_discount"));
//
//								JSONArray jsonArray1 = objet.getJSONArray("group_price");
//								for (int k = 0; k < jsonArray1.length(); k++) {
//									JSONObject objet1 = jsonArray.getJSONObject(k);
//									data.setGoods_id(objet1.getString("goods_id"));
//									data.setPrice(objet1.getString("price"));
//						        }
//								JSONArray jsonArray2 = objet.getJSONArray("activity_price");
//								for (int k = 0; k < jsonArray2.length(); k++) {
//									JSONObject objet2 = jsonArray.getJSONObject(k);
//									data.setGoods_id(objet2.getString("goods_id"));
//									data.setPeople(objet2.getString("people"));
//									data.setPrice(objet2.getString("price"));
//						        }
//								list.add(data);
//					        }
//						}
                                JSONArray jsot_ll = jsonobt.getJSONArray("foreman_list");
                                for (int k = 0; k < jsot_ll.length(); k++) {
                                    JSONObject obj1 = jsot_ll.getJSONObject(k);
//							data = new JuTuanGouData();
//							data.setOrder_no(obj1.getString("order_no"));
//							data.setOrder_no(obj1.getString("order_no"));
                                    data.setCompany_id(obj1.getString("company_id"));
                                    data.setUser_avatar(obj1.getString("user_avatar"));
                                    list_data2.add(data.getUser_avatar());
//							company_id = data.getCompany_id();
                                    JSONArray jsot1 = obj1.getJSONArray("order_goods");
                                    for (int q = 0; q < jsot1.length(); q++) {
                                        JSONObject jsont = jsot1.getJSONObject(q);
//								data = new JuTuanGouData();
                                        data.setArticle_id(jsont.getString("article_id"));
                                        data.setOrder_id(jsont.getString("order_id"));
                                        data.setGoods_id(jsont.getString("goods_id"));
                                        data.setQuantity(jsont.getString("quantity"));
////							data.setShare_img_url(jsont.getString("share_img_url"));
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
//								data.setGroupon_no(jsont.getString("groupon_no"));
//								data.setGroupon_item_id(jsont.getString("groupon_item_id"));
////							data.setOrder_no(jsont.getString("order_no"));
//					    		end_time = data.getEnd_time();
                                        timer_time = data.getTimer_time();
                                        foreman_id = data.getForeman_id();
                                        foreman_name = data.getForeman_name();
//								share_img_url = data.getShare_img_url();
//								timer_time = data.getTimer_time();
                                        ct_id = data.getOrder_id();
//								groupon_item_people = data.getActivity_people();
                                        ct_tuanshu = String.valueOf(data.getActivity_people() - data.getActivity_member());
                                        list_ll.add(data);
                                        //判断当前用户开团就执行
                                        if (user_id.equals(data.getForeman_id())) {
                                            timer_time = data.getTimer_time();
                                            String yq_people = String.valueOf(data.getActivity_people() - data.getActivity_member());
                                            System.out.println("yq_people---------------------" + yq_people);
                                            tv_yq_cantuan.setText("支付开团并邀请" + yq_people + "人参团，人数不足自动退款");
                                            tv_kaituan_ts.setText("您已经发起团购，您可以分享邀请好友参团");
                                            tv_tuanshu_ll.setText(yq_people);
                                            ll_qu_kaituan.setVisibility(View.VISIBLE);


                                            new_list.setVisibility(View.GONE);
                                            getCantuantime();//获取开团的倒计时
//									groupon_item_member(groupon_no);
//									handler.sendEmptyMessage(2);
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(JuJingCaiXqActivity.this, info, Toast.LENGTH_SHORT).show();
                            }
//					 System.out.println("list.size()---------------------"+list.size());
//					 System.out.println("data.getPrice()---------------------"+data.getPrice());
                            try {
                                System.out.println("=====data.getActivity_people()=====================" + data.getActivity_people());
//
                                for (int i = 0; i < data.getActivity_people(); i++) {
                                    System.out.println("=====i============" + i);
                                    System.out.println("=====list_data2=====================" + list_data2.size());
                                    if (list_data2.size() <= i) {
                                        list_data2.add("");
                                    }
                                    System.out.println("=====list_data2=========11============" + list_data2.size());
                                    TuanchengyuanAdapterll adapter = new TuanchengyuanAdapterll(list_data2, JuJingCaiXqActivity.this);
                                    gridView2.setAdapter(adapter);
                                }
                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                            intrendata();
                            handler.sendEmptyMessage(1);
                            webview.loadUrl(URLConstants.REALM_NAME_HTTP + "/mobile/goods/conent-" + data.article_id + ".html");//商品介绍
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
            System.out.println("---------------------" + data.getImg_url());
            Picasso.with(this).load(URLConstants.REALM_NAME_HTTP + data.getImg_url())
                    .into(iv_img);
//      mAq.id(iv_img).image(RealmName.REALM_NAME_HTTP+data.getImg_url());
            tv_hd_time.setVisibility(View.VISIBLE);
            tv_hd_time.setText("活动时间： " + data.getStart_time() + " ~ " + data.end_time);
            System.out.println("data.activity_rule=====================" + data.activity_rule);
            if (data.activity_rule.equals("null")) {
                tv_pt_gz.setVisibility(View.GONE);
                tv_pt_gz.setText("");
            } else {
                tv_pt_gz.setVisibility(View.VISIBLE);
                tv_pt_gz.setText(data.activity_rule);
            }

            tv_titel.setText(data.getTitle());
            tv_price.setText("原价：" + data.getSell_price());
//		tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            tv_yuanjia.setText("¥" + data.getSell_price());
            tv_tuangoujia.setText("¥" + data.getPrice());
            tv_tuanshu.setText(data.getPeople() + "人团");

            tv_price.getPaint().setFlags(
                    Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线

        } catch (Exception e) {

            e.printStackTrace();
        }

    }
//	ArrayList list_data1,list_data2;

    /**
     * 输出团成员
     *
     * @param
     */
    private void groupon_item_member(String groupon_no) {
        list_data2 = new ArrayList();
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_game_groupon_item_member?groupon_no=" + groupon_no + ""
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        System.out.println("=======输出团成员================================" + arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            datetime = object.getString("datetime");
                            if (status.equals("y")) {
                                list_cy = new ArrayList<JuTuanGouData>();
                                list_tx = new ArrayList<JuTuanGouData>();
                                try {
                                    JSONArray jay = object.getJSONArray("data");
                                    for (int k = 0; k < jay.length(); k++) {
                                        JuTuanGouData data = new JuTuanGouData();
                                        JSONObject obt = jay.getJSONObject(k);
                                        data.setId(obt.getString("id"));
                                        data.setTimer_time(obt.getString("timer_time"));
                                        timer_time = data.getTimer_time();
                                        data.setAvatar(obt.getString("avatar"));
                                        list_data2.add(data.getAvatar());
                                    }

//									String data_tx = "http://wx.qlogo.cn/mmopen/Zw5SzXToEzuCtHFRb2IVVZemJzJx4cLibMpDIE2y4kA1lgPfbhe2rO851s5G72B2U1Wz6cGe8Eb7B4AbtibiaUaSRBeH1XqqMiam/0";
//									list_data2.add(data_tx);
//									list_data2.add("/upload/phone/112293399/20170421173504531.jpg");
//									list_data2.add("");

                                    System.out.println("=====groupon_item_people=====================" + groupon_item_people);

                                    for (int i = 0; i < groupon_item_people; i++) {
                                        System.out.println("=====i============" + i);
                                        System.out.println("=====list_data2=====================" + list_data2.size());
                                        if (list_data2.size() <= i) {
                                            list_data2.add("null");
                                        }
                                        System.out.println("=====list_data2=========11============" + list_data2.size());

                                        TuanchengyuanAdapterll adapter = new TuanchengyuanAdapterll(list_data2, JuJingCaiXqActivity.this);
                                        gridView2.setAdapter(adapter);

                                    }

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }

                            } else {
//							Toast.makeText(JuTuanGouXq2Activity.this, info, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                }, getApplicationContext());
    }

//		private void getzhou() {
//			try {
//				list_data2 = new ArrayList();
//			ll_qu_kaituan.setVisibility(View.VISIBLE);
//			new_list.setVisibility(View.GONE);
//			System.out.println("=====================模拟测试===============");
//
//			String data_tx = "http://wx.qlogo.cn/mmopen/Zw5SzXToEzuCtHFRb2IVVZemJzJx4cLibMpDIE2y4kA1lgPfbhe2rO851s5G72B2U1Wz6cGe8Eb7B4AbtibiaUaSRBeH1XqqMiam/0";
////			if (data_tx.contains("http")) {
////				list_data2.add(data_tx);
////			}else {
////				new Thread(getPicByUrl).start();
////				list_data2.add("/upload/phone/113875199/20170217164544307.jpg");
////				list_data2.add(data_tx);
////				list_data2.add("");
////				list_data2.add("/upload/phone/112293399/20170421173504531.jpg");
////			}
//
////			list_data2.add("/upload/phone/113875199/20170217164544307.jpg");
//			list_data2.add("");
//			for (int i = 0; i < 10; i++) {
////				String num = String.valueOf(i);
////				list_data1.add(num);
//				System.out.println("=====i============"+i);
//				System.out.println("=====list_data2====================="+list_data2.size());
//				if (list_data2.size() <= i) {
////					list_data2.add("/upload/201608/04/201608041952577479.png");
////					list_data2.add("/upload/phone/113875199/20170217164544307.jpg");
//					list_data2.add("null");
//				}
//				System.out.println("=====list_data2=========11============"+list_data2.size());
//
//				TuanchengyuanAdapterll adapter = new TuanchengyuanAdapterll(list_data2,JuJingCaiXqActivity.this);
//				gridView2.setAdapter(adapter);
//			}
//			} catch (Exception e) {
//
//				e.printStackTrace();
//			}
//
//		}

    Runnable getPicByUrl = new Runnable() {
        @Override
        public void run() {
            try {
//						String img_url2 = "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
                String img_url2 = URLConstants.REALM_NAME_HTTP + "/upload/phone/113875199/20170217164544307.jpg";
                System.out.println("img_url2==============" + img_url2);
                Bitmap bmp = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
//						Bitmap bitMap_tx = Utils.toRoundBitmap(bmp,null);// 这个时候的图片已经被处理成圆形的了
//						System.out.println("bitMap_tx=============="+bitMap_tx);
                System.out.println("bmp==============" + bmp);
                list_data2.add(bmp);
            } catch (Exception e) {
                Log.i("ggggg", e.getMessage());
            }
        }
    };

    public void setListViewHeightBasedOnChildren(ListView listView) {
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
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.webview:
//			loadWeather();
//			webview.loadUrl("http://183.62.138.31:1010/mobile/goods/conent-"+data.article_id+".html");//商品介绍
                webview.loadUrl(URLConstants.REALM_NAME_HTTP + "/mobile/goods/conent-" + data.article_id + ".html");//商品介绍
                break;
            case R.id.tv_anniu1://会员开团邀请分享
                System.out.println("=====1======" + ct_id);
				if (UserLoginActivity.wx_fanhui == false) {
					Intent intent = new Intent(JuJingCaiXqActivity.this,UserLoginActivity.class);
					startActivity(intent);
				}else {
					Intent intentll = new Intent(JuJingCaiXqActivity.this,DBFengXiangActivity.class);
					intentll.putExtra("ct_id",ct_id);
					intentll.putExtra("title",data.getTitle());
					intentll.putExtra("fx_shuzi", "groupon");
//				intentll.putExtra("img_url", data.share_img_url);
					intentll.putExtra("subtitle",data.getSubtitle());
					intentll.putExtra("company_id",data.getCompany_id());
					intentll.putExtra("fx_shuzi","groupon");
					intentll.putExtra("img_url", "");
//				intentll.putExtra("subtitle","");
					startActivity(intentll);
				}
                break;
            case R.id.ll_dianping://收藏
//			progress.CreateProgress();
//			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
//			String user_name = spPreferences.getString("user", "");
//			String user_id = spPreferences.getString("user_id", "");
                System.out.println("user_name_phone=======收藏=========" + user_name_phone);
                if (!nickname.equals("")) {
                    if (!user_name_phone.equals("")) {
                        System.out.println("2================" + user_name_phone);
                        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/user_favorite?article_id=" + data.article_id + "&user_name=" + user_name_phone + "" +
                                "&user_id=" + user_id + "&tags=", new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String arg1) {

                                super.onSuccess(arg0, arg1);
                                try {
                                    JSONObject jsonObject = new JSONObject(arg1);
                                    System.out.println("收藏================" + arg1);
                                    String status = jsonObject.getString("status");
                                    String info = jsonObject.getString("info");
                                    if (status.equals("y")) {
                                        progress.CloseProgress();
                                        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
                                    } else {
                                        progress.CloseProgress();
                                        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }

                            }
                        }, getApplicationContext());
                    } else {
						Intent intent = new Intent(JuJingCaiXqActivity.this, TishiWxBangDingActivity.class);
						startActivity(intent);
                        progress.CloseProgress();
                    }
                } else {
                    try {

                        if (!user_name_phone.equals("")) {
                            System.out.println("2================" + user_name_phone);
                            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/user_favorite?article_id=" + data.article_id + "&user_name=" + user_name_phone + "" +
                                    "&user_id=" + user_id + "&tags=", new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int arg0, String arg1) {

                                    super.onSuccess(arg0, arg1);
                                    try {
                                        JSONObject jsonObject = new JSONObject(arg1);
                                        System.out.println("收藏================" + arg1);
                                        String status = jsonObject.getString("status");
                                        String info = jsonObject.getString("info");
                                        if (status.equals("y")) {
                                            progress.CloseProgress();
                                            Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
                                        } else {
                                            progress.CloseProgress();
                                            Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                    }

                                }
                            }, getApplicationContext());
                        } else {
							Intent intent = new Intent(JuJingCaiXqActivity.this,UserLoginActivity.class);
							startActivity(intent);
                            progress.CloseProgress();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }
                break;
            case R.id.ll_lijigoumai://立即购买
//			Intent intent = new Intent(JuJingCaiXqActivity.this, JuTuanXqTiShiActivity.class);
//			startActivity(intent);
                fangshi = 3;
                type_xq = true;
                type_spec_item = false;//聚团详情销售属性显示
                spec_text_list = 2;//销售套餐判断为2
                taocan_type = true;//判断商品套餐价格
//			JuJingCaiXqActivity.type_spec_item = false;
                CommomConfrim.showSheet(JuJingCaiXqActivity.this, data.id);
//			Intent intent = new Intent(JuJingCaiXqActivity.this,XiaoShouShuXingActivity.class);
//			intent.putExtra("id", data.id);
//			intent.putExtra("type_xq", getIntent().getStringExtra("type_xq"));
//			startActivity(intent);
//			if (!nickname.equals("")) {
//				if (!user_name_phone.equals("")) {
//					loadgouwuche();
//				} else {
//				Intent intent = new Intent(JuJingCaiXqActivity.this, TishiWxBangDingActivity.class);
//				startActivity(intent);
//				progress.CloseProgress();
//				}
//			} else {
//			if (!user_name_phone.equals("")) {
//			loadgouwuche();
//			} else {
//				Intent intent = new Intent(JuJingCaiXqActivity.this,UserLoginActivity.class);
//				startActivity(intent);
//				progress.CloseProgress();
//			}
//			}
                break;
            case R.id.ll_tuangou://团购
                fangshi = 4;
                type_xq = true;//聚团详情销售属性不显示
                type_spec_item = true;//聚团详情销售属性显示
                spec_text_list = 2;//销售套餐判断为2
                taocan_type = false;//积分兑换判断套餐价格显示
                CommomConfrim.showSheet(JuJingCaiXqActivity.this, data.id);

//			Intent intent1 = new Intent(JuJingCaiXqActivity.this,XiaoShouShuXingActivity.class);
//			intent1.putExtra("id", data.id);
//			intent1.putExtra("type_xq", getIntent().getStringExtra("type_xq"));
//			startActivity(intent1);

//			if (!nickname.equals("")) {
//				if (!user_name_phone.equals("")) {
//					Intent intent = new Intent(JuJingCaiXqActivity.this,JuTuanConfrimActivity.class);
//					choujiang = getIntent().getStringExtra("choujiang");
//					System.out.println("choujiang===================="+choujiang);
//					if (choujiang != null) {
////						zhuangtai = "110";
//						intent.putExtra("type_wx","type_wx");//支付方式
//					}
//					String groupon_id = getIntent().getStringExtra("id");
//					System.out.println("zhuangtai================"+zhuangtai);
////				 	if (foreman_id == null) {
////				 		foreman_id = user_id;
////				 		foreman_name = user_name_phone;
////					}
////				 	System.out.println("foreman_id================================="+foreman_id);
////				 	System.out.println("foreman_name================================="+foreman_name);
//					intent.putExtra("title", data.title);
//					intent.putExtra("price", data.price);
//					intent.putExtra("img_url", data.img_url);
//					intent.putExtra("groupon_price", tuangoujia);
//					intent.putExtra("item_id", item_id);
//					intent.putExtra("fx_key", "fx_key");
//					intent.putExtra("ct_id", ct_id);
////					intent.putExtra("foreman_id", data.user_id);
////					intent.putExtra("foreman_name", data.user_name);
//					intent.putExtra("foreman_id",user_id);
//					intent.putExtra("foreman_name",user_name_phone);
//					intent.putExtra("id",groupon_id);
//					intent.putExtra("groupon_no", groupon_no);
//					intent.putExtra("100", zhuangtai);
//					intent.putExtra("stare", "2");
//					intent.putExtra("type","1");//聚精彩状态
//					intent.putExtra("jiekou","1");//聚精彩接口状态
//					intent.putExtra("fx_shuzi","groupon");
//					startActivity(intent);
//				} else {
//				Intent intent = new Intent(JuJingCaiXqActivity.this, TishiWxBangDingActivity.class);
//				startActivity(intent);
//				progress.CloseProgress();
//				}
//			}else {
//				System.out.println("user_name_phone===================="+user_name_phone);
//			if (!user_name_phone.equals("")) {
                try {

//			Intent intent = new Intent(JuJingCaiXqActivity.this,JuTuanConfrimActivity.class);
//			choujiang = getIntent().getStringExtra("choujiang");
//			System.out.println("choujiang===================="+choujiang);
////			if (choujiang != null) {
//////				zhuangtai = "110";
////				intent.putExtra("type_wx","type_wx");//支付方式
////			}
////			System.out.println("zhuangtai================================="+zhuangtai);
//			String groupon_id = getIntent().getStringExtra("id");
////		 	if (foreman_id == null) {
////		 		foreman_id = user_id;
////		 		foreman_name = user_name_phone;
////			}
////		 	System.out.println("foreman_id==================1==============="+foreman_id);
////		 	System.out.println("foreman_name=================1================"+foreman_name);
//			System.out.println("user_id==================1==============="+user_id);
//		 	System.out.println("user_name_phone=================1================"+user_name_phone);
////			System.out.println("sp_id==========1======"+sp_id);
//			intent.putExtra("title", data.title);
//			intent.putExtra("price", data.sell_price);
//			intent.putExtra("img_url", data.img_url);
////			intent.putExtra("groupon_price", tuangoujia);
//			intent.putExtra("groupon_price", data.price);
//			intent.putExtra("item_id", item_id);
////			intent.putExtra("fx_key", "fx_key");
//			intent.putExtra("ct_id", ct_id);
////			intent.putExtra("foreman_id", data.user_id);
////			intent.putExtra("foreman_name", data.user_name);
//			intent.putExtra("foreman_id",user_id);
//			intent.putExtra("foreman_name",user_name_phone);
//			intent.putExtra("groupon_no", groupon_no);
////			intent.putExtra("id",groupon_id);
////			intent.putExtra("100", zhuangtai);
//			intent.putExtra("stare", "2");
//			intent.putExtra("type","1");//聚精彩状态
//			intent.putExtra("jiekou","1");//聚精彩接口状态
//			intent.putExtra("fx_shuzi","groupon");
//		 	intent.putExtra("type_wx","type_wx");//支付方式
//			startActivity(intent);

                } catch (Exception e) {

                    e.printStackTrace();
                }
//			} else {
//				Intent intent = new Intent(JuJingCaiXqActivity.this,UserLoginActivity.class);
//				startActivity(intent);
//				progress.CloseProgress();
//			}
//			}
                break;
            default:
                break;
        }
    }


    /**
     * 获取开团的倒计时
     */
    public void getCantuantime() {
        System.out.println("获取开团的倒计时----------");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("timer_time-------------" + timer_time);
        System.out.println("datetime-------------" + datetime);
        try {
//			timer_time = "2017-05-02 20:04:30";
//			timer_time = "2017-05-16 08:03:05";
            now_1 = df.parse(timer_time);
        } catch (java.text.ParseException e1) {

            e1.printStackTrace();
        }
        try {
//			datetime = "2017-05-02 18:04:10";
//			datetime = "2017-05-14 17:23:15";
            date_1 = df.parse(datetime);
        } catch (java.text.ParseException e1) {

            e1.printStackTrace();
        }

        try {
            long l = now_1.getTime() - date_1.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hourl = (l / (60 * 60 * 1000) - day * 24);
            min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            long xiaoshi = day * 24;
            zongxs = xiaoshi;
            System.out.println("----------" + xiaoshi + "--" + zongxs);
            System.out.println("" + day + "天" + hourl + "小时" + min + "分" + s + "秒");

            time = (zongxs * 3600 + min * 60 + s) * 1000;

            System.out.println("time--------------" + time);
            count = new MyCount(time, 1000);
            System.out.println("2-------------" + count);
            count.start();//开始计时

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    /**
     * 购物清单
     */
    private void loadgouwuche() {
        try {
//			progress.CreateProgress();

            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/add_shopping_buy?user_id=" + user_id + "&user_name=" + user_name_phone +
                    "&article_id=" + data.article_id + "&goods_id=" + data.goods_id + "&quantity=" + 1 + "", new AsyncHttpResponseHandler() {
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
                            String id = obj.getString("id");
//									String count = obj.getString("count");
//									Toast.makeText(JuTuanGouXqActivity.this, info, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(JuJingCaiXqActivity.this, JuTuanConfrimActivity.class);
//										if (choujiang != null) {
//											intent.putExtra("type_wx","type_wx");//支付方式
//										}

                            intent.putExtra("shopping_ids", id);
                            intent.putExtra("fx_key", "fx_key");
//										intent.putExtra("item_id", goumai_id);
//										intent.putExtra("foreman_id", foreman_id);
//										intent.putExtra("foreman_name", data.user_name);
                            startActivity(intent);
                        } else {
                            progress.CloseProgress();
                            Toast.makeText(JuJingCaiXqActivity.this, info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                    progress.CloseProgress();
                }

                @Override
                public void onFailure(Throwable arg0, String arg1) {

                    System.out.println("==========================访问接口失败！");
                    System.out.println("=========================" + arg0);
                    System.out.println("==========================" + arg1);
                    super.onFailure(arg0, arg1);
                }


            }, getApplicationContext());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public class MyCountAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list_ll.size();
        }

        @Override
        public Object getItem(int position) {
            return list_ll.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(JuJingCaiXqActivity.this, R.layout.cantuanjia_item, null);
                holder = new ViewHolder();
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            TextView tv_renshu = (TextView) convertView.findViewById(R.id.tv_renshu);
            TextView tv_qucantuan = (TextView) convertView.findViewById(R.id.tv_qucantuan);

            try {

                System.out.println("user_id-------------" + user_id);
                System.out.println("list_ll.get(position).getForeman_id()-------------" + list_ll.get(position).getForeman_id());
//			System.out.println("people-------------"+people);
                System.out.println("type-------------" + type);
//			if (type != null) {

                if (user_id.equals(list_ll.get(position).getForeman_id())) {
                    try {
                        System.out.println("list_ll.get(position).getTimer_time()-------------" + list_ll.get(position).getTimer_time());
                        ll_qu_kaituan.setVisibility(View.VISIBLE);
                        new_list.setVisibility(View.GONE);
//					timer_time = list_ll.get(position).getTimer_time();
//					System.out.println("timer_time------11-------"+timer_time);
//					System.out.println("people-----1--------"+people);
                        String people = String.valueOf(list_ll.get(position).getActivity_people() - list_ll.get(position).getActivity_member());
                        System.out.println("people-----2--------" + people);
//					String people_ct = String.valueOf(list_ll.get(position).getGroupon_item_people()- list_ll.get(position).getGroupon_item_member());
//					System.out.println("people_ct-------------"+people_ct);
//					tv_tuanshu_ll.setText(people);

//					getCantuantime();//获取开团的倒计时

                        Message msg = new Message();
                        msg.what = 8;
                        msg.obj = String.valueOf(list_ll.get(position).getGroupon_item_people() - list_ll.get(position).getGroupon_item_member());
                        handler.sendMessage(msg);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                } else {
                    new_list.setVisibility(View.GONE);
                }


            } catch (Exception e) {

                e.printStackTrace();
            }


//			Date date_finish = listData.get(position);

//			updateTextView( now.getTime()-time_Current, holder);

            //获取订单id分享
            if (user_id.equals(list_ll.get(position).getForeman_id())) {
                Message msg = new Message();
                msg.what = 7;
                msg.obj = list_ll.get(position).getOrder_id();
                handler.sendMessage(msg);
            }


            return convertView;
        }

        /****
         * 刷新倒计时控件
         */
//		public void updateTextView(long times_remain,ViewHolder hoder) {
//
//			if (times_remain <= 0) {
//				Message msg = new Message();
//				msg.what = 5;
//				handler.sendMessage(msg);
//				return;
//			}
//
//			current_time = times_remain;
////			System.out.println("current_time-------------"+current_time);
//			   long day=current_time/(24*60*60*1000);
//			   long hour=(current_time/(60*60*1000)-day*24);
//			   long min=((current_time/(60*1000))-day*24*60-hour*60);
//			   long s=(current_time/1000-day*24*60*60-hour*60*60-min*60);
////			   System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
//			hoder.tv_time.setText("剩余时间: "+day+"天" + hour + "小时" + min + "分" + s+"秒");
//		}

        private class ViewHolder {
            /**
             * 小时
             **/
            private TextView tv_time;
            /**
             * 小时
             **/
            private TextView tv_hour;
            /**
             * 分钟
             **/
            private TextView tv_minute;
            /**
             * 秒
             **/
            private TextView tv_second;
        }
    }

    //开团实现计时功能的类
    class MyCount extends MyCountdownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            //媒体对象
            txt_time.setText("此团已结束");
//			ll_qu_kaituan.setVisibility(View.GONE);
//			ll_qu_kaituan.setBackgroundResource(R.drawable.bg_ccc_3_5_bg);
            tv_anniu1.setVisibility(View.GONE);
            tv_anniu2.setVisibility(View.VISIBLE);
        }

        //更新剩余时间
        @Override
        public void onTick(long millisUntilFinished, int percent) {
            current_time = millisUntilFinished;

            long day = current_time / (24 * 60 * 60 * 1000);
            long hour = (current_time / (60 * 60 * 1000) - day * 24);
            long min = ((current_time / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (current_time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

//			   System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
//			   txt_time.setText("剩余时间: "+day+":" + hour + ":" + min + ":" + s);
            txt_time.setText("剩余: " + day + "天" + hour + "小时" + min + "分" + s + "秒");
        }
    }


}
