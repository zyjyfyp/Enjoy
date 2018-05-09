package com.yunsen.enjoy.http;

import com.yunsen.enjoy.BuildConfig;

/**
 * Created by Administrator on 2018/4/20.
 */

public class URLConstants {
    /**
     * 域名
     */
    public static final String REALM_URL = BuildConfig.ROOT_URL;

    public static final String REALM_ACCOUNT_URL = "http://szlxkg.com/tools/mobile_ajax.asmx";


    /**
     * 头部广告
     */
    public static final String HOME_ADV_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_adbanner_list?advert_id=";
    /**
     * 小汽车广告
     */
    public static final String CAR_ADV_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_adbanner_list?advert_id=13";
    /**
     * 公告
     */
    public static final String NOTICE_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_page_size_list";
    /**
     * 推荐汽车,品牌筛选，高级筛选
     */
    public static final String CAR_BRAND_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_brand";
    /**
     * 服务商
     */
    public static final String SERVICE_PROVIDE = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_commpany";

    /* ---买车--**/
    /**
     * 买车
     */
    public static final String BUY_CAR_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_page_size_list";

    /*----发现------**/
    /**
     * 头条
     */
    public static final String DISCOVER_FIRST_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_page_size_list";

    /**
     * 消息通知
     */
    private static String NOTICE_HTML_URL = "http://mobile.szlxkg.com/news/show-15933.html";

    public static String getNoticeHtmlUrl(String id) {
        return NOTICE_HTML_URL.replace("15933", id);
    }

    /**
     * 汽车详情
     */
    public static String CAR_DETAILS_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_model?id=";

    /**
     * 预约看车
     */
    public static String APPOINTEMENT_MANAGER = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_order_bespeak";
    /***
     * 用户信息
     */
    public static String PHONE_USER_INFO_URL = URLConstants.REALM_NAME_LL + "/get_user_model?username=";
    /**
     * 保存用户头像
     */
    public static final String SAVE_USER_ICON_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_avatar_save";

    /**
     * 添加收藏
     */
    public static final String ADD_COLLECT_URL = URLConstants.REALM_NAME_LL + "/user_favorite";
    /**
     * 详情服务商
     */
    public static final String SERVICE_SHOP_INFO_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_commpany_content?id=";
    /**
     * 用户余额
     */
    public static final String ACCOUNT_BALANCE_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_payrecord_expenses_sum";
    /**
     * 提现接口withdraw cash
     */
    public static final String WITH_DRAW_CASH_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_expense_list";
    /**
     * 铁杆圈gavelock ring
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_hardcore_list
     */
    public static final String GAVELOCK_RING_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_hardcore_list";
    /**
     * 朋友圈friend
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_friend_list
     */
    public static final String FRIEND_RING_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_friend_list";
    /**
     * 粉丝圈Vermicelli
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_fans_list
     */
    public static final String VERMICELLI_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_fans_list";
    /**
     * 申请服务商-
     * 服务商订单统计数量
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_commpany_orders_total
     */
    public static final String SERVICE_ODER_NUM_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_commpany_orders_total";
    /**
     * 服务商申请提交表单数据
     * https://szlxkg.com/tools/mobile_ajax.asmx/add_user_commpany_2017
     */
    public static final String APPLY_SERVICE_FORM_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_user_commpany_2017";
    /**
     * 预约管理Booking Management
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_order_page_size_list
     */
    public static final String MEET_MANAGEMENT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_order_page_size_list";
    /**
     * 预约看车
     * https://szlxkg.com/tools/mobile_ajax.asmx/add_order_bespeak
     */
    public static final String MEET_CAR_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_order_bespeak";
    /**
     * 获取企业信息
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_commpany_content
     * id: 用户id Enterprise information
     */
    public static final String ENTERPISE_INFO_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_commpany_content";
    /**
     * 取得行业类别Obtain industry
     * https://szlxkg.com/tools/mobile_ajax.asmx/getTrade
     */
    public static final String OBTAIN_INDUSTRY_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/getTrade";
    /**
     * 订单统计
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_order_count
     */
    public static final String USER_ORDER_COUNT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_order_count";
    /**
     * RealmName.REALM_NAME_LL
     * + "/get_order_page_size_list?user_id=" + user_id + ""
     * + "&page_size=1000&page_index=1&strwhere=datatype=1&orderby="
     * 订单列表
     */
    public static final String ORDER_LIST_URL = BuildConfig.ROOT_URL + "/get_order_page_size_list";


    //
//    public static final String REALM_NAME_WEB = "http://mobile.zams.cn";
//    public static final String REALM_NAME_HTTP = "http://mobile.zams.cn";
//    public static final String REALM_NAME_LL = "http://mobile.zams.cn/tools/mobile_ajax.asmx";
//    public static final String REALM_NAME_FX = "http://mobile.zams.cn";
    public static final String REALM_NAME_WEB = "http://szlxkg.com";
    public static final String REALM_NAME_HTTP = "http://szlxkg.com";
    public static final String REALM_NAME_LL = "http://szlxkg.com/tools/mobile_ajax.asmx";
    public static final String REALM_NAME_FX = "http://szlxkg.com";
}
