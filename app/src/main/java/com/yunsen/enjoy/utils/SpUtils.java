package com.yunsen.enjoy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.model.AuthorizationModel;
import com.yunsen.enjoy.model.UserInfo;

/**
 * Created by Administrator on 2018/5/9.
 */

public class SpUtils {

    public static void saveUserInfo(UserInfo userInfo) {
        AppContext context = AppContext.getInstance();
        SharedPreferences sp = context.getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("login_sign", userInfo.getLogin_sign());
        edit.putString("avatar", userInfo.getAvatar());
        edit.putString(SpConstants.MOBILE, userInfo.getMobile());
        edit.putString("group_id", "" + userInfo.getGroup_id());
        edit.putString(SpConstants.USER_NAME, userInfo.getUser_name());
        edit.putString("user_id", "" + userInfo.getId());
        edit.putString("point", "" + userInfo.getPoint());
        edit.putString("real_name", userInfo.getReal_name());
        edit.putString("company_id", "" + userInfo.getCompany_id());
        edit.putString("birthday", userInfo.getBirthday());
        edit.putString("sex", userInfo.getSex());
        edit.putString(SpConstants.LOGIN_SIGN, userInfo.getLogin_sign());
        edit.putString(SpConstants.NICK_NAME, userInfo.getNick_name());
        edit.putString(SpConstants.AMOUNT, "" + userInfo.getAmount());
        edit.putString(SpConstants.GROUP_NAME, userInfo.getGroup_name());
        edit.putString(SpConstants.RESERVE, "" + userInfo.getReserve());
        edit.putString(SpConstants.REAL_NAME, "" + userInfo.getReal_name());
        edit.commit();
    }

    /**
     * 保存用户信息
     *
     * @param userInfo
     * @param loginType
     */
    public static void saveUserInfo(AuthorizationModel userInfo, String loginType) {
        AppContext context = AppContext.getInstance();
        SharedPreferences sp = context.getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(SpConstants.LOGIN_FLAG, loginType);
        if (userInfo != null) {
            edit.putString("login_sign", userInfo.getLogin_sign());
            edit.putString("avatar", userInfo.getAvatar());
            edit.putString(SpConstants.MOBILE, userInfo.getMobile());
            edit.putString("group_id", "" + userInfo.getGroup_id());
            edit.putString(SpConstants.USER_NAME, userInfo.getUser_name());
            edit.putString("user_id", "" + userInfo.getId());
            edit.putString("point", "" + userInfo.getPoint());
            edit.putString("real_name", userInfo.getReal_name());
            edit.putString("company_id", "" + userInfo.getCompany_id());
            edit.putString("birthday", userInfo.getBirthday());
            edit.putString("sex", userInfo.getSex());
            edit.putString(SpConstants.LOGIN_SIGN, userInfo.getLogin_sign());
            edit.putString(SpConstants.AMOUNT, "" + userInfo.getAmount());
            edit.putString(SpConstants.GROUP_NAME, userInfo.getGroup_name());
            edit.putString(SpConstants.RESERVE, "" + userInfo.getReserve());
            edit.putString(SpConstants.REAL_NAME, "" + userInfo.getReal_name());
        }
        edit.commit();
    }

    public static UserInfo getUserInfo() {
        AppContext context = AppContext.getInstance();
        UserInfo userInfo = new UserInfo();
        SharedPreferences sp = context.getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        userInfo.setLogin_sign(sp.getString("login_sign", userInfo.getLogin_sign()));
        userInfo.setAvatar(sp.getString("avatar", userInfo.getAvatar()));
        userInfo.setMobile(sp.getString(SpConstants.MOBILE, userInfo.getMobile()));
        userInfo.setGroup_id(Integer.valueOf(sp.getString("group_id", "")));
        userInfo.setUser_name(sp.getString(SpConstants.USER_NAME, userInfo.getUser_name()));
        userInfo.setId(Integer.valueOf(sp.getString("user_id", "" + userInfo.getId())));
        userInfo.setPoint(Integer.valueOf(sp.getString("point", "" + userInfo.getPoint())));
        userInfo.setReal_name(sp.getString(SpConstants.REAL_NAME, userInfo.getReal_name()));
        userInfo.setCompany_id(Integer.valueOf(sp.getString("company_id", "" + userInfo.getCompany_id())));
        userInfo.setBirthday(sp.getString("birthday", userInfo.getBirthday()));
        userInfo.setSex(sp.getString("sex", userInfo.getSex()));
        userInfo.setAmount(Integer.valueOf(sp.getString(SpConstants.AMOUNT, "" + userInfo.getAmount())));
        userInfo.setGroup_name(sp.getString(SpConstants.GROUP_NAME, userInfo.getGroup_name()));
        userInfo.setReserve(Integer.valueOf(sp.getString(SpConstants.RESERVE, "" + userInfo.getReserve())));
        return userInfo;
    }

}
