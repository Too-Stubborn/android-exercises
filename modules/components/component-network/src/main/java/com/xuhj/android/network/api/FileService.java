package com.xuhj.android.network.api;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 文件相关接口
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public interface FileService {

    /**
     * 上传文件base64
     *
     * @param url 上传地址
     * @param req 图片base64内容
     */
    @POST
    Observable<ResponseBody> uploadFile(@Url String url,
                                        @Body String req);

    /**
     * 上传文件
     *
     * @param url  上传地址
     * @param body e.g.:RequestBody.create(MediaType.parse("application/octet-stream"), new File(""));
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String url,
                                        @Part("image\";filename=\"image.png\"") RequestBody body);

    /**
     * 多文件上传
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadFiles(@Url String url, @Part List<MultipartBody.Part> partList);

    /**
     * 多文件上传
     *
     * @param url         上传地址
     * @param description 文件名字
     * @param maps        文件集合
     */
    @POST
    Observable<ResponseBody> uploadFiles(
            @Url String url,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);

    /**
     * 下载文件
     *
     * @param fileUrl 下载地址
     */
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    /**
     * 下载大文件@Streaming
     *
     * @param fileUrl 下载地址
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadLargeFile(@Url String fileUrl);

}
