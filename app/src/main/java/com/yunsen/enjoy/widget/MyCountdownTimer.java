package com.yunsen.enjoy.widget;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

@SuppressLint({ "HandlerLeak", "UseValueOf" })
public abstract class MyCountdownTimer {

	private static final int MSG_RUN = 1;
	private long mCountdownInterval;// 定时间隔，以毫秒计
	private long mTotalTime;// 定时时间
	private long mRemainTime;// 剩余时间

	public MyCountdownTimer(long millisInFuture, long countDownInterval) {
		mTotalTime = millisInFuture;
		mCountdownInterval = countDownInterval;
		mRemainTime = millisInFuture;
	}

	public abstract void onFinish();

	public abstract void onTick(long millisUntilFinished, int percent);

	// 取消计时
	public final void cancel() {
		mHandler.removeMessages(MSG_RUN);
	}

	// 重新开始计时
	public final void resume() {
		mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_RUN));
	}

	// 暂停计时
	public final void pause() {
		mHandler.removeMessages(MSG_RUN);
	}

	// 开始计时
	public synchronized final MyCountdownTimer start() {
		if (mRemainTime <= 0) {// 计时结束后返回
			onFinish();
			return this;
		}
		// 设置计时间隔
		mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_RUN),
				mCountdownInterval);
		return this;
	}

	// 通过handler更新android UI，显示定时时间
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			synchronized (MyCountdownTimer.this) {
				if (msg.what == MSG_RUN) {
					mRemainTime = mRemainTime - mCountdownInterval;

					if (mRemainTime <= 0) {
						onFinish();
					} else if (mRemainTime < mCountdownInterval) {
						sendMessageDelayed(obtainMessage(MSG_RUN), mRemainTime);
					} else {

						onTick(mRemainTime,
								new Long(100 * (mTotalTime - mRemainTime) / mTotalTime).intValue());

						sendMessageDelayed(obtainMessage(MSG_RUN),
								mCountdownInterval);
					}
				}
			}
		}
	};

}
