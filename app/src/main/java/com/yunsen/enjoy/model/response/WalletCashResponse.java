package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.WalletCashBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */

public class WalletCashResponse extends RestApiResponse {

    private List<WalletCashBean> data;

    public List<WalletCashBean> getData() {
        return data;
    }

    public void setData(List<WalletCashBean> data) {
        this.data = data;
    }


}
