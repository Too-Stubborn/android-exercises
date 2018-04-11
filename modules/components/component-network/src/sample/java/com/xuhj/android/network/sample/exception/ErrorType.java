package com.xuhj.android.network.sample.exception;


/**
 * 网络请求错误类型表
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public enum ErrorType {

    UNKNOW("-1", "未知错误"),

    NETWORK_IS_NOT_AVAILABLE("1", "网络不可用"),

    CONNECT_TO_SERVER_FAILED("2", "服务器连接失败"),

    TOKEN_EXPIRE("3", "Token过期"),

    REQUEST_FAILED("4", "请求失败"),

    SIGN_WRONG("5", "签名错误"),

    JSON_PARSE_FAILED("6", "解析错误");

    private String code;
    private String message;

    ErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

