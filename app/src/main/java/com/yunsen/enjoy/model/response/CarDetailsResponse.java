package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.CarDetails;

/**
 * Created by Administrator on 2018/4/29.
 */

public class CarDetailsResponse extends RestApiResponse {

    private CarDetails data;

    public CarDetails getData() {
        return data;
    }

    public void setData(CarDetails data) {
        this.data = data;
    }
}
