package com.xuhj.mvp.base;

import android.app.ProgressDialog;

import com.xuhj.library.base.common.BaseFragment;
import com.xuhj.library.base.mvp.BasePresenter;

/**
 * 描述
 *
 * @author xuhj
 */
public abstract class AppBaseFragment<P extends BasePresenter> extends BaseFragment<P> implements AppBaseView {

    protected ProgressDialog mProgressDialog;

    @Override
    protected P getPresenter() {
        return null;
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

}
