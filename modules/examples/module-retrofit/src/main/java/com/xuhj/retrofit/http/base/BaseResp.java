package com.xuhj.retrofit.http.base;


import java.util.HashMap;
import java.util.Map;

/**
 * 接口返回参数基类
 *
 * @author xuhj
 */
public class BaseResp<T> {

    public static final Map<Integer, String> API_CODE_MAP = new HashMap<Integer, String>() {
        {
            put(400, "请求不合法");
            put(4010, "登陆失败");
            put(4011, "没有短信验证码");
            put(4012, "用户已存在");
            put(4013, "用户不存在");
            put(4200, "没有权限");
        }
    };

    /**
     * 400	请求不合法
     * 4010	登陆失败
     * 4011	没有短信验证码
     * 4012	用户已存在
     * 4013	用户不存在
     * 4200 没有权限
     * <p>
     * 200	成功
     * 404	请求数据不存在
     * <p>
     * 500	服务器内部错误
     */
    //返回码是否成功
    public boolean isSuccess() {
        if (code == 200)
            return true;
        //登录失效
        if (code == 4010) {
//            CommonUtils.logout(ActivityUtils.getCurrentActivity());
        }
        return false;
    }

    //响应码。成功为2XX，失败为4XX和5XX
    private int code;
    //字符串，说明错误原因
    private String desc;
    //成功时返回的结果。可能是一个数值，也可能是一个JSON串
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}