package com.xuhj.android.network.sample.entity;


/**
 * 接口返回参数基类
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/3/30
 */
public class BaseResp {

    // 状态码
    private String code;
    // 提示消息
    private String msg;
    // 业务data【需要AES解密】
    private String content;
    // 设备id
    private String device_id;
    // 当前时间戳【单位s】
    private String time;
    // 设备类型
    private String type;
    // token
    private String token;
    // 状态码【success】
    private String statusCode;
    // MD5加密串签名，用于数据比对，防篡改
    private String sign;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
