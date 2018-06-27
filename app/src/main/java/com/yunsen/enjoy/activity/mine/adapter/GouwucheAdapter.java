package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.JuJingCaiXqActivity;
import com.yunsen.enjoy.activity.mine.JuTuanGouXqActivity;
import com.yunsen.enjoy.activity.mine.WareInformationActivity;
import com.yunsen.enjoy.ui.BaseViewHolder;

import java.util.ArrayList;

/**
 * @author http://blog.csdn.net/finddreams
 * @Description:gridview的Adapter
 */
public class GouwucheAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList datatb1;
    private int clickTemp = -1;
    TextView tv;


    public GouwucheAdapter(ArrayList datatb1, Context mContext) {
        super();
        this.datatb1 = datatb1;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return datatb1.size();
    }

    @Override
    public Object getItem(int position) {
        return datatb1.get(position);
    }

    public void setSeclection(int position) {
        clickTemp = position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item0, parent, false);
            }

            tv = BaseViewHolder.get(convertView, R.id.btn_aaa1);
            tv.setText((String) datatb1.get(position));

            if (WareInformationActivity.spec_text_list == 1) {
                if (WareInformationActivity.spec_text.contains((String) datatb1.get(position))) {
                    tv.setTextColor(Color.RED);
                    tv.setBackgroundResource(R.drawable.juyunshang_bk);
                }
            } else if (JuJingCaiXqActivity.spec_text_list == 2) {
                if (JuJingCaiXqActivity.spec_text.contains((String) datatb1.get(position))) {
                    tv.setTextColor(Color.RED);
                    tv.setBackgroundResource(R.drawable.juyunshang_bk);
                }
            } else if (JuTuanGouXqActivity.spec_text_list == 3) {
                if (JuTuanGouXqActivity.spec_text.contains((String) datatb1.get(position))) {
                    tv.setTextColor(Color.RED);
                    tv.setBackgroundResource(R.drawable.juyunshang_bk);
                }
            } else {
                if (clickTemp == position) {
                    tv.setTextColor(Color.RED);
                    tv.setBackgroundResource(R.drawable.juyunshang_bk);
                } else {
                    tv.setTextColor(Color.GRAY);
                    tv.setBackgroundResource(R.drawable.zangfutiaoli);
                }
            }

            tv.setText((String) datatb1.get(position));
        } catch (Exception e) {

            e.printStackTrace();
        }
        return convertView;
    }

    public String getCurrentSpec() {
        if (clickTemp != -1 && clickTemp < datatb1.size()) {
            return (String) datatb1.get(clickTemp);
        }
        return "";
    }

}
