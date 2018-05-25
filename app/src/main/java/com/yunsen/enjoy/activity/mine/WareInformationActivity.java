package com.yunsen.enjoy.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.hengyushop.dao.WareDao;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.adapter.GoodsGgcsListAdapter;
import com.yunsen.enjoy.activity.mine.adapter.GuigeListAdapter;
import com.yunsen.enjoy.activity.user.DBFengXiangActivity;
import com.yunsen.enjoy.activity.user.TishiWxBangDingActivity;
import com.yunsen.enjoy.activity.user.UserLoginActivity;
import com.yunsen.enjoy.activity.user.UserLoginWayActivity;
import com.yunsen.enjoy.common.AppManager;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.GuigeBean;
import com.yunsen.enjoy.model.GuigeData;
import com.yunsen.enjoy.model.JuTuanGouData;
import com.yunsen.enjoy.model.UserRegisterData;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.model.XiangqingData;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.MyPosterOnClick;
import com.yunsen.enjoy.widget.MyPosterView;
;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"deprecation", "unused"})
public class WareInformationActivity extends AppCompatActivity implements
        OnClickListener {
    private List<Map<String, String>> allGriddatas = null;
    private TextView tv_market_money, tv_ware_name, tv_hengyu_money,
            tv_title_name, tv_ware_market_jifen, tv_integral, tv_spec_text, tv_wenzi1, tv_wenzi2;
    private Button btn_add_shop_cart;
    private ImageButton btn_ware_infromation_share, btn_wechat, btn_wx_friend, sms;
    private ImageButton img_btn_tencent;
    private View btn_sms;
    private LinearLayout market_information_param, market_information_sep, btn_collect, btn_dianping;
    // private TabHost tabHost;
    private LayoutInflater inflater;
    private View view;
    private PopupWindow pop;
    private String strUrl, str1, str2, str3, str4, str5, str6;
    private int productItemId, AvailableIntegral;
    public static String sp_id, proFaceImg, proInverseImg, proDoDetailImg, proDesignImg, point_ll,
            proName, proTip, retailPrice, marketPrice, proSupplementImg, goods_price, price, title_jduihuan,
            proComputerInfo, yth, key, releaseBossUid, AvailableJuHongBao, Atv_integral, company_id, imgs_url,
            productCount, title_ll, spec_ids, article_id, goods_id, subtitle, spec_text, point_id;
    XiangqingData xqdata;
    private String title, sub_title, sell_price, market_price, cost_price, rebate_price;
    private LinearLayout order_shop_now;
    private WareDao wareDao;
    private int index = 1;
    private int Orderid;
    private int ischecked = 1;
    private int wareId;
    private DialogProgress progress;
    private IWXAPI api;
    private ArrayList<XiangqingData> lists;
    private MyPosterView market_information_images;
    private UserRegisterData registerData;
    private int productItemType;
    private int iSLING = -1;
    private int CID;
    private LinearLayout images_layout;
    private LinearLayout market_information_describe;
    private TextView market_information_title, market_information_tip;
    private Button enter_shop, fanhui;
    private LinearLayout ll_money_ju, market_information_juduihuan, market_information_bottom;
    GuigeData md;
    GuigeBean mb;
    private ImageView img_shared;
    private Context context;
    private SharedPreferences spPreferences;
    public static String user_id;
    private LinearLayout ll_shiyishicai1, ll_shiyishicai2;
    private TextView bt_cart_all, bt_cart_low, bt_cart_stock;
    private TextView show_cart_all, show_cart_low, show_cart_stock;
    private ArrayList<GuigeData> list_ll = null;
    private ArrayList<GuigeData> list_lb = null;
    private WebView webview;
    private ListView new_list;
    int spjs = 1;
    boolean zhuantai = false;
    private ImageView ling_tip;
    String point;
    public AQuery mAq;
    ArrayList<XiangqingData> list_ggcs;
    public static String user_point, exchange_price, exchange_point;
    public static String title_jdh = "";
    public static String jdh_type = "";
    int len;
    String user_name = "";
    String user_name_3 = "";
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
    public static int spec_text_list = 0;//销售套餐判断为0
    public static boolean taocan_type = false;//判断商品套餐价格

    @Override
    public void onResume() {

        super.onResume();
        JuJingCaiXqActivity.type_xq = false;//聚团详情销售属性不显示
        JuJingCaiXqActivity.type_spec_item = false;//聚团详情销售属性不显示
        JuTuanGouXqActivity.type_xq = false;//聚团详情销售属性不显示
        JuTuanGouXqActivity.type_spec_item = false;//聚团详情销售属性不显示

        JuJingCaiXqActivity.spec_text_list = 0;//销售套餐判断为0
        JuTuanGouXqActivity.spec_text_list = 0;//销售套餐判断为0

        SharedPreferences spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
        nickname = spPreferences_login.getString(SpConstants.NICK_NAME, "");

        System.out.println("nickname=================" + nickname);
        if (!nickname.equals("")) {
            getjianche();//后台检测是否绑定手机
        } else {
            getuserxinxi();
        }
    }

    //当Activity被销毁时会调用onDestory方法
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (MyPosterView.type == true) {
                MyPosterView.mQuery.clear();
                MyPosterView.type = false;
            }
            mAq.clear();
            lists = null;
            xqdata = null;
            list_tp = null;
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void getjianche() {

        SharedPreferences spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
        nickname = spPreferences_login.getString(SpConstants.NICK_NAME, "");
        String headimgurl = spPreferences_login.getString("headimgurl", "");
        String unionid = spPreferences_login.getString("unionid", "");
        String access_token = spPreferences_login.getString("access_token", "");
        String sex = spPreferences_login.getString("sex", "");
        System.out.println("UserLoginActivity=====================" + UserLoginActivity.oauth_name);
        System.out.println("UserLoginWayActivity=====================" + UserLoginWayActivity.oauth_name);

        if (UserLoginActivity.oauth_name.equals("weixin")) {
            oauth_name = "weixin";
        } else if (UserLoginWayActivity.oauth_name.equals("weixin")) {
            oauth_name = "qq";
            unionid = "";
        }

        System.out.println("nickname-----1-----" + nickname);
        String nick_name = nickname.replaceAll("\\s*", "");
        System.out.println("nick_name-----2-----" + nick_name);

        //			String strUrlone = RealmName.REALM_NAME_LL + "/user_oauth_register_0215?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+"" +
        //			"&province=&city=&country=&oauth_name="+oauth_name+"&oauth_access_token="+access_token+"&oauth_unionid="+unionid+"";

        String oauth_openid = spPreferences_login.getString("oauth_openid", "");
        String strUrlone = URLConstants.REALM_NAME_LL + "/user_oauth_register_0217?nick_name=" + nick_name + "&sex=" + sex + "&avatar=" + headimgurl + "" +
                "&province=&city=&country=&oauth_name=" + oauth_name + "&oauth_unionid=" + unionid + "" +
                "&oauth_openid=" + oauth_openid + "";
        System.out.println("我的======11======1=======" + strUrlone);
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                System.out.println("我的======输出=====1========" + arg1);
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    String info = object.getString("info");
                    //						if (status.equals("y")) {
                    datall = object.getString("data");
                    //							JSONObject obj = object.getJSONObject("data");
                    //							data.id = obj.getString("id");
                    //							data.user_name = obj.getString("user_name");
                    //							province = obj.getString("province");
                    //							city = obj.getString("city");
                    //							area = obj.getString("area");

                    System.out.println("datall==============" + datall);
                    if (("null").equals(datall)) {
                        SharedPreferences spPreferences_tishi = getSharedPreferences("longuserset_tishi", MODE_PRIVATE);
                        weixin = spPreferences_tishi.getString("weixin", "");
                        qq = spPreferences_tishi.getString("qq", "");
                        System.out.println("=================weixin==" + weixin);
                        System.out.println("=================qq==" + qq);

                        System.out.println("UserLoginActivity.panduan====1==" + UserLoginActivity.panduan_tishi);
                        System.out.println("UserLoginWayActivity.panduan====2==" + UserLoginWayActivity.panduan_tishi);
                        if (!nickname.equals("")) {

                            if (UserLoginActivity.panduan_tishi == true) {
                                if (weixin.equals("weixin")) {
                                } else {
                                    Intent intent1 = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
                                    startActivity(intent1);
                                    UserLoginActivity.panduan_tishi = false;
                                }

                            } else if (UserLoginWayActivity.panduan_tishi == true) {
                                if (qq.equals("qq")) {
                                } else {
                                    Intent intent2 = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
                                    startActivity(intent2);
                                    UserLoginWayActivity.panduan_tishi = false;
                                }
                            }
                        }

                    } else {
                        UserRegisterllData data = new UserRegisterllData();
                        JSONObject obj = object.getJSONObject("data");
                        data.id = obj.getString("id");
                        data.user_name = obj.getString("user_name");
                        user_id = data.id;
                        System.out.println("---data.user_name-------------------" + data.user_name);
                        System.out.println("---user_id-------------------" + user_id);
                        if (data.user_name.equals("匿名")) {
                            System.out.println("---微信还未绑定-------------------");
                            Intent intent1 = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
                            startActivity(intent1);
                        } else {
                            SharedPreferences spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
                            String user = spPreferences.getString(SpConstants.USER_NAME, "");
                            System.out.println("---1-------------------" + user);
                            data.login_sign = obj.getString("login_sign");

                            Editor editor = spPreferences.edit();
                            editor.putString("user", data.user_name);
                            editor.putString("user_id", data.id);
                            editor.putString("login_sign", data.login_sign);
                            editor.commit();

                            String user_name = spPreferences.getString("user", "");
                            System.out.println("---2-------------------" + user_name);
                        }
                    }
                    getuserxinxi();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            ;
        }, WareInformationActivity.this);

    }

    private void getuserxinxi() {

        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name_phone = spPreferences.getString(SpConstants.USER_NAME, "");

        if (!user_name_phone.equals("")) {
            user_name = user_name_phone;
            user_id = spPreferences.getString("user_id", "");
        } else {
            user_name = "";
        }
        //接口调用user_name的参数值
        if (!user_name_phone.equals("")) {
            user_name_key = user_name_phone;
        }

        //		SharedPreference spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
        //		Editor editor = spPreferences.edit();
        //		editor.putString("user", user_name_key);
        //		editor.putString("user_id", user_id);
        //		editor.commit();

        System.out.println("user_name================" + user_name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shenghuoguan_home);//new_ware_infromation  shenghuoguan_home activity_fdxq

        mAq = new AQuery(this);

        ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

        //返回
        fanhui = (Button) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

        try {
            //			RelativeLayout rl_title = (RelativeLayout)findViewById(R.id.rl_title);
            //			rl_title.getBackground().setAlpha(50);
            //		imageLoader.clearMemoryCache();
            progress = new DialogProgress(WareInformationActivity.this);
            progress.CreateProgress();

            innidata();
            String jdh_id = getIntent().getStringExtra("jdh_id");
            System.out.println("jdh_id==============" + jdh_id);
            if (jdh_id != null) {
                market_information_juduihuan.setVisibility(view.VISIBLE);
                market_information_bottom.setVisibility(view.GONE);
                //			loadJuDuiHuan(jdh_id);//获取聚兑换商品详情
                getjutuanxq(jdh_id);//获取聚兑换商品详情
            } else {
                market_information_juduihuan.setVisibility(view.GONE);
                market_information_bottom.setVisibility(view.VISIBLE);
                loadWeather();//获取商品详情
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * 分享
     * @param view2
     * @param context
     */
    //	private void SoftWarePopuWindow(View view2,final Context context) {
    //		try {
    //	    mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    ////		inflater = LayoutInflater.from(context);
    //		view = mLayoutInflater.inflate(R.layout.ware_infromation_share, null);
    //		pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
    //				LayoutParams.WRAP_CONTENT, false);
    //
    //		pop.setBackgroundDrawable(new BitmapDrawable());
    //		pop.setOutsideTouchable(true);
    //		// pop.setFocusable(true);
    //		// pop.setTouchable(true); // 设置PopupWindow可触摸
    //		//
    //		if (!pop.isShowing()) {
    //			 pop.showAtLocation(view2, Gravity.BOTTOM, 0, 0);
    //		}
    //		btn_wechat = (ImageButton) view.findViewById(R.id.img_btn_wechat);
    //		btn_wx_friend = (ImageButton) view.findViewById(R.id.img_btn_wx_friend);
    //		btn_sms = (ImageButton) view.findViewById(R.id.img_btn_sms);
    //	    img_btn_tencent = (ImageButton) view.findViewById(R.id.img_btn_tencent);
    //
    //		} catch (Exception e) {
    //
    //			e.printStackTrace();
    //		}
    //		//微博
    //		img_btn_tencent.setOnClickListener(new OnClickListener() {
    //
    //			@Override
    //			public void onClick(View arg0) {
    //				con(19, 1);
    //				}
    //		});
    //
    //		btn_wechat.setOnClickListener(new OnClickListener() {
    //			@Override
    //			public void onClick(View v) {
    //				pop.dismiss();
    //				// progress.CreateProgress();
    //				con(16,1);
    //			}
    //		});
    //		btn_wx_friend.setOnClickListener(new OnClickListener() {
    //
    //			@Override
    //			public void onClick(View v) {
    //
    //				pop.dismiss();
    //				con(17,1);
    //			}
    //		});
    //
    //		btn_sms.setOnClickListener(new OnClickListener() {
    //			@Override
    //			public void onClick(View v) {
    //
    //				pop.dismiss();
    //				con(18,0);
    //			}
    //		});
    //	}

    //	private void con(final int index, int type) {
    //		try {
    //
    //		String user_name = spPreferences.getString("user", "");
    //		String user_id = spPreferences.getString("user_id", "");
    //		String sp_id = lists.get(0).id;
    //		System.out.println("1=============="+sp_id);
    //		String data = proName+"http://183.62.138.31:1010/mobile/goods/show-"+sp_id+".html?user_id="+user_id+"&user_name="+user_name+"";
    ////		String data = proName+"http://183.62.138.31:1010/mobile/goods/show-"+sp_id+".html?user_id=19&user_name=13714758507";
    //		System.out.println("2=============="+data);
    //		Message msg = new Message();
    //		msg.what = index;
    ////		msg.obj = jsonObject.getString("data");
    //		msg.obj = data;
    //		handler.sendMessage(msg);
    //
    //	} catch (Exception e) {
    //
    //		e.printStackTrace();
    //	}
    //
    //	}

    /**
     * 获取聚兑换商品详情
     *
     * @param groupon_id
     * @param category_id
     */
    JuTuanGouData data_xq;
    JSONObject obj;
    public static ArrayList data_shuzu, data_monney, data_goods_id, data_market_price, data_people, data_goods_id_1, data_people_1, data_price, data_spec_text, data_mrz, data_exchange_price, data_exchange_point;

    private void getjutuanxq(String jdh_id) {
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
        //		list_ll = new ArrayList<JuTuanGouData>();
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_article_foreman_list?article_id=" + jdh_id + "&datatype=2&top=1"
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        System.out.println("输出内容详情=========" + arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            //					datetime = object.getString("datetime");
                            if (status.equals("y")) {
                                JSONObject jsonobt = object.getJSONObject("data");
                                obj = jsonobt.getJSONObject("article_model");
                                data_xq = new JuTuanGouData();
                                data_xq.setId(obj.getString("id"));
                                data_xq.setTitle(obj.getString("title"));
                                data_xq.setImg_url(obj.getString("img_url"));
                                data_xq.setAdd_time(obj.getString("add_time"));
                                data_xq.setStart_time(obj.getString("start_time"));
                                data_xq.setUpdate_time(obj.getString("update_time"));
                                data_xq.setCategory_id(obj.getString("category_id"));
                                data_xq.setEnd_time(obj.getString("end_time"));
                                data_xq.setCompany_id(obj.getString("company_id"));
                                sp_id = data_xq.getId();
                                point_id = obj.getString("id");
                                title = obj.getString("title");
                                title_jduihuan = obj.getString("title");
                                subtitle = obj.getString("subtitle");
                                proInverseImg = obj.getString("img_url");
                                company_id = obj.getString("company_id");
                                imgs_url = obj.getString("imgs_url");
                                proFaceImg = obj.getString("img_url");

                                JSONObject jsot = obj.getJSONObject("default_spec_item");
                                data_xq.setGoods_id(jsot.getString("goods_id"));
                                data_xq.setSell_price(jsot.getString("sell_price"));
                                data_xq.setArticle_id(jsot.getString("article_id"));
                                data_xq.setMarket_price(jsot.getString("market_price"));
                                data_xq.setCashing_packet(jsot.getString("cashing_packet"));
                                data_xq.setCashing_point(jsot.getString("cashing_point"));
                                data_xq.setSpec_text(jsot.getString("spec_text"));
                                data_xq.setExchange_point(jsot.getString("exchange_point"));
                                data_xq.setExchange_price(jsot.getString("exchange_price"));
                                point_ll = jsot.getString("exchange_price");
                                goods_price = jsot.getString("cashing_packet");
                                retailPrice = jsot.getString("sell_price");
                                price = jsot.getString("market_price");
                                spec_text = jsot.getString("spec_text");
                                goods_id = jsot.getString("goods_id");
                                article_id = jsot.getString("article_id");
                                exchange_point = jsot.getString("exchange_point");
                                exchange_price = jsot.getString("exchange_price");

                                //					        data_exchange_point.add(data_xq.exchange_point);
                                //					        data_exchange_price.add(data_xq.exchange_price);

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
                                    System.out.println("data.exchange_price=================" + data.exchange_price);
                                    System.out.println("data.spec_ids=================" + data.spec_ids);
                                    data_shuzu.add(data.spec_ids);
                                    data_monney.add(data.sell_price);
                                    data_market_price.add(data.market_price);
                                    data_goods_id.add(data.goods_id);
                                    data_spec_text.add(data.spec_text);
                                    data_exchange_point.add(data.exchange_point);
                                    data_exchange_price.add(data.exchange_price);


                                    //							JSONArray jsonArray2 = objt.getJSONArray("activity_price");
                                    //							for (int k = 0; k < jsonArray2.length(); k++) {
                                    //							JSONObject objet2 = jsonArray2.getJSONObject(k);
                                    //							JuTuanGouData data1 = new JuTuanGouData();
                                    //							data1.setGoods_id(objet2.getString("goods_id"));
                                    //							data1.setPeople(objet2.getString("people"));
                                    //							data1.setPrice(objet2.getString("price"));
                                    //							data_goods_id_1.add(data1.goods_id);
                                    //							data_people_1.add(data1.people);
                                    //							data_price.add(data1.price);
                                    //				            }
                                }
                                obj = null;

                            } else {
                                Toast.makeText(WareInformationActivity.this, info, Toast.LENGTH_SHORT).show();
                            }
                            webview.loadUrl(URLConstants.REALM_NAME_HTTP + "/mobile/goods/conent-" + data_xq.getArticle_id() + ".html");//商品介绍
                            //					userjubi();//获取聚币
                            handler.sendEmptyMessage(4);
                            progress.CloseProgress();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                }, null);
    }


    /**
     * 获取聚兑换商品详情
     * @param jdh_id
     */
    //	private void loadJuDuiHuan(String jdh_id) {
    //		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_game_point_content?id="+jdh_id+"",new AsyncHttpResponseHandler() {
    //							@Override
    //							public void onSuccess(int arg0, String arg1) {
    //
    //								super.onSuccess(arg0, arg1);
    //								System.out.println("=========解析数据============"+arg1);
    //								formaJuDuiHuan(arg1);
    //							}
    //
    //						}, null);
    //	}

    //	private void formaJuDuiHuan(String result) {
    //		 lists = new ArrayList<XiangqingData>();
    //		try {
    //			System.out.println("=======详情数据=="+result);
    //			JSONObject object = new JSONObject(result);
    //			String status = object.getString("status");
    //			String info = object.getString("info");
    //			if (status.equals("y")) {
    //			JSONObject jobt = object.getJSONObject("data");
    //			xqdata = new XiangqingData();
    //			point_id = jobt.getString("id");
    //			subtitle = jobt.getString("title");
    //			goods_price = jobt.getString("goods_price");
    //	        xqdata.setId(jobt.getString("id"));
    //	        proInverseImg = jobt.getString("img_url");
    //	        exchange_point =  jobt.getString("exchange_point");
    //	        goods_id = jobt.getString("goods_id");
    //	        article_id =  jobt.getString("article_id");
    //	        price = jobt.getString("exchange_price");
    //	        spec_text = jobt.getString("spec_text");
    //	        company_id = jobt.getString("company_id");
    //			System.out.println("=====article_id====================="+article_id);
    //			System.out.println("=====goods_id====================="+goods_id);
    //
    //			lists.add(xqdata);
    //			handler.sendEmptyMessage(4);
    //			userjubi();//获取聚币
    //    		progress.CloseProgress();
    //
    //			}else {
    //				progress.CloseProgress();
    //				Toast.makeText(WareInformationActivity.this, info, Toast.LENGTH_SHORT).show();
    //			}
    //		} catch (JSONException e) {
    //
    //			e.printStackTrace();
    //		}
    //	}

    /**
     * 获取商品详情
     */
    private void loadWeather() {
        String id = getIntent().getStringExtra("id");
        //		int  id = Integer.parseInt(getIntent().getStringExtra("id"));
        //		int  id = Integer.parseInt(id_ll);
        System.out.println("=========1============" + id);//5897
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_article_id_content?id=" + id + "", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String arg1) {

                super.onSuccess(arg0, arg1);
                System.out.println("=========解析数据============" + arg1);
                Message msg = new Message();
                msg.what = 007;
                msg.obj = arg1;
                handler.sendMessage(msg);

            }


        }, null);
    }

    private ArrayList data_id, data1;
    ArrayList<XiangqingData> list_tp;
    JSONObject jobt;
    JSONObject jsot;

    //	private String id;
    private void formatWeather(String result) {
        data_id = new ArrayList();
        data_shuzu = new ArrayList();
        data_mrz = new ArrayList();
        data_monney = new ArrayList();
        data_goods_id = new ArrayList();
        data_market_price = new ArrayList();
        data_spec_text = new ArrayList();
        data_exchange_price = new ArrayList();
        data_exchange_point = new ArrayList();
        lists = new ArrayList<XiangqingData>();
        try {
            System.out.println("=======详情数据==" + result);
            JSONObject object = new JSONObject(result);
            String status = object.getString("status");//
            String info = object.getString("info");//info
            if (status.equals("y")) {

                jobt = object.getJSONObject("data");
                xqdata = new XiangqingData();
                xqdata.setTitle(jobt.getString("title"));
                xqdata.setSubtitle(jobt.getString("subtitle"));
                xqdata.setId(jobt.getString("id"));
                xqdata.img_url = jobt.getString("img_url");
                company_id = jobt.getString("company_id");
                imgs_url = jobt.getString("imgs_url");
                title = xqdata.getTitle();
                subtitle = xqdata.getSubtitle();
                sp_id = xqdata.getId();

                jsot = jobt.getJSONObject("default_spec_item");
                xqdata.setGoods_id(jsot.getString("goods_id"));
                xqdata.setSell_price(jsot.getString("sell_price"));
                xqdata.setArticle_id(jsot.getString("article_id"));
                xqdata.setMarket_price(jsot.getString("market_price"));
                //			xqdata.setCashing_packet(jsot.getString("cashing_packet"));
                //			xqdata.setCashing_point(jsot.getString("cashing_point"));
                xqdata.setSpec_text(jsot.getString("spec_text"));
                xqdata.setExchange_point(jsot.getString("exchange_point"));
                xqdata.setExchange_price(jsot.getString("exchange_price"));
                point_ll = jsot.getString("exchange_price");
                goods_price = jsot.getString("cashing_packet");
                retailPrice = jsot.getString("sell_price");
                marketPrice = jsot.getString("market_price");
                spec_text = jsot.getString("spec_text");
                goods_id = jsot.getString("goods_id");
                article_id = jsot.getString("article_id");
                exchange_point = jsot.getString("exchange_point");
                exchange_price = jsot.getString("exchange_price");

                //			JSONObject job = jobt.getJSONObject("spec_item");
                JSONArray jsonay = jobt.getJSONArray("spec_item");

                for (int i = 0; i < jsonay.length(); i++) {
                    JSONObject objt = jsonay.getJSONObject(i);
                    //			xqdata.setSub_title(job.getString("sub_title"));
                    xqdata.setSpec_text(objt.getString("spec_text"));
                    xqdata.setSell_price(objt.getString("sell_price"));
                    xqdata.setMarket_price(objt.getString("market_price"));
                    xqdata.setCost_price(objt.getString("cost_price"));
                    xqdata.setRebate_price(objt.getString("rebate_price"));
                    xqdata.setSpec_ids(objt.getString("spec_ids"));
                    xqdata.setGoods_id(objt.getString("goods_id"));
                    xqdata.setArticle_id(objt.getString("article_id"));
                    xqdata.goods_id = objt.getString("goods_id");
                    xqdata.cashing_packet = objt.getString("cashing_packet");
                    xqdata.give_pension = objt.getString("give_pension");

                    //			spec_ids = xqdata.getSpec_ids();
                    //			proTip = xqdata.getSub_title();
                    //			retailPrice = xqdata.getSell_price();
                    //			marketPrice = xqdata.getMarket_price();
                    //			AvailableJuHongBao = xqdata.getCost_price();
                    //			Atv_integral = xqdata.getRebate_price();
                    //			goods_id = xqdata.getGoods_id();
                    //			article_id = xqdata.getArticle_id();
                    //			spec_text =xqdata.getSpec_text();
                    String is_default = objt.getString("is_default");

                    data_mrz.add(is_default);
                    data_shuzu.add(xqdata.spec_ids);
                    data_monney.add(xqdata.sell_price);
                    data_market_price.add(xqdata.market_price);
                    data_goods_id.add(xqdata.goods_id);
                    data_spec_text.add(xqdata.spec_text);
                    data_exchange_point.add(xqdata.exchange_point);
                    data_exchange_price.add(xqdata.exchange_price);
                    System.out.println("=========数据============" + spec_ids);
                    System.out.println("=========数据article_id============" + article_id);
                }
                JSONArray jsonArray = jobt.getJSONArray("albums");
                list_tp = new ArrayList<XiangqingData>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    XiangqingData data_tp = new XiangqingData();
                    data_tp.setThumb_path(obj.getString("thumb_path"));
                    data_tp.setOriginal_path(obj.getString("original_path"));
                    xqdata.setThumb_path(obj.getString("thumb_path"));
                    xqdata.setOriginal_path(obj.getString("original_path"));
                    //    		proFaceImg = xqdata.getThumb_path();
                    //    		proInverseImg = xqdata.getOriginal_path();
                    proFaceImg = obj.getString("thumb_path");
                    System.out.println("图片地址:" + proFaceImg);
                    proInverseImg = obj.getString("original_path");
                    list_tp.add(data_tp);
                }

                try {

                    JSONArray jsonArray1 = jobt.getJSONArray("param");
                    len = jsonArray1.length();
                    System.out.println("=========数据len============" + len);
                    System.out.println("=========数据len============" + jsonArray1);
                    if (len > 0) {
                        list_ggcs = new ArrayList<XiangqingData>();
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            XiangqingData data_ggcs = new XiangqingData();
                            JSONObject obj = jsonArray1.getJSONObject(i);
                            data_ggcs.setTitle(obj.getString("title"));
                            data_ggcs.setContent(obj.getString("content"));
                            list_ggcs.add(data_ggcs);
                        }
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

                lists.add(xqdata);
                System.out.println("list_tp.size()=========数据============" + list_tp.size());
                if (list_tp.size() > 0) {
                    //商品图片轮播
                    Message msg = new Message();
                    msg.obj = list_tp;
                    msg.what = 0;
                    childHandler.sendMessage(msg);
                } else {
                    mAq.id(ling_tip).image(URLConstants.REALM_NAME_HTTP + imgs_url);
                }
                handler.sendEmptyMessage(2);
                progress.CloseProgress();

            } else {
                progress.CloseProgress();
                Toast.makeText(WareInformationActivity.this, info, Toast.LENGTH_SHORT).show();
            }
            jobt = null;
            jsot = null;
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    ArrayList<XiangqingData> tempss;
    private Handler childHandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    tempss = (ArrayList<XiangqingData>) msg.obj;
                    ArrayList<String> urls = new ArrayList<String>();
                    for (int i = 0; i < tempss.size(); i++) {
                        urls.add(URLConstants.REALM_NAME_HTTP + tempss.get(i).getOriginal_path());
                        System.out.println("tempss=================" + tempss.get(i).getOriginal_path());
                    }

                    if (urls.size() == 1) {
                        mAq.id(ling_tip).image(URLConstants.REALM_NAME_HTTP + proInverseImg);
                    } else {
                        market_information_images.setData(urls, new MyPosterOnClick() {
                            @Override
                            public void onMyclick(int position) {

                                //						Message msg = new Message();
                                //						msg.what = 13;
                                //						msg.obj = tempss.get(position).getId();
                                //						handler.sendMessage(msg);
                            }
                        }, true, true);
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private void softshareWxChat(String text) {
        try {

            String temp[] = text.split("http");
            api = WXAPIFactory.createWXAPI(WareInformationActivity.this, Constants.APP_ID, false);
            api.registerApp(Constants.APP_ID);
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = "http" + temp[1];
            WXMediaMessage msg = new WXMediaMessage(webpage);
            //		msg.title = "我发你一个软件,看看呗!";
            msg.title = "云商聚的分享";
            msg.description = temp[0];
            Bitmap thumb = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.app_icon);
            msg.thumbData = Utils.bmpToByteArray(thumb, true);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;
            boolean flag = api.sendReq(req);

            System.out.println("微信註冊" + flag);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        market_information_images.puseExecutorService();
    }

    private Handler handler = new Handler() {
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 007:
                    formatWeather((String) msg.obj);
                    break;
                case 16:
                    System.out.println("到这里了16");
                    String text1 = (String) msg.obj;
                    softshareWxChat(text1);
                    break;
                case 4:
                    if (spjs == 1) {
                        //				webview.loadUrl("http://183.62.138.31:1010/mobile/goods/conent-"+article_id+".html");//商品介绍
                        webview.loadUrl("http://mobile.zams.cn/goods/conent-" + article_id + ".html");
                    }
                    //				market_information_tip.setText(subtitle);
                    market_information_title.setText(title + "");
                    tv_hengyu_money.setText(exchange_point + "分");
                    tv_ware_market_jifen.setText("¥" + price);
                    tv_ware_market_jifen.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    ll_money_ju.setVisibility(View.GONE);
                    tv_spec_text.setText(spec_text);
                    //				market_information_juduihuan.setVisibility(view.VISIBLE);
                    //				market_information_bottom.setVisibility(view.GONE);

                    ling_tip.setVisibility(view.VISIBLE);
                    System.out.println("proInverseImg===================" + proInverseImg);
                    mAq.id(ling_tip).image(URLConstants.REALM_NAME_HTTP + proInverseImg);
                    tv_wenzi1.setText("福利兑换:");
                    tv_wenzi2.setText("市场价:");

                    //福利兑换
                    market_information_juduihuan.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            SharedPreferences spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
                            user_point = spPreferences.getString("point", "");
                            System.out.println("user_point==================" + user_point);
                            System.out.println("exchange_point========1==========" + exchange_point);
                            if (!user_name.equals("")) {
                                if (user_point.equals("")) {
                                    Toast.makeText(WareInformationActivity.this, "请重新登录获取用户福利", Toast.LENGTH_SHORT).show();
                                } else {
                                    try {
                                        int jubi = Integer.parseInt(exchange_point);
                                        int dq_jubi = Integer.parseInt(user_point);
                                        //							int dq_jubi =  10000;
                                        System.out.println("jubi==================" + jubi);
                                        System.out.println("dq_jubi===================" + dq_jubi);
                                        if (dq_jubi >= jubi) {
                                            fangshi = 3;//销售属性判断
                                            title_jdh = "2";
                                            taocan_type = true;//福利兑换判断套餐价格显示
                                            spec_text_list = 1;//销售套餐判断为1
                                            jdh_type = getIntent().getStringExtra("jdh_type");//乐豆兑换调用接口
                                            System.out.println("jdh_type===================" + jdh_type);
                                            CommomConfrim.showSheet(WareInformationActivity.this, article_id);
                                            progress.CloseProgress();
                                            //								Intent intent=new Intent(WareInformationActivity.this, MyOrderConfrimActivity.class);
                                            //								intent.putExtra("Toast.LENGTH_SHORT", "Toast.LENGTH_SHORT");
                                            //								intent.putExtra("point_id",point_id);
                                            //								intent.putExtra("img_url",proInverseImg);
                                            //								intent.putExtra("point",exchange_point);
                                            //								intent.putExtra("goods_price",goods_price);
                                            //								intent.putExtra("jdh_title",subtitle);
                                            //								intent.putExtra("price",price);
                                            //								startActivity(intent);
                                        } else {
                                            Toast.makeText(WareInformationActivity.this, "您当前的福利不够兑换", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {

                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                Intent intent = new Intent(WareInformationActivity.this, UserLoginActivity.class);
                                startActivity(intent);
                                progress.CloseProgress();
                            }
                        }
                    });

                    break;
                case 2:
                    try {
                        //					market_information_juduihuan.setVisibility(view.GONE);
                        //					market_information_bottom.setVisibility(view.VISIBLE);

                        System.out.println("值===================" + retailPrice);
                        if (spjs == 1) {
                            //					webview.loadUrl("http://183.62.138.31:1010/mobile/goods/conent-"+article_id+".html");//商品介绍
                            webview.loadUrl("http://mobile.zams.cn/goods/conent-" + article_id + ".html");
                        }
                        tv_hengyu_money.setText("¥" + retailPrice);
                        tv_market_money.setText("¥" + marketPrice);
                        market_information_title.setText(title);
                        //				market_information_tip.setText(subtitle);//proTip
                        tv_spec_text.setText(spec_text);

                        String hongbao = getIntent().getStringExtra("hongbao");
                        System.out.println("xqdata.cashing_packet==================================" + xqdata.cashing_packet);
                        if (xqdata.cashing_packet.equals("0.0")) {
                            tv_ware_market_jifen.setVisibility(view.GONE);
                            tv_wenzi2.setVisibility(view.GONE);
                        } else {
                            tv_ware_market_jifen.setVisibility(view.VISIBLE);
                            tv_wenzi2.setVisibility(view.VISIBLE);
                            tv_ware_market_jifen.setText("¥" + xqdata.cashing_packet);
                        }
                        //				tv_ware_market_jifen.setText( "¥" + xqdata.cashing_packet);
                        //				tv_integral.setText( "¥" + xqdata.give_pension);


                        //				ArrayList<String> images_ll = getDatall();
                        //				imageLoader.clearMemoryCache();
                        //				System.out.println("=========图片值============"+images_ll.size());
                        //
                        //				//动态广告
                        //				if(images_ll.size()==1){
                        //					market_information_images.setData(images_ll,new MyPosterOnClick() {
                        //
                        //								@Override
                        //								public void onMyclick(int position) {
                        //
                        //
                        //								}
                        //							}, true, imageLoader, false);
                        //				}else{
                        //					market_information_images.setData(images_ll,new MyPosterOnClick() {
                        //
                        //								@Override
                        //								public void onMyclick(int position) {
                        //
                        //
                        //								}
                        //							}, true, imageLoader, true);
                        //				}

                        progress.CloseProgress();
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "成功加入购物车", Toast.LENGTH_SHORT).show();
                    progress.CloseProgress();
                    // TODO: 2018/4/25  聚乐购
//                    try {
//
//                        AppManager.getAppManager().finishActivity(
//                                WareClassifyFourActivity.class);
//                        AppManager.getAppManager().finishActivity(
//                                WareClassifyThreeActivity.class);
//                        AppManager.getAppManager().finishActivity(
//                                WareClassifyTwoActivity.class);
//                        AppManager.getAppManager().finishActivity(NewWare.class);
//                    } catch (ConcurrentModificationException e) {
//
//
//                    }
//                    setResult(3, new Intent(WareInformationActivity.this,
//                            MainFragment.class));
//                    AppManager.getAppManager().finishActivity();
                    break;
                case 0:
                    //				formatWeatherll((String) msg.obj);
                    break;
                case 3:
                    String str = (String) msg.obj;
                    progress.CloseProgress();
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    break;
                case 20:
                    String mess = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    break;
                case 5:
                    //				adapter = new GuigeListAdapter(WareInformationActivity.this,list,data);
                    //				listview_01.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    System.out.println("=====5=====================");
                    break;
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 获取当前聚币
     *
     * @param
     */
    private void userjubi() {
        String strUrlone = URLConstants.REALM_NAME_LL + "/get_user_model?username=" + user_name + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    if (status.equals("y")) {
                        JSONObject obj = object.getJSONObject("data");
                        point = obj.getString("point");
                        System.out.println("point-------------" + point);
                    } else {
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            ;
        }, null);

    }
    //	private void load() throws JSONException, Exception {
    ////		String str2 = RealmName.REALM_NAME + "/mi/getdata.ashx";
    ////
    ////		Map<String, String> params = new HashMap<String, String>();
    ////		params.put("act", "myInfo");
    ////		params.put("key", registerData.getUserkey());
    ////		params.put("yth", yth);
    ////		AsyncHttp.post_1(str2, params, new AsyncHttpResponseHandler() {
    ////			@Override
    ////			public void onSuccess(int arg0, String arg1) {
    ////
    ////				super.onSuccess(arg0, arg1);
    ////				UserRegisterData data2 = null;
    ////				try {
    ////					JSONObject object2 = new JSONObject(arg1);
    ////					data2 = new UserRegisterData();
    ////					data2.hengyuCode = object2.getString("HengYuCode");
    ////					data2.userName = object2.getString("username");
    ////					data2.PassTicketBalance = object2
    ////							.getString("PassTicketBalance");
    ////					data2.shopPassTicket = object2.getString("shopPassTicket");
    ////					data2.avatarimageURL = object2.getString("avatarimageURL");
    ////					data2.credits = object2.getString("credits");
    ////				} catch (JSONException e) {
    ////
    ////					e.printStackTrace();
    ////				}
    ////				Message msg = new Message();
    ////				msg.what = 1;
    ////				msg.obj = data2;
    ////				handler.sendMessage(msg);
    ////			}
    ////		});
    //
    //
    //	}

    private void loadgouwuche() {
        try {
            progress.CreateProgress();
            spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
            String user_name = spPreferences.getString(SpConstants.USER_NAME, "");
            String user_id = spPreferences.getString("user_id", "");

            System.out.println("=====user_id=====================" + article_id);
            System.out.println("=====goods_id=====================" + goods_id);
            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/add_shopping_buy?user_id=" + user_id + "&user_name=" + user_name_key +
                    "&article_id=" + article_id + "&goods_id=" + goods_id + "&quantity=" + 1 + "", new AsyncHttpResponseHandler() {
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
                            String count = obj.getString("count");
                            //									Toast.makeText(WareInformationActivity.this, info, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(WareInformationActivity.this, MyOrderConfrimActivity.class);
                            intent.putExtra("shopping_ids", id);
                            startActivity(intent);
                        } else {
                            progress.CloseProgress();
                            Toast.makeText(WareInformationActivity.this, info, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

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

    private void loadWeatherll() {

        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_article_spec_list?" +
                "channel_name=goods", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String arg1) {

                super.onSuccess(arg0, arg1);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = arg1;
                handler.sendMessage(msg);
            }
        }, null);
    }

    ArrayList<GuigeData> list = new ArrayList<GuigeData>();

    private GuigeListAdapter adapter;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getAppManager().finishActivity();
        }
        return true;
    }

    private void innidata() {
        ling_tip = (ImageView) findViewById(R.id.ling_tip);
        bt_cart_all = (TextView) findViewById(R.id.bt_cart_all);
        bt_cart_low = (TextView) findViewById(R.id.bt_cart_low);
        show_cart_all = (TextView) findViewById(R.id.show_cart_all);
        show_cart_low = (TextView) findViewById(R.id.show_cart_low);
        ll_shiyishicai1 = (LinearLayout) findViewById(R.id.ll_shiyishicai1);
        ll_shiyishicai2 = (LinearLayout) findViewById(R.id.ll_shiyishicai2);
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new JavascriptHandler(), "handler");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });

        bt_cart_all.setOnClickListener(this);
        bt_cart_low.setOnClickListener(this);
        new_list = (ListView) findViewById(R.id.new_list);
        img_shared = (ImageView) findViewById(R.id.img_shared);
        img_shared.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {


                try {
                    if (!TextUtils.isEmpty(nickname)) {
                        if (!TextUtils.isEmpty(user_name)) {
                            if (UserLoginActivity.wx_fanhui == false) {
                                Intent intent5 = new Intent(WareInformationActivity.this, UserLoginActivity.class);
                                startActivity(intent5);
                            } else {
                                Intent intentll = new Intent(WareInformationActivity.this, DBFengXiangActivity.class);
                                intentll.putExtra("sp_id", sp_id);
                                intentll.putExtra("company_id", company_id);
                                intentll.putExtra("title", title);
                                intentll.putExtra("subtitle", subtitle);
                                intentll.putExtra("img_url", imgs_url);
                                //							title = obj.getString("title");
                                //							subtitle = obj.getString("subtitle");
                                //					        imgs_url = obj.getString("imgs_url");
                                startActivity(intentll);
                            }
                        } else {
                            Intent intent2 = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
                            startActivity(intent2);
                            progress.CloseProgress();
                        }
                    } else {

                        if (TextUtils.isEmpty(user_name)) {
                            Intent intent = new Intent(WareInformationActivity.this, UserLoginActivity.class);
                            startActivity(intent);
                            progress.CloseProgress();
                        } else {
                            //					SoftWarePopuWindow(img_shared, context);
                            if (UserLoginActivity.wx_fanhui == false) {
                                Intent intent5 = new Intent(WareInformationActivity.this, UserLoginActivity.class);
                                startActivity(intent5);
                            } else {
                                Intent intentll = new Intent(WareInformationActivity.this, DBFengXiangActivity.class);
                                intentll.putExtra("sp_id", sp_id);
                                intentll.putExtra("company_id", company_id);
                                intentll.putExtra("title", title);
                                intentll.putExtra("subtitle", subtitle);
                                intentll.putExtra("img_url", imgs_url);
                                startActivity(intentll);
                            }
                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });

        order_shop_now = (LinearLayout) findViewById(R.id.order_shop_now);
        btn_add_shop_cart = (Button) findViewById(R.id.btn_add_shop_cart);
        // btn_ware_infromation_share = (ImageButton)
        // findViewById(R.id.img_btn_ware_infromation_share);
        // btn_ware_infromation_share.setOnClickListener(this);
        btn_collect = (LinearLayout) findViewById(R.id.btn_collect);
        btn_collect.setOnClickListener(this);
        btn_dianping = (LinearLayout) findViewById(R.id.btn_dianping);
        market_information_param = (LinearLayout) findViewById(R.id.market_information_param);
        market_information_sep = (LinearLayout) findViewById(R.id.market_information_sep);

        btn_dianping.setOnClickListener(this);

        //		if (getIntent().hasExtra("vip")) {
        //			btn_dianping.setVisibility(View.INVISIBLE);
        //			btn_collect.setVisibility(View.INVISIBLE);
        //			btn_add_shop_cart.setText("升级VIP套餐");
        //		}

        market_information_describe = (LinearLayout) findViewById(R.id.market_information_describe);
        images_layout = (LinearLayout) findViewById(R.id.images_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;// 宽度height = dm.heightPixels ;
        images_layout.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, widthPixels));
        tv_ware_market_jifen = (TextView) findViewById(R.id.tv_ware_market_jifen);
        tv_integral = (TextView) findViewById(R.id.tv_integral);
        tv_market_money = (TextView) findViewById(R.id.tv_ware_market_money);
        market_information_title = (TextView) findViewById(R.id.market_information_title);
        market_information_tip = (TextView) findViewById(R.id.market_information_tip);
        tv_hengyu_money = (TextView) findViewById(R.id.tv_ware_hengyu_money);
        tv_wenzi1 = (TextView) findViewById(R.id.tv_wenzi1);
        tv_wenzi2 = (TextView) findViewById(R.id.tv_wenzi2);
        tv_spec_text = (TextView) findViewById(R.id.tv_spec_text);
        tv_market_money.getPaint().setFlags(
                Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置文字的中划线
        market_information_images = (MyPosterView) findViewById(R.id.market_information_images);
        enter_shop = (Button) findViewById(R.id.enter_shop);
        ll_money_ju = (LinearLayout) findViewById(R.id.ll_money_ju);
        market_information_juduihuan = (LinearLayout) findViewById(R.id.market_information_juduihuan);
        market_information_bottom = (LinearLayout) findViewById(R.id.market_information_bottom);

        //		//返回
        //		fanhui = (Button) findViewById(R.id.fanhui);
        //		fanhui.setOnClickListener(new OnClickListener() {
        //
        //					@Override
        //					public void onClick(View arg0) {
        //
        //						finish();
        //					}
        //				});

        //跳转到购物车
        enter_shop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                System.out.println("点击");
                if (!nickname.equals("")) {
                    if (!user_name.equals("")) {
                        // TODO: 2018/4/25 去购物车
                        Toast.makeText(context, "去购物车", Toast.LENGTH_SHORT).show();
//						Intent Intent2 = new Intent(WareInformationActivity.this,MyShopCarActivity.class);
//						startActivity(Intent2);
                    } else {
                        Intent intent2 = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
                        startActivity(intent2);
                        progress.CloseProgress();
                    }
                } else {
                    if (user_name.equals("")) {
                        Intent intent = new Intent(WareInformationActivity.this, UserLoginActivity.class);
                        startActivity(intent);
                        progress.CloseProgress();
                    } else {
                        // TODO: 2018/4/25 去购物车
                        Toast.makeText(context, "去购物车2", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(WareInformationActivity.this,MyShopCarActivity.class);
//						startActivity(intent);

                    }
                }
            }
        });


        //商品介绍
        market_information_describe.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //				Intent intent = new Intent(WareInformationActivity.this,WareInformationDetailsActivity.class);
                //				Bundle bundle = new Bundle();
                //				bundle.putInt("wareid", getIntent().getIntE-------------xtra("id", -1));
                //				intent.putExtras(bundle);
                //				startActivity(intent);/-
                //				lists.get(index).getGoods_id();

                //				System.out.println("================id="+lists.get(0).id);
                //				Intent intent= new Intent(WareInformationActivity.this,Webview1.class);
                //				intent.putExtra("spjs", lists.get(0).id);
                //				startActivity(intent);
            }
        });

        //加入购物车
        btn_add_shop_cart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //				try{
                //
                //					if (!user_name.equals("")) {
                //
                //						CommomConfrim.showSheet(WareInformationActivity.this, new onDeleteSelect() {
                //							@Override
                //							public void onClick(String resID) {
                //
                //		  					  }
                //						     }, lists.get(0).id);
                //
                //					} else {
                //						Intent intent = new Intent(WareInformationActivity.this,UserLoginActivity.class);
                //						startActivity(intent);
                //						progress.CloseProgress();
                //					}
                //
                //				} catch (Exception e) {
                //
                //					e.printStackTrace();
                //				}

                try {
                    System.out.println("-------nickname-------------" + nickname);
                    System.out.println("--------user_name------------" + user_name);
                    if (!nickname.equals("")) {
                        if (!user_name.equals("")) {
                            fangshi = 2;
                            taocan_type = false;//判断商品套餐价格
                            spec_text_list = 1;//销售套餐判断为1
                            jdh_type = "";
                            CommomConfrim.showSheet(WareInformationActivity.this, lists.get(0).id);
                            progress.CloseProgress();
                        } else {
                            Intent intent2 = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
                            startActivity(intent2);
                            progress.CloseProgress();
                        }
                    } else {
                        if (!user_name.equals("")) {
                            fangshi = 2;
                            taocan_type = false;//判断商品套餐价格
                            spec_text_list = 1;//销售套餐判断为1
                            jdh_type = "";
                            CommomConfrim.showSheet(WareInformationActivity.this, lists.get(0).id);
                            progress.CloseProgress();
                        } else {
                            Intent intent = new Intent(WareInformationActivity.this, UserLoginActivity.class);
                            startActivity(intent);
                            progress.CloseProgress();
                        }
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        });
        //选择:颜色/分类/套餐
        market_information_sep.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //				initPopupWindow(rads, 0);
                //				showPopupWindow(btn_add_shop_cart);
