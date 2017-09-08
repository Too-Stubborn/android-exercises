package com.xuhj.library.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.xuhj.library.util.ActivityManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统异常捕获处理
 *
 * @author xuhj
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";
    //实例
    private static CrashHandler instantce;
    //错误日志路径
    private String mErrorPath = Environment.getExternalStorageDirectory().getPath() + "/com.e_dewin/crash/";
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息  
    private Map<String, String> infos = new HashMap<String, String>();
    //用于格式化日期,作为日志文件名的一部分  
    private DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    static {
        instantce = new CrashHandler();
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return instantce;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        mContext = context;
        //设置异常捕获监听
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 自定义捕获系统异常并处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        //上传日志
//        uploadFiles(mErrorPath);
        //友盟错误日志上传
//        MobclickAgent.reportError(mContext, ex);
        //友盟保存统计数据
//        MobclickAgent.onKillProcess(mContext);
        //status != 0 表示非正常退出
        ActivityManager.exitApplication(1);
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = new File(mErrorPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(mErrorPath + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
                Log.d(TAG, "--- error --->>>" + sb.toString());
            }
            return fileName;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取路径下的.log文件，并上传
     *
     * @param path
     */
    private void uploadFiles(String path) {
        //如果是文件夹的话
        File file = new File(path);
        if (file.isDirectory()) {
            //返回文件夹中有的数据
            File[] files = file.listFiles();
            //先判断下有没有权限，如果没有权限的话，就不执行了
            if (null == files)
                return;

            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().endsWith(".log")) {
                    // TODO 上传日志文件至服务器
                    File uploadFile = files[i];
                }
            }
        }
    }

}  