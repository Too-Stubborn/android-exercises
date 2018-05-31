package com.company.base.utility.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数字格式化转换及精度计算处理工具类
 * <p>
 * 包括double等精度问题处理
 *
 * @author xuhj
 */
public class DigitalUtils {

    /**
     * java double  精度处理，保留两位
     * <p>
     * ###0.00;-###0.00  表示两位小数 #.0000四位小数 以此类推...
     *
     * @param num
     * @return
     */
    public static String double2StringKeep2(double num) {
        DecimalFormat df = new DecimalFormat();
        try {
            df.applyPattern("###0.00;-###0.00");  //默认保留两位
            return df.format(num);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * double 相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }

    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double subtract(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double multiply(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * double 除法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static double divide(double d1, double d2, int scale) {
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将时间转换成long 类型
     * @param nowTime
     * @return
     */
    public static long getLongTime(String nowTime) {
        if (nowTime == null) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(nowTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long l = date.getTime();
        return l;
    }
}
