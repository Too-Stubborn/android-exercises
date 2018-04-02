package com.xuhj.jellybean.ui.activity_stack;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.xuhj.jellybean.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Test2Activity extends AppCompatActivity {

    private static final String TAG = "Test2Activity";
    @BindView(R.id.activity_test2)
    RelativeLayout activityTest2;

    private Dialog mDialog;

    @Override
    protected Dialog onCreateDialog(int id) {
        return super.onCreateDialog(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate: " + this.toString() + "---taskid;" + getTaskId());
    }

    @OnClick(R.id.activity_test2)
    public void onClick() {
        Intent intent = new Intent(Test2Activity.this, Test3Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
