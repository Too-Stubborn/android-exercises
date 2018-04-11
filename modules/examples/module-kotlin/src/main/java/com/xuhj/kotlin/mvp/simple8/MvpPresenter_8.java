package com.xuhj.kotlin.mvp.simple8;

/**
 * 作者: Dream on 2017/9/4 21:40
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//高度抽象UI层接口
public interface MvpPresenter_8<V extends MvpView_8> {

    void attachView(V view);

    void detachView();

}
