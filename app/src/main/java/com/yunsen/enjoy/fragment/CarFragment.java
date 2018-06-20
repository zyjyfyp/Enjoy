package com.yunsen.enjoy.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.MyOrderConfrimActivity;
import com.yunsen.enjoy.activity.mine.adapter.GouWuCheAGoodsAdaper;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.DataBean;
import com.yunsen.enjoy.model.ShopCarCount;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.interfaces.OnRightOnclickListener;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/6/9.
 */

public class CarFragment extends BaseFragment implements View.OnClickListener {
    private static final int INITIALIZE = 0;
    private ListView mListView;// 列表
    private ListAdapter mListAdapter;// adapter
    private LinearLayout adv_pager;
    private LinearLayout ll_xianshi;
    private List<DataBean> mListData = new ArrayList<DataBean>();// 数据
    private boolean isBatchModel;// 是否可删除模式

    private CheckBox mCheckAll; // 全选 全不选

    private TextView mPriceAll; // 商品总价

    private TextView mSelectNum; // 选中数量

    private TextView mFavorite; // 移到收藏夹,分享

    private TextView mDelete; // 删除 结算

    public boolean ptye = false;
    private double totalPrice = 0; // 商品总价
    public static double dzongjia = 0;
    /**
     * 批量模式下，用来记录当前选中状态
     */
    private SparseArray<Boolean> mSelectState = new SparseArray<Boolean>();
    List<DataBean> result;
    private DialogProgress progress;
    private static List<String> list_id = new ArrayList<String>();
    private static List<String> list_size = new ArrayList<String>();
    String num = "1";
    public static StringBuffer str;
    private ImageView btn_register;
    public static boolean type = false;
    String user_name_phone = "";
    private SharedPreferences mSP;
    private String user_id;
    private String login_sign;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    protected void initView() {
        progress = new DialogProgress(getActivity());
        adv_pager = (LinearLayout) rootView.findViewById(R.id.adv_pager);
        ll_xianshi = (LinearLayout) rootView.findViewById(R.id.ll_xianshi);
        mCheckAll = (CheckBox) rootView.findViewById(R.id.check_box_all);
        mPriceAll = (TextView) rootView.findViewById(R.id.tv_cart_total);
        mFavorite = (TextView) rootView.findViewById(R.id.tv_cart_move_favorite);
        mDelete = (TextView) rootView.findViewById(R.id.tv_cart_buy_or_del);
        mListView = (ListView) rootView.findViewById(R.id.listview);
        mListView.setSelector(R.drawable.list_selector);
        btn_register = (ImageView) rootView.findViewById(R.id.btn_register);

        //购物车无商品去逛逛
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!AccountUtils.hasLogin()) {
                    UIHelper.showUserLoginActivity(getActivity());
                } else if (!AccountUtils.hasBoundPhone()) {
                    UIHelper.showBundPhoneActivity(getActivity());
                } else {
                    UIHelper.showHomeShopCar(getActivity());
                }
            }
        });
    }

    @Override
    protected void initData() {
        mSP = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        user_id = mSP.getString("user_id", "");
        login_sign = mSP.getString("login_sign", "");
    }

    @Override
    protected void requestData() {

    }


    @Override
    protected void initListener() {
        mDelete.setOnClickListener(this);
        mCheckAll.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (AccountUtils.hasBoundPhone()) {
            getgouwuche();
        } else {
            adv_pager.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            ll_xianshi.setVisibility(View.GONE);
        }

        //计算商品提交ID清空
        if (list_id.size() > 0) {
            list_id.clear();

        }
        //计算个数清空
        if (list_size.size() > 0) {
            list_size.clear();
            setQuantitySum();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    DataBean dm;

    private void getgouwuche() {

        if (!ptye) {
            progress.CreateProgress();
        }
        result = new ArrayList<DataBean>();
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_shopping_cart?pageSize=500&pageIndex=1&user_id=" + user_id + ""
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject jsonObject = new JSONObject(arg1);
                            System.out.println("购物车数据================");
                            //							System.out.println("购物车数据================"+arg1);
                            JSONArray jsot = jsonObject.getJSONArray("data");
                            System.out.println("jsot================" + jsot.length());
                            if (jsot.length() > 0) {
                                for (int i = 0; i < jsot.length(); i++) {
                                    dm = new DataBean();
                                    JSONObject object = jsot.getJSONObject(i);
                                    dm.setId(object.getInt("id"));
                                    dm.setTitle(object.getString("title"));
                                    dm.setMarket_price(object.getString("market_price"));
                                    dm.setSell_price(object.getDouble("sell_price"));
                                    dm.setImg_url(object.getString("img_url"));
                                    dm.setQuantity(object.getInt("quantity"));
                                    dm.setArticle_id(object.getString("article_id"));
                                    dm.setGoods_id(object.getString("goods_id"));
                                    result.add(dm);

                                }
                                dm = null;
                                progress.CloseProgress();
                                adv_pager.setVisibility(View.GONE);
                                mListView.setVisibility(View.VISIBLE);
                                ll_xianshi.setVisibility(View.VISIBLE);
                            } else {
                                progress.CloseProgress();
                                adv_pager.setVisibility(View.VISIBLE);
                                mListView.setVisibility(View.GONE);
                                ll_xianshi.setVisibility(View.GONE);
                            }
                            progress.CloseProgress();
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }

                }, null);

        totalPrice = 0;
        mPriceAll.setText("¥" + totalPrice);
        setQuantitySum();
        mCheckAll.setChecked(false);
        System.out.println("result22-------------" + result.size());
        loadData();
    }


    private void loadData() {
        new CarFragment.LoadDataTask().execute(new CarFragment.Params(INITIALIZE));
    }

    private void refreshListView() {
        if (mListAdapter == null) {
            mListAdapter = new CarFragment.ListAdapter();
            mListView.setAdapter(mListAdapter);
            mListView.setOnItemClickListener(mListAdapter);
            setListViewHeightBasedOnChildren(mListView);
        } else {
            try {
                mListAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(mListView);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    private List<DataBean> getData() {
        return result;
    }


    class Params {
        int op;

        public Params(int op) {
            this.op = op;
            System.out.println("result1-------------");
        }

    }

    class Result {
        int op;
        List<DataBean> list;
    }

    private class LoadDataTask extends AsyncTask<CarFragment.Params, Void, CarFragment.Result> {
        @Override
        protected CarFragment.Result doInBackground(CarFragment.Params... params) {
            CarFragment.Params p = params[0];
            CarFragment.Result result = new CarFragment.Result();
            result.op = p.op;
            try {// 模拟耗时
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.list = getData();
            return result;
        }

        @Override
        protected void onPostExecute(CarFragment.Result result) {
            super.onPostExecute(result);
            if (result.op == INITIALIZE) {
                mListData = result.list;
            } else {
                mListData.addAll(result.list);
                ToastUtils.makeTextShort("添加成功！");
            }
            refreshListView();
        }

    }

    boolean isSelect = false;

    /**
     * 商品列表
     *
     * @author Administrator
     */
    private class ListAdapter extends BaseAdapter implements
            AdapterView.OnItemClickListener {
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        CarFragment.ViewHolder holder = null;

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.cart_list_item, null);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
                holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
                holder.image = (ImageView) convertView.findViewById(R.id.iv_adapter_list_pic);
                holder.content = (TextView) convertView.findViewById(R.id.tv_intro);
                holder.carNum = (TextView) convertView.findViewById(R.id.tv_num);
                holder.price = (TextView) convertView.findViewById(R.id.tv_price);
                holder.add = (TextView) convertView.findViewById(R.id.tv_add);
                holder.red = (TextView) convertView.findViewById(R.id.tv_reduce);
                holder.deleteImg = (ImageView) convertView.findViewById(R.id.delete_img);
                holder.frontView = convertView.findViewById(R.id.item_left);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try {
                final DataBean data = mListData.get(position);
                bindListItem(holder, data);
                holder.deleteImg.setTag(data);
                if (data != null) {
                    // 判断是否选择
                    if (data.isChoose()) {
                        holder.checkBox.setChecked(true);
                    } else {
                        holder.checkBox.setChecked(false);
                    }

                    // 选中操作
                    //				holder.checkBox.setOnClickListener(new CheckBoxOnClick(data));
                    // 减少操作
                    holder.red.setOnClickListener(new ListAdapter.ReduceOnClick(data, holder.carNum));

                    // 增加操作
                    holder.add.setOnClickListener(new ListAdapter.AddOnclick(data, holder.carNum));
                    holder.deleteImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final View fView = view;
                            AlertDialog yesAndNoDialog = DialogUtils.createYesAndNoDialog(getActivity(), "", "先留着", "删除",
                                    null, new OnRightOnclickListener() {
                                        @Override
                                        public void onRightClick(int... index) {
                                            final DataBean data = (DataBean) fView.getTag();

                                            HttpProxy.deleteShopCarGoods(user_id, "" + data.getId(), new HttpCallBack<ShopCarCount>() {
                                                @Override
                                                public void onSuccess(ShopCarCount responseData) {
                                                    if (mListData.remove(data)) {
                                                        ListAdapter.this.notifyDataSetChanged();
                                                        count();
                                                    }
                                                }

                                                @Override
                                                public void onError(Request request, Exception e) {
                                                    ToastUtils.makeTextShort("异常删除失败");
                                                }
                                            });
                                        }
                                    });
                            yesAndNoDialog.show();
                            yesAndNoDialog.getButton(yesAndNoDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.color_theme));
                            yesAndNoDialog.getButton(yesAndNoDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                        }
                    });
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return convertView;
        }


        private class AddOnclick implements View.OnClickListener {

            DataBean shopcartEntity;
            TextView shopcart_number_btn;

            private AddOnclick(DataBean shopcartEntity,
                               TextView shopcart_number_btn) {
                this.shopcartEntity = shopcartEntity;
                this.shopcart_number_btn = shopcart_number_btn;

            }

            @Override
            public void onClick(View arg0) {
                shopcartEntity.setChoose(true);
                String numberStr = shopcart_number_btn.getText().toString();
                if (!TextUtils.isEmpty(numberStr)) {
                    int number = Integer.parseInt(numberStr);

                    int currentNum = number + 1;
                    // 设置列表
                    shopcartEntity.setQuantity(currentNum);
                    holder.carNum.setText("" + currentNum);
                    int cart_id = shopcartEntity.getId();
                    System.out.println("============cart_id==============" + cart_id);
                    AsyncHttp.get(URLConstants.REALM_NAME_LL + "/cart_goods_update?cart_id=" + cart_id + "&user_id=" + user_id + "&quantity=" + currentNum + "", new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            System.out.println("==========================访问接口成功！" + arg1);
                            super.onSuccess(arg0, arg1);
                        }

                    }, getActivity());
                    notifyDataSetChanged();
                }
                count();
            }

        }

        private class ReduceOnClick implements View.OnClickListener {
            DataBean shopcartEntity;
            TextView shopcart_number_btn;

            private ReduceOnClick(DataBean shopcartEntity, TextView shopcart_number_btn) {
                this.shopcartEntity = shopcartEntity;
                this.shopcart_number_btn = shopcart_number_btn;
            }

            @Override
            public void onClick(View arg0) {
                shopcartEntity.setChoose(true);
                String numberStr = shopcart_number_btn.getText().toString();
                if (!TextUtils.isEmpty(numberStr)) {
                    int number = Integer.parseInt(numberStr);
                    if (number == 1) {

                        ToastUtils.makeTextShort("不能往下减少了");
                    } else {
                        int currentNum = number - 1;
                        // 设置列表
                        shopcartEntity.setQuantity(currentNum);

                        holder.carNum.setText("" + currentNum);
                        int cart_id = shopcartEntity.getId();
                        System.out.println("============cart_id==============" + cart_id);
                        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/cart_goods_update?cart_id=" + cart_id + "&user_id=" + user_id + "&quantity=" + currentNum + "", new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String arg1) {
                                System.out.println("==========================2访问接口成功！" + arg1);
                                super.onSuccess(arg0, arg1);
                            }

                        }, getActivity());
                        notifyDataSetChanged();
                    }
                }
                count();
            }

        }

        private void bindListItem(CarFragment.ViewHolder holder, DataBean data) {
            holder.content.setText(data.getTitle());
            holder.price.setText("¥" + data.getSell_price());
            holder.carNum.setText(data.getQuantity() + "");
            holder.tv_size.setText("¥" + data.getMarket_price());
            holder.tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            Glide.with(CarFragment.this)
                    .load(data.getImg_url())
                    .into(holder.image);
            type = true;
            int _id = data.getId();
            boolean selected = mSelectState.get(_id, false);
            holder.checkBox.setChecked(selected);

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            DataBean bean = mListData.get(position);

            CarFragment.ViewHolder holder = (CarFragment.ViewHolder) view.getTag();
            int _id = (int) bean.getId();

            boolean selected = !mSelectState.get(_id, false);
            holder.checkBox.toggle();
            // 将CheckBox的选中状况记录下来
            mListData.get(position).setChoose(holder.checkBox.isChecked());
            // 调整选定条目
            if (holder.checkBox.isChecked()) {
                list_size.add(num);
                setQuantitySum();
                totalPrice += bean.getQuantity() * bean.getSell_price();
            } else {
                list_size.remove(num);
                setQuantitySum();
                mSelectState.delete(position);
                totalPrice -= bean.getQuantity() * bean.getSell_price();
            }
            BigDecimal c = new BigDecimal(totalPrice);
            dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            mPriceAll.setText("¥" + dzongjia + "");
            setQuantitySum();
            if (mSelectState.size() == mListData.size()) {
                mCheckAll.setChecked(true);
            } else {
                mCheckAll.setChecked(false);
            }

        }

    }


    class ViewHolder {
        CheckBox checkBox;
        ImageView image;
        TextView content, tv_size;
        TextView carNum;
        TextView price;
        TextView add;
        TextView red;
        View frontView;
        ImageView deleteImg;
    }

    List<Integer> list_num;
    List<Integer> list_num2;
    int i;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.check_box_all:
                totalPrice = 0;
                if (mCheckAll.isChecked()) {
                    list_size.clear();
                    for (int i = 0; i < mListData.size(); i++) {
                        mListData.get(i).setChoose(true);
                        // 如果为选中
                        if (mListData.get(i).isChoose()) {
                            totalPrice = totalPrice + mListData.get(i).getQuantity() * mListData.get(i).getSell_price();
                        }
                    }

                    // 刷新
                    mListAdapter.notifyDataSetChanged();
                    // 显示
                    BigDecimal c = new BigDecimal(totalPrice);
                    dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    mPriceAll.setText("¥" + dzongjia);
                    setQuantitySum();
                } else {
                    for (int i = 0; i < mListData.size(); i++) {
                        mListData.get(i).setChoose(false);
                        list_size.clear();
                    }
                    //刷新
                    mListAdapter.notifyDataSetChanged();
                    BigDecimal c = new BigDecimal(totalPrice);
                    dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    mPriceAll.setText("¥" + dzongjia);
                    setQuantitySum();
                }
                break;

            case R.id.tv_cart_buy_or_del:
                list_num = new ArrayList<Integer>();
                list_num2 = new ArrayList<Integer>();
                if (isBatchModel) {
                    if (list_size.size() == 0) {
                        if (mListData.get(i).isChoose()) {
                            System.out.println("i==========================" + i);
                            list_num.add(i);
                            String strUrl = URLConstants.REALM_NAME_LL + "/cart_goods_delete?" + "clear=0&user_id=" + user_id + "&cart_id=" + mListData.get(i).getId();
                            AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int arg0, String arg1) {

                                    System.out.println("==========================删除接口成功！" + arg1);
                                    super.onSuccess(arg0, arg1);
                                    try {

                                        JSONObject object = new JSONObject(arg1);
                                        String status = object.getString("status");
                                        String info = object.getString("info");
                                        if ("y".equals(status)) {
                                            ToastUtils.makeTextShort(info);
                                            ptye = true;
                                            System.out.println("list_size.size()==========================" + list_size.size());
                                            list_num2.add(i);
                                            System.out.println("list_num.size()==========================" + list_num.size());
                                            System.out.println("list_num2.size()==========================" + list_num2.size());
                                            if (list_num.size() == list_num2.size()) {
                                                getgouwuche();
                                                list_num.clear();
                                                list_num2.clear();
                                            }
                                        } else {
                                            ToastUtils.makeTextShort(info);
                                        }
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                    }
                                }

                            }, getActivity());

                        }

                    }

                    //计算个数清空
                    if (list_size.size() > 0) {
                        list_size.clear();
                        setQuantitySum();
                    }

                } else {
                    String str1 = "";
                    String str2 = "";
                    String str3 = "";
                    if (totalPrice != 0) {
                        for (int i = 0; i < mListData.size(); i++) {
                            if (mListData.get(i).isChoose()) {
                                String fegefu = str1.length() > 0 ? "," : "";
                                str1 = str1 + fegefu + String.valueOf(mListData.get(i).getArticle_id());
                                str2 = str2 + fegefu + String.valueOf(mListData.get(i).getGoods_id());
                                str3 = str3 + fegefu + String.valueOf(mListData.get(i).getQuantity());

                            }
                        }
                        System.out.println("str1-------------" + str1);
                        String zhou = str1 + "/" + str2 + "/" + str3;
                        if (str1.equals("")) {
                            ToastUtils.makeTextShort("请选择要支付的商品");
                            //							mListAdapter.notifyDataSetChanged();
                        } else {
                            loadgouwuche(str1, str2, str3);
                        }

                    } else {
                        ToastUtils.makeTextShort("请选择要支付的商品");
                        mListAdapter.notifyDataSetChanged();
                        return;
                    }
                }

                break;
            default:
                break;
        }

    }

    /**
     * 计算价格
     */
    public void count() {
        totalPrice = 0;// 人民币
        int quantitySum = 0;
        if (mListData != null && mListData.size() > 0) {
            for (int i = 0; i < mListData.size(); i++) {
                DataBean dataBean = mListData.get(i);
                if (dataBean.isChoose()) {
                    int quantity = dataBean.getQuantity();
                    totalPrice = totalPrice + quantity
                            * dataBean.getSell_price();
                    quantitySum += quantity;
                }
            }
            BigDecimal c = new BigDecimal(totalPrice);
            dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        mPriceAll.setText("¥" + dzongjia);
        setQuantitySum();
    }

    private void setQuantitySum() {
        int quantitySum = 0;
        if (mListData != null && mListData.size() > 0) {
            for (int i = 0; i < mListData.size(); i++) {
                DataBean dataBean = mListData.get(i);
                if (dataBean.isChoose()) {
                    int quantity = dataBean.getQuantity();
                    quantitySum += quantity;
                }
            }
        } else {
            adv_pager.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            mListView.setVisibility(View.GONE);
            ll_xianshi.setVisibility(View.GONE);
        }
        mDelete.setText(getResources().getString(R.string.menu_sett) + "(" + quantitySum + ")");
    }

    public void select() {
        int count = 0;
        for (int i = 0; i < mListData.size(); i++) {
            if (mListData.get(i).isChoose()) {
                count++;
            }
        }
        if (count == mListData.size()) {
            mCheckAll.setChecked(true);
        } else {
            isSelect = true;
            mCheckAll.setChecked(false);
        }

    }

    private void loadgouwuche(String str1, String str2, String str3) {
        try {
            progress.CreateProgress();
            AsyncHttp.get(URLConstants.REALM_NAME_LL + "/add_shopping_buys?user_id=" + user_id + "&user_name=" + user_name_phone +
                            "&user_sign=" + login_sign + "&article_id=" + str1 + "&goods_id=" + str2 + "&quantity=" + str3 + "",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {

                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject jsonObject = new JSONObject(arg1);
                                String status = jsonObject.getString("status");
                                System.out.println("购物清单================" + arg1);
                                String info = jsonObject.getString("info");
                                if (status.equals("y")) {
                                    progress.CloseProgress();
                                    JSONObject obj = jsonObject.getJSONObject("data");
                                    String buy_no = obj.getString("buy_no");
                                    String count = obj.getString("count");
                                    Intent intent = new Intent(getActivity(), MyOrderConfrimActivity.class);
                                    intent.putExtra("buy_no", buy_no);
                                    startActivity(intent);
                                } else {
                                    ToastUtils.makeTextShort(info);
                                }
                                progress.CloseProgress();
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {
                            super.onFailure(arg0, arg1);
                        }

                    }, getActivity());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        CarFragment.ListAdapter listAdapter = (CarFragment.ListAdapter) mListView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
