package com.yunsen.enjoy.activity.pay;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.pay.adapter.ZhiFufangshiAdapter;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.JuTuanGouData;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ZhiFuFangShiActivity extends AppCompatActivity implements
        OnClickListener {
	private DialogProgress progress;
	String user_name, user_id, login_sign, order_no;
	private SharedPreferences spPreferences;
	private String partner_id, prepayid, noncestr, timestamp, package_, sign;
	boolean flag;
	ArrayList<JuTuanGouData> list = new ArrayList<JuTuanGouData>();
	ZhiFufangshiAdapter zhiAdapter;
	private ListView listView;
	private int clickTemp = 0;
	public static String title, express_id;
	public static int express_fee;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhifu_fangshi);
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        user_name = spPreferences.getString(SpConstants.USER_NAME, "");
        user_id = spPreferences.getString("user_id", "");
        progress = new DialogProgress(ZhiFuFangShiActivity.this);
		setUpViews();
		loadWeather();
	}

	private void setUpViews() {
		// TextView item0 = (TextView) findViewById(R.id.item0);
		Button btn_login = (Button) findViewById(R.id.btn_login);
		listView = (ListView) findViewById(R.id.new_list);
		btn_login.setOnClickListener(this);

	}

	@Override
	protected void onResume() {

		super.onResume();

	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
				case 0:
					zhiAdapter = new ZhiFufangshiAdapter(getApplicationContext(),
							list);
					listView.setAdapter(zhiAdapter);

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {

							try {
								express_id = list.get(arg2).getId();
								title = list.get(arg2).getTitle();
								express_fee = list.get(arg2).getExpress_fee();

								System.out.println("=====id====================="
										+ express_id);
								// zhiAdapter.setSeclection(arg2);
								// zhiAdapter.notifyDataSetChanged();
								// Intent intent = new
								// Intent(ZhiFuFangShiActivity.this,
								// MyOrderConfrimActivity.class);
								// intent.putExtra("id", id);
								// intent.putExtra("title", title);
								// startActivity(intent);
								// int position = listView.getCheckedItemPosition();
								// if(ListView.INVALID_POSITION != position){
								// Toast.makeText(ZhiFuFangShiActivity.this,list.get(arg2).getTitle(),
								// 0).show();
								// }
								finish();
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					});
					break;
				case 1:
					break;
				default:
					break;
			}
		}
	};

	/**
	 * 配送方式
	 */
	private void loadWeather() {
		AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_express_list?top=5",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						System.out.println("输出所有拼团活动列表=========" + arg1);
						try {
							list = new ArrayList<JuTuanGouData>();

							progress.CloseProgress();
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = object
										.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject obj = jsonArray.getJSONObject(i);
									JuTuanGouData data = new JuTuanGouData();
									data.setId(obj.getString("id"));
									data.setTitle(obj.getString("title"));
									data.setExpress_fee(obj
											.getInt("express_fee"));
									list.add(data);
								}
								System.out.println("---------------------"
										+ list.size());
								progress.CloseProgress();
								handler.sendEmptyMessage(0);
							} else {
								progress.CloseProgress();
								Toast.makeText(ZhiFuFangShiActivity.this, info,
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {

							e.printStackTrace();
						}
					}

				}, null);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.item0:
				break;
			case R.id.btn_login:
				finish();
				break;
			default:
				break;
		}
	}

}
