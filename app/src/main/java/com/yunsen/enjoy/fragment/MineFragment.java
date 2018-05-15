package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
import com.yunsen.enjoy.activity.mine.AppointmentActivity;
import com.yunsen.enjoy.activity.mine.CollectionActivity;
import com.yunsen.enjoy.activity.mine.MyAssetsActivity;
import com.yunsen.enjoy.activity.mine.MyQianBaoActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.activity.mine.TeamActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.PullImageEvent;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.SpUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.GlideCircleTransform;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class MineFragment extends BaseFragment {
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
    @Bind(R.id.order_img_4)
    ImageView orderImg4;
    @Bind(R.id.order_title_4)
    TextView orderTitle4;
    @Bind(R.id.order_number_4)
    TextView orderNumber4;
    @Bind(R.id.account_manager_layout)
    LinearLayout accountManagerLayout;
    @Bind(R.id.collection_layout)
    LinearLayout collectionLayout;
    @Bind(R.id.team_layout)
    LinearLayout teamLayout;
    @Bind(R.id.finance_layout)
    LinearLayout financeLayout;
    @Bind(R.id.recharge_layout)
    LinearLayout rechargeLayout;
    @Bind(R.id.apply_service_layout)
    LinearLayout applyServiceLayout;
    @Bind(R.id.appointment_layout)
    LinearLayout appointmentLayout;
    @Bind(R.id.help_layout)
    LinearLayout helpLayout;
    @Bind(R.id.logout_layout)
    LinearLayout logoutLayout;
    @Bind(R.id.user_icon_img)
    ImageView userIconImg;
    @Bind(R.id.user_name_tv)
    TextView userNameTv;
    @Bind(R.id.phone_num_tv)
    TextView phoneNumTv;
    @Bind(R.id.grade_tv)
    TextView gradeTv;
    @Bind(R.id.grade_img)
    ImageView gradeImg;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.balance_layout)
    LinearLayout balanceLayout;
    @Bind(R.id.freeze_tv)
    TextView freezeTv;
    @Bind(R.id.freeze_layout)
    LinearLayout freezeLayout;
    @Bind(R.id.commission_tv)
    TextView commissionTv;
    @Bind(R.id.commission_layout)
    LinearLayout commissionLayout;
    @Bind(R.id.ready_money_tv)
    TextView readyMoneyTv;
    @Bind(R.id.ready_money_layout)
    LinearLayout readyMoneyLayout;
    @Bind(R.id.has_login_layout)
    LinearLayout hasLoginLayout;

    private Activity context;

    private String nickname;
    private String user_name_phone;
    private String user_id;
    private SharedPreferences mSp;
    private String headimgurl;
    private String unionid;
    private String access_token;
    private String sex;
    private String headimgurl2;
    private String user_name;
    private String strUrlone;
    private UserInfo data;

    private String mUserId;
    private Boolean mIsFacilitator = false;
    private String mUserName;//用户名

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
    }

    @Override
    protected void initData() {
        mSp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        mIsFacilitator = mSp.getBoolean(SpConstants.HAS_SERVICE_SHOP, false);
        if (AccountUtils.hasLogin()) {
            hasLoginLayout.setVisibility(View.VISIBLE);
            loginIcon.setVisibility(View.GONE);
            loginTv.setVisibility(View.GONE);
            SharedPreferences sp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
            nickname = mSp.getString(SpConstants.NICK_NAME, "");
            headimgurl = mSp.getString(SpConstants.HEAD_IMG_URL, "");
            unionid = mSp.getString(SpConstants.UNION_ID, "");
            access_token = mSp.getString(SpConstants.ACCESS_TOKEN, "");
            sex = mSp.getString(SpConstants.SEX, "");

            String imgUrl = sp.getString(SpConstants.USER_IMG, "");
            String imgUrl2 = sp.getString(SpConstants.AVATAR, "");
            String imgUrl3 = sp.getString(SpConstants.HEAD_IMG_URL_2, "");

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
                    .error(R.mipmap.ic_launcher_round)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(userIconImg);
            getUserInfo();

        } else {
            hasLoginLayout.setVisibility(View.GONE);
            loginIcon.setVisibility(View.VISIBLE);
            loginTv.setVisibility(View.VISIBLE);
        }
        //        spPreferences = getActivity().getSharedPreferences(SpConstants.SP_USER_SET, Context.MODE_PRIVATE);
        //        user_name_phone = spPreferences.getString(SpConstants.USER, "");
        //        if (!TextUtils.isEmpty(user_name_phone)) {
        //            user_id = spPreferences.getString(SpConstants.USER_ID, "");
        //            user_name_key = user_name_phone;
        //        }
    }


    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {

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

    @OnClick(R.id.order_img_4)
    public void onOrderImg4Clicked() {
        orderClick("4");
    }

    @OnClick(R.id.account_manager_layout) //账户管理
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

    @OnClick(R.id.collection_layout) //收藏
    public void onCollectionLayoutClicked() {
        goLoginOrOtherActivity(CollectionActivity.class);
    }

    @OnClick(R.id.team_layout) //团队信息
    public void onTeamLayoutClicked() {
        goLoginOrOtherActivity(TeamActivity.class);
    }

    @OnClick(R.id.finance_layout)
    public void onFinanceLayoutClicked() {
        goLoginOrOtherActivity(MyAssetsActivity.class);
    }

    @OnClick(R.id.recharge_layout)
    public void onRechargeLayoutClicked() {
        goLoginOrOtherActivity(MyQianBaoActivity.class);
    }

    @OnClick(R.id.apply_service_layout) //申请服务商（我是服务商）
    public void onApplyServiceLayoutClicked() {
        goLoginOrIsFacilitator();
    }

    @OnClick(R.id.appointment_layout) //预约管理
    public void onAppointmentLayoutClicked() {
        goLoginOrOtherActivity(AppointmentActivity.class);
    }

    @OnClick(R.id.help_layout)
    public void onHelpLayoutClicked() {
        UIHelper.showHelpActivity(getActivity());
    }

    @OnClick(R.id.logout_layout)
    public void onLogoutLayoutClicked() {
        if (AccountUtils.hasLogin()) {
            DialogUtils.showLoginDialog(getActivity());
        } else {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
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

    @OnClick({R.id.user_icon_img, R.id.balance_layout, R.id.freeze_layout, R.id.commission_layout, R.id.ready_money_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_icon_img:
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).requestPermission(Permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_EXTERNAL_STORAGE);
                }
                break;
            case R.id.balance_layout:
                assetsClick("1");
                break;
            case R.id.freeze_layout:
                assetsClick("2");
                break;
            case R.id.commission_layout:
                assetsClick("3");
                break;
            case R.id.ready_money_layout:
                assetsClick("4");
                break;
        }
    }

    /**
     * 第0个列表数据解析
     */
    ArrayList<String> list_0;
    ArrayList<String> list_1;
    ArrayList<String> list_2;
    ArrayList<String> list_3;
    ArrayList<String> list_4;

    /**
     * 加载订单信息
     */
    private void load_list() {
        list_0 = new ArrayList<String>();
        list_1 = new ArrayList<String>();
        list_2 = new ArrayList<String>();
        list_3 = new ArrayList<String>();
        list_4 = new ArrayList<String>();
        System.out.println("=========user_id============" + user_id);
        AsyncHttp.get(URLConstants.REALM_NAME_LL
                        + "/get_order_page_size_list?user_id=" + user_id + ""
                        + "&page_size=1000&page_index=1&strwhere=datatype=1&orderby=",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
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
                                    list_0.add(payment_status);
                                }
                                System.out.println("========list_0.size()======1=====" + list_0.size());
                                String num = String.valueOf(list_0.size());
                                orderNumber1.setText(num);
                                String num1 = String.valueOf(list_1.size());
                                orderNumber2.setText(num1);
                                String num2 = String.valueOf(list_2.size());
                                orderNumber3.setText(num2);
                                String num3 = String.valueOf(list_3.size());
                                orderNumber4.setText(num3);

                                list_0 = null;
                                list_1 = null;
                                list_2 = null;
                                list_3 = null;
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }

                }, getActivity());
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        nickname = mSp.getString(SpConstants.NICK_NAME, "");
        headimgurl = mSp.getString(SpConstants.HEAD_IMG_URL, "");
        headimgurl2 = mSp.getString(SpConstants.HEAD_IMG_URL_2, "");
        user_name_phone = mSp.getString(SpConstants.USER_NAME, "");
        user_id = mSp.getString(SpConstants.USER_ID, "");

        String loginFlag = mSp.getString(SpConstants.LOGIN_FLAG, "");
        mUserName = mSp.getString(SpConstants.USER_NAME, "");
        if (TextUtils.isEmpty(mUserName)) {
            mUserName = mSp.getString(SpConstants.NICK_NAME, "");
        }


        if (SpConstants.WEI_XIN.equals(loginFlag) || SpConstants.QQ_LOGIN.equals(loginFlag)) {//微信登录
            if (AccountUtils.hasBoundPhone()) {
                getLeXiangUserInfo();//获取乐享用户信息
                load_list();
                requestIsFacilitator();//判断是否是服务商
            } else {
                // TODO: 2018/4/26 清空用户数据
                //                        setinten();// 数据清空
                setUserIconAndName(mUserName, headimgurl2, headimgurl);
            }
        } else {
            if (!TextUtils.isEmpty(user_name_phone)) {//手机登录
                getLeXiangUserInfo();//获取乐享用户信息
                load_list();

            } else {
                hasLoginLayout.setVisibility(View.GONE);
                loginIcon.setVisibility(View.VISIBLE);
                loginTv.setVisibility(View.VISIBLE);
            }
        }

        //        if (!TextUtils.isEmpty(nickname)) {//是微信登录
        //            if (!TextUtils.isEmpty(user_name_phone)) {//有绑定手机
        //                getLeXiangUserInfo();//获取乐享用户信息
        //                load_list();
        //                requestIsFacilitator();//判断是否是服务商
        //            } else {
        //                // TODO: 2018/4/26 清空用户数据
        //                //                        setinten();// 数据清空
        //                setUserIconAndName(nickname, headimgurl2, headimgurl);
        //            }
        //        } else {
        //            if (!TextUtils.isEmpty(user_name_phone)) {//手机登录
        //                getLeXiangUserInfo();//获取乐享用户信息
        //                load_list();
        //            } else {
        //                hasLoginLayout.setVisibility(View.GONE);
        //                loginIcon.setVisibility(View.VISIBLE);
        //                loginTv.setVisibility(View.VISIBLE);
        //            }
        //        }

    }

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
            //            Bitmap bitmap = Utils.stringtoBitmap(imgString);
            //            if (bitmap != null) {
            //                bitmap = Utils.toRoundBitmap(bitmap); // 这个时候的图片已经被处理成圆形的了
            //                userIconImg.setImageBitmap(bitmap);
            //            }
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
        strUrlone = URLConstants.REALM_NAME_LL + "/get_user_model?username=" + user_name_phone + "";


        HttpProxy.getUserInfo(user_name_phone, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                // TODO: 2018/5/7 zyjy what?
                //                            double dzongjia = data.getExp() + data.getExp_weal() + data.getExp_invest() + data.getExp_action() + data.getExp_time();
                //                            BigDecimal w = new BigDecimal(dzongjia);
                //                            double zong_jz = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //价值
                SharedPreferences.Editor editor = mSp.edit();
                editor.putString("mobile", data.getMobile());
                editor.putString("avatar", data.getAvatar());
                editor.putString("group_id", "" + data.getGroup_id());
                editor.putString("group_name", data.getGroup_name());
                editor.commit();
                SpUtils.saveUserInfo(data);
                yth = data.getUser_code();
                balanceTv.setText("" + data.getAmount()); //钱包
                freezeTv.setText("" + data.getReserve());//冻结基金
                commissionTv.setText("0");//佣金
                readyMoneyTv.setText("0");// 提现
                String nickName = data.getNick_name();
                if (TextUtils.isEmpty(nickName)) {
                    userNameTv.setText(data.getUser_name());
                } else {
                    userNameTv.setText(nickName);
                    editor.putString(SpConstants.NICK_NAME, nickName);
                }
                gradeTv.setText(data.getGroup_name());
                phoneNumTv.setText("(" + data.getMobile() + ")");
                String avatar = data.getAvatar();

                if (!TextUtils.isEmpty(avatar) && avatar.startsWith("http")) {
                    Glide.with(MineFragment.this)
                            .load(avatar)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .transform(new GlideCircleTransform(getActivity()))
                            .into(userIconImg);
                } else {
                    //                    GetImgUtil.loadLocationImg(getActivity(), userIconImg);
                    Glide.with(MineFragment.this)
                            .load(URLConstants.REALM_URL + avatar)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .transform(new GlideCircleTransform(getActivity()))
                            .into(userIconImg);
                }
                // point
                //                                System.out.println("tp_type===============" + tp_type);
                //                                if (tp_type == false) { todo ??
                //                                    tp_type = false;
                //                                    mImageLoader = initImageLoader(getActivity(), mImageLoader, "test");
                //                                    if (!data.avatar.equals("")) {
                //                                        mImageLoader.displayImage(RealmName.REALM_NAME_FTP + data.avatar, networkImage);
                //                                    } else {
                //                                        if (data.avatar.equals("")) {
                //                                            bitMap = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon);
                //                                            networkImage.setImageBitmap(bitMap);
                //                                        } else {
                //                                            if (!headimgurl.equals("")) {
                //                                                img_head.setVisibility(View.GONE);
                //                                                networkImage.setVisibility(View.VISIBLE);
                //                                                mImageLoader = initImageLoader(getActivity(), mImageLoader, "test");
                //                                                mImageLoader.displayImage(headimgurl, networkImage);
                //                                            } else {
                //                                                if (!headimgurl2.equals("")) {
                //                                                    img_head.setVisibility(View.VISIBLE);
                //                                                    networkImage.setVisibility(View.GONE);
                //                                                    bitmap = BitUtil.stringtoBitmap(headimgurl2);
                //                                                    bitmap = Utils.toRoundBitmap(bitmap, null); // 这个时候的图片已经被处理成圆形的了
                //                                                    img_head.setImageBitmap(bitmap);
                //                                                }
                //                                            }
                //                                        }
                //                                    }
                //                                }
                //                                userpanduan(data.login_sign); 判断是否升级 todo??
            }

            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(UpUiEvent event) {
        switch (event.getEventId()) {
            case EventConstants.APP_LOGIN:
                hasLoginLayout.setVisibility(View.VISIBLE);
                loginIcon.setVisibility(View.GONE);
                loginTv.setVisibility(View.GONE);
                Log.e(TAG, "onEvent: 登录更新");
                getUserInfo();
                break;
            case EventConstants.APP_LOGOUT:
                hasLoginLayout.setVisibility(View.GONE);
                loginIcon.setVisibility(View.VISIBLE);
                loginTv.setVisibility(View.VISIBLE);
                Log.e(TAG, "onEvent: 注销更新");
                balanceTv.setText("");
                freezeTv.setText("");
                commissionTv.setText("");
                readyMoneyTv.setText("");
                orderNumber1.setText("0");
                orderNumber2.setText("0");
                orderNumber3.setText("0");
                orderNumber4.setText("0");
                SharedPreferences sp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
                sp.edit().clear().commit();
                //                Constants.QQauth = QQAuth.createInstance(Constants.APP_QQ_ID, AppContext.getInstance());
                Tencent tencent = Tencent.createInstance(Constants.APP_QQ_ID, getActivity());
                tencent.logout(getActivity());
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
                    SharedPreferences sp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
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
                    .placeholder(R.mipmap.ic_launcher_round)
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


}