package com.yunsen.enjoy.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.utils.BitmapUtil;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.Utils;
import com.yunsen.enjoy.widget.DialogProgress;

import java.io.ByteArrayOutputStream;

/**
 * 分享
 *
 * @author Administrator
 *
 */
public class DBFengXiangActivity extends AppCompatActivity implements OnClickListener {

	private ImageView iv_fanhui;
	private TextView tv_xiabu;
	EditText ra4;
	String check = "0";
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private ImageButton btn_wechat;
	String img_url,data;
	protected static Uri tempUri;
	private IWXAPI api;
	//	private View btn_sms;
//	private View btn_wx_friend;
	public static boolean fanhui_type = false;
	private ImageButton img_btn_tencent,btn_wx_friend,btn_sms;
	String user_id;
	String unionid;
	Bitmap thumb;
	String title = "";
	String subtitle = "";
	String fx_shuzi;
	String data_pt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fenxiang_time);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(DBFengXiangActivity.this);
		fanhui_type = true;
//		if (JuTuanGouXq2Activity.fanhui_type == true) {
//			fanhui_type = true;
//		}
		new Thread(getPicByUrl).start();
		intren();

	}

	public void intren() {
		try {
			btn_wechat = (ImageButton) findViewById(R.id.img_btn_wechat);
			btn_wx_friend = (ImageButton) findViewById(R.id.img_btn_wx_friend);
			btn_sms = (ImageButton) findViewById(R.id.img_btn_sms);
			img_btn_tencent = (ImageButton) findViewById(R.id.img_btn_tencent);
			Button btn_holdr = (Button) findViewById(R.id.btn_holdr);
			btn_holdr.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					finish();
				}
			});

			// 新浪
			img_btn_tencent.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();
//					con(19, 1);
					Toast.makeText(DBFengXiangActivity.this, "功能还未开发，敬请期待", Toast.LENGTH_SHORT).show();
				}
			});

			// 微信
			btn_wechat.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// progress.CreateProgress();
					finish();
					con(16, 1);
				}
			});
			// 朋友圈
			btn_wx_friend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					finish();
					con(17, 1);
				}
			});

			// 短信
			btn_sms.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					finish();
					con(18, 0);
				}
			});
