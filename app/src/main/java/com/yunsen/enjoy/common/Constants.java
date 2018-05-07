package com.yunsen.enjoy.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/23.
 */

public class Constants {
    //    微信
    public static final String APP_ID = "wx88beb35a2752bade";
    public static final String MCH_ID = "1394459302";
    public static final String APP_SECRET = "e94e0ca414b307f026334dbbc124426c";


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

    public static final String[] SORT_PRICES = new String[]{"不限", "3万以下", "3-5万", "5-7万", "7-9万", "9-12万", "12-16万", "16-20万", "20万以上"};
    public static Map<String, String> SHOT_PRICES_VALUES = new HashMap<>();

    static {
        SHOT_PRICES_VALUES.put("不限", "sell_price>=0");
        SHOT_PRICES_VALUES.put("3万以下", "sell_price<=3");
        SHOT_PRICES_VALUES.put("3-5万", "sell_price between 3and 5");
        SHOT_PRICES_VALUES.put("5-7万", "sell_price between 5and 7");
        SHOT_PRICES_VALUES.put("7-9万", "sell_price between 7and 9");
        SHOT_PRICES_VALUES.put("9-12万", "sell_price between 9and 12");
        SHOT_PRICES_VALUES.put("12-16万", "sell_price between 12and 16");
        SHOT_PRICES_VALUES.put("16-20万", "sell_price between 16and 20");
        SHOT_PRICES_VALUES.put("20万以上", "and sell_price>20");
    }


    public final static String[] DISCOVER_TITLE = {"头条", "导购", "用车", "百科"};
    public static final CharSequence[] BUY_CAR = {"新车", "二手车"};
    public static final String CHANNEL_KEY = "channel_key";
    public static final String WEB_URL_KEY = "web_url_key";
    public static final int PHOTO_IC_CARD = 1;
    public static final int PHOTO_IC_CARD_BG = 2;
    public static final int PHOTO_BANK = 3;

    /*******************************************************************
     *                          startActivityResult                   *
     *****************************************************************/
    public static final int PHOTO_ACTIVITY_REQUEST = 10;//照片页面请求码
    public static final int MEET_ADDRESS_REQUEST = 4;


    /*******************************************************************
     *                          申请手机权限时的请求码                      *
     *****************************************************************/
    public static final int CALL_PHONE = 1;//打电话的权限
    public static final int CAMERA = 2;
    public static final int WRITE_EXTERNAL_STORAGE = 3;//存储权限
    public static final int READ_PHONE_STATE = 4;

    /*******************************************************************
     *                            intent key                          *
     *****************************************************************/
    public static final String FRAGMENT_TYPE_KEY = "fragment_type";
    /**
     * 汽车详情
     */
    public static final String CAR_DETAILS_ID = "car_details_id";
    public static final String SERVICE_SHOP_KEY = "service_shop_key";

}
