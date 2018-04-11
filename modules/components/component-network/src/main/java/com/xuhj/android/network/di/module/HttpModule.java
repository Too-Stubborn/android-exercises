package com.xuhj.android.network.di.module;

import android.content.Context;
import android.text.TextUtils;

import com.xuhj.android.network.BuildConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求对象提供器
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
@Module
public class HttpModule {
    private static final String TAG = "HttpModule";

    private Context mContext;
    private String mBaseUrl;
    private long mTimeout;
    private List<Interceptor> mInterceptors;
    private List<Converter.Factory> mConverters;

    private HttpModule(HttpModule.Builder builder) {
        this.mContext = builder.context;
        this.mBaseUrl = builder.baseUrl;
        this.mTimeout = builder.timeout;
        this.mInterceptors = builder.interceptors;
        this.mConverters = builder.converters;
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder.baseUrl(mBaseUrl).build();
    }

    @Singleton
    @Provides
    public Retrofit.Builder provideRetrofitBuilder(OkHttpClient client) {
        // 生成Builder对象
        Retrofit.Builder builder = new Retrofit.Builder();
        /*
            基本参数配置
         */
        // 优先添加自定义解析器
        for (Converter.Factory c : mConverters) {
            builder.addConverterFactory(c);
        }
        // 配置默认参数
        builder.client(client)
                .addConverterFactory(GsonConverterFactory.create())  // Gson解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());  // 支持RxJava模式
        return builder;
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        //配置Cache
        File cacheFile = new File(mContext.getCacheDir(), "HttpResponseCache");
        //默认设置缓存大小10MB
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        // 生成Builder对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /*
            基本参数配置
         */
        // 优先添加自定义拦截器
        for (Interceptor i : mInterceptors) {
            builder.addInterceptor(i);
        }
        // 配置默认参数
        builder.addInterceptor(logging) // log输出拦截器，添加有先后顺序
                .cache(cache) //网络缓存，目前只支持GET方式
                .readTimeout(mTimeout, TimeUnit.SECONDS)
                .connectTimeout(mTimeout, TimeUnit.SECONDS);
        // 创建Client对象
        return builder.build();
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

        public HttpModule build() {
            return new HttpModule(this);
        }

        /**
         * 设置请求URL
         *
         * @param baseUrl baseUrl
         */
        public Builder setBaseUrl(String baseUrl) {
            if (TextUtils.isEmpty(baseUrl)) {
                throw new IllegalArgumentException("baseurl can not be empty");
            }
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
