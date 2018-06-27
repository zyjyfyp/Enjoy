package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.GuigeData;
import com.yunsen.enjoy.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author http://blog.csdn.net/finddreams
 * @Description:gridview的Adapter
 */
public class GuigeListAdapter extends BaseAdapter {
    private Context mContext;
    private List<GuigeData> list;
    private LayoutInflater inflater;
    private ArrayList data;
    private ArrayList data1;
    private ArrayList data2;

    public GuigeListAdapter(Context context, List<GuigeData> list,
                            ArrayList data, ArrayList data1, ArrayList data2) {

        this.data = data;
        this.data1 = data1;
        this.data2 = data2;
        this.list = list;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        // System.out.println("=====42====================="+list.size());
    }

    @Override
    public int getCount() {

        //		Log.i("data", "=============" + list.size());
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup patent) {
        try {
            System.out.println("=====51=====================");

            convertView = inflater.inflate(R.layout.guige_item, null);
            LinearLayout addview = (LinearLayout) convertView
                    .findViewById(R.id.addview);
            TextView tv_zhuti = (TextView) convertView
                    .findViewById(R.id.tv_zhuti);
            String zhou = list.get(position).getTitle();
            tv_zhuti.setText(list.get(position).getTitle());
            // System.out.println("=====6====================="+zhou);


            MyGridView gridView = (MyGridView) convertView.findViewById(R.id.gridView);
            System.out.println("=====00=====================" + list.size());
            //			for (int i = 0; i < list.size(); i++) {
            //				System.out.println("=====6=====================" + i);
            //				switch (i) {
            //				case 0:
            GouwucheAdapter MyAdapter = new GouwucheAdapter(data,
                    mContext);
            gridView.setAdapter(MyAdapter);
            //					System.out.println("=====0=====================");
            //					break;
            //				case 1:
            //					GouwucheAdapter MyAdapterl = new GouwucheAdapter(data1,
            //							mContext);
            //					gridView.setAdapter(MyAdapterl);
            //					System.out.println("=====1=====================");
            //					break;
            //				case 2:
            //					GouwucheAdapter MyAdapterll = new GouwucheAdapter(data2,
            //							mContext);
            //					gridView.setAdapter(MyAdapterll);
            //					System.out.println("=====2=====================");
            //					break;
            //
            //				default:
            //					break;
            //				}
            //			}
            addview.removeAllViews();

            // for (int i = 0; i < list.get(position).getList().size(); i++) {
            // System.out.println("=====600=====================");
            // String zhi = list.get(position).getList().get(i).getTitle();
            // // System.out.println("=====22====================="+zhi );
            // //
            // System.out.println("=====61====================="+list.get(position).getList().size());
            // //
            // System.out.println("=====62====================="+data.size());
            // View vi =
            // LayoutInflater.from(mContext).inflate(layout.guige_item_ll,
            // null);
            // MyGridView gridView=(MyGridView) vi.findViewById(R.id.gridView);
            //
            // // String title = list.get(position).getList().get(i).getTitle();
            // // System.out.println("=====22====================="+title);
            // // ArrayList data =new ArrayList();
            // // data.add(title);
            //
            // GouwucheAdapter MyAdapter=new GouwucheAdapter(data,mContext);
            // gridView.setAdapter(MyAdapter);
            //
            // addview.addView(vi);
            // }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return convertView;
    }

    // public View getView(int position, View convertView, ViewGroup parent) {
    // if (convertView == null) {
    // convertView =
    // LayoutInflater.from(mContext).inflate(R.layout.gridview_item0, parent,
    // false);
    // System.out.println("=====6=====================");
    // }
    //
    // // ImageLoader imageLoader=ImageLoader.getInstance();
    // // imageLoader.displayImage((String)
    // Config.URL_IMG+datatupian.get(position),iv);
    // return convertView;
    // }

}
