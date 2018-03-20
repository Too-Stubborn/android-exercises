package com.xuhj.kotlin.mvp.simple8;

/**
 * 作者: Dream on 2017/9/4 22:31
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//直接封装MVP实现
public class ProxyMvpCallback_8<V extends MvpView_8, P extends MvpPresenter_8<V>>
        implements MvpCallback_8<V, P>, MvpPresenter_8<V> {

    private P mPresneter;
    private V mView;
    //持有目标对象引用->Activity
    //mvpCallback->本质就是Actiivty
    private MvpCallback_8<V, P> mMvpCallback;

    public ProxyMvpCallback_8(MvpCallback_8<V, P> mvpCallback) {
        this.mMvpCallback = mvpCallback;
        if (mMvpCallback == null) {
            throw new NullPointerException("不能够为空");
        }
    }

    @Override
    public P createPresenter() {
        setPresenter(mMvpCallback.createPresenter());
        return getPresneter();
    }

    @Override
    public V createMvpView() {
        setMvpView(mMvpCallback.createMvpView());
        return getMvpView();
    }

    @Override
    public void setPresenter(P presenter) {
        this.mPresneter = presenter;
    }

    @Override
    public void setMvpView(V view) {
        this.mView = view;
    }

    public P getPresneter() {
        return mPresneter;
    }

    @Override
    public V getMvpView() {
        return mView;
    }

    @Override
    public void attachView(V view) {
        setMvpView(view);
    }

    @Override
    public void detachView() {
        setMvpView(null);
    }
}
