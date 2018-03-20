package com.xuhj.retrofit.http;

import android.content.Context;
import android.util.Log;

import com.xuhj.retrofit.http.base.BaseResp;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * 自定义处理Http回调
 */
public abstract class MyCallback<T> implements Callback<T> {
    private static final String TAG = "MyCallback";

    private Context context;

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        if (response.isSuccess()) {
            if (response.body() != null) {
                if (response.body() instanceof BaseResp) {
                    BaseResp resp = (BaseResp) response.body();
//                    if (resp.getCode() == 4010) {
//                        ToastUtils.showToast("登录过期");
//                        CommonUtils.gotoLoginActivity(context);
//                        return;
//                    } else {
                    success(response.body());
//                    }
                } else {
                    Log.d(TAG, "onResponse: 返回类型错误");
                }
            } else {
                Log.d(TAG, "onResponse: 返回null");
            }
        } else {
//            success(null);//接口未通时用的
            int statusCode = response.code();
            ResponseBody errorBody = response.errorBody();
            Log.d(TAG, "onResponse: " + "statusCode=%d" + statusCode);
        }
    }

    @Override
    public void onFailure(Throwable t) {
//        success(null);//接口未通时用的
        Log.d(TAG, "onFailure: " + t.getMessage());
        failure(t);
    }

    public abstract void success(T response);

    public abstract void failure(Throwable t);

}
