package com.xuhj.android.base.core;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhj.android.base.core.mvp.BasePresenter;
import com.xuhj.android.base.core.mvp.BaseView;

import java.lang.ref.SoftReference;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 * <p>
 * 1) 封装了MVP模式中的Presenter
 * 2) 封装了懒加载方法
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/3/30
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    private static final String TAG = "BaseFragment";

    // 上下文
    protected Context mContext;
    // application
    protected BaseApplication mBaseApplication;
    // rootView
    protected View mRootView;
    // butterknife unbinder
    private Unbinder mUnbinder;
    // 缓存View
    private SoftReference<View> mPreserveView;
    // P层控制器
    @Inject
    public P mPresenter;
    /*
        懒加载模块
     */
    // Fragment是否初始化完成【默认false：未初始化】
    protected boolean isInited = false;
    //当前Fragment是否可见【默认false：不可见】
    protected boolean isVisible = false;
    //是否是第一次加载数据【默认true：第一次加载】
    protected boolean isFirstLoad = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 这里保存的目的是，防止Fragment被销毁时，getActivity()==null的情况
        this.mContext = context;
        this.mBaseApplication = (BaseApplication) ((Activity) mContext).getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = mPreserveView == null ? null : mPreserveView.get();
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        mUnbinder = ButterKnife.bind(this, mRootView);
        // new Presenter() 注释原因请看方法说明
        mPresenter = getPresenter();
        // init component
        setupFragmentComponent();
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible) {  // 可见
            // 执行懒加载处理
            onLazyLoad();
        } else {  // 不可见

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onInitFragment(savedInstanceState);
        // 初始化标志位
        isInited = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        // activtiy的onResume时手动调用下懒加载的onResumeLoad
        onLazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // SoftReference cache view
        mPreserveView = new SoftReference<View>(getView());
        // unbind view
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        // unbind presenter
        if (mPresenter != null) {
            mPresenter.unbind();
            mPresenter = null;
        }
        /*
            懒加载模块
         */
        // 默认值，未初始化
        isInited = false;
        // 默认值，第一次加载
        isFirstLoad = true;
    }

    /**
     * 懒加载数据
     * <p>
     * 只有在Fragment初始化完成，并且可见时，才会调用
     */
    private void onLazyLoad() {
        // 只有Fragment初始化完成，并且可见时，才会执行
        if (isInited && isVisible) {
            // 每次可见时都会调用该方法
            // 注意第一次的时候onFirstLoad和onResumeLoad都会调用，小心初始化两次
            onResumeLoad();

            // 第一次加载
            if (isFirstLoad) {
                onFirstLoad();
                isFirstLoad = false;
            }
        }
    }

    /**
     * Fragment第一次可见时，会执行
     */
    protected void onFirstLoad() {
    }

    /**
     * Fragment每次可见时，都会执行
     */
    protected void onResumeLoad() {
    }

    /**
     * 根视图id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化Presenter同时，完成对Presenter与View的绑定
     * <p>
     *
     * @deprecated 该方法已被弃用，用Dagger2代替
     */
    @Deprecated
    protected P getPresenter() {
        return null;
    }

    /**
     * 初始化组件注入
     */
    protected void setupFragmentComponent() {

    }

    /**
     * 初始化View配置
     * <p>
     * Fragment初始化时，执行一次
     */
    protected abstract void onInitFragment(Bundle savedInstanceState);

    @Override
    public boolean isActive() {
        return isAdded();
    }
}