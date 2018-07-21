package com.yunsen.enjoy.activity.car.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.GoodsCarInfo;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.NoScrollLinearLayoutManager;
import com.yunsen.enjoy.widget.interfaces.GoodsSumInterface;
import com.yunsen.enjoy.widget.interfaces.TotalInterface;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/21/021.
 */

public class CarShoppingAdapter extends RecyclerView.Adapter<CarShoppingAdapter.ViewHolder> {
    private Context mContext;
    private int layoutId;
    private List<List<GoodsCarInfo>> mDatas;
    private TotalInterface totalInterface;
    private SparseArray<DShoppingCarAdapter> mLists = new SparseArray<>();

    public CarShoppingAdapter(Context mContext, int layoutId, List<List<GoodsCarInfo>> mData) {
        this.mContext = mContext;
        this.layoutId = layoutId;
        this.mDatas = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layoutId, null, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int fPositon = position;
        NoScrollLinearLayoutManager manager = new NoScrollLinearLayoutManager(mContext);
        holder.recyclerView.setLayoutManager(manager);
//        mLists.add(adapter);
        DShoppingCarAdapter adapter = mLists.get(position);
        List<GoodsCarInfo> carInfos = mDatas.get(position);
        if (adapter == null) {
            adapter = new DShoppingCarAdapter(mContext, R.layout.car_goods_item, carInfos);
            holder.recyclerView.setAdapter(adapter);
            mLists.put(position, adapter);
        } else {
            holder.recyclerView.setAdapter(adapter);
            adapter.upData(carInfos);
        }
        int size = carInfos.size();
        int i = 0;
        for (; i < size; i++) {
            if (!carInfos.get(i).isCheckGoods()) {
                break;
            }
        }
        holder.shopPersonCB.setChecked(i == size);
        holder.shopPersonName.setText("大道易客" + position);
        final DShoppingCarAdapter fAdapte = adapter;
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                if (adapter instanceof DShoppingCarAdapter) {
                    List<GoodsCarInfo> datas = ((DShoppingCarAdapter) adapter).getDatas();
                    if (datas != null && datas.size() > position) {
                        GoodsCarInfo data = datas.get(position);
                        UIHelper.showGoodsDescriptionActivity(mContext, String.valueOf(data.getArticle_id()), data.getTitle());
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        fAdapte.setGoodsSumCall(new GoodsSumInterface() {
            @Override
            public void GoodsSumCallBack(int goodsSum, double goodsPrices) {
                if (totalInterface != null) {
                    double goodsSumPrice = fAdapte.getGoodsSumPrice();
                    int goodsCount = fAdapte.getmGoodsCount();
                    totalInterface.upTotalData(goodsSumPrice, goodsCount, fPositon);
                    if (fAdapte.getItemCount() == 0) { //删除商家
                        mDatas.remove(fPositon);
                        CarShoppingAdapter.this.notifyDataSetChanged();
                        if (mDatas.size() == 0) {
                            totalInterface.upTotalData(0, 0, -1);
                        }
                    }
                }
            }

            @Override
            public void isCheckAll(boolean isCheckAll) {
                holder.shopPersonCB.setChecked(isCheckAll);
            }
        });

        holder.checkAllLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.shopPersonCB.setChecked(!holder.shopPersonCB.isChecked());
                fAdapte.setCheckAllOrNo(fAdapte, holder.shopPersonCB.isChecked());
                if (totalInterface != null) {
                    double goodsSumPrice = fAdapte.getGoodsSumPrice();
                    int goodsCount = fAdapte.getmGoodsCount();
                    totalInterface.upTotalData(goodsSumPrice, goodsCount, fPositon);
                }
            }
        });
    }

    private static final String TAG = "CarShoppingAdapter";

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public String[] getCurrentRequestDatas(int position) {
        DShoppingCarAdapter adapter = mLists.get(position);
        if (adapter != null) {
            return adapter.getSubmitRequestDatas();
        }
        return null;
    }

    /**
     * 提交订单界面的数据
     *
     * @param position
     * @return
     */
    public ArrayList<GoodsCarInfo> getCurrentRequestList(int position) {
        ArrayList<GoodsCarInfo> datas = new ArrayList<>();
        List<GoodsCarInfo> goodsCarInfos = mDatas.get(position);
        int size = goodsCarInfos.size();
        for (int i = 0; i < size; i++) {
            if (goodsCarInfos.get(i).isCheckGoods()) {
                datas.add(goodsCarInfos.get(i));
            }
        }
        return datas;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox shopPersonCB;
        TextView shopPersonName;
        RecyclerView recyclerView;
        RelativeLayout checkAllLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            checkAllLayout = itemView.findViewById(R.id.layout_1);
            shopPersonCB = itemView.findViewById(R.id.select_all_goods_cb);
            shopPersonName = itemView.findViewById(R.id.shop_person_name);
        }
    }

    public void upBaseDatas(List<List<GoodsCarInfo>> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }

        this.notifyDataSetChanged();
    }

    public void setTotalInterface(TotalInterface totalInterface) {
        this.totalInterface = totalInterface;
    }


}
