package com.yunsen.enjoy.activity.user;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.utils.Validator;
import com.yunsen.enjoy.widget.DialogProgress;


import org.json.JSONException;
import org.json.JSONObject;

public class MobilePhoneActivity extends AppCompatActivity implements
        OnClickListener {

	private EditText userpwd, userphone, et_user_yz;
	private Button btn_register, get_yz;
	private String name, phone, postbox, pwd, pwdagain, insertdata, yz,
			shoujihao;
	private String str, hengyuName;
	private DialogProgress progress;
	private String strUrl;
	private TextView regise_tip;
	private String yanzhengma;
	private LinearLayout ll_zhuchexieyi;
	private SharedPreferences spPreferences;
	String headimgurl2 = "";
	public static String oauth_name = "";
	String user_name, user_id, headimgurl, access_token, sex, unionid;
	String province = "";
	String city = "";
	String country = "";
	private SharedPreferences spPreferences_weixin;
	private SharedPreferences spPreferences_qq;
	private SharedPreferences spPreferences_login;
	String nickname = "";
	String user_name_weixin = "";
	String user_name_qq = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mobile_phone);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initdata();

		TextView img_menu = (TextView) findViewById(R.id.iv_fanhui);
		img_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});

	}

	Handler handler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {

			switch (msg.what) {
				case 0:
					String strhengyuname = (String) msg.obj;
					Toast.makeText(getApplicationContext(), strhengyuname,
							 Toast.LENGTH_SHORT).show();
					progress.CloseProgress();
					// Intent intent = new Intent(UserRegisterActivity.this,
					// UserLoginActivity.class);
					// startActivity(intent);
					finish();
					break;
				case 1:
					String strmsg = (String) msg.obj;
					Toast
							.makeText(getApplicationContext(), strmsg, Toast.LENGTH_SHORT)
							.show();
					break;
				case 2:
					Toast.makeText(getApplicationContext(), "验证码已发送",Toast.LENGTH_SHORT).show();
					new Thread() {
						public void run() {
							for (int i = 120; i >= 0; i--) {
								if (i == 0) {
									handler.sendEmptyMessage(4);
								} else {
									Message msg = new Message();
									msg.arg1 = i;
									msg.what = 5;
									handler.sendMessage(msg);

									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {

										e.printStackTrace();
									}

								}
							}
						};
					}.start();
					break;
				case 3:

					Toast.makeText(getApplicationContext(), "验证码已下发", Toast.LENGTH_SHORT).show();
					break;
				case 4:
					get_yz.setEnabled(true);
					get_yz.setText("获取验证码");
					break;
				case 5:
					get_yz.setEnabled(false);
					get_yz.setText(msg.arg1 + "s");
					break;
				default:
					break;
			}
		};

	};

	private void initdata() {
		try {
			et_user_yz = (EditText) findViewById(R.id.et_user_yz);
			userpwd = (EditText) findViewById(R.id.et_user_pwd);
			get_yz = (Button) findViewById(R.id.get_yz);
			userphone = (EditText) findViewById(R.id.et_user_phone);
			btn_register = (Button) findViewById(R.id.btn_register);
			btn_register.setOnClickListener(this);
			get_yz.setOnClickListener(this);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		try {


			switch (v.getId()) {
				case R.id.get_yz:
					phone = userphone.getText().toString().trim();
					if (phone.equals("")) {
						Toast.makeText(MobilePhoneActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT)
								.show();
					} else if (phone.length() < 11) {
						Toast.makeText(MobilePhoneActivity.this, "手机号码少于11位", Toast.LENGTH_SHORT)
								.show();
					} else {
						// phone = userphone.getText().toString().trim();
						if (Validator.isMobile(phone)) {
							strUrl = URLConstants.REALM_NAME_LL
									+ "/user_oauth_smscode?mobile=" + phone + "";
							AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {
									super.onSuccess(arg0, arg1);
									System.out.println("=============" + arg1);
									try {
										JSONObject object = new JSONObject(arg1);
										String result = object.getString("status");//
										String info = object.getString("info");// info
										if (result.equals("y")) {
											yanzhengma = object.getString("data");
											handler.sendEmptyMessage(2);
										} else {
											Toast.makeText(
													MobilePhoneActivity.this, info,
													Toast.LENGTH_SHORT).show();
											// handler.sendEmptyMessage(3);
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}, getApplicationContext());
						} else {
							Toast.makeText(MobilePhoneActivity.this, "手机号码不正确", Toast.LENGTH_SHORT)
									.show();
						}
					}

					break;
//				case R.id.img_title_login:
//					int index = 0;
//					Intent intent = new Intent(MobilePhoneActivity.this,
//							UserLoginActivity.class);
//					intent.putExtra("login", index);
//					startActivity(intent);
//					finish();
//					break;
				case R.id.btn_register:
					yz = et_user_yz.getText().toString().trim();
					phone = userphone.getText().toString().trim();
					pwd = userpwd.getText().toString().trim();
					System.out.println("1==================" + yz);
					System.out.println("2==================" + yanzhengma);
					if (phone.equals("")) {
						Toast.makeText(MobilePhoneActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT)
								.show();
					} else if (phone.length() < 11) {
						Toast.makeText(MobilePhoneActivity.this, "手机号码少于11位", Toast.LENGTH_SHORT)
								.show();
					} else if (yz.equals("")) {
						Toast.makeText(MobilePhoneActivity.this, "验证码不能为空", Toast.LENGTH_SHORT)
								.show();
					} else if (pwd.equals("")) {
						Toast.makeText(MobilePhoneActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
								.show();
					} else {
						try {
							progress = new DialogProgress(MobilePhoneActivity.this);
							progress.CreateProgress();
							new Thread() {
								public void run() {
									try {

										SharedPreferences spPreferences_tishi = getSharedPreferences(
												"longuserset_tishi", MODE_PRIVATE);
										spPreferences_tishi.edit().clear().commit();
										String weixin = spPreferences_tishi
												.getString("weixin", "");
										String qq = spPreferences_tishi.getString(
												"qq", "");
										System.out
												.println("=================weixin=="
														+ weixin);
										System.out.println("=================qq=="
												+ qq);

										spPreferences_login = getSharedPreferences(
												"longuserset_login", MODE_PRIVATE);
										headimgurl2 = spPreferences_login
												.getString("headimgurl2", "");
										nickname = spPreferences_login.getString(
												"nickname", "");
										unionid = spPreferences_login.getString(
												"unionid", "");
										access_token = spPreferences_login
												.getString("access_token", "");
										sex = spPreferences_login.getString("sex",
												"");
										String oauth_openid = spPreferences_login
												.getString("oauth_openid", "");

										// spPreferences_weixin =
										// getSharedPreferences("longuserset_weixin",
										// Context.MODE_PRIVATE);
										// user_name_weixin =
										// spPreferences_weixin.getString("nickname",
										// "");
										//
										// spPreferences_qq =
										// getSharedPreferences("longuserset_qq",
										// Context.MODE_PRIVATE);
										// user_name_qq =
										// spPreferences_qq.getString("nickname",
										// "");
										//
										// System.out.println("user_name_weixin================"+user_name_weixin);
										// System.out.println("user_name_qq================"+user_name_qq);
										//
										// if (!user_name_weixin.equals("")) {
										// nickname = user_name_weixin;
										// unionid =
										// spPreferences_weixin.getString("unionid",
										// "");
										// access_token =
										// spPreferences_weixin.getString("access_token",
										// "");
										// sex =
										// spPreferences_weixin.getString("sex",
										// "");
										// }else {
										// nickname = user_name_qq;
										// headimgurl2 =
										// spPreferences_qq.getString("headimgurl2",
										// "");
										// unionid =
										// spPreferences_qq.getString("unionid",
										// "");
										// access_token =
										// spPreferences_qq.getString("access_token",
										// "");
										// sex = spPreferences_qq.getString("sex",
										// "");
										// }

										province = getIntent().getStringExtra(
												"province");
										city = getIntent().getStringExtra("city");
										country = getIntent()
												.getStringExtra("area");

										if (province == null) {
											province = "";
										}
										if (city == null) {
											city = "";
										}
										if (country == null) {
											country = "";
										}
										// country =
										// getIntent().getStringExtra("country");
										// System.out.println("headimgurl2================"+headimgurl2);
										if (!headimgurl2.equals("")) {
											Bitmap bitmap = Utils
													.stringtoBitmap(headimgurl2);
											headimgurl = Utils
													.savePhoto(
															bitmap,
															Environment
																	.getExternalStorageDirectory()
																	.getAbsolutePath(),
															String.valueOf(System
																	.currentTimeMillis()));
											// headimgurl = headimgurl2;
										} else {
											// headimgurl =
											// getIntent().getStringExtra("headimgurl");
											headimgurl = spPreferences_login
													.getString("headimgurl", "");
										}

										if (sex.equals("1")) {
											sex = "男";
										} else {
											sex = "女";
										}

										SharedPreferences spPreferences_ptye = getSharedPreferences(
												"longuserset_ptye", MODE_PRIVATE);
										oauth_name = spPreferences_ptye.getString(
												"ptye", "");
										// if
										// (UserLoginActivity.oauth_name.equals("weixin"))
										// {
										// oauth_name = "weixin";
										// UserLoginActivity.oauth_name = "";
										// }else if
										// (UserLoginWayActivity.oauth_name.equals("qq"))
										// {
										// oauth_name = "qq";
										// UserLoginWayActivity.oauth_name = "";
										// }

										System.out
												.println("=================oauth_name=="
														+ oauth_name);

										SharedPreferences spPreferences = getSharedPreferences(
												"longuserset_tishi", MODE_PRIVATE);

										if (oauth_name.equals("weixin")) {
											Editor editor = spPreferences.edit();
											editor.putString("weixin", oauth_name);
											editor.commit();
										} else if (oauth_name.equals("qq")) {
											Editor editor = spPreferences.edit();
											editor.putString("qq", oauth_name);
											editor.commit();
										}

										// SharedPreference spPreferences_3_wx =
										// getSharedPreferences("longuserset_3_wx",
										// MODE_PRIVATE);
										// spPreferences_3_wx.edit().clear().commit();
										// SharedPreference spPreferences_qq =
										// getSharedPreferences("longuserset_3_qq",
										// MODE_PRIVATE);
										// spPreferences_qq.edit().clear().commit();

										// String strUrl = RealmName.REALM_NAME_LL +
										// "/user_oauth_bind_0215?mobile="+phone+"&code="+yz+"&nick_name="+nickname+"&sex="+sex+"&avatar="+headimgurl+""
										// +
										// "&province="+province+"&city="+city+"&country="+country+"&oauth_name="+oauth_name+"&oauth_access_token="+access_token+"&oauth_unionid="+unionid+"";

										String strUrl = URLConstants.REALM_NAME_LL
												+ "/user_oauth_bind_0217?mobile="
												+ phone + "&password=" + pwd
												+ "&code=" + yz + "&nick_name="
												+ nickname + "" + "&sex=" + sex
												+ "&avatar=" + headimgurl
												+ "&province=" + province
												+ "&city=" + city + "&country="
												+ country + "&oauth_name="
												+ oauth_name + ""
												+ "&oauth_unionid=" + unionid
												+ "&oauth_openid=" + oauth_openid
												+ "";
										System.out.println("注册" + strUrl);

										AsyncHttp.get(strUrl,
												new AsyncHttpResponseHandler() {
													@Override
													public void onSuccess(int arg0,
																		  String arg1) {

														// method stub
														super.onSuccess(arg0, arg1);
														try {
															JSONObject jsonObject = new JSONObject(
																	arg1);
															// System.out.println("=================arg1=="
															// + arg1);
															String status = jsonObject
																	.getString("status");
															String info = jsonObject
																	.getString("info");
															if (status.equals("n")) {
																System.out
																		.println("=================2==");
																str = jsonObject
																		.getString("info");
																String no = jsonObject
																		.getString("info");
																// // str =
																// jsonObject.getString("info");
																// Toast.makeText(getApplicationContext(),
																// no,false,
																// 0).show();
																progress.CloseProgress();
																Message message = new Message();
																message.what = 1;
																message.obj = str;
																handler.sendMessage(message);
															} else if (status
																	.equals("y")) {
																try {
																	System.out
																			.println("=================3==");
																	hengyuName = jsonObject
																			.getString("info");

																	// Toast.makeText(getApplicationContext(),
																	// info,false,
																	// 0).show();
																	JSONObject obj = jsonObject
																			.getJSONObject("data");
																	UserRegisterllData data = new UserRegisterllData();
																	data.id = obj
																			.getString("id");
																	data.user_name = obj
																			.getString("user_name");
																	data.user_code = obj
																			.getString("user_code");
																	data.agency_id = obj
																			.getInt("agency_id");
																	data.amount = obj
																			.getString("amount");
																	data.pension = obj
																			.getString("pension");
																	data.packet = obj
																			.getString("packet");
																	data.point = obj
																			.getString("point");
																	data.group_id = obj
																			.getString("group_id");
																	data.login_sign = obj
																			.getString("login_sign");
																	data.agency_name = obj
																			.getString("agency_name");
																	data.group_name = obj
																			.getString("group_name");
																	data.avatar = obj
																			.getString("avatar");
																	data.mobile = obj
																			.getString("mobile");
																	data.exp = obj
																			.getString("exp");
																	// String
																	// reg_site =
																	// obj.getString("reg_site");

																	SharedPreferences spPreferences_ptye = getSharedPreferences(
																			"longuserset_ptye",
																			MODE_PRIVATE);
																	spPreferences_ptye
																			.edit()
																			.clear()
																			.commit();

																	// if
																	// (oauth_name.equals("weixin"))
																	// {
																	// SharedPreference
																	// spPreferences
																	// =
																	// getSharedPreferences("longuserset_3_wx",
																	// MODE_PRIVATE);
																	// Editor editor
																	// =
																	// spPreferences.edit();
																	// editor.putString("user_wx",
																	// data.user_name);
																	// editor.putString("user_id",
																	// data.id);
																	// editor.commit();
																	// System.out.println("=================user_wx==");
																	// }else if
																	// (oauth_name.equals("qq")){
																	// SharedPreference
																	// spPreferences
																	// =
																	// getSharedPreferences("longuserset_3_qq",
																	// MODE_PRIVATE);
																	// Editor editor
																	// =
																	// spPreferences.edit();
																	// editor.putString("user_qq",
																	// data.user_name);
																	// editor.putString("user_id",
																	// data.id);
																	// editor.commit();
																	// System.out.println("=================user_qq==");
																	// }

																	SharedPreferences spPreferences = getSharedPreferences(
																			"longuserset",
																			MODE_PRIVATE);
																	Editor editor = spPreferences
																			.edit();
																	editor.putString(
																			"user",
																			data.user_name);
																	editor.putString(
																			"user_id",
																			data.id);
																	// editor.putString("user_code",
																	// data.user_code);
																	// editor.putString("exp",
																	// data.exp);
																	editor.commit();

																	progress.CloseProgress();
																	Message message = new Message();
																	message.what = 0;
																	message.obj = hengyuName;
																	handler.sendMessage(message);

																	// Intent intent
																	// = new
																	// Intent(UserRegisterActivity.this,
																	// HomeActivity.class);
																	// startActivity(intent);
																	finish();
																} catch (Exception e) {
																	e.printStackTrace();
																}
															}
														} catch (JSONException e) {

															// catch block
															e.printStackTrace();
														}
													}

													@Override
													public void onFailure(
															Throwable arg0,
															String arg1) {

														// method stub
														super.onFailure(arg0, arg1);
														System.out
																.println("=================arg0=="
																		+ arg0);
														System.out
																.println("=================arg1=="
																		+ arg1);
													}
												}, getApplicationContext());

									} catch (Exception e) {
										e.printStackTrace();
									}
								};
							}.start();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					break;

				default:
					break;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
