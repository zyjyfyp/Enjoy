package com.yunsen.enjoy.fragment;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.MainActivity;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.fragment.home.HomeGoodsAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.ImgAndTextModel;
import com.yunsen.enjoy.model.NoticeModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.ADTextView;
import com.yunsen.enjoy.widget.HorizontalLayout3;
import com.yunsen.enjoy.widget.HorizontalLayout4;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;


/**
 * 首页
 */
public class MainPagerFragment extends BaseFragment implements View.OnClickListener, MultiItemTypeAdapter.OnItemClickListener {

    private AutoLoopViewPager banner;
    private CirclePageIndicator indicatorLayout;
    private BannerAdapter bannerAdapter;
    private ADTextView adtTv1;
    private RecyclerView recyclerView;
    private View topView;
    private HomeGoodsAdapter mAdapter;

    private List<AdvertModel> mAdverModels = new ArrayList<>();
    private HorizontalLayout3 oneHLayout;
    private HorizontalLayout4 twoHLayout;
    private List<ImgAndTextModel> mBottomDatas;
    private View leftSearchImg;
    private View rightMenu;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView() {
        leftSearchImg = rootView.findViewById(R.id.left_search_img);
        rightMenu = rootView.findViewById(R.id.right_menu);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        topView = inflater.inflate(R.layout.fragment_top_layout, null);
        banner = (AutoLoopViewPager) topView.findViewById(R.id.pager);
        indicatorLayout = ((CirclePageIndicator) topView.findViewById(R.id.indicator));
        adtTv1 = (ADTextView) topView.findViewById(R.id.adt_text1);
        oneHLayout = topView.findViewById(R.id.one_horizontal_layout_3);
        twoHLayout = topView.findViewById(R.id.two_horizontal_layout_4);
    }

    @Override
    protected void initData() {

        GridLayoutManager layoutmanager = new GridLayoutManager(getActivity(), 2);
        //设置RecyclerView 布局
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);

        mBottomDatas = getOneHLayoutData();
        mAdapter = new HomeGoodsAdapter(getActivity(), R.layout.home_goods_item, mBottomDatas);
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(recyclerView, topView);
        bannerAdapter = new BannerAdapter(getData(), getActivity());
        banner.setAdapter(bannerAdapter);
        indicatorLayout.setViewPager(banner);
        indicatorLayout.setPadding(5, 5, 10, 5);
        oneHLayout.setData(getOneHLayoutData());
        twoHLayout.setData(getTwoHLayoutData());
    }


    @Override
    protected void initListener() {
        leftSearchImg.setOnClickListener(this);
        rightMenu.setOnClickListener(this);
        adtTv1.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
        oneHLayout.setmListener(new HorizontalLayout3.onHorizontalItemClick() {
            @Override
            public void onItemClick(int index) {
                ToastUtils.makeTextShort("" + index);
            }
        });
        twoHLayout.setmListener(new HorizontalLayout4.onHorizontalItemClick() {
            @Override
            public void onItemClick(int index) {
                ToastUtils.makeTextShort("" + index);
            }
        });
    }

    private static final String TAG = "MainPagerFragment";

    @Override
    protected void requestData() {
        HttpProxy.getHomeAdvertList(11, new HttpCallBack<List<AdvertModel>>() {
            @Override
            public void onSuccess(List<AdvertModel> responseData) {
                bannerAdapter = new BannerAdapter(responseData, getActivity());// TODO: 2018/4/20 need upData方法
                banner.setAdapter(bannerAdapter);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });

        //公告1
        HttpProxy.getNoticeData1(new HttpCallBack<List<NoticeModel>>() {
            @Override
            public void onSuccess(List<NoticeModel> responseData) {
                adtTv1.setResources(responseData);
                adtTv1.setTextStillTime(5000, 1);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }


    public List<AdvertModel> getData() {
        ArrayList<AdvertModel> data = new ArrayList<>();
        data.add(new AdvertModel(R.mipmap.adv_home, null));
        data.add(new AdvertModel(R.mipmap.adv_home, "http://pic71.nipic.com/file/20150610/13549908_104823135000_2.jpg"));
        data.add(new AdvertModel(R.mipmap.adv_home, "http://img07.tooopen.com/images/20170316/tooopen_sy_201956178977.jpg"));
        data.add(new AdvertModel(R.mipmap.adv_home, "http://img.zcool.cn/community/010a1b554c01d1000001bf72a68b37.jpg@1280w_1l_2o_100sh.png"));
        return data;
    }

    private String getAdverModelUrl(int index) {
        if (mAdverModels.size() > index) {
            return mAdverModels.get(index).getLink_url();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_search_img:
                UIHelper.showSearchActivity(getActivity());
                break;
            case R.id.right_menu:
                break;
            case R.id.adt_text1:
                NoticeModel data = adtTv1.getCurrentData();
                UIHelper.showNoticeWebActivity(getActivity(), data.getId());
                break;

        }

    }

    /**
     * 去买车界面
     */
    private void toBuyCarFragment() {
        ((MainActivity) getActivity()).setCurrIndex(1);
    }

    private void toBuyCarFragment(String data) {
        ((MainActivity) getActivity()).setCurrIndex(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoScroll();
        adtTv1.onStartAuto(1);
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoScroll();
        adtTv1.onStopAuto(1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        List<ImgAndTextModel> datas = mAdapter.getDatas();
        int pp = position - 1;
        if (datas != null && position > 0 && datas.size() > pp) {
            ToastUtils.makeTextShort(datas.get(pp).getText() + pp);
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    private List<ImgAndTextModel> getOneHLayoutData() {
        ArrayList<ImgAndTextModel> models = new ArrayList<>();
        models.add(new ImgAndTextModel(R.mipmap.repertory, "企业清仓", 0));
        models.add(new ImgAndTextModel(R.mipmap.change_product, "换产品", 1));
        models.add(new ImgAndTextModel(R.mipmap.change_service, "换服务", 2));
        models.add(new ImgAndTextModel(R.mipmap.change_card, "换卡卷", 3));
        return models;

    }

    private List<ImgAndTextModel> getTwoHLayoutData() {
        ArrayList<ImgAndTextModel> models = new ArrayList<>();
        models.add(new ImgAndTextModel(R.mipmap.apply_agent, "申请代理", 0));
        models.add(new ImgAndTextModel(R.mipmap.what_operate, "如何经营", 1));
        models.add(new ImgAndTextModel(R.mipmap.what_profit, "如何收益", 2));
        return models;
    }

}
