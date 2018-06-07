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
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.GlideCircleTransform;
import com.yunsen.enjoy.widget.MyAlertDialog;

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
        ll_update.setOnClickListener(this);
        rl_nichen.setOnClickListener(this);
        ll_gender.setOnClickListener(this);
        ll_diqu.setOnClickListener(this);
        mm.setOnClickListener(this);
        networkImage.setOnClickListener(this);

        String version = getAppVersionName(PersonCenterActivity.this);
        System.out.println("c_version==============" + version);
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

                            try {
                                if (!data.vip_card.equals("")) {
                                    tv_ka_name.setText("服务金卡");
                                    v2.setText(data.vip_card);
                                } else {
                                    tv_ka_name.setText("会员号");
                                    v2.setText(data.user_code);
                                }

                                String nickname = mSp.getString(SpConstants.NICK_NAME, "");
                                System.out.println("=============nickname======" + nickname);
                                if (!nickname.equals("")) {
                                    tv_nicheng.setText(nickname);
                                    tv_nicheng.setTextColor(getResources().getColor(R.color.black));
                                } else {
                                    if (nick_name.equals("")) {
                                        tv_nicheng.setText("请输入您的昵称");
                                        tv_nicheng.setTextColor(getResources().getColor(R.color.gray));
                                    } else {
                                        tv_nicheng.setText(nick_name);
                                        tv_nicheng.setTextColor(getResources().getColor(R.color.black));
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

                                if (data.province.equals("")) {

                                } else {
                                    tv_city.setText(data.province + "、" + data.city + "、" + data.area);
                                }

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
//                                if (SpConstants.OK.equals(data.avatar)) {
//                                    GetImgUtil.loadLocationImg(PersonCenterActivity.this, networkImage);
//                                } else {
//                                    ToastUtils.makeTextShort("需要默认图片");
//                                }


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
                                ll_shenji.setVisibility(View.VISIBLE);
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
                            System.out.println("content==============" + content);
                            System.out.println("服务器:" + server_version + "/当前:" + client_version);
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
            default:
                break;
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
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        //		System.out.println("裁剪图片方法实现================"+tempUri);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    public void saveBitmapFile(Bitmap bitmap) {
        File file = new File("/mnt/sdcard/pic/01.jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
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
            //			photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            System.out.println("图片的值1=================" + photo);
            System.out.println("图片的值2=================" + tempUri);
            networkImage.setImageBitmap(photo);
            try {
                imagePath = Utils.savePhoto(photo, Environment.getExternalStorageDirectory().getAbsolutePath(),
                        String.valueOf(System.currentTimeMillis()));
                System.out.println("imagePath=======================" + imagePath);

                new Thread() {
                    public void run() {
                        try {
                            FTPClient client = new FTPClient();
                            //							client.connect("183.62.138.31", 2021);
                            //							client.login("zams", "yunsen1230.");
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


    private LayoutInflater mLayoutInflater;
    private View popView;
    private PopupWindow mPopupWindow;

//    private void initPopupWindow() {
//        mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        popView = mLayoutInflater.inflate(R.layout.chose_payment, null);
//        mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
//                LayoutParams.WRAP_CONTENT);
//        // mPopupWindow.setBackgroundDrawable(new
//        // BitmapDrawable());//必须设置background才能消失
//        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
//                R.color.grey));
//        mPopupWindow.setOutsideTouchable(true);
//        // 自定义动画
//        // mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
//        // 使用系统动画
//        mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
//        mPopupWindow.update();
//        mPopupWindow.setTouchable(true);
//        mPopupWindow.setFocusable(true);
//        bank_item = (WheelViewll) popView.findViewById(R.id.bank_item);
//        Button cancle = (Button) popView.findViewById(R.id.cancle);
//        Button sure = (Button) popView.findViewById(R.id.sure);
//        String[] name = new String[2];
//        name[0] = "登录密码";
//        name[1] = "支付密码";
//        ArrayWheelAdapterll<String> bankAdapter = new ArrayWheelAdapterll<String>(
//                name);
//        bank_item.setAdapter(bankAdapter);
//
//        cancle.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//
//
//                dissPop();
//            }
//        });
//        sure.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                int index = bank_item.getCurrentItem();
//                Intent intent = new Intent(PersonCenterActivity.this,
//                        ModPassActivity.class);
//                intent.putExtra("tag", index);
//                startActivity(intent);
//
//                dissPop();
//            }
//        });
//    }
//
//    private void showPopupWindow(View view) {
//        if (!mPopupWindow.isShowing()) {
//            // mPopupWindow.showAsDropDown(view,0,0);
//            // 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
//            // 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
//            // int[] location = new int[2];
//            // view.getLocationOnScreen(location);
//            mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//        }
//    }

    private void dissPop() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
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

    public static File getFileFromServer(String path, ProgressDialog pd)
            throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            java.net.URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(),
                    "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk() {
        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(PersonCenterActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCanceledOnTouchOutside(true);
        pd.setProgressNumberFormat(null);
        pd.setMessage("正在下载更新");
        zhuangtai = true;
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(URL, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); // 结束掉进度条对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 安装apk
    protected void installApk(File file) {
//        MainFragment.zhuangtai = false;
//        UserLoginActivity.zhuangtai = false;
        PersonCenterActivity.zhuangtai = false;
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
        PersonCenterActivity.this.startActivity(intent);
    }

    /**
     * 修改地区
     */
    private void loadusersex() {
        try {
            AsyncHttp.get(URLConstants.REALM_ACCOUNT_URL
                            + "/user_info_edit?user_id=" + user_id + "&user_name=" + mUserName + "" +
                            "&nick_name=" + nick_name + "&mobile=" + mobile + "&sex=" + sex + "&birthday=string&email=string" +
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
