package com.company.base.utility.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * URL处理工具
 *
 * @author xuhj
 */
public class UrlUtils {

    private static String TAG = UrlUtils.class.getName();

    private static Pattern VALID_URL = Pattern.compile("(.+)(\\.)(.+)[^\\w]*(.*)", Pattern.CASE_INSENSITIVE);
    private static Pattern VALID_LOCAL_URL = Pattern.compile("(^file://.+)|(.+localhost:?\\d*/.+\\..+)", Pattern.CASE_INSENSITIVE);
    private static Pattern VALID_MTT_URL = Pattern.compile("mtt://(.+)", Pattern.CASE_INSENSITIVE);
    private static Pattern VALID_QB_URL = Pattern.compile("qb://(.+)", Pattern.CASE_INSENSITIVE);
    private static Pattern VALID_PAY_URL = Pattern.compile("(tenpay|alipay)://(.+)", Pattern.CASE_INSENSITIVE);
    private static Pattern VALID_IP_ADDRESS = Pattern.compile("(\\d){1,3}\\.(\\d){1,3}" + "\\.(\\d){1,3}\\.(\\d){1,3}(:\\d{1,4})?(/(.*))?", Pattern.CASE_INSENSITIVE);


    /**
     * 截取URL？后面的参数，返回Map类型
     *
     * @param queryString
     * @param enc
     * @return
     */
    public static Map getParamsMap(String queryString, String enc) {
        Map paramsMap = new HashMap();
        if (queryString != null && queryString.length() > 0) {
            int questIndex = queryString.indexOf('?');
            if (questIndex != -1) {
                queryString = queryString.substring(questIndex + 1, queryString.length());
            }

            int ampersandIndex, lastAmpersandIndex = 0;
            String subStr, param, value;
            String[] paramPair, values, newValues;
            do {
                ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
                if (ampersandIndex > 0) {
                    subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);
                    lastAmpersandIndex = ampersandIndex;
                } else {
                    subStr = queryString.substring(lastAmpersandIndex);
                }
                paramPair = subStr.split("=");
                param = paramPair[0];
                value = paramPair.length == 1 ? "" : paramPair[1];
                try {
                    value = URLDecoder.decode(value, enc);
                } catch (UnsupportedEncodingException ignored) {
                }
                if (paramsMap.containsKey(param)) {
                    values = (String[]) paramsMap.get(param);
                    int len = values.length;
                    newValues = new String[len + 1];
                    System.arraycopy(values, 0, newValues, 0, len);
                    newValues[len] = value;
                } else {
                    newValues = new String[]{value};
                }
                paramsMap.put(param, newValues);
            } while (ampersandIndex > 0);
        }
        return paramsMap;
    }

    /**
     * 根据输入，得到一个有效URL
     * 如果输入无法被解析为一个URL，返回NULL
     */
    public static String resolvValidUrl(final String aUrl) {
        if (aUrl == null || aUrl.length() == 0) {
            return null;
        }

        String url = aUrl.trim();

        if (isJavascript(url) || UrlUtils.isSpecialUrl(url)) {
            return url;
        } else if (isCandidateUrl(url)) {
            if (hasValidProtocal(url)) {
                return url;
            } else {
                return "http://" + url;
            }
        } else {
            return null;
        }
    }

    /**
     * 判断URL是否有一个有效的协议头
     */
    public static boolean hasValidProtocal(final String url) {
        if (url == null || url.length() == 0) {
            return false;
        }

        // 2013-06-29, modified by p_edenwang
        // Remove trim and toLowerCase operation for url.
        // Spaces and uppercase will not affect the order of "://" & "."
        // This modification will save 20+ms when loading www.sohu.com/?fr=wap.
        int pos1 = url.indexOf("://");
        int pos2 = url.indexOf('.');

        // 检测"wap.fchgame.com/2/read.jsp?url=http://www.zaobao.com/zg/zg.shtml"类型网址
        if (pos1 > 0 && pos2 > 0 && pos1 > pos2) {
            return false;
        }

        return url.contains("://");
    }

    /**
     * 判断URL是否是一个有效的格式
     */
    public static boolean isCandidateUrl(final String aUrl) {
        if (aUrl == null || aUrl.length() == 0 || aUrl.startsWith("data:")) {
            return false;
        }
        String url = aUrl.trim();

        Matcher validUrl = VALID_URL.matcher(url);
        Matcher validLocal = VALID_LOCAL_URL.matcher(url);
        Matcher validIp = VALID_IP_ADDRESS.matcher(url);
        Matcher validMtt = VALID_MTT_URL.matcher(url);
        Matcher validQb = VALID_QB_URL.matcher(url);
        Matcher validPay = VALID_PAY_URL.matcher(url);

        if (validUrl.find() || validLocal.find() || validIp.find() || validMtt.find() || validQb.find() || validPay.find())
            return true;
        else
            return false;
    }

    /**
     * 是否javaScript
     *
     * @param url
     * @return
     */
    public static boolean isJavascript(String url) {
        // javascript:
        return (null != url) && (url.length() > 10) && url.substring(0, 11).equalsIgnoreCase("javascript:");
    }

    /**
     * 判断是否为三星应用链接。
     *
     * @param url
     * @return
     */
    public static boolean isSamsungUrl(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith("samsungapps://")) {
            return true;
        }
        return false;
    }

    public static boolean isSpecialUrl(String url) {
        if (url == null) {
            return false;
        }

        String lowser = url.toLowerCase();
        return isSamsungUrl(lowser) || lowser.startsWith("about:blank") || lowser.startsWith("data:");
    }

    /**
     * 获取短连接@params #url#
     */
    public static String SINA_SHORT_URL = "http://api.weibo.com/2/short_url/shorten.json?source=3818214747&url_long=#url#";

    /**
     * 获取短连接
     */
    public static void getShortUrl(Context context, String originUrl) {
        String url = UrlUtils.resolvValidUrl(originUrl);
        if (url == null)
            return;

        final String apiUrl = SINA_SHORT_URL.replace("#url#", URLEncoder.encode(url));

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String response = "";
                    //通过openConnection 连接
                    URL openUrl = new URL(apiUrl);
                    HttpURLConnection conn = (HttpURLConnection) openUrl.openConnection();

                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    BufferedReader buffer = new BufferedReader(in);
                    String inputLine = null;
                    while (((inputLine = buffer.readLine()) != null)) {
                        response += inputLine + "\n";
                    }
                    String shortUrl = "";

                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray jsonArray = jsonObj.getJSONArray("urls");
                    if (jsonArray != null && jsonArray.length() > 0) {
                        shortUrl = jsonArray.getJSONObject(0).getString("url_short");
                    }
                    Log.i(TAG, "生成短连接为：" + shortUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
