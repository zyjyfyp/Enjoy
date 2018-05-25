package com.yunsen.enjoy.activity.mine;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hengyushop.dao.WareDao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.adapter.MyAddressManagerAdapter;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserAddressData;
import com.yunsen.enjoy.model.UserRegisterData;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.MyPopupWindowMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 云商聚管理地址
 *
 * @author Administrator
 */
public class AddressManagerActivity extends AppCompatActivity {

    private ListView list_address;
    private Button btn_add_address;
    private TextView tv_guanli;
    private WareDao wareDao;
    private String yth, key, strUrl;
    private UserRegisterData registerData;
    private DialogProgress progress;
    private MyPopupWindowMenu popupWindowMenu;
    private SharedPreferences spPreferences;
    private MyAddressManagerAdapter adapter;
    private int ID;
    private ArrayList<UserAddressData> list;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_manager_gl);
        popupWindowMenu = new MyPopupWindowMenu(this);
        progress = new DialogProgress(AddressManagerActivity.this);
        list_address = (ListView) findViewById(R.id.list_address);
        list_address.setCacheColorHint(0);
        tv_guanli = (TextView) findViewById(R.id.tv_guanli);
        spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);


        ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        list_address.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                ID = list.get(arg2).id;
                dialog(ID);
                return true;
            }
        });

        list_address.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {

//				if(getIntent().hasExtra("order_confrim")){
//					//表示是来自订单确认
//					Intent intent = new Intent();
//					intent.putExtra("data", list.get(arg2));
//					setResult(100, intent);
////					AppManager.getAppManager().finishActivity();
//					finish();
//				}
                String jutuan_type = getIntent().getStringExtra("jutuan_type");
                if (jutuan_type != null) {
                    intent = new Intent(AddressManagerActivity.this, JuTuanConfrimActivity.class);
                    JuTuanConfrimActivity.handlerll.sendEmptyMessage(2);
                } else {
                    intent = new Intent(AddressManagerActivity.this, MyOrderConfrimActivity.class);
//                    MyOrderConfrimActivity.handlerll.sendEmptyMessage(2);

                }

                String no = getIntent().getStringExtra("no");
                String title = getIntent().getStringExtra("title");
                String stare = getIntent().getStringExtra("stare");
                String groupon_price = getIntent().getStringExtra("groupon_price");
                String img_url = getIntent().getStringExtra("img_url");
                String price = getIntent().getStringExtra("price");
                String goods_price = getIntent().getStringExtra("goods_price");
                String jubi = getIntent().getStringExtra("point");
                String shopping_ids = getIntent().getStringExtra("shopping_ids");
                String tuangou_id = getIntent().getStringExtra("tuangou_id");

//				Intent intent = new Intent(AddressManagerActivity.this,MyOrderConfrimActivity.class);
                intent.putExtra("user_accept_name", list.get(arg2).user_accept_name);
                intent.putExtra("province", list.get(arg2).province);
                intent.putExtra("city", list.get(arg2).city);
                intent.putExtra("user_area", list.get(arg2).user_area);
                intent.putExtra("user_address", list.get(arg2).user_address);
                intent.putExtra("user_mobile", list.get(arg2).user_mobile);

                intent.putExtra("title", title);
                intent.putExtra("stare", stare);
                intent.putExtra("groupon_price", groupon_price);
                intent.putExtra("img_url", img_url);
                intent.putExtra("price", price);
                intent.putExtra("goods_price", goods_price);
                intent.putExtra("jubi", jubi);
                intent.putExtra("shopping_ids", shopping_ids);
                intent.putExtra("tuangou_id", tuangou_id);
                intent.putExtra("buy_no", getIntent().getStringExtra("buy_no"));
                intent.putExtra("type_title", getIntent().getStringExtra("type_title"));
                intent.putExtra("type_wx", getIntent().getStringExtra("type_wx"));
                intent.putExtra("jiekou", getIntent().getStringExtra("jiekou"));
                intent.putExtra("spec_text", getIntent().getStringExtra("spec_text"));
                intent.putExtra("price", getIntent().getStringExtra("price"));
                intent.putExtra("people", getIntent().getStringExtra("people"));
//				MyOrderConfrimActivity.handlerll.sendEmptyMessage(2);
                setResult(RESULT_OK, intent);
                finish();

            }

        });
        tv_guanli.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int index = 1;
                Intent intent = new Intent(AddressManagerActivity.this, AddressManagerGlActivity.class);
                intent.putExtra("order_confrim", "order_confrim");// 标示
                startActivity(intent);
