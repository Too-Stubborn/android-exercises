package com.xuhj.library.http;

/**
 * 网络请求错误类型表
 *
 * @author xuhj
 */
public enum ErrorType {

    UNKNOW("-1", 0);

//    UNKNOW("-1", R.string.api_error_unknown),
//
//    NETWORK_IS_NOT_AVAILABLE("1", R.string.api_error_network_is_not_available),
//
//    CONNECT_TO_SERVER_FAILED("2", R.string.api_error_connect_to_server_failed),
//
//    TOKEN_EXPIRE("3", R.string.api_error_token_expire),
//
//    REQUEST_FAILED("4", R.string.api_error_request_failed),
//
//    SIGN_WRONG("5", R.string.api_error_sign_wrong),
//
//    JSON_PARSE_FAILED("6", R.string.api_error_json_parse_failred);

    private String code;
    private int msgResId;

    ErrorType(String code, int msgResId) {
        this.code = code;
        this.msgResId = msgResId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMsgResId() {
        return msgResId;
    }

    public void setMsgResId(int msgResId) {
        this.msgResId = msgResId;
    }
}

