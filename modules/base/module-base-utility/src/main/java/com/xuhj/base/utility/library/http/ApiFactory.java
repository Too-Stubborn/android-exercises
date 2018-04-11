package com.xuhj.library.http;

import com.xuhj.android.core.BaseApplication;
import com.xuhj.library.BuildConfig;
import com.xuhj.library.common.Constants;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 网络请求管理类
 *
 * @author xuhj
 * @deprecated 暂时弃用，用Dagger2注入代替
 */
@Deprecated
public class ApiFactory {
    private static final String TAG = "ApiFactory";

    private static Retrofit.Builder sDefaultBuilder;
    private static Retrofit sRetrofit;

    /**
     * 生成Retrofit.Builder，便于拓展
     *
     * @return
     */
    public static Retrofit.Builder getDefaultBuilder() {
        if (sDefaultBuilder == null) {
            synchronized (ApiFactory.class) {
                if (sDefaultBuilder == null) {
                    // okhttp log output
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

                    //配置Cache
                    File cacheFile = new File(BaseApplication.getInstance().getCacheDir(), "HttpResponseCache");
                    //默认设置缓存大小10MB
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

                    //基本配置
                    OkHttpClient client = new OkHttpClient.Builder()
//                            .addInterceptor(new ApiInterceptor()) //自定义拦截器，添加有先后顺序
                            .addInterceptor(logging) // log输出拦截器，添加有先后顺序
                            .cache(cache) //网络缓存，目前只支持GET方式
                            .readTimeout(30, TimeUnit.SECONDS)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .build();

                    sDefaultBuilder = new Retrofit.Builder()
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())  //Gson解析器
//                            .addConverterFactory(ApiConverterFactory.create())  // 自定义解析器
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());  // 支持RxJava模式
                }
            }
        }
        return sDefaultBuilder;
    }

    /**
     * 获取Retrofit实例
     *
     * @return
     */
    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (ApiFactory.class) {
                if (sRetrofit == null) {
                    sRetrofit = getDefaultBuilder().baseUrl(Constants.BASE_URL).build();
                }
            }
        }
        return sRetrofit;
    }

    /**
     * 生成Service对象
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> service) {
        return getRetrofit().create(service);
    }

    /**
     * 针对不同服务器地址，生成新的Retrofit实例
     *
     * @param url
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T create(String url, Class<T> service) {
        return getDefaultBuilder().baseUrl(url).build().create(service);
    }

    /**
     * 封装下RxJava请求与回调，省去重复代码
     *
     * @param observable
     * @param callback
     * @param <T>
     * @return
     */
//    public static <T> Observer<T> enqueue(Context context, Observable<BaseResp<T>> observable, ApiCallback<T> callback) {
//        return handleResponseData(observable).subscribeWith(new ApiSubscriber<T>(context, callback));
//    }
//
//    public static <T> Observer<T> enqueueWithProgress(Context context, Observable<BaseResp<T>> observable, ApiCallback<T> callback) {
//        return handleResponseData(observable).subscribeWith(new ProgressSubscriber<T>(context, callback));
//    }

//    /**
//     * 去掉Callback这一层封装
//     */
//    public static <T> ApiSubscriber<T> enqueue(Observable<BaseResp<T>> observable, @NonNull ApiSubscriber<T> subscriber) {
//        return observable
//                .compose(ApiHelper.<T>transformerForResponseData())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(subscriber);
//    }
//
//    /**
//     * 自定义订阅者返回处理，无数据封装
//     */
//    public static <T> ApiSubscriber<T> enqueueWithCustom(Observable<T> observable, @NonNull ApiSubscriber<T> subscriber) {
//        return observable
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(subscriber);
//    }
//
//    /**
//     * 处理数据转换，并返回Observer对象
//     * <pre>
//     * .subscribeOn(Schedulers.io())
//     * .unsubscribeOn(Schedulers.io())
//     * .observeOn(AndroidSchedulers.mainThread())
//     *
//     * @param observable
//     * @param <T>
//     * @return
//     */
//    private static <T> Observable<T> handleResponseData(Observable<BaseResp<T>> observable) {
//        return observable
//                .compose(ApiHelper.<T>transformerForResponseData())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

}
