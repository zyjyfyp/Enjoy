package com.yunsen.enjoy.activity.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.order.DianPingActivity;
import com.yunsen.enjoy.activity.order.MyOrderXqActivity;
import com.yunsen.enjoy.activity.pay.MyOrderZFActivity;
import com.yunsen.enjoy.activity.pay.TishiCarArchivesActivity;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.MyOrderData;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 *
 * @author Administrator
 */
public class MyOrderllAdapter extends BaseAdapter {
    private Context context;
    private Intent intent;
    private List<MyOrderData> list;
    private LayoutInflater inflater;
    private Activity act;
    private Handler handler;
    private String payment_status, express_status, status;
    int zhuangtai, yunfei1;
    public static String total_cll, heji_zongjia;
    double dzongjia, yunfei;
    String user_name, user_id, login_sign, order_no;
    TextView tv_heji;
    public static boolean type = false;
    int p;
    public static String order_type = "0";
    public static AQuery mAq;
    public static String recharge_no, notify_url;
    public static String sell_price;

    // public static List<Double> list_monney = new ArrayList<Double>();
    public MyOrderllAdapter(List<MyOrderData> list, Context context,
                            Handler handler) {
        this.list = list;
        this.context = context;
        this.handler = handler;
        // this.payment_status = payment_status;
        mAq = new AQuery(context);
        this.inflater = LayoutInflater.from(context);
    }

    // @Override
    // public int getCount() {
    // if (list.size() < 1) {
    // return 0;
    // } else {
    // return list.size();
    // }
    // }

