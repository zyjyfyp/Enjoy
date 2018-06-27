package com.yunsen.enjoy.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;

import com.hengyushop.dao.CardItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.ShopingCartOrderAdapter;
import com.yunsen.enjoy.activity.pay.TishiCarArchivesActivity;
import com.yunsen.enjoy.activity.pay.ZhiFuFangShiActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.JuTuanGouData;
import com.yunsen.enjoy.model.ShopCartData;
import com.yunsen.enjoy.model.ShopCarts;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.model.WxSignData;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.thirdparty.Common;
import com.yunsen.enjoy.thirdparty.PayProxy;
import com.yunsen.enjoy.thirdparty.alipay.OrderInfoUtil2_0;
import com.yunsen.enjoy.thirdparty.alipay.PayResult;
import com.yunsen.enjoy.thirdparty.alipay.SignUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.InScrollListView;
import com.yunsen.enjoy.widget.MyPopupWindowMenu;
import com.yunsen.enjoy.widget.SlipButton;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * 订单确认
 *
 * @author Administrator
 */
public class MyOrderConfrimActivity extends BaseFragmentActivity implements OnClickListener {
    private String pwd;
    private DialogProgress progress;
    private StringBuilder orderid;
    private MyPopupWindowMenu popupWindowMenu;
    private String trade_no;
    private ArrayList<CardItem> banks = null;
    private String bankNames[] = null;
    private static final int REQUESTCODE = 10000;
    private ArrayList<ShopCarts> carts;
    private TextView tv_user_name, tv_user_address, tv_user_phone, tv_hongbao;
    public static String user_name, user_id;
    private ImageButton btn_add_address;
    private ShopingCartOrderAdapter adapter;
    private InScrollListView list_shop_cart;
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
    private String ZhiFuFangShi, express_id;
    private String jubi;
    String type = "5"; // 5 微信支付, 3 支付宝支付  ,2 余额支付
    String notify_url;
    int kou_hongbao;
    private IWXAPI api;
    private String partner_id, prepayid, noncestr, timestamp, package_, sign;
    private int len;
    String url;
    String login_sign, dandu_goumai;
    boolean flag;
    boolean hongbao_tety = true;
    String total_fee;
    ArrayList<JuTuanGouData> list_zf = new ArrayList<JuTuanGouData>();
    SlipButton sb;
    private String province, city, area, user_address, user_accept_name,
            user_mobile, shopping_address_id;
    public static String province1, city1, area1, user_address1, accept_name1,
            user_mobile1, recharge_no1, article_id1;
    public static String recharge_no, order_no, datetime1, sell_price1;
    private LinearLayout market_information_juduihuan;
    private SharedPreferences mSp;
    double mUserRedPacket;//用户持有的 红包数值;
    String mUserhongbao; //用户持有的 红包数值转换的字符串
    private ArrayList<ShopCartData> mReadyPay;
    private String mBuyNo;
    /**
     * 购物状态
     */
    public static final int PAY_DEFAULT = 0;
    public static final int PAY_ORDER = 1;
    public static final int PAY_FINISH = 2;
    private int mPayState = PAY_DEFAULT; // 0 默认 1生成了订单 2 支付完成
    private String mTradeNo; //交易号


