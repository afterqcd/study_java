package com.afterqcd.study.rxjava;

import rx.Observer;

/**
 * Created by afterqcd on 16/7/29.
 */
class Printer<T> implements Observer<T> {
    public static <U> Printer<U> create() {
        return new Printer<>();
    }

    @Override
    public void onCompleted() {
        Logger.log("Completed");
    }

    @Override
    public void onError(Throwable e) {
        Logger.log(e.getMessage());
    }

    @Override
    public void onNext(T value) {
        Logger.log("Received " + value);
    }
}
