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
        edit.putString(SpConstants.AVATAR, userInfo.getAvatar());
        edit.putString(SpConstants.MOBILE, userInfo.getMobile());
        edit.putString(SpConstants.GROUP_ID, "" + userInfo.getGroup_id());
        edit.putString(SpConstants.USER_NAME, userInfo.getUser_name());
        edit.putString(SpConstants.USER_ID, "" + userInfo.getId());
        edit.putString(SpConstants.POINT, "" + userInfo.getPoint());
        edit.putString(SpConstants.COMPANY_ID, "" + userInfo.getCompany_id());
        edit.putString(SpConstants.BIRTHDAY, userInfo.getBirthday());
        edit.putString(SpConstants.SEX, userInfo.getSex());
        edit.putString(SpConstants.LOGIN_SIGN, userInfo.getLogin_sign());
        edit.putString(SpConstants.NICK_NAME, userInfo.getNick_name());
        edit.putString(SpConstants.AMOUNT, "" + userInfo.getAmount());
        edit.putString(SpConstants.GROUP_NAME, userInfo.getGroup_name());
        edit.putString(SpConstants.RESERVE, "" + userInfo.getReserve());
        edit.putString(SpConstants.REAL_NAME, "" + userInfo.getReal_name());
        edit.putString(SpConstants.PROVINCE, "" + userInfo.getProvince());
        edit.putString(SpConstants.CITY, "" + userInfo.getCity());
        edit.putString(SpConstants.AREA, "" + userInfo.getArea());
        edit.putString(SpConstants.ADDRESS, "" + userInfo.getAddress());
        edit.putString(SpConstants.EMAIL, "" + userInfo.getEmail());
        edit.putString(SpConstants.PACKET, "" + userInfo.getPacket());

        edit.putString(SpConstants.USER_CODE, userInfo.getUser_code());
        edit.putString(SpConstants.CARD, String.valueOf(userInfo.getCard()));
