package com.xuhj.android.base.core.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity管理类
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/3/30
 */
public class ActivityManager {

    private static List<Activity> mActivityList;

    /**
     * 添加activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (mActivityList == null)
            mActivityList = new ArrayList<Activity>();
        mActivityList.add(activity);
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (mActivityList != null && mActivityList.contains(activity))
            mActivityList.remove(activity);

    }

    /**
     * 关闭全部activity
     */
    public static void finishAll() {
        if (mActivityList != null) {
            for (int i = 0; i < mActivityList.size(); i++) {
                mActivityList.get(i).finish();
            }
        }
    }

    /**
     * 除了某个activity，其他的全部关闭
     *
     * @param activity
     */
    public static void finishAllExcept(Activity activity) {
        if (mActivityList != null) {
            for (int i = 0; i < mActivityList.size(); i++) {
                if (mActivityList.get(i).equals(activity))
                    continue;
                mActivityList.get(i).finish();
            }
        }
    }

    /**
     * 除了某个activity，其他的全部关闭
     */
    public static void finishAllExcept(Class clazz) {
        if (mActivityList != null) {
            for (int i = 0; i < mActivityList.size(); i++) {
                if (mActivityList.get(i).getClass().equals(clazz))
                    continue;
                mActivityList.get(i).finish();
            }
        }
    }

    /**
     * 获取这个activity在堆栈中的对象，如果存在2个以上的情况，获取的则是在底部的activity
     *
     * @param clazz
     * @return
     */
    public static Activity getActivity(Class clazz) {
        if (mActivityList != null) {
            for (int i = 0; i < mActivityList.size(); i++) {
                if (mActivityList.get(i).getClass().equals(clazz)) {
                    return mActivityList.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 获取这个activity在堆栈中存在的数量
     *
     * @param clazz
     * @return
     */
    public static int getActivityCount(Class clazz) {
        int result = 0;
        if (mActivityList != null) {
            for (int i = 0; i < mActivityList.size(); i++) {
                if (mActivityList.get(i).getClass().equals(clazz))
                    result++;
            }
        }
        return result;
    }

    /**
     * 获取activity列表
     *
     * @return
     */
    public static List<Activity> getActivityList() {
        return mActivityList;
    }

    /**
     * 获取最新的activity
     *
     * @return
     */
    public static Activity getCurrentActivity() {
        if (mActivityList.size() > 0) {
            return mActivityList.get(mActivityList.size() - 1);
        }
        return null;
    }

    /**
     * 正常退出应用
     */
    public static void exitApplication() {
        exitApplication(0);
    }

    /**
     * 异常退出应用
     */
    public static void exitApplication(int status) {
        //结束所有activity堆栈
        finishAll();
        //结束当前进程
        android.os.Process.killProcess(android.os.Process.myPid());
        //status != 0 表示非正常退出
        System.exit(status);
    }
}