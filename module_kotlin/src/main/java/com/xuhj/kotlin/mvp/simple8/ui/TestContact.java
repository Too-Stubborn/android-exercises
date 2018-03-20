package com.xuhj.kotlin.mvp.simple8.ui;

import com.xuhj.kotlin.mvp.simple8.MvpPresenter_8;
import com.xuhj.kotlin.mvp.simple8.MvpView_8;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/5
 */
public interface TestContact {
    interface View extends MvpView_8 {

        void loginResult();
    }

    interface Presenter extends MvpPresenter_8<View> {
        void login(String userName, String password);
    }

}
