package com.xuhj.mock.dd;

import android.accessibilityservice.AccessibilityService;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.xuhj.mock.dd.common.UIConstants;
import com.xuhj.mock.dd.util.DensityUtils;
import com.xuhj.mock.dd.util.Shell;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * 描述
 *
 * @author xuhj
 */
public class MockService extends AccessibilityService {

    private static final String TAG = "MockService";

    private static final int REQUEST_CODE_REMINDER_AM = 1;
    private static final int REQUEST_CODE_REMINDER_PM = 2;

    /**
     * 操作状态
     */
    enum StepStatus {
        GO_WORK,
        GO_ATTENDANCE,
        GO_PUNCH_CARD,
        COMPLETE
    }

    private static final String WORK = "工作";
    private static final String ATTENDANCE = "考勤打卡";
    private static final String PUNCH_IN = "上班打卡";
    private static final String PUNCH_OUT = "下班打卡";
    private static final String CANCEL = "取消";
    private static final String OK = "确定";

    private StepStatus mResetStatus = StepStatus.GO_WORK;
    private StepStatus mCurrentStatus = mResetStatus;

    private ReminderReceiver mReminderReceiver = null;
    private PowerManager.WakeLock mWakeLock = null;

    //获取根节点
    private AccessibilityNodeInfo mRootInfo = null;
    //子节点列表
    private List<AccessibilityNodeInfo> mListNodeInfos = null;
    //关键字
    private String mFindText = "";

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected: ");
        acquireWakeLock();
        registerReminder();
        setReminder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        releaseWakeLock();
        unregisterReminder();
        cancelReminder();
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt: ");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent: Class--->" + event.getClassName() + "/mCurrentStatus=" + mCurrentStatus.name());
        //当切换页面的时候，延时一会儿，为保证正常运行操作
        delay(1000);
