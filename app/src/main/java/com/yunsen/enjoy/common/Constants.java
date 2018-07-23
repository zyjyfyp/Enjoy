package com.yunsen.enjoy.common;


import com.tencent.connect.auth.QQAuth;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/23.
 */

public class Constants {
    //    微信
    public static final String APP_ID = "wxe60c28541b0fa8a2";
    public static final String MCH_ID = "1505676961";
    public static final String APP_SECRET = "ca30ef7495cdd92ee94e1190f78a8735";
    //微信小程序原始ID
    public static final String WX_GH_ID = "gh_aeab7704dad3";
    public static final String WX_MCH_ID = "1502153101";
    public static final String TEXT_SPEC = "text_spec"; //物品销售属性
    public static final String PHONE_NUMBER = "400-832-7990";
    public static final int APPLY_SALE_AFTER_IMG = 5;
    public static final String BUY_TYPE_KEY = "buy_type_key";
    public static final String ACT_DATA = "act_data";
//    public static final String APP_SECRET = "";
//appId=wxe60c28541b0fa8a2&body=zyjytest&device_info=1000&key=ca30ef7495cdd92ee94e1190f78a8735&mch_id=1502153101&nonce_str=ibuaiVcKdpRxkhJA

    public static String WX_Code = "";
    public static QQAuth QQauth;
    public static final String APP_QQ_ID = "1106880995";// 测试时使用，真正发布的时候要换成自己的APP_ID


    /**
     * 买车界面
     */
    public static final String[] SORT_METHED = new String[]{"智能排序", "最新上架", "价格最低", "价格最高"};//, "车龄最短", "理财最少"
    public static Map<String, String> SHOT_METHED_VALUE = new HashMap<String, String>();
    public static final String ALL_CAR_TYPE = "all_car_type";

    static {
        SHOT_METHED_VALUE.put("智能排序", "click desc");
        SHOT_METHED_VALUE.put("最新上架", "add_time desc");
        SHOT_METHED_VALUE.put("价格最低", "sell_price asc");
        SHOT_METHED_VALUE.put("价格最高", "sell_price desc");
//        SHOT_METHED_VALUE.put("车龄最短", "");
//        SHOT_METHED_VALUE.put("理财最少", "");
    }

    public static final String[] SORT_PRICES = new String[]{"不限", "5万以下", "5-10万", "10-15万", "15-20万", "20万以上"};
    public static Map<String, String> SHOT_PRICES_VALUES = new HashMap<>();

    static {
        SHOT_PRICES_VALUES.put("不限", "sell_price>=0");
        SHOT_PRICES_VALUES.put("5万以下", "sell_price<=5");
        SHOT_PRICES_VALUES.put("5-10万", "sell_price between 5and 10");
        SHOT_PRICES_VALUES.put("10-15万", "sell_price between 10and 15");
        SHOT_PRICES_VALUES.put("15-20万", "sell_price between 15and 20");
        SHOT_PRICES_VALUES.put("20万以上", "sell_price>20");
    }


    public final static String[] DISCOVER_TITLE = {"新闻资讯", "导购", "用车", "百科"};
    public static final CharSequence[] BUY_CAR = {"新车", "二手车"};
    public static final String CHANNEL_KEY = "channel_key";
    public static final String WEB_URL_KEY = "web_url_key";
    public static final int PHOTO_IC_CARD = 1;
    public static final int PHOTO_IC_CARD_BG = 2;
    public static final int PHOTO_BANK = 3;

    /**
     * 订单统计take over
     */
    public static final String NO_PAYMENT = "status=1 and payment_status=1";    //待付款订单统计
    public static final String NO_DELIVERY = "status=2 and payment_status=2 and express_status=1";    //待发货订单统计
    public static final String NO_TAKE_OVER = "status=3 and payment_status=2 and express_status=2";    //待收货订单统计
    public static final String APPLY_AFTER_SALE = "status=4 and payment_status=3";    //申请售后订单统计


