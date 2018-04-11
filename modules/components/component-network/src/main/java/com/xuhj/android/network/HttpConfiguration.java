package com.xuhj.android.network;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import retrofit2.Converter;


/**
 * 网络请求配置信息
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public class HttpConfiguration {

    Context context;
    String baseUrl;
    long timeout = 30;
    List<Interceptor> interceptors = new ArrayList<>();
    List<Converter.Factory> converters = new ArrayList<>();

    private HttpConfiguration(Builder builder) {
        this.context = builder.context;
        this.baseUrl = builder.baseUrl;
        this.timeout = builder.timeout;
        this.interceptors = builder.interceptors;
        this.converters = builder.converters;
    }

    /**
     * 创建默认配置
     *
     * @param context context
     */
    public static HttpConfiguration createDefault(Context context) {
        return new Builder(context).build();
    }

    // ------ 建造者模式 --------------------------------------------------------------------------

    /**
     * 建造者模式
     */
    public static final class Builder {

        private Context context;
        // 请求URL
        private String baseUrl;
        // 请求超时时间【单位s】
        private long timeout = 30;
        // 拦截器
        private List<Interceptor> interceptors = new ArrayList<>();
        // 转换器
        private List<Converter.Factory> converters = new ArrayList<>();

        public Builder(Context context) {
            this.context = context;
        }

        public HttpConfiguration build() {
            if (baseUrl == null || baseUrl.length() == 0) {
                throw new IllegalArgumentException("baseurl can not be empty");
            }
            return new HttpConfiguration(this);
        }

        /**
         * 设置请求URL
         *
         * @param baseUrl baseUrl
         */
        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 设置请求超时时间【单位s】
         *
         * @param timeout timeout
         */
        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        /**
         * 可以添加多个拦截器
         *
         * @param interceptor interceptor
         */
        public Builder addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        /**
         * 可以添加多个转换器
         *
         * @param converter converter
         */
        public Builder addConverterFactory(Converter.Factory converter) {
            this.converters.add(converter);
            return this;
        }

    }

}
