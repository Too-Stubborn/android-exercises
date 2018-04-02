package com.xuhj.jellybean;

import android.app.Application;

import com.xuhj.library.util.ActivityManager;

import timber.log.Timber;


/**
 * 应用全局处理类
 */
public class JBApplication extends Application {

    public static JBApplication mApplication;

    public JBApplication() {
        super();
        mApplication = this;
    }

    public static JBApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 出现应用级异常时的处理
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if (!BuildConfig.DEBUG)
                            ActivityManager.exitApplication();
                    }
                }).start();
                throwable.printStackTrace();
            }
        });

        init();
    }

    private void init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    /**
     * 处理一些第三方资源的关闭释放
     */
    @Override
    public void onTerminate() {
        ActivityManager.exitApplication();
        super.onTerminate();
    }
}
