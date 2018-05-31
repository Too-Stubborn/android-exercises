package com.company.base.utility.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * 网络状态工具类
 *
 * @author xuhj
 */
public class NetworkUtils {

    /**
     * 没有网络
     */
    public static final String NETWORKTYPE_INVALID = "NoNetWork";
    /**
     * wap网络
     */
    public static final String NETWORKTYPE_WAP = "WAP";
    /**
     * 2G网络
     */
    public static final String NETWORKTYPE_2G = "2G";
    /**
     * 3G和3G以上网络
     */
    public static final String NETWORKTYPE_3G = "3G";
    /**
     * wifi网络
     */
    public static final String NETWORKTYPE_4G = "4G";
    /**
     * wifi网络
     */
    public static final String NETWORKTYPE_WIFI = "WIFI";

    private static String mNetWorkType;

    private static String getMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return NETWORKTYPE_2G; //  50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return NETWORKTYPE_2G; // 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return NETWORKTYPE_2G; //  50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return NETWORKTYPE_3G; // 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return NETWORKTYPE_3G; //  600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return NETWORKTYPE_2G; //  100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return NETWORKTYPE_3G; // 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return NETWORKTYPE_3G; // 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return NETWORKTYPE_3G; // 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return NETWORKTYPE_3G; // 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return NETWORKTYPE_3G; // 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return NETWORKTYPE_3G; // 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORKTYPE_3G; // 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORKTYPE_2G; // 25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORKTYPE_4G; // 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return NETWORKTYPE_INVALID;
            default:
                //默认返回3g
                return NETWORKTYPE_3G;
        }
    }

    public static String getNetWorkType(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();

            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                mNetWorkType = TextUtils.isEmpty(proxyHost)
                        ? getMobileNetwork(context) : NETWORKTYPE_WAP;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }

        return mNetWorkType;
    }


    //获取运营商信息
    public static String getProvidersName(Context context) {
        String ProvidersName = "Unkown";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = telephonyManager.getSubscriberId();
        if (TextUtils.isEmpty(IMSI)) {
            return ProvidersName;
        }
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        }
        return ProvidersName;
    }


    /**
     * 判断是否连网
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        // 获取代表联网状态的NetWorkInfo对象
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null)
            return networkInfo.isAvailable();
        return false;
    }

    /**
     * 判断是否有WIFI
     *
     * @param context
     * @return
     */
    private static boolean isWifiAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null)
            return mWiFiNetworkInfo.isAvailable();
        return false;
    }

}
