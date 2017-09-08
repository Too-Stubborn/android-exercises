package com.xuhj.library.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具
 *
 * @author xuhj
 */
public class DateUtils {

    public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将时间转化为时分秒
     *
     * @param second
     * @return
     */
    public static long[] getCountDownTime(long second) {

        long day = second / 60 / 60 / 24;
        long hour = (second - day * 60 * 60 * 24) / 60 / 60;
        long minute = (second - day * 60 * 60 * 24 - hour * 60 * 60) / 60;
        long sec = (second - day * 60 * 60 * 24 - hour * 60 * 60) - minute * 60;

        long[] times = {day, hour, minute, sec};
        return times;

    }

    /**
     * 将时间戳转化为String
     *
     * @param timeStamp
     * @param sdf
     * @return
     */
    public static String stamp2string(long timeStamp, SimpleDateFormat sdf) {
        return sdf.format(timeStamp * 1000);
    }


    /**
     * 将时间戳转化为String
     * 单位毫秒
     */
    public static String time2string(long millisecond, SimpleDateFormat sdf) {
        String timeStr = "";
        try {
            timeStr = sdf.format(new Date(millisecond));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }


    /**
     * 返回unix时间戳 (1970年至今的秒数)
     *
     * @return
     */
    public static long getUnixStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 得到今天的日期
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 得到昨天的日期
     *
     * @return
     */
    public static String getYesterDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = sdf.format(calendar.getTime());
        return yesterday;
    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 得到时间  HH:mm:ss
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String getTime(long timeStamp) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }

    /**
     * 得到时间  yyyy-MM-dd
     *
     * @param timeStamp 时间戳
     * @return
     */
    public static String getDayTime(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timeStamp * 1000);
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        return time / 60 + "";
    }

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        return date;
    }

}
