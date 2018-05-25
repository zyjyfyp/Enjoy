package com.yunsen.enjoy.activity.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
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

        private long timeGetTime;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup patent) {

        ViewHolder holder = null;
        holder = new ViewHolder();
        if (convertView == null) {
            try {
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.time_order_xq, null);
                LinearLayout addview = (LinearLayout) convertView.findViewById(R.id.gwc_addview);
                LinearLayout ll_zf_time = (LinearLayout) convertView
                        .findViewById(R.id.ll_zf_time);
                LinearLayout ll_wc_time = (LinearLayout) convertView
                        .findViewById(R.id.ll_wc_time);
                LinearLayout ll_xinxi = (LinearLayout) convertView
                        .findViewById(R.id.ll_xinxi);
                holder.lv_jijian = (TextView) convertView
                        .findViewById(R.id.lv_jijian);//
                holder.tv_kukuang = (TextView) convertView
                        .findViewById(R.id.tv_kukuang);
                holder.tv_sj_name = (TextView) convertView
                        .findViewById(R.id.tv_sj_name);//
                // holder.tv_quxiao = (TextView)
                // convertView.findViewById(R.id.tv_quxiao);//
                holder.tv_name = (TextView) convertView
                        .findViewById(R.id.tv_name);//
                holder.tv_addview = (TextView) convertView
                        .findViewById(R.id.tv_dizhi);//
                holder.tv_order_bh = (TextView) convertView
                        .findViewById(R.id.tv_order_bh);//
                holder.tv_order_cjsj = (TextView) convertView
                        .findViewById(R.id.tv_order_cjsj);//
                holder.tv_order_fksj = (TextView) convertView
                        .findViewById(R.id.tv_order_fksj);//
                holder.tv_order_chengjiao_time = (TextView) convertView
                        .findViewById(R.id.tv_order_chengjiao_time);//
                holder.shanchu = (TextView) convertView
                        .findViewById(R.id.tv_shanche);// 删除
                holder.tv_yunfei = (TextView) convertView
                        .findViewById(R.id.tv_yunfei);//
                holder.tv_haoma = (TextView) convertView
                        .findViewById(R.id.tv_haoma);//
                // holder.tv_name.setText("收货人: "+list.get(position).getAccept_name());//
                // holder.tv_haoma.setText("电话号码: "+list.get(position).getMobile());//

                if (!list.get(position).getAccept_name().equals("")) {
                    holder.tv_name.setText("收货人: "
                            + list.get(position).getAccept_name());//
                } else {
                    holder.tv_name.setVisibility(View.GONE);
                }

                if (!list.get(position).getMobile().equals("")) {
                    holder.tv_haoma.setText("电话号码: "
                            + list.get(position).getMobile());//
                } else {
                    holder.tv_haoma.setVisibility(View.GONE);
                }

                if (!list.get(position).getProvince().equals("")) {
                    holder.tv_addview.setText("收货地址: "
                            + list.get(position).getProvince() + "、"
                            + list.get(position).getCity() + "、 "
                            + list.get(position).getArea() + "、"
                            + list.get(position).getAddress());
                } else {
                    holder.tv_addview.setVisibility(View.GONE);
                }

                holder.tv_sj_name.setText(list.get(position).getCompany_name());
                holder.tv_order_bh.setText(list.get(position).getOrder_no());//
                // 订单时间
                holder.tv_order_cjsj.setText(list.get(position).getAdd_time());

                String yunfei = list.get(position).getExpress_fee();
                System.out.println("yunfei1=============" + yunfei);
                if (yunfei.equals("0.0")) {
                    holder.tv_yunfei.setVisibility(View.GONE);
                } else {
                    holder.tv_yunfei.setText("(含运费¥"
                            + list.get(position).getExpress_fee() + ")");
                }

                try {

                    // String status = list.get(position).getPayment_status();
                    // System.out.println("订单详情status============="+status);
                    // if (status != null) {
                    // if (status.equals("1")) {
                    // // holder.tv_kukuang.setText("确认付款");
                    // holder.tv_kukuang.setVisibility(View.VISIBLE);
                    // holder.shanchu.setVisibility(View.VISIBLE);
                    // // holder.tv_zhuangtai.setText("买家未付款");
                    // }

                    // 支付时间
                    // if (list.get(position).getPayment_time()!=null) {
                    if (!list.get(position).getPayment_time().equals("null")) {
                        holder.tv_order_fksj.setText(list.get(position)
                                .getPayment_time());//
                        ll_zf_time.setVisibility(View.VISIBLE);
                    }
                    // }

                    // 完成时间
                    // if (list.get(position).getComplete_time()!= null) {
                    if (!list.get(position).getComplete_time().equals("null")) {
                        holder.tv_order_chengjiao_time.setText(list.get(
                                position).getComplete_time());
                        ll_wc_time.setVisibility(View.VISIBLE);
                    }
                    // }

                    // }

                } catch (Exception e) {

                    e.printStackTrace();
                }

                addview.removeAllViews();

                for (int i = 0; i < list.get(position).getList().size(); i++) {
                    holder = new ViewHolder();
                    View vi = LayoutInflater.from(context).inflate(R.
                            layout.itme_my_order_xq, null);
                    holder.tv_goods_title = (TextView) vi
                            .findViewById(R.id.tv_goods_title);//
                    holder.tupian = (ImageView) vi.findViewById(R.id.iv_tupian);
                    holder.tv_market_price = (TextView) vi
                            .findViewById(R.id.tv_market_price);
                    holder.real_price = (TextView) vi
                            .findViewById(R.id.tv_real_price);
                    holder.quantity = (TextView) vi
                            .findViewById(R.id.tv_quantity);
                    holder.tv_zongjia = (TextView) vi
                            .findViewById(R.id.tv_zongjia);
                    holder.tv_hongbao = (TextView) vi
                            .findViewById(R.id.tv_hongbao);
                    holder.lv_dingdanxq = (LinearLayout) vi
                            .findViewById(R.id.lv_dingdanxq);
                    holder.ll_kedihongbao = (LinearLayout) vi.findViewById(R.id.ll_kedihongbao);
                    holder.tv_goods_title.setText(list.get(position).getList().get(i).getArticle_title());
                    holder.tv_market_price.setText("市场价:¥"
                            + list.get(position).getList().get(i)
                            .getMarket_price());
                    // holder.real_price.setText("价格:¥"+list.get(position).getList().get(i).getReal_price());
                    String kedi_honbao = list.get(position).getCashing_packet();
                    System.out.println("kedi_honbao=============" + kedi_honbao);
                    if (kedi_honbao.equals("0.0")) {
                        holder.ll_kedihongbao.setVisibility(View.GONE);
                    } else {
                        holder.tv_hongbao.setText("-¥" + list.get(position).getList().get(i).getCashing_packet());
                    }
                    holder.quantity
                            .setText("x"
                                    + list.get(position).getList().get(i)
                                    .getQuantity());
                    holder.tv_market_price.getPaint()
                            .setFlags(
                                    Paint.STRIKE_THRU_TEXT_FLAG
                                            | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
                    // ImageLoader imageLoader=ImageLoader.getInstance();
                    // imageLoader.displayImage(RealmName.REALM_NAME_HTTP +
                    // list.get(position).getList().get(i).getImg_url(),
                    // holder.tupian);
                    mAq.id(holder.tupian).image(
                            URLConstants.REALM_NAME_HTTP
                                    + list.get(position).getList().get(i)
                                    .getImg_url());

                    try {

                        // double a= 0;
                        // for (int w = 0; w < list.size(); w++) {
                        // String price =
                        // list.get(w).getList().get(i).getSell_price();
                        // int number =
                        // list.get(w).getList().get(i).getQuantity();
                        // BigDecimal c = new
                        // BigDecimal(Double.parseDouble(price)*number);
                        // //保留2位小数
                        // double total_c_ll =
                        // c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                        // // System.out.println("价格是多少0============="+total_c);
                        // a += total_c_ll;
                        // System.out.println("价格是多少============="+a);
                        // list_monney.add(a);
                        // //
                        // System.out.println("list_monney============="+list_monney.size());
                        // }
                        // total_c = Double.toString(a);
                        // System.out.println("total_c================"+total_c);

                        int number = list.get(position).getList().get(i)
                                .getQuantity();
                        BigDecimal c = new BigDecimal(
                                Double.parseDouble(list.get(position).getList()
                                        .get(i).getSell_price())
                                        / number);
                        // //保留2位小数
                        double sell_price = c.setScale(2,
                                BigDecimal.ROUND_HALF_UP).doubleValue();
                        holder.real_price.setText("价格:¥" + sell_price);

                        holder.tv_zongjia.setText("¥"
                                + list.get(position).getList().get(i)
                                .getSell_price());//

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    addview.addView(vi);
                    convertView.setTag(holder);

                }

                // Double sum1 = 0d;
                // for(Double a:list_monney)//个数数组
                // {
                // sum1 += a;
                // // System.out.println("sum1============="+sum1);
                // }
                // String total_c = Double.toString(sum1);
                System.out.println("yunfei================" + yunfei);

                // BigDecimal c = new
                // BigDecimal(Double.parseDouble(list.get(position).getPayable_amount())+Double.parseDouble(list.get(position).getExpress_fee()));
                // heji_zongjia =
                // Double.toString(c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                heji_zongjia = list.get(position).getPayable_amount();
                System.out.println("heji_zongjia=============" + heji_zongjia);
                holder.tv_heji = (TextView) convertView
                        .findViewById(R.id.tv_heji);
                holder.tv_heji.setText("合计:¥" + heji_zongjia);

            } catch (Exception e) {

                e.printStackTrace();
            }

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;

    }
}