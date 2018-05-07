package com.yunsen.enjoy.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.OnWheelChangedListener;
import com.gghl.view.wheelcity.WheelView;
import com.gghl.view.wheelcity.adapters.AbstractWheelTextAdapter;
import com.gghl.view.wheelcity.adapters.ArrayWheelAdapter;
import com.hengyushop.dao.CityDao;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.DBManager;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.AppManager;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserAddressData;
import com.yunsen.enjoy.model.UserRegisterData;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.MyAlertDialog;
import com.yunsen.enjoy.widget.MyPopupWindowMenu;
import com.yunsen.enjoy.widget.SlipButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditUserAddressActivity extends AppCompatActivity {

    private EditText et_username, et_userphone, et_user_dizhi, et_address;
    private Button btn_hold;
    private String name, phone, address;
    private Spinner sp_sheng, sp_shi, sp_xian;
    private ArrayList<String> al_sheng, al_shi, al_xian;
    private String sheng, shi, xian, yth, key;
    private int sheng_code, shi_code, area_code, index, is_default;
    private WareDao wareDao;
    private UserRegisterData registerData;
    private DialogProgress progress;
    private DBManager dbManager;
    private CityDao cityDao;
    @SuppressWarnings("rawtypes")
    private ArrayAdapter aa_sheng, aa_shi, aa_area;
    private String strUrl;
    private MyPopupWindowMenu popupWindowMenu;
    private SharedPreferences spPreferences;
    int user_address_id;
    private RelativeLayout rl_xzdz;
    private TextView tv_city;
    private String cityTxt, cityTxt1, cityTxt2, cityTxt3;
    String dizhi = "选择地区";
    SlipButton sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_address);
        popupWindowMenu = new MyPopupWindowMenu(this);
        progress = new DialogProgress(EditUserAddressActivity.this);
        spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
        innidade();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 在这里进行查询地址的操作
        // Toast.makeText(getApplicationContext(), "查询地址联网操作",Toast.LENGTH_SHORT).show();
        // handler.sendEmptyMessage(4);
        if (resultCode == 100) {

            UserAddressData dt = (UserAddressData) data
                    .getSerializableExtra("data");
            String name = dt.user_accept_name;
            String user_area = dt.user_area;
            String user_mobile = dt.user_mobile;
            String user_address = dt.user_address;
            System.out.println("checkedAddressId==================" + name);

            // et_username = (EditText) findViewById(R.id.et_user_name);
            // et_userphone = (EditText) findViewById(R.id.et_user_phone);
            // et_address = (EditText) findViewById(R.id.et_user_address);
            et_username.setText("收货人:" + name);
            et_userphone.setText(user_area + " " + user_address);
            et_userphone.setText(user_mobile);
        }
    }

    Handler handler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    // et_username.setText("");
                    // et_userphone.setText("");
                    // et_address.setText("");
                    String strmsg = (String) msg.obj;
                    progress.CloseProgress();
                    Toast.makeText(getApplicationContext(), strmsg, Toast.LENGTH_SHORT).show();
                    finish();
                /*
                 * if (index == 1) { Intent intent = new
				 * Intent(AddUserAddressActivity.this,
				 * AddressManagerActivity.class); startActivity(intent); } else
				 * if (index == 0) { Intent intent = new
				 * Intent(AddUserAddressActivity.this,
				 * OrderConfrimActivity.class); startActivity(intent); }
				 */
                    finish();
                    break;
                case 1:
                    // et_username.setText("");
                    // et_userphone.setText("");
                    // et_address.setText("");
                    String no = (String) msg.obj;
                    progress.CloseProgress();
                    Toast.makeText(getApplicationContext(), no, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }

        ;
    };

    private void innidade() {
        sb = (SlipButton) findViewById(R.id.sli);
        rl_xzdz = (RelativeLayout) findViewById(R.id.rl_xzdz);
        tv_city = (TextView) findViewById(R.id.tv_city);
        et_username = (EditText) findViewById(R.id.et_user_name);
        et_userphone = (EditText) findViewById(R.id.et_user_phone);
        // et_user_dizhi = (EditText) findViewById(R.id.et_user_dizhi);
        et_address = (EditText) findViewById(R.id.et_user_address);
        // sp_sheng = (Spinner) findViewById(R.id.sp_sheng);
        // sp_shi = (Spinner) findViewById(R.id.sp_shi);
        // sp_xian = (Spinner) findViewById(R.id.sp_xian);
        btn_hold = (Button) findViewById(R.id.btn_holdr);

        UserAddressData bean = (UserAddressData) getIntent()
                .getSerializableExtra("bean");
        et_username.setText(bean.user_accept_name);
        et_userphone.setText(bean.user_mobile);
        // et_user_dizhi.setText(bean.province);
        tv_city.setText(bean.province + "、" + bean.city + "、" + bean.user_area);
        et_address.setText(bean.user_address);
        user_address_id = bean.id;

        cityTxt1 = bean.province;
        cityTxt2 = bean.city;
        cityTxt3 = bean.user_area;

        /**
         * 判断是否使用红包
         */
        sb.SetOnChangedListener(new SlipButton.OnChangedListener() {
            @Override
            public void OnChanged(boolean isCheck) {
                System.out.println("isCheck================" + isCheck);
                if (isCheck == true) {
                    try {

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    is_default = 1;
                    //	            		hongbao_tety =  isCheck;//选择红包状态下
                } else if (isCheck == false) {
                    //						 sb.setCheck(true);
                    //						hongbao_tety =  isCheck;//不选择红包状态下
                    is_default = 0;
                }
            }
        });

        rl_xzdz.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                View view = dialogm();
                final MyAlertDialog dialog1 = new MyAlertDialog(
                        EditUserAddressActivity.this).builder()
                        .setTitle(dizhi.toString()).setView(view)
                        .setNegativeButton("取消", new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                dialog1.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_city.setText(cityTxt);
                    }
                });
                dialog1.show();
            }
        });

        ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

        btn_hold.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    name = et_username.getText().toString();
                    phone = et_userphone.getText().toString();
                    String dizhi = tv_city.getText().toString().trim();
                    address = et_address.getText().toString();

                    if (name.equals("")) {
                        Toast.makeText(getApplicationContext(), "请填写用户名", Toast.LENGTH_SHORT)
                                .show();
                    } else if (phone.equals("")) {
                        Toast.makeText(getApplicationContext(), "请填写联系电话", Toast.LENGTH_SHORT)
                                .show();
                    } else if (phone.length() < 11) {
                        Toast.makeText(getApplicationContext(), "手机号码少于11位",
                                Toast.LENGTH_SHORT).show();
                    } else if (dizhi.equals("")) {
                        Toast.makeText(getApplicationContext(), "请填写收货地址", Toast.LENGTH_SHORT)
                                .show();
                    } else if (address.equals("")) {
                        Toast.makeText(getApplicationContext(), "请填写详细收货地址",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        progress.CreateProgress();

                        String id = spPreferences.getString("user_id", "");
                        String user_name = spPreferences.getString("user", "");

                        // String pingjiedizhi = sheng + "、" + shi + "、" + xian;

                        strUrl = URLConstants.REALM_NAME_LL
                                + "/edit_user_shopping_address?user_address_id="
                                + user_address_id + "&user_id=" + id
                                + "&user_name=" + user_name + ""
                                + "&user_accept_name=" + name
                                + "&user_province=" + cityTxt1 + ""
                                + "&user_city=" + cityTxt2 + "" + "&user_area="
                                + cityTxt3 + "&user_street=&user_address="
                                + address + "&user_mobile=" + phone
                                + "&user_telphone="
                                + "&user_email=&user_post_code=&is_default=" + is_default + "";

                        // strUrl = URLConstants.REALM_NAME_LL
                        // + "/add_user_shopping_address?user_id="
                        // + id
                        // + "&user_name="
                        // + user_name+
                        // "&order_no=&sign=&accept_name=&province=&city=&area="
                        // + pingjiedizhi + "address=" + address
                        // + "&post_code&mobile=" + phone + "&telphone="
                        // + "&email=";
                        System.out.println("11================" + sheng);
                        System.out.println("11================" + shi);
                        System.out.println("11================" + xian);
                        System.out.println("strUrl================" + strUrl);
                        AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                            public void onSuccess(int arg0, String arg1) {
                                try {
                                    JSONObject object = new JSONObject(arg1);
                                    System.out.println("0================"
                                            + arg1);
                                    String status = object.getString("status");
                                    // String info = object.getString("info");
                                    if (status.equals("y")) {
                                        System.out.println("1================");
                                        String msg = object.getString("info");
                                        Log.v("data1", msg);
                                        Message message = new Message();
                                        message.what = 0;
                                        message.obj = msg;
                                        handler.sendMessage(message);
                                    } else {
                                        System.out.println("2================");
                                        String msg = object.getString("info");
                                        Message message2 = new Message();
                                        message2.what = 1;
                                        message2.obj = msg;
                                        handler.sendMessage(message2);
                                    }
                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }
                            }

                            ;

                            public void onFailure(Throwable arg0, String arg1) {
                                System.out.println("3================" + arg0);
                                System.out.println("3================" + arg1);

                            }

                            ;

                        }, null);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 选择城市
     *
     * @return
     */
    private View dialogm() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.wheelcity_cities_layout, null);
        final WheelView country = (WheelView) contentView
                .findViewById(R.id.wheelcity_country);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(this));

        final String cities[][] = AddressData.CITIES;
        final String ccities[][][] = AddressData.COUNTIES;
        final WheelView city = (WheelView) contentView
                .findViewById(R.id.wheelcity_city);
        city.setVisibleItems(0);

        // 地区选择
        final WheelView ccity = (WheelView) contentView
                .findViewById(R.id.wheelcity_ccity);
        ccity.setVisibleItems(0);// 不限城市

        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities(city, cities, newValue);
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                cityTxt1 = AddressData.PROVINCES[country.getCurrentItem()];
            }
        });

        city.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updatecCities(ccity, ccities, country.getCurrentItem(),
                        newValue);
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                cityTxt2 = AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()];
            }
        });

        ccity.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
                        + "、"
                        + AddressData.CITIES[country.getCurrentItem()][city
                        .getCurrentItem()]
                        + "、"
                        + AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
                cityTxt3 = AddressData.COUNTIES[country.getCurrentItem()][city
                        .getCurrentItem()][ccity.getCurrentItem()];
            }
        });

        country.setCurrentItem(1);// 设置北京
        city.setCurrentItem(1);
        ccity.setCurrentItem(1);
        return contentView;
    }

    /**
     * Updates the city wheel
     */
    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                cities[index]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    /**
     * Updates the ccity wheel
     */
    private void updatecCities(WheelView city, String ccities[][][], int index,
                               int index2) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                ccities[index][index2]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    /**
     * Adapter for countries
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        private String countries[] = AddressData.PROVINCES;

        /**
         * Constructor
         */
        protected CountryAdapter(Context context) {
            super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
            setItemTextResource(R.id.wheelcity_country_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return countries.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return countries[index];
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getAppManager().finishActivity();
        }
        return super.onKeyDown(keyCode, event);

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
