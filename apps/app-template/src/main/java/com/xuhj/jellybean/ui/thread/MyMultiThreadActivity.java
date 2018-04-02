package com.xuhj.jellybean.ui.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.Callable;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/3/13
 */
public class MyMultiThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    class WorkerRunnable implements Callable {
        @Override
        public Object call() throws Exception {
            return null;
        }
    }
}
