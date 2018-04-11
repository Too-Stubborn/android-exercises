package com.xuhj.library.http;


import android.content.Context;

import com.google.gson.Gson;
import com.xuhj.library.http.exception.ApiException;
import com.xuhj.library.util.ToastUtils;
import com.xuhj.library.util.security.SecurityUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * API工具类
 *
 * @author xuhj
 */
public class ApiHelper {

    /**
     * 异常错误信息处理
     *
     * @return 是否消费处理事件，true表示消费，并拦截，false不消费，继续往下走
     */
    public static boolean handleError(Context context, Throwable t) {
        // 异常错误日志输出：
        t.printStackTrace();

        if (t instanceof ApiException) {  // 自定义错误
            ApiException e = (ApiException) t;
            ErrorType errorType = e.getErrorType();

            if (errorType != null) {  // 自定义ErrorType的错误
                String code = errorType.getCode();
                int msgResId = errorType.getMsgResId();

                switch (errorType) {
//                    case REQUEST_FAILED:  // 请求失败
//                        // 显示错误信息
//                        ToastUtils.show(context, msgResId);
//                        break;
//
//                    case TOKEN_EXPIRE:  // 登录过期
//                        // 显示错误信息
//                        ToastUtils.show(context, msgResId);
//                        // 消费事件，禁止往下传递，并执行登录操作
//                        return true;
//
//                    case SIGN_WRONG:  // 签名错误
//                        // 显示错误信息
//                        ToastUtils.show(context, msgResId);
//                        break;

                    default:
                        // 显示错误信息
                        ToastUtils.show(context, msgResId);
                        break;
                }

            } else {  // status = failure，显示服务器返回的错误信息
                ToastUtils.show(context, e.getErrMessage());
            }

        } else {  // 其他系统错误

        }
        return false;
    }

    /**
     * 参数签名
     * <p>
     * 这里定义参数为Object，为了兼容Request和Response两个对象
     */
    @SuppressWarnings("unchecked")
    public static String buildSign(Object object) {
        //先把对象转成json字符串，由于对象本身就是由json字符串转成的，所以这里不需要验证格式
        String jsonStr = new Gson().toJson(object);
        //把json字符串转成Map，方便排序
        Map<String, Object> map = new Gson().fromJson(jsonStr, Map.class);
        //获取所有key
        Set<String> set = map.keySet();
        //转换成数组
        String[] keys = new String[set.size()];
        set.toArray(keys);
        //排序所有的key
        Arrays.sort(keys);
        //所有参数
        StringBuilder params = new StringBuilder();
        //开始拼接参数
        for (String key : keys) {
            //手动判断需要签名的参数
            if (key.equals("code")
                    || key.equals("content")
                    || key.equals("time")
                    || key.equals("type")
                    || key.equals("device_id")
                    || key.equals("token")) {
                params.append("&").append(key).append("=").append(map.get(key));
            }
        }
        return params.length() > 0 ? SecurityUtils.MD5(params.substring(1)) : "";//系统签名
    }
}
