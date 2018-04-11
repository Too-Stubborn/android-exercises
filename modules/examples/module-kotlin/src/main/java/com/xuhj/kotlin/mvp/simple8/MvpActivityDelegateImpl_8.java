package com.xuhj.kotlin.mvp.simple8;

import android.os.Bundle;

/**
 * 作者: Dream on 2017/9/4 22:38
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//第二重代理->目标对象->针对的是Activity生命周期
public class MvpActivityDelegateImpl_8<V extends MvpView_8, P extends MvpPresenter_8<V>>
        implements MvpActivityDelegate_7, MvpCallback_8<V, P> {

    private ProxyMvpCallback_8<V, P> mProxyMvpCallback;

    public MvpActivityDelegateImpl_8(MvpCallback_8<V, P> mvpCallback) {
        this.mProxyMvpCallback = new ProxyMvpCallback_8<>(mvpCallback);
    }

    // ------------------------------- 生命周期代理 -----------------------------------------------
    @Override
    public void onMvpCreate(Bundle savedInstanceState) {
        mProxyMvpCallback.createPresenter();
        mProxyMvpCallback.createMvpView();
        mProxyMvpCallback.attachView(mProxyMvpCallback.getMvpView());
    }

    @Override
    public void onMvpStart() {

    }

    @Override
    public void onMvpPause() {

    }

    @Override
    public void onMvpResume() {

    }

    @Override
    public void onMvpRestart() {

    }

    @Override
    public void onMvpStop() {

    }

    @Override
    public void onMvpDestroy() {
        mProxyMvpCallback.detachView();
    }

    // --------------------------------------------------------------------------------------------
    @Override
    public P createPresenter() {
        return mProxyMvpCallback.createPresenter();
    }

    @Override
    public V createMvpView() {
        return mProxyMvpCallback.createMvpView();
    }

    @Override
    public P getPresneter() {
        return mProxyMvpCallback.getPresneter();
    }

    @Override
    public V getMvpView() {
        return mProxyMvpCallback.getMvpView();
    }

    @Override
    public void setPresenter(P presenter) {
        mProxyMvpCallback.setPresenter(presenter);
    }

    @Override
    public void setMvpView(V view) {
        mProxyMvpCallback.setMvpView(view);
    }
}
