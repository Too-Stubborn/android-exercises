package com.xuhj.kotlin.mvp.simple8.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.xuhj.kotlin.R;
import com.xuhj.kotlin.mvp.simple8.MvpActivity_8;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/9/5
 */
public class TestActivity extends MvpActivity_8<TestContact.View, TestContact.Presenter> implements TestContact.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_test);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresneter().login("xhj", "123456");
            }
        });
    }

    @Override
    public TestContact.Presenter createPresenter() {
        return new TestPresenter(this);
    }

    @Override
    public TestContact.View createMvpView() {
        return this;
    }

    @Override
    public void loginResult() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

}
