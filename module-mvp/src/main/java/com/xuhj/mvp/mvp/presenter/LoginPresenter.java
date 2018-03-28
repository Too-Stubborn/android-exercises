package com.xuhj.mvp.mvp.presenter;

import android.text.TextUtils;

import com.xuhj.library.util.ToastUtils;
import com.xuhj.mvp.mvp.contract.LoginContract;

/**
 * 描述
 *
 * @author xuhj
 */
public class LoginPresenter extends LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public LoginContract.Model getModel() {
        return null;
    }

    @Override
    public void start() {
    }

    @Override
    public boolean checkLogin(String account, String password) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            ToastUtils.show(mContext, "输入格式错误");
            return false;
        }
        return true;
    }

    @Override
    public void login() {
        mView.showLoginSuccess("登录成功");
        mView.gotoProductList();
    }


}
