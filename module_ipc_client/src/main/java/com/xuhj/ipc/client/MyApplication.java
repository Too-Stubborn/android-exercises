package com.xuhj.ipc.client;

import android.app.Application;

import com.xuhj.ipc.client.util.BinderPool;

/**
 * 描述
 *
 * @author xuhj
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public MyApplication() {
        super();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BinderPool.getInstance().init(this);
    }
}
