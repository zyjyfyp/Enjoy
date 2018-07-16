package com.yunsen.enjoy.activity.mine;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.OnWheelChangedListener;
import com.gghl.view.wheelcity.WheelView;
import com.gghl.view.wheelcity.adapters.ArrayWheelAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.CountryAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.PermissionSetting;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.fragment.MineFragment;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.http.down.UpdateApkThread;
import com.yunsen.enjoy.model.AddressInfo;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.model.UserSenJiBean;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.PullImageEvent;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.DatePickerViewDialog;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.GlideCircleTransform;
import com.yunsen.enjoy.widget.MyAlertDialog;
import com.yunsen.enjoy.widget.interfaces.onLeftOnclickListener;
import com.yunsen.enjoy.widget.interfaces.onRightOnclickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import okhttp3.Request;


public class PersonCenterActivity extends BaseFragmentActivity implements OnClickListener {
    private static final int CHOOSE_PICTURE = 0;
    private static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    private static final int ADDRESS_ACT_REQUEST = 3;

    @Bind(R.id.default_address_tv)
    TextView defaultAddressTv;

    private String yth;
    private TextView v2, v7, tv_nicheng, tv_nick_name;
    private RelativeLayout v6, mm, ll_update, rl_nichen;
    ImageView networkImage;
    UserSenJiBean bean;
    private ImageView iv_personal_icon, iv_personal_icon1;
    private TextView tv_name, tv_shenfenzheng, tv_jxdizhi, tv_xqdizhi, tv_banbenhao, tv_city, tv_ka_name;
    private String strUr2 = URLConstants.REALM_ACCOUNT_URL + "/get_apk_version?browser=android";
    private String URL;
    private String content;
    private LinearLayout ll_gender, ll_diqu, ll_shenji;
    String dizhi = "选择地区";
    private String cityTxt, cityTxt1, cityTxt2, cityTxt3;
    String login_sign, sex, nick_name, mobile;
    String user_id;
    private DialogProgress progress;
    UserRegisterllData data;
    protected static Uri tempUri;
    public static String path, time, province, city, area, lng, lat, imagePath, tupian;
    View vi_shenji;
    public static boolean zhuangtai = false;
    private SharedPreferences mSp;
    private String mUserName;
    private TextView emilTv;
    private TextView onlineQQTv;
    private TextView birthdayDayTv;
    private String mBirthday = "";
    private DatePickerViewDialog mDataPicker;
    private String mQQData = "";

    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.person_center_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mSp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserName = mSp.getString(SpConstants.USER_NAME, "");
        user_id = mSp.getString(SpConstants.USER_ID, "");
        progress = new DialogProgress(PersonCenterActivity.this);
        init();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userloginqm();
    }

    @Override
    public void requestData() {
        super.requestData();
        requestDefaultAddress();
    }

    /**
     * \
     * 默认地址
     */
    private void requestDefaultAddress() {
        HttpProxy.getUserDefaultAddress(mUserName, new HttpCallBack<AddressInfo>() {
            @Override
            public void onSuccess(AddressInfo responseData) {
                String address = responseData.getProvince() + responseData.getCity() + responseData.getArea() + responseData.getUser_address();
                defaultAddressTv.setText(address);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

    private void init() {
        networkImage = (ImageView) findViewById(R.id.roundImage_network);
        v2 = (TextView) findViewById(R.id.v2);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_ka_name = (TextView) findViewById(R.id.tv_ka_name);
        tv_nicheng = (TextView) findViewById(R.id.tv_nicheng);
        tv_nick_name = (TextView) findViewById(R.id.edt_xingbie);
        v7 = (TextView) findViewById(R.id.v7);
        mm = (RelativeLayout) findViewById(R.id.mm);
        iv_personal_icon = (ImageView) findViewById(R.id.iv_personal_icon);
        iv_personal_icon1 = (ImageView) findViewById(R.id.iv_personal_icon1);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_banbenhao = (TextView) findViewById(R.id.tv_banbenhao);
        tv_shenfenzheng = (TextView) findViewById(R.id.tv_shenfenzheng);
        tv_jxdizhi = (TextView) findViewById(R.id.tv_jxdizhi);
        tv_xqdizhi = (TextView) findViewById(R.id.tv_xqdizhi);
        ll_update = (RelativeLayout) findViewById(R.id.ll_update);
        rl_nichen = (RelativeLayout) findViewById(R.id.rl_nichen); //昵称
        ll_gender = (LinearLayout) findViewById(R.id.ll_gender);
        ll_diqu = (LinearLayout) findViewById(R.id.ll_diqu);
        ll_shenji = (LinearLayout) findViewById(R.id.ll_shenji);
        vi_shenji = findViewById(R.id.vi_shenji);
        emilTv = ((TextView) (TextView) findViewById(R.id.emil_tv));
        ((RelativeLayout) (findViewById(R.id.emil_layout))).setOnClickListener(this);
        onlineQQTv = ((TextView) (TextView) findViewById(R.id.online_qq_tv));
        ((RelativeLayout) (findViewById(R.id.online_qq_layout))).setOnClickListener(this);

        birthdayDayTv = ((TextView) (TextView) findViewById(R.id.birthday_tv));
        ((RelativeLayout) (findViewById(R.id.birthday_layout))).setOnClickListener(this);
        ((RelativeLayout) (findViewById(R.id.logout_layout))).setOnClickListener(this);

        ll_update.setOnClickListener(this);
        rl_nichen.setOnClickListener(this);
        ll_gender.setOnClickListener(this);
        ll_diqu.setOnClickListener(this);
        mm.setOnClickListener(this);
        networkImage.setOnClickListener(this);
        emilTv.setOnClickListener(this);
        String version = getAppVersionName(PersonCenterActivity.this);
        tv_banbenhao.setText(version);
        tv_banbenhao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });

        ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });


        v6 = (RelativeLayout) findViewById(R.id.v6);
        v6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(PersonCenterActivity.this, AddressManagerGlActivity.class);
                intent.putExtra("order_confrim", "order_confrim");// 标示
                startActivityForResult(intent, ADDRESS_ACT_REQUEST);
            }
        });

    }

    /**
     * 获取用户资料
     */

    private void userloginqm() {
        try {
            String user_name = mSp.getString(SpConstants.USER_NAME, "");
            String strUrlone = URLConstants.REALM_ACCOUNT_URL + "/get_user_model?username=" + user_name + "";
            System.out.println("======11=============" + strUrlone);
            AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    try {
                        JSONObject object = new JSONObject(arg1);
                        String status = object.getString("status");
                        System.out.println("======arg1=============" + arg1);
                        if (status.equals("y")) {
                            JSONObject obj = object.getJSONObject("data");
                            data = new UserRegisterllData();
                            data.user_name = obj.getString("user_name");
                            data.user_code = obj.getString("user_code");
                            data.agency_id = obj.getInt("agency_id");
                            data.amount = obj.getString("amount");
                            data.pension = obj.getString("pension");
                            data.packet = obj.getString("packet");
                            data.point = obj.getString("point");
                            data.group_id = obj.getString("group_id");
                            data.login_sign = obj.getString("login_sign");
                            login_sign = data.login_sign;
                            data.agency_name = obj.getString("agency_name");
                            data.group_name = obj.getString("group_name");
                            data.mobile = obj.getString("mobile");
                            data.exp = obj.getString("exp");
                            data.real_name = obj.getString("real_name");
                            data.avatar = obj.getString("avatar");
                            sex = obj.getString("sex");
                            nick_name = obj.getString("nick_name");
                            data.province = obj.getString("province");
                            data.city = obj.getString("city");
                            data.area = obj.getString("area");
                            data.vip_card = obj.getString("vip_card");
                            mobile = data.user_name;
                            data.email = obj.getString("email");
                            mBirthday = obj.getString("birthday");
                            mQQData = obj.getString("qq");
                            if (mBirthday != null) {
                                mBirthday = mBirthday.trim();
                                int endIndex = mBirthday.indexOf(" ");
                                mBirthday = mBirthday.substring(0, endIndex);
                                birthdayDayTv.setText(mBirthday);
                            }
                            if (!TextUtils.isEmpty(mQQData)) {
                                onlineQQTv.setText(mQQData);
                            }

                            try {
                                if (!TextUtils.isEmpty(data.vip_card)) {
                                    tv_ka_name.setText("服务金卡");
                                    v2.setText(data.vip_card);
                                } else {
                                    tv_ka_name.setText("会员号");
                                    v2.setText(data.user_code);
                                }

                                String nickname = mSp.getString(SpConstants.NICK_NAME, "");
                                if (!nickname.equals("")) {
                                    tv_nicheng.setText(nickname);
                                } else {
                                    if (TextUtils.isEmpty(nick_name)) {
                                        tv_nicheng.setHint("请输入您的昵称");
                                    } else {
                                        tv_nicheng.setText(nick_name);
                                    }
                                }
                                if ("1".equals(sex)) {
                                    sex = "男";
                                } else if ("2".equals(sex)) {
                                    sex = "女";
                                }
                                tv_nick_name.setText(sex);

                                v7.setText(data.mobile);//手机号
                                Editor editor = mSp.edit();
                                editor.putString(SpConstants.AVATAR, data.avatar);
                                editor.commit();
                                if (TextUtils.isEmpty(data.province)) {
                                } else {
                                    tv_city.setText(data.province + "、" + data.city + "、" + data.area);
                                }
                                emilTv.setText(data.email);
                                String avatar = data.avatar;

                                if (!TextUtils.isEmpty(avatar) && avatar.startsWith("http")) {
                                    Glide.with(PersonCenterActivity.this)
                                            .load(avatar)
                                            .placeholder(R.mipmap.login_icon)
                                            .transform(new GlideCircleTransform(PersonCenterActivity.this))
                                            .into(networkImage);
                                } else {
                                    Glide.with(PersonCenterActivity.this)
                                            .load(URLConstants.REALM_URL + avatar)
                                            .placeholder(R.mipmap.login_icon)
                                            .transform(new GlideCircleTransform(PersonCenterActivity.this))
                                            .into(networkImage);
                                }

                            } catch (Exception e) {

                                e.printStackTrace();
                            }

                            String company = obj.getString("company");
                            JSONObject objt = new JSONObject(company);
                            bean = new UserSenJiBean();
                            bean.contact = objt.getString("contact");
                            bean.idcard = objt.getString("idcard");
                            bean.idcard_a = objt.getString("idcard_a");
                            bean.idcard_b = objt.getString("idcard_b");
                            bean.address = objt.getString("address");
                            bean.province = objt.getString("province");
                            bean.city = objt.getString("city");
                            bean.area = objt.getString("area");
                            bean.recommend_name = objt.getString("recommend_name");


                            tv_name = (TextView) findViewById(R.id.tv_name);
                            tv_shenfenzheng = (TextView) findViewById(R.id.tv_shenfenzheng);
                            tv_jxdizhi = (TextView) findViewById(R.id.tv_jxdizhi);
                            tv_xqdizhi = (TextView) findViewById(R.id.tv_xqdizhi);

                            if (bean.province.equals("")) {
                                ll_shenji.setVisibility(View.GONE);
                                vi_shenji.setVisibility(View.GONE);
                            } else {
//                                ll_shenji.setVisibility(View.VISIBLE);
                                vi_shenji.setVisibility(View.VISIBLE);
                                tv_name.setText(bean.contact);
                                tv_shenfenzheng.setText(bean.idcard);
                                String dizhi = bean.province + "、" + bean.city + "、" + bean.area;

                                Picasso.with(PersonCenterActivity.this)
                                        .load(URLConstants.REALM_URL + bean.idcard_a)
                                        .into(iv_personal_icon);
                                Picasso.with(PersonCenterActivity.this)
                                        .load(URLConstants.REALM_URL + bean.idcard_b)
                                        .into(iv_personal_icon1);
                                tv_jxdizhi.setText(dizhi);
                                tv_xqdizhi.setText(bean.address);
                            }
                            bean = null;
                            data = null;
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ;
            }, PersonCenterActivity.this);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    dialog();
                    break;
                case 1:
                    Toast.makeText(PersonCenterActivity.this, "当前版本是最新版本", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }

        ;
    };


    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.ll_update:
                /**
                 * 版本检查
                 */
                AsyncHttp.get(strUr2, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject jsonObject = new JSONObject(arg1);
                            JSONObject jsob = jsonObject.getJSONObject("data");
                            String file_version = jsob.getString("file_version");
                            String file_path = jsob.getString("file_path");
                            URL = URLConstants.REALM_URL + file_path;
                            System.out.println("首页版本URL==============" + URL);
                            String version = getAppVersionName(PersonCenterActivity.this);
                            String c_version = getAppVersionName(PersonCenterActivity.this).trim().replaceAll("\\.", "");
                            float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//服务器
                            float client_version = Float.parseFloat(c_version);//当前
                            content = "有最新版本了，服务器" + file_version + "是否替换当前版本" + version;
                            if (server_version > client_version) {
                                Message message = new Message();
                                message.what = 0;
                                handler.sendMessage(message);
                            } else if (server_version == client_version) {
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } else {
                                ToastUtils.makeTextShort("当前为最新版本");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, PersonCenterActivity.this);

                break;
            case R.id.rl_nichen:
                Intent intent = new Intent(PersonCenterActivity.this, TishiNicknameActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_gender:
                Intent intent1 = new Intent(PersonCenterActivity.this, GenderFangShiActivity.class);
                intent1.putExtra("type", "1");
                startActivity(intent1);
                break;
            case R.id.mm: //账户安全
                Intent intent2 = new Intent(PersonCenterActivity.this, GenderFangShiActivity.class);
                intent2.putExtra("type", "2");
                startActivity(intent2);
                break;
            case R.id.ll_diqu:
                View view = dialogm();
                final MyAlertDialog dialog1 = new MyAlertDialog(PersonCenterActivity.this).builder()
                        .setTitle(dizhi.toString()).setView(view)
                        .setNegativeButton("取消", new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                dialog1.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadusersex();
                    }
                });
                dialog1.show();
                break;
            case R.id.roundImage_network:
                showChoosePicDialog();
                break;
            case R.id.emil_layout:
                Intent intentE = new Intent(PersonCenterActivity.this, TishiNicknameActivity.class);
                intentE.putExtra(Constants.ACT_TYPE_KEY, Constants.EMIL);
                startActivity(intentE);
                break;
            case R.id.online_qq_layout:
                Intent intentOn = new Intent(PersonCenterActivity.this, TishiNicknameActivity.class);
                intentOn.putExtra(Constants.ACT_TYPE_KEY, Constants.ONLINE_QQ);
                startActivity(intentOn);
                break;
            case R.id.birthday_layout:
                showDatePicker();
                break;
            case R.id.logout_layout:
                DialogUtils.showLoginDialog(this);
                break;
            default:
                break;
        }
    }

    /**
     * 显示日志picker
     */
    private void showDatePicker() {
        if (mDataPicker == null) {
            mDataPicker = new DatePickerViewDialog(this);
            mDataPicker.setRightOnclickListener("确定", new onRightOnclickListener() {
                @Override
                public void onRightClick(int... index) {
                    final String date = index[0] + "-" + index[1] + "-" + index[2];
                    HttpProxy.changeBirthday(user_id, mUserName, login_sign, date, new HttpCallBack<Boolean>() {
                        @Override
                        public void onSuccess(Boolean responseData) {
                            if (responseData) {
                                ToastUtils.makeTextShort("修改成功");
                                birthdayDayTv.setText(date);
                            }
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            ToastUtils.makeTextShort("修改失败");
                        }
                    });
                    mDataPicker.dismiss();
                }
            });
            mDataPicker.setLeftOnclickListener("取消", new onLeftOnclickListener() {
                @Override
                public void onLeftClick() {
                    mDataPicker.dismiss();
                }
            });
        }

        if (!mDataPicker.isShowing()) {
            mDataPicker.show();
        }
    }

    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        requestPermission(Permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_EXTERNAL_STORAGE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        requestPermission(Permission.CAMERA, Constants.CAMERA);

                        break;
                }
            }

        });
        builder.create().show();
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        switch (requestCode) {
            case Constants.WRITE_EXTERNAL_STORAGE:
                UIHelper.showPhotoActivity(this, Constants.PHOTO_ACTIVITY_REQUEST);
                break;
            case Constants.CAMERA:
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                //拍照
                case TAKE_PICTURE:
//                    Glide.with(this)
//                            .load(tempUri)
//                            .transform(new GlideCircleTransform(this))
//                            .into(networkImage);
//                    //上传图片
//                    GetImgUtil.pullUserIcon(this, tempUri);
                    GetImgUtil.pullImageBase4(this, tempUri, EventConstants.USER_ICON);
                    break;
                //上传图片
                case Constants.PHOTO_ACTIVITY_REQUEST:
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    GetImgUtil.pullImageBase4(this, selectedImage, EventConstants.USER_ICON);

//                    Glide.with(this)
//                            .load(selectedImage)
//                            .transform(new GlideCircleTransform(this))
//                            .into(networkImage);
                    //上传图片
//                    GetImgUtil.pullUserIcon(this, selectedImage);
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
        if (requestCode == ADDRESS_ACT_REQUEST) {
            requestDefaultAddress();
        }
    }


    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap photo = extras.getParcelable("data");
        if (extras != null) {
            networkImage.setImageBitmap(photo);
            try {
                imagePath = Utils.savePhoto(photo, Environment.getExternalStorageDirectory().getAbsolutePath(),
                        String.valueOf(System.currentTimeMillis()));
                System.out.println("imagePath=======================" + imagePath);

                new Thread() {
                    public void run() {
                        try {
                            FTPClient client = new FTPClient();
                            client.connect("60.205.151.160", 2021);
                            client.login("zams", "zams1230.");
                            SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                            time = f.format(new Date());
                            yth = MineFragment.yth;   // TODO: 2018/4/24 yth 是什么值
                            String remotePathTmp = "phone/" + "" + yth + "";//路径
                            System.out.println("========================" + remotePathTmp);

                            try {
                                client.createDirectory(remotePathTmp);//客户端创建目录
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                client.changeDirectory(remotePathTmp);

                                File file = new File(imagePath);
                                FileInputStream fis = new FileInputStream(file);
                                try {
                                    client.upload(time + ".jpg", fis, 0, 0, null);
                                } catch (FTPDataTransferException e) {
                                    e.printStackTrace();
                                } catch (FTPAbortedException e) {
                                    e.printStackTrace();
                                }
                                fis.close();
                                client.logout();//exit
                            }

                            tupian = "/upload/phone/" + yth + "/" + time + ".jpg";
                            System.out.println("tupian1--------------------------" + tupian);

                        } catch (IllegalStateException e) {
                            e.printStackTrace();//非法状态异常
                        } catch (FTPIllegalReplyException e) {
                            e.printStackTrace();//非法回复异常
                        } catch (FTPException e) {
                            e.printStackTrace();//异常
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        gettouxiang();
                    }

                    ;

                }.start();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    private void gettouxiang() {

        String imgUrl = "/upload/phone/" + yth + "/" + time + ".jpg";
        System.out.println("imgUrl--------------------------" + imgUrl);
        Editor editor = mSp.edit();
        editor.putString(SpConstants.AVATAR, imgUrl);
        editor.commit();
        String strUrl = URLConstants.REALM_ACCOUNT_URL
                + "/user_avatar_save?user_name=" + mUserName + "&user_id=" + user_id + "&user_avatar=" + imgUrl + "&sign=" + login_sign + "";
        AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
            public void onSuccess(int arg0, String arg1) {
                try {
                    System.out.println("arg1--------------------------" + arg1);
                    JSONObject object = new JSONObject(arg1);
                    String status = object.getString("status");
                    String info = object.getString("info");
                    //					onResume();
                    if (status.equals("y")) {
                        //						Toast.makeText(context, info, 200).show();
                        Toast.makeText(PersonCenterActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        System.out.println("上传成功--------------------------");
                    } else {
                        //						Toast.makeText(context, info, 200).show();
                        Toast.makeText(PersonCenterActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ;
        }, PersonCenterActivity.this);
    }

    /**
     * 选择城市
     *
     * @return
     */
    private View dialogm() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.wheelcity_cities_layout, null);
        final WheelView country = (WheelView) contentView
                .findViewById(R.id.wheelcity_country);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(this));

        final String cities[][] = AddressData.CITIES;
        final String ccities[][][] = AddressData.COUNTIES;
        final WheelView city = (WheelView) contentView
                .findViewById(R.id.wheelcity_city);
        city.setVisibleItems(0);

        // 地区选择
        final WheelView ccity = (WheelView) contentView
                .findViewById(R.id.wheelcity_ccity);
        ccity.setVisibleItems(0);// 不限城市

        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities(city, cities, newValue);
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                cityTxt1 = AddressData.PROVINCES[country.getCurrentItem()];
            }
        });

        city.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updatecCities(ccity, ccities, country.getCurrentItem(),
                        newValue);
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                cityTxt2 = AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()];
            }
        });

        ccity.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                cityTxt3 = AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
            }
        });

        country.setCurrentItem(1);// 设置北京
        city.setCurrentItem(1);
        ccity.setCurrentItem(1);
        return contentView;
    }

    /**
     * Updates the city wheel
     */
    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                cities[index]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    /**
     * Updates the ccity wheel
     */
    private void updatecCities(WheelView city, String ccities[][][], int index,
                               int index2) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                ccities[index][index2]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }


    // 获取当前程序的版本信息
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            //				versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {

            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    // 程序版本更新
    private void dialog() {

        AlertDialog.Builder builder = new Builder(PersonCenterActivity.this);
        builder.setMessage(content);
        System.out.println("content==============" + content);
        //			builder.setMessage("检查到最新版本，是否要更新！");
        builder.setTitle("提示:新版本");
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (UpdateApkThread.IsLoading()) {
                    Toast.makeText(PersonCenterActivity.this, "正在下载...", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    AndPermission.with(PersonCenterActivity.this)
                            .permission(Permission.Group.STORAGE)
                            .onGranted(new Action() {
                                @Override
                                public void onAction(List<String> permissions) {
                                    String filePath = Environment.getExternalStorageDirectory() + "/ss";
                                    new UpdateApkThread(URL, filePath, "zams.apk", PersonCenterActivity.this).start();
                                }
                            })
                            .onDenied(new Action() {
                                @Override
                                public void onAction(List<String> permissions) {
                                    new PermissionSetting(PersonCenterActivity.this).showSettingStorage(permissions);
                                }
                            }).start();
                }
            }
        });
        builder.setNegativeButton("以后再说",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    /**
     * 修改地区
     */
    private void loadusersex() {
        try {
            AsyncHttp.get(URLConstants.REALM_ACCOUNT_URL
                            + "/user_info_edit?user_id=" + user_id + "&user_name=" + mUserName + "" +
                            "&nick_name=" + nick_name + "&mobile=" + mobile + "&sex=" + sex + "&birthday=" + mBirthday + "&email=" + data.email +
                            "&telphone=string&qq=string&msn=string&province=" + cityTxt1 + "&city=" + cityTxt2 + "&area=" + cityTxt3 + "&address=string&sign=" + login_sign + "",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("2=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    userloginqm();
                                } else {
                                    progress.CloseProgress();
                                    Toast.makeText(PersonCenterActivity.this, info, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserIconEvent(PullImageEvent event) {
        if (event.getEvenId() == EventConstants.USER_ICON) {
            final String imgUrl = event.getImgUrl();
            HttpProxy.putUserIcon(this, imgUrl, new HttpCallBack<String>() {
                @Override
                public void onSuccess(String responseData) {
                    SharedPreferences sp = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString(SpConstants.USER_IMG, imgUrl);
                    edit.commit();
                    EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                }

                @Override
                public void onError(Request request, Exception e) {
                    ToastUtils.makeTextShort("上传失败");
                }
            });
            Glide.with(this)
                    .load(URLConstants.REALM_URL + imgUrl)
                    .placeholder(R.mipmap.default_img)
                    .transform(new GlideCircleTransform(this))
                    .into(networkImage);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
