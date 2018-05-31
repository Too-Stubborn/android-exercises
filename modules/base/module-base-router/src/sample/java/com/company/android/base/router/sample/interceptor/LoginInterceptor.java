package com.company.android.base.router.sample.interceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.company.android.base.router.sample.router.RouterConsts;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/4/4
 */
@Interceptor(priority = 1, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {
    private static final String TAG = "LoginInterceptor";

    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.d(TAG, "process: 拦截器处理");
        if (postcard.getPath().equals(RouterConsts.uiSample2)) {
            postcard.withInt("Int", 333);
        }
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        System.out.println("init");
        mContext = context;
    }
}
