package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.JuTuanGouData;

import java.util.ArrayList;

public class MyJutuanMxAdapter extends BaseAdapter {

	private ArrayList<JuTuanGouData> list;
	private Context context;

	public MyJutuanMxAdapter(ArrayList<JuTuanGouData> list, Context context) {

		this.context = context;
		this.list = list;
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
			// convertView =
			// LinearLayout.inflate(context,R.layout.listitem_xsgy, null);
			convertView = LinearLayout.inflate(context,
					R.layout.listitem_my_jufen, null);
		}

		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);
		TextView tv_name = (TextView) convertView
				.findViewById(R.id.tv_ware_name);
		// TextView tv_ware_time = (TextView)
		// convertView.findViewById(R.id.tv_ware_time);
		// TextView tv_price = (TextView)
		// convertView.findViewById(R.id.tv_ware_price);
		// TextView tv_total = (TextView)
		// convertView.findViewById(R.id.tv_ware_total);

		String real_name = list.get(position).getForeman_name();//
		System.out.println("real_name=====================" + real_name);
		if (!real_name.equals("")) {
			tv_name.setText(real_name + "（"
					+ list.get(position).getForeman_name() + ")");
		} else {
			tv_name.setText("匿名用户（" + list.get(position).getForeman_name()
					+ ")");
		}

		// if (real_name.equals("")){
		// System.out.println("real_name11====================="+real_name);
		// tv_name.setText("匿名用户（"+list.get(position).mobile+")");
		// }

		return convertView;
	}
}
