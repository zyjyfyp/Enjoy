package com.yunsen.enjoy.model.response;

import com.yunsen.enjoy.http.RestApiResponse;
import com.yunsen.enjoy.model.SaleAfterBean;

/**
 * Created by Administrator on 2018/6/29.
 */

public class SelaAfterResponse extends RestApiResponse {
    private SaleMiddleData data;

    public SaleMiddleData getData() {
        return data;
    }

    public void setData(SaleMiddleData data) {
        this.data = data;
    }

    public static class SaleMiddleData {
        private SaleAfterBean saleModel;

        public SaleAfterBean getSaleModel() {
            return saleModel;
        }

        public void setSaleModel(SaleAfterBean saleModel) {
            this.saleModel = saleModel;
        }
    }
}
