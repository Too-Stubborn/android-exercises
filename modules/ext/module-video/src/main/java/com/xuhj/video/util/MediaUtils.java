package com.xuhj.video.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * 描述
 *
 * @author xuhj
 */
public class MediaUtils {

    public static void recordVideo(Activity activity, String filePath, int limit_time, int size) {
        try {

            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            if (limit_time != 0) {
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, limit_time);//限制录制时间(10秒=10)
            }
            if (size != 0) {
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, size * 1024 * 1024L);//限制录制大小(10M=10 * 1024 * 1024L)
            }

            File videoFile = new File(filePath, "xuhj_" + System.currentTimeMillis() + ".3gp");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
            activity.startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
