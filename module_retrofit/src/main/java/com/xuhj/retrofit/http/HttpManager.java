package com.xuhj.retrofit.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.xuhj.retrofit.BuildConfig;
import com.xuhj.retrofit.common.Constants;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Http处理工具
 */
public class HttpManager {

    private static Retrofit retrofit;

    private static Retrofit getRetrofit() {
        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            OkHttpClient client = new OkHttpClient();
            client.interceptors().add(new MyInterceptor()); //添加有先后顺序
            client.interceptors().add(logging);

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP_SERVRE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static <T> T getService(Class<T> service) {
        return getRetrofit().create(service);
    }
}
