package com.xuhj.rxjava.rx.scheme2;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * RxBus
 */
public final class RxBus1 {
    private final PublishSubject<Object> bus = PublishSubject.create();

    public void send(final Object event) {
        bus.onNext(event);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}