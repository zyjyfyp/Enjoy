package com.yunsen.enjoy.activity.user;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.hengyushop.dao.CityDao;
import com.hengyushop.dao.WareDao;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.Webview1;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.CityData;
import com.yunsen.enjoy.model.Register_Va;
import com.yunsen.enjoy.model.UserRegisterData;
import com.yunsen.enjoy.utils.Validator;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.MyPopupWindowMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRegisterActivity extends AppCompatActivity implements
        OnClickListener {

	private Button img_title_login;
	private EditText userpwd, userphone, et_user_yz;
	// userpostbox, userpwd, userpwdagain,
	private Button btn_register, get_yz;
	private String name, phone, postbox, pwd, pwdagain, insertdata, yz,
			shoujihao;
	private UserRegisterData data;
	private WareDao wareDao;
	private int isEnable = 1;
	private int isLogin = 0;
	private String str, hengyuName;
	private DialogProgress progress;
	private String strUrl;
	private MyPopupWindowMenu popupWindowMenu;
	private TextView regise_tip;
	private RelativeLayout lay;
	private ArrayAdapter aa_sheng, aa_shi, aa_area;
	private String yanzhengma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhuce);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.title_register);
		popupWindowMenu = new MyPopupWindowMenu(this);
		wareDao = new WareDao(getApplicationContext());
		initdata();

		System.out.println(Environment.getDataDirectory());
		System.out.println(Environment.getDataDirectory().getPath());
		File[] f = Environment.getDataDirectory().listRoots();
		for (int i = 0; i < f.length; i++) {
			System.out.println(f[i].getPath());
		}
		System.out.println(Environment.getDataDirectory());
		System.out.println(Environment.getDownloadCacheDirectory().getPath());
		System.out.println(Environment.getExternalStorageDirectory().getPath());
		System.out.println(Environment.getRootDirectory().getPath());

		//		ImageView img_menu = (ImageView) findViewById(R.id.img_menu);
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
					// et_user_yz.setText("");
					// username.setText("");
					// userphone.setText("");
					// userpostbox.setText("");
					// userpwd.setText("");
					// userpwdagain.setText("");
					String strhengyuname = (String) msg.obj;
					// dialog(strhengyuname);
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
							.makeText(getApplicationContext(), strmsg,  Toast.LENGTH_SHORT)
							.show();
					break;
				case 2:
					Toast.makeText(getApplicationContext(), "验证码已发送",
							Toast.LENGTH_SHORT).show();
					new Thread() {
						public void run() {
							for (int i = 120; i >= 0; i--) {
								if (i == Toast.LENGTH_SHORT) {
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

					Toast.makeText(getApplicationContext(), "验证码已发送",
							Toast.LENGTH_SHORT).show();
					break;
				case 4:
					get_yz.setEnabled(true);
					get_yz.setText("获取验证码");
					break;
				case 5:
					get_yz.setEnabled(false);
					get_yz.setText(msg.arg1 + "s");
					break;
				// case 6:
				// al_sheng = (ArrayList<String>) msg.obj;
				// aa_sheng = new ArrayAdapter(UserRegisterActivity.this,
				// android.R.layout.simple_spinner_item, al_sheng);
				// aa_sheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// sp_sheng.setAdapter(aa_sheng);
				// break;
				// case 7:
				// al_shi = (ArrayList<String>) msg.obj;
				// aa_shi = new ArrayAdapter(UserRegisterActivity.this,
				// android.R.layout.simple_spinner_item, al_shi);
				// aa_shi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// sp_shi.setAdapter(aa_shi);
				// break;
				// case 8:
				// al_xian = (ArrayList<String>) msg.obj;
				// aa_area = new ArrayAdapter(UserRegisterActivity.this,
				// android.R.layout.simple_spinner_item, al_xian);
				// aa_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// sp_xian.setAdapter(aa_area);
				// break;
				default:
					break;
			}
		};

	};
	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;

	// private Spinner sp_sheng;
	// private Spinner sp_shi;
	// private Spinner sp_xian;
	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.register_tip, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.ban_louming));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		final TextView lglottery_pop_closed = (TextView) popView
				.findViewById(R.id.register_tip);
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "GetwxzRegForIOS");
		params.put("yth", "test");

		AsyncHttp.post_1(URLConstants.REALM_URL + "/mi/getdata.ashx", params,
				new AsyncHttpResponseHandler() {
					// AsyncHttp.post_1(RealmName.REALM_NAME_LL+"/user_invite_code",
					// params,new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							lglottery_pop_closed.setText(object
									.getString("msg"));
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}
				});
	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			int[] location = new int[2];
			view.getLocationOnScreen(location);
