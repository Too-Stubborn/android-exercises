package com.xuhj.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuhj.library.BaseApplication;
import com.xuhj.library.mvp.BasePresenter;

import java.lang.ref.SoftReference;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 * <p>
 * 封装了MVP模式中的Presenter
 *
 * @author xuhj
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
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
            mUnbinder = ButterKnife.bind(this, mRootView);
        }
//        // new Presenter() 注释原因请看方法说明
//        mPresenter = getPresenter();
        // init component
        setupFragmentComponent();
        onInitFragment(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unbind view
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        // SoftReference cache view
        mPreserveView = new SoftReference<View>(getView());
        // unbind presenter
        if (mPresenter != null) {
            mPresenter.unbind();
            mPresenter = null;
        }
    }

    /**
     * 初始化Presenter同时，完成对Presenter与View的绑定
     * <p>
     *
     * @deprecated 该方法已被弃用，使用Dagger2注入代替
     */
    @Deprecated
    protected P getPresenter() {
        return null;
    }

    /**
     * 根视图id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化component注入
     */
    protected abstract void setupFragmentComponent();

    /**
     * 初始化View配置
     * <p>
     * Fragment初始化时，执行一次
     */
    protected abstract void onInitFragment(Bundle savedInstanceState);

    /**
     * 附加fragment
     *
     * @param containerViewId
     * @param instance
     * @param tag
     */
    protected void attachFragment(int containerViewId, Fragment instance, String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment attachedFragment = fragmentManager.findFragmentById(containerViewId);

        if (attachedFragment == null || !attachedFragment.getTag().equals(tag)) {
            //begin transaction
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // if exists,at first,detach
            if (attachedFragment != null) {
                transaction.detach(attachedFragment);
            }

            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            // if null,then add
            if (fragment == null) {
                fragment = instance;
                transaction.add(containerViewId, fragment, tag);
            } else {
                //if exists,then attach
                transaction.attach(fragment);
            }

            //finally commit
            transaction.commit();
        }
    }

}