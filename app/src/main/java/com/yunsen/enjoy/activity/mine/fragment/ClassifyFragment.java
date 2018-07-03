package com.yunsen.enjoy.activity.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.ClassifyActivity;
import com.yunsen.enjoy.activity.mine.adapter.GoodsClassifyAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.MyAlertDialog;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/2.
 */

public class ClassifyFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private GoodsClassifyAdapter mAdapter;
    private int fragmentTag; //0 , 1, 2
    private ArrayList<String> mDatas;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatas = new ArrayList<>();
        mAdapter = new GoodsClassifyAdapter(getActivity(), R.layout.classify_item, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        fragmentTag = arguments.getInt(Constants.FRAGMENT_TYPE_KEY);
        ArrayList<String> datas = new ArrayList<>();
        datas.add(fragmentTag+"第一个");
        datas.add(fragmentTag+"第二个");
        datas.add(fragmentTag+"第三个");
        datas.add(fragmentTag+"第四个");
        datas.add(fragmentTag+"第五个");
        datas.add("第六个");
        datas.add("第七个");
        datas.add("第八个");
        datas.add("第九个");
        datas.add("第十个");
        mAdapter.upBaseDatas(datas);


    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (getActivity() instanceof ClassifyActivity) {
            switch (fragmentTag) {
                case 1:
                    ((ClassifyActivity) getActivity()).showFragmentTwo(mDatas.get(position));
                    break;
                case 2:
                    ((ClassifyActivity) getActivity()).showFragmentThree(mDatas.get(position));
                    break;
                case 3:
                    ((ClassifyActivity) getActivity()).submitClassifyData(mDatas.get(position));
                    break;
            }
            ToastUtils.makeTextShort(mDatas.get(position));
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
