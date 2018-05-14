package com.yunsen.enjoy.utils;

import android.content.*;
import android.text.TextUtils;

import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;

/**
 * Created by Administrator on 2018/4/26.
 */

public class AccountUtils {
    //    private static String ACCOUNT_SP_NAME = "account_info";
    private static String ACCOUNT_SP_NAME = "longuserset";
    private static String ACCOUNT_SP_NAME2 = "longuserset_login";
    private static String user_name_phone;
    private static String user_name_key;
    private static String user_id;
    private static String nickname;
    private static String headimgurl;
    private static String unionid;
    private static String access_token;
    private static String sex;

    /**
     * 是否已经登录
     *
     * @return
     */
    public static boolean hasLogin() {
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String userId = sp.getString(SpConstants.USER_ID, "");
        String loginFlag = sp.getString(SpConstants.LOGIN_FLAG, "");
        return !TextUtils.isEmpty(userId) || !TextUtils.isEmpty(loginFlag);
    }

    /**
     * 是否绑定手机
     *
     * @return
     */
    public static boolean hasBoundPhone() {
//        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(ACCOUNT_SP_NAME, Context.MODE_PRIVATE);
//        user_name_phone = sp.getString("user", "");
//        System.out.println("user_name_phone=================" + user_name_phone);
//        if (!TextUtils.isEmpty(user_name_phone)) {
//            user_id = sp.getString("user_id", "");
//            user_name_key = user_name_phone;
//        }
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String userId = sp.getString(SpConstants.USER_ID, "");
        return !TextUtils.isEmpty(userId);

    }

    /**
     * 退出时清空
     */
    public static void clearData() {
        user_name_phone = "";
        user_name_key = "";
        user_id = "";
        nickname = "";
        headimgurl = "";
        unionid = "";
        access_token = "";
        sex = "";
    }

    public static String getUser_name_phone() {
        return user_name_phone;
    }

    public static String getUser_name_key() {
        return user_name_key;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static String getNickname() {
        return nickname;
    }

    public static String getHeadimgurl() {
        return headimgurl;
    }

    public static String getUnionid() {
        return unionid;
    }

    public static String getAccess_token() {
        return access_token;
    }

    public static String getSex() {
        return sex;
    }
}
