package com.yunsen.enjoy.activity.user;

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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.connect.auth.QQAuth;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.http.down.UpdateApkThread;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserLoginActivity extends AppCompatActivity implements OnClickListener {
	private Button btn_login;
	private DialogProgress progress;
	public static String kahao;
	//	private String user_name,user_id;
	private String nickname,headimgurl,access_token,sex,unionid,province,city,country,oauth_openid;
	private SharedPreferences spPreferences_weixin;
	private SharedPreferences spPreferences_login;
	public static boolean isWXLogin = false;
	public static IWXAPI mWxApi;
	public static String WX_CODE = "";
	public static String mAppid;
	public static QQAuth mQQAuth;
	public static Bitmap bitmap;
	public static String oauth_name = "";
	public static boolean panduan_tishi = false;
	public static boolean wx_fanhui = false;
	public static boolean panduan = false;
	public static Handler handler1;
	public static boolean zhuangtai = false;
	private String strUr2 = URLConstants.REALM_NAME_LL + "/get_apk_version?browser=android";
	private String URL;
	SharedPreferences spPreferences;
	SharedPreferences spPreferences_tishi;
	SharedPreferences longuserset_ptye;
	SharedPreferences spPreferences_qq;
	Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weixin_login);
		mWxApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
		mWxApi.registerApp(Constants.APP_ID);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		spPreferences_weixin = getSharedPreferences("longuserset_weixin", MODE_PRIVATE);
		spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
		try {
			progress = new DialogProgress(UserLoginActivity.this);
			//弹出框返回true关闭
			//			if (UserLoginWayActivity.jiemian == true) {
			//				UserLoginWayActivity.jiemian = false;
			//				finish();
			//			}

			initdata();

			handler1 = new Handler() {
				public void dispatchMessage(Message msg) {
					switch (msg.what) {
						case 0:
							break;
						case 1:
							finish();
							break;

						default:
							break;
					}
				}
			};
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {

		super.onResume();
		//			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		//			user_name = spPreferences.getString("user_name", "");

		//			SharedPreferences spPreferences_weixin = getSharedPreferences("longuserset_weixin", MODE_PRIVATE);
		//			spPreferences_weixin.edit().clear().commit();
		//			SharedPreferences spPreferences_qq = getSharedPreferences("longuserset_qq", MODE_PRIVATE);
		//			spPreferences_qq.edit().clear().commit();

		wx_fanhui = true;//分享微信返回APP

		if (zhuangtai == false) {
			updata();
		}

		if (isWXLogin) {
			panduan = true;
			panduan_tishi = true;
			oauth_name = "weixin";
			System.out.println("2------------------"+WX_CODE);
			//				Toast.makeText(this, "微信code为"+WX_CODE+"/", 1000).show();
			spPreferences_tishi = getSharedPreferences("longuserset_tishi", MODE_PRIVATE);
			String qq = spPreferences_tishi.getString("qq", "");
			if (!qq.equals("")) {
				spPreferences_tishi.edit().clear().commit();
				UserLoginWayActivity.panduan_tishi = false;
			}
			System.out.println("=================qq==" + qq);

			longuserset_ptye = getSharedPreferences("longuserset_ptye", MODE_PRIVATE);
			editor = longuserset_ptye.edit();
			editor.putString("ptye", "weixin");
			editor.commit();

			userxinxi();
		}else {
			//				onClickLogin();
			//				finish();
		}

	}

	public void userxinxi(){


		try{
			String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
					+ Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&code=" + WX_CODE +
					"&grant_type=authorization_code";
			//				Toast.makeText(MainUserLoginActivity.this, accessTokenUrl, 100000).show();
			System.out.println("======11============="+accessTokenUrl);
			AsyncHttp.get(accessTokenUrl, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					System.out.println("======输出1============="+arg1);
					//						Toast.makeText(MainUserLoginActivity.this, "数据为+"+arg1, 400).show();
					try {
						JSONObject object = new JSONObject(arg1);
						String access_token = object.getString("access_token");
						String openid = object.getString("openid");
						userxinxill(access_token,openid);
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};
			}, UserLoginActivity.this);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void userxinxill(String ACCESS_TOKEN, String openid){

		try{
			access_token = ACCESS_TOKEN;
			String accessTokenUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+ACCESS_TOKEN+"&openid="+openid+"";
			System.out.println("======22============="+accessTokenUrl);
			AsyncHttp.get(accessTokenUrl, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					System.out.println("======输出2============="+arg1);
					//						Toast.makeText(UserLoginActivity.this, "数据2为+"+arg1, 400).show();
					try {
						JSONObject object = new JSONObject(arg1);
						nickname = object.getString("nickname");
						headimgurl = object.getString("headimgurl");
						unionid = object.getString("unionid");
						sex = object.getString("sex");
						province = object.getString("province");
						city = object.getString("city");
						country = object.getString("country");
						oauth_openid = object.getString("openid");

						editor = spPreferences_login.edit();
						editor.putString("nickname", nickname);
						editor.putString("headimgurl", headimgurl);
						editor.putString("access_token", access_token);
						editor.putString("unionid", unionid);
						editor.putString("sex", sex);
						editor.putString("province", province);
						editor.putString("city", city);
						editor.putString("country", country);
						editor.putString("oauth_openid", oauth_openid);
						editor.commit();
						//							mBitmap = HttpUtil.getBitmapFromURL(headimgurl);
						//							mTextView.setText(nickname);
						//							mImageView.setImageBitmap(mBitmap);

					} catch (JSONException e) {

						e.printStackTrace();
					}

					spPreferences_qq = getSharedPreferences("longuserset_3_qq", MODE_PRIVATE);
					spPreferences_qq.edit().clear().commit();

					//						SharedPreferences spPreferences_tishi = getSharedPreferences("longuserset_tishi", MODE_PRIVATE);
					//						spPreferences_tishi.edit().clear().commit();

					isWXLogin = false;
					finish();
				};
			}, UserLoginActivity.this);

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
				case -1:
					progress.CloseProgress();
					break;
				case 10:

					break;
				case 1:
					String str = (String) msg.obj;
					Toast.makeText(UserLoginActivity.this, str, Toast.LENGTH_SHORT).show();
					break;
				case 7:

					break;
				default:
					break;
			}

		};

	};

	private void initdata() {
		btn_login = (Button) findViewById(R.id.btn_login);
		TextView tv_denglu = (TextView) findViewById(R.id.tv_denglu);
		TextView tv_qq_login = (TextView) findViewById(R.id.tv_qq_login);
		btn_login.setOnClickListener(this);
		tv_denglu.setOnClickListener(this);
		tv_qq_login.setOnClickListener(this);

		TextView img_menu = (TextView) findViewById(R.id.img_menu);
		img_menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				finish();
			}
		});
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_login://微信登录
				//			Intent intent = new Intent(UserLoginActivity.this,MainUserLoginActivity.class);
				//			startActivity(intent);
				isWXLogin = true;
				SendAuth.Req req = new SendAuth.Req();
				req.scope = "snsapi_userinfo";
				req.state = "wechat_sdk_demo";
				mWxApi.sendReq(req);
				break;
			case R.id.tv_qq_login://qq登录
				Intent intent = new Intent(UserLoginActivity.this,UserLoginWayActivity.class);
				startActivity(intent);
				break;
			case R.id.tv_denglu://
				try {
					System.out.println("0-------1----------");
					//			Intent intent3 = new Intent(UserLoginActivity.this,UserLoginWayActivity.class);
					Intent intent3 = new Intent(UserLoginActivity.this,PhoneLoginActivity.class);
					startActivity(intent3);
					System.out.println("0---------2--------");
				} catch (Exception e) {

					e.printStackTrace();
				}
				break;
			default:
				break;
		}
	}


	//	private class BaseUiListener implements IUiListener {
	//
	//		@Override
	//		public void onComplete(Object response) {
	//			System.out.println("response==============="+response);
	//			try {
	//				String access_token = ((JSONObject) response).getString("access_token");
	//				String openid = ((JSONObject) response).getString("openid");
	//				String ret = ((JSONObject) response).getString("ret");
	////				System.out.println("access_token==============="+access_token);
	//				Editor editor = spPreferences.edit();
	//				editor.putString("access_token", access_token);
	//				editor.putString("unionid", openid);
	//				editor.putString("sex", ret);
	//				editor.commit();
	//			} catch (JSONException e) {
	//
	//				e.printStackTrace();
	//			}
	////			Util.showResultDialog(UserLoginActivity.this, response.toString(),"登录成功");
	////
	//			doComplete((JSONObject) response);
	//		}
	//
	//		protected void doComplete(JSONObject values) {
	//
	//		}
	//
	//		@Override
	//		public void onError(UiError e) {
	//			Util.toastMessage(UserLoginActivity.this, "onError: " + e.errorDetail);
	//			Util.dismissDialog();
	//		}
	//
	//		@Override
	//		public void onCancel() {
	////			Util.toastMessage(UserLoginActivity.this, "用户取消");//onCancel:
	//			Util.dismissDialog();
	//		}
	//	}

	private void updata() {
		try {
			/**
			 * 版本2
			 */
			AsyncHttp.get(strUr2, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {

					super.onSuccess(arg0, arg1);
					System.out.println("首页版本=============="+arg1);
					try{
						JSONObject jsonObject = new JSONObject(arg1);
						JSONObject jsob = jsonObject.getJSONObject("data");
						String file_version = jsob.getString("file_version");
						String link_url = jsob.getString("link_url");
						String file_path = jsob.getString("file_path");
						String id = jsob.getString("id");
						URL = URLConstants.REALM_NAME_HTTP + file_path;
						System.out.println("首页版本URL=============="+URL);
						String c_version = getAppVersionName(getApplicationContext()).trim().replaceAll("\\.", "");
						float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//服务器
						float client_version = Float.parseFloat(c_version);//当前

						System.out.println("服务器:"+ server_version+"/当前:" + client_version);
						if (server_version > client_version) {
							//						Toast.makeText(UserLoginActivity.this, "提示更新", Toast.LENGTH_SHORT).show();
							Message message = new Message();
							message.what = 0;
							handler.sendMessage(message);
						}else {
							//					Toast.makeText(UserLoginActivity.this, "没有提示更新", Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, getApplicationContext());



		} catch (Exception e) {
			e.printStackTrace();
		}
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

	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(UserLoginActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
		pd.setCanceledOnTouchOutside(true);
		pd.setProgressNumberFormat(null);
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
		// TODO: 2018/4/25 zyjy
//		MainFragment.zhuangtai = false;
		UserLoginActivity.zhuangtai = false;
		PersonCenterActivity.zhuangtai = false;
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
		UserLoginActivity.this.startActivity(intent);
	}

	// 程序版本更新
	private void dialog() {

		AlertDialog.Builder builder = new Builder(UserLoginActivity.this);
		//		builder.setMessage(updatainfo);
		builder.setMessage("检查到最新版本，是否要更新！");
		builder.setTitle("提示:新版本");
		builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("UserLoginActivity.zhuangtai------------------"+UserLoginActivity.zhuangtai);
// TODO: 2018/4/25 zyjy
				//				if (MainFragment.zhuangtai == true) {
//					Toast.makeText(UserLoginActivity.this, "正在下载...", Toast.LENGTH_SHORT).show();
//					dialog.dismiss();
//				}else
					if (PersonCenterActivity.zhuangtai == true) {
					Toast.makeText(UserLoginActivity.this, "正在下载...", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}else if (UserLoginActivity.zhuangtai == true) {
					Toast.makeText(UserLoginActivity.this, "正在下载...", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}else{
					final String filePath = Environment.getExternalStorageDirectory() + "/ss";
					new UpdateApkThread("http://mobile.zams.cn/upload/201711/06/201711061711323273.apk", filePath, "zams.apk",UserLoginActivity.this).start();
					downLoadApk();
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

	// 获取当前程序的版本信息
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {

			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

}
