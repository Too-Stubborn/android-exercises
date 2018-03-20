package com.xuhj.jellybean.ui.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xuhj.jellybean.R;
import com.xuhj.jellybean.util.StatusBarUtil;

public class StatusBarActivity extends AppCompatActivity {


    public static void newInstance(Context context) {
        Intent i = new Intent(context, StatusBarActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_status_bar);

    }
}
