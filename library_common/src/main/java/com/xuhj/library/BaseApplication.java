package com.xuhj.library;

import android.app.Application;
import android.support.annotation.CallSuper;

import com.xuhj.library.common.CrashHandler;
import com.xuhj.library.rxbus.RxBus;
import com.xuhj.library.util.ActivityManager;


/**
 * 应用程序基类
 *
 * @author xuhj
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
     *
     * @return
     */
    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initCrashHandler();
        initUMeng();
    }

    /**
     * 初始化异常捕获处理
     */
    private void initCrashHandler() {
        CrashHandler.getInstance().init(this);
    }

    /**
     * 初始化友盟SDK
     */
    private void initUMeng() {
    }

    @CallSuper
    @Override
    public void onTerminate() {
        super.onTerminate();
        // 关闭所有Activity
        ActivityManager.exitApplication();
        // 移除所有粘性事件
        RxBus.getInstance().removeAllStickyEvents();
    }

}
