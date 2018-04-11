package com.xuhj.android.base.eventbus.sample;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/4/9
 */
public class ObjectMessage {

    public ObjectMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
