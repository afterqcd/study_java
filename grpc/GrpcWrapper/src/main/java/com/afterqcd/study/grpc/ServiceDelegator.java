package com.afterqcd.study.grpc;

import static com.afterqcd.study.grpc.Pipe.*;

import io.grpc.stub.StreamObserver;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.observers.Observers;
import rx.subjects.PublishSubject;

/**
 * Created by afterqcd on 2017/1/16.
 */
public class ServiceDelegator {
    /**
     * 将RxJava的方法包装成gRPC的方法。
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
     * 将RxJava的方法包装成gRPC的方法。
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
        return streamObserver(input);
    }
}
