package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.pay.MonneyChongZhiActivity;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 余额充值
 *
 * @author Administrator
 *
 */
public class MyQianBaoActivity extends AppCompatActivity implements OnClickListener {
	private Button chongzhi_submit;
	private TextView tv_ticket;
	private SharedPreferences spPreferences;
	String user_name, user_id, pwd;
	public static String recharge_no, notify_url, return_url;
	private ImageView iv_fanhui;
	private DialogProgress progress;
	LinearLayout yu_pay0;

	@Override
	protected void onResume() {

		super.onResume();
		userloginqm();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbao_chongzhi);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		progress = new DialogProgress(this);
		// yth = registerData.getHengyuCode();
		// key = registerData.getUserkey();

		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		pwd = spPreferences.getString("pwd", "");
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(this);

		// userloginqm();

		tv_ticket = (TextView) findViewById(R.id.tv_monney);
		chongzhi_submit = (Button) findViewById(R.id.chongzhi_submit);
		yu_pay0 = (LinearLayout) findViewById(R.id.yu_pay0);
		//		yu_pay0.setBackgroundResource(R.drawable.my_qianbao);
		Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.my_qianbao);
		BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
		yu_pay0.setBackgroundDrawable(bd);
		chongzhi_submit.setOnClickListener(this);
	}

	//当Activity被销毁时会调用onDestory方法
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//        MyQianBaoActivity.this.finish();
		BitmapDrawable bd1 = (BitmapDrawable)yu_pay0.getBackground();
		yu_pay0.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
		bd1.setCallback(null);
		bd1.getBitmap().recycle();
	}

	@Override
	public void onClick(View v) {


		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;
			case R.id.chongzhi_submit:
				Intent intent = new Intent(MyQianBaoActivity.this,
						MonneyChongZhiActivity.class);
				startActivity(intent);
				break;

			default:
				break;
		}
	}

	/**
	 * 获取登录签名
	 */
	private void userloginqm() {
		try {
			SharedPreferences spPreferences = getSharedPreferences(
					"longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String strUrlone = URLConstants.REALM_NAME_LL
					+ "/get_user_model?username=" + user_name + "";
			System.out.println("======11=============" + strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						JSONObject obj = object.getJSONObject("data");
						if (status.equals("y")) {
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
							String amount = obj.getString("amount");
							tv_ticket.setText(amount);
						} else {
						}
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};
			}, MyQianBaoActivity.this);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