//        printf("布局信息--->", getRootInActiveWindow());
        if (mCurrentStatus == StepStatus.COMPLETE) {
            Log.d(TAG, "onAccessibilityEvent: 自动打卡成功");
            return;
        }
        int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            // 通知栏事件

        } else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            dispatchEvent(event);
        }
    }

    /**
     * 分配任务
     *
     * @param event
     */
    private void dispatchEvent(AccessibilityEvent event) {
        if (event.getClassName().equals(UIConstants.HOME)) {
            doHome();
        } else if (event.getClassName().equals(UIConstants.COMMON_WEB_VIEW)) {
            doCommonWebView();
        }
    }

    /**
     * 首页
     */
    private void doHome() {
        if (!checkRootInfo()) {
            delay(1000);
            doHome();
            return;
        }
        //初始化页面状态
        if (mCurrentStatus == StepStatus.GO_WORK) {
            goWork();
            // 下一步，考勤打卡
            mCurrentStatus = StepStatus.GO_ATTENDANCE;
            delay(1000);
            doHome();

        } else if (mCurrentStatus == StepStatus.GO_ATTENDANCE) {
            goAttendance();
            // 下一步，上班打卡
            mCurrentStatus = StepStatus.GO_PUNCH_CARD;
        } else {
            stopRun();
            return;
        }

    }

    /**
     * 考勤打卡（CommonWebView）
     */
    private void doCommonWebView() {
        if (!checkRootInfo()) {
            delay(1000);
            doCommonWebView();
            return;
        }
        // 等待网页加载
        delay(2000);
        if (mCurrentStatus == StepStatus.GO_PUNCH_CARD) {
            shellTapScreenByRate(getApplicationContext(), 0.5f, 0.5f);
            delay(1000);
            shellTapScreenByRate(getApplicationContext(), 0.5f, 0.71f);
            mCurrentStatus = StepStatus.COMPLETE;
        }
    }

    /**
     * 跳转到工作
     */
    private void goWork() {
        mRootInfo = getRootInActiveWindow();
        //根据关键字获取所需节点信息
        mListNodeInfos = mRootInfo.findAccessibilityNodeInfosByViewId("com.alibaba.android.rimet:id/home_bottom_tab_button_work");
        if (mListNodeInfos == null || mListNodeInfos.isEmpty()) {
            stopRun();
            return;
        }
        // 点击工作
        mListNodeInfos.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    /**
     * 跳转到考勤打卡
     */
    private void goAttendance() {
        mRootInfo = getRootInActiveWindow();
        mFindText = ATTENDANCE;

        //根据关键字获取所需节点信息
        mListNodeInfos = mRootInfo.findAccessibilityNodeInfosByViewId("com.alibaba.android.rimet:id/oa_fragment_gridview");
        if (mListNodeInfos == null || mListNodeInfos.isEmpty()) {
            stopRun();
            return;
        }
        AccessibilityNodeInfo recyclerView = mListNodeInfos.get(0);
        //将页面一直往下滑动
        while (true) {
            //获取关键字节点信息
            mListNodeInfos = mRootInfo.findAccessibilityNodeInfosByText(ATTENDANCE);
            //如果节点信息为空，则继续往下滑动
            if (mListNodeInfos == null || mListNodeInfos.isEmpty()) {
                if (recyclerView.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
                    delay(1000);
                    continue;
                } else {
                    //退出循环
                    break;
                }
            }
            //根据关键字找View
            for (int i = 0; i < mListNodeInfos.size(); i++) {
                AccessibilityNodeInfo item = mListNodeInfos.get(i);
                if (item != null && TextUtils.equals(item.getText().toString(), mFindText)
                        && item.getParent() != null) {
                    item.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    break;
                }
            }
            break;
        }
    }


    /**
     * 检测根节点信息是否存在
     *
     * @return
     */
    private boolean checkRootInfo() {
        mRootInfo = getRootInActiveWindow();
        return mRootInfo != null;
    }

    private void stopRun() {
        Log.d(TAG, "程序不按套路出牌，正在重启初始化...");
        restartApp();
    }

    private void restartApp() {
        mCurrentStatus = mResetStatus;
        shellStopApp();
        delay(5000);
        shellStartApp();
    }

    private void setReminder() {
        Log.d(TAG, "setReminder: 设置定时器时间");
//        Calendar now = Calendar.getInstance();
//        Calendar amc = (Calendar) now.clone();
//        Calendar pmc = (Calendar) now.clone();
//        // am.
//        long msAm = PreferenceUtils.getLong(PreferenceUtils.PK_REMINDER_AM_TIME, 0);
//        if (msAm == 0) {
//            amc.setTimeInMillis(msAm);
//            amc.add(Calendar.DAY_OF_MONTH, 1);
//        }
//        amc.set(Calendar.HOUR_OF_DAY, 8);
//        amc.set(Calendar.MINUTE, new Random().nextInt(30) + 30);
//        setAlarmManager(amc.get(Calendar.DAY_OF_MONTH), amc.get(Calendar.HOUR_OF_DAY),
//                amc.get(Calendar.MINUTE), REQUEST_CODE_REMINDER_AM);
//        PreferenceUtils.putLong(PreferenceUtils.PK_REMINDER_AM_TIME, amc.getTimeInMillis());
//        // pm.
//        long msPm = PreferenceUtils.getLong(PreferenceUtils.PK_REMINDER_PM_TIME, 0);
//        if (msPm == 0) {
//            pmc.setTimeInMillis(msPm);
//            pmc.add(Calendar.DAY_OF_MONTH, 1);
//        }
//        pmc.set(Calendar.HOUR_OF_DAY, 18);
//        pmc.set(Calendar.MINUTE, new Random().nextInt(60));
//        setAlarmManager(pmc.get(Calendar.DAY_OF_MONTH), pmc.get(Calendar.HOUR_OF_DAY),
//                pmc.get(Calendar.MINUTE), REQUEST_CODE_REMINDER_PM);
//        PreferenceUtils.putLong(PreferenceUtils.PK_REMINDER_PM_TIME, pmc.getTimeInMillis());

        setAlarmManager(8, new Random().nextInt(30) + 30, REQUEST_CODE_REMINDER_AM);
        setAlarmManager(18, new Random().nextInt(60), REQUEST_CODE_REMINDER_PM);
        // test
//        setAlarmManager(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
//                (Calendar.getInstance().get(Calendar.MINUTE) + 2) % 60, 3);
//        setAlarmManager(20, 39, 4);
    }

    private void cancelReminder() {
        cancelAlarmManager(REQUEST_CODE_REMINDER_AM);
        cancelAlarmManager(REQUEST_CODE_REMINDER_PM);
//        cancelAlarmManager(3);
//        cancelAlarmManager(4);
    }

    /**
     * 提醒闹钟设置
     */
    private Calendar setAlarmManager(int hour, int minute, int requestCode) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(ReminderReceiver.RECEIVER_ACTION_REMINDER_REPEAT);
        PendingIntent sender = PendingIntent.getBroadcast(MockService.this, requestCode,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        manager.cancel(sender);
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        return (Calendar) calendar.clone();
    }

    /**
     * 取消闹钟
     */
    private void cancelAlarmManager(int requestCode) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MockService.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, requestCode, intent, 0);
        manager.cancel(sender);
    }

    private void registerReminder() {
        if (mReminderReceiver == null) {
            mReminderReceiver = new ReminderReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.setPriority(Integer.MAX_VALUE);
        filter.addAction(ReminderReceiver.RECEIVER_ACTION_REMINDER_REPEAT);
        registerReceiver(mReminderReceiver, filter);
    }

    private void unregisterReminder() {
        if (mReminderReceiver != null) {
            unregisterReceiver(mReminderReceiver);
        }
    }

    /**
     * 保持屏幕常亮
     *
     * @return
     */
    private void acquireWakeLock() {
        if (mWakeLock == null) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, this.getClass().getCanonicalName());
            mWakeLock.acquire();
        }
    }

    /**
     * 释放屏幕常亮
     *
     * @return
     */
    private void releaseWakeLock() {
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    //************************************ 辅助方法 **************************************
    private void delay(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void shellTapScreenLocation(int x, int y) {
        //模拟坐标点击
        Shell.execShell("input tap " + x + " " + y);
        //延时
        delay(1000);
    }

    private void shellTapScreenByRate(Context context, float xr, float yr) {
        //模拟坐标点击
        Shell.execShell("input tap "
                + (int) (DensityUtils.getDisplayMetrics(context).widthPixels * xr) + " "
                + (int) (DensityUtils.getDisplayMetrics(context).heightPixels * yr));
        //延时
        delay(1000);
    }

    private void shellStopApp() {
        Log.d(TAG, "shellStopApp: ");
        Shell.execShell("am force-stop " + UIConstants.PackageNameDingDing);
    }

    private void shellStartApp() {
        Log.d(TAG, "shellStartApp: ");
        Shell.execShell("am start -n " + UIConstants.PackageNameDingDing + "/" + UIConstants.LAUNCHER);
    }

    private void shellBackPressed() {
        //BACK
        Shell.execShell("input keyevent 4");
    }

    /**
     * 专用打印找控件信息
     *
     * @param item
     */
    private static void printf(String lable, AccessibilityNodeInfo item) {
        if (item == null) {
            Log.d(TAG, "printf: --->AccessibilityNodeInfo == NullPointerException");
            return;
        }
        Log.d(TAG, lable + "--->" + item.getText()
                + ";getClassName:" + item.getClassName()
                + ";getText:" + item.getText()
                + ";getParent.getClassName:" + (item.getParent() == null ? "null" : item.getParent().getClassName())
                + ";getChildCount:" + item.getChildCount());
        for (int i = 0; i < item.getChildCount(); i++) {
            Log.d(TAG, lable + "---getChild(" + i + ").getClassName--->" + item.getChild(i).getClassName()
                    + ";getText:" + item.getChild(i).getText()
                    + ";getViewIdResourceName:" + item.getChild(i).getViewIdResourceName());
        }
    }

    private static void printf(String lable, List<AccessibilityNodeInfo> list) {
        if (list == null || list.isEmpty()) {
            Log.d(TAG, lable + "--->List<AccessibilityNodeInfo> == NullPointerException or Empty");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            printf(lable, list.get(i));
//            Log.d(TAG, lable + "---item(" + i + ")--->getClassName:" + list.get(i).getClassName()
//                    + ";getText:" + list.get(i).getText());
        }
    }

    /**
     * 定时几个小时后再运行服务
     */
    public class ReminderReceiver extends BroadcastReceiver {

        // action ReminderReceiver 名称
        public static final String RECEIVER_ACTION_REMINDER_REPEAT = "com.xuhj.mock.dd.receiver.RECEIVER_ACTION_REMINDER_REPEAT";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ReminderReceiver.RECEIVER_ACTION_REMINDER_REPEAT)) {
                Log.d(TAG, "onReceive: 执行定时任务");
                // 重新设置定时器，主要是生成随机时间
                setReminder();
                restartApp();
            }
        }
    }

}
