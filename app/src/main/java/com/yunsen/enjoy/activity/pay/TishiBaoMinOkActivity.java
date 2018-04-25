package com.yunsen.enjoy.activity.pay;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.widget.DialogProgress;


/**
 * 提示已报名
 * @author
 *
 */
public class TishiBaoMinOkActivity extends AppCompatActivity implements OnClickListener {
	private TextView btnConfirm;//
	private TextView btnCancle,tv_yue;//
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign,amount;
	String user_name,user_id,headimgurl,access_token,sex,unionid,area,real_name;
	View iv_xian;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tishi_baomin_ok);
		//		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		//		user_name = spPreferences.getString("user", "");
		//		user_id = spPreferences.getString("user_id", "");
		//		progress = new DialogProgress(TishiWxBangDingActivity.this);
		initUI();
	}



	protected void initUI() {
		btnConfirm =(TextView) findViewById(R.id.btnConfirm);//
		btnConfirm.setOnClickListener(this);//
		btnCancle =(TextView) findViewById(R.id.btnCancle);//
		btnCancle.setOnClickListener(this);//
		iv_xian = findViewById(R.id.iv_xian);

		TextView tv_activity_ent = (TextView) findViewById(R.id.tv_activity_zt);

		System.out.println("bm_tishi----------------"+getIntent().getStringExtra("bm_tishi"));
		if (getIntent().getStringExtra("datatype_id").equals("8")) {
			if (getIntent().getStringExtra("bm_tishi").equals("1")) {
				tv_activity_ent.setText("您已报名，不可重复报名");
			}else {
				btnCancle.setVisibility(View.GONE);
				iv_xian.setVisibility(View.GONE);
				tv_activity_ent.setText("您已报名，不可重复报名");
			}
		}else if (getIntent().getStringExtra("datatype_id").equals("6")) {
			if (getIntent().getStringExtra("bm_tishi").equals("1")) {
				tv_activity_ent.setText("您已报名，不可重复报名");
			}else {
				btnCancle.setVisibility(View.GONE);
				iv_xian.setVisibility(View.GONE);
				tv_activity_ent.setText("您已报名，不可重复报名");
			}
		}else if (getIntent().getStringExtra("datatype_id").equals("5")) {
			tv_activity_ent.setText("您已报名，不可重复报名");
		}else if (getIntent().getStringExtra("datatype_id").equals("4")) {
			tv_activity_ent.setText("您已报名，不可重复报名");
		}

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
				Intent intent = new Intent(TishiBaoMinOkActivity.this, DianZiPiaoActivity.class);
				intent.putExtra("hd_title",getIntent().getStringExtra("title"));
				intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
				intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
				intent.putExtra("address",getIntent().getStringExtra("address"));
				intent.putExtra("trade_no",getIntent().getStringExtra("trade_no"));
				intent.putExtra("id",getIntent().getStringExtra("id"));
				intent.putExtra("real_name",getIntent().getStringExtra("real_name"));
				intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
				startActivity(intent);
				finish();
				break;

			default:
				break;
		}
	}



}