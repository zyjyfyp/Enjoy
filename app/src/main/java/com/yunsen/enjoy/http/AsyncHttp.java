package com.yunsen.enjoy.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AsyncHttp {
	private static AsyncHttpClient client = new AsyncHttpClient();
	@SuppressWarnings("unused")
	private static Context context;

	public static void get(String url, AsyncHttpResponseHandler handler,
                           Context con) {
		context = con;
		Log.i("hck", url);
		client.setUserAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		client.setTimeout(15000);
		client.get(url, handler);

	}

	public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler handler, Context con) {
		context = con;
		client.setUserAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		client.setTimeout(15000);
		client.post(url, params, handler);
	}

	/**
	 * post 提交的数据
	 *
	 * @param url
	 * @param params
	 * @param handler
	 * @param
	 */
	public static void post_1(final String url,
							  final Map<String, String> params,
							  final AsyncHttpResponseHandler handler) {
		client.post(processParams(params, url), handler);
		// new Thread(){
		// @Override
		// public void run() {
		//
		// super.run();
		// PostBean bean = new PostBean();
		// bean.setUrl(url);
		// bean.setParams(processParams(params, url));
		// bean.setHandler(handler);
		// Message msg = new Message();
		// msg.what = 1;
		// msg.obj = bean;
		// childHandler.sendMessage(msg);
		//
		// }
		// }.start();

	}

	private static Handler childHandler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1:
					PostBean bean = (PostBean) msg.obj;
					Log.i("hck", bean.getUrl());
					client.post(bean.getUrl(), bean.getParams(), bean.getHandler());
					break;

				default:
					break;
			}
		};
	};
	/**
	 * 处理请求参数(封装了参数的加密处理)
	 *
	 * @param params
	 * @return
	 */
	private final static String APPKEY = "0762222540";
	private final static String URL_SECRET = "6893599ba3cf4c0f9ae7f589495f870e";

	private static String processParams(Map<String, String> params, String url) {
		RequestParams request = new RequestParams();
		Set<String> keySet = params.keySet();
		Iterator<String> keyIterable = keySet.iterator();
		ArrayList<String> keys = new ArrayList<String>();
		while (keyIterable.hasNext()) {
			keys.add(keyIterable.next());
		}
		Collections.sort(keys);// 访问参数升序排列
		StringBuilder builder = new StringBuilder();
		builder.append(APPKEY);
		for (int i = 0; i < params.size(); i++) {
			String childKey = keys.get(i);
			String childValue = params.get(childKey);
			request.put(childKey, childValue);
			builder.append(childKey + childValue);
		}
		builder.append(URL_SECRET);
		System.out.println("加密前:" + builder.toString());

		String sign = SHA1.getDigestOfString(builder.toString()).toUpperCase();
		System.out.println("sign:" + sign);
		request.put("sign", sign);
		request.put("appkey", APPKEY);
		/**
		 * 地址拼接
		 */
		params.put("sign", sign);
		params.put("appkey", APPKEY);
		Set<String> keySet1 = params.keySet();
		Iterator<String> keyIterable1 = keySet1.iterator();
		ArrayList<String> keys1 = new ArrayList<String>();
		while (keyIterable1.hasNext()) {
			keys1.add(keyIterable1.next());
		}
		Collections.sort(keys1);// 访问参数升序排列
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			String childKey = keys1.get(i);
			String childValue = params.get(childKey);
			request.put(childKey, childValue);
			if (i == 0) {
				temp.append("?" + childKey + "=" + childValue);
			} else {
				temp.append("&" + childKey + "=" + childValue);
			}

		}
		System.out.println("地址" + url + temp.toString());
		return url + temp.toString();
		// return request;
	}

	@SuppressWarnings("unused")
	private void debug() {
		// 地址拼接

	}

}