    public void putData(ArrayList<MyOrderData> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public int getCount() {

        return list.size();
    }

    // @Override
    // public Object getItem(int position) {
    //
    // return list.get(position);
    // }

    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    class ViewHolder {
        TextView tv_goods_title = null;
        TextView tv_market_price = null;
        TextView sell_price = null;
        TextView quantity = null;
        ImageView tupian = null;
        TextView lv_jijian = null;
        TextView tv_kukuang = null;
        TextView tv_quxiao = null;
        TextView tv_zhuangtai = null;
        TextView jijian = null;
        TextView tv_heji, tv_yunfei;//
        TextView shanchu = null;// 删除
        TextView tv_zongjia = null;
        TextView tv_queren_fukuan = null;
        TextView tv_pingjia, tv_company_name, tv_hongbao, tv_tuikuan, tv_yanhuoma;
        LinearLayout lv_dingdanxq = null;
        LinearLayout ll_anliu = null;
        LinearLayout ll_hongbao = null;
        private long timeGetTime;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup patent) {

        ViewHolder holder = null;
        // holder = new ViewHolder();
        System.out
                .println("position==========================================================="
                        + position);
        // if (convertView == null) {
        holder = new ViewHolder();

        // convertView = inflater.inflate(R.layout.itme_my_order, null);
        if (convertView == null) {
            convertView = LinearLayout.inflate(context, R.layout.itme_my_order, null);
        }

        LinearLayout addview = (LinearLayout) convertView.findViewById(R.id.gwc_addview);
        try {
            holder.ll_hongbao = (LinearLayout) convertView.findViewById(R.id.ll_hongbao);//
            holder.ll_anliu = (LinearLayout) convertView.findViewById(R.id.ll_anliu);//
            holder.lv_jijian = (TextView) convertView.findViewById(R.id.lv_jijian);//
            holder.tv_kukuang = (TextView) convertView.findViewById(R.id.tv_kukuang);
            holder.tv_quxiao = (TextView) convertView.findViewById(R.id.tv_quxiao);//
            holder.tv_zhuangtai = (TextView) convertView.findViewById(R.id.tv_zhuangtai);//
            holder.tv_tuikuan = (TextView) convertView.findViewById(R.id.tv_tuikuan);//
            holder.tv_yunfei = (TextView) convertView.findViewById(R.id.tv_yunfei);//
            holder.shanchu = (TextView) convertView
                    .findViewById(R.id.tv_shanche);//
            holder.tv_queren_fukuan = (TextView) convertView
                    .findViewById(R.id.tv_queren_fukuan);//
            holder.tv_pingjia = (TextView) convertView
                    .findViewById(R.id.tv_pingjia);//
            holder.tv_hongbao = (TextView) convertView
                    .findViewById(R.id.tv_hongbao);//
            holder.tv_company_name = (TextView) convertView
                    .findViewById(R.id.tv_company_name);//
            holder.tv_heji = (TextView) convertView
                    .findViewById(R.id.tv_heji);//
            holder.tv_yanhuoma = (TextView) convertView
                    .findViewById(R.id.tv_yanhuoma);//
            View iv_hongbao = (View) convertView.findViewById(R.id.iv_hongbao);//

            // BigDecimal c = new
            // BigDecimal(Double.parseDouble(list.get(position).getPayable_amount())+Double.parseDouble(list.get(position).getExpress_fee()));
            // String total_cll =
            // Double.toString(c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());

            sell_price = list.get(position).getPayable_amount();
            holder.tv_heji.setText("¥" + list.get(position).getPayable_amount());

            holder.tv_company_name.setText(list.get(position).getCompany_name());
            payment_status = list.get(position).getPayment_status();
            System.out.println("payment_status=============" + payment_status);
            express_status = list.get(position).getExpress_status();
            System.out.println("express_status=============" + express_status);
            status = list.get(position).getStatus();
            System.out.println("status=============" + status);

            // yunfei = Double.parseDouble(list.get(position).getExpress_fee());
            // int yunfei =
            // Integer.parseInt(list.get(position).getExpress_fee());
            String yunfei = list.get(position).getExpress_fee();
            // System.out.println("yunfei1============="+yunfei);
            if (yunfei.equals("0.0")) {
                // if (yunfei == 0) {
                holder.tv_yunfei.setVisibility(View.GONE);
            } else {
                holder.tv_yunfei.setText("(含运费¥" + list.get(position).getExpress_fee() + ")");
            }
            if (list.get(position).getCashing_packet().equals("0.0")) {
                holder.ll_hongbao.setVisibility(View.GONE);
                iv_hongbao.setVisibility(View.GONE);
            } else {
                holder.ll_hongbao.setVisibility(View.VISIBLE);
                iv_hongbao.setVisibility(View.VISIBLE);
                holder.tv_hongbao.setText("已抵红包:-¥" + list.get(position).getCashing_packet());
            }
            // String kedi_honbao = list.get(position).getCashing_packet();
            // // System.out.println("kedi_honbao============="+kedi_honbao);
            // if (kedi_honbao.equals("0.0")) {
            // ll_hongbao.setVisibility(View.GONE);
            // }else {
            // tv_hongbao.setText("已抵红包:-¥"+kedi_honbao);
            // }

            // 订单状态
            if (payment_status.equals("1")) {
                System.out.println("待付款=============");
                holder.tv_zhuangtai.setText("等待付款");
                holder.ll_anliu.setVisibility(View.VISIBLE);
                holder.tv_kukuang.setVisibility(View.VISIBLE);
                holder.tv_pingjia.setVisibility(View.GONE);
                holder.tv_queren_fukuan.setVisibility(View.GONE);
                holder.shanchu.setVisibility(View.VISIBLE);
                holder.tv_tuikuan.setVisibility(View.GONE);
                holder.tv_kukuang.setText("去付款");
                zhuangtai = 2;
            } else if (status.equals("4")) {
                holder.tv_zhuangtai.setText("已退款");
                holder.ll_anliu.setVisibility(View.VISIBLE);
                holder.tv_tuikuan.setVisibility(View.GONE);
                holder.tv_kukuang.setVisibility(View.GONE);
                holder.tv_pingjia.setVisibility(View.GONE);
                holder.shanchu.setVisibility(View.GONE);
            } else if (payment_status.equals("2") && express_status.equals("1")
                    && status.equals("2")) {
                System.out.println("待发货=============");
                order_type = "1";
                // tv_yanhuoma.setVisibility(View.VISIBLE);
                holder.tv_zhuangtai.setText("已付款");
                System.out.println("待发货取货码为空======1======="
                        + list.get(position).getAccept_no());
                holder.ll_anliu.setVisibility(View.VISIBLE);
                holder.tv_kukuang.setVisibility(View.GONE);
                holder.tv_pingjia.setVisibility(View.GONE);
                holder.shanchu.setVisibility(View.GONE);
                if (!list.get(position).getAccept_no().equals("")) {
                    holder.tv_yanhuoma.setText("取货码:"
                            + list.get(position).getAccept_no());
                } else {
                    System.out.println("待发货取货码为空=============");
                }
                holder.tv_tuikuan.setVisibility(View.VISIBLE);
                zhuangtai = 3;
            } else if (payment_status.equals("2") && express_status.equals("2")
                    && status.equals("2")) {
                System.out.println("待收货=============");
                order_type = "2";
                holder.tv_zhuangtai.setText("已发货");
                holder.ll_anliu.setVisibility(View.VISIBLE);
                holder.tv_kukuang.setVisibility(View.GONE);
                holder.tv_pingjia.setVisibility(View.GONE);
                holder.tv_queren_fukuan.setVisibility(View.VISIBLE);
                holder.tv_tuikuan.setVisibility(View.GONE);
                holder.tv_queren_fukuan.setText("确认收货");
                zhuangtai = 4;
            } else if (payment_status.equals("2") && express_status.equals("2")
                    && status.equals("3")) {
                System.out.println("已完成=============");
                holder.tv_zhuangtai.setText("交易完成");
                holder.ll_anliu.setVisibility(View.VISIBLE);
                holder.tv_queren_fukuan.setVisibility(View.GONE);
                holder.tv_kukuang.setVisibility(View.GONE);
                holder.shanchu.setVisibility(View.VISIBLE);
                holder.tv_pingjia.setVisibility(View.VISIBLE);
                holder.tv_tuikuan.setVisibility(View.GONE);
                holder.tv_pingjia.setText("评价");
                zhuangtai = 5;
            }

            /**
             * 确认付款
             */
            holder.tv_kukuang.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        double a = 0;
                        // BigDecimal c = new
                        // BigDecimal(Double.parseDouble(list.get(position).getPayable_amount())+Double.parseDouble(list.get(position).getExpress_fee()));
                        // String total_cll =
                        // Double.toString(c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                        // System.out.println("total_cll================"+total_cll);
                        // if
                        // (list.get(position).getPayment_status().equals("2")
                        // && list.get(position).getExpress_status().equals("2")
                        // && list.get(position).getStatus().equals("2")){
                        // System.out.println("待收货=============");
                        // order_type = "1";
                        // }else if
                        // (list.get(position).getPayment_status().equals("2")
                        // && list.get(position).getExpress_status().equals("2")
                        // && list.get(position).getStatus().equals("3")){
                        // System.out.println("已完成=============");
                        // order_type = "2";
                        // }
                        order_type = "1";
                        System.out.println("order_type=====1============="
                                + order_type);

                        String order_no = list.get(position).getTrade_no();
                        Intent intent = new Intent(context, MyOrderZFActivity.class);
                        intent.putExtra("order_no", order_no);
                        intent.putExtra("order_type", order_type);
                        intent.putExtra("total_c", list.get(position).getPayable_amount());
                        context.startActivity(intent);

                        Message msg = new Message();
                        msg.what = 4;
                        msg.obj = order_no;
                        handler.sendMessage(msg);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });

            /**
             * 确认收货
             */
            holder.tv_queren_fukuan.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        order_type = "2";
                        System.out.println("order_type=====2================" + order_type);
                        Intent intent = new Intent(context, TishiCarArchivesActivity.class);
                        intent.putExtra("order_no", list.get(position).getOrder_no());
                        intent.putExtra("order_type", order_type);
                        intent.putExtra("title", "title");
                        context.startActivity(intent);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });

            /**
             * 取消订单
             */
            holder.tv_quxiao.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        String order_no = list.get(position).getOrder_no();
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = order_no;
                        handler.sendMessage(msg);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }
            });
            /**
             * 申请退款
             */
            holder.tv_tuikuan.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        String order_no = list.get(position).getOrder_no();
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = order_no;
                        handler.sendMessage(msg);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }
            });

            /**
             * 删除
             */
            holder.shanchu.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        String order_no = list.get(position).getOrder_no();
                        Message msg = new Message();
                        msg.what = 3;
                        msg.obj = order_no;
                        handler.sendMessage(msg);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });
            addview.removeAllViews();

            for (int i = 0; i < list.get(position).getList().size(); i++) {
                // ViewHolder holder = null;
                p = i;
                // holder = new ViewHolder();
                View vi = LayoutInflater.from(context).inflate(R.layout.itme_my_order_zhuti, null);

                holder.tv_goods_title = (TextView) vi.findViewById(R.id.tv_goods_title);//
                holder.tupian = (ImageView) vi.findViewById(R.id.iv_tupian);
                holder.tv_market_price = (TextView) vi.findViewById(R.id.tv_market_price);
                holder.sell_price = (TextView) vi.findViewById(R.id.tv_real_price);
                holder.quantity = (TextView) vi.findViewById(R.id.tv_quantity);
                holder.lv_dingdanxq = (LinearLayout) vi.findViewById(R.id.lv_dingdanxq);

                holder.tv_goods_title.setText(list.get(position).getList().get(i).getArticle_title());
                holder.tv_market_price.setText("¥" + list.get(position).getList().get(i)
                        .getMarket_price());
                // holder.sell_price.setText("¥"+list.get(position).getList().get(i).getSell_price());
                holder.quantity.setText("x"
                        + list.get(position).getList().get(i).getQuantity());
                holder.tv_market_price.getPaint().setFlags(
                        Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
                // ImageLoader imageLoader=ImageLoader.getInstance();
                // imageLoader.displayImage(RealmName.REALM_NAME_HTTP +
                // list.get(position).getList().get(i).getImg_url(),
                // holder.tupian);
                mAq.id(holder.tupian).image(URLConstants.REALM_NAME_HTTP + list.get(position).getList().get(i).getImg_url());

                type = true;
                int number = list.get(position).getList().get(i).getQuantity();
                BigDecimal c = new BigDecimal(Double.parseDouble(list.get(position).getList().get(i).getSell_price()) / number);
                // //保留2位小数
                double sell_price_zhi = c.setScale(2, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();

                holder.sell_price.setText("¥" + sell_price_zhi);// 价格

                System.out.println("getGoods_title=============" + list.get(position).getList().get(i).getArticle_title());

                addview.addView(vi);
                // convertView.setTag(holder);

                /**
                 * 订单详情
                 */
                holder.lv_dingdanxq.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        try {

                            Intent intent = new Intent(context,
                                    MyOrderXqActivity.class);
                            MyOrderData bean = list.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("bean", (Serializable) bean);
                            intent.putExtras(bundle);
                            intent.putExtra("payment_status", payment_status);
                            intent.putExtra("express_status", express_status);
                            intent.putExtra("status", status);
                            context.startActivity(intent);

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                });
            }

            /**
             * 评价
             */
            // TextView tv_pingjia = (TextView)
            // convertView.findViewById(R.id.tv_pingjia);//
            holder.tv_pingjia.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context,
                                DianPingActivity.class);
                        intent.putExtra("article_id", list.get(position)
                                .getList().get(p).getArticle_id());
                        context.startActivity(intent);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }

        // }else {
        // holder = (ViewHolder) convertView.getTag();
        // }
        return convertView;

    }


}