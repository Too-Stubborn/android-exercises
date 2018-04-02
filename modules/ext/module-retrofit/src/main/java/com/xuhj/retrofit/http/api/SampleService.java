package com.xuhj.retrofit.http.api;


import com.xuhj.retrofit.http.request.sample.SampleReq;
import com.xuhj.retrofit.http.base.BaseResp;
import com.xuhj.retrofit.http.response.sample.SampleResp;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * 接口文档
 */
public interface SampleService {

    /**
     * 示例代码POST
     */
    @POST("path/...")
    Call<BaseResp<SampleResp>> getSampleByPost(@Body SampleReq req, @Query("pageNo") int pageNo);

    /**
     * 示例代码GET
     */
    @GET("path/...")
    Call<BaseResp<SampleResp>> getSampleByGet(@Body SampleReq req, @Query("pageNo") int pageNo);
}
