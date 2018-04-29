package com.yunsen.enjoy.http;


import com.yunsen.enjoy.model.AdvertList;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.BrandResponse;
import com.yunsen.enjoy.model.CarBrand;
import com.yunsen.enjoy.model.CarBrandList;
import com.yunsen.enjoy.model.CarModel;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.GoogsListResponse;
import com.yunsen.enjoy.model.NoticeModel;
import com.yunsen.enjoy.model.NoticeResponse;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.model.ServiceProvideResponse;
import com.yunsen.enjoy.model.response.CarBrandResponese;

import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/20.
 */

public class HttpProxy {
    private static final String TAG = "HttpProxy";

    /**
     * 获取首页广告 ；买车页面广告
     */
    public static void getHomeAdvertList(int id, final HttpCallBack<List<AdvertModel>> callBack) {
        HttpClient.get(URLConstants.HOME_ADV_URL + id, null, new HttpResponseHandler<AdvertList>() {
            @Override
            public void onSuccess(AdvertList response) {
                List<AdvertModel> data = response.getData();
                callBack.onSuccess(data);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 获取车辆广告
     *
     * @param callBack
     */
    public static void getCarList(final HttpCallBack<List<AdvertModel>> callBack) {
        HttpClient.get(URLConstants.CAR_ADV_URL, null, new HttpResponseHandler<AdvertList>() {
            @Override
            public void onSuccess(AdvertList response) {
                List<AdvertModel> data = response.getData();
                callBack.onSuccess(data);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 首页通知1
     *
     * @param callBack
     */
    public static void getNoticeData1(final HttpCallBack<List<NoticeModel>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("channel_name", "news");
        param.put("category_id", "6");
        param.put("page_size", "8");
        param.put("page_index", "1");
        param.put("strwhere", "status=0");
        param.put("orderby", "");


        HttpClient.get(URLConstants.NOTICE_URL, param, new HttpResponseHandler<NoticeResponse>() {
            @Override
            public void onSuccess(NoticeResponse response) {
                callBack.onSuccess(response.getData());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 首页通知2
     *
     * @param callBack
     */
    public static void getNoticeData2(final HttpCallBack<List<NoticeModel>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("channel_name", "news");
        param.put("category_id", "7");
        param.put("page_size", "8");
        param.put("page_index", "1");
        param.put("strwhere", "status=0");
        param.put("orderby", "");

        HttpClient.get(URLConstants.NOTICE_URL, param, new HttpResponseHandler<NoticeResponse>() {
            @Override
            public void onSuccess(NoticeResponse response) {
                callBack.onSuccess(response.getData());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 推荐汽车列表
     *
     * @param callBack
     */
    public static void getBrandData(final HttpCallBack<List<CarModel>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("top", "4");
        param.put("parent_id", "0");
        param.put("channel_id", "7");
        param.put("orderby", "id desc");
        param.put("flag", "false");

        HttpClient.get(URLConstants.CAR_BRAND_URL, param, new HttpResponseHandler<BrandResponse>() {
            @Override
            public void onSuccess(BrandResponse response) {
                if (response.getData() != null) {
                    List<CarModel> list = response.getData().getList();
                    callBack.onSuccess(list);
                } else {
                    callBack.onError(null, new Exception("date is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });

    }

    /**
     * 首页底部的服务商
     *
     * @param callBack
     */
    public static void getServiceProvider(final HttpCallBack<List<SProviderModel>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("trade_id", "0");
        param.put("page_size", "5");
        param.put("page_index", "1");
        param.put("strwhere", "status=0 and datatype='Supply'");
        param.put("orderby", "");


        HttpClient.get(URLConstants.SERVICE_PROVIDE, param, new HttpResponseHandler<ServiceProvideResponse>() {
            @Override
            public void onSuccess(ServiceProvideResponse response) {
                if (response.getData() != null) {
                    List<SProviderModel> list = response.getData();
                    callBack.onSuccess(list);
                } else {
                    callBack.onError(null, new Exception("date is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 买车页面
     * <p>
     * channel_name: goods（新车）,promotion（二手车）
     * 价格：
     * 5万以下： and sell_price<5
     * 5-10万： and sell_price between 5 and 10
     * 10-15万： and sell_price between 10 and 15
     * 15万以上： and sell_price>15
     * <p>
     * 不限： and sell_price>=0
     * 3万以下： and sell_price<=3
     * 3-5万： and sell_price between 3 and 5
     * 5-7万： and sell_price between 5 and 7
     * 7-9万： and sell_price between 7 and 9
     * 9-12万： and sell_price between 9 and 12
     * 12-16万： and sell_price between 12 and 16
     * 16-20万： and sell_price between 16 and 20
     * 20万以上： and sell_price>20
     * <p>
     * 搜索框条件： and title like '%条件%'
     * <p>
     * 车型： and brand_id like '%条件%'
     * <p>
     * 城市： and city like '%"条件"%' or city like '%所有城市%'
     * <p>
     * strwhere: strwhere,
     * <p>
     * ------------排序条件----------------
     * <p>
     * 智能排序：click desc
     * 最新上架：add_time desc
     * 价格最低：sell_price asc
     * 价格最高：sell_price desc
     */

    public static void getFilterBuyCarDatas(final HttpCallBack<List<GoodsData>> callBack, String channel, String strwhere, String orderby) {
        HashMap<String, String> param = new HashMap<>();
        param.put("channel_name", channel);
        param.put("category_id", "0");
        param.put("page_size", "8");
        param.put("page_index", "1");
        param.put("strwhere", strwhere);
        param.put("orderby", orderby);

        HttpClient.get(URLConstants.BUY_CAR_URL, param, new HttpResponseHandler<GoogsListResponse>() {
            @Override
            public void onSuccess(GoogsListResponse response) {
                if (response.getData() != null) {
                    List<GoodsData> list = response.getData();
                    callBack.onSuccess(list);
                } else {
                    callBack.onError(null, new Exception("date is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }


    /**
     * 发现页面
     *
     * @param callBack
     */
    public static void getDiscoverDatas(final HttpCallBack<List<GoodsData>> callBack, String id) {
        HashMap<String, String> param = new HashMap<>();
        param.put("channel_name", "news");
        param.put("category_id", id);
        param.put("page_size", "8");
        param.put("page_index", "1");
        param.put("strwhere", "status=0");
        param.put("orderby", "");
        //      头条-3 / 导购-2778 / 用车-2750 / 百科-4065,


        HttpClient.get(URLConstants.DISCOVER_FIRST_URL, param, new HttpResponseHandler<GoogsListResponse>() {
            @Override
            public void onSuccess(GoogsListResponse response) {
                if (response.getData() != null) {
                    List<GoodsData> list = response.getData();
                    callBack.onSuccess(list);
                } else {
                    callBack.onError(null, new Exception("date is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 汽车品牌种类列表
     *
     * @param callBack
     * @param id
     */
    public static void getCarBrandDatas(final HttpCallBack<CarBrandList> callBack, String id) {
        HashMap<String, String> param = new HashMap<>();
        param.put("top", "0");
        param.put("parent_id", "0");
        param.put("channel_id", "7");
        param.put("orderby", "id desc");
        param.put("flag", "true");

        HttpClient.get(URLConstants.CAR_BRAND_URL, param, new HttpResponseHandler<CarBrandResponese>() {
            @Override
            public void onSuccess(CarBrandResponese response) {
                if (response.getData() != null) {
                    CarBrandList data = response.getData();
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(null, new Exception("date is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    public static void getSeniorCarBrandDatas(final HttpCallBack<List<CarBrand>> callBack, String id) {
        HashMap<String, String> param = new HashMap<>();
        param.put("top", "0");
        param.put("parent_id", "0");
        param.put("channel_id", "7");
        param.put("orderby", "id desc");
        param.put("flag", "false");

        HttpClient.get(URLConstants.CAR_BRAND_URL, param, new HttpResponseHandler<CarBrandResponese>() {
            @Override
            public void onSuccess(CarBrandResponese response) {
                if (response.getData() != null) {
                    CarBrandList data = response.getData();
                    if (data != null) {
                        List<CarBrand> list = data.getList();
                        callBack.onSuccess(list);
                        return;
                    }
                }

                callBack.onError(null, new Exception("date is empty!"));
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }
}
