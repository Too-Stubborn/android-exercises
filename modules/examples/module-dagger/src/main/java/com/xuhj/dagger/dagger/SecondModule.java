package com.xuhj.test.dagger;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * @author xuhj
 */
@Module
public class SecondModule {

    private Activity activity;

    public SecondModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }
}
