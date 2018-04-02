package com.xuhj.library.imageloader;

/**
 * 策略模式
 *
 * @author xuhj
 */
public interface ImageLoadStrategy {

    /**
     * 执行加载
     */
    void load();

    /**
     * 图片显示模式
     */
    public static enum Mode {
        FIT_CENTER,
        CENTER_CROP,
        CIRCLE
    }

}
