package com.yunsen.enjoy.activity.mine;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.adapter.MyAssetsAdapter;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.MyAssetsBean;
import com.yunsen.enjoy.model.UserRegisterllData;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.PullToRefreshView;
import com.yunsen.enjoy.widget.PullToRefreshView.OnFooterRefreshListener;
import com.yunsen.enjoy.widget.PullToRefreshView.OnHeaderRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * 我的资产
 *
 * @author Administrator
 *
 */
public class MyAssetsActivity extends AppCompatActivity implements OnClickListener {
	private ImageView iv_fanhui, cursor1, cursor2, cursor3, cursor4;
	private Button fanhui, btn_chongzhi;
	private LinearLayout index_item0, index_item1, index_item2, index_item3;
	private EditText et_chongzhi;
	private SharedPreferences spPreferences;
	private TextView tv_ticket, tv_shop_ticket, tv_jifen_ticket,
			tv_djjifen_ticket;
	private ArrayList<MyAssetsBean> list;
	private ListView listView;
	private PullToRefreshView refresh;
	MyAssetsAdapter adapter;
	int len;
	public static String fund_id = "0";
	private int RUN_METHOD = -1;
	private DialogProgress progress;
	private ImageView iv_biaoti1, iv_biaoti2, iv_biaoti3,
			iv_biaoti4;
	MyAssetsBean data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_assets);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(this);
		Initialize();

		list = new ArrayList<MyAssetsBean>();
		adapter = new MyAssetsAdapter(list, MyAssetsActivity.this);
		listView.setAdapter(adapter);
		loadguanggao();

		fund_id = "1";

		//		//判断状态到界面
		String status = getIntent().getStringExtra("status");
		if (status != null) {
			if (status.equals("1")) {
				cursor1.setVisibility(View.VISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				cursor4.setVisibility(View.INVISIBLE);
				fund_id = "1";
				load_list(true, fund_id);
			}else if (status.equals("2")) {
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.VISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				cursor4.setVisibility(View.INVISIBLE);
				fund_id = "4";
				load_list(true, fund_id);
			}else if (status.equals("3")) {
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.VISIBLE);
				cursor4.setVisibility(View.INVISIBLE);
				fund_id = "2";
				load_list(true, fund_id);
			}else if (status.equals("4")) {
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				cursor4.setVisibility(View.VISIBLE);
				fund_id = "3";
				load_list(true, fund_id);
			}
		}else{
			load_list(true, fund_id);
		}

	}


	/**
	 * 控件初始化
	 */
	private void Initialize() {

		try {
			iv_biaoti1 = (ImageView) findViewById(R.id.iv_biaoti1);
			iv_biaoti2 = (ImageView) findViewById(R.id.iv_biaoti2);
			iv_biaoti3 = (ImageView) findViewById(R.id.iv_biaoti3);
			iv_biaoti4 = (ImageView) findViewById(R.id.iv_biaoti4);
			Bitmap bm1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.zhye);
			BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), bm1);
			iv_biaoti1.setBackgroundDrawable(bd1);
			Bitmap bm2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ylj);
			BitmapDrawable bd2 = new BitmapDrawable(this.getResources(), bm2);
			iv_biaoti2.setBackgroundDrawable(bd2);
			Bitmap bm3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.jb);
			BitmapDrawable bd3 = new BitmapDrawable(this.getResources(), bm3);
			iv_biaoti3.setBackgroundDrawable(bd3);
			Bitmap bm4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.hb);
			BitmapDrawable bd4 = new BitmapDrawable(this.getResources(), bm4);
			iv_biaoti4.setBackgroundDrawable(bd4);
			refresh = (PullToRefreshView) findViewById(R.id.refresh);
			refresh.setOnHeaderRefreshListener(listHeadListener);
			refresh.setOnFooterRefreshListener(listFootListener);
			listView = (ListView) findViewById(R.id.new_list);
			tv_ticket = (TextView) findViewById(R.id.tv_ticket);
			tv_shop_ticket = (TextView) findViewById(R.id.tv_shop_ticket);
			tv_jifen_ticket = (TextView) findViewById(R.id.tv_jifen_ticket);
			tv_djjifen_ticket = (TextView) findViewById(R.id.tv_djjifen_ticket);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			index_item2 = (LinearLayout) findViewById(R.id.index_item2);
			index_item3 = (LinearLayout) findViewById(R.id.index_item3);
			cursor1 = (ImageView) findViewById(R.id.cursor1);
			cursor2 = (ImageView) findViewById(R.id.cursor2);
			cursor3 = (ImageView) findViewById(R.id.cursor3);
			cursor4 = (ImageView) findViewById(R.id.cursor4);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			index_item2.setOnClickListener(this);
			index_item3.setOnClickListener(this);

			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					finish();
				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.index_item0:
				// index_item0.setVisibility(View.VISIBLE);
				// index_item1.setVisibility(View.INVISIBLE);
				// index_item2.setVisibility(View.INVISIBLE);
				// index_item3.setVisibility(View.INVISIBLE);
				cursor1.setVisibility(View.VISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				cursor4.setVisibility(View.INVISIBLE);
				fund_id = "1";
				load_list(true, fund_id);
				break;
			case R.id.index_item1:
				// index_item0.setVisibility(View.INVISIBLE);
				// index_item1.setVisibility(View.VISIBLE);
				// index_item2.setVisibility(View.INVISIBLE);
				// index_item3.setVisibility(View.INVISIBLE);
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.VISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				cursor4.setVisibility(View.INVISIBLE);
				fund_id = "4";
				load_list(true, fund_id);
				break;
			case R.id.index_item2:
				// index_item0.setVisibility(View.INVISIBLE);
				// index_item1.setVisibility(View.INVISIBLE);
				// index_item2.setVisibility(View.VISIBLE);
				// index_item3.setVisibility(View.INVISIBLE);
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.VISIBLE);
				cursor4.setVisibility(View.INVISIBLE);
				fund_id = "2";
				load_list(true, fund_id);
				break;
			case R.id.index_item3:
				// index_item0.setVisibility(View.INVISIBLE);
				// index_item1.setVisibility(View.INVISIBLE);
				// index_item2.setVisibility(View.INVISIBLE);
				// index_item3.setVisibility(View.VISIBLE);
				cursor1.setVisibility(View.INVISIBLE);
				cursor2.setVisibility(View.INVISIBLE);
				cursor3.setVisibility(View.INVISIBLE);
				cursor4.setVisibility(View.VISIBLE);
				fund_id = "3";
				load_list(true, fund_id);
				break;

			default:
				break;
		}
	}

	/**
	 * 上拉列表刷新加载
	 */
	private OnHeaderRefreshListener listHeadListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {

			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					refresh.onHeaderRefreshComplete();
				}
			}, 1000);
		}
	};

	/**
	 * 下拉列表刷新加载
	 */
	private OnFooterRefreshListener listFootListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {

			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						// if (RUN_METHOD == 0) {
						// System.out.println("RUN_METHOD1========="+
						// RUN_METHOD);
						// load_list2(false);
						// } else {
						System.out.println("RUN_METHOD2=========" + RUN_METHOD);
						load_list(false, fund_id);
						// }
						refresh.onFooterRefreshComplete();

					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};

	// private OnFooterRefreshListener listFootListener = new
	// OnFooterRefreshListener() {
	//
	// @Override
	// public void onFooterRefresh(PullToRefreshView view) {
	//
	// refresh.postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// if(RUN_METHOD==0){
	// load_P(false);
	// }else {
	// // if(INDX!=-1)
	// load(INDX, false);
	// }
	// refresh.onFooterRefreshComplete();
	// }
	// }, 1000);
	// }
	// };

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
				case 0:
					adapter.putData(list);
					break;
				default:
					break;
			}
		};
	};

	/**
	 * 第1个列表数据解析
	 */
	// private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;

	private void load_list(boolean flag, String fund_id) {
		progress.CreateProgress();
		RUN_METHOD = 1;
		if (flag) {
			// 计数和容器清零
			System.out.println("=====================flag==" + flag);
			CURRENT_NUM = 1;
			list = new ArrayList<MyAssetsBean>();
		}
		String user_name = spPreferences.getString("user", "");
		String user_id = spPreferences.getString("user_id", "");
		// + user_id + "&user_name="+125+"&fund_id=13502883181
		System.out.println("=====================fund_id--" + fund_id);
		AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_payrecord_list?user_id="
						+ user_id + "&user_name=" + user_name + "&fund_id=" + fund_id
						+ "&page_size=" + VIEW_NUM + "&page_index=" + CURRENT_NUM + "",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {

						super.onSuccess(arg0, arg1);
						System.out.println("=====================二级值1" + arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = object
										.getJSONArray("data");
								len = jsonArray.length();
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject json = jsonArray
											.getJSONObject(i);
									data = new MyAssetsBean();
									data.fund = json.getString("fund");
									data.income = json.getString("income");
									data.user_name = json
											.getString("user_name");
									data.add_time = json.getString("add_time");
									data.expense = json.getString("expense");
									data.remark = json.getString("remark");
									data.balance = json.getString("balance");
									list.add(data);
								}
								data= null;
							} else {
								Toast.makeText(MyAssetsActivity.this, info, Toast.LENGTH_SHORT)
										.show();
							}
							System.out.println("=====================二级值12");
							Message msg = new Message();
							msg.what = 0;
							msg.obj = list;
							handler.sendMessage(msg);

							if (len != 0) {
								CURRENT_NUM = CURRENT_NUM + 1;
							}
							progress.CloseProgress();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable throwable, String s) {
						super.onFailure(throwable, s);
						progress.CloseProgress();
					}
				}, null);
	}

	/**
	 * 第2个列表数据解析
	 */
	//	private void load_list2(boolean flag) {
	//		list = new ArrayList<MyAssetsBean>();
	//		if (flag) {
	//			// 计数和容器清零
	//			CURRENT_NUM = 0;
	//			list = new ArrayList<MyAssetsBean>();
	//		}
	//		AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_payrecord_list?user_id="
	//				+ 125 + "&user_name=13502883181&fund_id=" + 1 + ""
	//				+ "&page_size=" + VIEW_NUM + "&page_index=" + CURRENT_NUM + "",
	//				new AsyncHttpResponseHandler() {
	//					@Override
	//					public void onSuccess(int arg0, String arg1) {
	//
	//						super.onSuccess(arg0, arg1);
	//						System.out.println("=====================二级值2" + arg1);
	//						try {
	//							JSONObject object = new JSONObject(arg1);
	//							String status = object.getString("status");
	//							String info = object.getString("info");
	//							if (status.equals("y")) {
	//								JSONArray jsonArray = object
	//										.getJSONArray("data");
	//								len = jsonArray.length();
	//								for (int i = 0; i < jsonArray.length(); i++) {
	//									JSONObject json = jsonArray
	//											.getJSONObject(i);
	//									MyAssetsBean data = new MyAssetsBean();
	//									data.fund = json.getString("fund");
	//									data.income = json.getString("income");
	//									data.user_name = json
	//											.getString("user_name");
	//									data.add_time = json.getString("add_time");
	//									data.remark = json.getString("remark");
	//									list.add(data);
	//								}
	//								refresh.setVisibility(View.VISIBLE);
	//							} else {
	//								refresh.setVisibility(View.GONE);
	//								Toast.makeText(MyAssetsActivity.this, info, 200)
	//										.show();
	//							}
	//							Message msg = new Message();
	//							msg.what = 0;
	//							msg.obj = list;
	//							handler.sendMessage(msg);
	//						} catch (JSONException e) {
	//
	//							e.printStackTrace();
	//						}
	//					}
	//				}, null);
	//	}

	/**
	 * 值
	 */
	private void loadguanggao() {
		try {
			String user_name = spPreferences.getString("user", "");
			String strUrl = URLConstants.REALM_NAME_LL
					+ "/get_user_model?username=" + user_name + "";
			System.out.println("======11=============" + strUrl);
			AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======输出用户资料=============" + arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							UserRegisterllData data = new UserRegisterllData();
							data.user_name = obj.getString("user_name");
							data.user_code = obj.getString("user_code");
							data.agency_id = obj.getInt("agency_id");
							data.amount = obj.getString("amount");
							data.pension = obj.getString("pension");
							data.packet = obj.getString("packet");
							data.point = obj.getString("point");// point
							data.group_id = obj.getString("group_id");
							data.login_sign = obj.getString("login_sign");
							data.agency_name = obj.getString("agency_name");
							data.group_name = obj.getString("group_name");

							tv_ticket.setText(data.amount + "元");
							tv_shop_ticket.setText(data.pension + "元");
							tv_jifen_ticket.setText(data.packet + "元");
							tv_djjifen_ticket.setText(data.point + "");// packet
							// point

						} else {

						}
					} catch (JSONException e) {

						e.printStackTrace();
					}
				};

				@Override
				public void onFailure(Throwable arg0, String arg1) {

					super.onFailure(arg0, arg1);
					Toast.makeText(MyAssetsActivity.this, "连接超时",
							Toast.LENGTH_SHORT).show();
				}
			}, getApplicationContext());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void onDestroy() {
		super.onDestroy();
		try {

			if (list.size() > 0) {
				list.clear();
				list = null;
			}

			BitmapDrawable bd1 = (BitmapDrawable)iv_biaoti1.getBackground();
			iv_biaoti1.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd1.setCallback(null);
			bd1.getBitmap().recycle();
			BitmapDrawable bd2 = (BitmapDrawable)iv_biaoti2.getBackground();
			iv_biaoti2.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd2.setCallback(null);
			bd2.getBitmap().recycle();
			BitmapDrawable bd3 = (BitmapDrawable)iv_biaoti3.getBackground();
			iv_biaoti3.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd3.setCallback(null);
			bd3.getBitmap().recycle();
			BitmapDrawable bd4 = (BitmapDrawable)iv_biaoti4.getBackground();
			iv_biaoti4.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
			bd4.setCallback(null);
			bd4.getBitmap().recycle();
		} catch (Exception e) {

			e.printStackTrace();
		}
	};
}
