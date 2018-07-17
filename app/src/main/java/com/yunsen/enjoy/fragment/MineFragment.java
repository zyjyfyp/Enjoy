package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.yunsen.enjoy.model.AchievementAccountBean;
import com.yunsen.enjoy.model.ProfitCountBean;
import com.yunsen.enjoy.model.UsedFunction;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.PullImageEvent;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.interfaces.OnLeftOnclickListener;
import com.yunsen.enjoy.ui.interfaces.OnRightOnclickListener;
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
    @Bind(R.id.user_count_tv_2)
    TextView userCountTv2;
    @Bind(R.id.order_count_tv_2)
    TextView orderCountTv2;
    @Bind(R.id.income_tv_2)
    TextView incomeTv2;
    @Bind(R.id.achi_root_layout_2)
    LinearLayout achiRootLayout2;
    @Bind(R.id.user_count_tv_3)
    TextView userCountTv3;
    @Bind(R.id.order_count_tv_3)
    TextView orderCountTv3;
    @Bind(R.id.income_tv_3)
    TextView incomeTv3;
    @Bind(R.id.achi_root_layout_3)
    LinearLayout achiRootLayout3;
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
    private String mUserId;
    private String mLoginSign;
    private String mGroupId;
    private String mCardMoney = Constants.EMPTY;
    private AlertDialog mCertificationDialog;
    private AlertDialog mNoCertificationDialog;

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
                .into(loginIcon);
        Glide.with(MineFragment.this)
                .load(R.mipmap.login_icon)
                .transform(new GlideCircleTransform(getActivity()))
                .into(userIconImg);
    }

    @Override
    protected void initData() {
        isShowCertification();

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
        mUserId = mSp.getString(SpConstants.USER_ID, "");
        mLoginSign = mSp.getString(SpConstants.LOGIN_SIGN, "");
        mGroupId = mSp.getString(SpConstants.GROUP_ID, "");
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

    private void isShowCertification() {
        if (!AccountUtils.hasBoundPhone()) {
            return;
        }
        switch (AccountUtils.getCertificationState()) {
            case 0:
                showNoCertificationDialog();
                break;
            case 1:
                showCertificationIngDialog();
                break;
        }
    }

    @Override
    protected void requestData() {

    }

    private void requestPaymentAmountSum() {
        HttpProxy.paymentAmountSumRequest(new HttpCallBack<ProfitCountBean>() {
            @Override
            public void onSuccess(ProfitCountBean responseData) {
                double monthProfitCount = responseData.getMonth_profit_count();
                int orderCount = responseData.getOrder_count();
                double profitCount = responseData.getProfit_count();
                allIncomeMoneyTv.setText(String.valueOf(monthProfitCount));
                allOrderCountTv.setText(String.valueOf(orderCount));
                allIncomeTv.setText(String.valueOf(profitCount));
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    /**
     * 直推
     */
    private void getAchievementAccount() {
        achiRootLayout3.setVisibility(View.GONE);
        achiRootLayout2.setVisibility(View.GONE);

        switch (mGroupId) {
            case Constants.THREE_LINE:
                achiRootLayout3.setVisibility(View.VISIBLE);
                HttpProxy.achievementAccountRequest(URLConstants.AGENT_ACHI_COUNT_URL, new HttpCallBack<AchievementAccountBean>() {
                    @Override
                    public void onSuccess(AchievementAccountBean responseData) {

                        userCountTv3.setText(responseData.getRecordCount() + "人");
                        orderCountTv3.setText(responseData.getOrderCounts() + "单");
                        incomeTv3.setText(responseData.getCumulative_income() + "元");
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }
                });
            case Constants.TWO_LINE:
                achiRootLayout2.setVisibility(View.VISIBLE);
                HttpProxy.achievementAccountRequest(URLConstants.TEAM_ACHI_COUNT_URL, new HttpCallBack<AchievementAccountBean>() {
                    @Override
                    public void onSuccess(AchievementAccountBean responseData) {
                        userCountTv2.setText(responseData.getRecordCount() + "人");
                        orderCountTv2.setText(responseData.getOrderCounts() + "单");
                        incomeTv2.setText(responseData.getCumulative_income() + "元");
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }
                });
            case Constants.ONE_LINE:
                HttpProxy.achievementAccountRequest(URLConstants.ACHIEVEMENT_ACCOUNT_URL, new HttpCallBack<AchievementAccountBean>() {
                    @Override
                    public void onSuccess(AchievementAccountBean responseData) {
                        userCountTv.setText(responseData.getRecordCount() + "人");
                        orderCountTv.setText(responseData.getOrderCounts() + "单");
                        incomeTv.setText(responseData.getCumulative_income() + "元");
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }
                });

                break;
        }


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
                mUserId = String.valueOf(data.getId());
                mUserName = data.getUser_name();
                mLoginSign = data.getLogin_sign();
                mGroupId = String.valueOf(data.getGroup_id());
                balanceTv.setText(data.getAmountStr()); //钱包
                mCardMoney = String.valueOf(data.getCard());
                storedIcCardTv.setText(mCardMoney);//储存卡
                String nickName = data.getNick_name();


                getAchievementAccount();
                requestPaymentAmountSum();
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
                    userIdTv.setText("ID: " + data.getUser_code());
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

    /**
     * 显示认证中dialog
     */
    private void showCertificationIngDialog() {
        if (mCertificationDialog == null) {
            mCertificationDialog = DialogUtils.createYesAndNoTitleDialog(getActivity(), "实名认证", "认证信息正在审核中...", "取消", "确认", new OnLeftOnclickListener() {
                @Override
                public void onLeftClick() {

                }
            }, new OnRightOnclickListener() {
                @Override
                public void onRightClick(int... index) {
                    UIHelper.showUserCertificationActivity(getActivity(), 1);
                }
            });
        }
        if (!mCertificationDialog.isShowing()) {
            mCertificationDialog.show();
        }
    }

    /**
     * 未认证dialog
     */
    private void showNoCertificationDialog() {
        if (mNoCertificationDialog == null) {
            mNoCertificationDialog = DialogUtils.createYesAndNoTitleDialog(getActivity(), "实名认证", "您还未实名认证请前往认证", "取消", "确认", new OnLeftOnclickListener() {
                @Override
                public void onLeftClick() {

                }
            }, new OnRightOnclickListener() {
                @Override
                public void onRightClick(int... index) {
                    UIHelper.showUserCertificationActivity(getActivity(), 0);
                }
            });
        }
        if (!mNoCertificationDialog.isShowing()) {
            mNoCertificationDialog.show();
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
                Log.e(TAG, "onEvent: 注销更新");
                allIncomeMoneyTv.setText("0");
                allOrderCountTv.setText("0");
                allIncomeTv.setText("0");
                balanceTv.setText("0.00");
                storedIcCardTv.setText("0.00");
                orderNumber1.setText("0");
                orderNumber2.setText("0");
                orderNumber3.setText("0");
                userCountTv.setText("0人");
                orderCountTv.setText("0单");
                incomeTv.setText("0元");
                userCountTv2.setText("0人");
                orderCountTv2.setText("0单");
                incomeTv2.setText("0元");
                userCountTv3.setText("0人");
                orderCountTv3.setText("0单");
                incomeTv3.setText("0元");
                mCardMoney = Constants.EMPTY;
                achiRootLayout2.setVisibility(View.GONE);
                achiRootLayout3.setVisibility(View.GONE);
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
            R.id.spread_layout, R.id.user_count_layout, R.id.order_count_layout, R.id.income_layout,
            R.id.user_count_layout_2, R.id.order_count_layout_2, R.id.income_layout_2,
            R.id.user_count_layout_3, R.id.order_count_layout_3, R.id.income_layout_3})
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
//                    assetsClick("1");
                    if (AccountUtils.hasLogin()) {
                        if (AccountUtils.hasBoundPhone()) {
                            UIHelper.showMoneyRecordActivity(getActivity(), Constants.BALANCE_RECORD);
                        } else {
                            UIHelper.showBundPhoneActivity(getActivity());
                        }
                    } else {
                        if (AccountUtils.hasBoundPhone()) {
                            UIHelper.showMoneyRecordActivity(getActivity(), Constants.BALANCE_RECORD);
                        } else {
                            UIHelper.showUserLoginActivity(getActivity());
                        }
                    }
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
                    UIHelper.showCumulativeIncomeActivity(getActivity(), false);
                    break;
                case R.id.stored_ic_card_layout:
                    if (AccountUtils.isVipAccount()) {
                        UIHelper.showStoredCardActivity(getActivity(), mCardMoney);
                    } else {
                        UIHelper.showBecomeVipActivity(getActivity());
                    }
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
                    UIHelper.showPersonNumberActivity(getActivity(), URLConstants.ACHIEVEMENT_CONTENT_URL);
                    break;
                case R.id.order_count_layout:
                    UIHelper.showOrderNumberActivity(getActivity(), URLConstants.ACHIEVEMENT_CONTENT_URL);
                    break;
                case R.id.income_layout:// 累计收益
                    UIHelper.showMineAchievementActivity(getActivity(), URLConstants.ACHIEVEMENT_CONTENT_URL);
                    break;
                case R.id.spread_layout_2:
                    break;
                case R.id.user_count_layout_2:
                    UIHelper.showPersonNumberActivity(getActivity(), URLConstants.TEAM_ACHI_CONTENT_URL);
                    break;
                case R.id.order_count_layout_2:
                    UIHelper.showOrderNumberActivity(getActivity(), URLConstants.TEAM_ACHI_CONTENT_URL);
                    break;
                case R.id.income_layout_2:
                    UIHelper.showMineAchievementActivity(getActivity(), URLConstants.TEAM_ACHI_CONTENT_URL);
                    break;
                case R.id.spread_layout_3:

                    break;
                case R.id.user_count_layout_3:
                    UIHelper.showPersonNumberActivity(getActivity(), URLConstants.AGENT_ACHI_CONTENT_URL);
                    break;
                case R.id.order_count_layout_3:
                    UIHelper.showOrderNumberActivity(getActivity(), URLConstants.AGENT_ACHI_CONTENT_URL);
                    break;
                case R.id.income_layout_3:
                    UIHelper.showMineAchievementActivity(getActivity(), URLConstants.AGENT_ACHI_CONTENT_URL);
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
                    UIHelper.showBalanceCashActivity(getActivity(), mBalance);
                    break;
                case 1:
                    UIHelper.showWalletActivity(getActivity(), mBalance);
                    break;
                case 2:
                    UIHelper.showAddressManagerGlActivity(getActivity());
                    break;
                case 3:
                    UIHelper.showCumulativeIncomeActivity(getActivity(), true);// TODO: 2018/7/4 昨日收益
                    break;
                case 4:
                    UIHelper.showExtensionActivity(getActivity());
                    break;
                case 5:
                    UIHelper.showBecomeVipActivity(getActivity());
                    if (AccountUtils.isVipAccount()) {
                    } else {

                    }
                    break;
                case 6:
                    UIHelper.showApplyAgentActivity(getActivity());
                    break;
                case 7:
                    int state = AccountUtils.getCertificationState();
                    UIHelper.showUserCertificationActivity(getActivity(), state);
                    break;
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

}