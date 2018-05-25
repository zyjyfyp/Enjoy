package com.yunsen.enjoy.activity.pay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.JuTuanGouData;


public class ZhiFufangshiAdapter extends BaseAdapter {

	private Context mContext;
	private java.util.List<JuTuanGouData> List;
	private LayoutInflater mInflater;
	private int clickTemp = 0;

	public ZhiFufangshiAdapter(Context context, java.util.List<JuTuanGouData> list) {
		this.List = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (List.size() < 1) {
			return 0;
		} else {
			return List.size();

		}
	}

	@Override
	public Object getItem(int position) {

		return List.get(position);
	}

	public void setSeclection(int position) {
		clickTemp = position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.zhifufangshi_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.iv_tubiao);
			holder.text = (TextView) convertView.findViewById(R.id.edt_zhifu);
			holder.cd = (CheckBox) convertView.findViewById(R.id.ck_xuanzhe);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// holder.img.setImageResource(dataList.get(position));
		// holder.text.setText("第" + position + "项");
		// clickTempll = position;

		try {

			System.out.println("position======================" + position);
			// if (position == 0) {
			// holder.cd.setChecked(true);
			// }
			int monney = List.get(position).getExpress_fee();
			if (monney == 0) {
				holder.text.setText(List.get(position).getTitle() + "(" + "免邮"
						+ ")");
			} else {
				String price = String.valueOf(monney);
                holder.text.setText(List.get(position).getTitle() + "(" + "¥"
                        + price + ")");
			}

			// if (clickTemp == position) {
			// holder.img.setBackgroundResource(R.drawable.ck_login);
			// } else {
			// holder.img.setBackgroundResource(R.drawable.ck_login_click);
			// }

			holder.cd.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					// int position = List.get(position).get;

					holder.cd.setChecked(true);

				}
			});

		} catch (Exception e) {

			e.printStackTrace();
		}

		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView text;
		CheckBox cd;
		RadioButton radioButton;
	}
}
