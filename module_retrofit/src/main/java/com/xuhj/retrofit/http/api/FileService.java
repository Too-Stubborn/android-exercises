package com.xuhj.retrofit.http.api;


import com.xuhj.retrofit.http.base.BaseResp;
import com.xuhj.retrofit.http.request.file.UploadFileReq;
import com.xuhj.retrofit.http.response.file.UploadFileResp;

import java.io.File;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * 文件相关接口
 */
public interface FileService {

    /**
     * @param body e.g.:RequestBody.create(MediaType.parse("application/octet-stream"), new File(""));
     * @return
     */
    @Multipart
    @POST("v1/img/upload")
    Call<BaseResp<UploadFileResp>> uploadFile(@Part("data\";filename=\"image\"") RequestBody body);

    /**
     * 上传文件base64
     *
     * @param req
     * @return
     */
    @POST("v1/img/upload")
    Call<BaseResp<UploadFileResp>> uploadFile(@Body UploadFileReq req);

    /**
     * 下载
     */
    @GET
    Call<File> getFile(@Url String url);
}
