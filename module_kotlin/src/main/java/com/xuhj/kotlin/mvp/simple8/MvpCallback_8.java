package com.xuhj.kotlin.mvp.simple8;

/**
 * 作者: Dream on 2017/9/4 22:28
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//抽象解绑和绑定(MvpCallback)
public interface MvpCallback_8<V extends MvpView_8, P extends MvpPresenter_8<V>>{

    //创建Presenter
    P createPresenter();

    //创建View
    V createMvpView();

    P getPresneter();

    V getMvpView();

    void setPresenter(P presenter);

    void setMvpView(V view);
}
