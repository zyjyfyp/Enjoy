package com.yunsen.enjoy.activity.mine.adapter;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.BuildConfig;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.OneNoticeInfoBean;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ItemViewDelegate;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12/012.
 * 消息列表
 */

public class NoticeListAdapter extends MultiItemTypeAdapter {
    private final LayoutInflater mInflater;
    private final int mOneLayoutId;
    private final int mThreeLayoutId;
    private final int mTwoLayoutId;

    public NoticeListAdapter(Context context, List<OneNoticeInfoBean> datas) {
        super(context, datas);
        mInflater = LayoutInflater.from(context);
        mOneLayoutId = R.layout.notice_one_item;
        mTwoLayoutId = R.layout.notice_two_item;
        mThreeLayoutId = R.layout.notice_three_item;
        addItemViewDelegate(new ItemViewDelegate<OneNoticeInfoBean>() {
            @Override
            public int getItemViewLayoutId() {
                return mOneLayoutId;
            }

            @Override
            public boolean isForViewType(OneNoticeInfoBean item, int position) {
                int datatypeId = item.getDatatype_id();
                return 1 == datatypeId || 4 == datatypeId || 5 == datatypeId;
            }


            @Override
            public void convert(ViewHolder holder, OneNoticeInfoBean data, int position) {
                NoticeListAdapter.this.convertOne(holder, data, position);
            }
        });
        addItemViewDelegate(new ItemViewDelegate<OneNoticeInfoBean>() {
            @Override
            public int getItemViewLayoutId() {
                return mTwoLayoutId;
            }

            @Override
            public boolean isForViewType(OneNoticeInfoBean item, int position) {
                int datatypeId = item.getDatatype_id();
                return 2 == datatypeId || 3 == datatypeId;
            }

            @Override
            public void convert(ViewHolder holder, OneNoticeInfoBean data, int position) {
                NoticeListAdapter.this.convertTwo(holder, data, position);
            }
        });
        addItemViewDelegate(new ItemViewDelegate<OneNoticeInfoBean>() {
            @Override
            public int getItemViewLayoutId() {
                return mThreeLayoutId;
            }

            @Override
            public boolean isForViewType(OneNoticeInfoBean item, int position) {
                int datatypeId = item.getDatatype_id();
                return 6 == datatypeId;
            }

            @Override
            public void convert(ViewHolder holder, OneNoticeInfoBean data, int position) {
                NoticeListAdapter.this.convertThree(holder, data, position);
            }
        });

        addItemViewDelegate(new ItemViewDelegate<OneNoticeInfoBean>() {
            @Override
            public int getItemViewLayoutId() {
                return mOneLayoutId;
            }

            @Override
            public boolean isForViewType(OneNoticeInfoBean item, int position) {
                int datatypeId = item.getDatatype_id();
                return 1 != datatypeId && 2 != datatypeId && 3 != datatypeId && 4 != datatypeId
                        && 5 != datatypeId && 5 != datatypeId;
            }

            @Override
            public void convert(ViewHolder holder, OneNoticeInfoBean data, int position) {
                NoticeListAdapter.this.convertOne(holder, data, position);
            }
        });
    }


    private void convertOne(ViewHolder holder, OneNoticeInfoBean data, int position) {
        holder.setText(R.id.notice_one_date_tv, data.getUpdate_time());
        holder.setText(R.id.notice_one_title, data.getTitle());
        holder.setText(R.id.notice_one_content, data.getContent());
    }

    private void convertTwo(ViewHolder holder, OneNoticeInfoBean data, int position) {
        holder.setText(R.id.notice_two_date_tv, data.getUpdate_time());
        holder.setText(R.id.notice_two_title, data.getTitle());
        holder.setText(R.id.notice_two_content, data.getContent());
        ImageView imageView = (ImageView) holder.getView(R.id.notice_two_img);
        if (BuildConfig.ROOT_URL.equals(data.getImg_url())) {
            imageView.setVisibility(View.GONE);
        } else {
            Glide.with(mContext)
                    .load(data.getImg_url())
                    .into(imageView);
        }

    }

    private void convertThree(ViewHolder holder, OneNoticeInfoBean data, int position) {
        holder.setText(R.id.notice_three_date_tv, data.getUpdate_time());
        holder.setText(R.id.notice_three_title, data.getTitle());
        TextView textView = (TextView) holder.getView(R.id.notice_three_content);
        String content = data.getContent();
        if (content != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(Html.fromHtml(content));
        }
    }

    public boolean addDatas(List<OneNoticeInfoBean> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public void upDatas(List<OneNoticeInfoBean> datas) {
        mDatas.clear();
        if (datas != null) {
            mDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }
}
