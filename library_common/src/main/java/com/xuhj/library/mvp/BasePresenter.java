package com.xuhj.library.mvp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Presenter控制层：连接View与Model之间的通信
 *
 * @author xuhj
 */
public abstract class BasePresenter<M, V extends BaseView> {

    protected M mModel;
    protected V mView;
    // 上下文，一般是Activity或者Fragment
    protected Context mContext;
    // 订阅集，用于管理请求取消
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 完成Presenter与View、Model之间的绑定关系
     */
    public BasePresenter(M m, V v) {
        this.mModel = m;
        this.mView = v;
        initContext();
    }

    /**
     * 初始化赋值上下文
     */
    private void initContext() {
        if (mView instanceof Fragment) {
            mContext = ((Fragment) mView).getActivity();
        } else if (mView instanceof Activity) {
            mContext = (Activity) mView;
        } else {
            mContext = null;
        }
    }

//    /**
//     * 创建Model层的同时，完成Presenter与Model的绑定，用Dagger2注入
//     *
//     * @return model对象
//     */
//    public abstract M getModel();

    /**
     * 在View层初始化完成后
     * 调用mPresenter.start()进行数据加载初始化
     */
    public void start() {

    }

    /**
     * 当View被销毁后onDestroy()，解除Presenter与View、Model的绑定
     * 防止持有对View层的引用，这样会引起内存泄漏
     */
    public void unbind() {
        mModel = null;
        mView = null;
        // 默认解绑的同时，对当前View中的每个Observer进行取消；
        mCompositeDisposable.clear();
        mCompositeDisposable = null;
    }

    /**
     * 开始订阅，用于事件总线通信
     * e.g. EventBus or RxBus
     */
    public void subscribe() {

    }

    /**
     * 取消订阅，用于事件总线通信
     * e.g. EventBus or RxBus
     */
    public void unsubscribe() {

    }

    public Context getContext() {
        return mContext;
    }

}
