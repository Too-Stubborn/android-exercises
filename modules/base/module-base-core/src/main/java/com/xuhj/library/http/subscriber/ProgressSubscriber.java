package com.xuhj.library.http.subscriber;

import android.app.ProgressDialog;
import android.content.Context;

import com.xuhj.library.R;

import io.reactivex.disposables.Disposable;

/**
 * 封装了ProgressDialog进度条
 *
 * @author xuhj
 */
public abstract class ProgressSubscriber<T> extends ApiSubscriber<T> {

    // 自定义样式的加载dialog
    private ProgressDialog mProgressDialog;

    public ProgressSubscriber(Context context) {
        super(context);
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        showProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }

    @Override
    public void onComplete() {
        super.onComplete();
        dismissProgressDialog();
    }

    /**
     * 显示加载进度条
     */
    private void showProgressDialog() {
        if (mContext == null)
            return;
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(mContext.getString(R.string.loading));
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏进度条dialog
     */
    private void dismissProgressDialog() {
        if (mContext == null)
            return;
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }
}
