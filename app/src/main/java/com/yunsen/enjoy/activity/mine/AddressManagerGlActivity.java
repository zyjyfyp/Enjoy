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
import android.widget.Toast;


import com.hengyushop.dao.WareDao;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.adapter.MyAddressManagerAdapter;
import com.yunsen.enjoy.activity.user.EditUserAddressActivity;
import com.yunsen.enjoy.common.Constants;
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
 * 修改地址 中安
 *
 * @author Administrator
 */
public class AddressManagerGlActivity extends AppCompatActivity {

    private ListView list_address;
    private Button btn_add_address;
    private WareDao wareDao;
    private String yth, key, strUrl;
    private UserRegisterData registerData;
    private DialogProgress progress;
    private MyPopupWindowMenu popupWindowMenu;
    private SharedPreferences spPreferences;
    private MyAddressManagerAdapter adapter;
    private int ID;
    private ArrayList<UserAddressData> list = new ArrayList<UserAddressData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_manager);
        try {
            popupWindowMenu = new MyPopupWindowMenu(this);
            wareDao = new WareDao(getApplicationContext());
            progress = new DialogProgress(AddressManagerGlActivity.this);
            list_address = (ListView) findViewById(R.id.list_address);
            btn_add_address = (Button) findViewById(R.id.btn_add_address);
            spPreferences = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);

        } catch (Exception e) {

            e.printStackTrace();
        }

        ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

        btn_add_address.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int index = 1;
                Intent intent = new Intent(AddressManagerGlActivity.this,
                        AddUserAddressActivity.class);
                intent.putExtra("index", index);
                startActivityForResult(intent, 0);
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
        getuseraddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 输出用户默认收货地址
     */
    private void getuseraddress() {
        progress.CreateProgress();
        String user_name = spPreferences.getString(SpConstants.USER_NAME, "");
        Logger.i("结果呢1==============" + user_name);
        AsyncHttp.get(URLConstants.REALM_ACCOUNT_URL
                        + "/get_user_shopping_address?user_name=" + user_name + "",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject jsonObject = new JSONObject(arg1);
                            System.out.println("1================" + arg1);
                            list = new ArrayList<UserAddressData>();
                            String status = jsonObject.getString("status");
                            if (status.equals("y")) {
                                JSONArray jsonArray = jsonObject
                                        .getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsot = jsonArray
                                            .getJSONObject(i);
                                    UserAddressData data = new UserAddressData();
                                    data.user_accept_name = jsot
                                            .getString("user_accept_name");
                                    data.province = jsot.getString("province");
                                    data.city = jsot.getString("city");
                                    data.user_area = jsot.getString("area");
                                    data.user_mobile = jsot
                                            .getString("user_mobile");
                                    data.user_address = jsot
                                            .getString("user_address");
                                    data.id = jsot.getInt("id");
                                    list.add(data);
                                }
                                Message message = new Message();
                                message.what = 0;
                                message.obj = list;
                                handler.sendMessage(message);
                            } else {
                                Message message = new Message();
                                message.what = 0;
                                message.obj = list;
                                handler.sendMessage(message);

                            }

                        } catch (JSONException e) {
                            handler.sendEmptyMessage(3);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String s) {
                        super.onFailure(throwable, s);
                        handler.sendEmptyMessage(3);
                    }
                }, getApplicationContext());

    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            progress.CloseProgress();
            switch (msg.what) {
                case 0:
                    try {

                        list = (ArrayList<UserAddressData>) msg.obj;
                        adapter = new MyAddressManagerAdapter(AddressManagerGlActivity.this, list);
                        list_address.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        list_address
                                .setOnItemClickListener(new OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> arg0,
                                                            View arg1, int arg2, long arg3) {

                                        if (getIntent().hasExtra("order_confrim")) {
                                            // 表示是来自订单确认

                                            Intent intent = new Intent(
                                                    AddressManagerGlActivity.this,
                                                    EditUserAddressActivity.class);

                                            UserAddressData bean = list.get(arg2);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("bean", bean);
                                            intent.putExtras(bundle);
                                            startActivity(intent);

                                        }

                                    }

                                });

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT)
                            .show();
                    break;
                default:
                    break;
            }
        }

    };

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
                        + "/tools/mobile_ajax.asmx/delete_user_shopping_address?user_id=" + user_id
                        + "&id=" + ID + "";
                Log.v("data1", "删除:" + strUrl);
                progress.CreateProgress();
                AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {

                        System.out.println("====================" + arg1);
                        progress.CloseProgress();
                        super.onSuccess(arg0, arg1);

                        // adapter.notifyDataSetChanged();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.ADD_ADDRESS_REQUEST) {
            getuseraddress();
        }

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
