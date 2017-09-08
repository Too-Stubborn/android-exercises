package com.xuhj.library.http.base;


/**
 * 接口请求参数基类
 *
 * @author xuhj
 */
public class BaseReq {

    // 业务编号
    private String code;
    // 业务请求体body【经过AES加密】
    private String content;
    // 设备id
    private String device_id;
    // 当前时间戳【单位s】
    private String time;
    // 设备类型
    private String type;
    // token
    private String token;
    // MD5加密串签名【各参数拼接，根据Key排序 code=A10000&content......（不包含sign） 再MD5生成】
    private String sign;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
