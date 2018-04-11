package com.xuhj.android.network.sample.exception;


/**
 * 异常封装类
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public class ApiException extends RuntimeException {

    // 错误状态码
    private String errCode;
    // 错误信息
    private String errMessage;
    // 优先处理自定义错误ErrorType，如果ErrorType=null，再处理errCode和errMessage
    private ErrorType errorType;

    public ApiException(String errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    @Override
    public String getMessage() {
        return errMessage;
    }

    // ------ Getter Setter -----------------------------------------------------------------------
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
