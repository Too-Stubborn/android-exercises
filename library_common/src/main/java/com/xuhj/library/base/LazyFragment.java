package com.xuhj.library.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 懒加载Fragment
 *
 * @author xuhj
 */
public abstract class LazyFragment extends BaseFragment {
    private static final String TAG = "MVPLazyFragment";

    // Fragment是否初始化完成【默认false：未初始化】
    protected boolean isInited = false;
    //当前Fragment是否可见【默认false：不可见】
    protected boolean isVisible = false;
    //是否是第一次加载数据【默认true：第一次加载】
    protected boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible) {
            onLazyLoad();
        } else {
            // ...
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isInited = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        // activtiy的onResume时手动调用下懒加载的onResumeLoad
        onLazyLoad();
    }

    /**
     * 懒加载数据
     * <p>
     * 只有在Fragment初始化完成，并且可见时，才会调用
     */
    private void onLazyLoad() {
        // 只有Fragment初始化完成，并且可见时，才会执行
        if (isInited && isVisible) {
            // 每次可见时都会调用该方法，注意第一次的时候onFirstLoad和onResumeLoad都会调用，小心初始化两次
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
     * Fragment被销毁后，记得把一些标志位变量初始化默认值，防止有些判断逻辑会错误
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 默认值，未初始化
        isInited = false;
        // 默认值，第一次加载
        isFirstLoad = true;
    }


}