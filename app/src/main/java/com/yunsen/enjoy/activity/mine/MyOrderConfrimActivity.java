package com.yunsen.enjoy.activity.mine;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import com.androidquery.AQuery;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.adapter.ShopingCartOrderAdapter;
import com.yunsen.enjoy.activity.pay.TishiCarArchivesActivity;
import com.yunsen.enjoy.activity.pay.ZhiFuFangShiActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.JuTuanGouData;
import com.yunsen.enjoy.model.ShopCartData;
import com.yunsen.enjoy.model.ShopCarts;
import com.yunsen.enjoy.model.UserAddressData;
import com.yunsen.enjoy.model.UserRegisterData;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.thirdparty.Common;
import com.yunsen.enjoy.thirdparty.PayResult;
import com.yunsen.enjoy.thirdparty.alipay.SignUtils;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.InScrollListView;
import com.yunsen.enjoy.widget.MyPopupWindowMenu;
import com.yunsen.enjoy.widget.SlipButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * 订单确认
 *
 * @author Administrator
 */
public class MyOrderConfrimActivity extends AppCompatActivity {
    private ListView list_address_a;
    private String yth, key, phone, imei, tel, endmoney, pwd, username,
            PassTicket, shopPassTicket, orderSerialNumber;
    private int addressid;
    private WareDao wareDao;
    private UserRegisterData registerData;
    private String strUrl;
    private ArrayList<ShopCartData> list;
    private DialogProgress progress;
    private int checkedAddressId;
    private StringBuilder orderid;
    private MyPopupWindowMenu popupWindowMenu;
    private String trade_no;
    private ArrayList<CardItem> banks = null;
    private String bankNames[] = null;
    private static final int REQUESTCODE = 10000;
    private int jf, express_fee;
    private ArrayList<ShopCarts> carts;
    private TextView tv_user_name, tv_user_address, tv_user_phone, tv_hongbao;
    private SharedPreferences spPreferences;
    public static String user_name, user_id;
    private ImageButton btn_add_address;
    private ShopingCartOrderAdapter adapter;
    private InScrollListView list_shop_cart;
    ArrayList<ShopCartData> list_ll = new ArrayList<ShopCartData>();
    private ShopCartData data;
    private ShopCartData dm;
    private LinearLayout layout0, ll_ljgm, layout2, ll_zhifufs;
    private RelativeLayout layout1, rl_hongbao;
    private TextView heji, tv_1, tv_2;
    private Button confrim_btn;
    private LinearLayout yu_pay0, yu_pay1, yu_pay2;
    private CheckBox yu_pay_c0, yu_pay_c1, yu_pay_c2;
    String jiekou_type_ysj;
    String jiekou_type;
    ImageView img_ware;
    TextView tv_warename, tv_jiaguo, tv_hb_ye, tv_hb_ye_2, tv_hb_ye_3;
    TextView tv_color;
    Activity activity;
    TextView tv_size, tv_zhifu;
    String name = "";
    private String ZhiFuFangShi, express_id;
    private String retailPrice, jubi;
    String type = "5";
    String order_str, notify_url;
    int zhifu, kou_hongbao;
    private IWXAPI api;
    private String partner_id, prepayid, noncestr, timestamp, package_, sign;
    private String user_dizhiname;
    boolean zhuangtai = false;
    public static boolean teby = false;
    private int len;
    String url;
    String login_sign, dandu_goumai;
    boolean flag;
    boolean hongbao_tety = true;
    public static double dzongjia = 0;
    double cashing_packet = 0;
    // double di_hongbao = 0;
    double packet, heji_zongjia;
    String total_fee, hongbao, kedi_hongbao;
    ArrayList<JuTuanGouData> list_zf = new ArrayList<JuTuanGouData>();
    SlipButton sb;
    double jiaguo = 0;
    public static String province, city, area, user_address, user_accept_name,
            user_mobile, shopping_address_id;
    public static String province1, city1, area1, user_address1, accept_name1,
            user_mobile1, recharge_no1, article_id1;
    public static String recharge_no, order_no, datetime1, sell_price1,
            give_pension1;
    private LinearLayout ll_money_ju, market_information_juduihuan,
            market_information_bottom;
    public AQuery mAq;

