package com.xuhj.android.network.sample.subscriber;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.CallSuper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 自定义Observer，用于处理接口返回
 * <p>
 * 封装了ProgressDialog进度条
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public abstract class BaseSubscriber<T> implements Observer<T>, Disposable {

    protected Context mContext;

    private Disposable mDisposable;
    // 自定义样式的加载dialog
    private ProgressDialog mProgressDialog;

    public BaseSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
        showProgressDialog();
    }

    @Override
    public abstract void onNext(T t);

    @CallSuper
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    @Override
    public void dispose() {
        mDisposable.dispose();
    }

    @Override
    public boolean isDisposed() {
        return mDisposable.isDisposed();
    }

    /**
     * 是否显示加载进度条【默认false不显示】
     *
     * @return true: 显示; false: 不显示
     */
    protected boolean isShowProgressDialog() {
        return false;
    }

    /**
     * 显示加载进度条
     */
    private void showProgressDialog() {
        if (!isShowProgressDialog() || mContext == null) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("正在加载...");
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏进度条dialog
     */
    private void dismissProgressDialog() {
        if (!isShowProgressDialog() || mContext == null) {
            return;
        }
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

}
