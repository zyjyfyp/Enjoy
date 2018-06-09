package com.yunsen.enjoy.common.wsmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.orhanobut.logger.Logger;
import com.yunsen.enjoy.BuildConfig;
import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.utils.DeviceUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2018/6/9.
 * webSocket manager
 */
public class WsManager {
    private static WsManager mInstance;
    private final String TAG = this.getClass().getSimpleName();

    /**
     * WebSocket config
     */
    private static final int FRAME_QUEUE_SIZE = 5;
    private static final int CONNECT_TIMEOUT = 5000;
    private static final String DEF_TEST_URL = "wss://ju918.com/tools/msim_ws.ashx";//测试服默认地址
    private static final String DEF_RELEASE_URL = "wss://ju918.com/tools/msim_ws.ashx";//正式服默认地址
    private static final String DEF_URL = BuildConfig.DEBUG ? DEF_TEST_URL : DEF_RELEASE_URL;
    private String url;

    private WsStatus mStatus;
    private WebSocket ws;
    private WsListener mListener;

    private int reconnectCount = 0;//重连次数
    private long minInterval = 3000;//重连最小时间间隔
    private long maxInterval = 60000;//重连最大时间间隔

    private WsManager() {
    }

    public static WsManager getInstance() {
        if (mInstance == null) {
            synchronized (WsManager.class) {
                if (mInstance == null) {
                    mInstance = new WsManager();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        if (ws == null) {
            try {
                /**
                 * configUrl其实是缓存在本地的连接地址
                 * 这个缓存本地连接地址是app启动的时候通过http请求去服务端获取的,
                 * 每次app启动的时候会拿当前时间与缓存时间比较,超过6小时就再次去服务端获取新的连接地址更新本地缓存
                 */
                String configUrl = "";
                url = TextUtils.isEmpty(configUrl) ? DEF_URL : configUrl;
                ws = new WebSocketFactory().createSocket(url, CONNECT_TIMEOUT)
                        .setFrameQueueSize(FRAME_QUEUE_SIZE)//设置帧队列最大值为5
                        .setMissingCloseFrameAllowed(false)//设置不允许服务端关闭连接却未发送关闭帧
                        .addListener(mListener = new WsListener())//添加回调监听
//                        .setAutoFlush(true)//自动刷新
                        .connectAsynchronously();//异步连接
                setStatus(WsStatus.CONNECTING);
                Log.e(TAG, "init: 第一次连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            reconnect();
        }

    }


    /**
     * 继承默认的监听空实现WebSocketAdapter,重写我们需要的方法
     * onTextMessage 收到文字信息
     * onConnected 连接成功
     * onConnectError 连接失败
     * onDisconnected 连接关闭
     */
    class WsListener extends WebSocketAdapter {
        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception {
            super.onTextMessage(websocket, text);
            Log.e("receiverMsg:%s", text);

            Response response = Codec.decoder(text);//解析出第一层bean
            if (response.getRespEvent() == 10) {//响应
                CallbackWrapper wrapper = callbacks.remove(Long.parseLong(response.getSeqId()));//找到对应callback
                if (wrapper == null) {
                    Logger.t(TAG).d("(action:%s) not found callback", response.getAction());
                    return;
                }

                try {
                    wrapper.getTimeoutTask().cancel(true);//取消超时任务
                    ChildResponse childResponse = null;//解析第二层bean
                    if (childResponse.isOK()) {


                        wrapper.getTempCallback().onSuccess(null);
                    } else {
                        wrapper.getTempCallback()
                                .onError(ErrorCode.BUSINESS_EXCEPTION.getMsg(), wrapper.getRequest(),
                                        wrapper.getAction());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    wrapper.getTempCallback()
                            .onError(ErrorCode.PARSE_EXCEPTION.getMsg(), wrapper.getRequest(),
                                    wrapper.getAction());
                }

            } else if (response.getRespEvent() == 20) {//通知

            }
        }

        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers)
                throws Exception {
            super.onConnected(websocket, headers);
            Log.e(TAG, "onConnected: 连接成功");
            setStatus(WsStatus.CONNECT_SUCCESS);
            cancelReconnect();//连接成功的时候取消重连,初始化连接次数
            doAuth();
        }


        @Override
        public void onConnectError(WebSocket websocket, WebSocketException exception)
                throws Exception {
            super.onConnectError(websocket, exception);
            Log.e(TAG, "onConnectError:连接错误 ");
            setStatus(WsStatus.CONNECT_FAIL);
            reconnect();//连接错误的时候调用重连方法
        }


        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer)
                throws Exception {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
            Log.e(TAG, "onDisconnected: 断开连接");
            setStatus(WsStatus.CONNECT_FAIL);
            reconnect();//连接错误的时候调用重连方法
        }
    }

    private void setStatus(WsStatus status) {
        this.mStatus = status;
    }

    private WsStatus getStatus() {
        return mStatus;
    }

    public void disconnect() {
        if (ws != null)
            ws.disconnect();
    }

    private Handler mHandler = new Handler();

    public void reconnect() {
        if (!DeviceUtil.isNetworkAvailable(AppContext.getInstance())) {
            reconnectCount = 0;
            Logger.t(TAG).d("重连失败网络不可用");
            return;
        }

        //这里其实应该还有个用户是否登录了的判断 因为当连接成功后我们需要发送用户信息到服务端进行校验
        //由于我们这里是个demo所以省略了
        if (ws != null && !ws.isOpen() &&//当前连接断开了
                getStatus() != WsStatus.CONNECTING) {//不是正在重连状态
            reconnectCount++;
            setStatus(WsStatus.CONNECTING);

            long reconnectTime = minInterval;
            if (reconnectCount > 3) {
                url = DEF_URL;
                long temp = minInterval * (reconnectCount - 2);
                reconnectTime = temp > maxInterval ? maxInterval : temp;
            }

            Logger.t(TAG).d("准备开始第%d次重连,重连间隔%d -- url:%s", reconnectCount, reconnectTime, url);
            mHandler.postDelayed(mReconnectTask, reconnectTime);
        }
    }


    private Runnable mReconnectTask = new Runnable() {
        @Override
        public void run() {
            try {
                ws = new WebSocketFactory().createSocket(url, CONNECT_TIMEOUT)
                        .setFrameQueueSize(FRAME_QUEUE_SIZE)//设置帧队列最大值为5
                        .setMissingCloseFrameAllowed(false)//设置不允许服务端关闭连接却未发送关闭帧
                        .addListener(mListener = new WsListener())//添加回调监听
                        .connectAsynchronously();//异步连接
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    private void cancelReconnect() {
        reconnectCount = 0;
        mHandler.removeCallbacks(mReconnectTask);
    }


    private boolean isNetConnect() {
        ConnectivityManager connectivity = (ConnectivityManager) AppContext.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 发送请求
     */
    private static final int REQUEST_TIMEOUT = 10000;//请求超时时间
    private AtomicLong seqId = new AtomicLong(SystemClock.uptimeMillis());//每个请求的唯一标识

    public void sendReq(Action action, Object req, ICallback callback) {
        sendReq(action, req, callback, REQUEST_TIMEOUT);
    }


    public void sendReq(Action action, Object req, ICallback callback, long timeout) {
        sendReq(action, req, callback, timeout, 1);
    }


    /**
     * showcode
     */
    private final int SUCCESS_HANDLE = 0x01;
    private final int ERROR_HANDLE = 0x02;

    private Handler mHandler2 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS_HANDLE:
                    CallbackDataWrapper successWrapper = (CallbackDataWrapper) msg.obj;
                    successWrapper.getCallback().onSuccess(successWrapper.getData());
                    break;
                case ERROR_HANDLE:
                    CallbackDataWrapper errorWrapper = (CallbackDataWrapper) msg.obj;
                    errorWrapper.getCallback().onFail((String) errorWrapper.getData());
                    break;
            }
        }
    };


    /**
     * 超时处理
     */
    private void timeoutHandle(Request request, Action action, ICallback callback, long timeout) {
        if (request.getReqCount() > 3) {
            Logger.d("(action:%s)连续3次请求超时 执行http请求", action.getAction());
            //走http请求
        } else {
            sendReq(action, request.getReq(), callback, timeout, (request.getReqCount() + 1));
            Logger.t(TAG).d("(action:%s)发起第%d次请求", action.getAction(), request.getReqCount());
        }
    }

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private Map<Long, CallbackWrapper> callbacks = new HashMap<>();

    /**
     * @param action   Action
     * @param req      请求参数
     * @param callback 回调
     * @param timeout  超时时间
     * @param reqCount 请求次数
     */
    @SuppressWarnings("unchecked")
    private <T> void sendReq(Action action, T req, final ICallback callback, final long timeout, int reqCount) {
        if (!isNetConnect()) {
            callback.onFail("网络不可用");
            return;
        }

        Request request = new Request.Builder<T>()
                .action(action.getAction())
                .reqEvent(action.getReqEvent())
                .seqId(seqId.getAndIncrement())
                .reqCount(reqCount)
                .req(req)
                .build();

        ScheduledFuture timeoutTask = enqueueTimeout(request.getSeqId(), timeout);//添加超时任务

        IWsCallback tempCallback = new IWsCallback() {

            @Override
            public void onSuccess(Object o) {
                mHandler.obtainMessage(SUCCESS_HANDLE, new CallbackDataWrapper(callback, o))
                        .sendToTarget();
            }


            @Override
            public void onError(String msg, Request request, Action action) {
                mHandler.obtainMessage(ERROR_HANDLE, new CallbackDataWrapper(callback, msg))
                        .sendToTarget();
            }


            @Override
            public void onTimeout(Request request, Action action) {
                timeoutHandle(request, action, callback, timeout);
            }
        };

        callbacks.put(request.getSeqId(),
                new CallbackWrapper(tempCallback, timeoutTask, action, request));
        String json = JSON.toJSONString(request);
        Logger.d("send text : %s", json);
        ws.sendText(json);
    }

    /**
     * 添加超时任务
     */
    private ScheduledFuture enqueueTimeout(final long seqId, long timeout) {
        return executor.schedule(new Runnable() {
            @Override
            public void run() {
                CallbackWrapper wrapper = callbacks.remove(seqId);
                if (wrapper != null) {
                    Logger.t(TAG).d("(action:%s)第%d次请求超时", wrapper.getAction().getAction(), wrapper.getRequest().getReqCount());
                    wrapper.getTempCallback().onTimeout(wrapper.getRequest(), wrapper.getAction());
                }
            }
        }, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 授权请求
     */
    private void doAuth() {
        sendReq(Action.LOGIN, null, new ICallback() {
            @Override
            public void onSuccess(Object o) {
                Logger.t(TAG).d("授权成功");
                setStatus(WsStatus.CONNECT_SUCCESS);
            }


            @Override
            public void onFail(String msg) {

            }
        });
    }


}