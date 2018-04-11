package com.xuhj.android.network.sample.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.xuhj.android.network.sample.util.NetworkUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;

/**
 * 自定义拦截请求处理
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public class ApiInterceptor implements Interceptor {
    private static final String TAG = "ApiInterceptor";

    private Context context;

    public ApiInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        //-------------------- 配置缓存机制Request --------------------

        //离线读取本地缓存，在线获取最新数据(读取单个请求的请求头，亦可统一设置)
        //目前缓存方式只支持GET，不支持POST，因此不采用
        //如果没有网络的情况下，设置强制使用缓存
        if (!NetworkUtils.isNetworkAvailable(context)) {
            requestBuilder.cacheControl(CacheControl.FORCE_CACHE);
        }

        //-------------------- 添加URL公共参数 ----------------------
//        HttpUrl httpUrl = request.url().newBuilder()
//                .addEncodedQueryParameter("device", "android")
//                .build();
//        requestBuilder.url(httpUrl);

        //-------------------- 添加Form表单公共参数 ------------------
//        String token = "123456789";
//        //设置公共参数
//        HashMap<String, Object> publicParams = new HashMap<>();
//        publicParams.put("token", token);
//        //重构requestBody
//        AppendRequestBody newRequestBody = new AppendRequestBody(request.body(), publicParams);
//        //获取请求方式,如果是POST，则添加自定义请求体，因为只有POST才有请求体
//        String method = request.method();
//        if (TextUtils.equals(method, "POST")) {
//            requestBuilder.post(newRequestBody);
//        }

        //-------------------------- 执行Http请求 --------------------------
        //执行request请求
        Response response = chain.proceed(requestBuilder.build());
        Response.Builder responseBuilder = response.newBuilder();

        //-------------------- 配置缓存机制Response -------------------------
        //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
        //移除干扰信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
        responseBuilder.removeHeader("Pragma");
        if (NetworkUtils.isNetworkAvailable(context)) {
            int maxAge = 0;// 有网络时 设置缓存超时时间
            if (request.cacheControl() == null || TextUtils.isEmpty(request.cacheControl().toString())) {
                responseBuilder.header("Cache-Control", "public, max-age=" + maxAge);
            } else {
                responseBuilder.header("Cache-Control", request.cacheControl().toString());
            }
        } else {
            int maxStale = 60 * 60;// 无网络时 设置缓存超时时间
            responseBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
        }
        return responseBuilder.build();
    }

    /**
     * 添加公共参数
     */
    private class AppendRequestBody extends RequestBody {

        private RequestBody body;
        private String parameter;

        AppendRequestBody(RequestBody body, HashMap<String, Object> map) {
            this.body = body;
            this.parameter = "";
            Iterator itor = map.entrySet().iterator();
            while (itor.hasNext()) {
                Map.Entry entry = (Map.Entry) itor.next();
                String key = (String) entry.getKey();
                Object value = (Object) entry.getValue();
                this.parameter += "&" + key + "=" + value;
            }
        }

        @Override
        public long contentLength() throws IOException {
            return body.contentLength() + parameter.length();
        }

        @Override
        public MediaType contentType() {
            return body.contentType();
        }

        @Override
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            body.writeTo(bufferedSink);
            bufferedSink.writeUtf8(parameter);
        }

    }

    /**
     * 把body转成string
     *
     * @param request request
     * @return string
     */
    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            if (copy.body() == null)
                return "";
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
