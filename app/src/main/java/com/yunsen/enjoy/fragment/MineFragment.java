package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.tauth.Tencent;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.activity.dealer.ApplyServiceActivity;
import com.yunsen.enjoy.activity.dealer.MyFacilitatorActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.adapter.ImageAndTextAdapter;
import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.common.wsmanager.WsManager;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UsedFunction;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.PullImageEvent;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.GlideCircleTransform;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class MineFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener {
    private static final String TAG = "MineFragment";
    public static String yth;
    @Bind(R.id.login_icon)
    ImageView loginIcon;
    @Bind(R.id.login_tv)
    TextView loginTv;
    @Bind(R.id.order_img_1)
    ImageView orderImg1;
    @Bind(R.id.order_title_1)
    TextView orderTitle1;
    @Bind(R.id.order_number_1)
    TextView orderNumber1;
    @Bind(R.id.order_img_2)
    ImageView orderImg2;
    @Bind(R.id.order_title_2)
    TextView orderTitle2;
    @Bind(R.id.order_number_2)
    TextView orderNumber2;
    @Bind(R.id.order_img_3)
    ImageView orderImg3;
    @Bind(R.id.order_title_3)
    TextView orderTitle3;
    @Bind(R.id.order_number_3)
    TextView orderNumber3;
    @Bind(R.id.grade_tv)
    TextView gradeTv;
    @Bind(R.id.grade_img)
    ImageView gradeImg;
    @Bind(R.id.has_login_layout)
    LinearLayout hasLoginLayout;
    @Bind(R.id.user_icon_img)
    ImageView userIconImg;
    @Bind(R.id.user_name_tv)
    TextView userNameTv;
    @Bind(R.id.user_sex_img)
    ImageView userSexImg;
    @Bind(R.id.user_contribution_tv)
    TextView userContributionTv;
    @Bind(R.id.user_id_tv)
    TextView userIdTv;
    @Bind(R.id.setting_img)
    ImageView settingImg;
    @Bind(R.id.all_income_money_tv)
    TextView allIncomeMoneyTv;
    @Bind(R.id.all_income_money_layout)
    LinearLayout allIncomeMoneyLayout;
    @Bind(R.id.all_order_count_tv)
    TextView allOrderCountTv;
    @Bind(R.id.all_order_count_layout)
    LinearLayout allOrderCountLayout;
    @Bind(R.id.all_income_tv)
    TextView allIncomeTv;
    @Bind(R.id.all_income_layout)
    LinearLayout allIncomeLayout;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.balance_layout)
    LinearLayout balanceLayout;
    @Bind(R.id.stored_ic_card_tv)
    TextView storedIcCardTv;
    @Bind(R.id.stored_ic_card_layout)
    LinearLayout storedIcCardLayout;
    @Bind(R.id.order_more_tv)
    TextView orderMoreTv;
    @Bind(R.id.grade_more_tv)
    TextView gradeMoreTv;
    @Bind(R.id.spread_layout)
    LinearLayout spreadLayout;
    @Bind(R.id.user_count_tv)
    TextView userCountTv;
    @Bind(R.id.user_count_layout)
    LinearLayout userCountLayout;
    @Bind(R.id.order_count_tv)
    TextView orderCountTv;
    @Bind(R.id.order_count_layout)
    LinearLayout orderCountLayout;
    @Bind(R.id.income_tv)
    TextView incomeTv;
    @Bind(R.id.income_layout)
    LinearLayout incomeLayout;
    @Bind(R.id.no_login_layout)
    LinearLayout noLoginLayout;
    @Bind(R.id.recycler_mine)
    RecyclerView recyclerMine;
    private Activity context;
    private String user_name_phone;
    private String user_id;
    private SharedPreferences mSp;
    private String headimgurl;
    private String headimgurl2;

    private Boolean mIsFacilitator = false; //是否是服务商
    private String mUserName;//用户名
    private ImageAndTextAdapter mFuncAdapter;
    private double mBalance = 0;//余额

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_member;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        Glide.with(MineFragment.this)
                .load(R.mipmap.login_icon)
                .transform(new GlideCircleTransform(getActivity()))
                .into(userIconImg);
    }

    @Override
    protected void initData() {
        recyclerMine.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        ArrayList<UsedFunction> datas = new ArrayList<>();
        datas.add(new UsedFunction(R.mipmap.withdraw_cash, "余额提现"));
        datas.add(new UsedFunction(R.mipmap.money_pakage, "钱包"));
        datas.add(new UsedFunction(R.mipmap.address_img, "收货地址"));
        datas.add(new UsedFunction(R.mipmap.yesterday_money, "昨日收益"));
        datas.add(new UsedFunction(R.mipmap.invitation_win, "有奖邀请"));
        datas.add(new UsedFunction(R.mipmap.upgrade_vip, "升级会员"));
        datas.add(new UsedFunction(R.mipmap.apply_proxy, "申请代理"));
        datas.add(new UsedFunction(R.mipmap.authentication, "实名认证"));
        mFuncAdapter = new ImageAndTextAdapter(getActivity(), R.layout.img_and_text_layout_2, datas);
        recyclerMine.setAdapter(mFuncAdapter);

        mSp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        mIsFacilitator = mSp.getBoolean(SpConstants.HAS_SERVICE_SHOP, false);
        if (AccountUtils.hasLogin()) {
            hasLoginLayout.setVisibility(View.VISIBLE);
            loginIcon.setVisibility(View.GONE);
            loginTv.setVisibility(View.GONE);
            noLoginLayout.setVisibility(View.GONE);
            headimgurl = mSp.getString(SpConstants.HEAD_IMG_URL, "");
            String imgUrl = mSp.getString(SpConstants.USER_IMG, "");
            String imgUrl2 = mSp.getString(SpConstants.AVATAR, "");
            String imgUrl3 = mSp.getString(SpConstants.HEAD_IMG_URL_2, "");
            String iUrl = null;
            if (!TextUtils.isEmpty(imgUrl2)) {
                iUrl = URLConstants.REALM_URL + imgUrl2;
            } else if (!TextUtils.isEmpty(imgUrl)) {
                iUrl = URLConstants.REALM_URL + imgUrl;
            } else if (!TextUtils.isEmpty(imgUrl3)) {
                iUrl = URLConstants.REALM_URL + imgUrl3;
            }

            Glide.with(MineFragment.this)
                    .load(iUrl)
                    .error(R.mipmap.login_icon)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(userIconImg);
            getUserInfo();
        } else {
            hasLoginLayout.setVisibility(View.GONE);
            loginIcon.setVisibility(View.VISIBLE);
            loginTv.setVisibility(View.VISIBLE);
            noLoginLayout.setVisibility(View.VISIBLE);
        }

    }


    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {
        mFuncAdapter.setOnItemClickListener(this);
    }

    @OnClick(R.id.login_icon)
    public void onLoginIconClicked() {
        UIHelper.showUserLoginActivity(getActivity());
    }

    /**
     * 判断是否登录和绑定跳转订单页面
     *
     * @param type
     */
    private void orderClick(String type) {
        if (AccountUtils.hasLogin()) {
            if (AccountUtils.hasBoundPhone()) {
                UIHelper.showOrderActivity(getActivity(), type);
            } else {
                UIHelper.showBundPhoneActivity(getActivity());
            }
        } else {
            if (AccountUtils.hasBoundPhone()) {
                UIHelper.showOrderActivity(getActivity(), type);
            } else {
                UIHelper.showUserLoginActivity(getActivity());
            }
        }
    }


    @OnClick(R.id.order_img_1)
    public void onOrderImg1Clicked() {
        orderClick("1");
    }

    @OnClick(R.id.order_img_2)
    public void onOrderImg2Clicked() {
        orderClick("2");
    }

    @OnClick(R.id.order_img_3)
    public void onOrderImg3Clicked() {
        orderClick("3");
    }


    //账户管理
    public void onAccountManagerLayoutClicked() {
        goLoginOrOtherActivity(PersonCenterActivity.class);
    }

    /**
     * 我的界面二级界面跳转
     *
     * @param cls
     */
    private void goLoginOrOtherActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent(getActivity(), cls);
        if (AccountUtils.hasLogin()) {
            if (AccountUtils.hasBoundPhone()) {
                getActivity().startActivity(intent);
            } else {
                UIHelper.showBundPhoneActivity(getActivity());
            }
        } else {
            if (AccountUtils.hasBoundPhone()) {
                getActivity().startActivity(intent);
            } else {
                UIHelper.showUserLoginActivity(getActivity());
            }
        }
    }

    /**
     * 我是服务商的判断跳转
     */
    private void goLoginOrIsFacilitator() {
        Intent intent = null;
        if (!mIsFacilitator) {
            intent = new Intent(getActivity(), ApplyServiceActivity.class);
        } else {
            intent = new Intent(getActivity(), MyFacilitatorActivity.class);
        }
        if (AccountUtils.hasLogin()) {
            if (AccountUtils.hasBoundPhone()) {
                getActivity().startActivity(intent);
            } else {
                UIHelper.showBundPhoneActivity(getActivity());
            }
        } else {
            if (AccountUtils.hasBoundPhone()) {
                getActivity().startActivity(intent);
            } else {
                UIHelper.showUserLoginActivity(getActivity());
            }
        }
    }


    /**
     * 判断是否登录和绑定跳转我的资产
     *
     * @param type
     */
    private void assetsClick(String type) {
        if (AccountUtils.hasLogin()) {
            if (AccountUtils.hasBoundPhone()) {
                UIHelper.showAssetsActivity(getActivity(), type);
            } else {
                UIHelper.showBundPhoneActivity(getActivity());
            }
        } else {
            if (AccountUtils.hasBoundPhone()) {
                UIHelper.showAssetsActivity(getActivity(), type);
            } else {
                UIHelper.showUserLoginActivity(getActivity());
            }
        }
    }


    /**
     * 加载订单信息
     */
    private void load_list() {

        System.out.println("=========user_id============" + user_id);
        AsyncHttp.get(URLConstants.REALM_NAME_LL
                        + "/get_order_page_size_list?user_id=" + user_id + ""
                        + "&page_size=1000&page_index=1&strwhere=datatype=1&orderby=",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        ArrayList<String> list_1 = new ArrayList<String>();
                        ArrayList<String> list_2 = new ArrayList<String>();
                        ArrayList<String> list_3 = new ArrayList<String>();
                        ArrayList<String> list_4 = new ArrayList<String>();
                        try {
                            JSONObject object = new JSONObject(arg1);
                            String status_1 = object.getString("status");
                            if ("y".equals(status_1)) {
                                JSONArray jsonArray = object.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    String payment_status = obj.getString("payment_status");
                                    String express_status = obj.getString("express_status");
                                    String status = obj.getString("status");
                                    // 订单状态
                                    if ("1".equals(payment_status)) {
                                        System.out.println("待付款=============");
                                        list_1.add(payment_status);
                                    } else if ("2".equals(payment_status) && "1".equals(express_status) && "2".equals(status)) {
                                        System.out.println("待发货=============");
                                        list_2.add(payment_status);
                                    } else if ("2".equals(payment_status) && "2".equals(express_status) && "2".equals(status)) {
                                        System.out.println("待收货=============");
                                        list_3.add(payment_status);
                                    } else if ("2".equals(payment_status) && "2".equals(express_status) && "3".equals(status)) {
                                        System.out.println("已完成=============");
                                        list_4.add(payment_status);
                                    }
                                }
                                String num = String.valueOf(list_1.size());
                                orderNumber1.setText(num);
                                String num1 = String.valueOf(list_2.size());
                                orderNumber2.setText(num1);
                                String num2 = String.valueOf(list_3.size());
                                orderNumber3.setText(num2);
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                }, getActivity());
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        String nickname = mSp.getString(SpConstants.NICK_NAME, "");
        headimgurl = mSp.getString(SpConstants.HEAD_IMG_URL, "");
        headimgurl2 = mSp.getString(SpConstants.HEAD_IMG_URL_2, "");
        user_name_phone = mSp.getString(SpConstants.USER_NAME, "");
        user_id = mSp.getString(SpConstants.USER_ID, "");

        String loginFlag = mSp.getString(SpConstants.LOGIN_FLAG, "");
        mUserName = mSp.getString(SpConstants.USER_NAME, "");
        if (TextUtils.isEmpty(mUserName)) {
            mUserName = nickname;
        }
        String userId = mSp.getString(SpConstants.USER_CODE, "");
        if (!TextUtils.isEmpty(userId)) {
            userIdTv.setText("ID: " + userId);
        }
        String groupName = mSp.getString(SpConstants.GROUP_NAME, "");
        if (!TextUtils.isEmpty(groupName)) {
            gradeTv.setText(groupName);
        }
        String sex = mSp.getString(SpConstants.SEX, "");
        if (Constants.GIRL.equals(sex)) {
            userSexImg.setImageResource(R.mipmap.girl_img);
        } else if (Constants.BOY.equals(sex)) {
            userSexImg.setImageResource(R.mipmap.boy_img);
        } else {
            userSexImg.setImageResource(R.mipmap.secrecy);
        }

        if (SpConstants.WEI_XIN.equals(loginFlag) || SpConstants.QQ_LOGIN.equals(loginFlag)) {//微信登录 QQ登录
            if (AccountUtils.hasBoundPhone()) {
                getLeXiangUserInfo();//获取乐享用户信息
                load_list();
                requestIsFacilitator();//判断是否是服务商
            } else {
                setUserIconAndName(mUserName, headimgurl2, headimgurl);
            }
        } else {
            if (!TextUtils.isEmpty(user_name_phone)) {//手机登录
                getLeXiangUserInfo();//获取乐享用户信息
                requestIsFacilitator();//判断是否是服务商
                load_list();
            } else {
                hasLoginLayout.setVisibility(View.GONE);
                loginIcon.setVisibility(View.VISIBLE);
                loginTv.setVisibility(View.VISIBLE);
                noLoginLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 是否是服务商
     */
    private void requestIsFacilitator() {
        //facilitator
        HttpProxy.getIsFacilitator(user_id, new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean isFacilitator) {
                mIsFacilitator = isFacilitator;
                SharedPreferences.Editor edit = mSp.edit();
                edit.putBoolean(SpConstants.HAS_SERVICE_SHOP, isFacilitator);
                edit.commit();
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }


    /**
     * 设置用户图标 和 名字
     *
     * @param name
     * @param imgString
     * @param imgUrl
     */
    private void setUserIconAndName(String name, String imgString, String imgUrl) {
        userNameTv.setText(name);
        if (!TextUtils.isEmpty(imgString)) {
            Glide.with(MineFragment.this)
                    .load(imgString)
                    .error(R.mipmap.login_icon)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(userIconImg);
        } else {
            Glide.with(MineFragment.this)
                    .load(imgUrl)
                    .error(R.mipmap.login_icon)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(userIconImg);
        }

    }

    /**
     * 获取乐享用户信息
     */
    public void getLeXiangUserInfo() {
        HttpProxy.getUserInfo(user_name_phone, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                yth = data.getUser_code();
                mBalance = data.getAmount();
                balanceTv.setText(data.getAmountStr()); //钱包
                String nickName = data.getNick_name();
                if (TextUtils.isEmpty(nickName)) {
                    userNameTv.setText(data.getUser_name());
                } else {
                    userNameTv.setText(nickName);
                }
                gradeTv.setVisibility(View.VISIBLE);
                gradeTv.setText(data.getGroup_name());
                String avatar = data.getAvatar();

                if (!TextUtils.isEmpty(avatar) && avatar.startsWith("http")) {
                    Glide.with(MineFragment.this)
                            .load(avatar)
                            .placeholder(R.mipmap.login_icon)
                            .transform(new GlideCircleTransform(getActivity()))
                            .into(userIconImg);
                } else {
                    Glide.with(MineFragment.this)
                            .load(URLConstants.REALM_URL + avatar)
                            .placeholder(R.mipmap.login_icon)
                            .transform(new GlideCircleTransform(getActivity()))
                            .into(userIconImg);
                }
                if (data.getId() != 0) {
                    userIdTv.setText("ID: " + data.getId());
                }
                if (!TextUtils.isEmpty(data.getGroup_name())) {
                    gradeTv.setText(data.getGroup_name());
                }
                String sex = data.getSex();
                if (Constants.GIRL.equals(sex)) {
                    userSexImg.setImageResource(R.mipmap.girl_img);
                } else if (Constants.BOY.equals(sex)) {
                    userSexImg.setImageResource(R.mipmap.boy_img);
                } else {
                    userSexImg.setImageResource(R.mipmap.secrecy);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.ORDER_ACT_REQUEST) {
            load_list();//更新订单信息
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(UpUiEvent event) {
        switch (event.getEventId()) {
            case EventConstants.APP_LOGIN:
                hasLoginLayout.setVisibility(View.VISIBLE);
                loginIcon.setVisibility(View.GONE);
                loginTv.setVisibility(View.GONE);
                noLoginLayout.setVisibility(View.GONE);
                Log.e(TAG, "onEvent: 登录更新");
                WsManager.getInstance().init();
                getUserInfo();
                break;
            case EventConstants.APP_LOGOUT:
                WsManager.getInstance().disconnect();
                hasLoginLayout.setVisibility(View.GONE);
                loginIcon.setVisibility(View.VISIBLE);
                loginTv.setVisibility(View.VISIBLE);
                noLoginLayout.setVisibility(View.VISIBLE);
                mIsFacilitator = false;
                balanceTv.setText("0.00");
                Log.e(TAG, "onEvent: 注销更新");
                orderNumber1.setText("0");
                orderNumber2.setText("0");
                orderNumber3.setText("0");
                gradeTv.setVisibility(View.GONE);
                AccountUtils.clearData();
                SharedPreferences sp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
                sp.edit().clear().commit();
                //                Constants.QQauth = QQAuth.createInstance(Constants.APP_QQ_ID, AppContext.getInstance());
                Tencent tencent = Tencent.createInstance(Constants.APP_QQ_ID, AppContext.getInstance());
                tencent.logout(getActivity());
                break;
            case EventConstants.UP_MINE_ORDER:
                load_list();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserIconEvent(PullImageEvent event) {
        if (event.getEvenId() == EventConstants.USER_ICON) {
            final String imgUrl = event.getImgUrl();
            HttpProxy.putUserIcon(getActivity(), imgUrl, new HttpCallBack<String>() {
                @Override
                public void onSuccess(String responseData) {
                    SharedPreferences.Editor edit = mSp.edit();
                    edit.putString(SpConstants.USER_IMG, imgUrl);
                    edit.commit();
                }

                @Override
                public void onError(Request request, Exception e) {
                    ToastUtils.makeTextShort("上传失败");
                }
            });
            Glide.with(this)
                    .load(URLConstants.REALM_URL + imgUrl)
                    .placeholder(R.mipmap.login_icon)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(userIconImg);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        DialogUtils.closeLoginDialog();
    }


    public void loadUserIcon(Uri selectedImage) {
        //上传图片
        GetImgUtil.pullImageBase4(getActivity(), selectedImage, EventConstants.USER_ICON);
    }


    @OnClick({
            R.id.balance_layout, R.id.user_icon_img, R.id.setting_img, R.id.all_income_money_layout,
            R.id.all_order_count_layout, R.id.all_income_layout, R.id.stored_ic_card_layout, R.id.order_more_tv, R.id.grade_more_tv,
            R.id.spread_layout, R.id.user_count_layout, R.id.order_count_layout, R.id.income_layout})
    public void onViewClicked(View view) {
        if (!AccountUtils.hasLogin()) {
            UIHelper.showUserLoginActivity(getActivity());
        } else if (!AccountUtils.hasBoundPhone()) {
            UIHelper.showBundPhoneActivity(getActivity());
        } else {
            switch (view.getId()) {
                case R.id.user_icon_img:
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).requestPermission(Permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_EXTERNAL_STORAGE);
                    }
                    break;
                case R.id.balance_layout:
                    assetsClick("1");
                    break;
                case R.id.setting_img:
                    UIHelper.showPersonCenterActivity(getActivity());
                    break;
                case R.id.all_income_money_layout:
                    UIHelper.showMonthIncomeActivity(getActivity());
                    break;
                case R.id.all_order_count_layout:
                    UIHelper.showMonthOrderActivity(getActivity());
                    break;
                case R.id.all_income_layout://累计收益
                    UIHelper.showCumulativeIncomeActivity(getActivity());
                    break;
                case R.id.stored_ic_card_layout:
                    UIHelper.showBecomeVipActivity(getActivity());
                    break;
                case R.id.order_more_tv: //我的订单更多
                    UIHelper.showOrderActivity(getActivity(), "0");
                    break;
                case R.id.grade_more_tv:
                    ToastUtils.makeTextShort("排名");
                    break;
                case R.id.spread_layout: //直推
//                    UIHelper.showShareAppInfoActivity(getActivity(), "");
                    break;
                case R.id.user_count_layout:
                    UIHelper.showPersonNumberActivity(getActivity());
                    break;
                case R.id.order_count_layout:
                    UIHelper.showOrderNumberActivity(getActivity());
                    break;
                case R.id.income_layout:// 累计收益
                    UIHelper.showMineAchievementActivity(getActivity());
                    break;

            }
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (!AccountUtils.hasLogin()) {
            UIHelper.showUserLoginActivity(getActivity());
        } else if (!AccountUtils.hasBoundPhone()) {
            UIHelper.showBundPhoneActivity(getActivity());
        } else {
            switch (position) {
                case 0:
                    UIHelper.showWithdrawCashActivity(getActivity(), mBalance);
                    break;
                case 1:
                    UIHelper.showWalletActivity(getActivity());
                    break;
                case 2:
                    UIHelper.showAddressManagerGlActivity(getActivity());
                    break;
                case 3:
                    UIHelper.showCumulativeIncomeActivity(getActivity());// TODO: 2018/7/4 昨日收益
                    break;
                case 4:
                    UIHelper.showExtensionActivity(getActivity());
                    break;
                case 5:
                    UIHelper.showBecomeVipActivity(getActivity());
                    break;
                case 6:
                    UIHelper.showApplyAgentActivity(getActivity());
                    break;
                case 7:
                    UIHelper.showUserCertificationActivity(getActivity());
                    break;
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}