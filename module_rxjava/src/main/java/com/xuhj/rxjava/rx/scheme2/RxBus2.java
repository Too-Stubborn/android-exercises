package com.xuhj.rxjava.rx.scheme2;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * RxBus & Replay
 */
public final class RxBus2 {
    private final Relay<Object> bus = PublishRelay.create().toSerialized();

    public void send(Object event) {
        bus.accept(event);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}