//				startActivityForResult(intent, 0);
            }
        });

    }


    Handler handler = new Handler() {

        @SuppressWarnings("unchecked")
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    progress.CloseProgress();
                    list = (ArrayList<UserAddressData>) msg.obj;
                    adapter = new MyAddressManagerAdapter(AddressManagerActivity.this, list);
                    list_address.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onResume() {

        super.onResume();
//		adapter.notifyDataSetChanged();
        getuseraddress();
    }

    /**
     * 输出用户默认收货地址
     */
    private void getuseraddress() {
        progress.CreateProgress();
        String user_name = spPreferences.getString(SpConstants.USER_NAME, "");
        System.out.println("结果呢1==============" + user_name);
        AsyncHttp.get(URLConstants.REALM_URL + "/tools/mobile_ajax.asmx/get_user_shopping_address?user_name=" + user_name + ""
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject jsonObject = new JSONObject(arg1);
                            System.out.println("1================" + arg1);
                            list = new ArrayList<UserAddressData>();
                            String status = jsonObject.getString("status");
                            if (status.equals("y")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsot = jsonArray.getJSONObject(i);
                                    UserAddressData data = new UserAddressData();
                                    data.user_accept_name = jsot.getString("user_accept_name");
                                    data.province = jsot.getString("province");
                                    data.city = jsot.getString("city");
                                    data.user_area = jsot.getString("area");
                                    data.user_mobile = jsot.getString("user_mobile");
                                    data.user_address = jsot.getString("user_address");
                                    data.id = jsot.getInt("id");
                                    list.add(data);
                                }
                                progress.CloseProgress();
                                Message message = new Message();
                                message.what = 0;
                                message.obj = list;
                                handler.sendMessage(message);

//								handler.sendEmptyMessage(0);
                            } else {
                                progress.CloseProgress();
                                Message message = new Message();
                                message.what = 0;
                                message.obj = list;
                                handler.sendMessage(message);
                            }

                        } catch (JSONException e) {

                            progress.CloseProgress();
                            e.printStackTrace();
                        }
                    }

                }, getApplicationContext());

    }


//	protected void dialog(final int ID) {
//		AlertDialog.Builder builder = new Builder(this);
//		builder.setMessage("确认删除这个商品吗？");
//		builder.setTitle("提示");
//		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//
//				String user_id = spPreferences.getString("user_id", "");
//					strUrl = RealmName.REALM_NAME_LL + "/delete_user_shopping_address?&user_id=" + user_id+ "&cart_id=" + ID;
//					AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(), AddressManagerActivity.this);
//
//					Toast.makeText(AddressManagerActivity.this, "删除成功", 200).show();
//					dialog.dismiss();
//					adapter.notifyDataSetChanged();
//					getuseraddress();
//
//			}
//		});
//		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//		builder.create().show();
//	}

    protected void dialog(final int ID) {
        AlertDialog.Builder builder = new Builder(this);
        builder.setMessage("确认删除该地址？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String user_id = spPreferences.getString("user_id", "");
                System.out.println("1111====================" + user_id);
                String strUrl = URLConstants.REALM_URL
                        + "/delete_user_shopping_address?user_id=" + user_id + "&id=" + ID + "";
                Log.v("data1", "删除:" + strUrl);
                progress.CreateProgress();
                AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        System.out.println("====================" + arg1);
                        progress.CloseProgress();
                        super.onSuccess(arg0, arg1);
//						adapter.notifyDataSetChanged();
                        getuseraddress();

                    }

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {

                        super.onFailure(arg0, arg1);
                        System.out.println("1====================" + arg0);
                        System.out.println("2====================" + arg1);
                    }
                }, null);


            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("menu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
            popupWindowMenu.dismiss(); // 对话框消失
            popupWindowMenu.currentState = 1; // 标记状态，已消失
        } else {
            popupWindowMenu.showAtLocation(findViewById(R.id.layout),
                    Gravity.BOTTOM, 0, 0);
            popupWindowMenu.currentState = 0; // 标记状态，显示中
        }
        return false; // true--显示系统自带菜单；false--不显示。
    }

}
