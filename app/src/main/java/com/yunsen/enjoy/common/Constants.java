package com.yunsen.enjoy.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/23.
 */

public class Constants {

    public final static String[] DISCOVER_TITLE = {"头条", "导购", "用车", "百科"};
    public static final CharSequence[] BUY_CAR = {"新车", "二手车"};
    public static final String[] SORT_METHED = new String[]{"智能排序", "最新上架", "价格最低", "价格最高", "车龄最短", "理财最少"};
    public static final String CHANNEL_KEY = "channel_key";

    public static Map<String, String> SHOT_METHED_VALUE = new HashMap<String, String>();

    static {
        SHOT_METHED_VALUE.put("智能排序", "click desc");
        SHOT_METHED_VALUE.put("最新上架", "add_time desc");
        SHOT_METHED_VALUE.put("价格最低", "sell_price asc");
        SHOT_METHED_VALUE.put("价格最高", "sell_price desc");
        SHOT_METHED_VALUE.put("车龄最短", "");
        SHOT_METHED_VALUE.put("理财最少", "");
    }

}
