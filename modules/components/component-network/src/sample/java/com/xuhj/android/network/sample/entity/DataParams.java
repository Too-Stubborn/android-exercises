package com.xuhj.android.network.sample.entity;

/**
 * 返回业务Data层封装，单个对象和数据集
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public class DataParams<T, F> {

    // 请求参数与回传
    private T parameter;
    // 响应数据
    private F data;
    // 状态码
    private String statusCode;
    // 提示消息
    private String msg;

//    /**
//     * 状态码是否成功
//     */
//    public boolean isSuccess() {
//        return TextUtils.equals(statusCode, Constants.HttpStatusCode.SUCCESS);
//    }

    public T getParameter() {
        return parameter;
    }

    public void setParameter(T parameter) {
        this.parameter = parameter;
    }

    public F getData() {
        return data;
    }

    public void setData(F data) {
        this.data = data;
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
}