//        edit.putString(SpConstants.PARENT_ID, "" + userInfo.getParent_id());
//        edit.putString(SpConstants.PARENT_NAME, "" + userInfo.getParent_name());
//        edit.putString(SpConstants.USER_LAYER, "" + userInfo.getUser_layer());
//        edit.putString(SpConstants.USER_LIST, "" + userInfo.getUser_list());
//        edit.putString(SpConstants.SALES_ID, "" + userInfo.getSales_id());
//        edit.putString(SpConstants.SALES_NAME, "" + userInfo.getSales_name());
//        edit.putString(SpConstants.AGENCY_ID, "" + userInfo.getAgency_id());
//        edit.putString(SpConstants.AGENCY_NAME, "" + userInfo.getAgency_name());
//        edit.putString(SpConstants.SHOPS_NAME, "" + userInfo.getShops_name());
//        edit.putString(SpConstants.SHOPS_ID, "" + userInfo.getShops_id());
//        edit.putString(SpConstants.STORE_NAME, "" + userInfo.getStore_name());
//        edit.putString(SpConstants.STORE_ID, "" + userInfo.getStore_id());
//        edit.putString(SpConstants.REG_IP, "" + userInfo.getReg_ip());
//        edit.putString(SpConstants.IDENTITY_CARD, "" + userInfo.getIdentity_card());
//        edit.putString(SpConstants.IDENTITY_CARD_A, "" + userInfo.getIdentity_card_a());
//        edit.putString(SpConstants.IDENTITY_CARD_B, "" + userInfo.getIdentity_card_b());
//        edit.putString(SpConstants.TELPHONE, "" + userInfo.getTelphone());
//        edit.putString(SpConstants.TELPHONE, "" + userInfo.getTelphone());
//        private String street;
//        private String qq;
//        private String weixin;
//        private String login_stamp;
//        private int promotion;
//        private int pension;
//        private int reserveb;
//        private int reserves;
//        private int exp;
//        private int exp_weal;
//        private int exp_invest;
//        private int exp_action;
//        private int exp_time;
//        private String vip_card;

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
            edit.putString(SpConstants.LOGIN_SIGN, userInfo.getLogin_sign());
            edit.putString(SpConstants.AVATAR, userInfo.getAvatar());
            edit.putString(SpConstants.MOBILE, userInfo.getMobile());
            edit.putString(SpConstants.GROUP_ID, "" + userInfo.getGroup_id());
            edit.putString(SpConstants.USER_NAME, userInfo.getUser_name());
            edit.putString(SpConstants.USER_ID, "" + userInfo.getId());
            edit.putString(SpConstants.POINT, "" + userInfo.getPoint());
            edit.putString(SpConstants.COMPANY_ID, "" + userInfo.getCompany_id());
            edit.putString(SpConstants.BIRTHDAY, userInfo.getBirthday());
            edit.putString(SpConstants.SEX, userInfo.getSex());
            edit.putString(SpConstants.NICK_NAME, userInfo.getNick_name());
            edit.putString(SpConstants.AMOUNT, "" + userInfo.getAmount());
            edit.putString(SpConstants.GROUP_NAME, userInfo.getGroup_name());
            edit.putString(SpConstants.RESERVE, "" + userInfo.getReserve());
            edit.putString(SpConstants.REAL_NAME, "" + userInfo.getReal_name());
            edit.putString(SpConstants.PROVINCE, "" + userInfo.getProvince());
            edit.putString(SpConstants.CITY, "" + userInfo.getCity());
            edit.putString(SpConstants.AREA, "" + userInfo.getArea());
            edit.putString(SpConstants.ADDRESS, "" + userInfo.getAddress());
            edit.putString(SpConstants.EMAIL, "" + userInfo.getEmail());
            edit.putString(SpConstants.PACKET, "" + userInfo.getPacket());
            edit.putString(SpConstants.USER_CODE, "" + userInfo.getUser_code());
            edit.putString(SpConstants.CARD, String.valueOf(userInfo.getCard()));
        }
        edit.commit();
    }

    /**
     * 从sp中获取用户信息
     *
     * @return
     */
    public static UserInfo getUserInfo() {
        AppContext context = AppContext.getInstance();
        UserInfo userInfo = new UserInfo();
        SharedPreferences sp = context.getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        userInfo.setLogin_sign(sp.getString(SpConstants.LOGIN_SIGN, userInfo.getLogin_sign()));
        userInfo.setAvatar(sp.getString(SpConstants.AVATAR, userInfo.getAvatar()));
        userInfo.setMobile(sp.getString(SpConstants.MOBILE, userInfo.getMobile()));
        userInfo.setGroup_id(Integer.valueOf(sp.getString(SpConstants.GROUP_ID, "" + userInfo.getGroup_id())));
        userInfo.setUser_name(sp.getString(SpConstants.USER_NAME, userInfo.getUser_name()));
        userInfo.setId(Integer.valueOf(sp.getString(SpConstants.USER_ID, "" + userInfo.getId())));
        userInfo.setPoint(Integer.valueOf(sp.getString(SpConstants.POINT, "" + userInfo.getPoint())));
        userInfo.setReal_name(sp.getString(SpConstants.REAL_NAME, userInfo.getReal_name()));
        userInfo.setCompany_id(Integer.valueOf(sp.getString(SpConstants.COMPANY_ID, "" + userInfo.getCompany_id())));
        userInfo.setBirthday(sp.getString(SpConstants.BIRTHDAY, userInfo.getBirthday()));
        userInfo.setSex(sp.getString(SpConstants.SEX, userInfo.getSex()));
        userInfo.setNick_name(sp.getString(SpConstants.NICK_NAME, userInfo.getNick_name()));
        userInfo.setAmount(Double.valueOf(sp.getString(SpConstants.AMOUNT, "" + userInfo.getAmount())));
        userInfo.setGroup_name(sp.getString(SpConstants.GROUP_NAME, userInfo.getGroup_name()));
        userInfo.setReserve(Double.valueOf(sp.getString(SpConstants.RESERVE, "" + userInfo.getReserve())));
        userInfo.setProvince(sp.getString(SpConstants.PROVINCE, "" + userInfo.getProvince()));
        userInfo.setCity(sp.getString(SpConstants.CITY, "" + userInfo.getCity()));
        userInfo.setArea(sp.getString(SpConstants.AREA, "" + userInfo.getArea()));
        userInfo.setAddress(sp.getString(SpConstants.ADDRESS, "" + userInfo.getAddress()));
        userInfo.setEmail(sp.getString(SpConstants.EMAIL, "" + userInfo.getEmail()));
        userInfo.setPacket(Double.valueOf(sp.getString(SpConstants.PACKET, "" + userInfo.getPacket())));
        userInfo.setPacket(Double.valueOf(sp.getString(SpConstants.USER_CODE, "" + userInfo.getUser_code())));
        userInfo.setCard((Double.valueOf(sp.getString(SpConstants.CARD, "" + userInfo.getCard()))));
        return userInfo;
    }

}
