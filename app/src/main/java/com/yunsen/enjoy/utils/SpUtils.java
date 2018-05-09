package com.yunsen.enjoy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.SpConstants;
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
        edit.putString(SpConstants.AMOUNT, "" + userInfo.getAmount());
        edit.putString(SpConstants.GROUP_NAME, userInfo.getGroup_name());
        edit.putString(SpConstants.RESERVE, "" + userInfo.getReserve());
        edit.commit();

    }
}
