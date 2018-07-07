package com.yunsen.enjoy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;

/**
 * Created by Administrator on 2018/4/26.
 */

public class AccountUtils {
    private static String user_name_phone;
    private static String user_name_key;
    private static String user_id;
    private static String nickname;
    private static String headimgurl;
    private static String unionid;
    private static String access_token;
    private static String sex;
    private static String userName;
    private static String loginSign;

    private static boolean mHasLogin;
    private static boolean mHasBound;

    public static boolean mWeiXiHasLogin = false; //微信登录时的标记
    private static SharedPreferences mSp;

    static {
        mSp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
    }

    /**
     * 是否已经登录
     *
     * @return
     */
    public static boolean hasLogin() {
        if (mHasLogin) {
            return true;
        }
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String userId = sp.getString(SpConstants.USER_ID, "");
        String loginFlag = sp.getString(SpConstants.LOGIN_FLAG, "");
        mHasLogin = !TextUtils.isEmpty(userId) || !TextUtils.isEmpty(loginFlag);
        return mHasLogin;
    }

    /**
     * 是否绑定手机
     *
     * @return
     */
    public static boolean hasBoundPhone() {
        if (mHasBound) {
            return true;
        }
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String userId = sp.getString(SpConstants.USER_ID, "");
        if (TextUtils.isEmpty(userId) || "0".equals(userId)) {
            mHasBound = false;
            return false;
        } else {
            mHasBound = true;
            return true;
        }
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
        mHasBound = false;
        mHasLogin = false;
        mWeiXiHasLogin = false;
        userName = "";
        loginSign = "";
    }

    public static String getUser_name_phone() {
        return user_name_phone;
    }

    public static String getUser_name_key() {
        return user_name_key;
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

    public static String getUser_id() {
        if (TextUtils.isEmpty(user_id)) {
            user_id = mSp.getString(SpConstants.USER_ID, Constants.EMPTY);
        }
        return user_id;
//        return "143";
    }

    public static String getUserName() {
        if (TextUtils.isEmpty(userName)) {
            userName = mSp.getString(SpConstants.USER_NAME, Constants.EMPTY);
        }
        return userName;
//        return "18516500065";
    }

    public static String getLoginSign() {
        if (TextUtils.isEmpty(loginSign)) {
            loginSign = mSp.getString(SpConstants.LOGIN_SIGN, Constants.EMPTY);
        }
        return loginSign;
//        return "21039E0FCD403C2E9C64CDD0515C7110";
    }


}
