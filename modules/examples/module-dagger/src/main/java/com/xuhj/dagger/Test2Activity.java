package com.xuhj.dagger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Test2Activity extends AppCompatActivity {

    private static final String TAG = "Test2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        Log.d(TAG, "onCreate: " + this.toString() + "---taskid;" + getTaskId());
    }
}
