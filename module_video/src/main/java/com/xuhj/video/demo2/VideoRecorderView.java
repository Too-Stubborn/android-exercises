package com.xuhj.video.demo2;

import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.xuhj.video.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 视频录制控件
 */
public class VideoRecorderView extends LinearLayout implements MediaRecorder.OnErrorListener {
    private static final String TAG = "VideoRecorderView";

    private static final int DEFAULT_VIDEO_MAX_DURATION = 60;
    private static final int DEFAULT_VIDEO_WIDTH_PX = 640;
    private static final int DEFAULT_VIDEO_HEIGHT_PX = 480;


    private Context mContext;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private ProgressBar mProgressBar;

    private MediaRecorder mMediaRecorder;
    private Camera mCamera;
    private Timer mTimer;// 计时器
    private OnRecordFinishListener mOnRecordFinishListener;// 录制完成回调接口

    private int mWidth;// 视频分辨率宽度
    private int mHeight;// 视频分辨率高度
    private int mRecordMaxTime;// 一次拍摄最长时间
    private int mTimeCount;// 时间计数
    private File mVideoFile = null;// 文件

    public VideoRecorderView(Context context) {
        this(context, null);
    }

    public VideoRecorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoRecorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {

        initLayoutInflater();

        mWidth = DEFAULT_VIDEO_WIDTH_PX;
        mHeight = DEFAULT_VIDEO_HEIGHT_PX;
        mRecordMaxTime = DEFAULT_VIDEO_MAX_DURATION;


        mProgressBar.setMax(mRecordMaxTime);// 设置进度条最大量

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new MySurfaceHolderCallBack());
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void initLayoutInflater() {
        LayoutInflater.from(mContext).inflate(R.layout.view_video_recorder2, this);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    /**
     * 初始化摄像头
     */
    private void initCamera() {
        try {
            if (mCamera != null) {
                releaseCamera();
            }
            try {
                mCamera = Camera.open();
            } catch (Exception e) {
                e.printStackTrace();
                releaseCamera();
            }
            if (mCamera == null)
                return;

            Camera.Parameters params = mCamera.getParameters();
            // 设置摄像头为竖屏
            params.set("orientation", "portrait");

            mCamera.setParameters(params);
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    Log.d(TAG, "onAutoFocus: "+success);
                }
            });
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
            mCamera.unlock();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     */
    private void initMediaRecorder() {
        try {
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.reset();
            if (mCamera != null)
                mMediaRecorder.setCamera(mCamera);

            mMediaRecorder.setOnErrorListener(this);
            mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音频源
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);// 视频输出格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 音频格式
            mMediaRecorder.setVideoSize(mWidth, mHeight);// 设置分辨率：
//            mMediaRecorder.setVideoFrameRate(15);// 这个我把它去掉了，感觉没什么用
            mMediaRecorder.setVideoEncodingBitRate(1 * 1024 * 512);// 设置帧频率，然后就清晰了
            mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);// 视频录制格式
            // mediaRecorder.setMaxDuration(Constant.MAXVEDIOTIME * 1000);
            mMediaRecorder.setOutputFile(mVideoFile.getAbsolutePath());

            mMediaRecorder.prepare();
            mMediaRecorder.start();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件
     */
    private void createVideoPath() {
        File tempFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/dewin/");
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        try {
            mVideoFile = File.createTempFile("xuhj", "id" + System.currentTimeMillis() + ".mp4", tempFile);//mp4格式
            Log.d("Path:", mVideoFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        if (mr != null)
            mr.reset();
    }


    /**
     * 开始录制视频
     */
    public void start() {
        createVideoPath();
        initCamera();
        initMediaRecorder();
        mTimeCount = 0;// 时间计数器重新赋值
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                mTimeCount++;
                mProgressBar.setProgress(mTimeCount);// 设置进度条
                if (mTimeCount == mRecordMaxTime) {// 达到指定时间，停止拍摄
                    stop();
                    if (mOnRecordFinishListener != null)
                        mOnRecordFinishListener.onRecordFinish();
                }
            }
        }, 0, 1000);
    }


    /**
     * 停止拍摄
     */
    public void stop() {
        mProgressBar.setProgress(0);
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        releaseMediaRecorder();
        releaseCamera();
    }

    /**
     * 释放摄像头资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 释放资源
     */
    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setPreviewDisplay(null);
            mMediaRecorder.stop();

            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    public void setOnRecordFinishListener(OnRecordFinishListener mOnRecordFinishListener) {
        this.mOnRecordFinishListener = mOnRecordFinishListener;
    }

    private class MySurfaceHolderCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            initCamera();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            releaseCamera();
        }

    }

    /**
     * 录制完成回调接口
     */
    public interface OnRecordFinishListener {
        void onRecordFinish();
    }

}