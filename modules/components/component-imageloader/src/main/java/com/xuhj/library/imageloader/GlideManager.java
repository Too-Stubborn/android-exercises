package com.xuhj.library.imageloader;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.xuhj.library.imageloader.util.BitmapUtils;


/**
 * Glide图片加载封装类
 *
 * @author xuhj
 */
public final class GlideManager implements ImageLoadStrategy {

    private ImageLoader.Builder mBuilder;

    GlideManager(ImageLoader.Builder builder) {
        this.mBuilder = builder;
    }

    @Override
    public void load() {
        // create builder
        DrawableRequestBuilder builder = Glide.with(mBuilder.context)
                .load(mBuilder.getUrl())
                .placeholder(mBuilder.getLoadingResId())
                .error(mBuilder.getErrorResId());
        // set mode
        setMode(mBuilder.getContext(), builder, mBuilder.getMode());
        // load
        builder.into(mBuilder.getImageView());
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
    private class CircleTransform extends BitmapTransformation {

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
