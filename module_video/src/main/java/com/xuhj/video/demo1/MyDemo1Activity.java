package com.xuhj.video.demo1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhj.video.R;
import com.xuhj.video.util.ZipUtils;
import com.yixia.camera.MediaRecorderBase;
import com.yixia.camera.MediaRecorderNative;
import com.yixia.camera.VCamera;
import com.yixia.camera.model.MediaObject;
import com.yixia.camera.util.FileUtils;
import com.yixia.videoeditor.adapter.UtilityAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDemo1Activity extends AppCompatActivity implements MediaRecorderBase.OnErrorListener, MediaRecorderBase.OnEncodeListener {

    private static final String TAG = "MyDemo1Activity";
    private static final int MSG_TIME_COUNTER = 1;

    @Bind(R.id.surface_view)
    SurfaceView surfaceView;
    @Bind(R.id.tv_time_counter)
    TextView tvTimeCounter;
    @Bind(R.id.btn_video_recorder_start)
    Button btnVideoRecorderStart;
    @Bind(R.id.btn_video_recorder_stop)
    Button btnVideoRecorderStop;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    public static final String VIDEO_PATH = "video_path";

    /**
     * 录制最长时间
     */
    public static int RECORD_TIME_MAX = 100;
    /**
     * 录制最小时间
     */
    public static int RECORD_TIME_MIN = 5;
    /**
     * SDK视频录制对象
     */
    private MediaRecorderBase mMediaRecorder;
    /**
     * 视频信息
     */
    private MediaObject mMediaObject;

    /**
     * 对焦动画
     */
    private Animation mFocusAnimation;

    private boolean mCreated;
    private boolean isCancelRecoder;
    private boolean isRecoder;
    private volatile boolean mReleased;

    private MyHander mHandler = new MyHander();
    private int mTimeCounter = 0;

    private ProgressDialog mProgressDialog;

    public static void newInstance(Context context) {
        Intent i = new Intent(context, MyDemo1Activity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 防止锁屏
        setContentView(R.layout.activity_my_demo1);
        ButterKnife.bind(this);

        initData();
        initMediaRecorder();
    }

    private void initData() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在处理...");
        mProgressDialog.setCancelable(false);
    }

    /**
     * 初始化拍摄SDK
     */
    private void initMediaRecorder() {
        mMediaRecorder = new MediaRecorderNative();

        mMediaRecorder.setOnErrorListener(this);
        mMediaRecorder.setOnEncodeListener(this);
        File f = new File(VCamera.getVideoCachePath());
        if (!FileUtils.checkFile(f)) {
            f.mkdirs();
        }
        String name = "dw_camera_" + System.currentTimeMillis();
        mMediaObject = mMediaRecorder.setOutputDirectory(name,
                VCamera.getVideoCachePath() + name);
        mMediaRecorder.setSurfaceHolder(surfaceView.getHolder());
        mMediaRecorder.prepare();
    }

    private void startRecord() {
        if (mMediaRecorder == null) {
            return;
        }
        MediaObject.MediaPart part = mMediaRecorder.startRecord();
        if (part == null) {
            return;
        }
        isRecoder = true;
        mHandler.sendEmptyMessageDelayed(MSG_TIME_COUNTER, 1000);
    }

    private void stopRecord(boolean isSave) {
        if (mMediaRecorder == null) {
            return;
        }
        mMediaRecorder.stopRecord();
        isRecoder = false;
        mTimeCounter = 0;
        mHandler.removeMessages(MSG_TIME_COUNTER);

        if (isSave)
            mMediaRecorder.startEncoding();
    }

    @OnClick({R.id.btn_video_recorder_start, R.id.btn_video_recorder_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_video_recorder_start:
                startRecord();
                break;
            case R.id.btn_video_recorder_stop:
                stopRecord(true);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilityAdapter.freeFilterParser();
        UtilityAdapter.initFilterParser();

        if (mMediaRecorder == null) {
            initMediaRecorder();
        } else {
            mMediaRecorder.prepare();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopRecord(false);
        UtilityAdapter.freeFilterParser();
        if (mMediaRecorder != null)
            mMediaRecorder.release();
    }

    @Override
    public void onVideoError(int what, int extra) {

    }

    @Override
    public void onAudioError(int what, String message) {

    }

    @Override
    public void onEncodeStart() {
        Log.d(TAG, "onEncodeStart: ");
        mProgressDialog.show();
    }

    @Override
    public void onEncodeProgress(int progress) {
        Log.d(TAG, "onEncodeProgress: " + progress);
    }

    @Override
    public void onEncodeComplete() {
        Log.d(TAG, "onEncodeComplete: ");
        mProgressDialog.dismiss();
        final String outputVideoPath = mMediaObject.getOutputVideoPath();
//        Intent data = new Intent();
//        data.putExtra(VIDEO_PATH, outputVideoPath);
//        setResult(RESULT_OK, data);
//        finish();
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(outputVideoPath);
                String targetPath = file.getParent()
                        + "/" + com.xuhj.video.util.FileUtils.getFileNameByPath(file.getPath()) + ".zip";
                try {
                    ZipUtils.ZipFolder(file.getPath(), targetPath);
                    Log.d(TAG, "run: zip finish");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Toast.makeText(this, "已保存到目录：" + outputVideoPath, Toast.LENGTH_LONG).show();

        try {
            FileOutputStream fos = new FileOutputStream("");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        } catch (Exception e) {

        }
    }

    @Override
    public void onEncodeError() {
        Log.d(TAG, "onEncodeError: ");
        mProgressDialog.dismiss();
        Toast.makeText(this, "视频转码失败", Toast.LENGTH_SHORT).show();
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
                        stopRecord(true);
                    } else {
                        tvTimeCounter.setText(time);
                        mHandler.sendEmptyMessageDelayed(MSG_TIME_COUNTER, 1000);
                    }
                    break;
            }
        }
    }


}