    /**
     * 销售总价
     */
    private double dzongjia = 0;
    /**
     * 运费
     */
    private double express_fee = 0;
    private String retailPrice;
    /**
     * 商品的可抵红包
     */
    double cashing_packet = 0;
    String kedi_hongbao;
    int mQuantityCount = 0;
    private boolean mHasPay;
    private WxSignData mSignData;
    private String mTextSpec;


    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.order_confrim;
    }

    @Override
    protected void initView() {
        popupWindowMenu = new MyPopupWindowMenu(this);
        progress = new DialogProgress(MyOrderConfrimActivity.this);
        api = WXAPIFactory.createWXAPI(MyOrderConfrimActivity.this, Constants.APP_ID, false);
//        api.registerApp(Constants.APP_ID);
        progress.CreateProgress();
        mSp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name = mSp.getString(SpConstants.USER_NAME, "");
        user_id = mSp.getString(SpConstants.USER_ID, "");
        sb = (SlipButton) findViewById(R.id.splitbutton);
        tv_hb_ye = (TextView) findViewById(R.id.tv_hb_ye);
        tv_hb_ye_2 = (TextView) findViewById(R.id.tv_hb_ye_2);
        tv_hb_ye_3 = (TextView) findViewById(R.id.tv_hb_ye_3);
        sb.setCheck(true);

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

        heji = (TextView) findViewById(R.id.heji);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);

        yu_pay0 = (LinearLayout) findViewById(R.id.yu_pay0);
        yu_pay1 = (LinearLayout) findViewById(R.id.yu_pay1);
        yu_pay2 = (LinearLayout) findViewById(R.id.yu_pay2);
        yu_pay_c0 = (CheckBox) findViewById(R.id.yu_pay_c0);
        yu_pay_c1 = (CheckBox) findViewById(R.id.yu_pay_c1);
        yu_pay_c2 = (CheckBox) findViewById(R.id.yu_pay_c2);


        handlerll = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        finish();
                        break;
                }
            }
        };
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mBuyNo = intent.getStringExtra("buy_no");
            mTextSpec = intent.getStringExtra(Constants.TEXT_SPEC);
            mReadyPay = new ArrayList<ShopCartData>();
            initdata();
        }
        getuseraddress2();
    }

    @Override
    protected void initListener() {
        ImageView imageView = (ImageView) findViewById(R.id.iv_tupian);
        imageView.setImageResource(R.drawable.xiantiap);
        ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dzongjia = 0;
        cashing_packet = 0;
        // 获取地址 //todo 获取上个界面传的收货地址
//        user_accept_name = getIntent().getStringExtra("user_accept_name");
//        System.out.println("name==============" + user_accept_name);
//        if (user_accept_name != null) {
//            province = getIntent().getStringExtra("province");
//            city = getIntent().getStringExtra("city");
//            area = getIntent().getStringExtra("user_area");
//            user_address = getIntent().getStringExtra("user_address");
//            user_mobile = getIntent().getStringExtra("user_mobile");
//
//            tv_user_name.setText("收货人：" + user_accept_name);
//            tv_user_address.setText("地址：" + province + "、" + city + "、" + area
//                    + "、" + user_address);
//            tv_user_phone.setText(user_mobile);
//        } else {
        //获取地址
        if (mHasPay) {
            return;
        }


        // 微信支付成功后关闭此界面
        if (flag) {
//            userloginqm();
            delayPayState(PAY_FINISH);
            finish();
            return;
        }


        // 立即购买
        img_ware = (ImageView) findViewById(R.id.img_ware);
        tv_warename = (TextView) findViewById(R.id.tv_ware_name);
        tv_color = (TextView) findViewById(R.id.tv_color);
        tv_size = (TextView) findViewById(R.id.tv_size);
        ll_ljgm = (LinearLayout) findViewById(R.id.ll_ljgm);
        getuserhongbao(user_name);
    }

    public static Handler handlerll;

    /**
     * 获取用户红包
     *
     * @param user_name
     */
    private void getuserhongbao(String user_name) {
        String strUrlone = URLConstants.REALM_NAME_LL + "/get_user_model?username=" + user_name + "";
        AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    JSONObject obj = object.getJSONObject("data");
                    if (status.equals("y")) {
                        UserRegisterllData data = new UserRegisterllData();
                        data.hongbao = obj.getDouble("packet");
                        mUserRedPacket = data.hongbao;
                        mUserhongbao = String.valueOf(mUserRedPacket);
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
                double needPayMoney = express_fee;
                if (isCheck) {
                    if (mUserRedPacket == 0.0) {
                        heji.setVisibility(View.VISIBLE);
                        tv_hongbao.setText("无可抵红包");
                        kou_hongbao = 0;// 红包为0
                        rl_hongbao.setVisibility(View.GONE);
                        tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + 0 + "元");

                    } else if (cashing_packet == 0) {
                        heji.setVisibility(View.VISIBLE);
                        tv_hongbao.setText("无可抵红包");
                        kou_hongbao = 0;// 无可抵红包
                        rl_hongbao.setVisibility(View.GONE);
                        tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + 0 + "元");

                    } else if (dzongjia == cashing_packet) {
                        heji.setVisibility(View.GONE);
                        tv_hongbao.setText("可用" + cashing_packet + "元红包抵" + cashing_packet + "元");
                        kou_hongbao = 1;// 已低红包
                        rl_hongbao.setVisibility(View.VISIBLE);
                        tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + cashing_packet + "元");

                    } else if (mUserRedPacket >= cashing_packet) {
                        BigDecimal w = new BigDecimal(dzongjia - cashing_packet);
                        dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        needPayMoney += dzongjia;
                        heji.setVisibility(View.VISIBLE);
                        heji.setText("实付款:" + needPayMoney);
                        tv_hongbao.setText("可用" + cashing_packet + "元红包抵" + cashing_packet + "元");
                        kou_hongbao = 1;// 已低红包
                        rl_hongbao.setVisibility(View.VISIBLE);
                        tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + cashing_packet + "元");

                    } else if (mUserRedPacket <= cashing_packet) {
                        BigDecimal w = new BigDecimal(dzongjia - mUserRedPacket);
                        dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        needPayMoney += dzongjia;
                        heji.setVisibility(View.VISIBLE);
                        heji.setText("实付款:" + needPayMoney);
                        tv_hongbao.setText("可用" + mUserRedPacket + "元红包抵" + mUserRedPacket + "元");
                        kou_hongbao = 1;// 已低红包
                        rl_hongbao.setVisibility(View.VISIBLE);
                        tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + mUserRedPacket + "元");
                    }

                    hongbao_tety = isCheck;
                } else {
                    if (mUserRedPacket == 0.0) {
                        heji.setVisibility(View.VISIBLE);
                        tv_hongbao.setText("不可以使用红包");
                        rl_hongbao.setVisibility(View.GONE);
                        tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + 0 + "元");

                    } else if ("0.0".equals(kedi_hongbao)) {
                        heji.setVisibility(View.VISIBLE);
                        tv_hongbao.setText("不可以使用红包");
                        rl_hongbao.setVisibility(View.GONE);
                        tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + 0 + "元");

                    } else if (dzongjia == cashing_packet) {
                        heji.setVisibility(View.VISIBLE);
                        // dzongjia = 0.0;
                        tv_hongbao.setText("可用" + cashing_packet + "元红包抵" + cashing_packet + "元");
                        rl_hongbao.setVisibility(View.VISIBLE);
                        tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + 0 + "元");

                    } else if (mUserRedPacket >= cashing_packet) {
                        BigDecimal w = new BigDecimal(dzongjia + cashing_packet);
                        dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        needPayMoney += dzongjia;
                        heji.setVisibility(View.VISIBLE);
                        heji.setText("实付款:" + needPayMoney);
                        tv_hongbao.setText("可用" + cashing_packet + "元红包抵" + cashing_packet + "元");
                        rl_hongbao.setVisibility(View.VISIBLE);
                        tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + 0 + "元");

                    } else if (mUserRedPacket <= cashing_packet) {
                        BigDecimal w = new BigDecimal(dzongjia + mUserRedPacket);
                        dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        needPayMoney += dzongjia;
                        heji.setVisibility(View.VISIBLE);
                        heji.setText("实付款:" + needPayMoney);
                        tv_hongbao.setText("可用" + mUserRedPacket + "元红包抵" + mUserRedPacket + "元");
                        rl_hongbao.setVisibility(View.VISIBLE);
                        tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + 0 + "元");
                    }

                    hongbao_tety = isCheck;
                    kou_hongbao = 0;// 不抵扣红包
                }

                tv_hb_ye.setText(mUserhongbao);
                tv_hb_ye_2.setText(mUserhongbao);
                tv_hb_ye_3.setText("当前红包:¥" + mUserhongbao + "元");
            }
        });


        /**
         * 选择红包状态下
         */
        double needPayMoney = express_fee;
        if (hongbao_tety) {
            if (mUserRedPacket == 0) {
                tv_hongbao.setText("无可抵红包");
                heji.setVisibility(View.VISIBLE);
                kou_hongbao = 0;// 红包为0
                rl_hongbao.setVisibility(View.GONE);
                tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + 0 + "元");

            } else if ("0.0".equals(kedi_hongbao)) {
                tv_hongbao.setText("无可抵红包");
                heji.setVisibility(View.VISIBLE);
                kou_hongbao = 0;// 无可抵红包
                rl_hongbao.setVisibility(View.GONE);
                tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + 0 + "元");

            } else if (dzongjia == cashing_packet) {
                heji.setVisibility(View.GONE);
                // dzongjia = 0.0;
                tv_hongbao.setText("可用" + cashing_packet + "元红包抵" + cashing_packet + "元");
                kou_hongbao = 1;// 已低红包
                rl_hongbao.setVisibility(View.VISIBLE);
                tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + cashing_packet + "元");

            } else if (mUserRedPacket >= cashing_packet) {
                BigDecimal w = new BigDecimal(dzongjia - cashing_packet);
                dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                needPayMoney += dzongjia;
                heji.setVisibility(View.VISIBLE);
                heji.setText("实付款:" + needPayMoney);
                tv_hongbao.setText("可用" + cashing_packet + "元红包抵" + cashing_packet + "元");
                kou_hongbao = 1;// 已低红包
                rl_hongbao.setVisibility(View.VISIBLE);
                tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + cashing_packet + "元");

            } else if (mUserRedPacket <= cashing_packet) {
                BigDecimal w = new BigDecimal(dzongjia - mUserRedPacket);
                dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                needPayMoney += dzongjia;
                heji.setVisibility(View.VISIBLE);
                heji.setText("实付款:" + needPayMoney);
                tv_hongbao.setText("可用" + mUserRedPacket + "元红包抵" + mUserRedPacket + "元");
                // tv_hongbao.setText("可用"+hongbao+"元红包");
                kou_hongbao = 1;// 已低红包
                rl_hongbao.setVisibility(View.VISIBLE);
                tv_jiaguo.setText("¥" + dzongjia + " , " + mQuantityCount + "件，红包可抵扣: ¥" + mUserRedPacket + "元");
            }

            tv_hb_ye.setText(String.valueOf(mUserRedPacket));
            tv_hb_ye_2.setText(String.valueOf(mUserRedPacket));
            tv_hb_ye_3.setText("当前红包:¥" + mUserRedPacket + "元");

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.PAY_MONEY_ACT_REQUEST:
                    delayPayState(PAY_ORDER);
                    finish();
                    break;
                case Constants.ADD_ADDRESS_ACT_REQUEST:
                    getuseraddress2();
                    break;
                case 100:
                    layout0.setVisibility(View.VISIBLE);
                    layout1.setVisibility(View.GONE);
                    String name = data.getStringExtra("user_accept_name");
                    String user_area = data.getStringExtra("user_area");
                    String user_mobile = data.getStringExtra("user_mobile");
                    String user_address = data.getStringExtra("user_address");
                    String province = data.getStringExtra("province");
                    String city = data.getStringExtra("city");
                    tv_user_name.setText("收货人:" + name);
                    tv_user_address.setText("地址：" + province + "、" + city + "、" + user_area + "、"
                            + user_address);
                    tv_user_phone.setText(user_mobile);
                    break;
            }
        } else if (resultCode == 1 && requestCode == Constants.PAY_MONEY_ACT_REQUEST) {
            finish();
        }
