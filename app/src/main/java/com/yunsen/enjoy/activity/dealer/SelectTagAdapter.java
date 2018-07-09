package com.yunsen.enjoy.activity.dealer;

import android.content.Context;
import android.widget.CheckBox;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.ServiceProject;
import com.yunsen.enjoy.widget.recyclerview.CommonAdapter;
import com.yunsen.enjoy.widget.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/9.
 */

public class SelectTagAdapter extends CommonAdapter<ServiceProject> {

    public SelectTagAdapter(Context context, int layoutId, List<ServiceProject> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ServiceProject data, int position) {
        CheckBox checkBox = (CheckBox) holder.getView(R.id.check_box);
        checkBox.setChecked(data.isSelected());
        holder.setText(R.id.select_tag_tv, data.getTitle());
    }

    public List<ServiceProject> getCheckedDatas() {
        List<ServiceProject> projects = new ArrayList<>();
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            ServiceProject project = mDatas.get(i);
            if (project.isSelected()) {
                projects.add(project);
            }
        }
        return projects;
    }

    public void setNeedToCheck(int position) {
        if (position >= 0 && position < mDatas.size()) {
            ServiceProject project = mDatas.get(position);
            project.setSelected(!project.isSelected());
        }
        this.notifyDataSetChanged();
    }
}
