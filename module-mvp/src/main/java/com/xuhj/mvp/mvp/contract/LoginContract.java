package com.xuhj.mvp.mvp.contract;

import com.xuhj.mvp.base.AppBaseModel;
import com.xuhj.mvp.base.AppBasePresenter;
import com.xuhj.mvp.base.AppBaseView;

/**
 * 描述
 *
 * @author xuhj
 */
public interface LoginContract {

    interface Model extends AppBaseModel {

    }

    interface View extends AppBaseView {

        void initView();

        void showLoginSuccess(String msg);

        void gotoProductList();
    }

    abstract class Presenter extends AppBasePresenter<Model, View> {
        public Presenter(View view) {
            super(view);
        }

        public abstract boolean checkLogin(String account, String password);

        public abstract void login();

    }
}
