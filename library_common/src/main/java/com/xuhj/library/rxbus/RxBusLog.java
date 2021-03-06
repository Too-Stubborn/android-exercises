package com.xuhj.library.rxbus;

import android.util.Log;

/**
 * rxbus日志工具类
 */
final class RxBusLog {
    private static boolean DEBUG = false;
    /**
     * 输出日志等级，当DEBUG为false的时候会根据设置的等级来输出日志<br>
     * 从高到低为ASSERT, ERROR, WARN, INFO, DEBUG, VERBOSE<br>
     * 使用adb shell setprop log.tag.{@link #LOG_TAG}来控制输出log等级
     */
    private static String LOG_TAG = "jrxbus2.log.DEGREE";

    protected static void enableLog(boolean enable) {
        DEBUG = enable;
    }

    public static void i(String tag, String msg) {
        if (DEBUG || Log.isLoggable(LOG_TAG, Log.INFO)) {
            msg = combineLogMsg(msg);
            Log.i(tag, "" + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG || Log.isLoggable(LOG_TAG, Log.DEBUG)) {
            msg = combineLogMsg(msg);
            Log.d(tag, "" + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG || Log.isLoggable(LOG_TAG, Log.ERROR)) {
            msg = combineLogMsg(msg);
            Log.e(tag, "" + msg);
        }
    }

    /**
     * 组装动态传参的字符串 将动态参数的字符串拼接成一个字符串
     */
    private static String combineLogMsg(String... msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("[Thread:").append(Thread.currentThread().getId()).append("]");
        sb.append(getCaller()).append(": ");
        if (null != msg) {
            for (String s : msg) {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    private static String getCaller() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "<unknown>";
        for (int i = 3; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(RxBusLog.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);
                caller = callingClass + "." + trace[i].getMethodName()
                        + "(rows:" + trace[i].getLineNumber() + ")";
                break;
            }
        }
        return caller;
    }
}