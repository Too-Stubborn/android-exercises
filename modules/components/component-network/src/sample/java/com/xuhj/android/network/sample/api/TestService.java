package com.xuhj.android.network.sample.api;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/10
 */
public interface TestService {

    /**
     * 淘宝IP地址查询
     */
    @GET("/service/getIpInfo.php?")
    Observable<ResponseBody> queryIP(@Query("ip") String ip);

}
