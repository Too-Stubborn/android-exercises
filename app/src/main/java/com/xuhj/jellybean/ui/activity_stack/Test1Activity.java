package com.xuhj.jellybean.ui.activity_stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xuhj.jellybean.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Test1Activity extends AppCompatActivity {

    private static final String TAG = "Test1Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: " + this.toString() + "---taskid;" + getTaskId());

    }

    @OnClick(R.id.activity_test1)
    public void onClick() {
//        Intent intent = new Intent();
//        ComponentName componentName = new ComponentName("com.xuhj.dagger", "com.xuhj.dagger.Test2Activity");
////        intent.setComponent(componentName);
//        intent.setAction("com.xuhj.dagger.action.test2activity");
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intent = new Intent(Test1Activity.this, Test2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
