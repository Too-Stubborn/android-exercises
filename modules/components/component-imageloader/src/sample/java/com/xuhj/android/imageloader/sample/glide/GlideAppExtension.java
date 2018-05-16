package com.xuhj.android.imageloader.sample.glide;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

/**
 * 描述
 *
 * @author xuhj <a href="mailto:1604983712@qq.com">Contact me.</a>
 * @version 1.0.0
 * @since 2018/4/11
 */
@GlideExtension
public class GlideAppExtension {
    private GlideAppExtension() {
    }

    @GlideOption
    public static RequestOptions toCircle(RequestOptions options) {
        return options
                .fitCenter();
    }
}
