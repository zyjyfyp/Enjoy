package com.yunsen.enjoy.activity.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.MyOrderData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 *
 * @author Administrator
 */
public class MyOrderXqAdapter extends BaseAdapter {
    private Context context;
    private Intent intent;
    private List<MyOrderData> list;
    private LayoutInflater inflater;
    private Activity act;
    private Handler handler;
    public static AQuery mAq;
    public static String total_c, heji_zongjia;
    public static List<Double> list_monney = new ArrayList<Double>();

    public MyOrderXqAdapter(Context context, List<MyOrderData> list,
                            Handler handler) {
        this.list = list;
        this.context = context;
        this.handler = handler;
        this.inflater = LayoutInflater.from(context);
        mAq = new AQuery(context);
    }

    @Override
    public int getCount() {
        if (list.size() < 1) {

            return 0;
        } else {

            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    private final class ViewHolder {
        TextView tv_goods_title;
        TextView tv_market_price;
        TextView real_price;
        TextView quantity;
        ImageView tupian;//
        TextView lv_jijian, tv_sj_name;
        TextView tv_kukuang, tv_haoma;
        TextView tv_quxiao;//
        TextView tv_name;//
        TextView tv_addview;//
        TextView tv_order_bh;//
        TextView shanchu;// 删除
        TextView tv_order_cjsj;
        TextView tv_order_fksj, tv_order_chengjiao_time;
        TextView tv_heji;
        TextView tv_zongjia, tv_yunfei, tv_hongbao;
        LinearLayout lv_dingdanxq;
        LinearLayout ll_kedihongbao;
        LinearLayout expressLayout;
        TextView expressTv;
        LinearLayout addView;
        LinearLayout ll_zf_time;
        LinearLayout ll_wc_time;
        LinearLayout ll_xinxi;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup patent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.time_order_xq, null);
            holder.addView = (LinearLayout) convertView.findViewById(R.id.gwc_addview);
            holder.ll_zf_time = (LinearLayout) convertView.findViewById(R.id.ll_zf_time);
            holder.ll_wc_time = (LinearLayout) convertView.findViewById(R.id.ll_wc_time);
            holder.ll_xinxi = (LinearLayout) convertView.findViewById(R.id.ll_xinxi);
            holder.lv_jijian = (TextView) convertView.findViewById(R.id.lv_jijian);//
            holder.tv_kukuang = (TextView) convertView.findViewById(R.id.tv_kukuang);
            holder.tv_sj_name = (TextView) convertView.findViewById(R.id.tv_sj_name);//
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);//
            holder.tv_addview = (TextView) convertView.findViewById(R.id.tv_dizhi);//
            holder.tv_order_bh = (TextView) convertView.findViewById(R.id.tv_order_bh);//
            holder.tv_order_cjsj = (TextView) convertView.findViewById(R.id.tv_order_cjsj);//
            holder.tv_order_fksj = (TextView) convertView.findViewById(R.id.tv_order_fksj);//
            holder.tv_order_chengjiao_time = (TextView) convertView.findViewById(R.id.tv_order_chengjiao_time);//
            holder.shanchu = (TextView) convertView.findViewById(R.id.tv_shanche);// 删除
            holder.tv_yunfei = (TextView) convertView.findViewById(R.id.tv_yunfei);//
            holder.tv_haoma = (TextView) convertView.findViewById(R.id.tv_haoma);//
            holder.expressLayout = (LinearLayout) convertView.findViewById(R.id.express_layout);
            holder.expressTv = (TextView) convertView.findViewById(R.id.express_tv);
            holder.tv_heji = (TextView) convertView.findViewById(R.id.tv_heji);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.addView.removeAllViews();
        MyOrderData myOrderData = list.get(position);

        if (!TextUtils.isEmpty(myOrderData.getAccept_name())) {
            holder.tv_name.setText("收货人: " + myOrderData.getAccept_name());//
        } else {
            holder.tv_name.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(myOrderData.getMobile())) {
            holder.tv_haoma.setText("电话号码: " + myOrderData.getMobile());//
        } else {
            holder.tv_haoma.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(myOrderData.getProvince())) {
            holder.tv_addview.setText("收货地址: "
                    + myOrderData.getProvince() + "、"
                    + myOrderData.getCity() + "、 "
                    + myOrderData.getArea() + "、"
                    + myOrderData.getAddress());
        } else {
            holder.tv_addview.setVisibility(View.GONE);
        }

        holder.tv_sj_name.setText(myOrderData.getCompany_name());
        holder.tv_order_bh.setText(myOrderData.getOrder_no());//
        // 订单时间
        holder.tv_order_cjsj.setText(myOrderData.getAdd_time());
        String yunfei = myOrderData.getExpress_fee();
        if ("0.0".equals(yunfei)) {
            holder.tv_yunfei.setVisibility(View.GONE);
        } else {
            holder.tv_yunfei.setText("(含运费¥" + myOrderData.getExpress_fee() + ")");
        }

        try {
            if (!myOrderData.getPayment_time().equals("null")) {
                holder.tv_order_fksj.setText(myOrderData.getPayment_time());//
                holder.ll_zf_time.setVisibility(View.VISIBLE);
            }

            if (!myOrderData.getComplete_time().equals("null")) {
                holder.tv_order_chengjiao_time.setText(myOrderData.getComplete_time());
                holder.ll_wc_time.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < myOrderData.getList().size(); i++) {
            ViewHolder holderChild = new ViewHolder();
            View vi = LayoutInflater.from(context).inflate(R.layout.itme_my_order_xq, null);
            holderChild.tv_goods_title = (TextView) vi.findViewById(R.id.tv_goods_title);//
            holderChild.tupian = (ImageView) vi.findViewById(R.id.iv_tupian);
            holderChild.tv_market_price = (TextView) vi.findViewById(R.id.tv_market_price);
            holderChild.real_price = (TextView) vi.findViewById(R.id.tv_real_price);
            holderChild.quantity = (TextView) vi.findViewById(R.id.tv_quantity);
            holderChild.tv_zongjia = (TextView) vi.findViewById(R.id.tv_zongjia);
            holderChild.tv_hongbao = (TextView) vi.findViewById(R.id.tv_hongbao);
            holderChild.lv_dingdanxq = (LinearLayout) vi.findViewById(R.id.lv_dingdanxq);
            holderChild.ll_kedihongbao = (LinearLayout) vi.findViewById(R.id.ll_kedihongbao);
            holderChild.tv_goods_title.setText(myOrderData.getList().get(i).getArticle_title());
            holderChild.tv_market_price.setText("市场价:¥" + myOrderData.getList().get(i).getMarket_price());
            String kedi_honbao = myOrderData.getCashing_packet();
            if ("0.0".equals(kedi_honbao)) {
                holderChild.ll_kedihongbao.setVisibility(View.GONE);
            } else {
                holderChild.tv_hongbao.setText("-¥" + myOrderData.getList().get(i).getCashing_packet());
            }
            holderChild.quantity.setText("x" + myOrderData.getList().get(i).getQuantity());
            holderChild.tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
            mAq.id(holderChild.tupian).image(myOrderData.getList().get(i).getImg_url());

            try {
                int number = myOrderData.getList().get(i).getQuantity();
                BigDecimal c = new BigDecimal(Double.parseDouble(myOrderData.getList().get(i).getSell_price()) / number);
                // //保留2位小数
                double sell_price = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                holderChild.real_price.setText("价格:¥" + sell_price);
                holderChild.tv_zongjia.setText("¥" + myOrderData.getList().get(i).getSell_price());//
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.addView.addView(vi);
        }
        heji_zongjia = myOrderData.getPayable_amount();
        holder.tv_heji.setText("合计:¥" + heji_zongjia);
        String expressNo = myOrderData.getExpress_no();
        if (TextUtils.isEmpty(expressNo)) {
            holder.expressLayout.setVisibility(View.GONE);
        } else {
            holder.expressLayout.setVisibility(View.VISIBLE);
            holder.expressTv.setText(expressNo);
        }
        return convertView;

    }
}