package com.afterqcd.study.grpc;

import io.grpc.stub.StreamObserver;
import rx.Observable;
import rx.Observer;
import rx.functions.Action2;
import rx.functions.Func1;
import rx.observers.Observers;
import rx.subjects.PublishSubject;

/**
 * Created by afterqcd on 2017/1/17.
 */
public class Method {
    /**
     * 将gRPC方法包装成RxJava方法。
     * @param input
     * @param method
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> Observable<O> wrap(
            final I input,
            final Action2<I, StreamObserver<O>> method) {
        return Observable.create(sub -> method.call(input, grpcObserver(sub)));
    }

    /**
     * 将gRPC方法包装成RxJava方法。
     * @param inputObservable
     * @param method
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> Observable<O> wrapWithClientStream(
            final Observable<I> inputObservable,
            final Func1<StreamObserver<O>, StreamObserver<I>> method) {
        return Observable.create(sub -> inputObservable.subscribe(rxObserver(method.call(grpcObserver(sub)))));
    }

    /**
     * 将gRPC方法的实现委托给RxJava方法。
     *
     * @param input
     * @param outputObserver
     * @param method
     * @param <I>
     * @param <O>
     */
    public static <I, O> void delegate(
            final I input,
            final StreamObserver<O> outputObserver,
            final Func1<I, Observable<O>> method) {
        method.call(input).subscribe(rxObserver(outputObserver));
    }

    /**
     * 将gRPC方法的实现委托给RxJava方法。
     *
     * @param outputObserver
     * @param method
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> StreamObserver<I> delegateWithClientStream(
            final StreamObserver<O> outputObserver,
            final Func1<Observable<I>, Observable<O>> method) {
        PublishSubject<I> input = PublishSubject.create();
        method.call(input).subscribe(rxObserver(outputObserver));
        return grpcObserver(input);
    }

    /**
     * 将{@link StreamObserver}包装成{@link Observer}
     * @param grpcObserver
     * @param <T>
     * @return
     */
    private static <T> Observer<T> rxObserver(final StreamObserver<T> grpcObserver) {
        return Observers.create(
                grpcObserver::onNext,
                grpcObserver::onError,
                grpcObserver::onCompleted
        );
    }

    /**
     * 将{@link Observer}包装成{@link StreamObserver}
     * @param rxObserver
     * @param <O>
     * @return
     */
    private static <O> StreamObserver<O> grpcObserver(final Observer<? super O> rxObserver) {
        return new StreamObserver<O>() {
            @Override
            public void onNext(O o) {
                rxObserver.onNext(o);
            }

            @Override
            public void onError(Throwable throwable) {
                rxObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                rxObserver.onCompleted();
            }
        };
    }
}
