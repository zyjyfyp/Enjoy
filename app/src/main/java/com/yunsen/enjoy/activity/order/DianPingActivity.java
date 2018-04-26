package com.yunsen.enjoy.activity.order;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 点评
 *
 * @author Administrator
 *
 */
public class DianPingActivity extends AppCompatActivity implements OnClickListener {

	private ImageView iv_fanhui;
	private TextView tv_xiabu;
	EditText ra4;
	String check = "";
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_dianping);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(DianPingActivity.this);
		intren();

	}

	public void intren() {
		try {

			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
			iv_fanhui.setOnClickListener(this);
			RadioGroup ra0 = (RadioGroup) findViewById(R.id.ra0);
			RadioButton ra1 = (RadioButton) findViewById(R.id.ra1);
			RadioButton ra2 = (RadioButton) findViewById(R.id.ra2);
			RadioButton ra3 = (RadioButton) findViewById(R.id.ra3);
			ra4 = (EditText) findViewById(R.id.ra4);
			Button ra5 = (Button) findViewById(R.id.ra5);
			ra5.setOnClickListener(this);

//		ra1.setChecked(true);


			ra0.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup arg0, int arg1) {

					switch (arg1) {
						case R.id.ra1:
							check = "0";
//					ra4.setText("我们的商品一定超出您的期望！");
							break;
						case R.id.ra2:
							check = "1";
//					ra4.setText("一定是我不够完美才让你这样犹豫，我们一定会做得更好！");
							break;
						case R.id.ra3:
							check = "2";
//					ra4.setText("您宝贵的建议对我们有很大的帮助！");
							break;
						default:
							break;
					}
				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void loadWeather() {
		String article_id = getIntent().getStringExtra("article_id");
		String user_name = spPreferences.getString("user", "");
		String id = spPreferences.getString("user_id", "");
		String content = ra4.getText().toString();
		if (check.equals("")) {
			Toast.makeText(DianPingActivity.this, "请选择评论", Toast.LENGTH_SHORT).show();
		}else {
			progress.CreateProgress();
			AsyncHttp.get(URLConstants.REALM_NAME_LL + "/comment_add?user_id="+id+"&user_name="+user_name+"" +
					"&article_id="+article_id+"&content="+content+"&score="+check+"", new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {

					super.onSuccess(arg0, arg1);
					System.out.println("=======详情数据=="+arg1);
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
							Toast.makeText(DianPingActivity.this, info, Toast.LENGTH_SHORT).show();
							finish();
						}else {
							Toast.makeText(DianPingActivity.this, info, Toast.LENGTH_SHORT).show();
						}
						progress.CloseProgress();
					} catch (JSONException e) {

						e.printStackTrace();
					}
				}

			}, null);
		}
	}


	@Override
	public void onClick(View v) {


		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;
			case R.id.ra5:
				loadWeather();
				break;

			default:
				break;
		}
	}
}
