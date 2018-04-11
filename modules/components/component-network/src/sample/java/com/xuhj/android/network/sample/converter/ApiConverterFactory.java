package com.xuhj.android.network.sample.converter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.MalformedJsonException;
import com.xuhj.android.network.BuildConfig;
import com.xuhj.android.network.sample.exception.ApiException;
import com.xuhj.android.network.sample.exception.ErrorType;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 自定义转换器
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public class ApiConverterFactory extends Converter.Factory {
    private static final String TAG = "OkHttp";

    // from表单
    public static final MediaType MEDIA_TYPE_FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded");
    // json字符串
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    // 文本
    public static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain;charset=utf-8");
    // 字符编码
    private static final Charset UTF_8 = Charset.forName("utf-8");

    private final Gson gson;

    public static ApiConverterFactory create() {
        return create(createMyGson());
    }

    public static ApiConverterFactory create(Gson gson) {
        return new ApiConverterFactory(gson);
    }

    private ApiConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    /**
     * 自定义gson
     */
    private static Gson createMyGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new MyRequestFactory<>(gson, adapter);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new MyResponseFactory<>(gson, adapter);
    }


    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }

    /**
     * 自定义requestBodyConverter处理类
     *
     * @param <T>
     */
    private class MyRequestFactory<T> implements Converter<T, RequestBody> {

        private final Gson gson;
        private final TypeAdapter<T> adapter;

        MyRequestFactory(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            String data = "";
            try {
                // 把请求对象转换成json
                String json = gson.toJson(value);
                // 打印请求体json
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "请求数据-->" + json);
                // 此处可以对请求数据进行加密等操作，如果出现异常，则content=""，继续执行请求;
                // ......

            } catch (Exception e) {
                e.printStackTrace();
            }

            // 此处可以添加请求体的公共参数
            // ......

            // 重新封装成json字符串上传
            String baseRequestJson = gson.toJson(value);
            return RequestBody.create(MEDIA_TYPE_JSON, baseRequestJson);
        }
    }

    /**
     * 自定义responseBodyConverter处理类
     *
     * @param <T>
     */
    private class MyResponseFactory<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        MyResponseFactory(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            // 此处对返回数据进行处理
            T data = null;
            try {
                // 解析服务器返回的数据，并处理相关业务
                // ......

                /*
                    共通业务逻辑处理
                    PS：注意校验顺序逻辑！！！
                 */
                // 校验请求失败、token过期、签名等操作
                // 如果校验不通过，则抛出异常 throw new ApiException();
                // ......

                // 此处可以对请求数据进行解密等操作
                // ......

                String dataJson = value.string();
                // 打印返回的数据内容
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "返回数据-->" + dataJson);
                // 解析content转换为对象，并返回
                data = adapter.fromJson(dataJson);
            } catch (MalformedJsonException e) {
                throw new ApiException(ErrorType.JSON_PARSE_FAILED);
            } catch (JsonParseException e) {
                throw new ApiException(ErrorType.JSON_PARSE_FAILED);
            } finally {
                value.close();
            }
            return data;
        }
    }


}
