package com.xuhj.kotlin.mvp.simple8;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 作者: Dream on 2017/9/4 21:46
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//Activity抽象类
public abstract class MvpActivity_8<V extends MvpView_8, P extends MvpPresenter_8<V>>
        extends Activity implements MvpCallback_8<V, P> {

    //代理对象持有目标对象引用
    private MvpActivityDelegateImpl_8<V, P> mDelegateImpl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDelegateImpl().onMvpCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDelegateImpl().onMvpStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDelegateImpl().onMvpRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDelegateImpl().onMvpResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getDelegateImpl().onMvpPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegateImpl().onMvpStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegateImpl().onMvpDestroy();
    }

    @Override
    public P createPresenter() {
        return null;
    }

    @Override
    public V createMvpView() {
        return null;
    }

    public P getPresneter() {
        return mDelegateImpl.getPresneter();
    }

    @Override
    public V getMvpView() {
        return mDelegateImpl.getMvpView();
    }

    @Override
    public void setPresenter(P presenter) {
        mDelegateImpl.setPresenter(presenter);
    }

    @Override
    public void setMvpView(V view) {
        mDelegateImpl.setMvpView(view);
    }

    public MvpActivityDelegateImpl_8<V, P> getDelegateImpl() {
        if (mDelegateImpl == null) {
            this.mDelegateImpl = new MvpActivityDelegateImpl_8<V, P>(this);
        }
        return mDelegateImpl;
    }

}