    /*******************************************************************
     *                          startActivityResult                   *
     *****************************************************************/
    public static final int PHOTO_ACTIVITY_REQUEST = 10;//照片页面请求码
    public static final int MEET_ADDRESS_REQUEST = 4;
    public static final int ADD_ADDRESS_REQUEST = 0; //添加地址 修改地址
    public static final int PAY_MONEY_ACT_REQUEST = 11;//支付页面，服务器的支付
    public static final int ADD_ADDRESS_ACT_REQUEST = 12;//支付页面添加地址
    public static final int PHONE_LOGIN_REQUEST = 14;// 手机登录
    public static final int BIND_BANK_CARD_REQUEST = 15;//绑定银行的页面
    public static final int LOCATION_ADDRESS_REQUEST = 16;//开启gps请求码


    /*******************************************************************
     *                          申请手机权限时的请求码                      *
     *****************************************************************/
    public static final int CALL_PHONE = 1;//打电话的权限
    public static final int CAMERA = 2;
    public static final int WRITE_EXTERNAL_STORAGE = 3;//存储权限
    public static final int READ_PHONE_STATE = 4;
    public static final int ORDER_ACT_REQUEST = 5; //跳转订单页面

    /*******************************************************************
     *                            intent key                          *
     *****************************************************************/
    public static final String FRAGMENT_TYPE_KEY = "fragment_type";
    /**
     * 搜索的key
     */
    public static final String SEARCH_KEY = "search_key";

    /**
     * 汽车详情
     */
    public static final String CAR_DETAILS_ID = "car_details_id";
    public static final String SERVICE_SHOP_KEY = "service_shop_key";
    /**
     * 看车 汽车id key
     */
    public static final String WATCH_CAR_ID = "watch_car_id";
    /**
     * 买车 汽车id
     */
    public static final String APPLY_BUY_CAR_ID = "apply_buy_car_id";
    public static final String APPLY_BUY_CAR_KEY = "apply_buy_car_key";

    public static final String ADDRESS_KEY = "address_key"; //预约看车地址
    public static final String POST_CODE_KEY = "post_code_key";
    public static final String INVOICE_TITLE_KEY = "invoice_title_key";

    public static final String CHANNEL_NAME_KEY = "changeName";
    public static final String CATEGORY_ID_KEY = "categoryIdKey";
    public static final String ACT_NAME_KEY = "activity_name";
    /**
     * 商品详情
     */
    public static final String GOODS_ID_KEY = "goods_id";
    public static final String ACT_TYPE_KEY = "act_type_key";
    public static final String REMAINING_TIME = "remaining_time_key";
    /**
     * 提现
     */
    public static final String BALANCE = "balance";

    public static final String ORDER_DATA = "ORDER_NO";

    /***
     * 分享
     */
    public static final String SHARE_IMG_URL = "share_img_url";
    public static final String SHARE_TYPE = "share_type";
    public static final int SHARE_APP_INFO = 1; //分享app
    public static final String SHARE_URL = "share_info";
    public static final int SHARE_GOODS_INFO = 2; //分享商品
    public static final String SHARE_TITLE = "share_title"; //分享标题
    public static final String SHARE_DESCRIPTION = "share_description";//描述

    /**
     * webView
     */
    public static final String WEB_VIEW_TYPE_KEY = "web_view_type_key";

    /**
     * 资金详情
     */
    public static final String MY_ASSETS_INDEX_KEY = "status";
    /**
     * 注册成为服务商
     */
    public static final String APPLY_FACILITATOR_KEY = "apply_facilitator_key";
    public static final String BUNDLE = "bundle";
    public static final int APPLY_SERVICE_REQUEST_1 = 1;
    public static final int APPLY_SERVICE_REQUEST_2 = 2;
    public static final int APPLY_SERVICE_REQUEST_3 = 3;
    public static final int APPLY_SERVICE_REQUEST_4 = 4;

    public static final int DEFAULT_BUY = 1;
    public static final int POINT_BUY = 2;

    public static final String NONE = "NONE";                   //普通消息
    public static final String Connected = "CONNECTED";         //服务链接消息
    public static final String Contact = "CONTACT";             //好友通知消息
    public static final String Profile = "PROFILE";             //资料通知消息
    public static final String Cmd = "CMD";                     //通用命令通知消息
    public static final String Info = "INFO";                   //提示条通知消息
    public static final String Grp = "GRP";                     //群组通知消息
    public static final String Diz = "DIZ";                     //讨论组通知消息
    public static final String Read = "READ";                   //已读通知消息
    public static final String PSCmd = "PSCMD";                 //公众服务命令消息
    public static final String CmdMsg = "CMDMSG";               //命令消息
    public static final String MESSAGE = "message";               //消息


}
