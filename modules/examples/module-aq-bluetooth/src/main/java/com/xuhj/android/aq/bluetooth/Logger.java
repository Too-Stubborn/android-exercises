package com.xuhj.android.aq.bluetooth;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志管理
 */
public class Logger {

    private static final String TAG = "Logger";
    private static final String PACKAGE_NAME = "com.xuhj.android.aq.bluetooth";

    private static final int DEBUG = 0x01;
    private static final int ERROR = 0x02;

    private static final boolean isDebug = BuildConfig.DEBUG;
    private static final boolean isWriteLog2Sdcard = false;

    private static String fileName = TAG + ".txt";
    private static String logcat = "[unkown]";

    public static void show(String msg) {
        if (isDebug) {
            show(msg, DEBUG);
        }
    }

    public static void show(String format, Object... argues) {
        StringBuffer result = new StringBuffer("");
        try {
            result.append(String.format(format, argues));
        } catch (Exception e) {
            result.append("format is err");
        }
        if (isDebug) {
            show(result.toString(), DEBUG);
        }
    }

    public static void show(Exception e) {
        StringBuffer exceptionStr = new StringBuffer();
        StackTraceElement[] elements = e.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString() + "\r\n");
        }
        show(exceptionStr.toString(), ERROR);
    }

    private static void show(String msg, int style) {
        if (isDebug) {
            String name = getFunctionName();
            String message = (name == null ? msg : (msg));
            switch (style) {
                case DEBUG:
                    Log.d(TAG, logcat + " - " + message);
                    break;
                case ERROR:
                    Log.e(TAG, logcat + " - " + message);
                    break;
            }
            if (isWriteLog2Sdcard)
                writeFileSdcard(name + " - " + message);
        }
    }

    /**
     * 输入到SD卡
     *
     * @param message
     */
    private static void writeFileSdcard(String message) {
        try {
            String cf = "\r\n";

            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + PACKAGE_NAME + "/log", fileName);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            if (!file.exists())
                file.createNewFile();

            FileOutputStream fout = new FileOutputStream(file.getPath(), true);
            byte[] bytes = message.getBytes("UTF-8");
            fout.write(bytes);
            fout.write(cf.getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTagName(String name) {
        String reatmp = "";
        if (!name.equals("")) {
            String tmp = new String(name);
            int i;
            String[] arrar = tmp.split("\\.");
            for (i = 3; arrar != null && i < arrar.length; i++) {
                reatmp = reatmp + "." + arrar[i];
            }
        }
        if (reatmp.equals(""))
            reatmp = "default";
        return reatmp;
    }

    /**
     * 获取类信息
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName() != null && st.getClassName().contains("Logger"))
                continue;
            String filename = getTagName(st.getClassName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            logcat = "[" + Thread.currentThread().getName() + filename + ":" + st.getLineNumber() + "]";
            return "[" + sdf.format(new Date()) + " " + Thread.currentThread().getName()
                    + "(" + Thread.currentThread().getId() + "): " + filename + ":" + st.getLineNumber() + "]";
        }
        return null;
    }
}
