package com.yunsen.enjoy.http;

/**
 * Created by Administrator on 2018/4/20.
 */

public class URLConstants {
    /**
     * 域名
     */
    public static final String REALM_URL = "http://szlxkg.com";
    /**
     * 头部广告
     */
    public static final String HOME_ADV_URL = "http://szlxkg.com/tools/mobile_ajax.asmx/get_adbanner_list?advert_id=";
    /**
     * 小汽车广告
     */
    public static final String CAR_ADV_URL = "http://szlxkg.com/tools/mobile_ajax.asmx/get_adbanner_list?advert_id=13";
    /**
     * 公告
     */
    public static final String NOTICE_URL = "http://szlxkg.com/tools/mobile_ajax.asmx/get_article_page_size_list";
    /**
     * 推荐汽车,品牌筛选，高级筛选
     */
    public static final String CAR_BRAND_URL = "http://szlxkg.com/tools/mobile_ajax.asmx/get_article_brand";
    /**
     * 服务商
     */
    public static final String SERVICE_PROVIDE = "http://szlxkg.com/tools/mobile_ajax.asmx/get_user_commpany";

    /* ---买车--**/
    /**
     * 买车
     */
    public static final String BUY_CAR_URL = "http://szlxkg.com/tools/mobile_ajax.asmx/get_article_page_size_list";

    /*----发现------**/
    /**
     * 头条
     */
    public static final String DISCOVER_FIRST_URL = "http://szlxkg.com/tools/mobile_ajax.asmx/get_article_page_size_list";

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
    public static String CAR_DETAILS_URL = "http://szlxkg.com/tools/mobile_ajax.asmx/get_article_model?id=";

    public static final String REALM_NAME_WEB = "http://mobile.zams.cn";
    public static final String REALM_NAME_HTTP = "http://mobile.zams.cn";
    public static final String REALM_NAME_LL = "http://mobile.zams.cn/tools/mobile_ajax.asmx";
    public static final String REALM_NAME_FX = "http://mobile.zams.cn";
}
