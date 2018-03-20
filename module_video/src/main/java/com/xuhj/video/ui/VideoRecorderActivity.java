package com.xuhj.video.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuhj.video.R;
import com.xuhj.video.demo2.VideoRecorderView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoRecorderActivity extends AppCompatActivity {
    private static final int MSG_TIME_COUNTER = 1;

    //录制最长时间
    public final static int RECORD_TIME_MAX = 60;
    //录制最小时间
    public final static int RECORD_TIME_MIN = 3;
    @Bind(R.id.tv_time_counter)
    TextView tvTimeCounter;
    @Bind(R.id.btn_video_recorder_start)
    Button btnVideoRecorderStart;
    @Bind(R.id.btn_video_recorder_stop)
    Button btnVideoRecorderStop;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.video_recorder_view)
    VideoRecorderView videoRecorderView;

    private MyHander mHandler = new MyHander();
    private int mTimeCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recorder);
        ButterKnife.bind(this);
    }

    private void startRecorder() {
        mHandler.sendEmptyMessageDelayed(MSG_TIME_COUNTER, 1000);
    }

    private void stopRecorder() {
        mTimeCounter = 0;
        mHandler.removeMessages(MSG_TIME_COUNTER);
    }

    @OnClick({R.id.btn_video_recorder_start, R.id.btn_video_recorder_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_video_recorder_start:
                startRecorder();
                break;
            case R.id.btn_video_recorder_stop:
                stopRecorder();
                break;
        }
    }

    class MyHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_TIME_COUNTER:
                    mTimeCounter++;
                    String time = String.format("%02d:%02d", mTimeCounter / 60, mTimeCounter % 60);
                    if (mTimeCounter >= RECORD_TIME_MAX) {
                        stopRecorder();
                    } else {
                        tvTimeCounter.setText(time);
                        mHandler.sendEmptyMessageDelayed(MSG_TIME_COUNTER, 1000);
                    }
                    break;
            }
        }
    }
}
