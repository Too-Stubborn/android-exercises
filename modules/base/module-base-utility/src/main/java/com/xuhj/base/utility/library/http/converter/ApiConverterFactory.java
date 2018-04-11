package com.xuhj.library.http.converter;//package com.e_dewin.library.http.converter;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.e_dewin.library.BaseApplication;
//import com.e_dewin.library.BuildConfig;
//import com.e_dewin.library.annotation.API;
//import com.e_dewin.library.common.Constants;
//import com.e_dewin.library.http.ApiHelper;
//import com.e_dewin.library.http.ErrorType;
//import com.e_dewin.library.http.base.BaseReq;
//import com.e_dewin.library.http.base.BaseResp;
//import com.e_dewin.library.http.exception.ApiException;
//import com.e_dewin.library.util.AndroidUtils;
//import com.e_dewin.library.util.security.SecurityUtils;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonParseException;
//import com.google.gson.TypeAdapter;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.MalformedJsonException;
//
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//import java.nio.charset.Charset;
//
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.Converter;
//import retrofit2.Retrofit;
//
///**
// * 自定义转换器
// *
// * @author xuhj
// */
//public class ApiConverterFactory extends Converter.Factory {
//    private static final String TAG = "OkHttp";
//
//    // from表单
//    public static final MediaType MEDIA_TYPE_FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded");
//    // json字符串
//    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
//    // 文本
//    public static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain;charset=utf-8");
//    // 字符编码
//    private static final Charset UTF_8 = Charset.forName("utf-8");
//
//    private final Gson gson;
//
//    public static ApiConverterFactory create() {
//        return create(createMyGson());
//    }
//
//    public static ApiConverterFactory create(Gson gson) {
//        return new ApiConverterFactory(gson);
//    }
//
//    private ApiConverterFactory(Gson gson) {
//        if (gson == null) throw new NullPointerException("gson == null");
//        this.gson = gson;
//    }
//
//    /**
//     * 自定义gson
//     */
//    private static Gson createMyGson() {
//        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//    }
//
//    @Override
//    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//        API api = getAPIAnnotation(methodAnnotations);
//        return new MyRequestFactory<>(gson, adapter, api);
//    }
//
//    @Override
//    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//        return new MyResponseFactory<>(gson, adapter);
//    }
//
//
//    @Override
//    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//        return super.stringConverter(type, annotations, retrofit);
//    }
//
//    /**
//     * 自定义requestBodyConverter处理类
//     *
//     * @param <T>
//     */
//    private class MyRequestFactory<T> implements Converter<T, RequestBody> {
//
//        private final Gson gson;
//        private final TypeAdapter<T> adapter;
//        private final API api;
//
//        MyRequestFactory(Gson gson, TypeAdapter<T> adapter, API api) {
//            this.gson = gson;
//            this.adapter = adapter;
//            this.api = api;
//        }
//
//        @Override
//        public RequestBody convert(T value) throws IOException {
//            String content = "";
//            try {
//                // 把请求对象转换成json
//                String json = gson.toJson(value);
//                // 打印请求体json
//                if (BuildConfig.DEBUG)
//                    Log.d(TAG, "请求数据-->" + json);
//                // 对content进行AES加密，如果出现异常，则content=""，继续执行请求;
//                content = SecurityUtils.encodeByAES(json);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // 实例化请求对象
//            BaseReq req = new BaseReq();
//            // 业务编号
//            req.setCode(api.code());
//            // 请求内容【AES加密】
//            req.setContent(content);
//            // 当前时间戳【s】
//            req.setTime(System.currentTimeMillis() / 1000 + "");
//            // 设备类型
//            req.setType(Constants.DEVICE_TYPE);
//            // 设备id
//            req.setDevice_id(AndroidUtils.getDeviceId(BaseApplication.getInstance()));
//            // 会话token
//            req.setToken("token");
//            // 签名
//            req.setSign(ApiHelper.buildSign(req));
//
//            // 重新封装成json字符串上传
//            String baseRequestJson = gson.toJson(req);
//            return RequestBody.create(MEDIA_TYPE_JSON, baseRequestJson);
//        }
//    }
//
//    /**
//     * 自定义responseBodyConverter处理类
//     *
//     * @param <T>
//     */
//    private class MyResponseFactory<T> implements Converter<ResponseBody, T> {
//        private final Gson gson;
//        private final TypeAdapter<T> adapter;
//
//        MyResponseFactory(Gson gson, TypeAdapter<T> adapter) {
//            this.gson = gson;
//            this.adapter = adapter;
//        }
//
//        @Override
//        public T convert(ResponseBody value) throws IOException {
//            BaseResp response = null;
//            T data = null;
//            try {
//                // 解析服务器返回json内容
//                response = gson.fromJson(value.string(), BaseResp.class);
//
//                /*
//                    共通业务逻辑处理
//                    PS：注意校验顺序逻辑！！！
//                 */
//
//                // 请求失败，显示服务器返回的message
//                if (!response.isSuccess()) {
//                    throw new ApiException(response.getCode(), response.getMsg());
//                }
//                // 判断token是否过期
//                if (TextUtils.isEmpty(response.getToken())) {
//                    throw new ApiException(ErrorType.TOKEN_EXPIRE);
//                }
//                // 校验sign签名是否一致，防止数据被篡改
//                if (!TextUtils.equals(response.getSign(), ApiHelper.buildSign(response))) {
//                    throw new ApiException(ErrorType.SIGN_WRONG);
//                }
//
//                // 解密content内容
//                String dataJson = SecurityUtils.decodeByAES(response.getContent());
//                // 打印返回的数据内容
//                if (BuildConfig.DEBUG)
//                    Log.d(TAG, "返回数据-->" + dataJson);
//                // 解析content转换为对象，并返回
//                data = adapter.fromJson(dataJson);
//            } catch (MalformedJsonException e) {
//                throw new ApiException(ErrorType.JSON_PARSE_FAILED);
//            } catch (JsonParseException e) {
//                throw new ApiException(ErrorType.JSON_PARSE_FAILED);
//            } finally {
//                value.close();
//            }
//            return data;
//        }
//    }
//
//    /**
//     * 根据自定义注解API获取业务编码
//     *
//     * @param methodAnnotations
//     * @return
//     */
//    private API getAPIAnnotation(Annotation[] methodAnnotations) {
//        for (Annotation ann : methodAnnotations) {
//            if (ann instanceof API) {
//                return (API) ann;
//            }
//        }
//        return null;
//    }
//
//}