//		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
//		tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
//		iv_fanhui.setOnClickListener(this);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void con(final int index, int type) {
		try {
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String user_id = spPreferences.getString("user_id", "");
			System.out.println("user_id==========" + user_id);

			SharedPreferences spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
//			SharedPreference spPreferences_tishi = getSharedPreferences("longuserset_tishi", MODE_PRIVATE);
			SharedPreferences spPreferences = getSharedPreferences("longuserset_ptye", MODE_PRIVATE);
			String ptye = spPreferences.getString("ptye", "");
//			String qq = spPreferences_tishi.getString("qq", "");
			System.out.println("ptye==========" + ptye);
			System.out.println("user_id==========" + user_id);
			if (ptye != null) {
				if (ptye.equals("weixin")) {
					unionid = spPreferences_login.getString("unionid", "");
				}else {
					unionid = spPreferences_login.getString("unionid", "");
				}
			}else {
				unionid = "";
			}
			System.out.println("unionid==========" + unionid);

			String sp_id = getIntent().getStringExtra("sp_id");//商品分享
			String pt_id = getIntent().getStringExtra("pt_id");//拼团分享
			String ct_id = getIntent().getStringExtra("ct_id");//参团分享
			String list_id = getIntent().getStringExtra("list_id");//新闻分享
			String activity_id = getIntent().getStringExtra("activity_id");//活动分享
//			String img_url = getIntent().getStringExtra("img_url");
			System.out.println("unionid==========" + unionid);
			System.out.println("sp_id==========" + sp_id);
			System.out.println("pt_id==========" + pt_id);
			System.out.println("ct_id==========" + ct_id);
			System.out.println("list_id==========" + list_id);
			System.out.println("activity_id==========" + activity_id);
//			System.out.println("img_url==========" + img_url);
			fx_shuzi = getIntent().getStringExtra("fx_shuzi");//
			System.out.println("fx_shuzi============================" + fx_shuzi);

			String company_id = getIntent().getStringExtra("company_id");
			System.out.println("company_id==========" + company_id);

			title = getIntent().getStringExtra("title");
			subtitle = getIntent().getStringExtra("subtitle");
			System.out.println("title==========" + title);
			System.out.println("subtitle==========" + subtitle);

			if (subtitle == null) {
				subtitle = "";
			}

			if (sp_id != null) {//商品分享
//				String data = "http://183.62.138.31:1010/mobile/goods/show-" + sp_id + ".html?user_id="+user_id+"&user_name="+user_name+"";
				String data_sp = subtitle + URLConstants.REALM_NAME_FX+"/goods/show-" + sp_id + ".html?cid="+company_id+"&unionid="+unionid+"&shareid="+user_id+"&from=android";
				System.out.println("分享11======================" + data_sp);
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_sp);
				}else if (index == 17) {
					System.out.println("==========" + 17);
					softshareWxFriend(data_sp);
				}else if (index == 18) {
					System.out.println("==========" + 18);
					Uri uri = Uri.parse("smsto:");
					Intent it = new Intent(Intent.ACTION_SENDTO, uri);
					it.putExtra("sms_body", data_sp);
					startActivity(it);
				}
			}else if (pt_id != null) {//拼团分享
//				String data_sp = subtitle + URLConstants.REALM_NAME_FX+"/goods/show-" + sp_id + ".html?cid="+company_id+"&unionid="+unionid+"&shareid="+user_id+"&from=android";
				String data_pt = subtitle + URLConstants.REALM_NAME_FX+"/"+fx_shuzi+"/show-"+pt_id+".html?cid="+company_id+"&unionid="+unionid+"&shareid="+user_id+"&from=android";
				System.out.println("分享22======================" + data_pt);
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_pt);
				}else if (index == 17) {
					System.out.println("==========" + 17);
					softshareWxFriend(data_pt);
				}else if (index == 18) {
					System.out.println("==========" + 18);
					Uri uri = Uri.parse("smsto:");
					Intent it = new Intent(Intent.ACTION_SENDTO, uri);
					it.putExtra("sms_body", data_pt);
					startActivity(it);
				}
			}else if (ct_id != null) {//参团分享
				String data_ct = subtitle + URLConstants.REALM_NAME_FX+"/"+fx_shuzi+"/join-"+ct_id+".html?cid="+company_id+"&unionid="+unionid+"&shareid="+user_id+"&from=android";
				System.out.println("分享33======================" + data_ct);
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_ct);
				}else if (index == 17) {
					System.out.println("==========" + 17);
					softshareWxFriend(data_ct);
				}else if (index == 18) {
					System.out.println("==========" + 18);
					Uri uri = Uri.parse("smsto:");
					Intent it = new Intent(Intent.ACTION_SENDTO, uri);
					it.putExtra("sms_body", data_ct);
					startActivity(it);
				}
			}else if (activity_id != null) {//活动分享
				String data_hdfx = subtitle + URLConstants.REALM_NAME_FX + "/signup/show/"+activity_id+".html?unionid="+unionid+"&shareid="+user_id+"&from=android";
//				                                  http://mobile.zams.cn/signup/show/8941.html?unionid=o-Qe5v63lGJMH3R_zxgK_eSMRz3s&shareid=5721&from=singlemessage
				System.out.println("分享44======================" + data_hdfx);
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_hdfx);
				}else if (index == 17) {
					System.out.println("==========" + 17);
					softshareWxFriend(data_hdfx);
				}else if (index == 18) {
					System.out.println("==========" + 18);
					Uri uri = Uri.parse("smsto:");
					Intent it = new Intent(Intent.ACTION_SENDTO, uri);
					it.putExtra("sms_body", data_hdfx);
					startActivity(it);
				}
			}else if (list_id != null) {//新闻分享
				String data_xw = subtitle + URLConstants.REALM_NAME_FX + "/mobile/news/conent-"+list_id+".html?unionid="+unionid+"&shareid="+user_id+"&from=android";
				System.out.println("分享55======================" + data_xw);
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_xw);
				}else if (index == 17) {
					System.out.println("==========" + 17);
					softshareWxFriend(data_xw);
				}else if (index == 18) {
					System.out.println("==========" + 18);
					Uri uri = Uri.parse("smsto:");
					Intent it = new Intent(Intent.ACTION_SENDTO, uri);
					it.putExtra("sms_body", data_xw);
					startActivity(it);
				}
			}
			//http://183.62.138.31:1010/mobile/news/conent-103.html?cid=0&unionid=&openid=&shareid=7260&from=android
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 微信分享
	 *
	 * @param text
	 */
	private void softshareWxChat(String text) {
		String temp[] = text.split("http");
		api = WXAPIFactory.createWXAPI(DBFengXiangActivity.this, Constants.APP_ID,false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = temp[0];//Bitmap
		System.out.println("thumb=============="+thumb);
		String img_url = getIntent().getStringExtra("img_url");
		System.out.println("img_url==========" + img_url);
		if (img_url.equals("")) {
			Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.drawable.app_zams);
			msg.thumbData = Utils.bmpToByteArray(thumb, true);// 设置缩略图
		}else if (thumb == null) {
//			msg.thumbData = Util.bmpToByteArray(thumb, true);// 设置缩略图
			Bitmap thumb = BitmapFactory.decodeResource(DBFengXiangActivity.this.getResources(),R.drawable.app_zams);
			msg.thumbData = Utils.bmpToByteArray(thumb, true);// 设置缩略图
		}else{
			Bitmap thumb_ll = BitmapUtil.comp(thumb);
			msg.thumbData = bitmap2Bytes(thumb_ll, 32768);
//			msg.thumbData = BitmapUtil.comp(thumb,32);
		}
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);
		System.out.println("微信分享好友-----" + flag);
		if (flag == false) {
//			WXMediaMessage msg1 = new WXMediaMessage(webpage);
//			msg1.title = title;
//			msg1.description = temp[0];//Bitmap
			Bitmap thumb1 = BitmapFactory.decodeResource(DBFengXiangActivity.this.getResources(),R.drawable.app_zams);
			msg.thumbData = Utils.bmpToByteArray(thumb1, true);// 设置缩略图
			SendMessageToWX.Req req1 = new SendMessageToWX.Req();
			req1.transaction = buildTransaction("webpage");
			req1.message = msg;
			req1.scene = SendMessageToWX.Req.WXSceneSession;
			boolean flag1 = api.sendReq(req1);
			System.out.println("微信分享好友----------" + flag1);
		}
	}


	/**
	 * 微信分享朋友圈
	 *
	 * @param text
	 */
	private void softshareWxFriend(String text) {
		String temp[] = text.split("http");
		api = WXAPIFactory.createWXAPI(DBFengXiangActivity.this, Constants.APP_ID, false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = temp[0];
		System.out.println("thumb=============="+thumb);
		String img_url = getIntent().getStringExtra("img_url");
		System.out.println("img_url==========" + img_url);
		if (img_url.equals("")) {
			Bitmap thumb = BitmapFactory.decodeResource(DBFengXiangActivity.this.getResources(),R.drawable.app_zams);
			msg.thumbData = Utils.bmpToByteArray(thumb, true);// 设置缩略图
		}else if (thumb == null) {
//			msg.thumbData = Util.bmpToByteArray(thumb, true);// 设置缩略图
			Bitmap thumb = BitmapFactory.decodeResource(DBFengXiangActivity.this.getResources(),R.drawable.app_zams);
			msg.thumbData = Utils.bmpToByteArray(thumb, true);// 设置缩略图
		}else {
			Bitmap thumb_ll = BitmapUtil.comp(thumb);
			msg.thumbData = bitmap2Bytes(thumb_ll, 32768);
//			msg.thumbData = bitmap2Bytes(thumb,32);
		}
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		boolean flag = api.sendReq(req);
//		System.out.println(flag + "----->" + msg.thumbData);
		System.out.println("微信分享朋友圈----->" + flag);
		if (flag == false) {
			Bitmap thumb = BitmapFactory.decodeResource(DBFengXiangActivity.this.getResources(),R.drawable.app_zams);
			msg.thumbData = Utils.bmpToByteArray(thumb, true);// 设置缩略图
			SendMessageToWX.Req req1 = new SendMessageToWX.Req();
			req1.transaction = buildTransaction("webpage");
			req1.message = msg;
			req1.scene = SendMessageToWX.Req.WXSceneTimeline;
			boolean flag1 = api.sendReq(req1);
			System.out.println("微信分享朋友圈---------->"+flag1);
		}
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	/**
	 * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
	 * @param bitmap
	 * @param maxkb
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
		int options = 100;
		while (output.toByteArray().length > maxkb&& options != 10) {
			output.reset(); //清空output
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
			options -= 10;
		}
		return output.toByteArray();
	}

	Runnable getPicByUrl = new Runnable() {
		@Override
		public void run() {
			try {
//					String img_url2 = "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
				String img_url = getIntent().getStringExtra("img_url");
				System.out.println("img_url==========" + img_url);
//					if (!img_url.equals("")) {
				String img_url2 = URLConstants.REALM_NAME_HTTP + img_url;
				System.out.println("img_url2=============="+img_url2);
				thumb = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
//					}else {
//						thumb = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_logn);
//					}
//					Bitmap bitMap_tx = Utils.toRoundBitmap(bmp,null);// 这个时候的图片已经被处理成圆形的了
//					System.out.println("bitMap_tx=============="+bitMap_tx);
				System.out.println("thumb=============="+thumb);

			} catch (Exception e) {
				Log.i("ggggg", e.getMessage());
			}
		}
	};

	@Override
	public void onClick(View v) {


		switch (v.getId()) {
			case R.id.iv_fanhui:
				finish();
				break;
			default:
				break;
		}
	}
}
