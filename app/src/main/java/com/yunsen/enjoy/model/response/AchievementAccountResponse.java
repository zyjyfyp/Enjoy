package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.AchievementAccountBean;

/**
 * Created by Administrator on 2018/7/6.
 */

public class AchievementAccountResponse extends RestApiResponse{

    /**
     * data : {"recordCount":0,"orderCounts":0,"cumulative_income":0}
     */

    private AchievementAccountBean data;

    public AchievementAccountBean getData() {
        return data;
    }

    public void setData(AchievementAccountBean data) {
        this.data = data;
    }


}
