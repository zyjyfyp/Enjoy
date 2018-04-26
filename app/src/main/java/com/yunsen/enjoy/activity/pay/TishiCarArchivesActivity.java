package com.yunsen.enjoy.activity.pay;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 钱包支付（输入密码）
 * @author
 *
 */
public class TishiCarArchivesActivity extends Activity implements OnClickListener {
	private TextView btnConfirm;//
	private TextView btnCancle,tv_yue;//
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	String user_name, user_id,pwd;
	private EditText zhidupess;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign,amount;
	public static String yue_zhuangtai;
	public static String order_type= "0";
	public static String province,city,area,user_address,accept_name,user_mobile;
	public static String recharge_no,order_no,datetime,sell_price,give_pension,article_id;
	public static String huodong_type = "0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tishi_carxing);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		//		pwd = spPreferences.getString("pwd", "");
		progress = new DialogProgress(TishiCarArchivesActivity.this);
		order_no = getIntent().getStringExtra("order_no");
		System.out.println("order_no-------------"+order_no);
		useryue();
		initUI();
	}



	protected void initUI() {
		zhidupess = (EditText) findViewById(R.id.et_user_pwd);
		btnConfirm =(TextView) findViewById(R.id.btnConfirm);//
		btnConfirm.setOnClickListener(this);//
		btnCancle =(TextView) findViewById(R.id.btnCancle);//
		//		tv_yue =(TextView) findViewById(R.id.tv_yue);
		//		System.out.println("amount-------------"+amount);
		//		tv_yue.setText("你剩余的余额为￥"+amount);

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
				//			String yue_fanhui = getIntent().getStringExtra("yue");
				finish();
				yue_zhuangtai = "1";
				break;
			case R.id.btnCancle://
				pwd = zhidupess.getText().toString().trim();
				System.out.println("pwd-------------"+pwd);
				if (pwd.equals("")) {
					Toast.makeText(TishiCarArchivesActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
				}else{
					String yue = getIntent().getStringExtra("yue");
					String jubi = getIntent().getStringExtra("jubi");
					String order_yue = getIntent().getStringExtra("order_yue");
					if (yue != null) {
						userloginqm();
					}else if (jubi != null) {
						userloginqm();
					}else if (order_yue != null) {
						userloginqm();
					}else {
						ShouhuoOK(order_no);
					}

				}
				break;

			default:
				break;
		}
	}

	/**
	 * 判断当前聚币与余额的值
	 * @param
	 */
	private void useryue() {
		String strUrlone = URLConstants.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
		AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					System.out.println("当前聚币与余额的值-------------"+arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					if (status.equals("y")) {
						JSONObject obj = object.getJSONObject("data");
						amount = obj.getString("amount");
						String point = obj.getString("point");
						String jubi = getIntent().getStringExtra("jubi");
						String title = getIntent().getStringExtra("title");
						tv_yue =(TextView) findViewById(R.id.tv_yue);
						if (title != null) {
							tv_yue.setText("提示");
						}else {
							if (jubi != null) {
								tv_yue.setText("您剩余的福利为￥"+point);
								System.out.println("point-------------"+point);
							}else {
								tv_yue.setText("您剩余的余额为￥"+amount);
								System.out.println("amount-------------"+amount);
							}
						}
					}else{
					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			};
		}, null);

	}

	/**
	 * 获取登录签名
	 * @param
	 */
	private void userloginqm() {
		try{
			//			SharedPreference spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			//			String user_name = spPreferences.getString("user", "");
			String strUrlone = URLConstants.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
							amount = obj.getString("amount");
							login_sign = data.login_sign;
							System.out.println("======login_sign============="+login_sign);
							System.out.println("======recharge_no============="+order_no);
							loadYue(order_no,login_sign);
						}else{
						}
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};
			}, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 余额支付
	 * @param login_sign
	 * @param
	 */
	private void loadYue(String order_no, String login_sign) {
		try {
			//		String total_fee = String.valueOf(Double.parseDouble(retailPrice) + Double.parseDouble(String.valueOf(express_fee)));
			//		AsyncHttp.get(URLConstants.REALM_NAME_LL
			//				+ "/payment_balance?user_id="+user_id+"&user_name="+user_name+"" +
			//						"&trade_no="+order_no+"&paypassword="+pwd+"&sign="+login_sign+"",
			AsyncHttp.get(URLConstants.REALM_NAME_HTTP+"/api/payment/balance/index.aspx?action=payment&user_id="+user_id+"&user_name="+user_name+"" +
							"&user_sign="+login_sign+"&paypassword="+pwd+"&trade_no="+order_no+"",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("2================================="+arg1);
								String status = object.getString("status");
								String info = object.getString("info");
								if (status.equals("y")) {
									progress.CloseProgress();
									//							    	JSONObject jsonObject = object.getJSONObject("data");
									//										JSONArray jay = jsonObject.getJSONArray("orders");
									//										for (int j = 0; j < jay.length(); j++){
									//										   JSONObject objc= jay.getJSONObject(j);
									//										    accept_name = objc.getString("accept_name");
									//										    province = objc.getString("province");
									//											city = objc.getString("city");
									//											area = objc.getString("area");
									//											user_mobile = objc.getString("mobile");
									//											user_address = objc.getString("address");
									//											recharge_no = objc.getString("order_no");
									//											datetime = objc.getString("add_time");
									//											sell_price = objc.getString("payable_amount");
									//											JSONArray jsonArray = objc.getJSONArray("order_goods");
									//											for (int i = 0; i < jsonArray.length(); i++) {
									//												JSONObject json = jsonArray.getJSONObject(i);
									//												article_id = json.getString("article_id");
									////												sell_price = json.getString("sell_price");
									//												give_pension = json.getString("give_pension");
									//											}
									//										}
									//										System.out.println("user_address================================="+user_address);
									//							    	String order_yue = getIntent().getStringExtra("order_yue");
									//							    	String yue = getIntent().getStringExtra("yue");
									//							    	String jubi = getIntent().getStringExtra("jubi");
									//									if (yue != null) {
									//										MyOrderConfrimActivity.teby = true;
									//										System.out.println("yue-------------"+yue);
									//									}
									//									else if (order_yue != null) {
									//										MyOrderXqActivity.teby = true;
									//										System.out.println("order_yue-------------"+order_yue);
									//									}
									//									else if (jubi != null) {
									//										System.out.println("jubi-------------"+jubi);
									//									}else {
									////										MyOrderActivity.teby = true;
									//									}
									//									 order_type = getIntent().getStringExtra("order_type");
									//									 order_type = "1";//支付状态
									//									 System.out.println("order_type==============1==================="+order_type);

									//活动支付成功不显示详情
									// TODO: 2018/4/25 zyjy 报名成功
									Toast.makeText(TishiCarArchivesActivity.this, "报名成功", Toast.LENGTH_SHORT).show();
//									if (BaoMinTiShiActivity.huodong_zf_type.equals("1")) {
//										BaoMinTiShiActivity.huodong_zf_type = "0";
//										//										 huodong_type = "1";//活动支付成功之后设置不能继续报名
//										Intent intent = new Intent(TishiCarArchivesActivity.this,BaoMinOKActivity.class);
//										intent.putExtra("img_url",getIntent().getStringExtra("img_url"));
//										intent.putExtra("hd_title",getIntent().getStringExtra("hd_title"));
//										intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
//										intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
//										intent.putExtra("address",getIntent().getStringExtra("address"));
//										intent.putExtra("trade_no",getIntent().getStringExtra("order_no"));
//										intent.putExtra("id",getIntent().getStringExtra("id"));
//										intent.putExtra("real_name",getIntent().getStringExtra("real_name"));
//										intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
//										startActivity(intent);
//										finish();
//									}else {
//										Toast.makeText(TishiCarArchivesActivity.this, info, Toast.LENGTH_SHORT).show();
//										//									Intent intent = new Intent(TishiCarArchivesActivity.this,ZhiFuOKActivity.class);
//										//									startActivity(intent);
//										finish();
//									}
								}else {
									progress.CloseProgress();
									Toast.makeText(TishiCarArchivesActivity.this, info, Toast.LENGTH_SHORT).show();
									finish();
								}

							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {

							super.onFailure(arg0, arg1);
							System.out.println("arg0-------------"+arg0);
							System.out.println("arg1-------------"+arg1);
							Toast.makeText(TishiCarArchivesActivity.this, "异常", Toast.LENGTH_SHORT).show();
						}

					}, null);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	/**
	 * 确认收货
	 * @param order_no
	 * @param
	 */
	public void ShouhuoOK(String order_no) {
		progress.CreateProgress();
		System.out.println("order_no================================="+order_no);
		System.out.println("login_sign================================="+pwd);
		AsyncHttp.get(URLConstants.REALM_NAME_LL
						+ "/update_order_complete?user_id="+user_id+"&user_name="+user_name+"" +
						"&trade_no="+order_no+"&paypassword="+pwd+"",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("arg1================================"+arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								progress.CloseProgress();
								//									  order_type = getIntent().getStringExtra("order_type");
								order_type = "2";//确认收货状态
								System.out.println("order_type=============2===================="+order_type);
								Toast.makeText(TishiCarArchivesActivity.this, "确认订单完成", Toast.LENGTH_SHORT).show();
								finish();
							}else {
								progress.CloseProgress();
								Toast.makeText(TishiCarArchivesActivity.this, info, Toast.LENGTH_SHORT).show();
							}


						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, TishiCarArchivesActivity.this);

	}

}