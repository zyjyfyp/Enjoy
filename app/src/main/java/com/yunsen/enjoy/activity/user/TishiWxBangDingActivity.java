package com.yunsen.enjoy.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 提示绑定手机号
 * @author
 *
 */
public class TishiWxBangDingActivity extends AppCompatActivity implements OnClickListener {
	private TextView btnConfirm;//
	private TextView btnCancle,tv_yue;//
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	private DialogProgress progress;
	private SharedPreferences spPreferences_login;
	private SharedPreferences spPreferences_weixin;
	private SharedPreferences spPreferences_qq;
	String login_sign,amount;
	public static String yue_zhuangtai;
	String user_name,user_id,headimgurl,access_token,sex,unionid,area,real_name;
	String province = "";
	String city = "";
	String country = "";
	String nickname = "";
	String user_name_weixin = "";
	String user_name_qq = "";
	//	private String nickname, headimgurl, access_token, sex, unionid,province,city,country;
	String oauth_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tishi_weixin);
		//		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		//		user_name = spPreferences.getString("user", "");
		//		user_id = spPreferences.getString("user_id", "");

		//		nickname = spPreferences.getString("nickname", "");
		//		headimgurl = spPreferences.getString("headimgurl", "");
		//		unionid = spPreferences.getString("unionid", "");
		//		access_token = spPreferences.getString("access_token", "");
		//		sex = spPreferences.getString("sex", "");
		//		province = getIntent().getStringExtra("province");
		//		city = getIntent().getStringExtra("city");
		//		country = getIntent().getStringExtra("country");
		//		progress = new DialogProgress(TishiWxBangDingActivity.this);
		initUI();
	}



	protected void initUI() {
		btnConfirm =(TextView) findViewById(R.id.btnConfirm);//
		btnConfirm.setOnClickListener(this);//
		btnCancle =(TextView) findViewById(R.id.btnCancle);//
		btnCancle.setOnClickListener(this);//

		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case 8:

				}
			}
		};
	}


	/**
	 * 点击触发事件
	 */
	@Override
	public void onClick(View v) {


		intent = new Intent();
		switch (v.getId()) {
			case R.id.btnConfirm://取消
				finish();
				break;
			case R.id.btnCancle://
				userlogin();
				finish();
				break;

			default:
				break;
		}
	}

	public void userlogin(){
		try{
			//				nickname = getIntent().getStringExtra("nickname");
			//				String headimgurl = getIntent().getStringExtra("headimgurl");
			//				String province = getIntent().getStringExtra("province");
			//				String city = getIntent().getStringExtra("city");
			//				String country = getIntent().getStringExtra("country");
			//				String access_token = getIntent().getStringExtra("access_token");
			//				String unionid = getIntent().getStringExtra("unionid");
			//				String sex_ll = getIntent().getStringExtra("sex");

			spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);

			nickname = spPreferences_login.getString("nickname", "");
			headimgurl = spPreferences_login.getString("headimgurl", "");
			unionid = spPreferences_login.getString("unionid", "");
			access_token = spPreferences_login.getString("access_token", "");
			sex = spPreferences_login.getString("sex", "");
			String oauth_openid = spPreferences_login.getString("oauth_openid", "");


			//				spPreferences_weixin = getSharedPreferences("longuserset_weixin", Context.MODE_PRIVATE);
			//				user_name_weixin = spPreferences_weixin.getString("nickname", "");
			//
			//                spPreferences_qq = getSharedPreferences("longuserset_qq", Context.MODE_PRIVATE);
			//                user_name_qq = spPreferences_qq.getString("nickname", "");
			//
			//				System.out.println("user_name_weixin================"+user_name_weixin);
			//				System.out.println("user_name_qq================"+user_name_qq);
			//
			//				if (!user_name_weixin.equals("")) {
			//					nickname = user_name_weixin;
			//					headimgurl = spPreferences_weixin.getString("headimgurl", "");
			//					unionid = spPreferences_weixin.getString("unionid", "");
			//					access_token = spPreferences_weixin.getString("access_token", "");
			//					sex = spPreferences_weixin.getString("sex", "");
			//				}else {
			//					nickname = user_name_qq;
			//					headimgurl = "";
			//					unionid = spPreferences_qq.getString("unionid", "");
			//					access_token = spPreferences_qq.getString("access_token", "");
			//					sex = spPreferences_qq.getString("sex", "");
			//				}

			province = getIntent().getStringExtra("province");
			city = getIntent().getStringExtra("city");
			//				country = getIntent().getStringExtra("country");

			if (province == null) {
				province = "";
			}
			if (city == null) {
				city = "";
			}

			System.out.println("UserLoginActivity.oauth_name====================="+UserLoginActivity.oauth_name);
			//				if (!UserLoginActivity.oauth_name.equals("")) {
			System.out.println("UserLoginActivity====================="+UserLoginActivity.oauth_name);
			System.out.println("UserLoginWayActivity====================="+UserLoginWayActivity.oauth_name);

			if (UserLoginActivity.oauth_name.equals("weixin")) {
				oauth_name = "weixin";
			} else if (UserLoginWayActivity.oauth_name.equals("weixin")) {
				oauth_name = "qq";
			}

			//				}
			System.out.println("nickname-----1-----"+nickname);
			String nick_name = nickname.replaceAll("\\s*", "");
			System.out.println("nick_name-----2-----"+nick_name);

			//				String strUrlone = URLConstants.REALM_NAME_LL + "/user_oauth_register_0215?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+"" +
			//				"&province="+province+"&city="+city+"&country="+country+"&oauth_name="+oauth_name+"&oauth_access_token="+access_token+"" +
			//						"&oauth_unionid="+unionid+"";
			String strUrlone = URLConstants.REALM_NAME_LL + "/user_oauth_register_0217?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+"" +
					"&province="+province+"&city="+city+"&country="+country+"&oauth_name="+oauth_name+"&oauth_unionid="+unionid+"" +
					"&oauth_openid="+oauth_openid+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					System.out.println("======输出============="+arg1);
					//						Toast.makeText(UserLoginActivity.this, "数据为+"+arg1, 400).show();
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						//							if (status.equals("y")) {
						String datall = object.getString("data");
						//								data.id = obj.getString("id");
						//								data.user_name = obj.getString("user_name");
						//								province = obj.getString("province");
						//								city = obj.getString("city");
						//								area = obj.getString("area");

						if (datall.equals("null")) {
							Intent intent = new Intent(TishiWxBangDingActivity.this, MobilePhoneActivity.class);
							intent.putExtra("nickname", nickname);
							intent.putExtra("access_token", access_token);
							intent.putExtra("unionid", unionid);
							intent.putExtra("sex", sex);
							intent.putExtra("headimgurl", headimgurl);
							startActivity(intent);

						}else {
							JSONObject obj = object.getJSONObject("data");
							UserRegisterllData data = new UserRegisterllData();
							data.id = obj.getString("id");
							data.user_name = obj.getString("user_name");
							user_id = data.id;
							System.out.println("---data.user_name-------------------"+data.user_name);
							System.out.println("---user_id-------------------"+user_id);

							SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
							String user = spPreferences.getString("user", "");
							System.out.println("---1-------------------"+user);

							Editor editor = spPreferences.edit();
							editor.putString("user",data.user_name);
							editor.putString("user_id", data.id);
							editor.commit();

							String user_name = spPreferences.getString("user", "");
							System.out.println("---2-------------------"+user_name);

						}

						//							}else {
						//								 Toast.makeText(TishiWxBangDingActivity.this, info, 200).show();
						//								 finish();
						//							}
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};
			}, TishiWxBangDingActivity.this);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}


}