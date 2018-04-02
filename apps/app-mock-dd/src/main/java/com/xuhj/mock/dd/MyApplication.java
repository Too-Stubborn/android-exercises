package com.xuhj.mock.dd;

import android.app.Application;

/**
 * 描述
 *
 * @author xuhj
 */
public class MyApplication extends Application {

    private static MyApplication ourInstance = new MyApplication();

    public static MyApplication getInstance() {
        return ourInstance;
    }

}
