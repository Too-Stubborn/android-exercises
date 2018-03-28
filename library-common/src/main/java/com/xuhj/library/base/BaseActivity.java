package com.xuhj.library.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.xuhj.library.BaseApplication;
import com.xuhj.library.mvp.BasePresenter;
import com.xuhj.library.util.ActivityManager;
import com.xuhj.library.util.AndroidUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Activity基类
 * <p>
 * 封装了MVP模式中的Presenter
 *
 * @author xuhj
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    // 上下文
    protected Activity mContext;
    // application
    protected BaseApplication mBaseApplication;
    // P层控制器
    @Inject
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this.mBaseApplication = (BaseApplication) getApplication();
        // add activity to manager
        ActivityManager.addActivity(this);
        // set content view
        setContentView(getLayoutId());
        // bind view
        ButterKnife.bind(this);
//        // new Presenter() 注释原因请看方法说明
//        mPresenter = getPresenter();
        // init component
        setupActivityComponent();
        // init activity
        onInitActivity(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 删除activity
        ActivityManager.removeActivity(this);
        // unbind presenter
        if (mPresenter != null) {
            mPresenter.unbind();
            mPresenter = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 初始化Presenter同时，完成对Presenter与View的绑定
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
    protected abstract void setupActivityComponent();

    /**
     * 初始化activity操作
     *
     * @param savedInstanceState
     */
    protected abstract void onInitActivity(Bundle savedInstanceState);


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        /*
            如果系统字体大小改变，则显示默认配置【fontScale：0.85 小 1 标准大小 1.3 超大 1.45 特大】
         */
        if (res.getConfiguration().fontScale != 1) {
            Configuration config = new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return super.getResources();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 点击空白处关闭软键盘
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AndroidUtils.HideKeyboard(getWindow().getDecorView());
            getWindow().getDecorView().requestFocus();
        }
        return super.onTouchEvent(event);
    }

    /**
     * 附加fragment
     *
     * @param containerViewId
     * @param instance
     * @param tag
     */
    protected void attachFragment(int containerViewId, Fragment instance, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
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