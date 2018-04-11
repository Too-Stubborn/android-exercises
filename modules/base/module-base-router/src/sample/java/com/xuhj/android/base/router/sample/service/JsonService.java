package com.xuhj.android.base.router.sample.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;
import com.xuhj.android.base.router.sample.router.RouterConsts;

import java.lang.reflect.Type;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/4/3
 */
@Route(path = RouterConsts.serviceJson, name = "Json解析服务")
public class JsonService implements SerializationService {
    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        System.out.println("json2Object");
        return new Gson().fromJson(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        System.out.println("object2Json");
        return new Gson().toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        System.out.println("parseObject");
        return new Gson().fromJson(input, clazz);
    }

    @Override
    public void init(Context context) {
        System.out.println("init");
    }
}
