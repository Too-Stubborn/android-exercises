package com.xuhj.jellybean.common;

import android.os.Environment;

import java.io.File;

/**
 * 路径参数
 */
public class Settings {

    //包名
    public static String PACKAGE_NAME = "";
    //sdcard路径"/sdcard/"
    public static String SD_PATH = "";
    //日志保存路径
    public static String LOG_TXT_PATH = "";

    static {
        init();
    }

    public static void init() {
//        PACKAGE_NAME = AndroidUtils.getPackageInfo().packageName;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            SD_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
        } else {
            SD_PATH = "/data/data/" + PACKAGE_NAME + "/";
        }
        LOG_TXT_PATH = SD_PATH + PACKAGE_NAME + "/";

        new File(LOG_TXT_PATH).mkdirs();
    }

    public static String getLogTxtPath() {
        if (LOG_TXT_PATH.length() == 0)
            init();
        return LOG_TXT_PATH;
    }

    public static String getSdPath() {
        if (LOG_TXT_PATH.length() == 0)
            init();
        return SD_PATH;
    }

    public static String getPackageName() {
        if (PACKAGE_NAME.length() == 0)
            init();
        return PACKAGE_NAME;
    }
}
