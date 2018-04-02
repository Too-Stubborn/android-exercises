package com.xuhj.retrofit.common;

import com.xuhj.retrofit.BuildConfig;

/**
 * 项目常量定义
 *
 * @author xuhj
 */
public class Constants {

    //域名
    public static String HTTP_DOMAIN = "";
    //服务器地址
    public static String HTTP_SERVRE_URL = "";

    static {
        if (BuildConfig.IS_TEST) {
            //测试
            HTTP_DOMAIN = "http://www.e-dewin.com:9991";
            HTTP_SERVRE_URL = HTTP_DOMAIN + "/DewinAppService.svc/";
        } else {
            //正式
            HTTP_DOMAIN = "http://www.e-dewin.com:9501";
            HTTP_SERVRE_URL = HTTP_DOMAIN + "/DewinAppService.svc/";
        }
    }

}
