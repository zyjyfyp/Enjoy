package com.yunsen.enjoy.activity.pay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.zxing.WriterException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.OrderBean;
import com.yunsen.enjoy.model.XiangqingData;
import com.yunsen.enjoy.utils.EncodingHandler;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DianZiPiaoActivity extends AppCompatActivity {
    private String yth, key, strUrl;
    private DialogProgress progress;
    Button btn_holdr;
    ImageView img_ware;
    private SharedPreferences spPreferences;
    //	TextView
    public static AQuery mAq;
    String user_name, user_id, real_name, mobile;
    Bitmap _Bitmap;
    TextView tv_feiyong, tv_name, tv_ware_name, tv_dizhi, tv_time, tv_ent_time, tv_qiandao;
    private int TIME = 1000;
    boolean weizhi_type = false;
    public static int erweima_type = 0;
    Handler handler = new Handler();
    LinearLayout ll_qiandao_pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianzipiao);
        progress = new DialogProgress(DianZiPiaoActivity.this);
        mAq = new AQuery(DianZiPiaoActivity.this);
        spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
        //		user_name = spPreferences.getString("user", "");
        //		user_id = spPreferences.getString("user_id", "");
        //		real_name = spPreferences.getString("real_name", "");
        mobile = spPreferences.getString("mobile", "");
        weizhi_type = false;
        initdata();
        load_list();
        System.out.println("erweima_type=================================" + erweima_type);
        handler.postDelayed(runnable, TIME); //每隔1s执行
    }


    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                //	                tvShow.setText(Integer.toString(i++));
                //	                System.out.println("weizhi_type============1================"+weizhi_type);
                //	                System.out.println("do...");
                if (weizhi_type == false) {
                    loadzhifuweix();
                } else {

                }
            } catch (Exception e) {

                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };

    //	Handler handler = new Handler() {
    //		public void dispatchMessage(Message msg) {
    //			switch (msg.what) {
    //			case 0:
    //				break;
    //
    //			default:
    //				break;
    //			}
    //		};
    //	};
    private void initdata() {
        tv_feiyong = (TextView) findViewById(R.id.tv_feiyong);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_qiandao = (TextView) findViewById(R.id.tv_qiandao_wz);
        ll_qiandao_pd = (LinearLayout) findViewById(R.id.ll_qiandao_pd);
        //		if (ZhongAnMinShenXqActivity.retailPrice.equals("0.0")) {
        //			tv_feiyong.setText("费用："+"免费");
        //		}else {
        //			tv_feiyong.setText("费用：￥"+ZhongAnMinShenXqActivity.retailPrice);
        //		}
        ////		tv_name.setText(BaoMinTiShiActivity.real_name+"("+BaoMinTiShiActivity.user_name+")");
        //		tv_name.setText(real_name+"("+user_name+")");

        tv_ware_name = (TextView) findViewById(R.id.tv_ware_name);
        tv_dizhi = (TextView) findViewById(R.id.tv_dizhi);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_ent_time = (TextView) findViewById(R.id.tv_ent_time);

        //		tv_ware_name.setText(getIntent().getStringExtra("hd_title"));
        //		tv_time.setText("时间："+getIntent().getStringExtra("start_time")+"--"+getIntent().getStringExtra("end_time"));
        //		tv_dizhi.setText("地点："+getIntent().getStringExtra("address"));

        ImageView img_ware = (ImageView) findViewById(R.id.img_ware);
        //		String url = "http://mobile.zams.cn/signup/award-"+getIntent().getStringExtra("trade_no")+".html";
        String url = getIntent().getStringExtra("trade_no");
        System.out.println("url---------------------" + url);
        try {
            _Bitmap = EncodingHandler.createQRCode(url, 350);


            img_ware.setImageBitmap(_Bitmap);
        } catch (WriterException e) {

            e.printStackTrace();
        }
        //		img_ware.setBackgroundResource(R.drawable.hd_reweiam);
        //		mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP + getIntent().getStringExtra("img_url"));

        ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });
    }


    /**
     * 签到查询
     */
    String id;

    private void loadzhifuweix() {
        try {
            //			progress.CreateProgress();
            id = getIntent().getStringExtra("id");
            AsyncHttp.get(URLConstants.REALM_NAME_LL
                            + "/check_order_signin?mobile=" + mobile + "&article_id=" + id + "",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject object = new JSONObject(arg1);
                                System.out.println("签到查询=================================" + arg1);
                                String status = object.getString("status");
                                String info = object.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    tv_qiandao.setText("报名成功");
                                    ll_qiandao_pd.setVisibility(View.VISIBLE);
                                    //							    	System.out.println("当前用户已报名，未签到=================================");
                                    //									Toast.makeText(DianZiPiaoActivity.this, info, 200).show();
                                } else {
                                    progress.CloseProgress();
                                    //							    	   if (info.contains("您未报名，不可以签到")) {
                                    //							    		   tv_qiandao.setText("您未报名，不可以签到");
                                    //									   }else {
                                    erweima_type = 1;
                                    weizhi_type = true;
                                    tv_qiandao.setText("已签到");
                                    ll_qiandao_pd.setVisibility(View.VISIBLE);
                                    System.out.println("当前用户已签到=================================");
                                    Toast.makeText(DianZiPiaoActivity.this, "已签到", Toast.LENGTH_SHORT).show();
                                    // TODO: 2018/4/25 签到成功
                                    finish(); //改写的
//									Intent intent = new Intent(DianZiPiaoActivity.this,TishiQianDaoOKActivity.class);
//									startActivity(intent);
                                    //									   }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {

                            super.onFailure(arg0, arg1);
                            progress.CloseProgress();
                            System.out.println("异常=================================" + arg1);
                            //						Toast.makeText(DianZiPiaoActivity.this, "异常", 200).show();
                        }
                    }, null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    /**
     * 根据交易号显示订单
     */
    XiangqingData md;
    OrderBean mb;

    private void load_list() {
        progress.CreateProgress();
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_order_trade_model?trade_no=" + getIntent().getStringExtra("trade_no") + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        System.out.println("=========数据接口============" + arg1);
                        try {
                            JSONObject object = new JSONObject(arg1);
                            String status = object.getString("status");
                            String info = object.getString("info");
                            if (status.equals("y")) {
                                JSONArray jsonArray = object.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    md = new XiangqingData();
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    //								md.setId(obj.getString("id"));
                                    md.user_name = obj.getString("user_name");
                                    md.address = obj.getString("address");
                                    String order_goods = obj.getString("order_goods");
                                    JSONArray ja = new JSONArray(order_goods);
                                    for (int j = 0; j < ja.length(); j++) {
                                        JSONObject jo = ja.getJSONObject(j);
                                        //									mb.setImg_url(jo.getString("img_url"));
                                        md.article_title = jo.getString("article_title");
                                        md.sell_price = jo.getString("sell_price");
                                        md.start_time = jo.getString("start_time");
                                        md.end_time = jo.getString("end_time");
                                        //									mb.setMarket_price(jo.getString("market_price"));
                                        //									mb.setReal_price(jo.getString("real_price"));
                                        //									mb.setQuantity(jo.getInt("quantity"));
                                        //									mb.setArticle_id(jo.getString("article_id"));
                                    }
                                }
                            } else {
                                progress.CloseProgress();
                            }

                            if (md.getSell_price().equals("0")) {
                                tv_feiyong.setText("费用：" + "免费");
                            } else if (mb.getSell_price().equals("0.0")) {
                                tv_feiyong.setText("费用：" + "免费");
                            } else {
                                tv_feiyong.setText("费用：￥" + md.getSell_price());
                            }
                            String real_name = getIntent().getStringExtra("real_name");
                            String mobile = getIntent().getStringExtra("mobile");

                            tv_name.setText(real_name + "(" + mobile + ")");

                            tv_ware_name.setText(md.getArticle_title());
                            tv_time.setText("开始时间：" + md.getStart_time().subSequence(0, 16));
                            tv_ent_time.setText("结束时间：" + md.getEnd_time().subSequence(0, 16));
                            if (md.getAddress().equals("")) {
                                View vi_dizhi = (View) findViewById(R.id.ll_dizhi);
                                vi_dizhi.setVisibility(View.GONE);
                                tv_dizhi.setVisibility(View.GONE);
                            } else {
                                tv_dizhi.setText("地点：" + md.getAddress());
                            }
                            progress.CloseProgress();
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }

                }, DianZiPiaoActivity.this);
    }


    /**
     * 判断是否签到
     */
    //	private void panduanqiandao() {
    //		try {
    ////			progress.CreateProgress();
    //			String id = getIntent().getStringExtra("id");
    //		   AsyncHttp.get(URLConstants.REALM_NAME_LL
    //				+ "/exists_order_signup?mobile="+user_name+"&article_id="+id+"",
    //				new AsyncHttpResponseHandler() {
    //					@Override
    //					public void onSuccess(int arg0, String arg1) {
    //						super.onSuccess(arg0, arg1);
    //						try {
    //							JSONObject object = new JSONObject(arg1);
    //							System.out.println("签到查询================================="+arg1);
    //							  String status = object.getString("status");
    //							    String info = object.getString("info");
    //							    if (status.equals("y")) {
    //							    	   progress.CloseProgress();
    //							    	   erweima_type = 1;
    //							    	   tv_qiandao.setText("签到成功");
    ////							    	   Toast.makeText(DianZiPiaoActivity.this, info, 200).show();
    //							    }else {
    //							    	progress.CloseProgress();
    //							    	 erweima_type = 2;
    //							    	tv_qiandao.setText("报名成功");
    ////									Toast.makeText(DianZiPiaoActivity.this, info, 200).show();
    //								}
    //						} catch (JSONException e) {
    //							e.printStackTrace();
    //						}
    //					}
    //
    //					@Override
    //					public void onFailure(Throwable arg0, String arg1) {
    //
    //						super.onFailure(arg0, arg1);
    //						progress.CloseProgress();
    //						System.out.println("异常================================="+arg1);
    ////						Toast.makeText(DianZiPiaoActivity.this, "网络超时异常", 200).show();
    //					}
    //				}, null);
    //
    //		} catch (Exception e) {
    //
    //			e.printStackTrace();
    //		}
    //	}


}
