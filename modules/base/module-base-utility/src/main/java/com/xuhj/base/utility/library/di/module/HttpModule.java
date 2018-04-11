package com.xuhj.library.di.module;

import android.text.TextUtils;
import android.util.Log;

import com.xuhj.android.core.BaseApplication;
import com.xuhj.library.BuildConfig;
import com.xuhj.library.common.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
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
 * @author xuhj
 */
@Module
public class HttpModule {
    private static final String TAG = "HttpModule";

    private String mBaseUrl;
    private List<Interceptor> mInterceptors;
    private List<Converter.Factory> mConverters;

    private HttpModule() {
    }

    private HttpModule(Builder builder) {
        this.mBaseUrl = builder.baseUrl;
        this.mInterceptors = builder.interceptors;
        this.mConverters = builder.converters;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Retrofit.Builder builder, @Named("BaseUrl") String baseUrl) {
        Log.i(TAG, "provideRetrofit: ");
        return builder.baseUrl(baseUrl).build();
    }

    @Singleton
    @Provides
    public Retrofit.Builder provideRetrofitBuilder(OkHttpClient client, List<Converter.Factory> converters) {
        Log.i(TAG, "provideRetrofitBuilder: ");
        // 生成Builder对象
        Retrofit.Builder builder = new Retrofit.Builder();
        /*
            基本参数配置
         */
        // 优先添加自定义解析器
        for (Converter.Factory c : converters) {
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
    public OkHttpClient provideOkHttpClient(BaseApplication application, List<Interceptor> interceptors) {
        Log.i(TAG, "provideOkHttpClient: ");
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        //配置Cache
        File cacheFile = new File(application.getCacheDir(), "HttpResponseCache");
        //默认设置缓存大小10MB
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        // 生成Builder对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /*
            基本参数配置
         */
        // 优先添加自定义拦截器
        for (Interceptor i : interceptors) {
            builder.addInterceptor(i);
        }
        // 配置默认参数
        builder.addInterceptor(logging) // log输出拦截器，添加有先后顺序
                .cache(cache) //网络缓存，目前只支持GET方式
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
        // 创建Client对象
        return builder.build();
    }

    @Singleton
    @Provides
    @Named("BaseUrl")
    public String provideBaseUrl() {
        Log.i(TAG, "provideBaseUrl: " + mBaseUrl);
        return mBaseUrl == null ? Constants.BASE_URL : mBaseUrl;
    }

    @Singleton
    @Provides
    public List<Interceptor> provideInterceptors() {
        Log.i(TAG, "provideInterceptor: ");
        return mInterceptors;
    }

    @Singleton
    @Provides
    public List<Converter.Factory> provideConverter() {
        Log.i(TAG, "provideConverter: ");
        return mConverters;
    }

    /**
     * 建造模式
     */
    public static final class Builder {
        private String baseUrl;
        private List<Interceptor> interceptors = new ArrayList<>();
        private List<Converter.Factory> converters = new ArrayList<>();

        public Builder() {
        }

        /**
         * 设置请求URL
         *
         * @param baseUrl
         * @return
         */
        public Builder setBaseUrl(String baseUrl) {
            if (TextUtils.isEmpty(baseUrl)) {
                throw new IllegalArgumentException("baseurl can not be empty");
            }
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 可以添加多个拦截器
         *
         * @param interceptor
         * @return
         */
        public Builder addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        /**
         * 可以添加多个转换器
         *
         * @param converter
         * @return
         */
        public Builder addConverterFactory(Converter.Factory converter) {
            this.converters.add(converter);
            return this;
        }

        public HttpModule build() {
            return new HttpModule(this);
        }
    }
}
