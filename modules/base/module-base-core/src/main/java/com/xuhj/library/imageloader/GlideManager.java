package com.xuhj.library.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.xuhj.library.util.BitmapUtils;

/**
 * Glide图片加载封装类
 *
 * @author xuhj
 */
public class GlideManager implements ImageLoadStrategy {

    private static GlideManager instance = new GlideManager();

    public static GlideManager getInstance() {
        return instance;
    }

    private GlideManager() {
    }

    @Override
    public void load(Context context, String url, ImageView imageView) {
        this.load(context, url, imageView, ImageLoadStrategy.Mode.FIT_CENTER);

    }

    @Override
    public void load(Context context, String url, ImageView imageView, ImageLoadStrategy.Mode mode) {
        this.load(context, url, imageView, mode, 0, 0);
    }

    @Override
    public void load(Context context, String url, ImageView imageView, ImageLoadStrategy.Mode mode, int loadingResId, int errorResId) {
        // create builder
        DrawableRequestBuilder builder = Glide.with(context)
                .load(url)
                .placeholder(loadingResId)
                .error(errorResId);
        // set mode
        setMode(context, builder, mode);
        // load
        builder.into(imageView);
    }

    /**
     * set image load mode
     *
     * @param context
     * @param builder
     * @param mode
     * @return
     */
    private DrawableRequestBuilder setMode(Context context, DrawableRequestBuilder builder, ImageLoadStrategy.Mode mode) {
        switch (mode) {
            case FIT_CENTER:
                builder.fitCenter();
                break;
            case CENTER_CROP:
                builder.centerCrop();
                break;
            case CIRCLE:
                builder.transform(new CircleTransform(context));
                break;
        }
        return builder;
    }

    /**
     * 把图片转换圆形
     */
    public class CircleTransform extends BitmapTransformation {

        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
            return BitmapUtils.getCircleBitmap(source);
        }

        @Override
        public String getId() {
            return CircleTransform.class.getSimpleName();
        }
    }
}
