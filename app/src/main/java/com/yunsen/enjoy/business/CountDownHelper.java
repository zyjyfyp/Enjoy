package com.yunsen.enjoy.business;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

import com.yunsen.enjoy.model.SecondGoodsInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * Created by Administrator on 2018/6/15.
 */

public class CountDownHelper {
    private static CountDownHelper helper;

    private static HashMap<Integer, SecondGoodsInfo> mDatas = new HashMap<Integer, SecondGoodsInfo>();

    public static Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Set<Integer> keySet = mDatas.keySet();
            Iterator<Integer> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                Integer index = iterator.next();
                SecondGoodsInfo goodsInfo = mDatas.get(index);
                boolean hasRemain = goodsInfo.nextRemainingTime();
                if (!hasRemain) {
                    mDatas.remove(goodsInfo);
                }
            }
            return true;
        }
    });

    private CountDownHelper() {
    }

    public static CountDownHelper getInstantce() {
        if (helper == null) {
            synchronized (CountDownHelper.class) {
                if (helper == null) {
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                    helper = new CountDownHelper();
                }
            }
        }
        return helper;
    }

//    public static

}
