package com.xuhj.android.base.eventbus;

/**
 * 线程枚举
 */
public enum ThreadMode {
    /**
     * current thread
     */
    CURRENT_THREAD,

    /**
     * android main thread, see AndroidSchedulers.mainThread()
     */
    MAIN,

    /**
     * new thread, see  Schedulers.newThread()
     */
    NEW_THREAD,

    /**
     * io, see Schedulers.io()
     */
    IO,

    /**
     * single, see Schedulers.single()
     */
    SINGLE,

    /**
     * computation, see Schedulers.computation()
     */
    COMPUTATION,

    /**
     * trampoline, see Schedulers.trampoline()
     */
    TRAMPOLINE
}
