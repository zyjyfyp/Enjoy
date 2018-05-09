package com.yunsen.enjoy.http;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.BuildConfig;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.model.AccountBalanceModel;
import com.yunsen.enjoy.model.AdvertList;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.BrandResponse;
import com.yunsen.enjoy.model.CarBrand;
import com.yunsen.enjoy.model.CarBrandList;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.CarModel;
import com.yunsen.enjoy.model.GoodsData;
import com.yunsen.enjoy.model.GoogsListResponse;
import com.yunsen.enjoy.model.NoticeModel;
import com.yunsen.enjoy.model.NoticeResponse;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.model.ServiceProvideResponse;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.response.AccountBalanceResponse;
import com.yunsen.enjoy.model.response.CarBrandResponese;
import com.yunsen.enjoy.model.response.CarDetailsResponse;
import com.yunsen.enjoy.model.response.ServiceShopInfoResponse;
import com.yunsen.enjoy.model.response.UserInfoResponse;

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
     * @param userId
     * @param callBack
     */
    public static void getUserInfo(String userId, final HttpCallBack<UserInfo> callBack) {
        HttpClient.get(URLConstants.PHONE_USER_INFO_URL + userId, new HashMap<String, String>(), new HttpResponseHandler<UserInfoResponse>() {
            @Override
            public void onSuccess(UserInfoResponse response) {
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
     * 获取服务商的详细信息
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
    public static void requestMeetingCar() {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", "");
        param.put("user_name", "");
        param.put("article_id", "");
        param.put("goods_id", "");
        param.put("payment_id", " 5");
        param.put("express_id", "7");
        param.put("is_invoice", "0");
        param.put("accept_name", "");
        param.put("province", "");
        param.put("city", "");
        param.put("area", "");
        param.put("address", "");
        param.put("telphone", "");
        param.put("mobile", "");
        param.put("email", "");
        param.put("post_code", "");
        param.put("message", "");
        param.put("invoice_title", "");
        HttpClient.get(URLConstants.MEET_CAR_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
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
     * 提现接口
     */
    public static void getWithDrawCash() {
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", "");
        param.put("fund_id", "1");
        param.put("expenses_id", "0");
        param.put("page_size", "8");
        param.put("page_index", "1");
        HttpClient.get(URLConstants.WITH_DRAW_CASH_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
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
    public static void getServiceOrderCount() {
        HashMap<String, String> param = new HashMap<>();
        param.put("commpany_id", "");//: 用户id,
        param.put("start_time", "");
        param.put("end_time", "");

        HttpClient.get(URLConstants.SERVICE_ODER_NUM_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
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
     * 服务商申请提交表单数据
     */
    public static void getApplyServiceForm() {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", "");   //  user_id: userInfo.id,//用户id
        param.put("user_name", ""); //       user_name: userInfo.user_name,//用户名字
        param.put("trade_id", "");        //trade_id: trade_id,//行业ID
        param.put("name", "");   //      name: name,//企业名称
        param.put("content", "");    //     content: content,//企业介绍
        param.put("artperson", "");     //    artperson: '',//法人
        param.put("contact", "");      //   contact: contact,//联系人
        param.put("mobile", "");      //  mobile: mobile,//联系人电话
        param.put("tel", "");      //   tel: '',//企业电话
        param.put("nature", "");     //   nature: '',//企业性质
        param.put("post_code", "");     //   post_code: '',//邮编
        param.put("email", "");      //  email: '',//电子邮件
        param.put("address", "");      //  address: address,//地址
        param.put("sort_id", "");     // sort_id: 99,//排序
        param.put("logo_url", "");     //  logo_url: logo_url,//企业logo
        param.put("img_url", "");     //  img_url: '',//企业图片
        param.put("seo_title", "");     //  seo_title: '',//seo标题
        param.put("seo_keywords", "");     //   seo_keywords: '',//seo关键字
        param.put("seo_description", "");      //  seo_description: '',//seo描述
        param.put("province", "");    //   province: province,//省份
        param.put("city", "");    //  city: city,//城市
        param.put("area", "");    //    area: area,//区县
        param.put("regtime", "");    //    regtime: utili.formatTime2(new Date()),//注册时间
        param.put("lng", "");    //     lng: lng,//经度
        param.put("lat", "");    //       lat: lat,//纬度
        param.put("advantage", "");    //       advantage: advantage,//企业优势
        param.put("idcard_a", "");    //       idcard_a: '',//法人身份证(正面)
        param.put("idcard_b", "");    //       idcard_b: '',//法人身份证(反面)
        param.put("license", "");    //       license: license,//工商营业执照
        param.put("accredit", "");    //       accredit: '',//厂家授权或者厂家合同
        param.put("aptitude", "");    //        aptitude: '',//企业资质
        param.put("revenue_card", "");    //        revenue_card: revenue_card,//税务
        param.put("organi_card", "");    //        organi_card: organi_card,//组织机构代码证
        param.put("brand_card", "");    //        brand_card: '',//品牌注册证
        param.put("licence_card", "");    //        licence_card: '',//开户行许许可证
        param.put("trade_aptitude", "");    //        trade_aptitude: '',//行业资质证明文件
        param.put("account_name", "");    //        account_name: '',//企业开户名称
        param.put("bank_name", "");    //        bank_name: '',//企业开户银行
        param.put("bank_account", "");    //       bank_account: '',//企业银行账号
        param.put("registeredid", "");    //        registeredid: registeredid,//工商执照注册号
        param.put("service_time", "");    //        service_time: service_time,//企业服务时间
        param.put("service_ids", "");    //        service_ids: ''
        HttpClient.get(URLConstants.APPLY_SERVICE_FORM_URL, param, new HttpResponseHandler<AccountBalanceResponse>() {
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

}