//package com.xuhj.imageloader;
//
//import android.content.Context;
//import android.widget.ImageView;
//
///**
// * 工厂模式--图片加载方法定义
// *
// * @author xuhj
// */
//public class ImageLoader {
//
//    private ImageLoader() {
//    }
//
//    public static Builder builder(Context context) {
//        return new Builder(context);
//    }
//
//    /**
//     * 构建者模式
//     */
//    public static class Builder {
//
//        /**
//         * 使用Glide框架，此处方便拓展，修改其他图片加载框架
//         */
//        public GlideManager build() {
//            if (context == null) {
//                throw new IllegalArgumentException("context is null");
//            }
//            if (imageView == null) {
//                throw new IllegalArgumentException("imageView is null");
//            }
//            return new GlideManager(this);
//        }
//
//        // 上下文
//        Context context;
//        // 图片链接
//        String url;
//        // 需要加载的View
//        ImageView imageView;
//        // 加载中的图片
//        int loadingResId = 0;
//        // 错误图片
//        int errorResId = 0;
//        // 图片显示模式
//        ImageLoadStrategy.Mode mode = ImageLoadStrategy.Mode.FIT_CENTER;
//
//        public Builder(Context context) {
//            this.context = context;
//        }
//
//        public Builder setUrl(String url) {
//            this.url = url;
//            return this;
//        }
//
//        public Builder setImageView(ImageView imageView) {
//            this.imageView = imageView;
//            return this;
//        }
//
//        public Builder setLoadingResId(int loadingResId) {
//            this.loadingResId = loadingResId;
//            return this;
//        }
//
//        public Builder setErrorResId(int errorResId) {
//            this.errorResId = errorResId;
//            return this;
//        }
//
//        public Builder setMode(ImageLoadStrategy.Mode mode) {
//            this.mode = mode;
//            return this;
//        }
//
//        public Context getContext() {
//            return context;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public ImageView getImageView() {
//            return imageView;
//        }
//
//        public int getLoadingResId() {
//            return loadingResId;
//        }
//
//        public int getErrorResId() {
//            return errorResId;
//        }
//
//        public ImageLoadStrategy.Mode getMode() {
//            return mode;
//        }
//
//
//    }
//}
