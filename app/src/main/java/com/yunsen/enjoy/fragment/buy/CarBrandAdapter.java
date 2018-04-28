package com.yunsen.enjoy.fragment.buy;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.CarBrand;
import com.yunsen.enjoy.model.CarBrandList;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ItemViewDelegate;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/28.
 */

public class CarBrandAdapter extends MultiItemTypeAdapter<CarBrand> {
    private final LayoutInflater mInflater;
    private int mTitleLayoutId;
    private int mContentLayoutId;
    private int mItemSize;

    public CarBrandAdapter(Context context, List<CarBrand> datas) {
        super(context, datas);
        mInflater = LayoutInflater.from(context);
        mTitleLayoutId = R.layout.select_brand_item_title;
        mContentLayoutId = R.layout.select_brand_item_content;

        addItemViewDelegate(new ItemViewDelegate<CarBrand>() {
            @Override
            public int getItemViewLayoutId() {
                return mTitleLayoutId;
            }

            @Override
            public boolean isForViewType(CarBrand item, int position) {
                return !TextUtils.isEmpty(item.getFlag());
            }

            @Override
            public void convert(ViewHolder holder, CarBrand t, int position) {
                CarBrandAdapter.this.convertTitle(holder, t, position);
            }
        });

        addItemViewDelegate(new ItemViewDelegate<CarBrand>() {
            @Override
            public int getItemViewLayoutId() {
                return mContentLayoutId;
            }

            @Override
            public boolean isForViewType(CarBrand item, int position) {
                return TextUtils.isEmpty(item.getFlag());
            }

            @Override
            public void convert(ViewHolder holder, CarBrand carBrand, int position) {
                CarBrandAdapter.this.convertContent(holder, carBrand, position);
            }
        });
    }

    private void convertTitle(ViewHolder holder, CarBrand brand, int position) {
        holder.setText(R.id.brand_name_first, brand.getTitle());
        holder.setText(R.id.brand_word, brand.getFlag());
        ImageView view = holder.getView(R.id.brand_ic_first);
        Glide.with(mContext)
                .load(brand.getImg_url())
                .placeholder(R.mipmap.brand_default)
                .centerCrop()
                .into(view);

    }


    private void convertContent(ViewHolder holder, CarBrand brand, int position) {
        holder.setText(R.id.brand_name, brand.getTitle());
        ImageView view = holder.getView(R.id.brand_ic);
        Glide.with(mContext)
                .load(brand.getImg_url())
                .placeholder(R.mipmap.brand_default)
                .centerCrop()
                .into(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void upDatas(CarBrandList datas) {
        if (datas != null) {
            List<CarBrand> listA = datas.getListA();
            if (listA != null) {
                mDatas.addAll(listA);
            }
            List<CarBrand> listB = datas.getListB();
            if (listB != null) {
                mDatas.addAll(listB);
            }
            List<CarBrand> listC = datas.getListC();
            if (listC != null) {
                mDatas.addAll(listC);
            }
            List<CarBrand> listD = datas.getListD();
            if (listD != null) {
                mDatas.addAll(listD);
            }
            List<CarBrand> listE = datas.getListE();
            if (listE != null) {
                mDatas.addAll(listE);
            }
            List<CarBrand> listF = datas.getListF();
            if (listF != null) {
                mDatas.addAll(listF);
            }
            List<CarBrand> listG = datas.getListG();
            if (listG != null) {
                mDatas.addAll(listG);
            }
            List<CarBrand> listH = datas.getListH();
            if (listH != null) {
                mDatas.addAll(listH);
            }
            List<CarBrand> listI = datas.getListI();
            if (listI != null) {
                mDatas.addAll(listI);
            }
            List<CarBrand> listJ = datas.getListJ();
            if (listJ != null) {
                mDatas.addAll(listJ);
            }
            List<CarBrand> listK = datas.getListK();
            if (listK != null) {
                mDatas.addAll(listK);
            }
            List<CarBrand> listL = datas.getListL();
            if (listL != null) {
                mDatas.addAll(listL);
            }
            List<CarBrand> listM = datas.getListM();
            if (listM != null) {
                mDatas.addAll(listM);
            }
            List<CarBrand> listN = datas.getListN();
            if (listN != null) {
                mDatas.addAll(listN);
            }
            List<CarBrand> listO = datas.getListO();
            if (listO != null) {
                mDatas.addAll(listO);
            }
            List<CarBrand> listP = datas.getListP();
            if (listP != null) {
                mDatas.addAll(listP);
            }
            List<CarBrand> listQ = datas.getListQ();
            if (listQ != null) {
                mDatas.addAll(listQ);
            }
            List<CarBrand> listR = datas.getListR();
            if (listR != null) {
                mDatas.addAll(listR);
            }
            List<CarBrand> listS = datas.getListS();
            if (listS != null) {
                mDatas.addAll(listS);
            }
            List<CarBrand> listT = datas.getListT();
            if (listT != null) {
                mDatas.addAll(listT);
            }
            List<CarBrand> listU = datas.getListU();
            if (listU != null) {
                mDatas.addAll(listU);
            }
            List<CarBrand> listV = datas.getListV();
            if (listV != null) {
                mDatas.addAll(listV);
            }
            List<CarBrand> listW = datas.getListW();
            if (listW != null) {
                mDatas.addAll(listW);
            }
            List<CarBrand> listX = datas.getListX();
            if (listX != null) {
                mDatas.addAll(listX);
            }
            List<CarBrand> listY = datas.getListY();
            if (listY != null) {
                mDatas.addAll(listY);
            }
            List<CarBrand> listZ = datas.getListZ();
            if (listZ != null) {
                mDatas.addAll(listZ);
            }
            this.notifyDataSetChanged();
        }
    }


}
