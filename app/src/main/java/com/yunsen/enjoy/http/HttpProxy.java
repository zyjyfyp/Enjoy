package com.yunsen.enjoy.http;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.model.AccountBalanceModel;
import com.yunsen.enjoy.model.AddressInfo;
import com.yunsen.enjoy.model.AdvertList;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.ApkVersionInfo;
import com.yunsen.enjoy.model.AuthorizationModel;
import com.yunsen.enjoy.model.BindCardBean;
import com.yunsen.enjoy.model.BrandResponse;
import com.yunsen.enjoy.model.CarBrand;
import com.yunsen.enjoy.model.CarBrandList;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.CarModel;
import com.yunsen.enjoy.model.GoodsCarInfo;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.GoogsListResponse;
import com.yunsen.enjoy.model.NoticeModel;
import com.yunsen.enjoy.model.NoticeResponse;
import com.yunsen.enjoy.model.OrderInfo;
import com.yunsen.enjoy.model.PullImageResult;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.model.ServiceProject;
import com.yunsen.enjoy.model.ServiceProvideResponse;
import com.yunsen.enjoy.model.ShopCarCount;
import com.yunsen.enjoy.model.TradeData;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.WXAccessTokenEntity;
import com.yunsen.enjoy.model.WXUserInfo;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.model.WatchCarBean;
import com.yunsen.enjoy.model.request.ApplyCarModel;
import com.yunsen.enjoy.model.request.ApplyFacilitatorModel;
import com.yunsen.enjoy.model.request.ApplyWalletCashRequest;
import com.yunsen.enjoy.model.request.BindBankCardRequest;
import com.yunsen.enjoy.model.request.SubmitGoodsModel;
import com.yunsen.enjoy.model.request.WatchCarModel;
import com.yunsen.enjoy.model.response.AccountBalanceResponse;
import com.yunsen.enjoy.model.response.AddShoppingBuysResponse;
import com.yunsen.enjoy.model.response.ApkVersionResponse;
import com.yunsen.enjoy.model.response.AuthorizationResponse;
import com.yunsen.enjoy.model.response.BindBankListResponse;
import com.yunsen.enjoy.model.response.CarBrandResponese;
import com.yunsen.enjoy.model.response.CarDetailsResponse;
import com.yunsen.enjoy.model.response.DefaultAddressResponse;
import com.yunsen.enjoy.model.response.GoodsCarResponse;
import com.yunsen.enjoy.model.response.PullImageResponse;
import com.yunsen.enjoy.model.response.SearchListResponse;
import com.yunsen.enjoy.model.response.ServiceProjectListResponse;
import com.yunsen.enjoy.model.response.ServiceShopInfoResponse;
import com.yunsen.enjoy.model.response.ShopCarAccountResponse;
import com.yunsen.enjoy.model.response.StringResponse;
import com.yunsen.enjoy.model.response.TradeListResponse;
import com.yunsen.enjoy.model.response.UserInfoResponse;
import com.yunsen.enjoy.model.response.WalletCashResponse;
import com.yunsen.enjoy.model.response.WatchCarResponse;
import com.yunsen.enjoy.utils.EntityToMap;
import com.yunsen.enjoy.utils.SpUtils;
import com.yunsen.enjoy.utils.ToastUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static com.yunsen.enjoy.http.HttpClient.mapToQueryString;

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
     * @param pageIndex
     * @param callBack
     */
    public static void getServiceProvider(int pageIndex, String city, final HttpCallBack<List<SProviderModel>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("trade_id", "0");
        param.put("page_size", "5");
        param.put("page_index", "" + pageIndex);
        param.put("strwhere", "status=0 and datatype='Supply'and city = \'" + city + "\'");
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

    public static void getFilterBuyCarDatas(final HttpCallBack<List<GoodsData>> callBack, String channel, String strwhere, String orderby, String city) {
        HashMap<String, String> param = new HashMap<>();
        param.put("channel_name", channel);
        param.put("category_id", "0");
        param.put("page_size", "8");
        param.put("page_index", "1");
        param.put("strwhere", strwhere + " and city=\'" + city + "\'");
        param.put("orderby", orderby);

        HttpClient.get(URLConstants.BUY_CAR_URL, param, new HttpResponseHandler<GoogsListResponse>() {
            @Override
            public void onSuccess(GoogsListResponse response) {
                if (response.getData() != null) {
                    List<GoodsData> list = response.getData();
                    callBack.onSuccess(list);
                } else {
                    callBack.onSuccess(null);
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

    /**
     * 获取汽车详情
     *
     * @param callBack
     * @param carId
     */
    public static void getCarDetailsData(final HttpCallBack<CarDetails> callBack, String carId) {
        HttpClient.get(URLConstants.CAR_DETAILS_URL + carId, new HashMap<String, String>(), new HttpResponseHandler<CarDetailsResponse>() {
            @Override
            public void onSuccess(CarDetailsResponse response) {
                super.onSuccess(response);
                callBack.onSuccess(response.getData());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
            }
        });
    }

    public static void getAppointementCarData() {
        HttpClient.get(URLConstants.APPOINTEMENT_MANAGER, new HashMap<String, String>(), new HttpResponseHandler() {
            @Override
            public void onSuccess(Object response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param userName 用户名
     * @param callBack
     */
    public static void getUserInfo(String userName, final HttpCallBack<UserInfo> callBack) {
        HttpClient.get(URLConstants.PHONE_USER_INFO_URL + userName, new HashMap<String, String>(), new HttpResponseHandler<UserInfoResponse>() {
            @Override
            public void onSuccess(UserInfoResponse response) {
                super.onSuccess(response);
                UserInfo data = response.getData();
                if (data != null) {
                    SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
                    String loginSign = sp.getString(SpConstants.LOGIN_SIGN, "");
                    if (loginSign.equals(data.getLogin_sign())) {  //如果签名一致保存数据， 如果不一致删除数据需要重新登录
                        SpUtils.saveUserInfo(data);
                    } else {
                        sp.edit().clear().commit();
                    }
                    callBack.onSuccess(data);
                } else {
                    ToastUtils.makeTextShort("获取用户信息失败");
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 电话用户登录
     *
     * @param phone    登录的手机号
     * @param pwd      密码
     * @param callBack
     */
    public static void getUserLogin(String phone, String pwd, final HttpCallBack<UserInfo> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("username", phone);
        param.put("password", pwd);
        param.put("site", "mobile");
        param.put("terminal", "true");
        HttpClient.get(URLConstants.USER_LOGIN_URL, param, new HttpResponseHandler<UserInfoResponse>() {
            @Override
            public void onSuccess(UserInfoResponse response) {
                super.onSuccess(response);
                if (response != null && response.getData() != null) {
                    UserInfo data = response.getData();
                    SpUtils.saveUserInfo(data);
                    callBack.onSuccess(data);
                } else {
                    ToastUtils.makeTextShort("获取用户信息失败");
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
     * 添加收藏
     *
     * @param userId
     * @param userName
     * @param goodsId
     * @param callBack
     */
    public static void getAddCollect(String userId, String userName, String goodsId, final HttpCallBack<String> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("article_id", goodsId);
        param.put("user_name", userName);
        param.put("user_id", userId);
        param.put("tags", "");
        HttpClient.get(URLConstants.ADD_COLLECT_URL, param, new HttpResponseHandler<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse response) {
                super.onSuccess(response);
                callBack.onSuccess(response.getInfo());

            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 删除收藏
     *
     * @param userId
     * @param goodsId
     * @param callBack
     */
    public static void getDeleteCollect(String userId, String goodsId, final HttpCallBack<String> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("id", goodsId);
        HttpClient.get(URLConstants.DELECT_COLLECT_URL, param, new HttpResponseHandler<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse response) {
                super.onSuccess(response);
                callBack.onSuccess(response.getInfo());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 我的收藏
     *
     * @param userId
     * @param pageIndex
     * @param callBack
     */
    public static void getCollectList(String userId, String pageIndex, final HttpCallBack<List<GoodsData>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("page_size", "10");
        param.put("page_index", pageIndex);
        param.put("strwhere", "");
        param.put("orderby", "");
        HttpClient.get(URLConstants.COLLECT_LIST_URL, param, new HttpResponseHandler<GoogsListResponse>() {
            @Override
            public void onSuccess(GoogsListResponse response) {
                super.onSuccess(response);
                callBack.onSuccess(response.getData());

            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 商店收藏列表
     *
     * @param userId
     * @param pageIndex
     * @param callBack
     */
    public static void getShopCollectList(String userId, String pageIndex, final HttpCallBack<List<SProviderModel>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("page_size", "10");
        param.put("page_index", pageIndex);
        param.put("strwhere", "");
        param.put("orderby", "");
        HttpClient.get(URLConstants.SHOP_COLLECT_LIST_URL, param, new HttpResponseHandler<ServiceProvideResponse>() {
            @Override
            public void onSuccess(ServiceProvideResponse response) {
                super.onSuccess(response);
                callBack.onSuccess(response.getData());

            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 获取服务商的详细信息 and 判断是否是服务商
     *
     * @param serviceId
     * @param callBack
     */
    public static void getServiceShopInfo(String serviceId, final HttpCallBack<SProviderModel> callBack) {
        HttpClient.get(URLConstants.SERVICE_SHOP_INFO_URL + serviceId, new HashMap<String, String>(), new HttpResponseHandler<ServiceShopInfoResponse>() {
            @Override
            public void onSuccess(ServiceShopInfoResponse response) {
                super.onSuccess(response);
                callBack.onSuccess(response.getData());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 上传用户图片
     *
     * @param act
     * @param userAvatar
     * @param callBack
     */
    public static void putUserIcon(Activity act, String userAvatar, final HttpCallBack<String> callBack) {
        HashMap<String, String> param = new HashMap<>();
        SharedPreferences sp = act.getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String userName = sp.getString(SpConstants.USER_NAME, "");
        String userId = sp.getString(SpConstants.USER_ID, "");
        String sign = sp.getString(SpConstants.LOGIN_SIGN, "");
        param.put("user_name", userName);
        param.put("user_id", userId);
        param.put("user_avatar", userAvatar);
        param.put("sign", sign);

        HttpClient.get(URLConstants.SAVE_USER_ICON_URL, param, new HttpResponseHandler<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse response) {
                super.onSuccess(response);
                callBack.onSuccess(response.getInfo());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 获取订单信息
     *
     * @param userId
     * @param fundId
     * @param callBack
     */
    public static void getAccountBalance(String userId, String fundId, final HttpCallBack<AccountBalanceModel> callBack) {
        //                //我的余额统计信息
        //                fund_id: 1,
        //                //冻结资金统计信息
        //                fund_id: 6,
        //                //佣金统计信息
        //                fund_id: 3,
        //                //提现统计信息
        //                fund_id: 11,
        final String typeId = fundId;
        HashMap<String, String> param = new HashMap<>();
        param.put("to_user_id", userId);
        param.put("fund_id", fundId);
        param.put("expenses_id", "0");
        HttpClient.get(URLConstants.ACCOUNT_BALANCE_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
            @Override
            public void onSuccess(AccountBalanceResponse response) {
                super.onSuccess(response);
                AccountBalanceModel data = response.getData();
                if (data != null) {
                    data.setType(typeId);
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(null, new Exception("data is null!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    /**
     * 预约看车
     */
    public static void requestMeetingCar(WatchCarModel model, final HttpCallBack<WatchCarBean> callBack) {
        model.setPayment_id("5");
        model.setExpress_id("7");
        model.setIs_invoice("0");
        Map<String, String> param = EntityToMap.ConvertObjToMap(model);
        HttpClient.get(URLConstants.MEET_CAR_URL, param, new HttpResponseHandler<WatchCarResponse>() {
            @Override
            public void onSuccess(WatchCarResponse response) {
                WatchCarBean data = response.getData();
                if (data != null) {
                    callBack.onSuccess(data);
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e("onFailure: " + e.getMessage());
                callBack.onError(request, e);
            }
        });
    }


    /**
     * 提现接口
     */
    public static void getWithDrawCash(String userId, String pageIndex, final HttpCallBack<List<WalletCashBean>> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("fund_id", "1");
        param.put("expenses_id", "0");
        param.put("page_size", "8");
        param.put("page_index", pageIndex);
        HttpClient.get(URLConstants.WITH_DRAW_CASH_URL, param, new HttpResponseHandler<WalletCashResponse>() {
            @Override
            public void onSuccess(WalletCashResponse response) {
                super.onSuccess(response);
                callBack.onSuccess(response.getData());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
                callBack.onError(request, e);
            }
        });
    }


    /**
     * 铁杆圈
     */
    public static void getGavelockRing() {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", "");
        param.put("page_size", "5");
        param.put("strwhere", "");
        param.put("orderby", "id desc");
        param.put("page_index", "1");
        HttpClient.get(URLConstants.GAVELOCK_RING_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
            @Override
            public void onSuccess(AccountBalanceResponse response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }


    /**
     * 朋友圈
     */
    public static void getFriendRing() {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", "");
        param.put("page_size", "5");
        param.put("page_index", "1");
        param.put("strwhere", "");
        param.put("orderby", "id desc");
        HttpClient.get(URLConstants.FRIEND_RING_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
            @Override
            public void onSuccess(AccountBalanceResponse response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    /**
     * 粉丝圈Vermicelli
     */
    public static void getVermicelliRing() {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", "");
        param.put("page_size", "5");
        param.put("page_index", "1");
        param.put("strwhere", "");
        param.put("orderby", "id desc");
        HttpClient.get(URLConstants.VERMICELLI_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
            @Override
            public void onSuccess(AccountBalanceResponse response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    /**
     * 申请服务商-
     * 服务商订单统计数量
     */
    public static void getServiceOrderCount(Activity act, ApplyFacilitatorModel data, HttpCallBack<RestApiResponse> callBack) {

        HashMap<String, String> param = new HashMap<>();
        param.put("commpany_id", "");//: 用户id,
        param.put("start_time", "");
        param.put("end_time", "");

        HttpClient.get(URLConstants.SERVICE_ODER_NUM_URL, param, new HttpResponseHandler<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse response) {
                Log.e(TAG, "onSuccess: " + response.getInfo());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    /**
     * 获取企业信息
     */
    public static void getEnterpicseInfo() {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", "");//: 用户id,id: 用户id
        HttpClient.get(URLConstants.ENTERPISE_INFO_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
            @Override
            public void onSuccess(AccountBalanceResponse response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    /**
     * 预约管理
     */
    public static void getMeetManagement() {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", "");//: 用户id,id: 用户id
        param.put("page_size", "10");
        param.put("page_index", "1");
        param.put("strwhere", "status=2 and datatype=11");
        param.put("orderby", "");

        HttpClient.get(URLConstants.MEET_MANAGEMENT_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
            @Override
            public void onSuccess(AccountBalanceResponse response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    /**
     * 预约管理
     */
    public static void getObtainIndustry() {
        HashMap<String, String> param = new HashMap<>();
        param.put("parent_id", "273");//: 用户id,id: 用户id

        HttpClient.get(URLConstants.OBTAIN_INDUSTRY_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
            @Override
            public void onSuccess(AccountBalanceResponse response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    /**
     * 预约管理
     */
    public static void getUserOrderCount() {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", "");//: 用户id,id: 用户id
        param.put("sign", "");
        param.put("where", "");

        HttpClient.get(URLConstants.USER_ORDER_COUNT_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
            @Override
            public void onSuccess(AccountBalanceResponse response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    /**
     * 订单列表
     */
    public static void getOrderList() {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", "");//: 用户id,id: 用户id
        param.put("page_size", "1000");
        param.put("page_index", "1");
        param.put("strwhere", "datatype=1");
        param.put("orderby=", "");

        HttpClient.get(URLConstants.USER_ORDER_COUNT_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
            @Override
            public void onSuccess(AccountBalanceResponse response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    /**
     * 服务商申请提交表单数据
     */
    public static void getApplyServiceForm(Activity act, ApplyFacilitatorModel data, final HttpCallBack<RestApiResponse> callBack) {
        SharedPreferences sp = act.getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String userId = sp.getString(SpConstants.USER_ID, "");
        String userName = sp.getString(SpConstants.USER_NAME, "");
        data.setUser_id(userId);
        data.setUser_name(userName);
        data.setSort_id("0");
        Map<String, String> param = EntityToMap.ConvertObjToMap(data);
        HttpClient.get(URLConstants.APPLY_SERVICE_FORM_URL, param, new HttpResponseHandler<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse response) {
                callBack.onSuccess(response);
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                Logger.e("onFailure: " + e.getMessage());
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 获取服务生列表
     *
     * @param callBack
     */
    public static void getServiceProjectList(final HttpCallBack<List<ServiceProject>> callBack) {
        HttpClient.get(URLConstants.SERVICE_PROJECT_URL, new HashMap<String, String>(),
                new HttpResponseHandler<ServiceProjectListResponse>() {
                    @Override
                    public void onSuccess(ServiceProjectListResponse response) {
                        List<ServiceProject> data = response.getData();
                        if (data != null) {
                            callBack.onSuccess(data);
                        }
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        super.onFailure(request, e);
                    }
                });
    }

    /**
     * 行业类别
     *
     * @param callBack
     */
    public static void getTradeList(final HttpCallBack<List<TradeData>> callBack) {
        HttpClient.get(URLConstants.TRADE_LIST_URL, new HashMap<String, String>(),
                new HttpResponseHandler<TradeListResponse>() {
                    @Override
                    public void onSuccess(TradeListResponse response) {
                        List<TradeData> data = response.getData();
                        if (data != null) {
                            callBack.onSuccess(data);
                        }
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        super.onFailure(request, e);
                    }
                });
    }

    /**
     * 是否是服务商
     *
     * @param callBack
     */
    public static void getIsFacilitator(String userId, final HttpCallBack<Boolean> callBack) {
        HttpClient.get(URLConstants.IS_FACILITATOR_URL + userId, new HashMap<String, String>(), new HttpResponseHandler<ServiceShopInfoResponse>() {
            @Override
            public void onSuccess(ServiceShopInfoResponse response) {
                super.onSuccess(response);
                SProviderModel data = response.getData();
                if (data != null) {
                    callBack.onSuccess(true);
                } else {
                    callBack.onSuccess(false);
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onSuccess(false);
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 申请买车
     *
     * @param callBack
     */
    public static void getApplyBuyCar(ApplyCarModel model, final HttpCallBack<Boolean> callBack) {
        Map<String, String> map = EntityToMap.ConvertObjToMap(model);

        HttpClient.get(URLConstants.APPLY_BUY_CAR_URL, map, new HttpResponseHandler<StringResponse>() {
            @Override
            public void onSuccess(StringResponse response) {
                super.onSuccess(response);
                callBack.onSuccess(true);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onSuccess(false);
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 获取搜索列表
     *
     * @param searchKey
     * @param callBack
     */
    public static void getSearchList(String searchKey, String pageIndex, final HttpCallBack<List<CarDetails>> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel_name", "goods");
        map.put("category_id", "0");
        map.put("page_size", "10");
        map.put("page_index", pageIndex);
        map.put("keyword", searchKey);
        map.put("orderby", "id desc");
        HttpClient.get(URLConstants.SEARCH_KEY_WORK_URL, map, new HttpResponseHandler<SearchListResponse>() {
            @Override
            public void onSuccess(SearchListResponse response) {
                super.onSuccess(response);
                List<CarDetails> data = response.getData();
                if (data != null) {
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(null, new Exception("data is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 获取搜索列表
     *
     * @param
     * @param callBack
     */
    public static void getDiscoverBannerList(final HttpCallBack<List<GoodsData>> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel_name", "news");
        map.put("top", "5");
        map.put("strwhere", "is_slide=1");
        HttpClient.get(URLConstants.DISCOVER_BANNER_URL, map, new HttpResponseHandler<GoogsListResponse>() {
            @Override
            public void onSuccess(GoogsListResponse response) {
                super.onSuccess(response);
                List<GoodsData> data = response.getData();
                if (data != null) {
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(null, new Exception("data is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
                super.onFailure(request, e);
            }
        });
    }


    /**
     * 上传base64图片
     *
     * @param imgBase64
     * @param callBack
     */
    public static void getPullImageBase64(String imgBase64, final HttpCallBack<PullImageResult> callBack) {
        HashMap<String, String> param = new HashMap<>();
        param.put("base64", imgBase64);
        HttpClient.post(URLConstants.PULL_IMG_URL, param,
                new HttpResponseHandler<PullImageResponse>() {
                    @Override
                    public void onSuccess(PullImageResponse response) {
                        PullImageResult data = response.getData();
                        if (data != null) {
                            callBack.onSuccess(data);
                        }
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        super.onFailure(request, e);
                    }
                });
    }

    /**
     * 第三方授权
     *
     * @param callBack
     */
    public static void requestBindPhone(final HttpCallBack<AuthorizationModel> callBack) {
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String nickName = sp.getString(SpConstants.NICK_NAME, "");
        String avatar = sp.getString(SpConstants.HEAD_IMG_URL_2, "");
        String sex = sp.getString(SpConstants.SEX, "");
        String province = sp.getString(SpConstants.PROVINCE, "");
        String city = sp.getString(SpConstants.CITY, "");
        String country = sp.getString(SpConstants.COUNTRY, "");
        String oauthOpenId = sp.getString(SpConstants.OAUTH_OPEN_ID, "");
        String oauthName = sp.getString(SpConstants.OAUTH_NAME, null);
        String oauthUnionId = sp.getString(SpConstants.OAUTH_UNIONID, "");

        HashMap<String, String> param = new HashMap<>();
        param.put(SpConstants.NICK_NAME, nickName);
        param.put(SpConstants.AVATAR, avatar);
        param.put(SpConstants.SEX, sex);
        param.put(SpConstants.PROVINCE, province);
        param.put(SpConstants.CITY, city);
        param.put(SpConstants.COUNTRY, country);
        param.put(SpConstants.OAUTH_OPEN_ID, oauthOpenId);
        param.put(SpConstants.OAUTH_NAME, oauthName);
        param.put(SpConstants.OAUTH_UNIONID, oauthUnionId);

        HttpClient.get(URLConstants.BOUDLE_PHONE_URL, param, new HttpResponseHandler<AuthorizationResponse>() {
            @Override
            public void onSuccess(AuthorizationResponse response) {
                super.onSuccess(response);
                AuthorizationModel data = response.getData();
                callBack.onSuccess(data);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * @param callBack
     */
    public static void requestBindPhone(String loginTyp, final HttpCallBack<AuthorizationModel> callBack) {
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String nickName = sp.getString(SpConstants.NICK_NAME, "");
        String avatar = sp.getString(SpConstants.HEAD_IMG_URL_2, "");
        String sex = sp.getString(SpConstants.SEX, "");
        String province = sp.getString(SpConstants.PROVINCE, "");
        String city = sp.getString(SpConstants.CITY, "");
        String country = sp.getString(SpConstants.COUNTRY, "");
        String oauthOpenId = sp.getString(SpConstants.OAUTH_OPEN_ID, "");
        String oauthName = sp.getString(SpConstants.OAUTH_NAME, loginTyp);
        String oauthUnionId = sp.getString(SpConstants.OAUTH_UNIONID, "");

        HashMap<String, String> param = new HashMap<>();
        param.put(SpConstants.NICK_NAME, nickName);
        param.put(SpConstants.AVATAR, avatar);
        param.put(SpConstants.SEX, sex);
        param.put(SpConstants.PROVINCE, province);
        param.put(SpConstants.CITY, city);
        param.put(SpConstants.COUNTRY, country);
        param.put(SpConstants.OAUTH_OPEN_ID, oauthOpenId);
        param.put(SpConstants.OAUTH_NAME, oauthName);
        param.put(SpConstants.OAUTH_UNIONID, oauthUnionId);


        HttpClient.get(URLConstants.BOUDLE_PHONE_URL, param, new HttpResponseHandler<AuthorizationResponse>() {
            @Override
            public void onSuccess(AuthorizationResponse response) {
                super.onSuccess(response);
                AuthorizationModel data = response.getData();
                callBack.onSuccess(data);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 大道
     * 首页底部的商品
     *
     * @param callBack
     */
    public static void getHomeGoodsList(final HttpCallBack<List<GoodsData>> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel_name", "goods");
        map.put("top", "4");
        map.put("strwhere", "is_red=1");
        HttpClient.get(URLConstants.HOME_GOODS_LIST, map, new HttpResponseHandler<GoogsListResponse>() {
            @Override
            public void onSuccess(GoogsListResponse response) {
                super.onSuccess(response);
                List<GoodsData> data = response.getData();
                if (data != null) {
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(null, new Exception("data is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 换产品的接口
     */
    public static void getChangeGoodsList(String channelName, String categoryId, String pageIdx,
                                          String orderBy, String city,
                                          final HttpCallBack<List<CarDetails>> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("channel_name", channelName);
        map.put("category_id", categoryId);
        map.put("page_size", "10");
        map.put("page_index", pageIdx);
        map.put("strwhere", "city=\'" + city + " \'");//and brand_id like '%条件%'
        map.put("orderby", orderBy);
        HttpClient.get(URLConstants.CHANGE_GOODS_LIST, map, new HttpResponseHandler<SearchListResponse>() {
            @Override
            public void onSuccess(SearchListResponse response) {
                super.onSuccess(response);
                List<CarDetails> data = response.getData();
                if (data != null) {
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(null, new Exception("data is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 获取购物车
     */
    public static void getMyShoppingCart(String userId, String pageIdx, final HttpCallBack<List<GoodsCarInfo>> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("pageSize", "50");
        map.put("pageIndex", pageIdx);
        map.put("user_id", userId);
        HttpClient.get(URLConstants.MY_SHOPPING_CART_LIST, map, new HttpResponseHandler<GoodsCarResponse>() {
            @Override
            public void onSuccess(GoodsCarResponse response) {
                super.onSuccess(response);
                List<GoodsCarInfo> data = response.getData();
                if (data != null) {
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(null, new Exception("data is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 删除购物车的某个商品
     */
    public static void deleteShopCarGoods(String userId, String GoodsId, final HttpCallBack<ShopCarCount> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("clear", "0");
        map.put("user_id", userId);
        map.put("cart_id", GoodsId);
        HttpClient.get(URLConstants.DELETE_SHOPPING_CART_GOODS, map, new HttpResponseHandler<ShopCarAccountResponse>() {
            @Override
            public void onSuccess(ShopCarAccountResponse response) {
                super.onSuccess(response);
                ShopCarCount data = response.getData();
                if (data != null) {
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(null, new Exception("data is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 更新购物车物品数量
     *
     * @param userId
     * @param GoodsId
     * @param callBack
     */
    public static void upShopCarGoods(String userId, String GoodsId, String quantity, final HttpCallBack<ShopCarCount> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("quantity", quantity);
        map.put("user_id", userId);
        map.put("cart_id", GoodsId);

        HttpClient.get(URLConstants.UP_SHOPPING_CART_GOODS, map, new HttpResponseHandler<ShopCarAccountResponse>() {
            @Override
            public void onSuccess(ShopCarAccountResponse response) {
                super.onSuccess(response);
                ShopCarCount data = response.getData();
                if (data != null) {
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(null, new Exception("data is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 从购物车购买物品
     *
     * @param articleIds
     * @param goodsIds
     * @param quantities
     * @param callBack
     */
    public static void submitShoppingGoods(String articleIds, String goodsIds, String quantities, final HttpCallBack<OrderInfo> callBack) {
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String userId = sp.getString(SpConstants.USER_ID, "");
        String loginSign = sp.getString(SpConstants.LOGIN_SIGN, "");
        String userName = sp.getString(SpConstants.USER_NAME, "");

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("user_name", userName);
        map.put("user_sign", loginSign);
        map.put("article_id", articleIds);
        map.put("goods_id", goodsIds);
        map.put("quantity", quantities);

        HttpClient.get(URLConstants.ADD_SHOPPING_BUYS, map, new HttpResponseHandler<AddShoppingBuysResponse>() {
            @Override
            public void onSuccess(AddShoppingBuysResponse response) {
                super.onSuccess(response);
                OrderInfo data = response.getData();
                if (data != null) {
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(null, new Exception("data is empty!"));
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 余额支付
     *
     * @param pwd
     * @param tradeNo
     * @param callBack
     */
    public static void getBalanceBuyGoods(String pwd, String tradeNo, final HttpCallBack<Boolean> callBack) {
        SharedPreferences sp = AppContext.getInstance().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        String userId = sp.getString(SpConstants.USER_ID, "");
        String loginSign = sp.getString(SpConstants.LOGIN_SIGN, "");
        String userName = sp.getString(SpConstants.USER_NAME, "");

        HashMap<String, String> map = new HashMap<>();
        map.put("action", "payment");
        map.put("user_id", userId);
        map.put("user_name", userName);
        map.put("paypassword", pwd);
        map.put("trade_no", tradeNo);
        map.put("user_sign", loginSign);

        HttpClient.get(URLConstants.BALANCE_BAY_URL, map, new HttpResponseHandler<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse response) {
                super.onSuccess(response);
                callBack.onSuccess(true);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                callBack.onError(request, e);
                super.onFailure(request, e);
            }
        });
    }

    /**
     * 获取用户默认地址
     */
    public static void getUserDefaultAddress(String userName, final HttpCallBack<AddressInfo> callBack) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("user_name", userName);
        HttpClient.get(URLConstants.DEFAULT_ADDRESS_URL, param, new HttpResponseHandler<DefaultAddressResponse>() {
            @Override
            public void onSuccess(DefaultAddressResponse response) {
                callBack.onSuccess(response.getData());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 绑定银行卡
     *
     * @param request
     * @param callBack
     */
    public static void bindBankCard(BindBankCardRequest request, final HttpCallBack<Boolean> callBack) {
        Map<String, String> param = EntityToMap.ConvertObjToMap(request);
        HttpClient.get(URLConstants.BIND_BANK_CARD_URL, param, new HttpResponseHandler<StringResponse>() {
            @Override
            public void onSuccess(StringResponse response) {
                callBack.onSuccess(true);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 获取用户绑定的银行卡列表
     *
     * @param userId
     * @param userSign
     * @param callBack
     */
    public static void getBindBankCardList(String userId, String userSign, final HttpCallBack<List<BindCardBean>> callBack) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("sign", userSign);
        HttpClient.get(URLConstants.GET_BIND_BACK_LIST_URL, param, new HttpResponseHandler<BindBankListResponse>() {
            @Override
            public void onSuccess(BindBankListResponse response) {
                callBack.onSuccess(response.getData());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 申请提现
     *
     * @param request
     * @param callBack
     */
    public static void applyWalletCash(ApplyWalletCashRequest request, final HttpCallBack<Boolean> callBack) {
        Map<String, String> param = EntityToMap.ConvertObjToMap(request);
        HttpClient.get(URLConstants.APPLY_WALLET_CASH_URL, param, new HttpResponseHandler<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse response) {
                callBack.onSuccess(true);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 是否已经收藏
     *
     * @param goodsId
     * @param userId
     * @param callBack
     */
    public static void getHasCollectGoods(String goodsId, String userId, final HttpCallBack<Boolean> callBack) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("article_id", goodsId);
        param.put("user_id", userId);

        HttpClient.get(URLConstants.GOODS_HAS_COLLECT_URL, param, new HttpResponseHandler<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse response) {
                callBack.onSuccess(true);// 未收藏
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 取消收藏
     *
     * @param goodsId
     * @param userId
     * @param callBack
     */
    public static void cancelCollectGoods(String goodsId, String userId, final HttpCallBack<Boolean> callBack) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("article_id", goodsId);
        param.put("user_id", userId);
        HttpClient.get(URLConstants.CANCEL_GOODS_COLLECT_URL, param, new HttpResponseHandler<RestApiResponse>() {
            @Override
            public void onSuccess(RestApiResponse response) {
                callBack.onSuccess(true);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    /**
     * 获取微信信息
     *
     * @param param
     */
    public static void getWXAccessTokenEntity(Map<String, String> param, final HttpCallBack<WXAccessTokenEntity> callBack) {
        String url = URLConstants.WX_ACCESS_TOKEN_URL;
        if (param != null && param.size() > 0) {
            url = url + "?" + mapToQueryString(param);
            Log.e(TAG, "getWXAccessTokenEntity: " + url);
        }
        final Request request = new Request.Builder().url(url).build();
        HttpClient.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = response.body().string();
                    WXAccessTokenEntity wxResponse = JSON.parseObject(responseBody, WXAccessTokenEntity.class);
                    callBack.onSuccess(wxResponse);
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    callBack.onError(request, e);
                }
            }
        });
    }

    /**
     * 获取微信信息
     *
     * @param param
     */
    public static void getWxLoginInfo(Map<String, String> param, final HttpCallBack<WXUserInfo> callBack) {
        String url = URLConstants.WX_LOGIN_URL;
        if (param != null && param.size() > 0) {
            url = url + "?" + mapToQueryString(param);
            Log.e(TAG, "getWxLoginInfo: " + url);
        }
        final Request request = new Request.Builder().url(url).build();
        HttpClient.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String responseBody = response.body().string();
                    WXUserInfo wxResponse = JSON.parseObject(responseBody, WXUserInfo.class);
                    callBack.onSuccess(wxResponse);
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    callBack.onError(request, e);
                }
            }
        });
    }

    /**
     * 获取apk版本信息
     *
     * @param callBack
     */
    public static void getApkVersion(final HttpCallBack<ApkVersionInfo> callBack) {
        HashMap<String, Object> param = new HashMap<>();
        HttpClient.get(URLConstants.GET_APK_VERSION, param, new HttpResponseHandler<ApkVersionResponse>() {
            @Override
            public void onSuccess(ApkVersionResponse response) {
                callBack.onSuccess(response.getData());
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }

    public static void submitChangeGoods(SubmitGoodsModel mRequestData, final HttpCallBack<Boolean> callBack) {
        Map<String, String> param = EntityToMap.ConvertObjToMap(mRequestData);
        HttpClient.post(URLConstants.SUBMIT_GOODS_ChANGES_URL, param, new HttpResponseHandler<ApkVersionResponse>() {
            @Override
            public void onSuccess(ApkVersionResponse response) {
                callBack.onSuccess(true);
            }

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);
                callBack.onError(request, e);
            }
        });
    }
}

