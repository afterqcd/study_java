package com.afterqcd.study.grpc;

import static com.afterqcd.study.grpc.Pipe.*;

import io.grpc.stub.StreamObserver;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func1;
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
        return Observable.create(sub -> pipe(inputObservable, method.call(grpcObserver(sub))));
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
        pipe(method.call(input), outputObserver);
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
        pipe(method.call(input), outputObserver);
        return grpcObserver(input);
    }
}
