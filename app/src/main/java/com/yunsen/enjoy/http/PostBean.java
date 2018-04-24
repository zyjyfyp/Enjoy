package com.yunsen.enjoy.http;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PostBean implements Serializable {
	private String url;
	private RequestParams params;
	private AsyncHttpResponseHandler handler;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public RequestParams getParams() {
		return params;
	}
	public void setParams(RequestParams params) {
		this.params = params;
	}
	public AsyncHttpResponseHandler getHandler() {
		return handler;
	}
	public void setHandler(AsyncHttpResponseHandler handler) {
		this.handler = handler;
	}
	
}
