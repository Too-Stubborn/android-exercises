package com.xuhj.mvp.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.xuhj.mvp.R;
import com.xuhj.mvp.base.AppBaseActivity;

import butterknife.Bind;

/**
 * 描述
 *
 * @author xuhj
 */
public class LoginActivity extends AppBaseActivity {
    private static final String TAG = "LoginActivity";

    @Bind(R.id.container)
    FrameLayout container;

    private LoginFragment mLoginFragment;

    public static void newInstance(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_act;
    }

    @Override
    protected void onInitActivity(Bundle savedInstanceState) {
        super.onInitActivity(savedInstanceState);
        mLoginFragment = LoginFragment.newInstance(0);
        attachFragment(R.id.container, mLoginFragment, LoginFragment.class.getSimpleName());
    }

}
