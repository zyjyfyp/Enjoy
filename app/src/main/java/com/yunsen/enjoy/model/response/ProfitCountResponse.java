package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.ProfitCountBean;

/**
 * Created by Administrator on 2018/7/6.
 */

public class ProfitCountResponse extends RestApiResponse {

    /**
     * data : {"push_income":0,"team_income":0,"agent_income":0}
     */

    private ProfitCountBean data;

    public ProfitCountBean getData() {
        return data;
    }

    public void setData(ProfitCountBean data) {
        this.data = data;
    }

}
