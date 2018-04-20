/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.yunsen.enjoy.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;

public class HttpResponseHandler<T> {
    protected static final int SUCCESS_MESSAGE = 0;
    protected static final int FAILURE_MESSAGE = 1;

    private Handler handler;

    /**
     * Creates a new AsyncHttpResponseHandler
     */
    public HttpResponseHandler() {
        // Set up a handler to post events back to the correct thread if possible
        if (Looper.myLooper() != null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    HttpResponseHandler.this.handleMessage(msg);
                }
            };
        }
    }

    //
    // Callbacks to be overridden, typically anonymously
    //

    /**
     * Fired when a request returns successfully, override to handle in your own code
     *
     * @param response the body of the HTTP RESTApi response from the server
     */
    public void onSuccess(T response) {
    }

    /**
     * Called when the request could not be executed due to cancellation, a
     * connectivity problem or timeout. Because networks can fail during an
     * exchange, it is possible that the remote server accepted the request
     * before the failure.
     */
    private static final String TAG = "HttpResponseHandler";
    public void onFailure(Request request, Exception e) {
        Log.e(TAG, "onFailure: "+e.getMessage() );
    }

    //
    // 后台线程调用方法，通过Handler sendMessage把结果转到UI主线程
    //

    protected void sendSuccessMessage(String jsonString) {
        try {
            T response = getRestApiResponse(jsonString);
            sendMessage(obtainMessage(SUCCESS_MESSAGE, response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private T getRestApiResponse(String responseBody) throws Exception {
        Class<? super T> rawType;
        rawType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        RestApiResponse apiResponse = (RestApiResponse) JSON.parseObject(responseBody, rawType);
        if (apiResponse == null && !"y".equalsIgnoreCase(apiResponse.getStatus())) {
            throw new Exception("server error (response = " + responseBody + ")");
        }
        return (T) apiResponse;
    }

    protected void sendFailureMessage(Request request, Exception e) {
        sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{e, request}));
    }

    //
    // Pre-processing of messages (in original calling thread, typically the UI thread)
    //

    protected void handleSuccessMessage(T response) {
        onSuccess(response);
    }

    protected void handleFailureMessage(Request request, Exception e) {
        onFailure(request, e);
    }


    // Methods which emulate android's Handler and Message methods
    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case SUCCESS_MESSAGE:
                handleSuccessMessage((T) msg.obj);
                break;
            case FAILURE_MESSAGE:
                Object[] response = (Object[]) msg.obj;
                handleFailureMessage((Request) response[1], (Exception) response[0]);
                break;
        }
    }

    protected void sendMessage(Message msg) {
        if (handler != null) {
            handler.sendMessage(msg);
        } else {
            handleMessage(msg);
        }
    }

    protected Message obtainMessage(int responseMessage, Object response) {
        Message msg = null;
        if (handler != null) {
            msg = this.handler.obtainMessage(responseMessage, response);
        } else {
            msg = Message.obtain();
            msg.what = responseMessage;
            msg.obj = response;
        }
        return msg;
    }
}