// TODO: 2018/4/25  选择商品数量
                Toast.makeText(WareInformationActivity.this, "选择商品数量", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(WareInformationActivity.this, SYBActivity.class);
//                intent.putExtra("proName", title);
//                intent.putExtra("proFaceImg", proFaceImg);
//                intent.putExtra("retailPrice", retailPrice);
//                intent.putExtra("spec_ids", spec_ids);
//                intent.putExtra("goods_id", goods_id);
//                intent.putExtra("article_id", article_id);
//                startActivity(intent);
            }
        });

        //属性
        //		market_information_param.setOnClickListener(new OnClickListener() {
        //
        //			@Override
        //			public void onClick(View arg0) {
        //
        //			Intent intent=new Intent(WareInformationActivity.this, SYBActivity.class);
        //			intent.putExtra("proName", title);
        //			intent.putExtra("proFaceImg", proFaceImg);
        //			intent.putExtra("retailPrice", retailPrice);
        //			intent.putExtra("spec_ids", spec_ids);//data_shuzu
        //			intent.putExtra("goods_id",goods_id);
        //			intent.putExtra("article_id",article_id);
        //			startActivity(intent);
        //			}
        //		});

        //立刻购买
        order_shop_now.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!nickname.equals("")) {
                    if (!user_name.equals("")) {
                        fangshi = 1;//销售套餐购买判断为1
                        spec_text_list = 1;//销售套餐判断为1
                        taocan_type = false;//判断商品套餐价格
                        jdh_type = "";
                        CommomConfrim.showSheet(WareInformationActivity.this, lists.get(0).id);
                        progress.CloseProgress();
                    } else {
                        Intent intent2 = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
                        startActivity(intent2);
                        progress.CloseProgress();
                    }
                } else {
                    if (!user_name.equals("")) {
                        //				loadgouwuche();//立即购买加入购物车
                        fangshi = 1;//销售套餐购买判断为1
                        spec_text_list = 1;//销售套餐判断为1
                        taocan_type = false;//判断商品套餐价格
                        jdh_type = "";
                        CommomConfrim.showSheet(WareInformationActivity.this, lists.get(0).id);
                        progress.CloseProgress();
                    } else {
                        Intent intent = new Intent(WareInformationActivity.this, UserLoginActivity.class);
                        startActivity(intent);
                        progress.CloseProgress();
                    }

                }

            }
        });


    }


    private ArrayList<String> getDatall() {
        ArrayList<String> list = new ArrayList<String>();
        if (!"".equals(proFaceImg)) {
            list.add(URLConstants.REALM_NAME_HTTP + proFaceImg);
        }
        if (!"".equals(proInverseImg)) {
            list.add(URLConstants.REALM_NAME_HTTP + proInverseImg);
        }
        //		if (!"".equals(proDoDetailImg)) {
        //			list.add(RealmName.REALM_NAME + "/admin/" + proDoDetailImg);
        //		}
        //		if (!"".equals(proDesignImg)) {
        //			list.add(RealmName.REALM_NAME + "/admin/" + proDesignImg);
        //		}
        //		if (!"".equals(proSupplementImg)) {
        //			list.add(RealmName.REALM_NAME + "/admin/" + proSupplementImg);
        //		}
        return list;
    }

    private ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<String>();
        if (!"".equals(proFaceImg)) {
            list.add(URLConstants.REALM_URL + "/admin/" + proFaceImg);
        }
        if (!"".equals(proInverseImg)) {
            list.add(URLConstants.REALM_URL + "/admin/" + proInverseImg);
        }
        if (!"".equals(proDoDetailImg)) {
            list.add(URLConstants.REALM_URL + "/admin/" + proDoDetailImg);
        }
        if (!"".equals(proDesignImg)) {
            list.add(URLConstants.REALM_URL + "/admin/" + proDesignImg);
        }
        if (!"".equals(proSupplementImg)) {
            list.add(URLConstants.REALM_URL + "/admin/" + proSupplementImg);
        }
        return list;
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    private String processParam(String temp)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(temp, "UTF-8");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //商品详情
            case R.id.bt_cart_all:
                System.out.println("1================");
                ll_shiyishicai1.setVisibility(View.VISIBLE);
                ll_shiyishicai2.setVisibility(View.GONE);
                bt_cart_all.setTextColor(Color.RED);
                bt_cart_low.setTextColor(Color.GRAY);
                //			show_cart_all.setTextColor(Color.RED);
                //			show_cart_low.setTextColor(Color.GRAY);
                show_cart_all.setBackgroundColor(getResources().getColor(R.color.hongse));
                show_cart_low.setBackgroundColor(getResources().getColor(R.color.all_c1c1c1));
                spjs = 0;
                //			shangpingjieshan();
                break;
            //规格参数
            case R.id.bt_cart_low:
                try {
                    System.out.println("len================" + len);
                    ll_shiyishicai1.setVisibility(View.GONE);
                    ll_shiyishicai2.setVisibility(View.VISIBLE);
                    bt_cart_all.setTextColor(Color.GRAY);
                    bt_cart_low.setTextColor(Color.RED);
                    show_cart_all.setTextColor(Color.GRAY);
                    show_cart_low.setTextColor(Color.RED);
                    show_cart_all.setBackgroundColor(getResources().getColor(R.color.all_c1c1c1));
                    show_cart_low.setBackgroundColor(getResources().getColor(R.color.hongse));
                    if (len > 0) {
                        GoodsGgcsListAdapter ggcsadapter = new GoodsGgcsListAdapter(list_ggcs, getApplicationContext());
                        new_list.setAdapter(ggcsadapter);
                        setListViewHeightBasedOnChildren(new_list);
                    } else {
                        //				loadguigecanshu();
                        //				Toast.makeText(WareInformationActivity.this, "无规格参数", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
                break;
            case R.id.btn_collect://收藏

                progress.CreateProgress();
                //			String  id = UserLoginActivity.id;
                //			String  user_name = UserLoginActivity.user_name;
                if (!nickname.equals("")) {
                    if (!user_name.equals("")) {
                        String article_id = getIntent().getStringExtra("id");
                        //					System.out.println("1================"+user_id);
                        //					System.out.println("2================"+user_name);
                        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/user_favorite?article_id=" + article_id + "&goods_id=" + goods_id + "&user_name=" + user_name_key + "" +
                                "&user_id=" + user_id + "&tags=", new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String arg1) {

                                super.onSuccess(arg0, arg1);
                                try {
                                    JSONObject jsonObject = new JSONObject(arg1);
                                    System.out.println("收藏================" + arg1);
                                    progress.CloseProgress();
                                    String info = jsonObject.getString("info");
                                    Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }

                            }
                        }, getApplicationContext());
                    } else {
                        Intent intent2 = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
                        startActivity(intent2);
                        progress.CloseProgress();
                    }
                } else {
                    if (user_name.equals("")) {
                        Intent intent = new Intent(WareInformationActivity.this, UserLoginActivity.class);
                        startActivity(intent);
                        progress.CloseProgress();
                    } else {

                        String article_id = getIntent().getStringExtra("id");
                        System.out.println("1================" + user_id);
                        System.out.println("2================" + user_name);

                        //			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/user_favorite?article_id="+article_id+"&user_name="+user_name_key+"" +
                        //					"&user_id="+user_id+"&tags=",
                        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/user_favorite_goods?article_id=" + article_id + "&goods_id=" + goods_id + "&user_name=" + user_name_key + "" +
                                        "&user_id=" + user_id + "&tags=",
                                new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int arg0, String arg1) {

                                        super.onSuccess(arg0, arg1);
                                        try {
                                            JSONObject jsonObject = new JSONObject(arg1);
                                            System.out.println("收藏================" + arg1);
                                            progress.CloseProgress();
                                            String info = jsonObject.getString("info");
                                            Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }

                                    }
                                }, getApplicationContext());

                    }
                }
                break;
            case R.id.btn_dianping://点评
                progress.CreateProgress();
                //			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
                //			user_name = spPreferences.getString("user", "");
                //			id = spPreferences.getString("user_id", "");
                if (!nickname.equals("")) {
                    if (!user_name.equals("")) {
                        // TODO: 2018/4/25 去评价页面 点评
                        Toast.makeText(this, "点评", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(WareInformationActivity.this, DianPingActivity.class);
//                        intent.putExtra("article_id", sp_id);
//                        startActivity(intent);
                        progress.CloseProgress();
                    } else {
                        Intent intent2 = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
                        startActivity(intent2);
                        progress.CloseProgress();
                    }
                } else {
                    if (!user_name.equals("")) {
                        // TODO: 2018/4/25 去评价页面 点评
                        Toast.makeText(this, "点评", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(WareInformationActivity.this, DianPingActivity.class);
//                        intent.putExtra("article_id", sp_id);
//                        startActivity(intent);
                        progress.CloseProgress();
                    } else {
                        UIHelper.showUserLoginActivity(this);
                        progress.CloseProgress();
                    }
                }

                break;
            default:
                break;
        }
    }

    //商品介绍网页端
    private void shangpingjieshan() {
        //		webview.loadUrl("http://183.62.138.31:1010/mobile/goods/conent-"+article_id+".html");//商品介绍
        webview.loadUrl("http://mobile.zams.cn/goods/conent-" + article_id + ".html");
    }

    /**
     * 解析规格列表数据
     */
    ArrayList data;

    private void loadguigecanshu() {
        //		progress.CreateProgress();
        data = new ArrayList();
        list_ll = new ArrayList<GuigeData>();
        list_lb = new ArrayList<GuigeData>();
        String article_id = WareInformationActivity.article_id;
        System.out.println("article_id==========================" + article_id);
        //		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_spec_list?" +"channel_name=goods",
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_spec_list?" + "article_id=" + article_id + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        System.out.println("=====规格数据=====================" + arg1);

                        try {
                            JSONObject object = new JSONObject(arg1);
                            JSONArray jobt = object.getJSONArray("data");
                            for (int i = 0; i < jobt.length(); i++) {
                                JSONObject obj = jobt.getJSONObject(i);
                                md = new GuigeData();
                                md.setTitle(obj.getString("title"));
                                String title = md.getTitle();
                                //							String cars = obj.getString("child");
                                System.out.println("=====1值=====================" + title);
                                JSONArray jaArray = obj.getJSONArray("child");
                                md.setList(new ArrayList<GuigeBean>());
                                for (int j = 0; j < jaArray.length(); j++) {
                                    JSONObject objc = jaArray.getJSONObject(j);
                                    mb = new GuigeBean();
                                    mb.setTitle(objc.getString("title"));
                                    String zhou = mb.getTitle();
                                    System.out.println("=====2值=====================" + zhou);
                                    data.add(zhou);

                                    md.getList().add(mb);
                                }
                                list.add(md);
                            }
                            //							CanshuGuiGeAdapter jysadapter = new CanshuGuiGeAdapter(list,lists_ll,getApplicationContext());
                            //							new_list.setAdapter(jysadapter);
                            //							shangpingcsAdapter jysadapter = new shangpingcsAdapter(list,data,getApplicationContext());
                            //							new_list.setAdapter(jysadapter);
                            System.out.println("=====值1=====================");
                            //							WideMarketAdapter adapter = new WideMarketAdapter(list,getApplicationContext(), handler);
                            //							new_list.setAdapter(adapter);
                            //							setListViewHeightBasedOnChildren(new_list);
                            //							adapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, null);
    }


    class JavascriptHandler {
        @JavascriptInterface
        public void getContent(String htmlContent) {
        }
    }

    public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript:window.handler.getContent(document.body.innerHTML);");
    }

    LayoutInflater mLayoutInflater;
    PopupWindow mPopupWindow;


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
    /**
     * 立即购买
     */
    //	private void addNow() {
    //		List<UserRegisterData> username = wareDao.findisLogin();
    //		Log.v("number", username.size() + "");
    //		if (username.size() != 0) {
    //			if (style_name.size() >= 2) { // 当商品只有两条属性时
    //				if (propits[0] == null || propits[1] == null) {
    //					Toast.makeText(getApplicationContext(), "请选择商品的属性", Toast.LENGTH_SHORT)
    //							.show();
    //				} else {
    //					stylename1 = style_name.get(0).toString();
    //					stylename2 = style_name.get(1).toString();
    //					stylenature1 = propits[0].toString();
    //					stylenature2 = propits[1].toString();
    //					go();
    //				}
    //			} else if (style_name.size() == 0) { // 当商品没有属性时
    //				stylename1 = "";
    //				stylename2 = "";
    //				stylenature1 = "";
    //				stylenature2 = "";
    //				go();
    //			} else if (style_name.size() == 1) { // 当商品没有属性时
    //				if (propits[0] == null  ) {
    //					Toast.makeText(getApplicationContext(), "请选择商品的属性", Toast.LENGTH_SHORT)
    //							.show();}
    //				else {
    //					stylename1 = style_name.get(0).toString();
    //					stylename2 = "";
    //					stylenature1 = propits[0].toString();
    //					stylenature2 = "";
    //					go();
    //				}
    //
    //
    //			}
}
