package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.MyAssetsActivity;
import com.yunsen.enjoy.model.MyAssetsBean;

import java.util.ArrayList;

public class MyAssetsAdapter extends BaseAdapter {

	private ArrayList<MyAssetsBean> list;
	private Context context;



	public MyAssetsAdapter(ArrayList<MyAssetsBean> list, Context context) {

		this.context = context;
		this.list = list;
	}

	public void putData(ArrayList<MyAssetsBean> list){
		this.list = list;
		this.notifyDataSetChanged();
	}

	public int getCount() {

		return list.size();
	}

	public Object getItem(int position) {

		return position;
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			//			convertView = LinearLayout.inflate(context,R.layout.listitem_xsgy, null);
			convertView = LinearLayout.inflate(context, R.layout.listitem_myasste, null);
		}
		System.out.println("=====================二级值16");
		//		TextView tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
		TextView tv_income = (TextView) convertView.findViewById(R.id.tv_income);
		TextView tv_add_time = (TextView) convertView.findViewById(R.id.tv_add_time);
		TextView tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);

		System.out.println("=====================二级值17");
		//		if (list.get(position).remark.contains("扣取账户金额")) {
		//			tv_income.setText("-"+list.get(position).expense+"元");
		//		}else if (list.get(position).remark.contains("积分")) {
		//			tv_income.setText("+"+list.get(position).income+"元");
		//		}else{
		//			tv_income.setText("+"+list.get(position).income+"元");
		//		}

		if (MyAssetsActivity.fund_id.equals("2")) {
			if (!list.get(position).expense.equals("")){
				tv_income.setText("-"+list.get(position).expense+"");
			}else if (!list.get(position).income.equals("")){
				tv_income.setText("+"+list.get(position).income+"");
			}
		}else {
			if (!list.get(position).expense.equals("")){
				tv_income.setText("-"+list.get(position).expense+"元");
			}else if (!list.get(position).income.equals("")){
				tv_income.setText("+"+list.get(position).income+"元");
			}
		}


		//		if (list.get(position).remark.contains("扣取账户金额")) {
		//			tv_income.setText("-"+list.get(position).expense+"元");
		//		}else if (list.get(position).remark.contains("余额支付，退款")){
		//			tv_income.setText("+"+list.get(position).income+"元");
		//		}else  if (list.get(position).remark.contains("养老金")){
		//			tv_income.setText("+"+list.get(position).income+"元");
		//		}else if (list.get(position).remark.contains("积分")){
		//			tv_income.setText("+"+list.get(position).balance+"分");
		//		}else if (list.get(position).remark.contains("红包")){
		//			tv_income.setText("+"+list.get(position).balance+"元");
		//		}else {
		//			tv_income.setText("+"+list.get(position).income+"元");
		//		}

		tv_remark.setText(list.get(position).remark);
		//		tv_user_name.setText("(下单人:"+list.get(position).user_name+")");
		tv_add_time.setText(list.get(position).add_time);


		return convertView;
	}
}