//        else if (resultCode == 2) {
//            delayPayState(PAY_FINISH);
//        } else if (requestCode == 3) {
//            delayPayState(PAY_ORDER);
//        }
        Logger.t(TAG).d("onActivityResult:resultCode= " + resultCode + " requestCode= " + requestCode);
    }

    private static final String TAG = "MyOrderConfrimActivity";

    private void initdata() {

        /**
         * 支付方式
         */
        ll_zhifufs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                System.out.println("支付方式retailPrice=====================" + retailPrice);
                Intent intent = new Intent(MyOrderConfrimActivity.this, ZhiFuFangShiActivity.class);
                intent.putExtra("order_confrim", "order_confrim");
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
                String groupon_price = getIntent().getStringExtra("groupon_price");
                String img_url = getIntent().getStringExtra("img_url");
                String price = getIntent().getStringExtra("price");
                String goods_price = getIntent().getStringExtra("goods_price");
                String jubi = getIntent().getStringExtra("point");
                String shopping_ids = getIntent().getStringExtra("shopping_ids");
                String tuangou_id = getIntent().getStringExtra("tuangou_id");

                Intent intent = new Intent(MyOrderConfrimActivity.this, AddressManagerActivity.class);
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
                intent.putExtra("buy_no", getIntent().getStringExtra("buy_no"));
                startActivityForResult(intent, 100);
            }
        });

        /**
         * 添加收货地址
         */
        layout1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyOrderConfrimActivity.this, AddUserAddressActivity.class);
                startActivityForResult(intent, Constants.ADD_ADDRESS_ACT_REQUEST);
            }
        });


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

        yu_pay2.setOnClickListener(this);
        /**
         * 结算方式
         */
        confrim_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (user_accept_name == null) {
                    Toast.makeText(MyOrderConfrimActivity.this, "您还未添加收货地址",
                            Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("type======结算方式========" + type);
                    loadusertijiao(type, kou_hongbao);// 提交聚团订单

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
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_user_shopping_address?user_name=" + user_name + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject jsonObject = new JSONObject(arg1);
                            System.out.println("输出用户默认收货地址================" + arg1);
                            String status = jsonObject.getString("status");
                            if (status.equals("y")) {
                                try {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsot = jsonArray.getJSONObject(i);
                                        String user_dizhiname = jsot.getString("user_accept_name");
                                        shopping_address_id = jsot.getString("id");
                                        province = jsot.getString("province");
                                        city = jsot.getString("city");
                                        area = jsot.getString("area");
                                        user_mobile = jsot.getString("user_mobile");
                                        user_address = jsot.getString("user_address");
                                        user_accept_name = user_dizhiname;

                                        int is_default = jsot.getInt("is_default");
                                        if (is_default == 1) {
                                            tv_user_name.setText("收货人：" + user_accept_name);
                                            tv_user_address.setText("地址：" + province + "、" + city + "、" + area + "、" + user_address);
                                            tv_user_phone.setText(user_mobile);
                                            dizhi_type = true;
                                            break;
                                        }
                                    }

                                    if (!dizhi_type) {
                                        tv_user_name.setText("收货人：" + user_accept_name);
                                        tv_user_address.setText("地址：" + province + "、" + city + "、" + area + "、" + user_address);
                                        tv_user_phone.setText(user_mobile);
                                    }

                                    SharedPreferences spPreferences = getSharedPreferences(SpConstants.ADDRESS_SP, MODE_PRIVATE);
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

                }, this);
    }

    /**
     * 获取购单物信息 第1个列表数据解析
     */
    private int CURRENT_NUM = 1;
    private final int VIEW_NUM = 10;
    private int RUN_METHOD = -1;

    private void load_list(boolean flag) {

        mReadyPay.clear();
        RUN_METHOD = 1;
        url = URLConstants.REALM_NAME_LL + "/get_shopping_buy?buy_no=" + mBuyNo + "";
        mQuantityCount = 0;
        AsyncHttp.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, String arg1) {
                super.onSuccess(arg0, arg1);
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    String info = object.getString("info");
                    if ("y".equals(status)) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        len = jsonArray.length();
                        double sellPriceCount = 0; //销售价
                        double di_hongbao = 0;  //可抵红包
                        ShopCartData data = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            data = new ShopCartData();
                            data.title = json.getString("title");
                            data.market_price = json.getString("market_price");
                            data.sell_price = json.getString("sell_price");
                            data.cashing_packet = json.getDouble("cashing_packet");
                            data.exchange_price = json.getString("exchange_price");
                            data.exchange_point = json.getString("exchange_point");
                            data.spec_text = json.getString("spec_text");
                            data.id = json.getString("id");
                            data.setQuantity(json.getInt("quantity"));
                            data.img_url = json.getString("img_url");
                            mQuantityCount += data.quantity;
                            // 保留2位小数 商品价格
                            double sellPrices = Double.parseDouble(data.sell_price) * data.quantity;
                            sellPriceCount += sellPrices;
                            // 红包金额
                            double redPackageSupport = data.cashing_packet * data.quantity;
                            // 保留2位小数
                            di_hongbao += redPackageSupport;
                            if (!TextUtils.isEmpty(mTextSpec)) {  //当前为立即购买
                                data.spec_text = mTextSpec;
                            }
                            mReadyPay.add(data);
                        }

                        BigDecimal w = new BigDecimal(sellPriceCount);
                        dzongjia = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        retailPrice = Double.toString(dzongjia);

                        BigDecimal b = new BigDecimal(di_hongbao);
                        cashing_packet = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        kedi_hongbao = String.valueOf(cashing_packet);


                        if (mUserRedPacket == 0) {
                            tv_hongbao.setText("不可以使用红包");
                        } else if (cashing_packet == 0) {
                            tv_hongbao.setText("不可以使用红包");
                        } else if (mUserRedPacket >= cashing_packet) {
                            tv_hongbao.setText("可用" + cashing_packet + "元红包抵" + cashing_packet + "元");
                        } else if (mUserRedPacket < cashing_packet) {
                            tv_hongbao.setText("可用" + mUserRedPacket + "元红包抵" + mUserRedPacket + "元");
                        }

                        progress.CloseProgress();
