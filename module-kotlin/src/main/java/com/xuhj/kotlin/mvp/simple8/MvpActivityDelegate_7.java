package com.xuhj.kotlin.mvp.simple8;

import android.os.Bundle;

/**
 * 作者: Dream on 2017/9/4 22:35
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//第二重代理->目标接口->针对Activity生命周期进行代理
public interface MvpActivityDelegate_7 {

    public void onMvpCreate(Bundle savedInstanceState);

    public void onMvpStart();

    public void onMvpPause();

    public void onMvpResume();

    public void onMvpRestart();

    public void onMvpStop();

    public void onMvpDestroy();

}
