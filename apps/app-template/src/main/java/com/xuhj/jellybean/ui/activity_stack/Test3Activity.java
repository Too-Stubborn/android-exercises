package com.xuhj.jellybean.ui.activity_stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xuhj.jellybean.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Test3Activity extends AppCompatActivity {

    private static final String TAG = "Test3Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: " + this.toString() + "---taskid;" + getTaskId());
    }

    @OnClick(R.id.activity_test3)
    public void onClick() {
        Intent intent = new Intent(Test3Activity.this, Test1Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
