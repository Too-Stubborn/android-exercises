package com.xuhj.library.http.exception;


import com.xuhj.library.http.ErrorType;

/**
 * 异常封装类
 *
 * @author xuhj
 */
public class ApiException extends RuntimeException {

    // 错误状态码
    private String errCode;
    // 错误信息
    private String errMessage;
    // 优先处理ErrorType，如果ErrorType=null，再处理errCode和errMessage
    private ErrorType errorType;

    public ApiException(String errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public ApiException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}
