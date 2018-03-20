package com.xuhj.mvp.base;

import com.xuhj.library.base.mvp.BaseView;

/**
 * 描述
 *
 * @author xuhj
 */
public interface AppBaseView extends BaseView {

    void showProgressDialog();

    void dismissProgressDialog();
}
