package com.yunsen.enjoy.activity.pay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.MyOrderConfrimActivity;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.widget.DialogProgress;


/**
 * 支付成功
 *
 * @author Administrator
 */
public class ZhiFuOKActivity extends AppCompatActivity implements OnClickListener {

    private ImageView iv_fanhui;
    private TextView textView1, textView2, textView3, textView4, textView5,
            textView6;
    private DialogProgress progress;
    public static String province, city, area, user_address, accept_name,
            user_mobile;
    public static String recharge_no, order_no, datetime, sell_price,
            give_pension, article_id;
    public static String huodong_type = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhifu_ok);
        progress = new DialogProgress(ZhiFuOKActivity.this);
        huodong_type = "1";// 活动支付成功之后设置不能继续报名
        intren();
    }

    public void intren() {
        try {
            textView1 = (TextView) findViewById(R.id.textView1);
            textView2 = (TextView) findViewById(R.id.textView2);
            textView3 = (TextView) findViewById(R.id.textView3);
            textView4 = (TextView) findViewById(R.id.textView4);
            textView5 = (TextView) findViewById(R.id.textView5);
            textView6 = (TextView) findViewById(R.id.textView6);
            System.out.println("1================================="
                    + TishiCarArchivesActivity.accept_name);
            System.out.println("2================================="
                    + MyOrderConfrimActivity.accept_name1);
            System.out.println("3================================="
                    + MyOrderZFActivity.accept_name);
            if (TishiCarArchivesActivity.accept_name != null) {
                accept_name = TishiCarArchivesActivity.accept_name;
                user_mobile = TishiCarArchivesActivity.user_mobile;
                province = TishiCarArchivesActivity.province;
                city = TishiCarArchivesActivity.city;
                area = TishiCarArchivesActivity.area;
                user_address = TishiCarArchivesActivity.user_address;
                recharge_no = TishiCarArchivesActivity.recharge_no;
                datetime = TishiCarArchivesActivity.datetime;
                sell_price = TishiCarArchivesActivity.sell_price;
            } else if (MyOrderConfrimActivity.accept_name1 != null) {
                accept_name = MyOrderConfrimActivity.accept_name1;
                user_mobile = MyOrderConfrimActivity.user_mobile1;
                province = MyOrderConfrimActivity.province1;
                city = MyOrderConfrimActivity.city1;
                area = MyOrderConfrimActivity.area1;
                user_address = MyOrderConfrimActivity.user_address1;
                recharge_no = MyOrderConfrimActivity.recharge_no1;
                datetime = MyOrderConfrimActivity.datetime1;
                sell_price = MyOrderConfrimActivity.sell_price1;
            } else if (MyOrderZFActivity.accept_name != null) {
                accept_name = MyOrderZFActivity.accept_name;
                user_mobile = MyOrderZFActivity.user_mobile;
                province = MyOrderZFActivity.province;
                city = MyOrderZFActivity.city;
                area = MyOrderZFActivity.area;
                user_address = MyOrderZFActivity.user_address;
                recharge_no = MyOrderZFActivity.recharge_no;
                datetime = MyOrderZFActivity.datetime;
                sell_price = MyOrderZFActivity.sell_price;
            }
            // Toast.makeText(ZhiFuOKActivity.this,
            // MyOrderZFActivity.sell_price+"/"+MyOrderZFActivity.datetime,
            // 200).show();
            textView1.setText(accept_name);
            textView2.setText(user_mobile);
            textView3.setText(province + city + area + user_address);
            textView4.setText(recharge_no);
            textView5.setText(datetime);
            textView6.setText("¥" + sell_price);

            iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
            iv_fanhui.setOnClickListener(this);
            System.out.println("4=================================");
// TODO: 2018/4/25  养老金
            Toast.makeText(this, "养老金", Toast.LENGTH_SHORT).show();
//			Intent intent = new Intent(ZhiFuOKActivity.this,
//					TishiPensionActivity.class);
//			startActivity(intent);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.iv_fanhui:
                finish();
                break;
            default:
                break;
        }
    }
}
