package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.AccountBalanceModel;

/**
 * Created by Administrator on 2018/5/8.
 */

public class AccountBalanceResponse extends RestApiResponse {

    /**
     * pensions : 0
     */

    private AccountBalanceModel data;

    public AccountBalanceModel getData() {
        return data;
    }

    public void setData(AccountBalanceModel data) {
        this.data = data;
    }


}
