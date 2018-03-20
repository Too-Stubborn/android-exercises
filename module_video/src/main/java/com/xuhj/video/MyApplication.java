package com.xuhj.video;

import android.app.Application;
import android.os.Environment;

import com.yixia.camera.VCamera;
import com.yixia.camera.util.DeviceUtils;

import java.io.File;

/**
 * 描述
 *
 * @author xuhj
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initVCameraSdk();
//        initVCameraSdkOffcial();
    }

    private void initVCameraSdk() {
        //设置拍摄视频缓存路径
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/Camera/Dewin/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath()
                        .replace("/sdcard/", "/sdcard-ext/") + "/Camera/Dewin/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/Camera/Dewin/");
        }
        // 开启 log 输出,ffmpeg 输出到 logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄 SDK，必须
        VCamera.initialize(this);
    }

    private void initVCameraSdkOffcial() {
//        //设置拍摄视频缓存路径
//        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        if (DeviceUtils.isZte()) {
//            if (dcim.exists()) {
//                com.yixia.weibo.sdk.VCamera.setVideoCachePath(dcim + "/Camera/Dewin/");
//            } else {
//                com.yixia.weibo.sdk.VCamera.setVideoCachePath(dcim.getPath()
//                        .replace("/sdcard/", "/sdcard-ext/") + "/Camera/Dewin/");
//            }
//        } else {
//            com.yixia.weibo.sdk.VCamera.setVideoCachePath(dcim + "/Camera/Dewin/");
//        }
//        // 开启 log 输出,ffmpeg 输出到 logcat
//        com.yixia.weibo.sdk.VCamera.setDebugMode(true);
//        // 初始化拍摄 SDK，必须
//        com.yixia.weibo.sdk.VCamera.initialize(this);
    }
}
