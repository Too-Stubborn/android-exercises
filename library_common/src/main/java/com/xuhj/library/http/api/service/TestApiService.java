package com.xuhj.library.http.api.service;

import com.xuhj.library.http.annotation.API;
import com.xuhj.library.http.api.request.TestReq;
import com.xuhj.library.http.base.DataResp;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑    Code is far away from bug
 * 　　　　┃　　　┃代码无BUG   with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * API接口定义
 *
 * @author xuhj
 */
public interface TestApiService {

    /**
     * 测试API
     */
    @POST("apiservice")
    @API(code = "003")
    Observable<DataResp> testApi(@Body TestReq req);

}
