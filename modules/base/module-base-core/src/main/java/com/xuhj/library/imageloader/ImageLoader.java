package com.xuhj.library.imageloader;

/**
 * 工厂模式--图片加载方法定义
 *
 * @author xuhj
 */
public class ImageLoader {

    public static GlideManager getInstance() {
        return GlideManager.getInstance();
    }

    private ImageLoader() {
    }

}
