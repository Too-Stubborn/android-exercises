package com.xuhj.android.network;


import android.content.Context;
import android.support.annotation.NonNull;

import com.xuhj.android.network.subscriber.BaseSubscriber;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 网络请求管理类
 * <p>
 * 使用说明：
 * Step1: HttpFactory.getInstance().init();
 * Step2: HttpFactory.getClient().create(IService.class);
 * Step3: HttpFactory.enqueue();
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public class HttpFactory {
    private static final String TAG = "HttpFactory";

    /**
     * 单例模式DCL
     */
    private volatile static HttpFactory sInstance = null;

    private HttpFactory() {
    }

    public static HttpFactory getInstance() {
        if (sInstance == null) {
            synchronized (HttpFactory.class) {
                if (sInstance == null) {
                    sInstance = new HttpFactory();
                }
            }
        }
        return sInstance;
    }

    private Retrofit mRetrofit;
    private HttpConfiguration mDefaultConfig;

    /**
     * 创建OkHttpClient
     *
     * @param config config
     * @return OkHttpClient
     */
    private static OkHttpClient createOkHttpClient(HttpConfiguration config) {
        // okhttp log output
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        //配置Cache
        File cacheFile = new File(config.context.getCacheDir(), "HttpResponseCache");
        //默认设置缓存大小10MB
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        // 生成Builder对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 优先添加自定义拦截器
        for (Interceptor i : config.interceptors) {
            builder.addInterceptor(i);
        }

        // 配置默认参数
        builder.addInterceptor(logging) // log输出拦截器，添加有先后顺序
                .cache(cache) //网络缓存，目前只支持GET方式
                .readTimeout(config.timeout, TimeUnit.SECONDS)
                .connectTimeout(config.timeout, TimeUnit.SECONDS);
        return builder.build();
    }

    /**
     * 创建Retrofit.Builder，便于拓展
     *
     * @return Retrofit.Builder
     */
    private static Retrofit.Builder createRetrofitBuilder(HttpConfiguration config) {
        // 生成Builder对象
        Retrofit.Builder builder = new Retrofit.Builder();
        /*
            基本参数配置
         */
        // 优先添加自定义解析器
        for (Converter.Factory c : config.converters) {
            builder.addConverterFactory(c);
        }
        // 配置默认参数
        builder.client(createOkHttpClient(config))
                .baseUrl(config.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())  // Gson解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());  // 支持RxJava模式
        return builder;
    }

    /**
     * 获取Retrofit单例实例
     *
     * @return Retrofit
     */
    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (HttpFactory.class) {
                if (mRetrofit == null) {
                    if (!isInited()) {
                        throw new UnsupportedOperationException("Please initialize HttpFactory");
                    }
                    mRetrofit = createRetrofitBuilder(mDefaultConfig).build();
                }
            }
        }
        return mRetrofit;
    }

    /**
     * 是否已初始化
     */
    private boolean isInited() {
        return mDefaultConfig != null;
    }

    // --------------------------------------------------------------------------------------------
    // ------ Public API Methods ------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------

    /**
     * 初始化
     *
     * @param config 配置信息
     */
    public void init(HttpConfiguration config) {
        mDefaultConfig = config;
    }

    /**
     * 获取Retrofit实例
     *
     * @return Retrofit
     */
    public Retrofit getClient() {
        return getRetrofit();
    }

    /**
     * 创建新的Retrofit实例
     *
     * @param context context
     * @param baseUrl baseUrl
     * @return Retrofit
     */
    public static Retrofit newClient(Context context, String baseUrl) {
        return createRetrofitBuilder(HttpConfiguration.createDefault(context, baseUrl)).build();
    }
    // ------ 接口请求与返回封装方法 --------------------------------------------------------------

    /**
     * 封装下RxJava请求与回调，省去重复代码
     *
     * @param observable Retrofit返回数据对象
     * @param subscriber 观察者
     * @param <T>        Response结构体
     * @return 观察者
     */
    public static <T> BaseSubscriber enqueue(Observable<T> observable, @NonNull BaseSubscriber<T> subscriber) {
        BaseSubscriber baseSubscriber = observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber);
        return baseSubscriber;
    }

    /**
     * 支持RxJava链式编程
     *
     * @param observable Retrofit返回数据对象
     * @param <T>        Response结构体
     * @return 观察者
     */
    public static <T> Observable<T> enqueue(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
