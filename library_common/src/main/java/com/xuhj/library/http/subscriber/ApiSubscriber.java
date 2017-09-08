package com.xuhj.library.http.subscriber;


import android.content.Context;

import com.xuhj.library.http.ApiHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 自定义Observer，用于处理接口返回
 *
 * @author xuhj
 */
public abstract class ApiSubscriber<T> implements Observer<T>, Disposable {

    protected Context mContext;

    private Disposable mDisposable;

    public ApiSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.mDisposable = d;
    }

    @Override
    public void onError(Throwable e) {
        // 如果事件被方法内部消费了,即true，则直接拦截，防止继续往下执行
        // TODO: 2017/5/25 xuhj 此处代码有问题，拦截处理无效
        if (ApiHelper.handleError(mContext, e)) {
            return;
        }
    }

    @Override
    public void onComplete() {

    }


    @Override
    public void dispose() {
        mDisposable.dispose();
    }

    @Override
    public boolean isDisposed() {
        return mDisposable.isDisposed();
    }

}
