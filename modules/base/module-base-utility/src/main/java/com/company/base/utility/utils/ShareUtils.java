package com.company.base.utility.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * 系统分享工具
 *
 * @author xuhj
 */
public class ShareUtils {

    //分享文字
    public static void shareText(Context context, String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);//添加分享内容
        shareIntent.setType("text/plain");

        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    //分享单张图片
    public static void shareSingleImage(Context context, String imagePath) {
//        String imagePath = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Log.d("share", "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    //分享多张图片
    public static void shareMultipleImage(Context context, String[] imagePaths) {
        if (imagePaths.length <= 0) {
            Toast.makeText(context,"请至少选择一张图片",Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Uri> uriList = new ArrayList<>();

        String path = Environment.getExternalStorageDirectory() + File.separator;
        for (int i = 0; i < imagePaths.length; i++) {
            uriList.add(Uri.fromFile(new File(imagePaths[i])));
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
