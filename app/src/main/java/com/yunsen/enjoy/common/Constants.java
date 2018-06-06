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
    public static final String MCH_ID = "1395825802";
    //    public static final String APP_SECRET = "e90f651c7db96bbf164ab28a25b3f5cd";
    public static final String APP_SECRET = "ba972155375ade032175041398457297";
//    public static final String APP_SECRET = "";


    public static String WX_Code = "";
    public static QQAuth QQauth;
    public static final String APP_QQ_ID = "222222";// 测试时使用，真正发布的时候要换成自己的APP_ID


    /**
     * 买车界面
     */
    public static final String[] SORT_METHED = new String[]{"智能排序", "最新上架", "价格最低", "价格最高", "车龄最短", "理财最少"};
    public static Map<String, String> SHOT_METHED_VALUE = new HashMap<String, String>();

    static {
        SHOT_METHED_VALUE.put("智能排序", "click desc");
        SHOT_METHED_VALUE.put("最新上架", "add_time desc");
        SHOT_METHED_VALUE.put("价格最低", "sell_price asc");
        SHOT_METHED_VALUE.put("价格最高", "sell_price desc");
        SHOT_METHED_VALUE.put("车龄最短", "");
        SHOT_METHED_VALUE.put("理财最少", "");
    }

    public static final String[] SORT_PRICES = new String[]{"5万以下", "5-10万", "10-15万", "15-20万", "20万以上"};
    public static Map<String, String> SHOT_PRICES_VALUES = new HashMap<>();

    static {
//        SHOT_PRICES_VALUES.put("不限", "sell_price>=0");
        SHOT_PRICES_VALUES.put("5万以下", "sell_price<=5");
        SHOT_PRICES_VALUES.put("5-10万", "sell_price between 5and 10");
        SHOT_PRICES_VALUES.put("10-15万", "sell_price between 10and 15");
        SHOT_PRICES_VALUES.put("15-20万", "sell_price between 15and 20");
        SHOT_PRICES_VALUES.put("20万以上", "and sell_price>20");
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

    /***
     * Activity type
     */
    public static final int REPERTORY_ACT = 1;
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

}
