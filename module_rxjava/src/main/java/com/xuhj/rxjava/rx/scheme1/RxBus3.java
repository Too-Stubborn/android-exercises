package com.xuhj.rxjava.rx.scheme1;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * 有异常处理的 Rxbus
 * <p>
 * 上面的两种 RxBus1 在订阅者处理事件出现异常后，订阅者无法再收到事件，
 * 这是 RxJava 当初本身的设计原则，但是在事件总线中这反而是个问题，
 * 不过 JakeWharton 大神写了即使出现异常也不会终止订阅关系的 RxRelay，
 * 所以基于 RxRelay 就能写出有异常处理能力的 Rxbus。
 */
public class RxBus3 {

    private final Relay<Object> mBus;

    private RxBus3() {
        // toSerialized method made bus thread safe
        mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus3 get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.accept(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBus3 BUS = new RxBus3();
    }
}