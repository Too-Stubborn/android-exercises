package com.xuhj.jellybean.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 相册拍照工具类
 */
public class CameraUtils {

    public final static int REQUEST_CODE_CAMERA = 1;// 拍照
    public final static int REQUEST_CODE_ALBUM = 2;// 相册
    public final static int REQUEST_CODE_CROP = 3;// 裁剪

    /**
     * 打开系统相册
     *
     * @param activity
     * @param requestCode
     */
    public static void openAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*"); // 获取任意类型的图片
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开系统相册
     *
     * @param activity
     * @param requestCode
     */
    public static File openAlbumWithCrop(Activity activity, int requestCode, String targetFileName,
                                         int cropWidth, int cropHeight) {
        File filePath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + activity.getPackageName(), "/temp");
        } else {
            filePath = new File(activity.getCacheDir().getPath(), "/temp");
        }
        if (!filePath.exists())
            filePath.mkdirs();
        File file = new File(filePath, targetFileName);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.setType("image/*"); // 获取任意类型的图片
        intent.putExtra("crop", "true"); // 滑动选中图片区域
        intent.putExtra("aspectX", 1); // 表示剪切框的比例为1:1
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", cropWidth); // 指定输出图片的大小
        intent.putExtra("outputY", cropHeight);
        intent.putExtra("return-data", false);
        intent.setType("image/*"); // 获取任意类型的图片
        activity.startActivityForResult(intent, requestCode);
        return file;
    }

    /**
     * 打开相机
     *
     * @param activity
     * @param requestCode
     * @param targetFileName
     * @return
     */
    public static File openCamera(Activity activity, int requestCode, String targetFileName) {
        File filePath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + activity.getPackageName(), "/temp");
        } else {
            filePath = new File(activity.getCacheDir().getPath(), "/temp");
        }
        if (!filePath.exists())
            filePath.mkdirs();
        File file = new File(filePath, targetFileName);

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(file));
        activity.startActivityForResult(intent, requestCode);
        return file;
    }

    /**
     * 剪切图片
     *
     * @param activity
     * @param requestCode
     * @param srcFile
     * @param targetFileName
     * @param cropWidth
     * @param cropHeight
     */
    public static File cropPicture(Activity activity, int requestCode, File srcFile, String targetFileName,
                                   int cropWidth, int cropHeight) {
        File filePath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + activity.getPackageName(), "/temp");
        } else {
            filePath = new File(activity.getCacheDir().getPath(), "/temp");
        }
        if (!filePath.exists())
            filePath.mkdirs();
        File file = new File(filePath, targetFileName);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.setDataAndType(Uri.fromFile(srcFile), "image/*"); // 获取任意类型的图片
        intent.putExtra("crop", "true"); // 滑动选中图片区域
        intent.putExtra("aspectX", 1); // 表示剪切框的比例为1:1
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", cropWidth); // 指定输出图片的大小
        intent.putExtra("outputY", cropHeight);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        activity.startActivityForResult(intent, requestCode);
        return file;
    }

    /**
     * 获取从本地图库返回来的时候的URI解析出来的文件路径
     *
     * @param context
     * @param imageUri
     * @return
     */
    public static String getPhotoPathByLocalUri(Context context, Uri imageUri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        String scheme = imageUri.getScheme();
        String picturePath = null;
        if (scheme == null) {
            picturePath = imageUri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            picturePath = imageUri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(imageUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return picturePath;
    }

    /**
     * 用时间戳生成照片名称(格式：'IMG'_yyyyMMdd_HHmmss.png)
     */
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmssddd");
        return dateFormat.format(date) + ".png";
    }
}
