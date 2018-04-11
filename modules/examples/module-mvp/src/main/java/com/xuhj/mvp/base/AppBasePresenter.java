package com.xuhj.mvp.base;

import com.xuhj.library.base.mvp.BaseModel;
import com.xuhj.library.base.mvp.BasePresenter;
import com.xuhj.library.base.mvp.BaseView;

/**
 * 描述
 *
 * @author xuhj
 */
public abstract class AppBasePresenter<M extends BaseModel, V extends BaseView> extends BasePresenter<M, V> {

    public AppBasePresenter(V v) {
        super(v);
    }

}
