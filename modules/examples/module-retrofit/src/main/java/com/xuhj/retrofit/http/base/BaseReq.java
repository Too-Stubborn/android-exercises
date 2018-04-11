package com.xuhj.retrofit.http.base;

/**
 * Describe :  接口请求参数基类
 * Author   :  xuhj
 * Date     :  2016/10/11
 * Version  :  1.0
 */
public class BaseReq {

    //是否需要对请求参数base64加密，默认加密
    private boolean isEncode = true;

    public boolean isEncode() {
        return isEncode;
    }

    public void setEncode(boolean encode) {
        isEncode = encode;
    }
}
