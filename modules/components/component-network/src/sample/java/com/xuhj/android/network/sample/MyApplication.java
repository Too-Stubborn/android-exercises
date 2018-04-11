package com.xuhj.android.network.sample;

import android.app.Application;

import com.xuhj.android.network.HttpConfiguration;
import com.xuhj.android.network.HttpFactory;

/**
 * 描述
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        HttpConfiguration configuration = new HttpConfiguration.Builder(this)
                .setBaseUrl("http://ip.taobao.com")
                .build();
        HttpFactory.getInstance().init(configuration);

    }
}
