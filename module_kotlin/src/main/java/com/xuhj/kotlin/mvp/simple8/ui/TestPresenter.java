package com.xuhj.kotlin.mvp.simple8.ui;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/5
 */
public class TestPresenter implements TestContact.Presenter {

    private TestContact.View mView;

    public TestPresenter(TestContact.View view) {
        mView = view;
    }

    @Override
    public void login(String userName, String password) {
        mView.loginResult();
    }

    @Override
    public void attachView(TestContact.View view) {

    }

    @Override
    public void detachView() {

    }
}
