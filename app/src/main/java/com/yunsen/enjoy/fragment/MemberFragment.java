package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.ApplyServiceActivity;
import com.yunsen.enjoy.activity.mine.AppointmentActivity;
import com.yunsen.enjoy.activity.mine.CollectionActivity;
import com.yunsen.enjoy.activity.mine.MyAssetsActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.activity.mine.TeamActivity;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.MyOrderData;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.PicassoRoundTransform;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberFragment extends BaseFragment {
    private static final String TAG = "MemberFragment";
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
    private SharedPreferences spPreferences;
    private String user_id;
    private String user_name_key;
    private SharedPreferences spPreferences_login;
    private String headimgurl;
    private String unionid;
    private String access_token;
    private String sex;
    private String headimgurl2;
    private String user_name;
    private String strUrlone;
    private UserRegisterllData data;

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
        if (AccountUtils.hasLogin()) {
            hasLoginLayout.setVisibility(View.VISIBLE);
            loginIcon.setVisibility(View.GONE);
            loginTv.setVisibility(View.GONE);
            getUserInfo();
        } else {
            hasLoginLayout.setVisibility(View.GONE);
            loginIcon.setVisibility(View.VISIBLE);
            loginTv.setVisibility(View.VISIBLE);
        }
        spPreferences = getActivity().getSharedPreferences("longuserset",
                Context.MODE_PRIVATE);
        user_name_phone = spPreferences.getString("user", "");
        System.out.println("user_name_phone================="
                + user_name_phone);
        if (!TextUtils.isEmpty(user_name_phone)) {
            user_id = spPreferences.getString("user_id", "");
            user_name_key = user_name_phone;
        }

        spPreferences_login = getActivity().getSharedPreferences(
                "longuserset_login", Context.MODE_PRIVATE);
        nickname = spPreferences_login.getString("nickname", "");
        headimgurl = spPreferences_login.getString("headimgurl", "");
        unionid = spPreferences_login.getString("unionid", "");
        access_token = spPreferences_login.getString("access_token", "");
        sex = spPreferences_login.getString("sex", "");
        String oauth_openid = spPreferences_login.getString("oauth_openid", "");

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
//        UIHelper.showPersonCenterActivity(getActivity());
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

    @OnClick(R.id.collection_layout) //收藏
    public void onCollectionLayoutClicked() {
        goLoginOrOtherActivity(CollectionActivity.class);
    }

    @OnClick(R.id.team_layout) //团队信息
    public void onTeamLayoutClicked() {
        UIHelper.showTeamActivity(getActivity());
    }

    @OnClick(R.id.finance_layout)
    public void onFinanceLayoutClicked() {
//        UIHelper.showAssetsActivity(getActivity());
        goLoginOrOtherActivity(MyAssetsActivity.class);
    }

    @OnClick(R.id.recharge_layout)
    public void onRechargeLayoutClicked() {
//        UIHelper.showRechargeActivity(getActivity());
        goLoginOrOtherActivity(TeamActivity.class);
    }

    @OnClick(R.id.apply_service_layout) //申请服务商（我是服务商）
    public void onApplyServiceLayoutClicked() {
//        UIHelper.showApplyServiceActivity(getActivity());
        goLoginOrOtherActivity(ApplyServiceActivity.class);
    }

    @OnClick(R.id.appointment_layout) //预约管理
    public void onAppointmentLayoutClicked() {
//        UIHelper.showAppointmentActivity(getActivity());
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
                UIHelper.showPersonCenterActivity(getActivity());
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
    private ArrayList<MyOrderData> list1; //我的余额

    /**
     * //加载订单信息
     */
    private void load_list() {
        list_0 = new ArrayList<String>();
        list_1 = new ArrayList<String>();
        list_2 = new ArrayList<String>();
        list_3 = new ArrayList<String>();
        list_4 = new ArrayList<String>();
        list1 = new ArrayList<MyOrderData>();
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
                            if (status_1.equals("y")) {
                                //								ArrayList<MyOrderData> list1 = new ArrayList<MyOrderData>();
                                JSONArray jsonArray = object.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //									MyOrderData md = new MyOrderData();
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    //									md.setPayment_status(obj.getString("payment_status"));
                                    String payment_status = obj.getString("payment_status");
                                    String express_status = obj.getString("express_status");
                                    String status = obj.getString("status");
                                    // 订单状态
                                    if (payment_status.equals("1")) {
                                        System.out.println("待付款=============");
                                        list_1.add(payment_status);
                                        //									} else if (status.equals("4")) {
                                        //										list_2.add(status)
                                    } else if (payment_status.equals("2") && express_status.equals("1") && status.equals("2")) {
                                        System.out.println("待发货=============");
                                        list_2.add(payment_status);
                                    } else if (payment_status.equals("2") && express_status.equals("2") && status.equals("2")) {
                                        System.out.println("待收货=============");
                                        list_3.add(payment_status);
                                    } else if (payment_status.equals("2") && express_status.equals("2") && status.equals("3")) {
                                        System.out.println("已完成=============");
                                        list_4.add(payment_status);
                                    }

                                    list_0.add(payment_status);
                                    // tv_unpay,tv_delivered,tv_received,tv_payed
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

    private void getUserInfo() {
        try {

            spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
            nickname = spPreferences_login.getString("nickname", "");
            headimgurl = spPreferences_login.getString("headimgurl", "");
            headimgurl2 = spPreferences_login.getString("headimgurl2", "");

            spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
            user_name_phone = spPreferences.getString("user", "");
            System.out.println("user_name_phone=================" + user_name_phone);

            if (!TextUtils.isEmpty(user_name_phone)) {
                user_id = spPreferences.getString("user_id", "");
                user_name_key = user_name_phone;
            }
            System.out.println("user_id=================" + user_id);
            System.out.println("user_name_key=================" + user_name_key);

            if (!TextUtils.isEmpty(nickname)) {
                System.out.println("==11==user_name_phone==" + user_name_phone);
                if (!TextUtils.isEmpty(user_name_phone)) {
                    System.out.println("==11====");
                    getLeXiangUserInfo();//获取乐享用户信息
                    load_list();
                } else {
                    try {
                        System.out.println("==22====");
                        // TODO: 2018/4/26 清空用户数据
//                        setinten();// 数据清空
                        setUserIconAndName(nickname, headimgurl2, headimgurl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("==33======================="
                        + user_name_phone);
                if (!TextUtils.isEmpty(user_name_phone)) {
//                    handler.sendEmptyMessage(-11);
                    getLeXiangUserInfo();//获取乐享用户信息
                    load_list();
                } else {
                    hasLoginLayout.setVisibility(View.GONE);
                    loginIcon.setVisibility(View.VISIBLE);
                    loginTv.setVisibility(View.VISIBLE);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 设置用户图标 和 名字
     *
     * @param name
     * @param imgString
     * @param imgUrl
     */
    private void setUserIconAndName(String name, String imgString, String imgUrl) {
        if (!TextUtils.isEmpty(imgString)) {
            userIconImg.setVisibility(View.VISIBLE);
            userNameTv.setVisibility(View.VISIBLE);
            Bitmap bitmap = Utils.stringtoBitmap(imgString);
            // Bitmap bitmap = UserLoginActivity.bitmap;
            bitmap = Utils.toRoundBitmap(bitmap, null); // 这个时候的图片已经被处理成圆形的了
            userIconImg.setImageBitmap(bitmap);
            userNameTv.setText(name);
        } else {
            Picasso.with(getActivity())
                    .load(imgUrl)
                    .error(R.mipmap.login_icon)
                    .transform(new PicassoRoundTransform())
                    .into(userIconImg);
            userNameTv.setText(name);
        }

    }

    /**
     * 获取乐享用户信息
     */
    public void getLeXiangUserInfo() {
        try {
            System.out.println("======user_name======1=======" + user_name);
            System.out.println("======user_name_key======2=======" + user_name_key);
            strUrlone = URLConstants.REALM_NAME_LL
                    + "/get_user_model?username=" + user_name_key + "";
            System.out.println("======11=============" + strUrlone);
            AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    JSONObject obj;
                    try {
                        System.out.println("======输出用户资料============="
                                + arg1);
                        JSONObject object = new JSONObject(arg1);
                        String status = object.getString("status");
                        if (status.equals("y")) {
                            try {
                                obj = object.getJSONObject("data");
                                data = new UserRegisterllData();
                                data.user_name = obj.getString("user_name");
                                data.user_code = obj.getString("user_code");
                                data.agency_id = obj.getInt("agency_id");
                                data.amount = obj.getString("amount");
                                data.pension = obj.getString("pension");
                                data.packet = obj.getString("packet");
                                data.point = obj.getString("point");
                                data.group_id = obj.getString("group_id");

                                data.login_sign = obj
                                        .getString("login_sign");
                                data.agency_name = obj
                                        .getString("agency_name");
                                data.group_name = obj
                                        .getString("group_name");
                                data.avatar = obj.getString("avatar");
                                data.mobile = obj.getString("mobile");
                                data.vip_card = obj.getString("vip_card");
                                //									exp+exp_weal+exp_invest+exp_action+exp_time

                                double exp = obj.getDouble("exp");
                                double exp_weal = obj.getDouble("exp_weal");
                                double exp_invest = obj.getDouble("exp_invest");
                                double exp_action = obj.getDouble("exp_action");
                                double exp_time = obj.getDouble("exp_time");

                                double dzongjia = exp + exp_weal + exp_invest + exp_action + exp_time;
                                BigDecimal w = new BigDecimal(dzongjia);
                                double zong_jz = w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //价值
                                System.out.println("zong_jz===价值============" + zong_jz);

                                System.out.println("avatar===============" + data.avatar);

                                spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = spPreferences.edit();
                                editor.putString("mobile", data.mobile);
                                editor.putString("avatar", data.avatar);
                                editor.putString("group_id", data.group_id);
                                editor.putString("group_name", data.group_name);
                                editor.commit();

//                                if (!data.vip_card.equals("")) {
//                                    tv_usertag.setText("服务金卡:" + data.vip_card);
//                                } else {
//                                    tv_usertag.setText("健康卡号:" + data.user_code);
//                                }
                                System.out.println("group_name=======企业职员========" + data.group_name);
//                                if (data.group_name.contains("企业职员")) {
//                                    iv_buju.setVisibility(View.VISIBLE);
//                                    ll_saoyisao_qd.setVisibility(View.VISIBLE);
//                                } else {
//                                    iv_buju_dzc.setVisibility(View.VISIBLE);
//                                    img_btn_daizhuce.setVisibility(View.VISIBLE);
//                                }

                                data.exp = obj.getString("exp");

                                yth = data.user_code;
                                balanceTv.setText(data.amount); //钱包
                                freezeTv.setText(data.pension);//养老金
                                commissionTv.setText(data.packet);//钱包
                                readyMoneyTv.setText(data.point);// 福利
                                // point
//                                System.out.println("tp_type===============" + tp_type);
                                System.out.println("data.avatar===============" + data.avatar);
//                                if (tp_type == false) { todo ??
//                                    tp_type = false;
//                                    mImageLoader = initImageLoader(getActivity(), mImageLoader, "test");
//                                    if (!data.avatar.equals("")) {
//                                        mImageLoader.displayImage(RealmName.REALM_NAME_FTP + data.avatar, networkImage);
//                                    } else {
//                                        if (data.avatar.equals("")) {
//                                            bitMap = BitmapFactory.decodeResource(getResources(), R.drawable.app_zams);
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

                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                            data = null;
                            obj = null;
                        } else {

                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable arg0, String arg1) {
                    super.onFailure(arg0, arg1);
                    Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
                }
            }, context);

        } catch (Exception e) {

            e.printStackTrace();
        }
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
                break;
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


}