package com.xuhj.rxjava.rx.scheme1;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 没有背压处理（Backpressure）的 Rxbus
 * <p>
 * 在 RxJava 2.0 之后，io.reactivex.Observable中没有进行背压处理了，
 * 如果有大量消息堆积在总线中来不及处理会产生MissingBackpressureException或者OutOfMemoryError，
 * 有新的类io.reactivex.Flowable 专门针对背压问题。
 */
public class RxBus1 {

    private final Subject<Object> mBus;

    private RxBus1() {
        // toSerialized method made bus thread safe
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus1 get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
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
        private static final RxBus1 BUS = new RxBus1();
    }
}
