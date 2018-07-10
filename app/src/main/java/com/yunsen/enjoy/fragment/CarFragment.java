package com.yunsen.enjoy.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.text.TextUtils;
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
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.ToastUtils;
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
    private LinearLayout adv_pager, ll_tjsp;
    private LinearLayout ll_xianshi;

    List<DataBean> result; //购物车商品列表
    private List<DataBean> mListData = new ArrayList<DataBean>();// 数据

    private boolean isBatchModel;// 是否可删除模式 true 删除 false 结算

    private RelativeLayout mBottonLayout;

    private CheckBox mCheckAll; // 全选 全不选

    private TextView mEdit; // 切换到删除模式

    private TextView mPriceAll; // 商品总价

    private int mSelectNum; // 选中数量

    private TextView mFavorite; // 移到收藏夹,分享

    private TextView mDelete; // 删除 结算

    private TextView subtitle;

    ImageView imageView1;

    private double totalPrice = 0; // 商品总价

    public double dzongjia = 0;

    public static StringBuffer str;

    private Button btn_register;

    private MyGridView myGridView;

    private GouWuCheAGoodsAdaper jdhadapter;

    private SharedPreferences mSP;

    private String user_id;

    private String login_sign;

    /**
     * 热销专区
     */
    private List<CarDetails> mLists;
    private String mUserName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gouwuche;
    }

    @Override
    protected void initView() {
        imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);
        imageView1.setImageResource(R.drawable.zams_gwc);
        myGridView = (MyGridView) rootView.findViewById(R.id.gridView);
        myGridView.setFocusable(false);
        adv_pager = (LinearLayout) rootView.findViewById(R.id.adv_pager);
        ll_xianshi = (LinearLayout) rootView.findViewById(R.id.ll_xianshi);
        mBottonLayout = (RelativeLayout) rootView.findViewById(R.id.cart_rl_allprie_total);
        mCheckAll = (CheckBox) rootView.findViewById(R.id.check_box_all);
        mEdit = (TextView) rootView.findViewById(R.id.subtitle);
        mPriceAll = (TextView) rootView.findViewById(R.id.tv_cart_total);
        mFavorite = (TextView) rootView.findViewById(R.id.tv_cart_move_favorite);
        mDelete = (TextView) rootView.findViewById(R.id.tv_cart_buy_or_del);
        subtitle = (TextView) rootView.findViewById(R.id.subtitle);
        mListView = (ListView) rootView.findViewById(R.id.listview);
        mListView.setSelector(R.drawable.list_selector);
        btn_register = (Button) rootView.findViewById(R.id.btn_register);
        ll_tjsp = (LinearLayout) rootView.findViewById(R.id.ll_tjsp);
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<CarDetails>();
        result = new ArrayList<DataBean>();
        mSP = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        user_id = mSP.getString(SpConstants.USER_ID, "");
        login_sign = mSP.getString(SpConstants.LOGIN_SIGN, "");
        mUserName = mSP.getString(SpConstants.USER_NAME, "");
        load_list();
    }

    @Override
    protected void requestData() {
    }


    @Override
    protected void initListener() {
        mEdit.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mCheckAll.setOnClickListener(this);
        ll_tjsp.setOnClickListener(this);
        //购物车无商品去逛逛
        btn_register.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (AccountUtils.hasBoundPhone()) {
            getgouwuche();
        } else {
            adv_pager.setVisibility(View.VISIBLE);
            subtitle.setVisibility(View.GONE);
            mListView.setVisibility(View.GONE);
            ll_xianshi.setVisibility(View.GONE);
        }
        setQuantitySum();
    }

    /**
     * 获取购物车商品
     */
    private void getgouwuche() {
        //还原商品信息
        result.clear();
        totalPrice = 0;
        mCheckAll.setChecked(false);
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/get_shopping_cart?pageSize=500&pageIndex=1&user_id=" + user_id + ""
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject jsonObject = new JSONObject(arg1);
                            JSONArray jsot = jsonObject.getJSONArray("data");
                            if (jsot.length() > 0) {
                                DataBean dataBean;
                                for (int i = 0; i < jsot.length(); i++) {
                                    dataBean = new DataBean();
                                    JSONObject object = jsot.getJSONObject(i);
                                    dataBean.setId(object.getInt("id"));
                                    dataBean.setTitle(object.getString("title"));
                                    dataBean.setMarket_price(object.getString("market_price"));
                                    dataBean.setSell_price(object.getDouble("sell_price"));
                                    dataBean.setImg_url(object.getString("img_url"));
                                    dataBean.setQuantity(object.getInt("quantity"));
                                    dataBean.setArticle_id(object.getString("article_id"));
                                    dataBean.setGoods_id(object.getString("goods_id"));
                                    result.add(dataBean);
                                }
                                adv_pager.setVisibility(View.GONE);
                                subtitle.setVisibility(View.VISIBLE);
                                mListView.setVisibility(View.VISIBLE);
                                ll_xianshi.setVisibility(View.VISIBLE);

                            } else {
                                adv_pager.setVisibility(View.VISIBLE);
                                subtitle.setVisibility(View.GONE);
                                mListView.setVisibility(View.GONE);
                                ll_xianshi.setVisibility(View.GONE);
                            }
                            setQuantitySum();
                            mPriceAll.setText("¥" + totalPrice);
                            //    更新数据
                            loadData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, null);
    }

    private void loadData() {
        mListData.clear();
        mListData.addAll(result);
        refreshListView();
        count();
    }

    /**
     * 热销专区
     */
    private void load_list() {
        mLists.clear();
        HttpProxy.getGoodsPartsDatas(new HttpCallBack<List<CarDetails>>() {
            @Override
            public void onSuccess(List<CarDetails> responseData) {
                mLists.addAll(responseData);
                jdhadapter = new GouWuCheAGoodsAdaper(responseData, getActivity());
                myGridView.setAdapter(jdhadapter);
                myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        CarDetails details = mLists.get(arg2);
                        UIHelper.showGoodsDescriptionActivity(getActivity(), String.valueOf(details.getId()), details.getTitle());
                    }
                });
            }

            @Override
            public void onError(Request request, Exception e) {
            }
        });
    }

    /**
     * 刷新购物车列表
     */
    private void refreshListView() {
        if (mListAdapter == null) {
            mListAdapter = new ListAdapter();
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

    /**
     * 购物车列表
     *
     * @author Administrator
     */
    private class ListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.cart_list_item, null);
                holder.checkBox = (CheckBox) view.findViewById(R.id.check_box);
                holder.tv_size = (TextView) view.findViewById(R.id.tv_size);
                holder.image = (ImageView) view.findViewById(R.id.iv_adapter_list_pic);
                holder.content = (TextView) view.findViewById(R.id.tv_intro);
                holder.carNum = (TextView) view.findViewById(R.id.tv_num);
                holder.price = (TextView) view.findViewById(R.id.tv_price);
                holder.add = (TextView) view.findViewById(R.id.tv_add);
                holder.red = (TextView) view.findViewById(R.id.tv_reduce);
                holder.frontView = view.findViewById(R.id.item_left);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            final DataBean data = mListData.get(position);
            bindListItem(holder, data);
            //			mListData.get(position).setChoose(true);
            if (data != null) {
                // 判断是否选择
                holder.checkBox.setChecked(data.isChoose());
                // 选中操作
                //				holder.checkBox.setOnClickListener(new CheckBoxOnClick(data));
                // 减少操作
                holder.red.setOnClickListener(new ReduceOnClick(data, holder.carNum));
                // 增加操作
                holder.add.setOnClickListener(new AddOnclick(data, holder.carNum));
            }
            return view;
        }

        /**
         * 点击item 后是否选中checkBox
         *
         * @param position
         */
        public void setSelected(int position) {
            if (position >= 0 && position < mListData.size()) {
                DataBean bean = mListData.get(position);
                bean.setChoose(!bean.isChoose());
            }
            this.notifyDataSetChanged();
        }

        private class AddOnclick implements View.OnClickListener {
            DataBean shopcartEntity;
            TextView carNumTv;

            private AddOnclick(DataBean shopcartEntity, TextView carNumtv) {
                this.shopcartEntity = shopcartEntity;
                this.carNumTv = carNumtv;
            }

            @Override
            public void onClick(View arg0) {
                shopcartEntity.setChoose(true);
                String numberStr = carNumTv.getText().toString();
                if (!TextUtils.isEmpty(numberStr)) {
                    int number = Integer.parseInt(numberStr);
                    int currentNum = number + 1;
                    // 设置列表
                    shopcartEntity.setQuantity(currentNum);
                    carNumTv.setText("" + currentNum);
                    int cart_id = shopcartEntity.getId();
                    AsyncHttp.get(URLConstants.REALM_NAME_LL + "/cart_goods_update?cart_id=" + cart_id + "&user_id=" + user_id + "&quantity=" + currentNum + "", new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                        }
                    }, getActivity());
                }
                notifyDataSetChanged();
                count();
            }

        }

        private class ReduceOnClick implements View.OnClickListener {
            DataBean shopcartEntity;
            TextView carNumTv;

            private ReduceOnClick(DataBean shopcartEntity, TextView carNumTv) {
                this.shopcartEntity = shopcartEntity;
                this.carNumTv = carNumTv;
            }

            @Override
            public void onClick(View arg0) {
                shopcartEntity.setChoose(true);
                String numberStr = carNumTv.getText().toString();
                if (!TextUtils.isEmpty(numberStr)) {
                    int number = Integer.parseInt(numberStr);
                    if (number == 1) {
                        ToastUtils.makeTextShort("不能往下减少了");
                    } else {
                        int currentNum = number - 1;
                        // 设置列表
                        shopcartEntity.setQuantity(currentNum);
                        carNumTv.setText("" + currentNum);
                        int cart_id = shopcartEntity.getId();
                        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/cart_goods_update?cart_id=" + cart_id + "&user_id=" + user_id + "&quantity=" + currentNum + "", new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String arg1) {
                                super.onSuccess(arg0, arg1);
                            }
                        }, getActivity());
                    }
                }
                notifyDataSetChanged();
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
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mListAdapter.setSelected(position);
            count();
            setQuantitySum();
            BigDecimal c = new BigDecimal(totalPrice);
            dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            mPriceAll.setText("¥" + dzongjia);
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.subtitle:
                isBatchModel = !isBatchModel;
                if (isBatchModel) {//删除商品
                    mEdit.setText(getResources().getString(R.string.menu_enter));
                    mDelete.setText(getResources().getString(R.string.menu_del));
                    mBottonLayout.setVisibility(View.VISIBLE);
                    mFavorite.setVisibility(View.GONE);
                    BigDecimal c = new BigDecimal(totalPrice);
                    dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    mPriceAll.setText("¥" + dzongjia);
                    setQuantitySum();
                } else {
                    mEdit.setText(getResources().getString(R.string.menu_edit));
                    mDelete.setText(getResources().getString(R.string.menu_sett));
                    mFavorite.setVisibility(View.GONE);
                    mBottonLayout.setVisibility(View.VISIBLE);
                    BigDecimal c = new BigDecimal(totalPrice);
                    dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    mPriceAll.setText("¥" + dzongjia);
                    setQuantitySum();
                }
                break;
            case R.id.check_box_all:
                totalPrice = 0;
                if (mCheckAll.isChecked()) {
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
                    }
                    //刷新
                    mListAdapter.notifyDataSetChanged();
                    BigDecimal c = new BigDecimal(totalPrice);
                    dzongjia = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    mPriceAll.setText("¥" + dzongjia);
                    setQuantitySum();
                }
                break;

            case R.id.tv_cart_buy_or_del:  //结算 或删除
                if (isBatchModel) {  //删除
                    int size = mListData.size();
                    for (int i = 0; i < size; i++) {
                        if (mListData.get(i).isChoose()) {
                            String strUrl = URLConstants.REALM_NAME_LL + "/cart_goods_delete?" + "clear=0&user_id=" + user_id + "&cart_id=" + mListData.get(i).getId();
                            AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int arg0, String arg1) {
                                    super.onSuccess(arg0, arg1);
                                    try {
                                        JSONObject object = new JSONObject(arg1);
                                        String status = object.getString("status");
                                        String info = object.getString("info");
                                        if ("y".equals(status)) {
                                            ToastUtils.makeTextShort(info);
                                            getgouwuche();
                                        } else {
                                            ToastUtils.makeTextShort(info);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, getActivity());

                        }
                    }
                    setQuantitySum();
                } else {
                    String articleId = "";
                    String goodId = "";
                    String quantity = "";
                    for (int i = 0; i < mListData.size(); i++) {
                        if (mListData.get(i).isChoose()) {
                            String fegefu = articleId.length() > 0 ? "," : "";
                            articleId = articleId + fegefu + String.valueOf(mListData.get(i).getArticle_id());
                            goodId = goodId + fegefu + String.valueOf(mListData.get(i).getGoods_id());
                            quantity = quantity + fegefu + String.valueOf(mListData.get(i).getQuantity());
                        }
                    }

                    if (TextUtils.isEmpty(articleId)) {
                        ToastUtils.makeTextShort("请选择要支付的商品");
                    } else {
                        loadgouwuche(articleId, goodId, quantity);
                    }
                }
                break;
            case R.id.ll_tjsp:
                UIHelper.showPartsShopActivity(getActivity()); //去配件商城页面
                break;
            case R.id.btn_register:  //首页
                if (!AccountUtils.hasLogin()) {
                    UIHelper.showUserLoginActivity(getActivity());
                } else if (!AccountUtils.hasBoundPhone()) {
                    UIHelper.showBundPhoneActivity(getActivity());
                } else if (!AccountUtils.hasVIP()) {
                    DialogUtils.showBecomeVipDialog(getActivity());
                } else {
                    UIHelper.showHomeShopCar(getActivity());
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
            mPriceAll.setText("¥" + dzongjia);
            setQuantitySum();
        }
    }

    /**
     * 计算选中数量
     */
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
            mSelectNum = quantitySum;
            if (isBatchModel) {
                subtitle.setText(R.string.menu_edit);
                mDelete.setText(getResources().getString(R.string.menu_del) + "(" + quantitySum + ")");
            } else {
                subtitle.setText(R.string.menu_enter);
                mDelete.setText(getResources().getString(R.string.menu_sett) + "(" + quantitySum + ")");
            }
        }
    }

    /**
     * 购物清单
     *
     * @param article_id
     * @param goods_id
     * @param quantity
     */
    private void loadgouwuche(String article_id, String goods_id, String quantity) {
        AsyncHttp.get(URLConstants.REALM_NAME_LL + "/add_shopping_buys?user_id=" + user_id + "&user_name=" + mUserName +
                        "&user_sign=" + login_sign + "&article_id=" + article_id + "&goods_id=" + goods_id + "&quantity=" + quantity,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject jsonObject = new JSONObject(arg1);
                            String status = jsonObject.getString("status");
                            String info = jsonObject.getString("info");
                            if (status.equals("y")) {
                                JSONObject obj = jsonObject.getJSONObject("data");
                                String buy_no = obj.getString("buy_no");
                                String count = obj.getString("count");
                                Intent intent = new Intent(getActivity(), MyOrderConfrimActivity.class);
                                intent.putExtra("buy_no", buy_no);
                                startActivity(intent);
                            } else {
                                ToastUtils.makeTextShort(info);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                    }
                }, getActivity());
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
