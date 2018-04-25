package com.yunsen.enjoy.activity.mine.adapter;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.androidquery.AQuery;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.JuTuanGouData;
import com.yunsen.enjoy.ui.photoview.RoundImageView;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.Utils;

import java.io.File;
import java.util.ArrayList;

public class TuanchengyuanAdapterll extends BaseAdapter {

	private ArrayList<JuTuanGouData> list;
	private ArrayList<Integer> list_num;
	// private ArrayList datadz;
	private ArrayList datadz2;
	private Context context;
	public AQuery mAq;
	String user_id, avatar;
	ImageView image;
	String jutuan_tx;
	int num;
	String img_url;
	Bitmap bitMap_tx;
	String data_tx;


	public TuanchengyuanAdapterll(ArrayList datadz2, Context context) {
		this.context = context;
		this.datadz2 = datadz2;
		mAq = new AQuery(context);
	}

	public int getCount() {

		return datadz2.size();
	}

	public Object getItem(int position) {

		return datadz2.get(position);
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LinearLayout.inflate(context,
					R.layout.tuan_chengyuan_itemll, null);
		}
		RoundImageView networkImage = (RoundImageView) convertView
				.findViewById(R.id.roundImage_network);
		image = (ImageView) convertView.findViewById(R.id.img_ware);
		LinearLayout ll_touxiang = (LinearLayout) convertView
				.findViewById(R.id.ll_touxiang);
		System.out.println("position=================" + position);
		try {
			num = position;
			System.out.println("datadz2================="
					+ (String) datadz2.get(position));

			if (!datadz2.get(position).equals("")) {
				// circleImageView.setVisibility(View.VISIBLE);
				// image.setVisibility(View.GONE);
				data_tx = (String) datadz2.get(position);
				img_url = "http://mobile.ju918.com/templates/mobile/images/leader-user.png";// 默认图片
				if (data_tx.contains("http")) {
					img_url = (String) datadz2.get(position);
				} else if (data_tx.contains("upload")) {
					img_url = "http://ju918.com"
							+ (String) datadz2.get(position);
				}
				mAq.id(image).image(img_url);
			} else {
				// circleImageView.setVisibility(View.GONE);
				// image.setVisibility(View.VISIBLE);
				String img_url = "http://ju918.com/images/ysj_tx.png";
				mAq.id(image).image(img_url);
				System.out.println("1=================");
			}

			// mImageLoader = initImageLoader(context, mImageLoader, "test");
			// mImageLoader.displayImage(RealmName.REALM_NAME_FTP
			// +datadz2.get(position),networkImage);

			// String data_tx =
			// "http://wx.qlogo.cn/mmopen/Zw5SzXToEzuCtHFRb2IVVZemJzJx4cLibMpDIE2y4kA1lgPfbhe2rO851s5G72B2U1Wz6cGe8Eb7B4AbtibiaUaSRBeH1XqqMiam/0";
			// String data_tx = (String) datadz2.get(position);
			// Bitmap bitmap = BitUtil.stringtoBitmap(data_tx);
			// bitmap = Utils.toRoundBitmap(bitmap, null); // 这个时候的图片已经被处理成圆形的了
			// image.setImageBitmap(bitmap);

			// System.out.println("================="+RealmName.REALM_NAME_HTTP+datadz2.get(position));

			// // if (position == 1) {
			// // System.out.println("position=========22========"+position);
			// //// if (!avatar.equals("")) {
			// //// mImageLoader = initImageLoader(context, mImageLoader,
			// "test");
			// //// mImageLoader.displayImage(RealmName.REALM_NAME_FTP
			// +avatar,networkImage);
			// //// }
			// // if (!datadz2.get(position).equals("")) {
			// // String data_tx = (String) datadz2.get(position);
			// // if (data_tx.contains("http")) {
			// //// ll_touxiang.setVisibility(View.VISIBLE);
			// //// networkImage.setVisibility(View.GONE);
			// //// Bitmap bitmap = BitUtil.stringtoBitmap(data_tx);
			// //// bitmap = Utils.toRoundBitmap(bitmap, null); //
			// 这个时候的图片已经被处理成圆形的了
			// //// image.setImageBitmap(bitmap);
			// //
			// // mImageLoader = initImageLoader(context, mImageLoader, "test");
			// // mImageLoader.displayImage(data_tx,networkImage);
			// // }else if (!datadz2.get(position).equals("")){
			// // mImageLoader = initImageLoader(context, mImageLoader, "test");
			// // mImageLoader.displayImage(RealmName.REALM_NAME_FTP
			// +datadz2.get(position),networkImage);
			// // }
			// // }else {
			// //
			// // }
			// // }
			//
			// else {
			// if (!datadz2.get(position).equals("")) {
			// String data_tx = (String) datadz2.get(position);
			// if (data_tx.contains("http")) {
			// // ll_touxiang.setVisibility(View.VISIBLE);
			// // networkImage.setVisibility(View.GONE);
			// // Bitmap bitmap = BitUtil.stringtoBitmap(data_tx);
			// // bitmap = Utils.toRoundBitmap(bitmap, null); //
			// 这个时候的图片已经被处理成圆形的了
			// // image.setImageBitmap(bitmap);
			//
			// mImageLoader = initImageLoader(context, mImageLoader, "test");
			// mImageLoader.displayImage(data_tx,networkImage);
			// }else if (datadz2.get(position).equals("")){
			// ll_touxiang.setVisibility(View.VISIBLE);
			// networkImage.setVisibility(View.GONE);
			// image.setBackgroundResource(R.drawable.ysj_tx);
			// }else {
			// mImageLoader = initImageLoader(context, mImageLoader, "test");
			// mImageLoader.displayImage(RealmName.REALM_NAME_FTP
			// +datadz2.get(position),networkImage);
			// }
			// }else {
			//
			// }
			// }

		} catch (Exception e) {

			e.printStackTrace();
		}

		System.out
				.println("==============================================================");
		return convertView;
	}

	Runnable getPicByUrl = new Runnable() {
		@Override
		public void run() {
			try {
				// String img_url2 =
				// "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
				String img_url2 = URLConstants.REALM_NAME_HTTP
						+ (String) datadz2.get(num);
				System.out.println("img_url2=====1=========" + img_url2);
				Bitmap bmp = GetImgUtil.getImage(img_url);// BitmapFactory：图片工厂！
				bitMap_tx = Utils.toRoundBitmap(bmp, null);// 这个时候的图片已经被处理成圆形的了
				System.out.println("bitMap1==============" + bitMap_tx);
				// image.setImageBitmap(bitMap_tx);
			} catch (Exception e) {
				Log.i("ggggg", e.getMessage());
			}
		}
	};





	private int getMemoryCacheSize(Context context) {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
			// limit
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}
		return memoryCacheSize;
	}

	/**
	 *
	 * @param x
	 *            图像的宽度
	 * @param y
	 *            图像的高度
	 * @param image
	 *            源图片
	 * @param outerRadiusRat
	 *            圆角的大小
	 * @return 圆角图片
	 */
	Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat) {
		// 根据源文件新建一个darwable对象
		Drawable imageDrawable = new BitmapDrawable(image);

		// 新建一个新的输出图片
		Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		// 新建一个矩形
		RectF outerRect = new RectF(50, 50, x, y);

		// 产生一个红色的圆角矩形
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

		// 将源图片绘制到这个圆角矩形上
		// paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		imageDrawable.setBounds(0, 0, x, y);
		canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
		imageDrawable.draw(canvas);
		canvas.restore();

		return output;
	}
}
