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
    private static double mBalance;
    private static boolean mHasLogin;
    private static boolean mHasBound;

    public static boolean mWeiXiHasLogin = false; //微信登录时的标记
    private static SharedPreferences mSp;
    private static boolean mIsVip;
    private static boolean mIsAgent;
    private static int mCertificationState = 0; // 0 未认证，1 正在认证中 2 认证完成

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
     * 是否是Vip
     *
     * @return
     */
    public static boolean isVipAccount() {
        if (mIsVip) {
            return true;
        }
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String groupId = sp.getString(SpConstants.GROUP_ID, "");
        if (Constants.TWO_LINE.equals(groupId) || Constants.THREE_LINE.equals(groupId)) {
            mIsVip = true;
        } else {
            mIsVip = false;
        }
        return mIsVip;
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
        mIsVip = false;
        mIsAgent = false;
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

    public static String getBalance() {
        String balance = mSp.getString(SpConstants.AMOUNT, "0.00");
        return balance;
    }

    /**
     * 是否是代理
     *
     * @return
     */
    public static boolean isAgentUser() {
        if (mIsAgent) {
            return mIsAgent;
        }
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String groupId = sp.getString(SpConstants.GROUP_ID, "");
        if (Constants.THREE_LINE.equals(groupId)) {
            mIsAgent = true;
        } else {
            mIsAgent = false;
        }
        return mIsAgent;
    }

    /**
     * 1\ 真实姓名 real_name 和 身份证号 identity 都为空 未认证
     * 2、提交了认证信息 真实姓名 real_name 和 身份证号 identity 都不为空 状态 status=1
     * 3、提交了认证信息 审核通过 真实姓名 real_name 和 身份证号 identity 都不为空 状态 status=0
     *
     * @return
     */
    public static int getCertificationState() {
        mCertificationState = 0;
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String identityCard = sp.getString(SpConstants.IDENTITY_CARD, "");
        String realName = sp.getString(SpConstants.REAL_NAME, "");
        String status = sp.getString(SpConstants.STATUS, "");
        if (TextUtils.isEmpty(identityCard) && TextUtils.isEmpty(realName)) {
            mCertificationState = 0;
        } else if (!TextUtils.isEmpty(identityCard) && !TextUtils.isEmpty(realName)) {
            if ("1".endsWith(status)) {//提交了认证信息
                mCertificationState = 1;
            } else { //2提交了认证信息 审核通过
                mCertificationState = 2;
            }
        }
        return mCertificationState;
    }
}
