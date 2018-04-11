package com.xuhj.android.base.core.mvp;

/**
 * View视图层：只负责UI的处理，数据处理交给Presenter
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/3/30
 */
public interface BaseView {

    /**
     * View是否处于活动状态
     */
    boolean isActive();
}
