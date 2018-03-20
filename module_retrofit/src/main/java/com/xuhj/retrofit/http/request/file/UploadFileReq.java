package com.xuhj.retrofit.http.request.file;

public class UploadFileReq {

    //图片格式后缀，如".png", ".jpg"等等
    private String type;
    //Base64转换
    private String data;
    //图片文字描述
    private String desc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