//			mPopupWindow.showAsDropDown(lay);
		}
	}

	public float[] bubbleSort(float[] args) {// 冒泡排序算法
		for (int i = 0; i < args.length - 1; i++) {
			for (int j = i + 1; j < args.length; j++) {
				if (args[i] < args[j]) {
					float temp = args[i];
					args[i] = args[j];
					args[j] = temp;
				}
			}
		}
		return args;
	}

	private void initdata() {
		try {
			LinearLayout ll_buju = (LinearLayout) findViewById(R.id.ll_buju);
			//			ll_buju.setBackgroundResource(R.drawable.denglu_beijing);
			// sp_sheng = (Spinner) findViewById(R.id.sp_sheng);
			// sp_shi = (Spinner) findViewById(R.id.sp_shi);
			// sp_xian = (Spinner) findViewById(R.id.sp_xian);

			cityDao = new CityDao(UserRegisterActivity.this);
			ArrayList<CityData> shengs = cityDao.findSheng();
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < shengs.size(); i++) {
				list.add(shengs.get(i).name);
			}
			Message message = new Message();
			message.what = 6;
			message.obj = list;
			handler.sendMessage(message);

			// spinnerData();
//			lay = (RelativeLayout) findViewById(R.id.lay);
			regise_tip = (TextView) findViewById(R.id.regise_tip);
			et_user_yz = (EditText) findViewById(R.id.et_user_yz);
			get_yz = (Button) findViewById(R.id.get_yz);
			// img_title_login = (Button) findViewById(R.id.img_title_login);
			// username = (EditText) findViewById(R.id.et_user_name);
			userphone = (EditText) findViewById(R.id.et_user_phone);
			// userpostbox = (EditText) findViewById(R.id.et_user_postbox);
			userpwd = (EditText) findViewById(R.id.et_user_pwd);
			// userpwdagain = (EditText) findViewById(R.id.et_user_pwd_again);
			btn_register = (Button) findViewById(R.id.btn_register);
			// img_title_login.setOnClickListener(this);
			btn_register.setOnClickListener(this);
			get_yz.setOnClickListener(this);
			// regise_tip.setText(Html.fromHtml(" <u>用户协议</u> "));
			regise_tip.setOnClickListener(this);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private ArrayList<String> al_sheng, al_shi, al_xian;
	private String sheng, shi, xian, yth, key;
	private int sheng_code, shi_code, area_code, index;
	private CityDao cityDao;

	// private void spinnerData() {
	// sp_sheng.setOnItemSelectedListener(new OnItemSelectedListener() {
	//
	//
	//
	// @Override
	// public void onItemSelected(AdapterView<?> arg0, View arg1,
	// int arg2, long arg3) {
	//
	// sheng = al_sheng.get(arg2);
	// cityDao = new CityDao(UserRegisterActivity.this);
	// CityData cityData = cityDao.findShengCode(sheng);
	// sheng_code = cityData.getCode();
	// Log.v("data2", cityData.getCode() + "");
	// cityDao = new CityDao(UserRegisterActivity.this);
	// ArrayList<CityData> shis = cityDao.findCity(sheng_code);
	// ArrayList<String> list2 = new ArrayList<String>();
	// for (int i = 0; i < shis.size(); i++) {
	// list2.add(shis.get(i).name);
	// }
	// Message message = new Message();
	// message.what = 7;
	// message.obj = list2;
	// handler.sendMessage(message);
	//
	// }
	//
	// @Override
	// public void onNothingSelected(AdapterView<?> arg0) {
	//
	// }
	// });
	//
	// sp_shi.setOnItemSelectedListener(new OnItemSelectedListener() {
	//
	// @Override
	// public void onItemSelected(AdapterView<?> arg0, View arg1,
	// int arg2, long arg3) {
	//
	// shi = al_shi.get(arg2);
	// cityDao = new CityDao(UserRegisterActivity.this);
	// CityData cityData = cityDao.findCityCode(shi);
	// shi_code = cityData.getCode();
	// cityDao = new CityDao(UserRegisterActivity.this);
	// ArrayList<CityData> areas = cityDao.findArea(shi_code);
	// ArrayList<String> list3 = new ArrayList<String>();
	// for (int i = 0; i < areas.size(); i++) {
	// list3.add(areas.get(i).name);
	// }
	// Message message = new Message();
	// message.what = 8;
	// message.obj = list3;
	// handler.sendMessage(message);
	// }
	//
	// @Override
	// public void onNothingSelected(AdapterView<?> arg0) {
	//
	// }
	// });
	// sp_xian.setOnItemSelectedListener(new OnItemSelectedListener() {
	//
	// @Override
	// public void onItemSelected(AdapterView<?> arg0, View arg1,
	// int arg2, long arg3) {
	//
	// xian = al_xian.get(arg2);
	// cityDao = new CityDao(UserRegisterActivity.this);
	// CityData cityData = cityDao.findAreaCode(xian);
	// area_code = cityData.getCode();
	// }
	//
	// @Override
	// public void onNothingSelected(AdapterView<?> arg0) {
	//
	// }
	// });
	//
	// }

	// 字符串上传服务器 乱码 问题的解决方法
	private String processParam(String temp)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(temp, "UTF-8");
	}

	@Override
	public void onClick(View v) {
		try {


			switch (v.getId()) {
				case R.id.regise_tip:
					// initPopupWindow();
					// showPopupWindow(regise_tip);

					Intent intent4 = new Intent(UserRegisterActivity.this,
							Webview1.class);
					intent4.putExtra("zhuce_id", "5997");
					startActivity(intent4);
					break;
				case R.id.get_yz:
					phone = userphone.getText().toString().trim();
					if (phone.equals("")) {
						Toast.makeText(UserRegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT)
								.show();
					}
					// else
					// if (phone.length() < 11 ) {
					// Toast.makeText(UserRegisterActivity.this, "手机号少于11位",
					// Toast.LENGTH_SHORT).show();
					// }
					else {
						if (Validator.isMobile(phone)) {
							// if (phone != null && phone.length() == 11) {
							strUrl = URLConstants.REALM_NAME_LL
									+ "/user_verify_smscode?mobile=" + phone + "";

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
											// Toast.makeText(UserRegisterActivity.this,
											// info, Toast.LENGTH_SHORT).show();
											yanzhengma = object.getString("data");
											handler.sendEmptyMessage(2);
										} else {
											Toast.makeText(
													UserRegisterActivity.this,
													info, Toast.LENGTH_SHORT).show();
											// handler.sendEmptyMessage(3);
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}, getApplicationContext());

						} else {
							// Toast.makeText(getApplicationContext(),
							// "请输入手机号码",false,0).show();
							Toast.makeText(UserRegisterActivity.this, "手机号码不正确",
									Toast.LENGTH_SHORT).show();
						}
						// } else {
						// // Toast.makeText(getApplicationContext(),
						// "请输入手机号码",false,0).show();
						// Toast.makeText(UserRegisterActivity.this, "手机号码不能为空",
						// Toast.LENGTH_SHORT).show();
						// }
					}

					break;
//				case R.id.img_title_login:
//					int index = 0;
//					Intent intent = new Intent(UserRegisterActivity.this,
//							UserLoginActivity.class);
//					intent.putExtra("login", index);
//					startActivity(intent);
//					finish();
//					break;
				case R.id.btn_register:
					yz = et_user_yz.getText().toString().trim();
					// name = username.getText().toString().trim();
					phone = userphone.getText().toString().trim();
					// postbox = userpostbox.getText().toString().trim();
					pwd = userpwd.getText().toString().trim();
					// pwdagain = userpwdagain.getText().toString().trim();
					// SimpleDateFormat formatter = new SimpleDateFormat(
					// "yyyy年MM月dd日   HH:mm:ss");
					// Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
					// insertdata = formatter.format(curDate);

					if (phone.equals("")) {
						Toast.makeText(UserRegisterActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT)
								.show();
					} else if (phone.length() < 11) {
						Toast.makeText(UserRegisterActivity.this, "手机号码少于11位", Toast.LENGTH_SHORT)
								.show();
					} else if (yz.equals("")) {
						Toast.makeText(UserRegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT)
								.show();
					}
					// else if (yz.length() < 6 ) {
					// Toast.makeText(UserRegisterActivity.this, "验证码少于六位",
					// Toast.LENGTH_SHORT).show();
					// }
					else if (pwd.equals("")) {
						Toast.makeText(UserRegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT)
								.show();
					} else if (pwd.length() < 8) {
						Toast.makeText(UserRegisterActivity.this, "密码不得小于8位", Toast.LENGTH_SHORT)
								.show();
					} else if (!(userpwd.getText().toString().length() < 20 && userpwd
							.getText().toString().length() >= 8)) {
						Toast.makeText(UserRegisterActivity.this, "密码在8-20位之间", Toast.LENGTH_SHORT)
								.show();
					} else {
						try {
							progress = new DialogProgress(UserRegisterActivity.this);
							progress.CreateProgress();
							new Thread() {
								public void run() {
									try {
										String sn = "";

									/*
									 * System.out.println(Environment.
									 * getExternalStorageDirectory());
									 * searchFile(".sntxt",
									 * Environment.getExternalStorageDirectory
									 * ()); //float i=141533847400f;
									 * if(bookList!=null&&bookList.size()!=0){
									 *
									 * float time[] = new
									 * float[bookList.size()]; for(int
									 * i=0;i<time.length;i++){
									 *
									 *
									 * time[i] = bookList.get(i).getTime();
									 * System.out.println(time[i]+"---"+i); }
									 * for(int j=0;j<time.length;j++){
									 * bubbleSort(time); }
									 *
									 * for(int k=0;k<bookList.size();k++){
									 * System
									 * .out.println(k+"------"+bookList.get
									 * (k).getPath());
									 * if(bookList.get(k).getTime()==time[0]){
									 * System
									 * .out.println("最大的地址"+bookList.get(k)
									 * .getPath()); File file = new
									 * File(bookList.get(k).getPath());
									 * HttpUtils utils = new HttpUtils();
									 * FileInputStream in = new
									 * FileInputStream(file);
									 * sn=utils.convertStreamToString(in,
									 * "UTF-8"); } } }
									 */

										// strUrl = URLConstants.REALM_NAME
										// + "/mi/registerUser2."
										// + "ashx?act=UserBasicRegister&username="
										// + processParam(name) + "&pwd=" + pwd
										// + "&phone=" + phone + "&email"
										// + postbox + "&regValidatecode=" +
										// yz+"&RequestType=1&addressProvinceCode="+sheng_code+"&addressCityCode="+shi_code+"&addressAreaCode="+area_code+"&sn="+sn;
										// System.out.println("注册" + strUrl);

										// et_user_yz.setText("");
										// username.setText("");
										// userphone.setText("");
										// userpostbox.setText("");
										// userpwd.setText("");
										// userpwdagain.setText("");
										// System.out.println("=================11=="
										// + phone);
										// System.out.println("=================12=="
										// + pwd);
										// System.out.println("=================13=="
										// + postbox);
										// System.out.println("=================14=="
										// + shoujihao);//"+123488+"

										strUrl = URLConstants.REALM_NAME_LL
												+ "/user_register?site=mobile&code="
												+ yz + "&username=" + phone
												+ "&password=" + pwd + "&mobile="
												+ phone + "";
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
															System.out
																	.println("=================1=="
																			+ arg1);
															String status = jsonObject
																	.getString("status");
															String info = jsonObject
																	.getString("info");
															if (status.equals("n")) {
																System.out
																		.println("=================2==");
																str = jsonObject
																		.getString("info");
																// String no =
																// jsonObject.getString("info");
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
																			.println("=================3=="
																					+ info);
																	hengyuName = jsonObject
																			.getString("info");
																	// Toast.makeText(getApplicationContext(),
																	// info,false,
																	// 0).show();
																	SharedPreferences spPreferences = getSharedPreferences(
																			"longuserset_user",
																			MODE_PRIVATE);
																	Editor editor = spPreferences
																			.edit();
																	editor.putBoolean(
																			"save",
																			true);
																	editor.putString(
																			"user_name",
																			userphone
																					.getText()
																					.toString());
																	editor.putString(
																			"pwd",
																			userpwd.getText()
																					.toString());
																	editor.commit();

																	Log.v("data1",
																			hengyuName
																					+ "");
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
																	// finish();
																} catch (Exception e) {
																	e.printStackTrace();
																}
															}
														} catch (JSONException e) {

															// catch block
															e.printStackTrace();
														}
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

	/*
	 * searchFile 查找文件并加入到ArrayList 当中去
	 *
	 * @String keyword 查找的关键词
	 *
	 * @File filepath 查找的目录
	 */
	ArrayList<Register_Va> bookList = new ArrayList<Register_Va>();;

	private void searchFile(String keyword, File filepath) {

		// 判断SD卡是否存在
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File[] files = filepath.listFiles();

			if (files.length > 0) {
				for (File file : files) {
					if (file.isDirectory()) {
						// 如果目录可读就执行（一定要加，不然会挂掉）
						if (file.canRead()) {
							searchFile(keyword, file); // 如果是目录，递归查找
						}
					} else {
						// 判断是文件，则进行文件名判断
						try {
							if (file.getName().indexOf(keyword) > -1
									|| file.getName().indexOf(
									keyword.toUpperCase()) > -1) {
								Register_Va va = new Register_Va();
								/*
								 * HashMap<> rowItem = new HashMap<String,
								 * Object>(); rowItem.put("number", index); //
								 * 加入序列号 rowItem.put("bookName",
								 * file.getName());// 加入名称 rowItem.put("path",
								 * file.getPath()); // 加入路径 rowItem.put("size",
								 * file.length()); // 加入文件大小 rowItem.put("time",
								 * file.lastModified()+"f");
								 */
								System.out.println(file.getName() + "-"
										+ file.getPath() + "-"
										+ file.lastModified());
								va.setPath(file.getPath());
								va.setTime(Float.parseFloat(file.lastModified()
										+ "f"));
								bookList.add(va);
								index++;
							}
						} catch (Exception e) {
							Toast.makeText(this, "查找发生错误", Toast.LENGTH_SHORT)
									.show();
						}
					}
				}
			}
		}
	}

	protected void dialog(String hengyucode) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("请牢记云盟号:" + hengyucode);
		builder.setTitle("提示:注册成功");
		builder.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // 对话框消失
			popupWindowMenu.currentState = 1; // 标记状态，已消失
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}

}
