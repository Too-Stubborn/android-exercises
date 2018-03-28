package com.xuhj.mvp.ui.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xuhj.library.base.rxbus.RxBus;
import com.xuhj.library.base.rxbus.Subscribe;
import com.xuhj.mvp.R;
import com.xuhj.mvp.ui.product.ProductListActivity;
import com.xuhj.mvp.base.AppBaseFragment;
import com.xuhj.mvp.model.User;
import com.xuhj.mvp.mvp.contract.LoginContract;
import com.xuhj.mvp.mvp.presenter.LoginPresenter;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述
 *
 * @author xuhj
 */
public class LoginFragment extends AppBaseFragment<LoginPresenter> implements LoginContract.View {
    private static final String TAG = "LoginFragment";

    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;

    public static LoginFragment newInstance(int index) {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_frag;
    }

    @Override
    protected void onInitFragment(Bundle savedInstanceState) {
        super.onInitFragment(savedInstanceState);

        initView();
        mPresenter.start();
        RxBus.getInstance().register(this);
    }

    @Override
    public void initView() {
        etAccount.setText("xuhaojie");
        etPassword.setText("123456");
    }

    @Override
    public void showLoginSuccess(String msg) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void gotoProductList() {

        startActivity(new Intent(getActivity(), ProductListActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.getInstance().unregister(this);
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String account = etAccount.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (mPresenter.checkLogin(account, password)) {
                    mPresenter.login();
                }
                break;
        }
    }


    @Subscribe(code = 1)
    public void receiverLogin(User user) {
        Log.d(TAG, "receiverLogin: ");
        etAccount.setText(user.getName());
    }

    @Subscribe(code = 2)
    public void receiverLogin2(User user) {
        Log.d(TAG, "receiverLogin2: ");
        etAccount.setText(user.getName());
    }

}
