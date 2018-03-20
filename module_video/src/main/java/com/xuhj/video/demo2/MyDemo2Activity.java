package com.xuhj.video.demo2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.xuhj.video.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDemo2Activity extends AppCompatActivity {
    private static final String TAG = "MyDemo2Activity";

    @Bind(R.id.btn_video_recorder_start)
    Button btnVideoRecorderStart;
    @Bind(R.id.btn_video_recorder_stop)
    Button btnVideoRecorderStop;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.video_recorder_view)
    VideoRecorderView videoRecorderView;

    public static void newInstance(Context context) {
        Intent i = new Intent(context, MyDemo2Activity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 防止锁屏
        setContentView(R.layout.activity_my_demo2);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
    }


    @OnClick({R.id.btn_video_recorder_start, R.id.btn_video_recorder_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_video_recorder_start:
                videoRecorderView.start();
                break;
            case R.id.btn_video_recorder_stop:
                videoRecorderView.stop();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
