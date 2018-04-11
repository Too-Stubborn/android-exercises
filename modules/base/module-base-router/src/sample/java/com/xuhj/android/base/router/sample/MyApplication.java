package com.xuhj.android.base.router.sample;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xuhj.android.base.router.BuildConfig;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/4/2
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
