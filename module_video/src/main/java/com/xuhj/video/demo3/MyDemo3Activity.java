package com.xuhj.video.demo3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.xuhj.video.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDemo3Activity extends AppCompatActivity {

    private static final String TAG = "MyDemo3Activity";

    public static void newInstance(Context context) {
        Intent i = new Intent(context, MyDemo3Activity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 防止锁屏
        setContentView(R.layout.activity_my_demo3);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
    }

    private void invokeSystemVideoRecorder() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);//参数设置可以省略
        startActivityForResult(intent, 1);
    }

    @OnClick({R.id.btn_video_recorder_start, R.id.btn_video_recorder_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_video_recorder_start:
                invokeSystemVideoRecorder();
                break;
            case R.id.btn_video_recorder_stop:
                break;
        }
    }

}
