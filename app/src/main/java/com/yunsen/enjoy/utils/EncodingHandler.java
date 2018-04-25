package com.yunsen.enjoy.utils;

import android.app.Activity;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

public final class EncodingHandler {

	private static final int BLACK = 0xff000000;
	private static Activity activity;

	public static Bitmap createQRCode(String str, int widthHeight)
			throws WriterException {

		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		// 设置二维码编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		BitMatrix marBitMatrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, widthHeight, widthHeight);
		int _width = marBitMatrix.getWidth();
		int _height = marBitMatrix.getHeight();
		// 根据宽度和高度计算像素大小；
		int[] pixels = new int[_height * _width];

		// 描点
		for (int y = 0; y < _height; y++) {
			for (int x = 0; x < _width; x++) {
				if (marBitMatrix.get(x, y)) {
					pixels[y * _width + x] = BLACK;
				}
			}
		}

		// 生成位图
		Bitmap mBitmap = Bitmap.createBitmap(_width, _height,
				Bitmap.Config.ARGB_8888);
		mBitmap.setPixels(pixels, 0, _width, 0, 0, _width, _height);

		// Intent intent13 = new Intent(activity, Webview1.class);
		// activity.startActivity(intent13);
		return mBitmap;
	}

}