//                        jiekou_type_ysj = WareInformationActivity.jdh_type;
//                        if (jiekou_type_ysj.equals("1")) {  //团购 todo 团购
//                            ll_ljgm.setVisibility(View.VISIBLE);
//                            rl_hongbao.setVisibility(View.GONE);
//                            jubi = data.exchange_point;
//                            dzongjia = Double.parseDouble(data.exchange_price);
//                            Glide.with(MyOrderConfrimActivity.this)
//                                    .load(URLConstants.REALM_NAME_HTTP + data.img_url)
//                                    .into(img_ware);
//                            tv_color.setText(jubi);// 聚币
//                            tv_color.setVisibility(View.GONE);
//                            tv_size.setText(jubi + "聚币" + "+" + dzongjia + "元");// 价格
//                            tv_2.setText("聚兑价:");
//                            tv_warename.setText(data.title);
//                            tv_1.setText("聚币:");
//                            tv_1.setVisibility(View.GONE);
//                        } else {
                        adapter = new ShopingCartOrderAdapter(mReadyPay, MyOrderConfrimActivity.this, handler);
                        list_shop_cart.setAdapter(adapter);
                        loadWeather();
                    } else {
                        progress.CloseProgress();
                        Toast.makeText(MyOrderConfrimActivity.this, info, Toast.LENGTH_SHORT).show();
//                        finish();
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
                        try {
                            list_zf = new ArrayList<JuTuanGouData>();
                            progress.CloseProgress();
                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            if (status.equals("y")) {
                                JSONArray jsonArray = object.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    JuTuanGouData data = new JuTuanGouData();
                                    data.setId(obj.getString("id"));
                                    data.setTitle(obj.getString("title"));
                                    data.setExpress_fee(obj.getInt("express_fee"));
                                    list_zf.add(data);
                                }
                                ZhiFuFangShi = ZhiFuFangShiActivity.title;
                                express_id = ZhiFuFangShiActivity.express_id;
                                express_fee = ZhiFuFangShiActivity.express_fee;
                                double needMoney = express_fee;

                                if (ZhiFuFangShi != null) {
                                    if (express_fee == 0) {
                                        tv_zhifu.setText(ZhiFuFangShi + "(免邮)");
                                        if (jubi != null) {
                                            if (dzongjia > 0) {
                                                heji.setText("实付款:" + " 福利" + jubi + " + " + "¥" + dzongjia);
                                            } else {
                                                heji.setText("实付款:" + " 福利" + jubi);
                                            }
                                        } else {
                                            heji.setText("实付款:" + " ¥" + dzongjia);
                                        }
                                    } else {
                                        String price = String.valueOf(express_fee);
                                        tv_zhifu.setText(ZhiFuFangShi + "(" + "¥" + price + ")");
                                        BigDecimal c = new BigDecimal(dzongjia + express_fee);
                                        needMoney = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                        if (jubi != null) {
                                            heji.setText("实付款:" + " 福利" + jubi + " + " + "¥" + needMoney);
                                        } else {
                                            heji.setText("实付款:" + " ¥" + needMoney);
                                        }
                                    }
                                } else {
                                    ZhiFuFangShi = list_zf.get(0).getTitle();
                                    express_id = list_zf.get(0).getId();
                                    express_fee = list_zf.get(0).getExpress_fee();
                                    if (express_fee == 0) {
                                        tv_zhifu.setText(ZhiFuFangShi + "(免邮)");
                                        if (jubi != null) {
                                            if (dzongjia > 0) {
                                                heji.setText("实付款:" + " 福利" + jubi + " + " + "¥" + dzongjia);
                                            } else {
                                                heji.setText("实付款:" + " 福利" + jubi);
                                            }
                                        } else {
                                            heji.setText("实付款:" + " ¥" + dzongjia);
                                        }
                                    } else {
                                        String price = String.valueOf(express_fee);
                                        tv_zhifu.setText(ZhiFuFangShi + "(" + "¥" + price + ")");
                                        BigDecimal c = new BigDecimal(dzongjia + express_fee);
                                        needMoney = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                        if (jubi != null) {
                                            heji.setText("实付款:" + " 福利" + jubi + " + " + "¥" + needMoney);
                                        } else {
                                            heji.setText("实付款:" + " ¥" + needMoney);
                                        }
                                    }
                                }
                                gethongbao();
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
        jiekou_type_ysj = WareInformationActivity.jdh_type;
        System.out.println("jiekou_type_ysj=====================" + jiekou_type_ysj);
        if ("1".equals(jiekou_type_ysj)) {
            jiekou_type = "add_order_point";// 提交兑换订单
            is_cashing_point = "1";
        } else {
            jiekou_type = "order_save";// 商品提交订单
            is_cashing_point = "0";
        }
        login_sign = mSp.getString(SpConstants.LOGIN_SIGN, "");
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
                    String status = object.getString("status");
                    String info = object.getString("info");
                    if ("y".equals(status)) {
                        JSONObject jsonObject = object.getJSONObject("data");
                        recharge_no = jsonObject.getString("trade_no");
                        if ("1".equals(jiekou_type_ysj)) {
                            WareInformationActivity.jdh_type = "";
                            total_amount = jsonObject.getString("payable_amount");
                        } else {
                            total_amount = jsonObject.getString("total_amount");
                        }
                        mPayState = PAY_ORDER;
                        if ("3".equals(type)) {
                            loadzhidu(recharge_no, total_amount);
                        } else if ("5".equals(type)) {
                            loadweixinzf2(recharge_no, total_amount);
                        } else if ("2".equals(type)) {
                            mHasPay = true;
                            Intent intent = new Intent(MyOrderConfrimActivity.this, TishiCarArchivesActivity.class);
                            intent.putExtra("order_no", recharge_no);
                            intent.putExtra("yue", "yue");
                            startActivityForResult(intent, Constants.PAY_MONEY_ACT_REQUEST);
                        }
                    } else {
                        Toast.makeText(MyOrderConfrimActivity.this, info, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, MyOrderConfrimActivity.this);

    }

    Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void dispatchMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    ali_pay();
                    break;
                case 2:// 微信支付
                    try {
                        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                        if (isPaySupported && mSignData != null) {
                            try {
                                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                api.registerApp(mSignData.getApp_id());
                                PayReq req = new PayReq();
                                req.appId = mSignData.getApp_id();
                                req.partnerId = mSignData.getMch_id();//商户id
                                req.prepayId = mSignData.getPrepay_id();// 7 预支付交易会话ID
                                req.nonceStr = mSignData.getNonce_str();// 3
                                req.timeStamp = mSignData.getTimestamp();// -1
                                req.packageValue = "Sign=WXPay"; //扩展字段
                                req.sign = mSignData.getSign();// -3
                                //3.调用微信支付sdk支付方法
                                flag = api.sendReq(req);
                            } catch (Exception e) {
                                Toast.makeText(MyOrderConfrimActivity.this, "支付失败。。。", Toast.LENGTH_SHORT).show();
                                delayPayState(mPayState);
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MyOrderConfrimActivity.this, "支付失败。。。", Toast.LENGTH_SHORT).show();
                            delayPayState(mPayState);
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    break;
                case PayProxy.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MyOrderConfrimActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        userloginqm();
                        mPayState = PAY_FINISH;
                        delayPayState(mPayState);
                        finish();
                    } else {
                        //                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        Toast.makeText(MyOrderConfrimActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        delayPayState(mPayState);
                        finish();
                    }
                    break;
                }
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
                                finish();
                            } else {
                                progress.CloseProgress();
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
                        Toast.makeText(MyOrderConfrimActivity.this, "异常", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }, MyOrderConfrimActivity.this);


    }

    /**
     * 支付宝
     *
     * @param total_amount
     * @param
     */
    private void loadzhidu(String recharge_no3, String total_amount) {
        recharge_no = recharge_no3;
        total_fee = total_amount;
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/payment_sign?user_id="
                + user_id + "&user_name=" + user_name + "" + "&total_fee="
                + total_amount + "&out_trade_no=" + recharge_no
                + "&payment_type=alipay", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String arg1) {
                super.onSuccess(arg0, arg1);
                try {
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    String info = object.getString("info");
                    if ("y".equals(status)) {
                        JSONObject obj = object.getJSONObject("data");
                        PayProxy.NOTIFY_URL = notify_url = obj.getString("notify_url");
                        handler.sendEmptyMessage(1);
                    } else {
                        ToastUtils.makeTextShort(info);
                    }
                    progress.CloseProgress();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, null);
    }

    /**
     * 微信支付
     *
     * @param total_amount
     * @param
     */
    private void loadweixinzf2(String recharge_no2, String total_amount) {
        recharge_no = recharge_no2;
        String monney = String.valueOf(Double.parseDouble(total_amount) * 100);

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
                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                JSONObject jsonObject = object.getJSONObject("data");
                                String json = jsonObject.toString();
                                mSignData = JSON.parseObject(json, WxSignData.class);
                                progress.CloseProgress();
                                handler.sendEmptyMessage(2);
                            } else {
                                progress.CloseProgress();
                                Toast.makeText(MyOrderConfrimActivity.this, info, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, MyOrderConfrimActivity.this);

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
//                                    userloginqm();
                                    finish();
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(MyOrderConfrimActivity.this, info, Toast.LENGTH_SHORT).show();
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
        String bizContent = "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""
                + total_fee + "\",\"subject\":\"袋鼠车宝\",\"body\":\"商品描述\",\"out_trade_no\":\"" + recharge_no + "\"}";
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(PayProxy.APPID, true, bizContent);
        PayProxy.payV2(this, handler, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fanhui:
                finish();
                break;
            case R.id.yu_pay2:
            case R.id.yu_pay_c2:
                String amountStr = mSp.getString(SpConstants.AMOUNT, "0");
                Double amount = Double.valueOf(amountStr);
                if (amount < dzongjia) {
                    ToastUtils.makeTextShort("您的余额不足，请前往充值中心充值，或请使用其他方式支付");
                    return;
                }
                yu_pay_c0.setChecked(false);
                yu_pay_c1.setChecked(false);
                yu_pay_c2.setChecked(true);
                // 余额支付
                type = "2";
                break;
        }
    }

    /**
     * 处理支付状态
     *
     * @param state PAY_DEFAULT -- PAY_ORDER --PAY_FINISH
     */
    private void delayPayState(int state) {
        switch (state) {
            case PAY_DEFAULT:
                break;
            case PAY_ORDER:
                UIHelper.showOrderActivity(MyOrderConfrimActivity.this, "1");
                EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.UP_MINE_ORDER));
                finish();
                break;
            case PAY_FINISH:
                UIHelper.showMyOrderXqActivity(MyOrderConfrimActivity.this, mTradeNo);
                finish();
                break;
        }
    }

}
