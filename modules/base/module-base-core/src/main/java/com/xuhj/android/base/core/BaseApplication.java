package com.xuhj.android.base.core;

import android.app.Application;
import android.support.annotation.CallSuper;

import com.xuhj.android.base.core.common.ActivityManager;


/**
 * 应用程序基类
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/3/30
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";

    private static BaseApplication instance;

    public BaseApplication() {
        super();
        instance = this;
    }

    /**
     * 全局单例
     */
    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @CallSuper
    @Override
    public void onTerminate() {
        super.onTerminate();
        // 关闭所有Activity
        ActivityManager.exitApplication();
    }

}
