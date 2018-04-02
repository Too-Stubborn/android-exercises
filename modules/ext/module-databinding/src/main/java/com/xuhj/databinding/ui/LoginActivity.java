package com.xuhj.databinding.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.xuhj.databinding.R;
import com.xuhj.databinding.databinding.ActivityLoginBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/12/1
 */
public class LoginActivity extends FragmentActivity implements android.databinding.DataBindingComponent {

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        try {
            Method method = this.getClass().getMethod("");
            method.invoke(this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        startActivity(new Intent());

        // TODO: 2018/3/2 test
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    private <T> void test(T clazz) {
        try {
            Class t = Class.forName(clazz.getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