    @Override
    protected void onResume() {

        super.onResume();
        try {

            spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
            user_name = spPreferences.getString("user", "");
            dzongjia = 0;
            cashing_packet = 0;
            // System.out.println("dzongjiade值为零================"+dzongjia);
            // System.out.println("cashing_packet值为零================"+cashing_packet);

            // 获取地址
            user_accept_name = getIntent().getStringExtra("user_accept_name");
            System.out.println("name==============" + user_accept_name);
            if (user_accept_name != null) {
                province = getIntent().getStringExtra("province");
                city = getIntent().getStringExtra("city");
                area = getIntent().getStringExtra("user_area");
                user_address = getIntent().getStringExtra("user_address");
                user_mobile = getIntent().getStringExtra("user_mobile");

                tv_user_name.setText("收货人：" + user_accept_name);
                tv_user_address.setText("地址：" + province + "、" + city + "、" + area
                        + "、" + user_address);
                tv_user_phone.setText(user_mobile);
            } else {
                getuseraddress2();
            }

            System.out.println("teby==============" + teby);
            // 余额支付成功后更新订单
            if (teby == true) {
                // userloginqm();
                teby = false;
                finish();
            }

            // 微信支付成功后关闭此界面
            if (flag == true) {
                userloginqm();
                // finish();
            }

            // 余额支付取消关闭此界面
            if (TishiCarArchivesActivity.yue_zhuangtai != null) {
                TishiCarArchivesActivity.yue_zhuangtai = null;
                finish();
            }


            // 立即购买
            img_ware = (ImageView) findViewById(R.id.img_ware);
            tv_warename = (TextView) findViewById(R.id.tv_ware_name);
            tv_color = (TextView) findViewById(R.id.tv_color);
            tv_size = (TextView) findViewById(R.id.tv_size);
            ll_ljgm = (LinearLayout) findViewById(R.id.ll_ljgm);

            // JuTuanGouData bean = (JuTuanGouData)
            // getIntent().getSerializableExtra("bean");
            // String title = bean.getTitle();

            // String title = getIntent().getStringExtra("title");
            // String jdh_title = getIntent().getStringExtra("jdh_title");
            // if (title != null) {
            // String stare = getIntent().getStringExtra("stare");
            // if (stare.equals("1")) {
            // dzongjia =
            // Double.parseDouble(getIntent().getStringExtra("price"));
            // tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |
            // Paint.ANTI_ALIAS_FLAG);
            // String groupon_price =
            // getIntent().getStringExtra("groupon_price");
            // String price = getIntent().getStringExtra("price");
            // tv_color.setText( "￥" + price);
            // tv_size.setText( "￥" + groupon_price);
            // tv_1.setText("价格:");
            // tv_2.setText("团购价:");
            // }else {
            // dzongjia =
            // Double.parseDouble(getIntent().getStringExtra("groupon_price"));
            // tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |
            // Paint.ANTI_ALIAS_FLAG);
            // String groupon_price =
            // getIntent().getStringExtra("groupon_price");
            // tv_color.setText( "￥" + groupon_price);//团购价
            // String price = getIntent().getStringExtra("price");
            // tv_size.setText( "￥" + price);
            // }
            // rl_hongbao.setVisibility(View.GONE);
            // ll_ljgm.setVisibility(View.VISIBLE);
            // System.out.println("1================");
            // String img_url = getIntent().getStringExtra("img_url");
            // mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP + img_url);
            // // dzongjia =
            // Double.parseDouble(getIntent().getStringExtra("groupon_price"));
            // // dzongjia =
            // Double.parseDouble(getIntent().getStringExtra("price"));
            // // String groupon_price =
            // getIntent().getStringExtra("groupon_price");
            // // tv_color.setText( "￥" + groupon_price);//团购价
            // // String price = getIntent().getStringExtra("price");
            // // tv_size.setText( "￥" + price);
            // // tv_color.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG |
            // Paint.ANTI_ALIAS_FLAG);
            // tv_warename.setText(title);
            // // heji.setText("实付款:" + "￥" + retailPrice);
            // loadWeather();
            // } else
            // //聚兑换
            // if (jdh_title != null) {
            // ll_ljgm.setVisibility(View.VISIBLE);
            // rl_hongbao.setVisibility(View.GONE);
            // System.out.println("2================");
            // String img_url = getIntent().getStringExtra("img_url");
            // jubi = getIntent().getStringExtra("point");
            // dzongjia =
            // Double.parseDouble(getIntent().getStringExtra("point"));
            // String goods_price = getIntent().getStringExtra("goods_price");
            // ImageLoader imageLoaderll = ImageLoader.getInstance();
            // imageLoaderll.displayImage(RealmName.REALM_NAME_HTTP + img_url,
            // img_ware);
            // tv_color.setText(jubi);//福利
            // tv_size.setText(goods_price);
            // tv_size.getPaint().setFlags(
            // Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            // tv_warename.setText(jdh_title);
            // tv_1.setText("福利:");
            // tv_2.setText( "￥" + "市场价:");
            //
            // // market_information_juduihuan.setVisibility(View.VISIBLE);
            // // heji.setText("实付款:" + retailPrice);
            // loadWeather();
            // }else{
            getuserhongbao(user_name);
            // }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // @Override
    // protected void onDestroy() {
    //
    // super.onDestroy();
    // }

    public static Handler handlerll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            popupWindowMenu = new MyPopupWindowMenu(this);
            progress = new DialogProgress(MyOrderConfrimActivity.this);
            api = WXAPIFactory.createWXAPI(MyOrderConfrimActivity.this, null);
            api.registerApp(Constants.APP_ID);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.order_confrim);
            progress.CreateProgress();
            spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
            user_name = spPreferences.getString("user", "");
            user_id = spPreferences.getString("user_id", "");
            mAq = new AQuery(this);
            sb = (SlipButton) findViewById(R.id.splitbutton);
            // sb.setCheck(false);
            tv_hb_ye = (TextView) findViewById(R.id.tv_hb_ye);
            tv_hb_ye_2 = (TextView) findViewById(R.id.tv_hb_ye_2);
            tv_hb_ye_3 = (TextView) findViewById(R.id.tv_hb_ye_3);
            sb.setCheck(true);

            handlerll = new Handler() {
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 2:
                            finish();
                            break;
                    }
                }
            };
            initdata();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 获取用户红包
     *
     * @param user_name
     */
    private void getuserhongbao(String user_name) {
        String strUrlone = URLConstants.REALM_NAME_LL
                + "/get_user_model?username=" + user_name + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    System.out.println("获取红包值================" + arg1);
                    String status = object.getString("status");
                    JSONObject obj = object.getJSONObject("data");
                    if (status.equals("y")) {
                        UserRegisterllData data = new UserRegisterllData();
                        data.hongbao = obj.getDouble("packet");
                        packet = data.hongbao;
                        hongbao = String.valueOf(packet);
                    } else {

                    }
                    load_list(true);
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            ;
        }, MyOrderConfrimActivity.this);
    }

    private void gethongbao() {


        /**
         * 判断是否使用红包
         */
        sb.SetOnChangedListener(new SlipButton.OnChangedListener() {
            @Override
            public void OnChanged(boolean isCheck) {
                System.out.println("isCheck================" + isCheck);
                System.out.println("dzongjia================" + dzongjia);
                System.out
                        .println("retailPrice1================" + retailPrice);
                System.out.println("packet================" + packet);
                System.out.println("cashing_packet================" + cashing_packet);
                System.out.println("hongbao================" + hongbao);
                System.out.println("kedi_hongbao================" + kedi_hongbao);
                if (isCheck == true) {
                    System.out.println("1================" + isCheck);
                    try {
                        if (hongbao.equals("0.0")) {
                            heji.setVisibility(View.VISIBLE);
                            tv_hongbao.setText("无可抵红包");
                            kou_hongbao = 0;// 红包为0
                            rl_hongbao.setVisibility(View.GONE);
                            tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + 0 + "元");
                        } else if (kedi_hongbao.equals("0.0")) {
                            heji.setVisibility(View.VISIBLE);
                            tv_hongbao.setText("无可抵红包");
                            kou_hongbao = 0;// 无可抵红包
                            rl_hongbao.setVisibility(View.GONE);
                            tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + 0 + "元");
                        } else if (dzongjia == cashing_packet) {
                            heji.setVisibility(View.GONE);
                            // dzongjia = 0.0;
                            tv_hongbao.setText("可用" + cashing_packet + "元红包抵"
                                    + cashing_packet + "元");
                            kou_hongbao = 1;// 已低红包
                            rl_hongbao.setVisibility(View.VISIBLE);
                            tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + cashing_packet + "元");
                        } else if (packet >= cashing_packet) {
                            BigDecimal w = new BigDecimal(dzongjia - cashing_packet);
                            dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            // retailPrice =
                            // Double.toString(w.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                            System.out.println("dzongjia11================"
                                    + dzongjia);
                            System.out.println("retailPrice11================"
                                    + retailPrice);
                            heji.setVisibility(View.VISIBLE);
                            heji.setText("实付款:" + dzongjia);
                            tv_hongbao.setText("可用" + cashing_packet + "元红包抵" + cashing_packet + "元");
                            kou_hongbao = 1;// 已低红包
                            rl_hongbao.setVisibility(View.VISIBLE);
                            tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + cashing_packet + "元");
                        } else if (packet <= cashing_packet) {
                            BigDecimal w = new BigDecimal(dzongjia - packet);
                            dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP)
                                    .doubleValue();
                            System.out.println("dzongjia11================"
                                    + dzongjia);
                            System.out.println("retailPrice11================"
                                    + retailPrice);
                            heji.setVisibility(View.VISIBLE);
                            heji.setText("实付款:" + dzongjia);
                            tv_hongbao.setText("可用" + packet + "元红包抵" + packet + "元");
                            // tv_hongbao.setText("可用"+hongbao+"元红包");
                            kou_hongbao = 1;// 已低红包
                            rl_hongbao.setVisibility(View.VISIBLE);
                            tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + packet + "元");
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    //				if (hongbao.equals("0.0")) {
                    //					kou_hongbao = 0;// 红包为0
                    //				} else if (kedi_hongbao.equals("0.0")) {
                    //					kou_hongbao = 0;// 无可抵红包
                    //				}else {
                    //					kou_hongbao = 1;// 已低红包
                    //				}
                    hongbao_tety = isCheck;
                } else if (isCheck == false) {
                    System.out.println("1================" + isCheck);
                    // sb.setCheck(true);
                    System.out.println("dzongjia2================" + dzongjia);
                    System.out.println("retailPrice2================"
                            + retailPrice);
                    if (hongbao.equals("0.0")) {
                        heji.setVisibility(View.VISIBLE);
                        tv_hongbao.setText("不可以使用红包");
                        rl_hongbao.setVisibility(View.GONE);
                        tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + 0 + "元");
                    } else if (kedi_hongbao.equals("0.0")) {
                        heji.setVisibility(View.VISIBLE);
                        tv_hongbao.setText("不可以使用红包");
                        rl_hongbao.setVisibility(View.GONE);
                        tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + 0 + "元");
                    } else if (dzongjia == cashing_packet) {
                        heji.setVisibility(View.VISIBLE);
                        // dzongjia = 0.0;
                        tv_hongbao.setText("可用" + cashing_packet + "元红包抵"
                                + cashing_packet + "元");
                        rl_hongbao.setVisibility(View.VISIBLE);
                        tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + 0 + "元");
                    } else if (packet >= cashing_packet) {
                        BigDecimal w = new BigDecimal(dzongjia + cashing_packet);
                        dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        System.out.println("dzongjia22================" + dzongjia);
                        heji.setVisibility(View.VISIBLE);
                        heji.setText("实付款:" + dzongjia);
                        tv_hongbao.setText("可用" + cashing_packet + "元红包抵"
                                + cashing_packet + "元");
                        rl_hongbao.setVisibility(View.VISIBLE);
                        tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + 0 + "元");
                    } else if (packet <= cashing_packet) {
                        BigDecimal w = new BigDecimal(dzongjia + packet);
                        dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP)
                                .doubleValue();
                        System.out.println("dzongjia11================"
                                + dzongjia);
                        heji.setVisibility(View.VISIBLE);
                        heji.setText("实付款:" + dzongjia);
                        tv_hongbao.setText("可用" + packet + "元红包抵" + packet
                                + "元");
                        rl_hongbao.setVisibility(View.VISIBLE);
                        tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + 0 + "元");
                    }

                    hongbao_tety = isCheck;
                    kou_hongbao = 0;// 不抵扣红包
                }

                tv_hb_ye.setText(String.valueOf(packet));
                tv_hb_ye_2.setText(String.valueOf(packet));
                //				tv_hb_ye_3.setText(String.valueOf(hongbao));
                tv_hb_ye_3.setText("当前红包:￥" + String.valueOf(hongbao) + "元");
            }
        });

        System.out.println("设置状态================" + hongbao_tety);
        try {

            /**
             * 选择红包状态下
             */
            if (hongbao_tety == true) {
                System.out.println("dzongjia3================" + dzongjia);
                System.out
                        .println("retailPrice================" + retailPrice);
                System.out.println("hongbao================" + hongbao);
                System.out.println("cashing_packet================"
                        + cashing_packet);
                if (hongbao.equals("0.0")) {
                    tv_hongbao.setText("无可抵红包");
                    heji.setVisibility(View.VISIBLE);
                    kou_hongbao = 0;// 红包为0
                    rl_hongbao.setVisibility(View.GONE);
                    tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + 0 + "元");
                } else if (kedi_hongbao.equals("0.0")) {
                    tv_hongbao.setText("无可抵红包");
                    heji.setVisibility(View.VISIBLE);
                    kou_hongbao = 0;// 无可抵红包
                    rl_hongbao.setVisibility(View.GONE);
                    tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + 0 + "元");
                } else if (dzongjia == cashing_packet) {
                    heji.setVisibility(View.GONE);
                    // dzongjia = 0.0;
                    tv_hongbao.setText("可用" + cashing_packet + "元红包抵"
                            + cashing_packet + "元");
                    kou_hongbao = 1;// 已低红包
                    rl_hongbao.setVisibility(View.VISIBLE);
                    tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + cashing_packet + "元");
                } else if (packet >= cashing_packet) {
                    BigDecimal w = new BigDecimal(dzongjia - cashing_packet);
                    dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP)
                            .doubleValue();
                    // retailPrice =
                    // Double.toString(w.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    System.out.println("dzongjia11================" + dzongjia);
                    heji.setVisibility(View.VISIBLE);
                    heji.setText("实付款:" + dzongjia);
                    tv_hongbao.setText("可用" + cashing_packet + "元红包抵"
                            + cashing_packet + "元");
                    kou_hongbao = 1;// 已低红包
                    rl_hongbao.setVisibility(View.VISIBLE);
                    tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + cashing_packet + "元");
                } else if (packet <= cashing_packet) {
                    BigDecimal w = new BigDecimal(dzongjia - packet);
                    dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP)
                            .doubleValue();
                    System.out.println("dzongjia11================" + dzongjia);
                    heji.setVisibility(View.VISIBLE);
                    heji.setText("实付款:" + dzongjia);
                    tv_hongbao.setText("可用" + packet + "元红包抵" + packet + "元");
                    // tv_hongbao.setText("可用"+hongbao+"元红包");
                    kou_hongbao = 1;// 已低红包
                    rl_hongbao.setVisibility(View.VISIBLE);
                    tv_jiaguo.setText("￥" + dzongjia + " , " + heji_quantity + "件，红包可抵扣: ￥" + packet + "元");
                }


                tv_hb_ye.setText(String.valueOf(packet));
                tv_hb_ye_2.setText(String.valueOf(packet));
                tv_hb_ye_3.setText("当前红包:￥" + String.valueOf(hongbao) + "元");

                //				tv_hb_ye.setText("使用"+packet+"红包抵扣"+packet+"件，红包可抵扣: ￥"+hongbao+"元");

                //				if (hongbao.equals("0.0")) {
                //					kou_hongbao = 0;// 红包为0
                //				} else if (kedi_hongbao.equals("0.0")) {
                //					kou_hongbao = 0;// 无可抵红包
                //				}else {
                //					kou_hongbao = 1;// 已低红包
                //				}
                //				kou_hongbao = 1;// 低红包
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 在这里进行查询地址的操作
        // Toast.makeText(getApplicationContext(), "查询地址联网操作",200).show();
        // handler.sendEmptyMessage(4);
        if (resultCode == 100) {
            layout0.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.GONE);

            UserAddressData dt = (UserAddressData) data
                    .getSerializableExtra("data");
            checkedAddressId = dt.consigneeAddressId;
            String name = dt.user_accept_name;
            String user_area = dt.user_area;
            String user_mobile = dt.user_mobile;
            String user_address = dt.user_address;
            System.out.println("checkedAddressId==================" + name);

            tv_user_name.setText("收货人:" + name);
            tv_user_address.setText(user_area + " " + user_address);
            tv_user_phone.setText(user_mobile);

        }

    }

    private void initdata() {
        try {
            market_information_juduihuan = (LinearLayout) findViewById(R.id.market_information_juduihuan);
            confrim_btn = (Button) findViewById(R.id.confrim_btn);
            list_shop_cart = (InScrollListView) findViewById(R.id.list_shop_cart);
            list_shop_cart.setFocusable(false);
            btn_add_address = (ImageButton) findViewById(R.id.img_btn_add_address);
            layout0 = (LinearLayout) findViewById(R.id.layout0);
            layout1 = (RelativeLayout) findViewById(R.id.layout1);
            rl_hongbao = (RelativeLayout) findViewById(R.id.rl_hongbao);
            layout2 = (LinearLayout) findViewById(R.id.layout2);
            ll_zhifufs = (LinearLayout) findViewById(R.id.ll_zhifufs);
            tv_user_name = (TextView) findViewById(R.id.tv_user_name);
            tv_user_address = (TextView) findViewById(R.id.tv_user_address);
            tv_user_phone = (TextView) findViewById(R.id.tv_user_phone);
            tv_hongbao = (TextView) findViewById(R.id.tv_hongbao);
            tv_zhifu = (TextView) findViewById(R.id.tv_zhifu);
            tv_jiaguo = (TextView) findViewById(R.id.tv_jiaguo);
            //			tv_hb_ye = (TextView) findViewById(R.id.tv_hb_ye);
            //			tv_hb_ye_2 = (TextView) findViewById(R.id.tv_hb_ye_2);
            //			tv_hb_ye_3 = (TextView) findViewById(R.id.tv_hb_ye_3);
            heji = (TextView) findViewById(R.id.heji);
            tv_1 = (TextView) findViewById(R.id.tv_1);
            tv_2 = (TextView) findViewById(R.id.tv_2);

            yu_pay0 = (LinearLayout) findViewById(R.id.yu_pay0);
            yu_pay1 = (LinearLayout) findViewById(R.id.yu_pay1);
            yu_pay2 = (LinearLayout) findViewById(R.id.yu_pay2);
            yu_pay_c0 = (CheckBox) findViewById(R.id.yu_pay_c0);
            yu_pay_c1 = (CheckBox) findViewById(R.id.yu_pay_c1);
            yu_pay_c2 = (CheckBox) findViewById(R.id.yu_pay_c2);

            ImageView imageView = (ImageView) findViewById(R.id.iv_tupian);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xiantiap);

            BitmapDrawable bd = new BitmapDrawable(bitmap);
            bd.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            imageView.setBackgroundDrawable(bd);

            // retailPrice = getIntent().getStringExtra("total_cll");
            // System.out.println("实付款----------------"+retailPrice);
            // if (retailPrice != null) {
            // heji.setText("实付款:" + retailPrice);
            // }

            ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
            iv_fanhui.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    finish();

                }
            });

            /**
             * 支付方式
             */
            ll_zhifufs.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    System.out.println("支付方式retailPrice=====================" + retailPrice);
                    Intent intent = new Intent(MyOrderConfrimActivity.this,
                            ZhiFuFangShiActivity.class);
                    intent.putExtra("order_confrim", "order_confrim");//
                    startActivity(intent);
                }
            });

            /**
             * 收货地址列表
             */
            layout0.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String title = getIntent().getStringExtra("title");
                    String stare = getIntent().getStringExtra("stare");
                    String groupon_price = getIntent().getStringExtra(
                            "groupon_price");
                    String img_url = getIntent().getStringExtra("img_url");
                    String price = getIntent().getStringExtra("price");
                    String goods_price = getIntent().getStringExtra(
                            "goods_price");
                    String jubi = getIntent().getStringExtra("point");
                    String shopping_ids = getIntent().getStringExtra(
                            "shopping_ids");
                    String tuangou_id = getIntent()
                            .getStringExtra("tuangou_id");

                    Intent intent = new Intent(MyOrderConfrimActivity.this,
                            AddressManagerActivity.class);
                    intent.putExtra("order_confrim", "order_confrim");// 标示
                    intent.putExtra("title", title);// 标示
                    intent.putExtra("stare", stare);
                    intent.putExtra("groupon_price", groupon_price);
                    intent.putExtra("img_url", img_url);
                    intent.putExtra("price", price);
                    intent.putExtra("goods_price", goods_price);
                    intent.putExtra("jubi", jubi);
                    intent.putExtra("shopping_ids", shopping_ids);
                    intent.putExtra("tuangou_id", tuangou_id);
                    intent.putExtra("buy_no",
                            getIntent().getStringExtra("buy_no"));
                    startActivity(intent);
                    // startActivityForResult(intent, 100);
                    // startActivityForResult(intent, 0);
                }
            });

            /**
             * 添加收货地址
             */
            layout1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // int index = 0;
                    Intent intent = new Intent(MyOrderConfrimActivity.this,
                            AddUserAddressActivity.class);
                    // intent.putExtra("index", index);
                    // startActivityForResult(intent, 0);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }


        /**
         * 微信支付
         */
        yu_pay_c0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                yu_pay_c1.setChecked(false);
                yu_pay_c2.setChecked(false);
                yu_pay_c0.setChecked(true);
                System.out.println("type======微信支付========" + type);
                // 微信
                type = "5";
            }
        });
        yu_pay0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // 点击设置是否为点击状态
                yu_pay_c1.setChecked(false);
                yu_pay_c2.setChecked(false);
                yu_pay_c0.setChecked(true);
                System.out.println("type======微信支付========" + type);
                // 微信
                type = "5";
            }
        });
        /**
         * 支付宝支付
         */
        yu_pay_c1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                yu_pay_c0.setChecked(false);
                yu_pay_c1.setChecked(true);
                yu_pay_c2.setChecked(false);
                System.out.println("type======支付宝支付========" + type);
                // 支付宝
                type = "3";
            }
        });
        yu_pay1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                yu_pay_c0.setChecked(false);
                yu_pay_c1.setChecked(true);
                yu_pay_c2.setChecked(false);
                System.out.println("type======支付宝支付========" + type);
                // 支付宝
                type = "3";
            }
        });


        /**
         * 余额支付
         */
        yu_pay_c2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                yu_pay_c0.setChecked(false);
                yu_pay_c1.setChecked(false);
                yu_pay_c2.setChecked(true);
                // 余额支付
                type = "2";
            }
        });
        yu_pay2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                yu_pay_c0.setChecked(false);
                yu_pay_c1.setChecked(false);
                yu_pay_c2.setChecked(true);
                // 余额支付
                type = "2";
            }
        });


        /**
         * 结算方式
         */
        confrim_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                System.out.println("user_accept_name======11========"
                        + user_accept_name);
                if (user_accept_name == null) {
                    Toast.makeText(MyOrderConfrimActivity.this, "您还未添加收货地址",
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {

                        System.out.println("type======结算方式========" + type);
                        loadusertijiao(type, kou_hongbao);// 提交聚团订单

                        //						CommomConfrim.showSheet(MyOrderConfrimActivity.this,new onDeleteSelect() {
                        //
                        //									@Override
                        //									public void onClick(int resID) {
                        //
                        //										switch (resID) {
                        //										case R.id.item0:
                        //											// 余额支付
                        //											type = "2";
                        //											loadusertijiao(type, kou_hongbao);
                        //											break;
                        //										case R.id.item1:
                        //											break;
                        //										case R.id.item2:// 支付宝
                        //											// 支付宝
                        //											type = "3";
                        //											loadusertijiao(type, kou_hongbao);
                        //											break;
                        //										case R.id.item3:// 微信
                        //											// 微信
                        //											type = "5";
                        //											loadusertijiao(type, kou_hongbao);
                        //											break;
                        //										case R.id.item4:
                        //											break;
                        //										default:
                        //											break;
                        //										}
                        //									}
                        //
                        //								}, cancelListener, null);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }

            }
        });

    }

    /**
     * 获取登录签名
     */
    private void userloginqm() {
        try {
            String strUrlone = URLConstants.REALM_NAME_LL
                    + "/get_user_model?username=" + user_name + "";
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
                            loadguanggaoll(recharge_no, login_sign);
                        } else {
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                ;
            }, MyOrderConfrimActivity.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 输出用户默认收货地址
     */
    boolean dizhi_type = false;

    private void getuseraddress2() {
        try {

            list_ll = new ArrayList<ShopCartData>();
            user_name = spPreferences.getString("user", "");
            user_id = spPreferences.getString("user_id", "");
            AsyncHttp.get(URLConstants.REALM_NAME_LL
                    + "/get_user_shopping_address?user_name=" + user_name// get_user_shopping_address_default
                    + "", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int arg0, String arg1) {


                    super.onSuccess(arg0, arg1);
                    try {
                        JSONObject jsonObject = new JSONObject(arg1);
                        System.out.println("输出用户默认收货地址================" + arg1);
                        String status = jsonObject.getString("status");
                        if (status.equals("y")) {
                            try {
                                // JSONObject jsot =
                                // jsonObject.getJSONObject("data");
                                JSONArray jsonArray = jsonObject
                                        .getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsot = jsonArray.getJSONObject(i);
                                    // UserAddressData data = new UserAddressData();
                                    String user_dizhiname = jsot
                                            .getString("user_accept_name");
                                    shopping_address_id = jsot.getString("id");
                                    province = jsot.getString("province");
                                    city = jsot.getString("city");
                                    area = jsot.getString("area");
                                    user_mobile = jsot.getString("user_mobile");
                                    user_address = jsot.getString("user_address");
                                    user_accept_name = user_dizhiname;

                                    int is_default = jsot.getInt("is_default");
                                    if (is_default == 1) {
                                        tv_user_name.setText("收货人："
                                                + user_accept_name);
                                        tv_user_address.setText("地址：" + province
                                                + "、" + city + "、" + area + "、"
                                                + user_address);
                                        tv_user_phone.setText(user_mobile);
                                        dizhi_type = true;
                                    }
                                }
                                if (dizhi_type == false) {
                                    tv_user_name.setText("收货人：" + user_accept_name);
                                    tv_user_address.setText("地址：" + province + "、"
                                            + city + "、" + area + "、"
                                            + user_address);
                                    tv_user_phone.setText(user_mobile);
                                }

                                SharedPreferences spPreferences = getSharedPreferences(
                                        "user_dizhixinxi", MODE_PRIVATE);
                                Editor editor = spPreferences.edit();
                                editor.putString("province", province);
                                editor.putString("city", city);
                                editor.putString("area", area);
                                editor.putString("user_address", user_address);
                                editor.putString("user_mobile", user_mobile);
                                editor.commit();

                                layout1.setVisibility(View.GONE);
                                progress.CloseProgress();
                                layout0.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {

                                progress.CloseProgress();
                                e.printStackTrace();
                            }
                        } else {
                            progress.CloseProgress();
                            layout1.setVisibility(View.VISIBLE);
                            layout0.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {

                        progress.CloseProgress();
                        e.printStackTrace();
                    }
                }

            }, getApplicationContext());

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * 获取购单物信息 第1个列表数据解析
     */
    private int CURRENT_NUM = 1;
    private final int VIEW_NUM = 10;
    private int RUN_METHOD = -1;
    int heji_quantity = 0;

    private void load_list(boolean flag) {
        RUN_METHOD = 1;
        list = new ArrayList<ShopCartData>();
        String buy_no = getIntent().getStringExtra("buy_no");
        System.out.println("buy_no=====================" + buy_no);
        url = URLConstants.REALM_NAME_LL + "/get_shopping_buy?buy_no=" + buy_no
                + "";
        AsyncHttp.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String arg1) {

                super.onSuccess(arg0, arg1);
                System.out.println("=====================二级值1" + arg1);
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    String info = object.getString("info");
                    if (status.equals("y")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        len = jsonArray.length();
                        double a = 0;
                        double di_hongbao = 0;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            data = new ShopCartData();
                            data.title = json.getString("title");
                            data.market_price = json.getString("market_price");
                            data.sell_price = json.getString("sell_price");
                            data.cashing_packet = json
                                    .getDouble("cashing_packet");
                            data.exchange_price = json
                                    .getString("exchange_price");
                            data.exchange_point = json
                                    .getString("exchange_point");
                            // cashing_packet = data.cashing_packet;
                            data.id = json.getString("id");
                            // data.quantity = json.getInt("quantity");
                            data.setQuantity(json.getInt("quantity"));
                            data.img_url = json.getString("img_url");
                            heji_quantity += data.quantity;
                            // 商品价格
                            BigDecimal c = new BigDecimal(Double.parseDouble(data.sell_price) * data.quantity);
                            // 保留2位小数
                            double total_c_ll = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            a += total_c_ll;
                            System.out.println("a---------------" + a);
                            BigDecimal w = new BigDecimal(a);
                            dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            System.out.println("cashing_packet---------------" + dzongjia);

                            retailPrice = Double.toString(dzongjia);

                            // 红包金额
                            BigDecimal e = new BigDecimal(data.cashing_packet
                                    * data.quantity);
                            // 保留2位小数
                            double total_c = e.setScale(2,
                                    BigDecimal.ROUND_HALF_UP).doubleValue();
                            di_hongbao += total_c;

                            BigDecimal b = new BigDecimal(di_hongbao);
                            cashing_packet = b.setScale(2,
                                    BigDecimal.ROUND_HALF_UP).doubleValue();
                            System.out.println("cashing_packet---------------"
                                    + cashing_packet);

                            list.add(data);
                        }
                        try {

                            // 判断红包是否

                            hongbao = String.valueOf(packet);
                            kedi_hongbao = String.valueOf(cashing_packet);
                            System.out.println("可用红包---------------" + packet);
                            System.out.println("可抵红包---------------"
                                    + cashing_packet);
                            if (hongbao.equals("0.0")) {
                                tv_hongbao.setText("不可以使用红包");
                            } else if (kedi_hongbao.equals("0.0")) {
                                tv_hongbao.setText("不可以使用红包");
                            } else if (packet > cashing_packet) {
                                tv_hongbao.setText("可用" + cashing_packet
                                        + "元红包抵" + cashing_packet + "元");
                            } else if (packet < cashing_packet) {
                                // tv_hongbao.setText("可用"+packet+"元红包");
                                tv_hongbao.setText("可用" + packet + "元红包抵"
                                        + packet + "元");
                            }
                            System.out.println("dzongjia---------------"
                                    + dzongjia);
                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                        zhuangtai = false;
                        progress.CloseProgress();
                        jiekou_type_ysj = WareInformationActivity.jdh_type;
                        System.out
                                .println("jiekou_type_ysj====================="
                                        + jiekou_type_ysj);
                        if (jiekou_type_ysj.equals("1")) {
                            ll_ljgm.setVisibility(View.VISIBLE);
                            rl_hongbao.setVisibility(View.GONE);
                            jubi = data.exchange_point;
                            dzongjia = Double.parseDouble(data.exchange_price);
                            mAq.id(img_ware).image(
                                    URLConstants.REALM_NAME_HTTP + data.img_url);
                            tv_color.setText(jubi);// 聚币
                            tv_color.setVisibility(View.GONE);
                            tv_size.setText(jubi + "聚币" + "+" + dzongjia + "元");// 价格
                            tv_2.setText("聚兑价:");
                            tv_warename.setText(data.title);
                            tv_1.setText("聚币:");
                            tv_1.setVisibility(View.GONE);
                        } else {
                            handler.sendEmptyMessage(0);
                        }
                        loadWeather();
                    } else {
                        progress.CloseProgress();
                        // Toast.makeText(MyOrderConfrimActivity.this, info,
                        // 200).show();
                        finish();
                    }

                    if (len != 0) {
                        // CURRENT_NUM = CURRENT_NUM + VIEW_NUM;
                        CURRENT_NUM = 1;
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, null);
    }

    /**
     * 配送方式
     */
    private void loadWeather() {
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_express_list?top=5",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        System.out.println("输出所有拼团活动列表=========" + arg1);
                        try {
                            list_zf = new ArrayList<JuTuanGouData>();
                            progress.CloseProgress();
                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            // String info = object.getString("info");
                            if (status.equals("y")) {
                                JSONArray jsonArray = object
                                        .getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    JuTuanGouData data = new JuTuanGouData();
                                    data.setId(obj.getString("id"));
                                    data.setTitle(obj.getString("title"));
                                    data.setExpress_fee(obj.getInt("express_fee"));
                                    list_zf.add(data);
                                }

                                try {
                                    System.out.println("合计1----------------"
                                            + retailPrice);
                                    System.out
                                            .println("合计1dzongjia----------------"
                                                    + dzongjia);
                                    ZhiFuFangShi = ZhiFuFangShiActivity.title;
                                    express_id = ZhiFuFangShiActivity.express_id;
                                    express_fee = ZhiFuFangShiActivity.express_fee;
                                    if (ZhiFuFangShi != null) {
                                        if (express_fee == 0) {
                                            tv_zhifu.setText(ZhiFuFangShi
                                                    + "(免邮)");
                                            if (jubi != null) {
                                                if (dzongjia > 0) {
                                                    heji.setText("实付款:" + " 福利"
                                                            + jubi + " + "
                                                            + "￥" + dzongjia);
                                                } else {
                                                    heji.setText("实付款:" + " 福利"
                                                            + jubi);
                                                }
                                            } else {
                                                heji.setText("实付款:" + " ￥"
                                                        + dzongjia);
                                            }
                                        } else {
                                            String price = String.valueOf(express_fee);
                                            tv_zhifu.setText(ZhiFuFangShi + "("
                                                    + "￥" + price + ")");
                                            BigDecimal c = new BigDecimal(
                                                    dzongjia + express_fee);
                                            // retailPrice =
                                            // Double.toString(c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                                            dzongjia = c.setScale(2,
                                                    BigDecimal.ROUND_HALF_UP)
                                                    .doubleValue();
                                            // heji.setText("实付款:" + dzongjia);
                                            if (jubi != null) {
                                                heji.setText("实付款:" + " 福利"
                                                        + jubi + " + " + "￥"
                                                        + dzongjia);
                                            } else {
                                                heji.setText("实付款:" + " ￥"
                                                        + dzongjia);
                                            }
                                        }
                                        // ZhiFuFangShiActivity.title = null;
                                        // ZhiFuFangShiActivity.express_id =
                                        // null;
                                        // ZhiFuFangShiActivity.express_fee = 0;
                                    } else {
                                        ZhiFuFangShi = list_zf.get(0)
                                                .getTitle();
                                        express_id = list_zf.get(0).getId();
                                        express_fee = list_zf.get(0)
                                                .getExpress_fee();
                                        if (express_fee == 0) {
                                            tv_zhifu.setText(ZhiFuFangShi
                                                    + "(免邮)");
                                            // heji.setText("实付款:" + dzongjia);
                                            if (jubi != null) {
                                                if (dzongjia > 0) {
                                                    heji.setText("实付款:" + " 福利"
                                                            + jubi + " + "
                                                            + "￥" + dzongjia);
                                                } else {
                                                    heji.setText("实付款:" + " 福利"
                                                            + jubi);
                                                }
                                            } else {
                                                heji.setText("实付款:" + " ￥"
                                                        + dzongjia);
                                            }
                                        } else {
                                            String price = String
                                                    .valueOf(express_fee);
                                            tv_zhifu.setText(ZhiFuFangShi + "("
                                                    + "￥" + price + ")");
                                            BigDecimal c = new BigDecimal(
                                                    dzongjia + express_fee);
                                            // retailPrice =
                                            // Double.toString(c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                                            // heji.setText("实付款:" +
                                            // retailPrice);
                                            dzongjia = c.setScale(2,
                                                    BigDecimal.ROUND_HALF_UP)
                                                    .doubleValue();
                                            // heji.setText("实付款:" + dzongjia);
                                            if (jubi != null) {
                                                heji.setText("实付款:" + " 福利"
                                                        + jubi + " + " + "￥"
                                                        + dzongjia);
                                            } else {
                                                heji.setText("实付款:" + " ￥"
                                                        + dzongjia);
                                            }
                                        }
                                    }
                                    System.out.println("合计2----------------"
                                            + retailPrice);
                                    System.out
                                            .println("合计2dzongjia----------------"
                                                    + dzongjia);

                                    gethongbao();

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                                progress.CloseProgress();
                            } else {
                                progress.CloseProgress();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                }, null);
    }

    /**
     * 提交用户订单
     *
     * @param payment_id
     * @param kou_hongbao
     */
    String total_amount, is_cashing_point;

    private void loadusertijiao(String payment_id, int kou_hongbao) {
        try {
            // progress.CreateProgress();
            // jiekou_type = getIntent().getStringExtra("jdh_type");
            // System.out.println("jiekou_type=====================" + jiekou_type);
            jiekou_type_ysj = WareInformationActivity.jdh_type;
            System.out.println("jiekou_type_ysj=====================" + jiekou_type_ysj);
            if (jiekou_type_ysj.equals("1")) {
                // WareInformationActivity.jdh_type = "";
                jiekou_type = "add_order_point";// 提交兑换订单
                is_cashing_point = "1";
            } else {
                jiekou_type = "order_save";// 商品提交订单
                is_cashing_point = "0";
            }
            spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
            login_sign = spPreferences.getString("login_sign", "");
            System.out.println("login_sign=====================" + login_sign);
            String buy_no = getIntent().getStringExtra("buy_no");
            System.out.println("buy_no=====================" + buy_no);
            url = URLConstants.REALM_NAME_LL + "/" + jiekou_type + "?user_id="
                    + user_id + "&user_name=" + user_name + "&user_sign="
                    + login_sign + "&is_cashing_packet=" + kou_hongbao + ""
                    + "&is_cashing_point=" + is_cashing_point + "&buy_no=" + buy_no + "&payment_id="
                    + payment_id + "&express_id=" + express_id
                    + "&is_invoice=0&invoice_title=&address_id="
                    + shopping_address_id + "" + "&accept_name="
                    + user_accept_name + "&province=" + province + "&city="
                    + city + "&area=" + area + "&address=" + user_address
                    + "&telphone=" + "&mobile=" + user_mobile
                    + "&email=&post_code=&message=";

            AsyncHttp.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int arg0, String arg1) {
                    super.onSuccess(arg0, arg1);
                    try {
                        JSONObject object = new JSONObject(arg1);
                        System.out
                                .println("提交用户订单 ================================="
                                        + arg1);
                        try {

                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                JSONObject jsonObject = object
                                        .getJSONObject("data");
                                recharge_no = jsonObject.getString("trade_no");
                                // datetime = jsonObject.getString("datetime");
                                // order_no = jsonObject.getString("order_no");
                                // total_amount =
                                // jsonObject.getString("payable_amount");
                                System.out
                                        .println("jiekou_type_ysj====================="
                                                + jiekou_type_ysj);
                                if (jiekou_type_ysj.equals("1")) {
                                    WareInformationActivity.jdh_type = "";
                                    total_amount = jsonObject
                                            .getString("payable_amount");
                                } else {
                                    total_amount = jsonObject
                                            .getString("total_amount");
                                }
                                // data = new ShopCartData();
                                if (type.equals("3")) {
                                    loadzhidu(recharge_no, total_amount);
                                } else if (type.equals("5")) {
                                    loadweixinzf2(recharge_no, total_amount);
                                } else if (type.equals("2")) {
                                    // loadYue(recharge_no);
                                    // teby = true;
                                    Intent intent = new Intent(
                                            MyOrderConfrimActivity.this,
                                            TishiCarArchivesActivity.class);
                                    intent.putExtra("order_no", recharge_no);
                                    intent.putExtra("yue", "yue");
                                    startActivity(intent);
                                    // finish();
                                }
                                // handler.sendEmptyMessage(0);
                            } else {
                                // progress.CloseProgress();
                                Toast.makeText(MyOrderConfrimActivity.this,
                                        info, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, getApplicationContext());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    OnCancelListener cancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            /*
             * http://www.ju918.com/mi/getdata.ashx?act=UserCartInfo&appkey=
			 * 0762222540
			 * &key=QUPgWi93j719&sign=AAE3474591B6B22950AD09A11082D4D751DDABC9
			 * &yth=112967999
			 */
        }
    };

    Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void dispatchMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    try {

                        System.out.println("3================" + list.size());
                        adapter = new ShopingCartOrderAdapter(list,
                                getApplicationContext(), handler);
                        list_shop_cart.setAdapter(adapter);
                        // list_shop_cart.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    break;
                case 1:
                    ali_pay();
                    break;
                case 2:// 微信支付
                    try {
                        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                        // System.err.println("isPaySupported=============="+isPaySupported);
                        // Toast.makeText(MyOrderConfrimActivity.this, "获取订单中...",
                        // Toast.LENGTH_SHORT).show();
                        String zhou = String.valueOf(isPaySupported);
                        // Toast.makeText(MyOrderConfrimActivity.this, zhou,
                        // Toast.LENGTH_SHORT).show();
                        if (isPaySupported) {
                            try {
                                PayReq req = new PayReq();
                                req.appId = Constants.APP_ID;
                                req.partnerId = Constants.MCH_ID;
                                req.prepayId = prepayid;// 7
                                req.nonceStr = noncestr;// 3
                                req.timeStamp = timestamp;// -1
                                req.packageValue = package_;
                                req.sign = sign;// -3

                                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                api.registerApp(Constants.APP_ID);
                                // api.sendReq(req);
                                flag = api.sendReq(req);
                                System.out.println("支付" + flag);
                                // finish();
                                // Toast.makeText(MyOrderConfrimActivity.this,
                                // "支付true", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MyOrderConfrimActivity.this, "支付失败。。。",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    break;
                case 5:// 支付宝
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();
                    System.out.println(resultInfo + "---" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(MyOrderConfrimActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        userloginqm();
                        //finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MyOrderConfrimActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(MyOrderConfrimActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 订单更新支付
     *
     * @param login_sign
     * @param
     */
    private void loadguanggaoll(String recharge_noll, String login_sign) {
        try {
            // recharge_no = recharge_noll;
            AsyncHttp.get(URLConstants.REALM_NAME_LL
                            + "/update_order_payment?user_id=" + user_id
                            + "&user_name=" + user_name + "" + "&trade_no="
                            + recharge_noll + "&sign=" + login_sign + "",

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
                                    list_shop_cart.setVisibility(View.GONE);
                                    teby = false;

                                    //get_order_trade_list //

                                    // JSONObject jsonObject =
                                    // object.getJSONObject("data");
                                    // JSONArray jay =
                                    // jsonObject.getJSONArray("orders");
                                    // for (int j = 0; j < jay.length(); j++){
                                    // JSONObject objc= jay.getJSONObject(j);
                                    // accept_name1 = objc.getString("accept_name");
                                    // province1 = objc.getString("province");
                                    // city1 = objc.getString("city");
                                    // area1 = objc.getString("area");
                                    // user_mobile1 = objc.getString("mobile");
                                    // user_address1 = objc.getString("address");
                                    // recharge_no1 = objc.getString("order_no");
                                    // datetime1 = objc.getString("add_time");
                                    // sell_price1 = objc.getString("payable_amount");
                                    // JSONArray jsonArray =
                                    // objc.getJSONArray("order_goods");
                                    // for (int i = 0; i < jsonArray.length(); i++) {
                                    // JSONObject json = jsonArray.getJSONObject(i);
                                    // article_id1 = json.getString("article_id");
                                    // // sell_price1 = json.getString("sell_price");
                                    // give_pension1 = json.getString("give_pension");
                                    // }
                                    // }

                                    //							 Intent intent = new Intent(MyOrderConfrimActivity.this,MyOrderXqActivity.class);
                                    //							 startActivity(intent);
                                    //							Toast.makeText(MyOrderConfrimActivity.this, info,Toast.LENGTH_SHORT).show();
                                    finish();
                                    // Intent intent = new Intent(MyOrderConfrimActivity.this,ZhiFuOKActivity.class);
                                    // startActivity(intent);
                                } else {
                                    progress.CloseProgress();
                                    teby = false;
                                    Toast.makeText(MyOrderConfrimActivity.this, info, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            super.onFailure(arg0, arg1);
                            System.out.println("11================================="
                                    + arg0);
                            System.out.println("22================================="
                                    + arg1);
                            Toast.makeText(MyOrderConfrimActivity.this, "异常", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 支付宝
     *
     * @param total_amount
     * @param
     */
    private void loadzhidu(String recharge_no3, String total_amount) {
        try {
            recharge_no = recharge_no3;
            // total_fee = String.valueOf(Double.parseDouble(retailPrice) +
            // Double.parseDouble(String.valueOf(express_fee)));
            total_fee = total_amount;
            System.out.println("22retailPrice================================="
                    + total_fee);
            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/payment_sign?user_id="
                    + user_id + "&user_name=" + user_name + "" + "&total_fee="
                    + total_amount + "&out_trade_no=" + recharge_no
                    + "&payment_type=alipay", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int arg0, String arg1) {
                    super.onSuccess(arg0, arg1);
                    try {
                        JSONObject object = new JSONObject(arg1);
                        System.out.println("2================================="
                                + arg1);
                        String status = object.getString("status");
                        String info = object.getString("info");
                        if (status.equals("y")) {
                            JSONObject obj = object.getJSONObject("data");
                            notify_url = obj.getString("notify_url");
                            progress.CloseProgress();
                            handler.sendEmptyMessage(1);
                        } else {
                            progress.CloseProgress();
                            Toast.makeText(MyOrderConfrimActivity.this, info,
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 微信支付
     *
     * @param total_amount
     * @param
     */
    private void loadweixinzf2(String recharge_no2, String total_amount) {
        try {
            recharge_no = recharge_no2;
            System.out.println("总金额===================" + retailPrice);
            // String monney =
            // String.valueOf(Integer.parseInt(retailPrice)*100);
            String monney = String
                    .valueOf(Double.parseDouble(total_amount) * 100);

            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/payment_sign?user_id="
                            + user_id + "&user_name=" + user_name + "" + "&total_fee="
                            + monney + "&out_trade_no=" + recharge_no
                            + "&payment_type=weixin",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {

                                JSONObject object = new JSONObject(arg1);
                                System.out
                                        .println("weixin================================="
                                                + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    JSONObject jsonObject = object
                                            .getJSONObject("data");
                                    partner_id = jsonObject.getString("mch_id");
                                    prepayid = jsonObject.getString("prepay_id");
                                    noncestr = jsonObject.getString("nonce_str");
                                    timestamp = jsonObject.getString("timestamp");
                                    package_ = "Sign=WXPay";
                                    sign = jsonObject.getString("sign");
                                    System.out
                                            .println("weixin================================="
                                                    + package_);
                                    progress.CloseProgress();
                                    handler.sendEmptyMessage(2);
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MyOrderConfrimActivity.this, info,
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 余额支付
     *
     * @param
     */
    private void loadYue(String recharge_no) {
        try {
            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/payment_balance?user_id="
                            + user_id + "&user_name=" + user_name + "" + "&trade_no="
                            + recharge_no + "&paypassword=" + pwd + "",

                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("2================================="
                                        + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    userloginqm();
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MyOrderConfrimActivity.this, info,
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void ali_pay() {
        try {

            //
            String orderInfo = getOrderInfo("乐享汽车商品", "商品描述", recharge_no);

            // 对订单做RSA 签名
            String sign = sign(orderInfo);
            try {
                // 仅需对sign 做URL编码
                sign = URLEncoder.encode(sign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // 完整的符合支付宝参数规范的订单信息
            final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                    + getSignType();

            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(MyOrderConfrimActivity.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(payInfo, true);
                    Message msg = new Message();
                    msg.what = 5;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, Common.RSA_PRIVATE, false);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String dingdan) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Common.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Common.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + dingdan + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + total_fee + "\"";
        // orderInfo += "&total_fee=" + "\"" + 0.01 + "\"";

        // 服务器异步通知页面路径
        System.out.println("======notify_url=============" + notify_url);
        // String str = notify_url;
        // String notify_url_ll = str.replaceAll("\\s*", "");
        // orderInfo += "&notify_url=" + "\""+notify_url_ll+"\"";
        orderInfo += "&notify_url=" + "\"" + notify_url + "\"";
        System.out.println("======orderInfo=============" + orderInfo);

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        // orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
        System.out.println(orderInfo);
        return orderInfo;
    }
}
