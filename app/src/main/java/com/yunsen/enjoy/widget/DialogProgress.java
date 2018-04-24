package com.yunsen.enjoy.widget;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogProgress {

	private Context context;

	private ProgressDialog progressDialog;

	public DialogProgress(Context context) {
		this.context = context;
	}

	public void CreateProgress() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("努力加载中...");
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(true);
		progressDialog.show();
	}

	public void CloseProgress() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

}
