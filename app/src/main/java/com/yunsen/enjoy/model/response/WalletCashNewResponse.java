package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.model.WalletCashNewBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/31.
 */

public class WalletCashNewResponse extends RestApiResponse {

    private List<WalletCashNewBean> data;

    public List<WalletCashNewBean> getData() {
        return data;
    }

    public void setData(List<WalletCashNewBean> data) {
        this.data = data;
    }


}
