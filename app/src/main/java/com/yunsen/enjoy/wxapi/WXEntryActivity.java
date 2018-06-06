package com.yunsen.enjoy.wxapi;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.WXAccessTokenEntity;
import com.yunsen.enjoy.model.WXBaseRespEntity;
import com.yunsen.enjoy.model.WXUserInfo;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.utils.ToastUtils;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import okhttp3.Request;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";
    /**
     * 微信登录相关
     */
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        //将应用的appid注册到微信
        api.registerApp(Constants.APP_ID);
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
        try {
            boolean result = api.handleIntent(getIntent(), this);
            if (!result) {
                Logger.d("参数不合法，未被SDK处理，退出");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Logger.d("baseReq:" + JSON.toJSONString(baseReq));
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Logger.d("baseResp:--A" + JSON.toJSONString(baseResp));
        Log.e(TAG, "baseResp--B:" + baseResp.errStr + "," + baseResp.openId + "," + baseResp.transaction + "," + baseResp.errCode);
        WXBaseRespEntity entity = JSON.parseObject(JSON.toJSONString(baseResp), WXBaseRespEntity.class);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                HashMap<String, String> param = new HashMap<>();
                param.put("appid", Constants.APP_ID);
                param.put("secret", Constants.APP_SECRET);
                param.put("code", entity.getCode());
                param.put("grant_type", "authorization_code");
                HttpProxy.getWXAccessTokenEntity(param, new HttpCallBack<WXAccessTokenEntity>() {
                    @Override
                    public void onSuccess(WXAccessTokenEntity accessTokenEntity) {
                        if (accessTokenEntity != null) {
                            getUserInfo(accessTokenEntity);
                        } else {
                            Logger.e("获取失败");
                        }
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        Logger.e("获取失败");
                    }
                });
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtils.makeTextShort("登录取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtils.makeTextShort("发送被拒绝");
                finish();
                break;
            case BaseResp.ErrCode.ERR_BAN:
                ToastUtils.makeTextShort("签名错误");
                break;
            default:
                ToastUtils.makeTextShort("发送返回");
                finish();
                break;
        }

    }

    /**
     * 获取微信个人信息
     *
     * @param accessTokenEntity
     */
    private void getUserInfo(WXAccessTokenEntity accessTokenEntity) {
        HashMap<String, String> param = new HashMap<>();
        final String accessToken = accessTokenEntity.getAccess_token();
        param.put("access_token", accessToken);
        param.put("openid", accessTokenEntity.getOpenid()); //openid:授权用户唯一标识
        HttpProxy.getWxLoginInfo(param, new HttpCallBack<WXUserInfo>() {
            @Override
            public void onSuccess(WXUserInfo wxResponse) {
                String headUrl = wxResponse.getHeadimgurl();
                Intent intent = getIntent();
                intent.putExtra("headUrl", headUrl);
                WXEntryActivity.this.setResult(2, intent);
                Log.e(TAG, "onSuccess: " + headUrl);

                SharedPreferences spPreferences_login = getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, MODE_PRIVATE);
                SharedPreferences.Editor editor = spPreferences_login.edit();
                editor.putString(SpConstants.NICK_NAME, wxResponse.getNickname());
                editor.putString("headimgurl", wxResponse.getHeadimgurl());
                editor.putString("access_token", accessToken);
                editor.putString("unionid", wxResponse.getUnionid());
                editor.putString("sex", "" + wxResponse.getSex());
                editor.putString("province", wxResponse.getProvince());
                editor.putString("city", wxResponse.getCity());
                editor.putString("country", wxResponse.getCountry());
                editor.putString("oauth_openid", wxResponse.getOpenid());
                editor.putString(SpConstants.LOGIN_FLAG, SpConstants.WEI_XIN);
                editor.commit();
                EventBus.getDefault().postSticky(new UpUiEvent(EventConstants.APP_LOGIN));
                finish();
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }

}