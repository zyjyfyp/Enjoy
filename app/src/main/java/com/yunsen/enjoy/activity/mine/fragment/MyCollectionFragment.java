package com.yunsen.enjoy.activity.mine.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.fragment.buy.FilterRecAdapter;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.CollectWareData;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import okhttp3.Request;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/5/15.
 */

public class MyCollectionFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener {
    private ArrayList<CollectWareData> list;
    private DialogProgress progress;
    private SharedPreferences spPreferences;
    private RecyclerView recyclerView;
    private String strUrl;
    private TextView noCollectTv;
    private ArrayList<GoodsData> mDatas;
    private FilterRecAdapter mAdapter;
    private String mUserId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_collection;
    }

    @Override
    protected void initView() {
        progress = new DialogProgress(getActivity());
        spPreferences = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.collect_recycler);
        noCollectTv = (TextView) rootView.findViewById(R.id.no_collect_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatas = new ArrayList<>();
        mAdapter = new FilterRecAdapter(getActivity(), R.layout.goods_item_2, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        SharedPreferences sp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        mUserId = sp.getString(SpConstants.USER_ID, "");

    }

    @Override
    protected void requestData() {
        HttpProxy.getCollectList(mUserId, "1", new HttpCallBack<List<GoodsData>>() {
            @Override
            public void onSuccess(List<GoodsData> responseData) {
                if (responseData == null) {
                    noCollectTv.setVisibility(View.VISIBLE);
                } else {
                    noCollectTv.setVisibility(View.GONE);
                }
                mAdapter.upData(responseData);

            }

            @Override
            public void onError(Request request, Exception e) {
                noCollectTv.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > 0 && mDatas.size() > position) {
            GoodsData goodsData = mDatas.get(position);
            UIHelper.showCarDetailsActivity(getActivity(), Integer.toString(goodsData.getArticle_id()));
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (mDatas != null && mDatas.size() > 0 && mDatas.size() > position) {
            GoodsData goodsData = mDatas.get(position);
            dialog(goodsData.getId(), position);
        }
        return true;
    }

    protected void dialog(final int goodId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认删除该收藏？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCollect(goodId, position);
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

    private void deleteCollect(int goodId, final int position) {
        HttpProxy.getDeleteCollect(mUserId, String.valueOf(goodId), new HttpCallBack<String>() {
            @Override
            public void onSuccess(String responseData) {
                ToastUtils.makeTextShort("取消收藏");
                mAdapter.removePosition(position);
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }
}
