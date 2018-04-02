package com.xuhj.library.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * 策略模式
 *
 * @author xuhj
 */
public interface ImageLoadStrategy {

    public enum Mode {
        FIT_CENTER,
        CENTER_CROP,
        CIRCLE
    }

    void load(Context context, String url, ImageView imageView);

    void load(Context context, String url, ImageView imageView, Mode mode);

    void load(Context context, String url, ImageView imageView, Mode mode, int loadingResId, int errorResId);

}
