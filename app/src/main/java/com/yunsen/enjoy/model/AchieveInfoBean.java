package com.yunsen.enjoy.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */

public class AchieveInfoBean {
    private List<UserInfo> listModel;
    private List<ListOrderCountBean> listOrderCount;
    private List<ListcumulativeIncomeBean> listcumulative_income;

    public List<UserInfo> getListModel() {
        return listModel;
    }

    public void setListModel(List<UserInfo> listModel) {
        this.listModel = listModel;
    }

    public List<ListOrderCountBean> getListOrderCount() {
        return listOrderCount;
    }

    public void setListOrderCount(List<ListOrderCountBean> listOrderCount) {
        this.listOrderCount = listOrderCount;
    }

    public List<ListcumulativeIncomeBean> getListcumulative_income() {
        return listcumulative_income;
    }

    public void setListcumulative_income(List<ListcumulativeIncomeBean> listcumulative_income) {
        this.listcumulative_income = listcumulative_income;
    }


}
