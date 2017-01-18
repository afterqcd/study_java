package com.afterqcd.study.grpc;

import io.grpc.stub.StreamObserver;
import rx.Observable;
import rx.Observer;
import rx.observers.Observers;

/**
 * Created by afterqcd on 2017/1/17.
 */
public class Pipe {
    static <T> void pipe(final Observable<T> from, final StreamObserver<T> to) {
        from.subscribe(observer(to));
    }

    private static <T> Observer<T> observer(final StreamObserver<T> to) {
        return Observers.create(to::onNext, to::onError, to::onCompleted);
    }

    static <O> StreamObserver<O> streamObserver(final Observer<? super O> observer) {
        return new StreamObserver<O>() {
            @Override
            public void onNext(O o) {
                observer.onNext(o);
            }

            @Override
            public void onError(Throwable throwable) {
                observer.onError(throwable);
            }

            @Override
            public void onCompleted() {
                observer.onCompleted();
            }
        };
    }
}
